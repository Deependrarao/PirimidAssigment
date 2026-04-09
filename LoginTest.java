package com.pfm.automation.tests;

import com.pfm.automation.base.BaseTest;
import com.pfm.automation.pages.LoginPage;
import com.pfm.automation.pages.OnboardingPage;
import com.pfm.automation.pages.OtpPage;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * LoginTest: Covers TC-03 (successful login) and TC-04 (invalid credentials).
 * Test Data:
 *   VALID   → 10-digit mobile + non-empty name → should reach OTP screen
 *   INVALID → short mobile / empty name        → should show error on Login screen
 */
public class LoginTest extends BaseTest {

    // Test data
    private static final String VALID_MOBILE   = "9876543210";
    private static final String VALID_NAME     = "Deepu Test";
    private static final String INVALID_MOBILE = "123";          // too short
    private static final String INVALID_NAME   = "";             // empty
    private static final String VALID_OTP      = "123456";       // any 6-digit OTP is accepted

    private LoginPage loginPage;

    @BeforeMethod
    public void navigateToLogin() {
        // Skip onboarding and land on the Login screen before each test
        loginPage = new OnboardingPage(driver).tapSkip();
    }

    // TC-03: Successful login — valid credentials → OTP screen

    @Test(priority = 1, description = "TC-03: Valid credentials should navigate to OTP screen")
    public void testSuccessfulLogin() {
        test = extent.createTest("TC-03: Successful Login with Valid Credentials");
        OtpPage otpPage = loginPage.loginWith(VALID_MOBILE, VALID_NAME);
        Assert.assertTrue(otpPage.isOtpScreenDisplayed(), "OTP screen should be displayed after successful login");
        test.info("App navigated to OTP screen successfully ✓");
    }

    // TC-04a: Invalid mobile number → error on Login screen

    @Test(priority = 2, description = "TC-04a: Invalid mobile number should show error")
    public void testInvalidMobileNumber() {
        test = extent.createTest("TC-04a: Invalid Mobile Number");
        loginPage.enterMobileNumber(INVALID_MOBILE)
                 .enterName(VALID_NAME)
                 .checkAgreeTerms()
                 .tapSendOtp();
        String error = loginPage.getMobileErrorMessage();
        test.info("Error message displayed: \"" + error + "\"");
        Assert.assertTrue(loginPage.isLoginScreenDisplayed(), "App should remain on Login screen after invalid mobile");
        Assert.assertFalse(error.isEmpty(), "An error message should be displayed for an invalid mobile number");
    }

    // TC-04b: Empty name → error on Login screen

    @Test(priority = 3, description = "TC-04b: Empty name field should show error")
    public void testEmptyName() {
        test = extent.createTest("TC-04b: Empty Name Field");
        loginPage.enterMobileNumber(VALID_MOBILE)
                 .enterName(INVALID_NAME)          // empty string
                 .checkAgreeTerms()
                 .tapSendOtp();
        String error = loginPage.getErrorMessage();
        test.info("Error message displayed: \"" + error + "\"");
        Assert.assertTrue(loginPage.isLoginScreenDisplayed(), "App should remain on Login screen when name is empty");
        Assert.assertFalse(error.isEmpty(), "An error message should be displayed when name is empty");
    }

    // TC-04c: Both fields invalid → error on Login screen

    @Test(priority = 4, description = "TC-04c: Both invalid mobile and empty name should show error")
    public void testBothFieldsInvalid() {
        test = extent.createTest("TC-04c: Both Mobile and Name Invalid");
        loginPage.enterMobileNumber(INVALID_MOBILE)
                 .enterName(INVALID_NAME)
                 .checkAgreeTerms()
                 .tapSendOtp();

        Assert.assertTrue(loginPage.isLoginScreenDisplayed(), "App should remain on Login screen when both fields are invalid");
        Assert.assertFalse(loginPage.getErrorMessage().isEmpty(), "At least one error message should be displayed");
        test.info("Error correctly displayed for invalid mobile + empty name ✓");
    }
}
