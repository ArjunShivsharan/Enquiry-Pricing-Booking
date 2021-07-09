package com.org.testcases;

import java.util.ArrayList;
import java.util.List;
import org.testng.TestNG;
import org.testng.annotations.Test;

public class  TestNGSuiteRunner {
	@Test(invocationCount = 1, threadPoolSize = 1) // For Functional put 1, For Load testing put 'n' , threadPoolSize =
	
	public synchronized void loadtest() throws Exception {
		
		TestNG obj = new TestNG();
		List<String> suites = new ArrayList<String>(); 
		long id = Thread.currentThread().getId();
		System.out.println("Currently running thread :" + id);
        suites.add("C:\\Users\\Mallikarjun.Shivshar\\eclipse-workspace\\ICMS_EP2\\enquirytestng.xml");
        suites.add("C:\\Users\\Mallikarjun.Shivshar\\eclipse-workspace\\ICMS_EP2\\pricingtestng.xml");
        suites.add("C:\\Users\\Mallikarjun.Shivshar\\eclipse-workspace\\ICMS_EP2\\bookingtestng.xml");
        
		obj.setTestSuites(suites);
		obj.run();
	    
	}
}
