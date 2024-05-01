package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject {

    public NavigationUI(RemoteWebDriver driver)
    {
        super(driver);
    }

    protected static String
            BACK_ARROW,
            SAVED_TAB,
            READING_LISTS_TAB,
            CLOSE_LOG_IN_DIALOG_BUTTON,
            CANCEL_BUTTON,
            MENU_BUTTON,
            WATCHLIST;

    @Step("Tap the back arrow")
    public void clickBackArrow() {
        this.waitForElementAndClick(
            BACK_ARROW,
            "The Back Arrow cannot be found using '" + BACK_ARROW + "'",
            5
        );
    }

    @Step("Open a list containing saved articles")
    public void openSavedListsView() {
        this.waitForElementAndClick(SAVED_TAB,
            "The tab 'Saved' cannot be found using '" + SAVED_TAB + "'",
            5
        );
    }

    @Step("Close an auth dialog")
    public void closeLogInDialog() {
        this.waitForElementAndClick(
                CLOSE_LOG_IN_DIALOG_BUTTON,
                "The Close Button cannot be found using '" + CLOSE_LOG_IN_DIALOG_BUTTON + "'",
                5
        );
    }

    @Step("[iOS] Open the 'Reading Lists' tab")
    public void openReadingListsTab() {
        this.waitForElementAndClick(
                READING_LISTS_TAB,
                "The 'Reading Lists' Tab cannot be found using '" + READING_LISTS_TAB + "'",
                5
            );
    }

    @Step("[Mobile Web] Open a main menu")
    public void openMenu() {
        this.waitForElementAndClick(
                MENU_BUTTON,
                "The Menu Button cannot be found using '" + MENU_BUTTON + "'",
                2
        );
    }

    @Step("[Mobile Web] Open a list containing saved articles")
    public void openWatchlistFromMenu() {
        this.waitForElementAndClick(
                WATCHLIST,
                "The Watchlist cannot be found using '" + WATCHLIST + "'",
                5
        );
    }

}
