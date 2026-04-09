package com.pfm.automation.utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * WaitUtils: Reusable explicit-wait helpers.
 * All page objects and tests use these instead of Thread.sleep().
 */
public class WaitUtils {

    private static final int DEFAULT_TIMEOUT_SECONDS = 15;

    private WaitUtils() { /* utility class */ }

    /** Wait until an element is visible and return it. */
    public static WebElement waitForVisibility(AndroidDriver driver, By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Wait until an element is clickable and return it. */
    public static WebElement waitForClickability(AndroidDriver driver, By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    /** Wait until an element contains the expected text. */
    public static boolean waitForText(AndroidDriver driver, By locator, String text) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /** Check whether an element is currently displayed (no wait). */
    public static boolean isDisplayed(AndroidDriver driver, By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Short, explicit pause — use sparingly and only when absolutely needed. */
    public static void hardWait(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException ignored) { }
    }
}
