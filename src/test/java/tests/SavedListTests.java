package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SavedListPageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

@Epic(value = "User Saved Lists")
public class SavedListTests extends CoreTestCase {

    private static final String
            DEFAULT_QUERY = "Java",
            DEFAULT_ARTICLE_TITLE = "Java (programming language)",
            DEFAULT_ARTICLE_DESCRIPTION = "Object-oriented programming language",
            DEFAULT_LIST_NAME = "Java Appium Automation";


    @Test
    @Features(value = {@Feature(value = "Search Results"), @Feature(value = "Article screen"),
            @Feature(value = "Saved List")})
    @Story("Search, open, save to a new list and remove an article")
    @DisplayName("Test saving and removing an article")
    @Description("Skip the Welcome Screen, search for and open an article, save it to a new list," +
            "then return to the Main Screen, open the list and remove the article")
    @Step("Start 'testAddArticleToSaved' test")
    @Flaky
    @Severity(SeverityLevel.CRITICAL)
    public void testAddArticleToSaved() throws InterruptedException {
        String list_name = "Languages";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        SavedListsPageObject savedListsPageObject = SavedListPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.enterQueryIntoSearchField(DEFAULT_QUERY);
        searchPageObject.openArticleWithDescription(DEFAULT_ARTICLE_DESCRIPTION);

        /* Save the article */
        articlePageObject.initSavingArticle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.saveArticleToNewList(list_name);
        } else if (Platform.getInstance().isIOS()) {
            articlePageObject.clickCreateNewListIOS();
            articlePageObject.saveArticleToNewList(list_name);
        } else {
            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.clickAuthButton();
            try {
                auth.typeLoginData();
            } catch (IOException e) {
                throw new RuntimeException("Login and Password cannot be typed");
            }
            auth.submitForm();

            articlePageObject.waitForArticleTitle();
            assertEquals(
                    "After logging in, we are not on the same page",
                    DEFAULT_ARTICLE_TITLE,
                    articlePageObject.getArticleTitleText()
            );
        }

        /* Open the list */
        if (Platform.getInstance().isAndroid()) {
            navigationUI.clickBackArrow();
            navigationUI.clickBackArrow();
            navigationUI.openSavedListsView();
            savedListsPageObject.openSavedListByName(list_name);
        }
        else if (Platform.getInstance().isIOS()) {
            navigationUI.clickBackArrow();
            searchPageObject.tapCancelSearchButton();
            navigationUI.openSavedListsView();
            navigationUI.closeLogInDialog();
            navigationUI.openReadingListsTab();
            savedListsPageObject.openSavedListByName(list_name);
        }
        else {
            navigationUI.openMenu();
            navigationUI.openWatchlistFromMenu();
        }

        /* Remove the article from the list */
        if (Platform.getInstance().isAndroid()) {
            savedListsPageObject.swipeArticleToRemove(DEFAULT_ARTICLE_TITLE);

            boolean is_article_not_on_list = savedListsPageObject.isArticleNotOnListByTitle(DEFAULT_ARTICLE_TITLE);
            assertTrue(
                    "The Article '" + DEFAULT_ARTICLE_TITLE + "' is still present on the list '" + list_name + "'",
                    is_article_not_on_list
            );
        } else if (Platform.getInstance().isIOS()) {
            /* TODO: in iOS swipe doesn't work */
            System.out.println("The removing of the article was skipped due to a non-working swipe");
        } else {
            savedListsPageObject.removeArticleFromWatchlist(DEFAULT_ARTICLE_TITLE);
            boolean is_article_on_watchlist = savedListsPageObject.isArticleOnWatchlist(DEFAULT_ARTICLE_TITLE);
            assertFalse(
                    "The Article '" + DEFAULT_ARTICLE_TITLE + "' is still present on the watchlist",
                    is_article_on_watchlist
            );
        }
    }

    @Test
    @Features(value = {@Feature(value = "Search Results"), @Feature(value = "Article screen"),
            @Feature(value = "Saved List")})
    @Story("Search, open, save two articles to a new list, remove the first one, and verify the second one")
    @DisplayName("Test saving two articles")
    @Description("Skip the Welcome Screen, search for and open an article. Save it to a new list. Then, " +
            "search for and open another article, and save it to the same list. " +
            "Return to the Main Screen, open the list, remove the first article and verify the second one.")
    @Step("Start 'testAddArticleToSaved' test")
    @Flaky
    @Severity(SeverityLevel.CRITICAL)
    public void testSaveTwoArticles() throws InterruptedException {
        String
                second_query = "Appium",
                second_article_title = "Appium",
                second_article_description = "Automation for Apps";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        SavedListsPageObject savedListsPageObject = SavedListPageObjectFactory.get(driver);

        /* Save the first article to a new list */
        searchPageObject.initSearchInput();
        searchPageObject.enterQueryIntoSearchField(DEFAULT_QUERY);
        searchPageObject.openArticleWithDescription(DEFAULT_ARTICLE_DESCRIPTION);
        articlePageObject.initSavingArticle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.saveArticleToNewList(DEFAULT_LIST_NAME);
        } else if (Platform.getInstance().isIOS()) {
            articlePageObject.clickCreateNewListIOS();
            articlePageObject.saveArticleToNewList(DEFAULT_LIST_NAME);
        } else {
            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.clickAuthButton();
            try {
                auth.typeLoginData();
            } catch (IOException e) {
                throw new RuntimeException("Login and Password cannot be typed");
            }
            auth.submitForm();

            articlePageObject.waitForArticleTitle();
            assertEquals(
                    "After logging in, we are not on the same page",
                    DEFAULT_ARTICLE_TITLE,
                    articlePageObject.getArticleTitleText()
            );
        }

        /* Save the second article to the same list */
        searchPageObject.enterQueryIntoArticleToolbarSearchField(second_query);
        searchPageObject.openArticleWithDescription(second_article_description);
        articlePageObject.initSavingArticle();

        /* Open the list */
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.saveArticleToExistingList(DEFAULT_LIST_NAME);
            for (int i = 0; i < 3; i++) navigationUI.clickBackArrow();
            navigationUI.openSavedListsView();
            savedListsPageObject.openSavedListByName(DEFAULT_LIST_NAME);
        } else if (Platform.getInstance().isIOS()) {
            articlePageObject.saveArticleToExistingList(DEFAULT_LIST_NAME);
            navigationUI.clickBackArrow();
            searchPageObject.tapCancelSearchButton();
            navigationUI.clickBackArrow();
            navigationUI.openSavedListsView();
            navigationUI.closeLogInDialog();
            navigationUI.openReadingListsTab();
            savedListsPageObject.openSavedListByName(DEFAULT_LIST_NAME);
        } else {
            navigationUI.openMenu();
            Thread.sleep(1000);
            navigationUI.openWatchlistFromMenu();
        }

        /* Remove the first article */
        if (Platform.getInstance().isAndroid()) {
            savedListsPageObject.swipeArticleToRemove(DEFAULT_ARTICLE_TITLE);
            savedListsPageObject.waitForSnackbarToDisappear();

            boolean is_article_not_on_list = savedListsPageObject.isArticleNotOnListByTitle(DEFAULT_ARTICLE_TITLE);
            assertTrue(
                    "The Article '" + DEFAULT_ARTICLE_TITLE + "' is still present in the list '" + DEFAULT_LIST_NAME + "'",
                    is_article_not_on_list
            );
        } else if (Platform.getInstance().isIOS()) {
            /* TODO: in iOS swipe doesn't work */
            System.out.println("The removing of the article was skipped due to a non-working swipe");
        } else {
            savedListsPageObject.removeArticleFromWatchlist(DEFAULT_ARTICLE_TITLE);

            boolean is_article_on_watchlist = savedListsPageObject.isArticleOnWatchlist(DEFAULT_ARTICLE_TITLE);
            assertFalse(
                    "The Article '" + DEFAULT_ARTICLE_TITLE + "' is still present on the watchlist",
                    is_article_on_watchlist
            );
        }

        /* Verify that the second article is still present on the list */
        if (Platform.getInstance().isMobileWeb()) {
            boolean is_article_on_watchlist = savedListsPageObject.isArticleOnWatchlist(second_article_title);
            System.out.println(is_article_on_watchlist);
            assertTrue(
                    "The Article '" + second_article_title + "' not on the watchlist",
                    is_article_on_watchlist
            );
        } else {
            boolean is_second_article_still_on_list = savedListsPageObject.isArticleOnListByDescription(second_article_description);
            assertTrue(
                    "The Article '" + second_article_description + "' not on the list '" + DEFAULT_LIST_NAME + "'",
                    is_second_article_still_on_list);
        }

        /* Open the second article, then verify that it's the same as the one before */
        if (Platform.getInstance().isMobileWeb()) {
            savedListsPageObject.openArticleFromWatchlistByTitle(second_article_title);

            String title = articlePageObject.getArticleTitleText();
            assertEquals(
                    "The Article Title doesn't match to '" + second_article_title + "'",
                    second_article_title,
                    title
            );
        } else {
            searchPageObject.openArticleWithDescription(second_article_description);

            String description = articlePageObject.getArticleDescriptionText();
            assertEquals(
                    "The Article Description doesn't match to '" + second_article_description + "'",
                    second_article_description,
                    description
            );
        }
    }
}
