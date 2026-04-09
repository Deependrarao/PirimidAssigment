package com.pfm.automation.pages;

import com.pfm.automation.utils.WaitUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;



/**
 * LoginPage: Page Object for the Login screen.
 * Flutter element keys map 1-to-1 to Android resource-id when using UIAutomator2.
 */
public class LoginPage {

    private final AndroidDriver driver;

    // ── Locators (Flutter key → accessibilityId) ──────────────────────────
    private static final By MOBILE_NUMBER_FIELD = AppiumBy.accessibilityId("txt_fld_mobile_number");
    private static final By NAME_FIELD          = AppiumBy.accessibilityId("txt_fld_person_name");
    private static final By AGREE_CHECKBOX      = AppiumBy.accessibilityId("chk_bx_agree_terms");
    private static final By SEND_OTP_BTN        = AppiumBy.accessibilityId("btn_send_otp");

    // Error messages matched by text content
    private static final By ERROR_MESSAGE = By.xpath(
            "//*[contains(@text,'error') or contains(@content-desc,'error')]");
    private static final By MOBILE_ERROR  = By.xpath(
            "//*[contains(@text,'mobile') or contains(@text,'number')]");

    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        // No PageFactory.initElements needed — all elements resolved lazily via findElement()
    }

    /** Clear and enter a mobile number into the mobile field. */
    public LoginPage enterMobileNumber(String mobile) {
        WaitUtils.waitForVisibility(driver, MOBILE_NUMBER_FIELD).clear();
        driver.findElement(MOBILE_NUMBER_FIELD).sendKeys(mobile);
        return this;
    }

    /** Clear and enter a name into the name field. */
    public LoginPage enterName(String name) {
        WaitUtils.waitForVisibility(driver, NAME_FIELD).clear();
        driver.findElement(NAME_FIELD).sendKeys(name);
        return this;
    }

    /** Tap the "I agree" checkbox if it is not already checked. */
    public LoginPage checkAgreeTerms() {
        var checkbox = WaitUtils.waitForClickability(driver, AGREE_CHECKBOX);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        return this;
    }

    /** Tap the Send OTP button and return the OTP verification page. */
    public OtpPage tapSendOtp() {
        WaitUtils.waitForClickability(driver, SEND_OTP_BTN).click();
        return new OtpPage(driver);
    }

    /** Returns true when the Send OTP button is visible (Login screen is active). */
    public boolean isLoginScreenDisplayed() {
        return WaitUtils.isDisplayed(driver, SEND_OTP_BTN);
    }

    /** Returns visible error message text, or empty string if none. */
    public String getErrorMessage() {
        try {
            return WaitUtils.waitForVisibility(driver, ERROR_MESSAGE).getText();
        } catch (Exception e) {
            return "";
        }
    }

    /** Returns the mobile-number specific error message. */
    public String getMobileErrorMessage() {
        try {
            return WaitUtils.waitForVisibility(driver, MOBILE_ERROR).getText();
        } catch (Exception e) {
            return "";
        }
    }

    /** Convenience: fill all login fields and submit. */
    public OtpPage loginWith(String mobile, String name) {
        return enterMobileNumber(mobile)
                .enterName(name)
                .checkAgreeTerms()
                .tapSendOtp();
    }

}
