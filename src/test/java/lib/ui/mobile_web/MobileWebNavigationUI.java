package lib.ui.mobile_web;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MobileWebNavigationUI extends NavigationUI {
    public MobileWebNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        MENU_BUTTON = "css:label[role='button'][for='main-menu-input']";
        WATCHLIST = "css:li.toggle-list-item:has(a>span.minerva-icon--watchlist)";
    }
}
