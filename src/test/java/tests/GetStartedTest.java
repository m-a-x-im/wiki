package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import lib.ui.factories.WelcomePageObjectFactory;
import org.junit.Test;

@Epic(value = "Onboarding")
public class GetStartedTest extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value = "Welcome Screen"), @Feature(value = "Navigation")})
    @Story(value = "Go through the welcome pages")
    @DisplayName("Test Welcome Screen")
    @Description("Go through every page of the Welcome Screen, then tap 'Get started' button")
    @Step("Start 'testPassThroughWelcome' test")
    @Severity(SeverityLevel.CRITICAL)
    public void testGoThroughWelcome() {

        if (Platform.getInstance().isMobileWeb()) return;

        WelcomePageObject welcomePageObject = WelcomePageObjectFactory.get(driver);

        welcomePageObject.waitForFirstPageLink();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForSecondPageTitle();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForThirdPageLink();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForFourthPageLink();
        welcomePageObject.clickGetStartedOrAcceptButton();
    }
}
