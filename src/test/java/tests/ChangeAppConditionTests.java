package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Epic("Application State")
public class ChangeAppConditionTests extends CoreTestCase {

    private static final String
            DEFAULT_QUERY = "Java",
            DEFAULT_ARTICLE_DESCRIPTION = "Object-oriented programming language";


    @Test
    @Features(value = {@Feature(value = "Search Results"), @Feature(value = "Article screen"),
            @Feature(value = "Screen Rotation")})
    @DisplayName("Test an Article Title after Screen Rotation.")
    @Description("Open an Article with a specified description, " +
            "change the screen rotation twice, and verify that the Article Title hasn't changed.")
    @Severity(SeverityLevel.NORMAL)
    @Step("Start the 'testArticleTitleAfterRotation' test.")
    public void testArticleTitleAfterRotation() {

        if (Platform.getInstance().isMobileWeb()) return;

        String
                query = "System of a Down",
                article_description = "American metal band";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.enterQueryIntoSearchField(query);
        searchPageObject.openArticleWithDescription(article_description);

        String title_before_rotation = articlePageObject.getArticleTitleText();

        this.rotateScreenLandscape();
        String title_after_rotation = articlePageObject.getArticleTitleText();
        assertEquals(
                "The Subtitle of Article changed after the screen rotation was changed to landscape",
                title_before_rotation,
                title_after_rotation
        );

        this.rotateScreenPortrait();
        String title_after_second_rotation = articlePageObject.getArticleTitleText();
        assertEquals(
                "The Subtitle of Article changed after the screen rotation was changed to portrait",
                title_before_rotation,
                title_after_second_rotation
        );
    }

    @Test
    @Features(value = {@Feature(value = "Search Results"), @Feature(value = "App running in the background")})
    @DisplayName("Test the Search Results after sending the App to the background.")
    @Description("Find an Article in the Search Results, send the App to the background for 2 seconds, " +
            "then verify that the Search Results with the Article are still presented.")
    @Severity(SeverityLevel.NORMAL)
    @Step("Start the 'testArticleInBackground' test.")
    public void testArticleInBackground() {

        if (Platform.getInstance().isMobileWeb()) return;

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.enterQueryIntoSearchField(DEFAULT_QUERY);
        searchPageObject.waitForSearchResultWithDescription(DEFAULT_ARTICLE_DESCRIPTION);

        this.sendAppToBackground(2);

        searchPageObject.waitForSearchResultWithDescription(DEFAULT_ARTICLE_DESCRIPTION);
    }
}
