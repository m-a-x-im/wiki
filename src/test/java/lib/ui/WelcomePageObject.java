package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class WelcomePageObject extends MainPageObject {

    protected static String
            FIRST_PAGE_LINK,
            NEXT_BUTTON,
            SECOND_PAGE_TITLE,
            THIRD_PAGE_LINK,
            FOURTH_PAGE_LINK,
            GET_STARTED_ACCEPT_BUTTON;


    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Wait for the First Page link to appear")
    public void waitForFirstPageLink() {
        this.waitForElementPresent(
                FIRST_PAGE_LINK,
                "The 'Learn more about Wikipedia' Link cannot be found using '" + FIRST_PAGE_LINK + "'",
                5
        );
    }

    @Step("Tap the 'Next' button")
    public void clickNextButton() {
        this.waitForElementAndClick(
                NEXT_BUTTON,
                "The 'Next' Button cannot be found using '" + NEXT_BUTTON + "'",
                5
        );
    }

    @Step("Wait for the Second Page title")
    public void waitForSecondPageTitle() {
        this.waitForElementPresent(
                SECOND_PAGE_TITLE,
                "The 'New Ways To Explore' Title cannot be found using '" + SECOND_PAGE_TITLE + "'",
                5
        );
    }

    @Step("Wait for the Third Page link")
    public void waitForThirdPageLink() {
        this.waitForElementPresent(
                THIRD_PAGE_LINK,
                "The 'Add or edit preferred languages' Link cannot be found using '" + THIRD_PAGE_LINK + "'",
                5
        );
    }

    @Step("Wait for the Fourth Page link")
    public void waitForFourthPageLink() {
        this.waitForElementPresent(
                FOURTH_PAGE_LINK,
                "The 'Learn more about data collected' Link cannot be found using '" + FOURTH_PAGE_LINK + "'",
                5
        );
    }

    @Step("Tap the 'Get started' / 'Accept' button")
    public void clickGetStartedOrAcceptButton() {
        this.waitForElementAndClick(
                GET_STARTED_ACCEPT_BUTTON,
                "The 'Get started' Button cannot be found using '" + GET_STARTED_ACCEPT_BUTTON + "'",
                5
        );
    }
}
