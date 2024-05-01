package lib.ui.android;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidSearchPageObject extends SearchPageObject {

    public AndroidSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        SKIP_WELCOME = "id:org.wikipedia:id/fragment_onboarding_skip_button";
        SEARCH_INIT_ELEMENT = "id:org.wikipedia:id/search_container";
        SEARCH_INPUT = "id:org.wikipedia:id/search_src_text";
        SEARCH_CANCEL = "id:org.wikipedia:id/search_close_btn";
        LANG_BUTTON = "id:org.wikipedia:id/search_lang_button";
        SEARCH_RESULTS_LIST = "id:org.wikipedia:id/search_results_list";
        SEARCH_RESULT_WITH_DESCRIPTION_TEMPLATE = "xpath:" +
                "//*[@resource-id='org.wikipedia:id/page_list_item_description']" +
                "[contains(@text, '{DESCRIPTION}')]";
        SEARCH_RESULT_WITH_TITLE_AND_DESCRIPTION_TEMPLATE = "xpath:" +
                "//android.view.ViewGroup[android.widget.TextView[contains(@text, '{TITLE}')] and " +
                "android.widget.TextView[contains(@text, '{DESCRIPTION}')]]";
        SEARCH_RESULT_TITLE_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title']";
        LABEL_OF_EMPTY_SEARCH_RESULTS = "xpath://*[@resource-id='org.wikipedia:id/results_text'][@text='No results']";
        ARTICLE_TOOLBAR_SEARCH_BUTTON = "id:org.wikipedia:id/page_toolbar_button_search";
        SEARCH_RESULT_ARTICLE_TITLE_ATTRIBUTE = "text";
        SEARCH_INPUT_TEXT_ATTRIBUTE = "text";
    }
}
