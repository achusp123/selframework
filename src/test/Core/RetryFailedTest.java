package test.Core;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class RetryFailedTest implements IRetryAnalyzer  {

	private int retrycnt=1;
	private int Maxretrycount=Integer.parseInt(ReadProperties.Properties("RetryCount"));
	public boolean retry(ITestResult result){
		if(retrycnt<Maxretrycount){
			System.out.println("Retrying "+result.getName()+" again and the count is " + (retrycnt+1));
			retrycnt++;
			return true;
		}
		return false;
	}
	
		
		

}