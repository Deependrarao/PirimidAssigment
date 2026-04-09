package com.pfm.automation.pages;

import com.pfm.automation.utils.WaitUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * OtpPage: Page Object for the OTP Verification screen.
 * Flutter keys: txt_fld_otp_0 … txt_fld_otp_5, btn_verifty_otp
 *
 */
public class OtpPage {

    private final AndroidDriver driver;

    // ── Locators ─────────────────────────────────────────────────────────
    private static final By[] OTP_FIELDS = {
            By.id("txt_fld_otp_0"),
            By.id("txt_fld_otp_1"),
            By.id("txt_fld_otp_2"),
            By.id("txt_fld_otp_3"),
            By.id("txt_fld_otp_4"),
            By.id("txt_fld_otp_5")
    };

    private static final By VERIFY_BTN = By.id("btn_verifty_otp");   // spec typo preserved

    // Success indicator — adjust content-desc / text to match actual app
    private static final By SUCCESS_SCREEN = By.xpath("//*[@content-desc='otp_success' or contains(@text,'verified')]");

    // Error shown when OTP fields are empty or OTP is wrong
    private static final By OTP_ERROR_MSG = By.xpath("//*[@content-desc='otp_error' or contains(@text,'invalid') " + "or contains(@text,'empty') or contains(@text,'OTP')]");

    public OtpPage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /**
     * Enter a 6-digit OTP string one character per field.
     * @param otp exactly 6 characters, e.g. "123456"
     */
    public OtpPage enterOtp(String otp) {
        if (otp == null || otp.length() != 6) {
            throw new IllegalArgumentException("OTP must be exactly 6 digits, got: " + otp);
        }
        for (int i = 0; i < OTP_FIELDS.length; i++) {
            WebElement field = WaitUtils.waitForVisibility(driver, OTP_FIELDS[i]);
            field.clear();
            field.sendKeys(String.valueOf(otp.charAt(i)));
        }
        return this;
    }

    /** Tap the Verify OTP button. */
    public OtpPage tapVerifyOtp() {
        WaitUtils.waitForClickability(driver, VERIFY_BTN).click();
        return this;
    }

    /** Returns true when the success screen / confirmation is displayed. */
    public boolean isOtpSuccessDisplayed() {
        return WaitUtils.isDisplayed(driver, SUCCESS_SCREEN);
    }

    /** Returns true when an OTP error message is visible. */
    public boolean isOtpErrorDisplayed() {
        return WaitUtils.isDisplayed(driver, OTP_ERROR_MSG);
    }

    /** Returns the OTP error message text. */
    public String getOtpErrorMessage() {
        try {
            return WaitUtils.waitForVisibility(driver, OTP_ERROR_MSG).getText();
        } catch (Exception e) {
            return "";
        }
    }

    /** Returns true when the Verify OTP button is visible (OTP screen is active). */
    public boolean isOtpScreenDisplayed() {
        return WaitUtils.isDisplayed(driver, VERIFY_BTN);
    }
}
