package lib.ui.mobile_web;

import lib.ui.SavedListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MobileWebSavedListPageObject extends SavedListsPageObject {
    public MobileWebSavedListPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        WATCHLIST = "xpath://a[@data-event-name='menu.watchlist']/..";
        SAVED_ARTICLE_WITH_TITLE_TEMPLATE = "css:li.with-watchstar[title='{ARTICLE_TITLE}']";
        UNWATCH_ARTICLE_BUTTON_TEMPLATE = "css:li.with-watchstar[title='{ARTICLE_TITLE}']>a.watched";
    }
}
