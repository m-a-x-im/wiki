package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AuthorizationPageObject extends MainPageObject {
    public AuthorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    private static final String
        LOGIN_BUTTON = "css:a.cdx-button--action-progressive",
        LOGIN_INPUT = "css:#wpName1",
        PASSWORD_INPUT = "css:#wpPassword1",
        SUBMIT_BUTTON = "css:button.mw-htmlform-submit";


    @Step("Retrieve the Login Data from a configuration file")
    private List<String> getLoginData() throws IOException {
        String configFilePath = "src/test/java/config.properties";
        FileInputStream propertiesInput = new FileInputStream(configFilePath);

        Properties properties = new Properties();
        properties.load(propertiesInput);

        List<String> loginData = new ArrayList<>();
        loginData.add(properties.getProperty("LOGIN"));
        loginData.add(properties.getProperty("PASSWORD"));

        return loginData;
    }

    @Step("Tap the Login Button")
    public void clickAuthButton() throws InterruptedException {
        this.waitForElementPresent(
                LOGIN_BUTTON,
                "The Login Button cannot be found using '" + LOGIN_BUTTON + "'",
                5
        );
        /* Without a sleep, it doesn't work –
        chromedriver clicks before JavaScript has a chance to subscribe to the click event */
        Thread.sleep(2000);
        this.waitForElementAndClick(
                LOGIN_BUTTON,
                "The Login Button cannot be found and clicked using '" + LOGIN_BUTTON + "'",
                2
        );
    }

    @Step("Enter Login and Password into a Login Form")
    public void typeLoginData() throws IOException {

        String login = getLoginData().get(0);
        String password = getLoginData().get(1);

        this.waitForElementAndSendKeys(
                LOGIN_INPUT,
                login,
                "The Login Input cannot be found and typed using '" + LOGIN_INPUT + "'",
                2
        );
        this.waitForElementAndSendKeys(
                PASSWORD_INPUT,
                password,
                "The Password Input cannot be found and typed using '" + PASSWORD_INPUT + "'",
                2
        );
    }

    @Step("Tap the Submit Button to send a Login Data")
    public void submitForm() {
        this.waitForElementAndClick(
                SUBMIT_BUTTON,
                "The Submit Button cannot be found and clicked using '" + SUBMIT_BUTTON + "'",
                1
        );
    }
}
