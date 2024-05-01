package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Epic(value = "Articles")
public class ArticleTests extends CoreTestCase {

    private static final String
            DEFAULT_QUERY = "Java",
            DEFAULT_ARTICLE_TITLE = "Java (programming language)",
            DEFAULT_ARTICLE_DESCRIPTION = "Object-oriented programming language";


    @Test
    @Features(value = {@Feature(value = "Search Results"), @Feature(value = "Article screen")})
    @DisplayName("Test an Article Title")
    @Description("Open the Default Article and verify that the Article Title meets expectations")
    @Step("Start the 'testArticleTitle' test.")
    @Severity(SeverityLevel.BLOCKER)
    public void testArticleTitle() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.enterQueryIntoSearchField(DEFAULT_QUERY);
        searchPageObject.openArticleWithDescription(DEFAULT_ARTICLE_DESCRIPTION);
        String title = articlePageObject.getTitleIfExist();

        assertEquals(
                "The Title is not equal to '" + DEFAULT_ARTICLE_TITLE + "'",
                DEFAULT_ARTICLE_TITLE,
                title
        );
    }

    @Test
    @Features(value = {@Feature(value = "Search Results"), @Feature(value = "Article screen")})
    @DisplayName("Test an Article Description")
    @Description("Open the Default Article and verify that the Article Description meets expectations")
    @Step("Start the 'testCompareArticleDescription' test.")
    @Severity(SeverityLevel.CRITICAL)
    public void testCompareArticleDescription() {

        if (Platform.getInstance().isMobileWeb()) {
            System.out.println("testCompareArticleDescription was skipped for the platform '" +
                    Platform.getInstance().getPlatformVar() + "'");
            return;
        }

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.enterQueryIntoSearchField(DEFAULT_QUERY);
        searchPageObject.openArticleWithDescription(DEFAULT_ARTICLE_DESCRIPTION);
        articlePageObject.clickHeaderToCloseHint();

        String subtitle_text = articlePageObject.getArticleDescriptionText();
        Assert.assertEquals(
                "The Text of the Subtitle is not equal to '" + DEFAULT_ARTICLE_DESCRIPTION + "'",
                DEFAULT_ARTICLE_DESCRIPTION,
                subtitle_text
        );
    }

    @Test
    @Features(value = {@Feature(value = "Search Results"), @Feature(value = "Article screen")})
    @DisplayName("Scroll an Article down (not available on Mobile Web)")
    @Description("Open an Article and scroll down to a Footer")
    @Step("Start the 'testScrollArticle' test.")
    @Severity(SeverityLevel.NORMAL)
    public void testScrollArticle() {
        String
                query = "System of a Down",
                article_subtitle = "American metal band";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.enterQueryIntoSearchField(query);
        searchPageObject.openArticleWithDescription(article_subtitle);
        articlePageObject.waitForArticleTitle();
        articlePageObject.scrollToFooter();
    }
}
