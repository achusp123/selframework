

package test.Core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;
import org.yaml.snakeyaml.Yaml;

public class  RetryListenerClass implements IAnnotationTransformer
{
	@Override
	public void transform(ITestAnnotation testannotation, Class testClass, Constructor testConstructor, Method testMethod) {
		 
		 
		 if (testannotation.getRetryAnalyzerClass() == null) {
		 testannotation.setRetryAnalyzer(RetryFailedTest.class);
		 }
		 
		 }
	
	}

