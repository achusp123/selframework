// <summary>
//     Smoke Test 
// </summary>
// <revision>
//     Author:	Nidhish Jain
//     Date:	07/14/2015		Action: Created
// </revision>

package test.Core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;















import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.IClass;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;



public class Log extends TestListenerAdapter {
	
	public String strfileName;
	public String strmessages;
	static Action Action =new Action();
	public  static Logger log;
	public String strfoldername;
    int count =0;
	int loop =0;
	ITestResult tr;
	public static File fil;
	public Log(String name)
	{
		// configure log4j properties file
	System.setProperty("logdir", "test");
	PropertyConfigurator.configure("Log4j.properties");
	log = Logger.getLogger(Log.class.getName());
	strfoldername=Common.GetLocationPath()+"\\Log\\"+Common.GetDate();
	Common.CreateDirectory(Common.GetLocationPath()+"\\test-output\\TestCaseLogs");
	 }
	
	@Override
	public void onTestStart(ITestResult tr) {
	
			CreateLogFile(tr);
			Info("****************************************************************************************");
			 
			Info("****************************************************************************************");
		 
			Info("$$$$$$$$$$$$$$                 "+tr.getName()+ "       $$$$$$$$$$$$$$$$$$$$$");
		 
			Info("****************************************************************************************");
		 
			Info("****************************************************************************************");
		 
			Action.DeleteAllCookies();
	
			
	}

      public Log()
      {
    	
      }
      public static String getCurrentDate() {
    	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    	    Date now = new Date();
    	    String strDate = sdfDate.format(now);
    	    return strDate;
    	}
      public static String getCurrentTime() {
    	    SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
    	    Date now = new Date();
    	    String strTime = sdfDate.format(now);
    	    return strTime;
    	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		
			Info(tr.getName() + "'PASSED");
			
			
			System.out
					.println("..................................FINISH.......................................");
			Action.DeleteAllCookies();
			
	}

	@Override
	public void onTestFailure(ITestResult tr) {

			Error("Test '" + tr.getName() + "' FAILED");
			System.out.println(".............................................................................");
			Action.TakeScreenshot(tr.getName());
			Action.DeleteAllCookies();
			
	
			}

	@Override
	public void onTestSkipped(ITestResult tr) {
		if(loop==0){
			Info("Test '" + tr.getName() + "' SKIPPED");
			System.out.println(".....");
		   loop++;
		}
		
	}

	private void log(String methodName) {
		System.out.println(methodName);
		
		//WriteToLogFile(methodName);
	}

	private void log(IClass testClass) {
		System.out.println(testClass);
		
		//WriteToLogFile(testClass.toString());
		
	}
	
	 private static boolean initializationFlag = false;
//Create a log file for every run , if we re run the test cases on same day it will replace the file with latest log.
public String CreateLogFile(ITestResult tr)
{
	strfileName=Common.GetLocationPath()+"\\test-output\\TestCaseLogs"+"\\"+tr.getName()+".txt";
	 fil = new File(strfileName);
	
	try {
		fil.createNewFile();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	return strfileName;
}

public void GenerateReportsLog()
{
	
Common.CopyDirectory(Common.GetLocationPath()+"\\test-output\\",Common.GetLogReportFolderLocation());

}


public  void Info(String message) {
	 log.info(message);
	try {
		FileWriter fw = new FileWriter(fil.getAbsoluteFile(),true);
		fw.write(System.getProperty( "line.separator" )+getCurrentDate()+" "+getCurrentTime()+" - "+message);
		fw.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
public  void InfoMobile(String message) {
	 log.info(message);
	
	}
public void Warn(String message) {

log.warn(message);

}

public void Error(String message) {
	
	WebdriverFactory dr = new WebdriverFactory();

	if(dr.driver ==null){
		
	}else{
			
	}
	


}

public  void fatal(String message) {

log.fatal(message);

}

public  void debug(String message) {

log.debug(message);

}


	


String readFile(String fileName) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(fileName));
    try {
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(System.getProperty( "line.separator" )+line);
            
            line = br.readLine();
        }
        return sb.toString();
    } finally {
        br.close();
    }
}



}

	
