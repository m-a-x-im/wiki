package lib.ui.android;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidNavigationUI extends NavigationUI {
    public AndroidNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        BACK_ARROW = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";
        SAVED_TAB = "id:org.wikipedia:id/nav_tab_reading_lists";
    }
}
