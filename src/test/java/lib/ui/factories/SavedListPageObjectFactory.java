package lib.ui.factories;

import lib.Platform;
import lib.ui.SavedListsPageObject;
import lib.ui.android.AndroidSavedListPageObject;
import lib.ui.ios.iOSSavedListPageObject;
import lib.ui.mobile_web.MobileWebSavedListPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SavedListPageObjectFactory {

    public static SavedListsPageObject get(RemoteWebDriver driver) {

        if (Platform.getInstance().isAndroid()) {
            return new AndroidSavedListPageObject(driver);
        } else if (Platform.getInstance().isIOS()) {
            return new iOSSavedListPageObject(driver);
        } else {
            return new MobileWebSavedListPageObject(driver);
        }
    }
}
