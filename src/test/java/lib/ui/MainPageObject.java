package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lib.Platform;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;


public class MainPageObject {

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }


    @Step("Get a locator by string: '{locator_with_type}'")
    protected By getLocatorByString(String locator_with_type) {

        String[] exploited_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String type = exploited_locator[0];
        String locator = exploited_locator[1];

        switch (type) {
            case "xpath":
                return By.xpath(locator);
            case "id":
                return By.id(locator);
            case "css":
                return By.cssSelector(locator);
            default:
                throw new IllegalArgumentException("Unable to get the type of the locator: " + locator);
        }
    }

    @Step("Retrieve an element using the locator '{locator_with_type}' " +
            "with an explicit wait of {'timeOutInSeconds'} seconds")
    public WebElement waitForElementPresent(String locator_with_type, String error_message, long timeOutInSeconds) {
        By locator = getLocatorByString(locator_with_type);

        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage("\n" + error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    @Step("Verify that an element found using the locator '{locator_with_type}' is not present on the screen " +
            "with an explicit wait of {'timeOutInSeconds'} seconds")
    public void waitForElementNotPresent(String locator_with_type, String error_message, long timeoutInSeconds) {
        By locator = getLocatorByString(locator_with_type);

        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    @Step("Wait for an element found using the locator '{locator_with_type}' with an explicit wait of {'timeOutInSeconds'} seconds, " +
            "then tap it")
    public void waitForElementAndClick(String locator_with_type, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(locator_with_type, error_message, timeOutInSeconds);
        element.click();
    }

    @Step("Retrieve an element using the locator '{locator_with_type}' with an explicit wait of {'timeOutInSeconds'} seconds, " +
            "then send the value '{value}'")
    public WebElement waitForElementAndSendKeys(
            String locator_with_type, String value, String error_message, long timeOutInSeconds) {

        WebElement element = waitForElementPresent(locator_with_type, error_message, timeOutInSeconds);
        element.sendKeys(value);
        return element;
    }

    @Step("Wait for an element found using the locator '{locator_with_type}' with an explicit wait of {'timeOutInSeconds'} seconds, " +
            "then retrieve the value of the attribute '{attribute}'")
    public String waitForElementAndGetAttribute(
            String locator_with_type, String attribute, String error_message, long timeOutInSeconds) {

        WebElement element = waitForElementPresent(locator_with_type, error_message, timeOutInSeconds);
        return element.getAttribute(attribute);
    }

    @Step("Get the number of elements found using the locator 'locator_with_type'")
    public int getNumberOfElements(String locator_with_type) {
        By locator = getLocatorByString(locator_with_type);

        List elements = driver.findElements(locator);
        return elements.size();
    }

    @Step("[Mobile Web] Check if the element found using the locator '{locator}' is present on the screen")
    public boolean isElementPresent(String locator) {
        return getNumberOfElements(locator) > 0;
    }

    @Step("Scroll the screen down for {timeOfScroll} milliseconds")
    public void scrollDown(int timeOfScroll) {

        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;

            Dimension size = driver.manage().window().getSize();
            int center_x = size.width / 2;
            int start_y = (int) (size.height * 0.8);
            int end_y = (int) (size.height * 0.2);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

            Sequence actions = new Sequence(finger, 1);
            actions
                    .addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), center_x, start_y))

                    // Тап по экрану
                    .addAction(finger.createPointerDown(0))

                    // Движение к конечной точке
                    .addAction(finger.createPointerMove(
                            Duration.ofMillis(timeOfScroll), PointerInput.Origin.viewport(), center_x, end_y))

                    // Поднять палец
                    .addAction(finger.createPointerUp(0));

            // Выполнить действия
            driver.perform(Collections.singletonList(actions));
        } else {
            System.out.println("Method scrollUp() does nothing for the platform " +
                    Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Swipe an element found using the locator '{locator_with_type}' to the left")
    public void swipeElementToLeft(String locator_with_type, String error_message) {

        if (driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent(locator_with_type, error_message, 10);

            Point location = element.getLocation();
            Dimension size = element.getSize();

            int left_x = location.getX();
            int right_x = left_x + size.getWidth();
            int upper_y = location.getY();
            int middle_y = upper_y + (size.getHeight() / 2);

            int start_x = right_x - 20;
            int end_x = left_x + 20;

            this.swipe(
                    new Point(start_x, middle_y),
                    new Point(end_x, middle_y),
                    Duration.ofMillis(500)
            );
        } else {
            System.out.println("Method swipeElementToLeft() does nothing for the platform " +
                    Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Perform a swipe from point '{start}' to point '{end}' for {duration} milliseconds")
    protected void swipe(Point start, Point end, Duration duration) {

        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);

            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(List.of(swipe));
        } else {
            System.out.println("Method swipe() does nothing for the platform " +
                    Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Scroll the screen down quickly")
    public void scrollDownQuickly() {
        scrollDown(150);
    }

    @Step("[Android] Scroll down until the element is found using the locator '{locator_with_type}', " +
            "or until the number of swipes is {max_swipes}")
    public void scrollDownToFindElement(String locator_with_type, String error_message, int max_swipes) {

        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;

            By locator = getLocatorByString(locator_with_type);
            int swipes = 0;

            while (driver.findElements(locator).isEmpty()) {
                if (swipes >= max_swipes) {
                    waitForElementPresent(locator_with_type, error_message, 0);
                }
                scrollDownQuickly();
                ++swipes;
            }
        } else {
            System.out.println("Method scrollUpToFindElement() does nothing for the platform " +
                    Platform.getInstance().getPlatformVar());
        }
    }

    @Step("[iOS] Scroll down until the element is found using the locator '{locator_with_type}', " +
            "or until the number of swipes is {max_swipes}")
    public void scrollUpUntilTheElementAppears(String locator_with_type, String error_message, int max_swipes) {

        if (driver instanceof AppiumDriver) {
            int swipes = 0;
            while (!this.isElementLocatedOnTheScreen(locator_with_type)) {
                if (swipes >= max_swipes) {
                    Assert.assertTrue(error_message, this.isElementLocatedOnTheScreen(locator_with_type));
                }
                scrollDown(3000);
                ++swipes;
            }
        } else {
            System.out.println("Method scrollUpUntilTheElementAppears() does nothing for the platform " +
                    Platform.getInstance().getPlatformVar());
        }
    }

    @Step("[Mobile Web] Scroll the webpage down")
    public void scrollWebPageUp() {

        if (Platform.getInstance().isMobileWeb()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            JSExecutor.executeScript("window.scrollBy(0, 250)"); // Скролл с нулевой позиции до 250px
        } else {
            System.out.println("Method scrollWebPageUp() does nothing for the platform " +
                    Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Scroll the webpage down until the element is found using the locator '{locator_with_type}', " +
            "or until the number of swipes is {max_swipes}")
    public void scrollWebPageUntilTheElementIsVisible(String locator_with_type, String error_message, int max_swipes) {

        int swipes = 0;
        WebElement element = this.waitForElementPresent(locator_with_type, error_message, 5);

        while (!this.isElementLocatedOnTheScreen(locator_with_type)) {
            scrollWebPageUp();
            ++swipes;
            if (swipes >= max_swipes) {
                Assert.assertTrue(error_message, element.isDisplayed());
            }
        }
    }

    @Step("Check if the element found using the locator '{locator_with_type}' is present on the screen")
    public boolean isElementLocatedOnTheScreen(String locator_with_type) {

        // Расположение элемента по высоте
        int element_location_by_y = this.waitForElementPresent(
                locator_with_type,
                "The Element cannot be found using '" + locator_with_type + "'", 5
        ).getLocation().getY();

        /* getLocation() в вебе – это позиция относительно верха страницы, а не текущего положения пользователя,
        * поэтому используем скрипт и вычитаем из верха текущее положение */
        if (Platform.getInstance().isMobileWeb()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            Object js_result = JSExecutor.executeScript("return window.scrollY");
            element_location_by_y -= Integer.parseInt(js_result.toString());
        }
        // Высота экрана
        int screen_size_by_y = driver.manage().window().getSize().getHeight();

        return element_location_by_y < screen_size_by_y;
    }

    @Step("Take and save a screenshot.")
    public String takeScreenshot(String name) {
        TakesScreenshot takesScreenshot = this.driver;
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/" + name + "_screenshot.png";

        try {
            FileUtils.copyFile(source, new File(path));
            System.out.println("The screenshot was taken from " + path);
        } catch (IOException e) {
            System.out.println("The screenshot cannot be taken. Error: " + e.getMessage());
        }
        return path;
    }

    @Step("Get an array of bytes from the screenshot located by path '{path}' to attach it to the report")
    @Attachment
    public static byte[] screenshot(String path) {
        byte[] bytes = new byte[0];

        try {
            bytes = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            System.out.println("The bytes from the screenshot cannot be retrieved. Error: " + e.getMessage());
        }

        return bytes;
    }
}
