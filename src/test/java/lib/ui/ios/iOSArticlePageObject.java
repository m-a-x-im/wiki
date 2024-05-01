package lib.ui.ios;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSArticlePageObject extends ArticlePageObject {

    public iOSArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        ARTICLE_TITLE = "xpath://XCUIElementTypeOther[@value='1']/XCUIElementTypeStaticText";
        ARTICLE_DESCRIPTION = "xpath://XCUIElementTypeOther[@name='banner']/XCUIElementTypeOther[2]/XCUIElementTypeStaticText";
        HEADER = "xpath://XCUIElementTypeOther[@name='banner']";
        FOOTER = "xpath://XCUIElementTypeStaticText[@name='View article in browser']";
        SAVE_BUTTON = "xpath://XCUIElementTypeButton[@name='Save for later']";
        SNACKBAR_BUTTON = "xpath://XCUIElementTypeStaticText[contains(@name, 'to a reading list?')]";
        CREATE_LIST_BUTTON_IOS = "xpath://XCUIElementTypeButton[@name='Create a new list']";
        LIST_NAME_INPUT = "xpath://XCUIElementTypeTextField[@value='reading list title']";
        OK_BUTTON = "xpath://XCUIElementTypeButton[@name='Create reading list']";
        LIST_TO_SAVE_ARTICLE_TEMPLATE = "xpath://XCUIElementTypeStaticText[@name='{LIST_NAME}']";
        SEARCH_INPUT_TEXT_ATTRIBUTE = "value";
    }
}
