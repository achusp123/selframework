
package test.Core;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteTouchScreen;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.Reporter;
import org.apache.commons.io.FileUtils;

import com.sun.xml.internal.bind.v2.model.core.Element;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
public class MobileAction<IWebElement> {

	public static  AndroidDriver<AndroidElement> driver;
	public  MobileElement element;

	String BrowserName;
	
	//public Actions act = new Actions(driver);
	public static HashMap<String, String> id = new HashMap<String, String>();

	By by;
	private WebDriverWait WebDriverWait;
	
	Log log= new Log();
	//Paramaterized constuctor to assign the driver instance 
	public MobileAction(AndroidDriver<AndroidElement> iDriver)
	{
	driver=iDriver;
	this.WebDriverWait = new WebDriverWait(iDriver, 150);

   	}
	//Constructor
	public MobileAction() {
		// TODO Auto-generated constructor stub
			}

	
	
	

	public  MobileAction enterText(LocatorObject locator,String text)
	{
		driverwait(5);
		FindElement(locator).sendKeys(text);
		return this;
	}
	

	public  MobileAction enterTextWithClear(LocatorObject locator,String text)
	{
		driverwait(5);
		FindElement(locator).clear();
		FindElement(locator).sendKeys(text);
		
		return this;
	}

	public  MobileAction Click(LocatorObject locator)
	{
		driverwait(5);
		//FindElement(locator).sendKeys(Keys.TAB);
		FindElement(locator).click();
		driverwait(10);
		return this;
	}
	
	

	
	
	public MobileAction Wait(){
		this.WebDriverWait =new WebDriverWait(driver, 15);
		try {
			Thread.sleep(200);
		} catch (Exception r) {
			// TODO: handle exception
		}
		return this;
	}
	
		

	public  MobileAction driverwait(long time)
	{
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		return this;
	}

    
    public MobileAction  WaitForPageLoad()
    {
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    	return this;
    }
    



	public MobileElement FindElement(LocatorObject locator) {
		try {
			
			new WebDriverWait(driver,60).
			until(ExpectedConditions.visibilityOfElementLocated(locator.locatorValue));
			
			
			
			return driver.findElement(locator.locatorValue);
		} catch (org.openqa.selenium.NoSuchElementException ex) {
			// Handle exception if the element is not found
			AssertFail("NoSuchElementException: The Object "
					+ locator.objectDescription + " not found! "
					);
		}catch (org.openqa.selenium.StaleElementReferenceException ex) {
			WaitForPageLoad();
			
					WebDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator.locatorValue));
			
			
			return driver.findElement(locator.locatorValue);
			
		} catch (ElementNotVisibleException ex) {
			// Handle exception if the element is not found
			AssertFail("ElementNotVisibleException: The Object "
					+ locator.objectDescription + " not found! ");
		} catch (WebDriverException ex) {
			// Handle exception if the element is not found
			AssertFail("NoSuchElementException: The Object "
					+ locator.objectDescription + " not found! "
					+ ex.getMessage());
		}
		
		return driver.findElement(locator.locatorValue);

		
	}
	
	
	
	 public MobileAction ActionLog(String message)
     {
        
        log.Info(message);
        Reporter.log(message);
        System.out.println(message);
      
        

         return this;
     }

public void AssertFail(String message)
{
//    String tempMessage = "|| Expected Result : " + " ---|| Actual Result: " + message + " || ";
	String tempMessage = "|| " + message + " || ";
//   System.out.println(tempMessage);
 log.Error(tempMessage);
 Assert.assertEquals("", message, tempMessage);
   //log.WriteToLogFile(tempMessage);
    try {
		throw new Exception(tempMessage);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


public String GetText(LocatorObject locator)
{
    String value = null;

     value= FindElement(locator).getText();
   
    return value;
}




public MobileAction TakeScreenshot(String screenShotName)
{
	String location  = Common.GetLocationPath() +"\\test-output\\"+ Common.GetDate()+"Screenshots";
 Common.CreateDirectory(location);
 try { 
	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	File destFile=new File(location+"\\"+screenShotName+".jpg");
	ActionLog("Failed Screenshot Saved at :"+location+"\\"+screenShotName+".jpg");
	
		FileUtils.copyFile(scrFile,destFile );
	} catch (IOException e) {
		// TODO Auto-generated catch block
	e.printStackTrace();
	AssertFail(e.getMessage());
	}
 catch (WebDriverException e) {
		// TODO Auto-generated catch block
	e.printStackTrace();
	AssertFail(e.getMessage());
	}
    return this;
}





public  MobileAction SendKeys(LocatorObject locator,Keys Keys)
{
	
	FindElement(locator).sendKeys(Keys);
	return this;
}




		public  MobileAction enterText(LocatorObject locator,Keys key)
		{
			driverwait(5);
			FindElement(locator).sendKeys(key);
			return this;
		}
		

	
	public MobileAction Actionclick(LocatorObject locator){
		element =FindElement(locator);
		Actions act=new Actions(driver);
		act.moveToElement(element).click().build().perform();
		return this;
		
	}




}

