package lib.ui.factories;

import lib.Platform;
import lib.ui.WelcomePageObject;
import lib.ui.android.AndroidWelcomePageObject;
import lib.ui.ios.iOSWelcomePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObjectFactory {

    public static WelcomePageObject get(RemoteWebDriver driver) {

        if (Platform.getInstance().isIOS()) {
            return new iOSWelcomePageObject(driver);
        } else {
            return new AndroidWelcomePageObject(driver);
        }
    }
}
