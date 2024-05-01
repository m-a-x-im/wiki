package lib.ui.android;

import lib.ui.SavedListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidSavedListPageObject extends SavedListsPageObject {

    public AndroidSavedListPageObject(RemoteWebDriver driver) {
        super(driver);
    }

     static {
         SAVED_LIST_TEMPLATE = "xpath://*[@resource-id='org.wikipedia:id/item_title'][@text='{LIST_NAME}']";
         SAVED_ARTICLE_WITH_TITLE_TEMPLATE = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']/" +
                         "android.widget.TextView[@text='{ARTICLE_TITLE}']";
         SAVED_ARTICLE_WITH_DESCRIPTION_TEMPLATE = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']/" +
                 "android.widget.TextView[contains(@text, '{ARTICLE_DESCRIPTION}')]";
         SNACKBAR_ACTION = "id:org.wikipedia:id/snackbar_action";
     }
}
