package com.pfm.automation.pages;

import com.pfm.automation.utils.WaitUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * OnboardingPage: Page Object for the tutorial / onboarding screens.
 * Flutter element keys are used as Accessibility IDs — the most reliable
 * locator strategy for Flutter apps running with flutter_driver or
 * the standard Appium UIAutomator2 driver.
 */
public class OnboardingPage {

    private final AndroidDriver driver;

    // Locators
    private static final By SKIP_BTN        = By.id("btn_skip");
    private static final By NEXT_BTN        = By.id("btn_tutorial_next_screen");

    // XPath-based locator used to count onboarding screens (page indicators / dots)
    // Adjust the XPath to match the actual widget tree if needed.
    private static final By PAGE_INDICATOR  = By.xpath("//*[@content-desc='page_indicator_dot']");

    // Description text on each onboarding screen
    private static final By SCREEN_DESCRIPTION = By.xpath("//*[@content-desc='onboarding_description']");

    public OnboardingPage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /** Tap the Skip button to jump directly to the Login screen. */
    public LoginPage tapSkip() {
        WaitUtils.waitForClickability(driver, SKIP_BTN).click();
        return new LoginPage(driver);
    }

    /** Tap the Next button to advance to the next onboarding screen. */
    public OnboardingPage tapNext() {
        WaitUtils.waitForClickability(driver, NEXT_BTN).click();
        return this;
    }

    /** Navigate through ALL onboarding screens until Next is no longer available. */
    public LoginPage navigateThroughAllScreens() {
        while (WaitUtils.isDisplayed(driver, NEXT_BTN)) {
            tapNext();
        }
        // After the last screen the app transitions to Login automatically
        return new LoginPage(driver);
    }

    /** Returns the number of page-indicator dots visible (= number of onboarding screens). */
    public int getNumberOfScreens() {
        List<WebElement> dots = driver.findElements(PAGE_INDICATOR);
        return dots.size();
    }

    /** Returns the description text shown on the current onboarding screen. */
    public String getCurrentScreenDescription() {
        return WaitUtils.waitForVisibility(driver, SCREEN_DESCRIPTION).getText();
    }

    /** Returns true when the Skip button is visible (i.e., we are on the onboarding flow). */
    public boolean isOnboardingDisplayed() {
        return WaitUtils.isDisplayed(driver, SKIP_BTN);
    }
}
