package lib.ui.ios;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSSearchPageObject extends SearchPageObject {

    public iOSSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        SKIP_WELCOME = "xpath://XCUIElementTypeButton[@name='Skip']";
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@label='Search Wikipedia']";
        SEARCH_CANCEL = "xpath://XCUIElementTypeStaticText[@name='Cancel']";
        LANG_BUTTON = "xpath://XCUIElementTypeButton[@name='Change language']";
        SEARCH_RESULTS_LIST = "xpath://XCUIElementTypeCollectionView[@visible='true']";
        SEARCH_RESULT_WITH_DESCRIPTION_TEMPLATE = "xpath://XCUIElementTypeStaticText[contains(@name, '{DESCRIPTION}')]";
        SEARCH_RESULT_WITH_TITLE_AND_DESCRIPTION_TEMPLATE = "xpath:" +
                "//XCUIElementTypeOther[XCUIElementTypeStaticText[contains(@name, '{TITLE}')] and " +
                "XCUIElementTypeStaticText[contains(@name, '{DESCRIPTION}')]]";
        SEARCH_RESULT_TITLE_ELEMENT = "xpath:(//XCUIElementTypeCollectionView)[1]/XCUIElementTypeCell/" +
                "XCUIElementTypeOther[2]/XCUIElementTypeStaticText[1]";
        LABEL_OF_EMPTY_SEARCH_RESULTS = "xpath://XCUIElementTypeStaticText[@name='No results found']";
        ARTICLE_TOOLBAR_SEARCH_BUTTON = "xpath://XCUIElementTypeButton[@name='Search Wikipedia']";
        SEARCH_RESULT_ARTICLE_TITLE_ATTRIBUTE = "value";
        TAB_BAR = "id:Tab Bar";
        SEARCH_INPUT_TEXT_ATTRIBUTE = "value";
    }
}
