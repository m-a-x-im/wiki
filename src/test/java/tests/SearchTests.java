package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Epic(value = "Search")
public class SearchTests extends CoreTestCase {

    private static final String
            DEFAULT_QUERY = "Java",
            DEFAULT_ARTICLE_DESCRIPTION = "Object-oriented programming language";

    @Test
    @Feature(value = "Search Results")
    @Story(value = "Search and open an article")
    @DisplayName("Simple search test")
    @Description("Search and open an Article with the specified description.")
    @Step("Start the 'testSearchSimple' test.")
    @Severity(SeverityLevel.BLOCKER)
    public void testSearchSimple() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.enterQueryIntoSearchField(DEFAULT_QUERY);
        searchPageObject.openArticleWithDescription(DEFAULT_ARTICLE_DESCRIPTION);
    }

    @Test
    @Feature(value = "Search Field")
    @Story(value = "Perform a search, then reset the search query")
    @DisplayName("Search Reset test")
    @Description("Enter a query, clear the search field, and verify that the Reset Button has disappeared")
    @Step("Start the 'testResetSearch' test.")
    @Severity(SeverityLevel.NORMAL)
    public void testResetSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.enterQueryIntoSearchField(DEFAULT_QUERY);
        searchPageObject.waitForCancelSearchButtonToAppear();
        searchPageObject.tapCancelSearchButton();
        searchPageObject.waitForCancelSearchButtonToDisappear();
    }

    @Test
    @Features(value = {@Feature(value = "Search Field"), @Feature(value = "Navigation")})
    @Story(value = "Search. reset, return to main screen")
    @DisplayName("Test for clearing the Search Field")
    @Description("Enter a query and verify the text in the Search Field, " +
            "then clear the Search Field and verify the Search Field Placeholder. " +
            "After that, go back to the Main Screen and verify that the Reset Button has disappeared.")
    @Step("Start the 'testClearSearch' test.")
    @Severity(SeverityLevel.NORMAL)
    public void testClearSearch() {

        if (Platform.getInstance().isMobileWeb()) {
            System.out.println("The 'testClearSearch' test was skipped for the platform " + Platform.getInstance().getPlatformVar());
            return;
        }

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        NavigationUI navigationUI = NavigationUIFactory.get(driver);

        searchPageObject.initSearchInput();
        String search_bar_placeholder = searchPageObject.getSearchBarText();

        WebElement search_bar = searchPageObject.enterQueryIntoSearchField(DEFAULT_QUERY);
        String search_bar_text = searchPageObject.getSearchBarText();

        assertEquals(
                "The Search Bar Text is not equal '" + DEFAULT_QUERY + "'",
                DEFAULT_QUERY,
                search_bar_text);

        search_bar.clear();

        search_bar_text = searchPageObject.getSearchBarText();
        assertEquals(
                "The Search Bar Text is not empty",
                search_bar_placeholder,
                search_bar_text);

        if (Platform.getInstance().isAndroid()) {
            navigationUI.clickBackArrow();
            searchPageObject.waitToLangButtonToDisappear();
        }
        else {
            searchPageObject.tapCancelSearchButton();
            searchPageObject.waitForCancelSearchButtonToDisappear();
        }
    }

    @Test
    @Feature(value = "Search Field")
    @Story(value = "There is the Search Field Placeholder")
    @DisplayName("Simple test for Search Field Placeholder")
    @Description("Tap the Search Field, then verify that the Placeholder text meets expectations")
    @Step("Start the 'testSearchFieldPlaceholder' test.")
    @Severity(SeverityLevel.TRIVIAL)
    public void testSearchFieldPlaceholder() {

        String placeholder = "Search Wikipedia";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();

        String actual_text = searchPageObject.getSearchBarText();
        assertEquals(
                "The Search Bar Placeholder is not equal to '" + placeholder + "'",
                placeholder,
                actual_text
        );
    }

    @Test
    @Feature(value = "Search Results")
    @Story(value = "Cancel search")
    @DisplayName("Test for search and search cancellation")
    @Description("Enter a query and verify that the Number of Search Results is greater than one. " +
            "After that, cancel the search and verify that the Search Result List is empty.")
    @Step("Start the 'testSearchAndCancel' test.")
    @Severity(SeverityLevel.NORMAL)
    public void testSearchAndCancel() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.enterQueryIntoSearchField(DEFAULT_QUERY);
        searchPageObject.waitForSearchResultsList();

        int number_of_search_results = searchPageObject.getNumberOfArticlesFound();
        assertTrue(
                "The Number of Search Results for the query '" + DEFAULT_QUERY + "' isn't greater than 1",
                number_of_search_results > 1
        );

        searchPageObject.waitForCancelSearchButtonToAppear();
        searchPageObject.tapCancelSearchButton();
        searchPageObject.waitForCancelSearchButtonToDisappear();

        if (Platform.getInstance().isIOS()) {
            searchPageObject.waitForTabBarToAppear();
        } else {
            int size_of_search_results_list = searchPageObject.getSearchResultsList().size();
            assertEquals("The Search Results List is not empty", 0, size_of_search_results_list);
        }
    }

    @Test
    @Feature(value = "Search Results")
    @Story(value = "Article Titles in Search Results are correct")
    @DisplayName("Test Article Titles from Search Results")
    @Description("Enter a query and verify that the Search Result List isn't empty." +
            "After that, verify that every Article Title contains the query")
    @Step("Start the 'testTextInSearchResults' test.")
    @Severity(SeverityLevel.NORMAL)
    public void testTextInSearchResults() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.enterQueryIntoSearchField(DEFAULT_QUERY);
        searchPageObject.waitForSearchResultsList();

        int number_of_search_results = searchPageObject.getNumberOfArticlesFound();
        assertTrue(
                "The Number of Search Results for the query '" + DEFAULT_QUERY + "' isn't greater than 0",
                number_of_search_results > 0
        );

        List<WebElement> search_result_elements = searchPageObject.getSearchResultsList();

        for (WebElement element : search_result_elements) {
            System.out.println("Element: " + element.toString());
            String title = searchPageObject.getArticleTitleByAttribute(element).toLowerCase();
            System.out.println("Title: " + title);
            assertTrue(
                    "The Article titled '" + title + "' doesn't contain '" + DEFAULT_QUERY + "'",
                    title.contains(DEFAULT_QUERY.toLowerCase())
            );
        }
    }

    @Test
    @Feature(value = "Search Results")
    @Story(value = "Can obtain search results.")
    @DisplayName("Number of Search Results test")
    @Description("Enter a query and verify that the Number of Articles in Search Results is greater than zero.")
    @Step("Start the 'testNumberOfSearchResults' test.")
    @Severity(SeverityLevel.NORMAL)
    public void testNumberOfSearchResults() {
        String query = "System of a Down discography";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.enterQueryIntoSearchField(query);
        searchPageObject.waitForSearchResultsList();

        int number_of_search_results = searchPageObject.getNumberOfArticlesFound();
        assertTrue(
                "The Number of Search Results for the query '" + query + "' isn't greater than 0",
                number_of_search_results > 0
        );
    }

    @Test
    @Feature(value = "Search Results")
    @Story(value = "Correct display of empty results")
    @DisplayName("Empty Search Result List test")
    @Description("Enter an incorrect query, " +
            "then verify that the Empty Label is presented and the Search Result List is empty")
    @Step("Start the 'testEmptySearchResultsList' test.")
    @Severity(SeverityLevel.TRIVIAL)
    public void testEmptySearchResultsList() {

        String query = "zxcvbnmasdfghjk";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.enterQueryIntoSearchField(query);
        searchPageObject.waitForEmptyResultsLabel();

        int size_of_search_results_list = searchPageObject.getSearchResultsList().size();
        assertEquals("The Search Results List is not empty", 0, size_of_search_results_list);
    }

    @Test
    @Feature(value = "Search Results")
    @Story(value = "Article Titles and Descriptions in Search Results are correct")
    @DisplayName("Test for Three Search Results based on Articles Title and Description")
    @Description("Enter a query which returns at least three results, " +
            "then verify that both the Result Title and Result Description meet expectations")
    @Step("Start the 'testSearchByTitleAndDescription' test.")
    @Severity(SeverityLevel.MINOR)
    public void testSearchByTitleAndDescription() {

        String query = "Jav", title = "Java";
        String[] descriptions = {
                "Object-oriented programming language",
                "High-level programming language",
                "List of versions of the Java programming language"
        };

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.enterQueryIntoSearchField(query);

        String[] not_found = new String[3];
        int count = 0;

        for (String description : descriptions) {
            try {
                searchPageObject.waitForSearchResultWithTitleAndDescription(title, description);
            } catch (Exception e) {
                not_found[count] = description;
            }
            ++count;
        }

        String[] empty_array = new String[3];
        assertEquals(
                String.format("\nNot found:\n%s", Arrays.toString(not_found)),
                Arrays.toString(empty_array),
                Arrays.toString(not_found)
        );
    }
}
