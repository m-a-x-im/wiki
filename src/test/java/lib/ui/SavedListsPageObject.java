package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SavedListsPageObject extends MainPageObject {

    public SavedListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }


    protected static String
            SAVED_LIST_TEMPLATE,
            WATCHLIST,
            UNWATCH_ARTICLE_BUTTON_TEMPLATE,
            SAVED_ARTICLE_WITH_TITLE_TEMPLATE,
            SAVED_ARTICLE_WITH_DESCRIPTION_TEMPLATE,
            SWIPE_ACTION_DELETE_BUTTON,
            SNACKBAR_ACTION;


    /* TEMPLATE METHODS */

    @Step("Get a locator for the List '{list_name}'")
    private static String getSavedListLocatorByName(String list_name) {
        return SAVED_LIST_TEMPLATE.replace("{LIST_NAME}", list_name);
    }

    @Step("Get a locator for the Article Title '{article_title}'")
    private static String getSavedArticleLocatorByTitle(String article_title) {
        return SAVED_ARTICLE_WITH_TITLE_TEMPLATE.replace("{ARTICLE_TITLE}", article_title);
    }

    @Step("Get a locator for the Article Description '{article_description}'")
    private static String getSavedArticleLocatorByDescription(String article_description) {
        return SAVED_ARTICLE_WITH_DESCRIPTION_TEMPLATE.replace("{ARTICLE_DESCRIPTION}", article_description);
    }

    @Step("[Mobile Web] Get the Unwatch button locator for the Article titled '{article_title}'")
    private static String getUnwatchButtonLocatorByTitle(String article_title) {
        return UNWATCH_ARTICLE_BUTTON_TEMPLATE.replace("{ARTICLE_TITLE}", article_title);
    }

    /* TEMPLATE METHODS */


    @Step("Open the List '{list_name}'")
    public void openSavedListByName(String list_name) {
        String saved_list_locator = getSavedListLocatorByName(list_name);

        this.waitForElementAndClick(
                saved_list_locator,
                "The Saved List '" + list_name + "' cannot be open using '" + saved_list_locator + "'",
                5
        );
    }

    @Step("Wait for the Article titled '{article_title}' to appear. After that, get the Article Title locator")
    public String waitForArticleToAppearByTitle(String article_title) {

        String saved_article_locator = getSavedArticleLocatorByTitle(article_title);

        this.waitForElementPresent(
                saved_article_locator,
                "The Article '" + article_title + "' cannot be found in the saved list using '" + saved_article_locator + "'",
                10
        );
        return saved_article_locator;
    }

    @Step("Wait for the Article titled '{article_title}' to disappear from the List.")
    public void waitForArticleToDisappearByTitle(String article_title) {
        String saved_article_locator = getSavedArticleLocatorByTitle(article_title);

        this.waitForElementNotPresent(
                saved_article_locator,
                "The Article '" + article_title + "' is still present in the saved list",
                10
        );
    }

    @Step("Swipe the Article titled '{article_title}' to remove it from the List")
    public void swipeArticleToRemove(String article_title) {

        String saved_article_xpath = waitForArticleToAppearByTitle(article_title);
        this.swipeElementToLeft(
                saved_article_xpath,
                "Couldn't swipe element with text '" + article_title + "' to delete"
        );
        if (Platform.getInstance().isIOS()) tapRemoveArticleButtonIOS();

        waitForArticleToDisappearByTitle(article_title);
    }

    @Step("[iOS] Tap the button to remove the Article from the List")
    public void tapRemoveArticleButtonIOS() {
        waitForElementAndClick(
                SWIPE_ACTION_DELETE_BUTTON,
                "The Swipe Action Delete Button cannot be found using '" + SWIPE_ACTION_DELETE_BUTTON + "'",
                5
        );
    }

    @Step("Wait for the Snackbar with a cancel button to disappear")
    public void waitForSnackbarToDisappear() {
        this.waitForElementNotPresent(
                SNACKBAR_ACTION,
                "The Snackbar with the Undo Button is still present on the screen",
                5
        );
    }

    @Step("Check if the Article titled '{article_title}' is not in the List")
    public boolean isArticleNotOnListByTitle(String article_title) {
        String saved_article_locator = getSavedArticleLocatorByTitle(article_title);
        By locator = this.getLocatorByString(saved_article_locator);
        return driver.findElements(locator).isEmpty();
    }

    @Step("Check if the Article described '{article_description}' is in the List")
    public boolean isArticleOnListByDescription(String article_description) {
        String saved_article_locator = getSavedArticleLocatorByDescription(article_description);
        By locator = this.getLocatorByString(saved_article_locator);
        return !(driver.findElements(locator).isEmpty());
    }

    @Step("[Mobile Web] Check if the Article titled 'article_title' is in the Watchlist")
    public boolean isArticleOnWatchlist(String article_title) {
        String unwatch_button_locator = getUnwatchButtonLocatorByTitle(article_title);
        try {
            this.waitForElementNotPresent(unwatch_button_locator, "", 5);
            return false;
        } catch (TimeoutException e) {
            return true;
        }
    }

    @Step("Remove the Article titled '{article_title}' from the List")
    public void removeArticleFromWatchlist(String article_title) {
        String unwatch_button_locator = getUnwatchButtonLocatorByTitle(article_title);
        this.waitForElementAndClick(
                unwatch_button_locator,
                "The Article cannot be removed from the watchlist using '" + unwatch_button_locator + "'",
                5
        );
    }

    @Step("[Mobile Web] Open the Article titled '{article_title}' from the Watchlist")
    public void openArticleFromWatchlistByTitle(String article_title) {
        String saved_article_locator = getSavedArticleLocatorByTitle(article_title);
        this.waitForElementAndClick(
                saved_article_locator,
                "The Article with title cannot be found using '" + saved_article_locator + "'",
                5
        );
    }
}
