
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
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
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

import io.appium.java_client.android.AndroidDriver;
public class Action<IWebElement> {

	public static  WebDriver driver;
	public  WebElement element;

	String BrowserName;
	
	//public Actions act = new Actions(driver);
	public static HashMap<String, String> id = new HashMap<String, String>();

	By by;
	private WebDriverWait WebDriverWait;
	
	Log log= new Log();
	//Paramaterized constuctor to assign the driver instance 
	public Action(WebDriver iDriver)
	{
	driver=iDriver;
	this.WebDriverWait = new WebDriverWait(iDriver, 150);

   	}
	//Constructor
	public Action() {
		// TODO Auto-generated constructor stub
			}

	
	
	

//</revision>
	public  Action OpenURl(String url)
	{
		
			driver.navigate().to(url);	
		
		
		return this;
	}
	

	public  Action enterText(LocatorObject locator,String text)
	{
		driverwait(5);
		FindElement(locator).sendKeys(text);
		return this;
	}
	
	
	public  Action Click(LocatorObject locator)
	{
		driverwait(5);
		
		FindElement(locator).click();
		driverwait(10);
		return this;
	}
		public Action Wait(){
		this.WebDriverWait =new WebDriverWait(driver, 15);
		try {
			Thread.sleep(200);
		} catch (Exception r) {
			// TODO: handle exception
		}
		return this;
	}
	
		public Action javascriptClick(LocatorObject locator){
			element =FindElement(locator);
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", element);
			return this;
		}
		
		public boolean IsVisible(LocatorObject locator)
		{
		  
			WaitForPageLoad();
			if(driver.findElements(locator.locatorValue).size()>0)
		  {
			return true;
		  }
			else
			{
				return false;
			}
		}
	public  Action driverwait(long time)
	{
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		return this;
	}
	    
    public Action  WaitForPageLoad()
    {
    	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    	return this;
    }
    	


	public WebElement FindElement(LocatorObject locator) {
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
	
	 public Action ActionLog(String message)
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
   System.out.println(tempMessage);
   log.Info(tempMessage);
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
public String GetPageTitle()
{
    return driver.getTitle();
   
   
}
public Action VerifyPageTitle(String pageTitle)

{
	if (GetPageTitle().toLowerCase().contains(pageTitle.toLowerCase()))
	{
		ActionLog(pageTitle+" : Verifed Successfully");
	}
	else
	{
		AssertFail(pageTitle +": Not Found");
	
	}
	
	return this;
}



public Action SelectdropdrownValueByVisibleText(LocatorObject locator,String Text)
{
	Select dropdown = new Select(FindElement(locator));
//	System.err.println(Text);
	dropdown.selectByVisibleText(Text);
	return this;
}


public Action SelectdropdrownValueByIndex(LocatorObject locator,int index)
{
	Select dropdown = new Select(FindElement(locator));
	dropdown.selectByIndex(index);
	return this;
}


public Action SelectdropdrownValueByValue(LocatorObject locator,String value)
{
	try {
		Select dropdown = new Select(FindElement(locator));
		dropdown.selectByValue(value);	
	} catch (NullPointerException e) {
		e.printStackTrace();
	}
	
	return this;
}

public Action TakeScreenshot(String screenShotName)
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



public Action DeleteAllCookies()
{
    
	if(driver == null){
		
	}else{
		try
	    {     
	         driver.manage().deleteAllCookies();
	               
	             
	    }
	    catch (Exception ex)
	    {
	        ActionLog("Cookie deletion failed. " + ex.getMessage());
	    }	
	}
	
	

    return this;
}


public  Action SendKeys(LocatorObject locator,Keys Keys)
{
	
	FindElement(locator).sendKeys(Keys);
	return this;
}





public String GetCurrentUrl(){
	String strcurrentUrl;
	strcurrentUrl =driver.getCurrentUrl();
	return strcurrentUrl;
}








		public  Action enterText(LocatorObject locator,Keys key)
		{
			driverwait(5);
			FindElement(locator).sendKeys(key);
			return this;
		}
		
		
	
	


	
	public Action executeJavaScript(String javascript){
		
		
		
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript(javascript);
		return this;
	}
	
	
		

		public Action SendKeysEnter(){
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ENTER).click().build().perform();
			return this;
		}
		
		
		public Action wait(int time){
	    	try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return this;
	    }
	
    public WebElement fluentWait(final LocatorObject locator) {
                     Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                             .withTimeout(30, TimeUnit.SECONDS)
                             .pollingEvery(5, TimeUnit.SECONDS)
                             .ignoring(NoSuchElementException.class);

                     WebElement foo =wait.until(new com.google.common.base.Function<WebDriver, WebElement>() {
                             public WebElement apply(WebDriver driver) {
                             return FindElement(locator);
                         }
                     });

                     return  foo;
                 }
   


}

