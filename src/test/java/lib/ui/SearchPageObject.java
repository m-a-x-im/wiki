package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

abstract public class SearchPageObject extends MainPageObject {

    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    protected static String
            SKIP_WELCOME,
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL,
            LANG_BUTTON,
            SEARCH_RESULTS_LIST,
            SEARCH_RESULT_WITH_DESCRIPTION_TEMPLATE,
            SEARCH_RESULT_WITH_TITLE_AND_DESCRIPTION_TEMPLATE,
            SEARCH_RESULT_TITLE_ELEMENT,
            LABEL_OF_EMPTY_SEARCH_RESULTS,
            ARTICLE_TOOLBAR_SEARCH_BUTTON,
            SEARCH_RESULT_ARTICLE_TITLE_ATTRIBUTE,
            SEARCH_INPUT_TEXT_ATTRIBUTE,
            TAB_BAR;


    /* TEMPLATE METHODS */

    @Step("Retrieve the Locator for the Search Result based on the Article Description: '{description}'")
    private static String getSearchResultWithDescriptionLocator(String description) {
        return SEARCH_RESULT_WITH_DESCRIPTION_TEMPLATE.replace(
                "{DESCRIPTION}",
                description
        );
    }

    @Step("Retrieve the Locator for the Search Result " +
            "based on the Article Title – '{title}' and Description – '{description}'")
    private static String getSearchResultWithTitleAndDescriptionXpath(String title, String description) {
        String xpath_with_title = SEARCH_RESULT_WITH_TITLE_AND_DESCRIPTION_TEMPLATE.replace(
                "{TITLE}",
                title
        );
        return xpath_with_title.replace("{DESCRIPTION}", description);
    }
    /* TEMPLATE METHODS */


    @Step("Initialize the Search Input: bypass the Welcome Page and tap the Search Bar")
    public void initSearchInput() {
        if (!Platform.getInstance().isMobileWeb()) {
            this.waitForElementAndClick(
                    SKIP_WELCOME,
                    "The Skip Button cannot be found using '" + SKIP_WELCOME + "'",
                    5
            );
        }

        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "The Search Bar Container cannot be found using '" + SEARCH_INIT_ELEMENT + "'",
                5
        );
    }

    @Step("Enter '{query}' into the Search Field")
    public WebElement enterQueryIntoSearchField(String query) {
        return
                this.waitForElementAndSendKeys(
                        SEARCH_INPUT,
                        query,
                        "The Search Line cannot be found using '" + SEARCH_INPUT + "'",
                        5
                );
    }

    @Step("Wait for the Cancel Search Button to appear")
    public void waitForCancelSearchButtonToAppear() {
        this.waitForElementPresent(
                SEARCH_CANCEL,
                "The Cancel Search Button cannot be found using '" + SEARCH_CANCEL + "'",
                5
        );
    }

    @Step("Tap the Cancel Search Button")
    public void tapCancelSearchButton() {
        this.waitForElementAndClick(
                SEARCH_CANCEL,
                "The Cancel Search Button cannot be found using '" + SEARCH_CANCEL + "'",
                5
        );
    }

    @Step("Wait for the Cancel Search Button to appear")
    public void waitForCancelSearchButtonToDisappear() {
        this.waitForElementNotPresent(
                SEARCH_CANCEL,
                "The Cancel Search Button is still on the screen",
                5
        );
    }

    @Step("Wait for the Search Result List to appear")
    public void waitForSearchResultsList() {
        this.waitForElementPresent(
                SEARCH_RESULTS_LIST,
                "The List of Search Results cannot be found using '" + SEARCH_RESULTS_LIST + "'",
                10
        );
    }

    @Step("Wait for the Search Result that matches the Article Description: '{description}'")
    public void waitForSearchResultWithDescription(String description) {
        String search_result_locator = getSearchResultWithDescriptionLocator(description);

        this.waitForElementPresent(
                search_result_locator,
                "The Search Result cannot be found using '" + search_result_locator + "'",
                10
        );
    }

    @Step("Open the Article that matches '{description}'")
    public void openArticleWithDescription(String description) {
        String search_result_locator = getSearchResultWithDescriptionLocator(description);

        this.waitForElementAndClick(
                search_result_locator,
                "The Search Result cannot be found using '" + search_result_locator + "'",
                10
        );
    }

    @Step("Get the Number of Articles found")
    public int getNumberOfArticlesFound() {
        this.waitForElementPresent(
                SEARCH_RESULT_TITLE_ELEMENT,
                "No Articles could be found using '" + SEARCH_RESULT_TITLE_ELEMENT + "'",
                5
        );
        return this.getNumberOfElements(SEARCH_RESULT_TITLE_ELEMENT);
    }

    @Step("Wait for the Empty Search Result Label to appear")
    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(
                LABEL_OF_EMPTY_SEARCH_RESULTS,
                "The Label of Empty Search Results cannot be found using '" + LABEL_OF_EMPTY_SEARCH_RESULTS + "'",
                10
        );
    }

    @Step("Retrieve a List of Search Results containing title elements")
    public List getSearchResultsList() {
        By locator = this.getLocatorByString(SEARCH_RESULT_TITLE_ELEMENT);
        return driver.findElements(locator);
    }

    @Step("Retrieve the Search Bar text")
    public String getSearchBarText() {
        return
                this.waitForElementAndGetAttribute(
                        SEARCH_INPUT,
                        SEARCH_INPUT_TEXT_ATTRIBUTE,
                        "The Search Bar cannot be found using '" + SEARCH_INPUT + "'",
                        5
                );
    }

    @Step("Retrieve the Article Title Text by the Attribute")
    public String getArticleTitleByAttribute(WebElement element) {
        return element.getAttribute(SEARCH_RESULT_ARTICLE_TITLE_ATTRIBUTE);
    }

    @Step("Enter the Query into the Search Field located on the Article Toolbar")
    public void enterQueryIntoArticleToolbarSearchField(String query) {
        this.waitForElementAndClick(
                ARTICLE_TOOLBAR_SEARCH_BUTTON,
                "The Search Bar on the toolbar cannot be found using '" + ARTICLE_TOOLBAR_SEARCH_BUTTON + "'",
                5
        );
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                query,
                "The Search Line cannot be found using '" + SEARCH_INPUT + "'",
                5
        );
    }

    @Step("Wait for the Article that matches the Title '{title}' and Description '{description}' in the Search Results")
    public void waitForSearchResultWithTitleAndDescription(String title, String description) {
        String search_result_xpath = getSearchResultWithTitleAndDescriptionXpath(title, description);

        this.waitForElementPresent(
                search_result_xpath,
                "The Article with title '" + title + "' and description '" + description + "' cannot be " +
                        "found using '" + search_result_xpath + "'",
                5
        );
    }

    @Step("Wait for the Land Button to disappear")
    public void waitToLangButtonToDisappear() {
        this.waitForElementNotPresent(
                LANG_BUTTON,
                "The Lang Button is still present on the screen",
                5
        );
    }

    @Step("Wait for the Tab Bar to appear")
    public void waitForTabBarToAppear() {
        this.waitForElementPresent(
                TAB_BAR,
                "The Tab Bar cannot be found using '" + TAB_BAR + "'",
                5
        );
    }
}
