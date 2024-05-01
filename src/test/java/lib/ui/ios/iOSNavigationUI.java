package lib.ui.ios;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSNavigationUI extends NavigationUI {
    public iOSNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        BACK_ARROW = "id:Back";
        SAVED_TAB = "xpath://XCUIElementTypeButton[@name='Saved']";
        CANCEL_BUTTON = "xpath://XCUIElementTypeStaticText[@name='Cancel']";
        READING_LISTS_TAB = "xpath://XCUIElementTypeStaticText[@name='Reading lists']";
        CLOSE_LOG_IN_DIALOG_BUTTON = "id:Close";
    }
}
