package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MobileWebSearchPageObject extends SearchPageObject {

    public MobileWebSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL = "css:div.header-action>button.cancel";
        SEARCH_RESULTS_LIST = "css:ul.mw-mf-page-list";
        SEARCH_RESULT_WITH_DESCRIPTION_TEMPLATE = "xpath:" +
                "//div[@class='wikidata-description'][contains(text(), '{DESCRIPTION}')]";
        SEARCH_RESULT_WITH_TITLE_AND_DESCRIPTION_TEMPLATE = "xpath:" +
                "//div[@class='wikidata-description'][contains(text(), '{DESCRIPTION}')]/.. | " +
                "li[@class='page-summary'][contains(@title, '{TITLE}')]";
        SEARCH_RESULT_TITLE_ELEMENT = "css:li.page-summary";
        LABEL_OF_EMPTY_SEARCH_RESULTS = "xpath://div[@class='search-results-view'][@style='display: none;']";
        ARTICLE_TOOLBAR_SEARCH_BUTTON = "css:button#searchIcon";
        SEARCH_RESULT_ARTICLE_TITLE_ATTRIBUTE = "title";
        SEARCH_INPUT_TEXT_ATTRIBUTE = "placeholder";
    }
}
