package com.org.testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.org.pages.EnquiryElements;


public class EnquiryPunchTest 
{
	WebDriver driver;
	public ExtentReports report;
	EnquiryElements enquiry;
	ExtentHtmlReporter extent;
	public ExtentTest logger;
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
	
	@BeforeTest
	public void openDatabase() throws SQLException, IOException 
	{
		try 
		{
			File source = new File("./data/DataSheetICMS.xlsx");
			FileInputStream fis = new FileInputStream(source);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet1 = workbook.getSheetAt(0);
			
			String username = sheet1.getRow(9).getCell(1).getStringCellValue();
			String password = sheet1.getRow(10).getCell(1).getStringCellValue();
			String dburl = sheet1.getRow(8).getCell(1).getStringCellValue();
		    System.out.println("Connecting to Database...");
			connection=DriverManager.getConnection(dburl,username,password);
			System.out.println("Connected to Database!");//:
			
		} catch (SQLServerException e) 
		{
			
			System.out.println("Unable to connect to DataBase. Please make sure your VPN is connected.");
		}
		
	}
	
	@BeforeClass
	public void setUp() throws Exception 
	{
			
			
			driver.get("https://webpush.sarjak.com/iCMSIdentityServer/Account/Login");
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			
				
	}
	
		
	@Test(priority = 1)
	   public void loginAppforEnquiry() throws Exception 
	   {
		   enquiry = PageFactory.initElements(driver, EnquiryElements.class);
		   enquiry.login("admin","nil"); 
		   System.out.println("Successfully Logged In to ICMS PLUS for Punching Enquiry  using Credentials : Username: admin, Password: nil");
    }
	
	
	@Test(priority = 2)
	   public void searchItemTest() throws InterruptedException 
	   {
		   Thread.sleep(1000);
		   enquiry.searchItem("Freight Request");
		   System.out.println("Clicked on Freight Request..");
	   }
	
	
	@Test(priority = 3)
	   public void newInquiryPunchTest() throws Exception 
	   {
		   Thread.sleep(1000);
		   enquiry.newInquiryPunch();
	   }
	@AfterClass
	public void closeDriver() throws Exception 
	{
		driver.close(); 
	}
	
	@AfterTest
	public void closeDatabase() throws Exception 
	{
		   try {
			String sp ="SELECT * FROM EnquiryDetails WHERE CreatedDate = (SELECT MAX(CreatedDate) FROM EnquiryDetails)";
			   Statement statement = connection.createStatement();
			   ResultSet rs = statement.executeQuery(sp);
			   System.out.println("  EnquiryNo"+ "\t" + "POL"+ "\t  " + "POD "+ "\t" + "CreatedDate");
			   while (rs.next()) 
			   {
			   String enquiryno = rs.getString("EnquiryNo");
			   String pol = rs.getString("POL");
			   String finalplaceofdelivery = rs.getString("FinalPlaceofDelivery");
			   String createddate = rs.getString("CreatedDate");
			   System.out.println("|"+ enquiryno + "    |" + pol +"|" +finalplaceofdelivery +"     | "+ createddate +" | "+"\n" + "-----------");
			   
			   }
		} catch (java.lang.NullPointerException e) {
			System.out.println("It seems connection was never established to database so unable to fetch any data from Database.");
		}
		   connection.close();
		   System.out.println("Database connection is closed!");
	}
}
