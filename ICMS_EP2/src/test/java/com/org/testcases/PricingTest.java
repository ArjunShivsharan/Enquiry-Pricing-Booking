package com.org.testcases;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.org.pages.PricingElements;

public class PricingTest 
{
	WebDriver driver;
	public ExtentReports report;
	PricingElements pricing;
	ExtentHtmlReporter extent;
	Connection connection;

	@BeforeSuite
	public void setUpSuite() 
	{
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		System.out.println("Allowing Geolocations..");
		HashMap<String, Integer> contentSetting = new HashMap<String, Integer>();
		HashMap<String, Object> profile = new HashMap<String, Object>();
		HashMap<String, Object> prefs = new HashMap<String, Object>();
	    contentSetting.put("notifications", 1);
		contentSetting.put("geolocation", 1);
		contentSetting.put("geolocation", 1);
		profile.put("managed_default_content_settings", contentSetting);
		prefs.put("profile", profile);
		options.addArguments("enable-features=NetworkServiceInProcess");
		options.setExperimentalOption("prefs", prefs);
		
		driver = new ChromeDriver(options);
	}
	
	@BeforeClass
	public void setUp() throws Exception 
	{
		    System.out.println("Launching ICMS website..");
            driver.get("https://webpush.sarjak.com/iCMSIdentityServer/Account/Login");
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	
	
	@Test(priority = 1)
	public void loginApp() throws Exception {
		
		pricing = PageFactory.initElements(driver, PricingElements.class);
		pricing.login("darshana.g", "nil1");
		System.out.println("Logged In using credentials as Username:darshana.g and Password: nil1 ");
	}

	@Test(priority = 2)
	public void searchItemTest() throws InterruptedException {
		System.out.println("Pricing Process Started..");
		pricing.searchItem("Order Pricing");
	}

	@Test(priority = 3)
	public void startPricingTest() throws Exception {
		try {
			
			File source = new File("./data/DataSheetICMS.xlsx");
			FileInputStream fis = new FileInputStream(source);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet1 = workbook.getSheetAt(0);
			int carrierCount = (int) sheet1.getRow(4).getCell(1).getNumericCellValue();
			System.out.println(carrierCount);
			
			if(carrierCount==1) 
			{
				pricing.startPricingForOneCarrier();
			}
			
			if(carrierCount==2) 
			{
				pricing.startPricingForTwoCarrier();
			}
			if(carrierCount==3) 
			{
				pricing.startPricingForThreeCarrier();
			}
			
			
			} catch (Exception e) {
				
				e.printStackTrace();
			} 
	}
	
	
	@AfterSuite
	public void closeDriver() throws InterruptedException 
	{
		Thread.sleep(5000);
		driver.close();
	}
	
}
