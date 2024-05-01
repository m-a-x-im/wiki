package lib;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileOutputStream;
import java.time.Duration;
import java.util.Properties;

/**
 * Set Up & Tear Down methods
 */
public class CoreTestCase {

    protected RemoteWebDriver driver;

    @Before
    @Step("Run the Driver and Session")
    public void setUp() throws Exception {
        driver = Platform.getInstance().getDriver();
        this.createAllureEnvironmentPropertiesFile();
        this.rotateScreenPortrait();
        this.openWikiWebpageForMobileWeb();
    }

    @After
    @Step("Terminate the Driver and Session")
    public void tearDown() {
        driver.quit();
    }

    @Step("Rotate the Screen to the Portrait Mode (not available on Mobile Web)")
    protected void rotateScreenPortrait() {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.PORTRAIT);
        } else {
            System.out.println("Method rotateScreenPortrait() does nothing for " +
                    Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Rotate the Screen to the Landscape Mode (not available on Mobile Web)")
    protected void rotateScreenLandscape() {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.LANDSCAPE);
        } else {
            System.out.println("Method rotateScreenLandscape() does nothing for " +
                    Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Send the App to the background for a while (not available on Mobile Web)")
    protected void sendAppToBackground(int seconds) {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.runAppInBackground(Duration.ofSeconds(seconds));
        } else {
            System.out.println("Method sendAppToBackground() does nothing for " +
                    Platform.getInstance().getPlatformVar());
        }
    }

    @Step("[Mobile Web] Open the main Wiki Webpage (not available on Android and iOS)")
    protected void openWikiWebpageForMobileWeb() {
        if (Platform.getInstance().isMobileWeb()) {
            driver.get("https://en.m.wikipedia.org/");
        } else {
            System.out.println("Method openWikiWebpageForMobileWeb() does nothing for " +
                    Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Create the Allure Environment Properties file")
    private void createAllureEnvironmentPropertiesFile() {
        String configFilePath = System.getProperty("allure.results.directory");
        try {
            Properties properties = new Properties();
            FileOutputStream propertiesOutput = new FileOutputStream(configFilePath + "/environment.properties");

            properties.setProperty("Platform", Platform.getInstance().getPlatformVar());

            String value = Platform.getInstance().getCapabilitiesString();
            properties.setProperty("Capabilities", value);

            properties.store(propertiesOutput, "See https://allurereport.org/docs/#_environment");
            propertiesOutput.close();
        } catch (Exception e) {
            System.err.println("There is some problem with writing to the Allure Environment Properties file.");
            e.printStackTrace();
        }
    }
}
