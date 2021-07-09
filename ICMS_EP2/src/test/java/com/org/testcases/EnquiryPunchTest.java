package com.org.testcases;

import java.sql.Connection;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.org.pages.EnquiryElements;


public class EnquiryPunchTest 
{
	WebDriver driver;
	EnquiryElements enquiry;
	Connection connection;
	
	

	@BeforeSuite
	public void setUpSuite() throws Exception 
	{
		System.out.println("Setting up System Properties..");
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
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
			try {
				driver.get("https://webpush.sarjak.com/iCMSIdentityServer/Account/Login");
				driver.manage().window().maximize();
				driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			} catch (WebDriverException e) {
				System.out.println("Please check your Internet Connectivity..!");
				driver.close();
			}
	}
	
	
	@Test(priority = 1)
	   public void loginAppWithInvalidCredentials() throws Exception 
	   {
		   enquiry = PageFactory.initElements(driver, EnquiryElements.class);
		   enquiry.login("invalidUsername","invalidPassword"); 
		   System.out.println("Logging In with Invalid Credentials : Username: invalidUsername , Password: invalidPassword");
		   Thread.sleep(2000);
		   String error = driver.findElement(By.cssSelector("div[class='alert alert-danger']")).getText();
		   System.out.println(error);
		   Thread.sleep(2000);
		   System.out.println("Please try with correct credentials");
		}
		
	@Test(priority = 2)
	   public void loginWithValidCredentials() throws Exception 
	   {
		   enquiry.login("admin","nil"); 
		   System.out.println("Logging In with correct Credentials : Username: admin, Password: nil");
		   Thread.sleep(2000);
		   System.out.println("Logged In sucessfully.");
		}
	
	
	@Test(priority = 3)
	   public void searchItemTest() throws InterruptedException 
	   {
		   Thread.sleep(1000);
		   enquiry.searchItem("Freight Request");
		   System.out.println("Clicked on Freight Request..");
	   }
	
	
	@Test(priority = 4)
	   public void newInquiryPunchTest() throws Exception 
	   {
		   Thread.sleep(1000);
		   enquiry.newInquiryPunch();
	   }
	@AfterClass
	public void closeDriver() throws Exception 
	{
		Thread.sleep(3000);
		driver.close(); 
	}
	
	

}
