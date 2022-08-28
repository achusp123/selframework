
package test.Core;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.server.browserlaunchers.InternetExplorerLauncher;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.appium.java_client.*;

public class WebdriverFactory {

	public static WebDriver driver;
	public static RemoteWebDriver mobile_driver;
	public static DesiredCapabilities dc;
	String strUserAgent;
	Proxy proxy = new Proxy();
	ChromeOptions options = new ChromeOptions();
	Map<String, String> mobileEmulation = new HashMap<>();

	public WebDriver getDriver(String browser, String server, String port) throws MalformedURLException {
		switch (browser) {
		case "chrome":
//        	System.setProperty("webdriver.chrome.silentOutput","true");
//        	System.setProperty("webdriver.chrome.driver", "Drivers/chromeDriver.exe");
//        	System.setProperty("webdriver.chrome.driver", "src/test/resources/Drivers/chromeDriver.exe");
			dc = DesiredCapabilities.chrome();
			options.addArguments("--disable-infobars");
			options.addArguments("--disable-notifications");
			dc.setCapability(ChromeOptions.CAPABILITY, options);
			dc.isJavascriptEnabled();
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(dc);

			break;

		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();

			break;
		case "firefox":
			ProfilesIni profile = new ProfilesIni();
			FirefoxProfile myprofile=profile.getProfile("testProfile");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(myprofile);
			break;
		}
//		  try {
//			driver = new RemoteWebDriver(new URL("http://"+server+":"+port+"/wd/hub"), dc);
//					
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} 

		return driver;

	}

}
