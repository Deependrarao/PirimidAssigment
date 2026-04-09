package com.pfm.automation.tests;

import com.pfm.automation.base.BaseTest;
import com.pfm.automation.pages.LoginPage;
import com.pfm.automation.pages.OnboardingPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * OnboardingTest: Covers TC-01 (Skip tutorial) and TC-02 (Navigate all screens).
 */
public class OnboardingTest extends BaseTest {

    // Adjust to the actual number of onboarding screens in the app
    private static final int EXPECTED_SCREEN_COUNT = 3;

    private OnboardingPage onboardingPage;

    @BeforeMethod
    public void initPage() {
        onboardingPage = new OnboardingPage(driver);
    }

    // TC-01: Skip tutorial

    @Test(priority = 1, description = "TC-01: Tap Skip on onboarding — should land on Login screen")
    public void testSkipTutorial() {
        test = extent.createTest("TC-01: Skip Tutorial");

        Assert.assertTrue(onboardingPage.isOnboardingDisplayed(), "Onboarding screen should be visible on app launch");
        LoginPage loginPage = onboardingPage.tapSkip();
        Assert.assertTrue(loginPage.isLoginScreenDisplayed(), "After Skip, Login screen should be visible");
        test.info("Skip button navigated directly to the Login screen ✓");
    }

    // TC-02a: Navigate through all onboarding screens and count them

    @Test(priority = 2, description = "TC-02a: Navigate all onboarding — validate screen count")
    public void testOnboardingScreenCount() {
        test = extent.createTest("TC-02a: Onboarding Screen Count");
        int actualCount = onboardingPage.getNumberOfScreens();
        test.info("Detected " + actualCount + " onboarding screen indicator(s)");
        Assert.assertEquals(actualCount, EXPECTED_SCREEN_COUNT, "Number of onboarding screens should be " + EXPECTED_SCREEN_COUNT);
    }


    // TC-02b: Validate description on each screen while navigating

    @Test(priority = 3, description = "TC-02b: Navigate all onboarding — validate description text")
    public void testOnboardingDescriptions() {
        test = extent.createTest("TC-02b: Onboarding Descriptions");
        for (int screen = 1; screen <= EXPECTED_SCREEN_COUNT; screen++) {
            String description = onboardingPage.getCurrentScreenDescription();
            test.info("Screen " + screen + " description: \"" + description + "\"");
            Assert.assertNotNull(description, "Description on screen " + screen + " should not be null");
            Assert.assertFalse(description.isBlank(), "Description on screen " + screen + " should not be empty");
            if (screen < EXPECTED_SCREEN_COUNT) {
                onboardingPage.tapNext();
            }
        }

        // After all screens, app should transition to Login
        LoginPage loginPage = new LoginPage(driver);
        onboardingPage.tapNext(); // last Next transitions to Login
        Assert.assertTrue(loginPage.isLoginScreenDisplayed(), "After last onboarding screen, Login screen should appear");
    }
}
