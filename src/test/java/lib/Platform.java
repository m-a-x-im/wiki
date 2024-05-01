package lib;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Platform {
    private static final String
            PLATFORM_IOS = "ios",
            PLATFORM_ANDROID = "android",
            PLATFORM_MOBILE_WEB = "mobile_web",
            APPIUM_URL = "http://127.0.0.1:4723/",
            ANDROID_APP_PATH = "/Users/maxim/git/m-a-x-im/OldJavaAppiumAutomation/apk_files/org.wikipedia.apk",
            IOS_APP_PATH = "/Users/maxim/git/m-a-x-im/OldJavaAppiumAutomation/apk_files/Wikipedia693.app";


    /* Приватный конструктор синглтона */
    private static Platform instance;
    private Platform() {};

    public static Platform getInstance() {
        if (instance == null)
            instance = new Platform();
        return instance;
    }
    /* */

    /**
     * Получить драйвер в зависимости от платформы
     */
    public RemoteWebDriver getDriver() throws Exception {

        URL url = new URL(APPIUM_URL);

        if (this.isAndroid()) {
            return new AndroidDriver<>(url, this.setAndroidCapabilities());
        } else if (this.isIOS()) {
            return new IOSDriver<>(url, this.setIOSCapabilities());
        } else if (this.isMobileWeb()) {
            return new ChromeDriver(this.getMobileWebChromeOptions());
        } else {
            throw new Exception("The Driver Type cannot be detected for the platform value: " + this.getPlatformVar());
        }
    }

    public boolean isAndroid() {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS() {
        return isPlatform(PLATFORM_IOS);
    }

    public boolean isMobileWeb() {
        return isPlatform(PLATFORM_MOBILE_WEB);
    }

    /**
     * Установить capabilities для запуска тестов на Android
     */
    private DesiredCapabilities setAndroidCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:deviceName", "AndroidTestDevice");
        capabilities.setCapability("appium:platformVersion", "14");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:appPackage", "org.wikipedia");
        capabilities.setCapability("appium:appActivity", ".main.MainActivity");
        capabilities.setCapability("appium:app", ANDROID_APP_PATH);
        return capabilities;
    }

    /**
     * Установить capabilities для запуска тестов на iOS
     */
    private DesiredCapabilities setIOSCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("appium:deviceName", "iPhone 15 Pro Max");
        capabilities.setCapability("appium:platformVersion", "17.2");
        capabilities.setCapability("appium:automationName", "XCUITest");
        capabilities.setCapability("appium:app", IOS_APP_PATH);
        return capabilities;
    }

    /**
     * Установить capabilities для запуска тестов в Chrome
     */
    private ChromeOptions getMobileWebChromeOptions() {
        Map<String, Object> deviceMetrics = new HashMap<String, Object>();
        deviceMetrics.put("width", 360);
        deviceMetrics.put("height", 740);
        deviceMetrics.put("pixelRatio", 1.0);

        Map<String, Object> mobileEmulation = new HashMap<String, Object>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Mobile Safari/537.36");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=360,740");

        return chromeOptions;
    }

    /**
     * Сравнить значение платформы с переменной окружения
     */
    private boolean isPlatform(String my_platform) {
        String platform = getPlatformVar();
        return my_platform.equals(platform);
    }

    /**
     * Получить значение переменной окружения "PLATFORM"
     */
    public String getPlatformVar() {
        return System.getenv("PLATFORM");
    }

    /**
     * Получить строковое представление capabilities
     */
    public String getCapabilitiesString() {
        if (this.isAndroid()) return this.setAndroidCapabilities().toString();
        else if (this.isIOS()) return this.setIOSCapabilities().getCapabilityNames().toString();
        else return getMobileWebChromeOptions().toString();
    }
}
