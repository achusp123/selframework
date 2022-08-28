// <summary>
//     Test Base 
// </summary>
// <revision>
//     Author:	Nidhish Jain
//     Date:	07/14/2015		Action: Created
// </revision>

package test.Core;

import org.testng.ITestResult;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.internal.TestResult;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestBase {

	public static WebDriver driver = null;
	public static AndroidDriver<AndroidElement> mobile_driver = null;
	static WebdriverFactory driverFactory = new WebdriverFactory();
	public static StringBuffer verificationErrors = new StringBuffer();
	static Log log;
	Action Action;
	static IMethodInstance instns = null;
	static String strTestCaseID;
	static ITestResult result;
	public static Instant teststart;
	public static Instant testend;

	@BeforeSuite(alwaysRun = true)
	public void BeforeSuit() {
		Common.DeleteDirectory();
		Common.CreateDirectory(Common.GetLocationPath() + "\\Log\\" + Common.GetDate());
		log = new Log("Log file Created");

	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "Browser" })

	public void beforeTest(@Optional String Browser) {

		try {
			if (Browser == null) {

				driver = driverFactory.getDriver(ReadProperties.Properties("Browsername"),ReadProperties.Properties("SeleniumServer"), ReadProperties.Properties("ServerPort"));
				teststart = Instant.now();
				driver.manage().window().maximize();
			}

			else {
				driver = driverFactory.getDriver(Browser, ReadProperties.Properties("SeleniumServer"),ReadProperties.Properties("ServerPort"));
				driver.manage().window().maximize();
				teststart = Instant.now();
			}

			new Action(driver);
			// System.out.println(driver);
			// driver.manage().window().maximize();

			// log.Info("Test case started");
			Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
			String browserName = caps.getBrowserName();
			String browserVersion = caps.getVersion();
			log.Info("Test case started on " + browserName + " " + browserVersion);

		} catch (Exception ex) {
			log.Error(ex.getMessage());
		}

	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod() {

		if (driver == null) {

		} else {
			// driver.close();
			// driver.manage().deleteAllCookies();
			driver.quit();
			testend = Instant.now();
			Duration testDuration = Duration.between(teststart, testend);
			double min;
			min = (testDuration.toMillis() / 1000.0) / 60.0;
			double mili = testDuration.toMillis();
			log.Info("Time taken for the test: " + (Math.round(min * 100.0) / 100.0) + " Minutes");
		}

	}

	@AfterSuite(alwaysRun = true)
	public void AfterSuit() {
		log.GenerateReportsLog();

	}

}
