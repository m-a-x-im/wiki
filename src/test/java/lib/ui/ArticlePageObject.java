package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject {

    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }


    protected static String
            ARTICLE_TITLE,
            ARTICLE_DESCRIPTION,
            HEADER,
            FOOTER,
            SAVE_BUTTON,
            SNACKBAR_BUTTON,
            CREATE_LIST_BUTTON_IOS,
            LIST_NAME_INPUT,
            OK_BUTTON,
            LIST_TO_SAVE_ARTICLE_TEMPLATE,
            SEARCH_INPUT_TEXT_ATTRIBUTE,
            UNWATCH_ARTICLE_BUTTON;


    /* TEMPLATE METHODS */

    @Step("Retrieve the List '{list_name}' Locator to add the article to this list")
    private static String getListToSaveArticleLocatorByName(String list_name) {
        return LIST_TO_SAVE_ARTICLE_TEMPLATE.replace("{LIST_NAME}", list_name);
    }

    /* TEMPLATE METHODS */


    @Step("Retrieve the Article Title element")
    public WebElement waitForArticleTitle() {
        return
                this.waitForElementPresent(
                        ARTICLE_TITLE,
                        "The Title of the Article cannot be found using '" + ARTICLE_TITLE + "'",
                        10
                );
    }

    @Step("Retrieve the Article Description element")
    public WebElement waitForArticleDescription() {
        return
                this.waitForElementPresent(
                        ARTICLE_DESCRIPTION,
                        "The Description of the Article cannot be found using '" + ARTICLE_DESCRIPTION + "'",
                        10
                );
    }

    @Step("Retrieve the Article Title text")
    public String getArticleTitleText() {

        WebElement title_element = waitForArticleTitle();

        screenshot(this.takeScreenshot("article_title"));

        if (Platform.getInstance().isMobileWeb()) {
            return title_element.getText();
        }
        else{
            return title_element.getAttribute(SEARCH_INPUT_TEXT_ATTRIBUTE);
        }
    }

    @Step("Retrieve the Article Title text if it is presented on the screen")
    public String getTitleIfExist() {

        if (!Platform.getInstance().isMobileWeb()) clickHeaderToCloseHint();

        try {
            return getArticleTitleText();
        } catch (NoSuchElementException e) {
            throw new AssertionError("The Title cannot be found using '" + ARTICLE_TITLE + "'");
        }
    }

    @Step("Retrieve the Article Description text")
    public String getArticleDescriptionText() {
        WebElement subtitle_element = waitForArticleDescription();
        return subtitle_element.getAttribute("name");  // OR "contentDescription", but NOT "content-desc"
    }

    @Step("Scroll the screen down to the Footer")
    public void scrollToFooter() {

        if (Platform.getInstance().isAndroid()) {
            this.scrollDownToFindElement(
                    FOOTER,
                    "The Footer cannot be found using scroll and locator '" + FOOTER + "'",
                    40
            );
        } else if (Platform.getInstance().isIOS()) {
            this.scrollUpUntilTheElementAppears(
                    FOOTER,
                    "The Footer cannot be found using scroll and locator '" + FOOTER + "'",
                    40);
        } else {
            this.scrollWebPageUntilTheElementIsVisible(
                    FOOTER,
                    "The Footer cannot be found using scroll and locator '" + FOOTER + "'",
                    40
            );
        }
    }

    @Step("Initialize the Article Saving")
    public void initSavingArticle() {

        if (Platform.getInstance().isMobileWeb()) this.removeArticleFromWatchlistIfItWasAdded();

        this.waitForElementAndClick(
                SAVE_BUTTON,
                "The Save Button cannot be found using '" + SAVE_BUTTON + "'",
                5
        );

        if (!Platform.getInstance().isMobileWeb()) {
            this.waitForElementAndClick(
                    SNACKBAR_BUTTON,
                    "The Snackbar Button cannot be found using '" + SNACKBAR_BUTTON + "'",
                    3
            );
        }
    }

    @Step("[iOS] Tap the 'Create New List' button")
    public void clickCreateNewListIOS() {
        this.waitForElementAndClick(
                CREATE_LIST_BUTTON_IOS,
                "The Create_New_List Button cannot be found using '" + CREATE_LIST_BUTTON_IOS + "'",
                5
        );
    }

    @Step("Save the Article to a new list with the name '{list_name}'")
    public void saveArticleToNewList(String list_name) {
        this.waitForElementAndSendKeys(
                LIST_NAME_INPUT,
                list_name,
                "The Text Input Line cannot be found using '" + LIST_NAME_INPUT + "'",
                5
        );

        this.waitForElementAndClick(
                OK_BUTTON,
                "Couldn't tap the OK button ('" + OK_BUTTON + "')",
                5
        );
    }

    @Step("Save the Article to an existing list with the name '{list_name}'")
    public void saveArticleToExistingList(String list_name) {

        String list_to_save_locator = getListToSaveArticleLocatorByName(list_name);

        this.waitForElementAndClick(
                list_to_save_locator,
                "The List To Save the Article cannot be found using '" + list_to_save_locator + "'",
                5
        );
    }

    @Step("[Android] Tap the Header to close the hint")
    public void clickHeaderToCloseHint() {
        this.waitForElementAndClick(
                HEADER,
                "The Header cannot be found using '" + HEADER + "'",
                5
        );
    }

    @Step("[Mobile Web] Check if there is a Button to remove the Article from saved")
    public boolean isUnwatchButtonPresent() {
        return isElementPresent(UNWATCH_ARTICLE_BUTTON);

    }

    @Step("[Mobile Web] Remove the Article from saved if it is added")
    public void removeArticleFromWatchlistIfItWasAdded() {

        if (this.isUnwatchButtonPresent()) {
            this.waitForElementAndClick(
                    UNWATCH_ARTICLE_BUTTON,
                    "The Unwatch Button cannot be found and click using '" + UNWATCH_ARTICLE_BUTTON + "'",
                    5
            );
            this.waitForElementPresent(
                    SAVE_BUTTON,
                    "The Save Button cannot be found using '" + SAVE_BUTTON + "' after removing it from this list before",
                    5
            );
        }
    }
}
