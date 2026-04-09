package com.pfm.automation.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

/**
 * DriverManager: Centralises AndroidDriver creation.
 * Reads configuration from config.properties so no hard-coding in test classes.
 */
public class DriverManager {

    private DriverManager() { /* utility class */ }

    public static AndroidDriver createDriver() throws IOException {
        Properties props = loadConfig();
        UiAutomator2Options options = new UiAutomator2Options();
        // Device / App configuration
        options.setDeviceName(props.getProperty("device.name", "Android Emulator"));
       // options.setPlatformVersion(props.getProperty("platform.version", "16.0"));
        options.setApp(props.getProperty("app.path"));          // absolute path to .apk
        options.setAppPackage(props.getProperty("app.package", "com.pfm.app"));
        options.setAppActivity(props.getProperty("app.activity", ".MainActivity"));

        // Appium behaviour
        options.setAutoGrantPermissions(true);                  // auto-grant Android permissions
        options.setNoReset(false);                              // fresh install each run
        options.setNewCommandTimeout(Duration.ofSeconds(120));
        String appiumUrl = props.getProperty("appium.url", "http://127.0.0.1:4723");
        return new AndroidDriver(new URL(appiumUrl), options);
    }

    // ── Loads src/test/resources/config.properties ──────────────────────
    private static Properties loadConfig() throws IOException {
        Properties props = new Properties();
        try (InputStream is = DriverManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) {
                throw new IOException("config.properties not found on classpath.");
            }
            props.load(is);
        }
        return props;
    }
}
