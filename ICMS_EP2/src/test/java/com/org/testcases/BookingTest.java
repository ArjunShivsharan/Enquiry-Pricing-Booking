package com.org.testcases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.org.pages.BookingElements;

public class BookingTest {
	
	WebDriver driver;
	BookingElements book;
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
		    System.out.println("Launching Old Sarjak Booking Module..");
            driver.get("http://10.10.0.72/ICMSVerNew/Login.aspx?redirect=/ICMSVerNew/CustomerService/BookingEntry.aspx");
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	
    @BeforeTest
	public String getEnquiryNumberTest() throws Exception {
		
    	String enquiryno = null;
    	try 
		{
			
			connection=DriverManager.getConnection("jdbc:sqlserver://150.242.14.21;databaseName=ICMS","sa","pass@123$");
			String sp ="SELECT * FROM EnquiryDetails WHERE CreatedDate = (SELECT MAX(CreatedDate) FROM EnquiryDetails)";
			   Statement statement = connection.createStatement();
			   ResultSet rs = statement.executeQuery(sp);
			   
			   while (rs.next()) 
			   {
				   enquiryno =  rs.getString("EnquiryNo");
			    }
			   
		} catch (SQLServerException e) 
		{
			System.out.println("Unable to connect to DataBase. Please make sure your VPN is connected.");
		}
		return enquiryno;
	}
	
	
	
	@Test(priority = 1)
	public void loginApp() throws Exception {
		
		book = PageFactory.initElements(driver, BookingElements.class);
		book.login("admin", "nil");
		System.out.println("Logged In using credentials as Username: admin and Password: nil ");
	}
	
	@Test(priority = 2)
	public void createBookingTest() throws Exception 
	{
		book.createBooking(getEnquiryNumberTest());
		
	}

	@AfterSuite
	public void closeDriver() throws InterruptedException 
	{
		Thread.sleep(15000);
		driver.close();
	}

}
