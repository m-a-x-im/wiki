package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MobileWebArticlePageObject extends ArticlePageObject {
    public MobileWebArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    static {
        ARTICLE_TITLE = "css:#content h1";
        FOOTER = "css:footer.mw-footer";
        SAVE_BUTTON = "css:li:has(a[role='button']):has(span.minerva-icon--star-base20)";
        UNWATCH_ARTICLE_BUTTON = "css:li:has(a[href*='=unwatch'])";
    }
}
