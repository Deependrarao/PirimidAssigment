package com.pfm.automation.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.pfm.automation.utils.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;

/**
 * BaseTest: Sets up and tears down the Appium AndroidDriver before/after each test class.
 * Also initialises ExtentReports for HTML reporting.
 */
public class BaseTest {

    protected AndroidDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;


    //initialise ExtentReports once

    @BeforeSuite
    public void setUpReport() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports/PFM_TestReport.html");
        sparkReporter.config().setReportName("PFM App – Automation Test Report");
        sparkReporter.config().setDocumentTitle("PFM Appium Tests");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Framework", "Appium 8.x + TestNG");
        extent.setSystemInfo("Language", "Java 11");
    }

    // launch the app before each class

    @BeforeClass
    public void setUpDriver() throws IOException {
        driver = DriverManager.createDriver();
    }

    // log pass/fail to extent

    @AfterMethod
    public void logResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed successfully.");
        } else {
            test.skip("Test skipped.");
        }
    }

    //  quit driver after each class

    @AfterClass
    public void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    // flush ExtentReports once

    @AfterSuite
    public void tearDownReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
