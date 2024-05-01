package lib.ui.ios;

import lib.ui.SavedListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSSavedListPageObject extends SavedListsPageObject {

    public iOSSavedListPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        SAVED_LIST_TEMPLATE = "xpath://XCUIElementTypeCell//" +
                "descendant::XCUIElementTypeStaticText[@name='{LIST_NAME}']";
        SAVED_ARTICLE_WITH_TITLE_TEMPLATE = "xpath://XCUIElementTypeCell//" +
                "descendant::XCUIElementTypeStaticText[@name='{ARTICLE_TITLE}']";
        SAVED_ARTICLE_WITH_DESCRIPTION_TEMPLATE = "xpath://XCUIElementTypeCell//" +
                "descendant::XCUIElementTypeStaticText[contains(@value, '{ARTICLE_DESCRIPTION}')]";
        SWIPE_ACTION_DELETE_BUTTON = "xpath://XCUIElementTypeButton[@name='swipe action delete']";
    }
}
