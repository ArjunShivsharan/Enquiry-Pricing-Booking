package com.org.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class BookingElements {
	
	public WebDriver driver;
	
	public BookingElements(WebDriver driver) 
	{
		this.driver = driver;
	}
	
	@FindBy(id = "txtUserName") WebElement uname;
	@FindBy(id = "txtPassword") WebElement pass;
	@FindBy(id = "btnSubmit") WebElement loginButton;
	@FindBy(id = "ctl00_cphMaster_btnNew") WebElement newButton;
	
	public void login(String username, String password) throws Exception {
		uname.sendKeys(username); //txtUserName
		pass.sendKeys(password);//txtPassword
		loginButton.click(); //btnSubmit
	}

	
	public void createBooking(String getEnquiryNumber) throws InterruptedException 
	{
		Actions action = new Actions(driver);
		System.out.println("Received Enquiry Number from Database as :"+getEnquiryNumber);
		action.moveToElement(newButton);
		newButton.click();
		Thread.sleep(2000);
		driver.findElement(By.id("ctl00_cphMaster_txtRefNo")).sendKeys(getEnquiryNumber);
		Thread.sleep(1000);
		driver.findElement(By.id("ctl00_cphMaster_btnGeneratebooking")).click();
		Thread.sleep(2000);
		String message = driver.findElement(By.id("ctl00_cphMaster_MessageBox_lblMsg")).getText();
		Thread.sleep(2000);
		System.out.println(message);
		Thread.sleep(2000);
		driver.findElement(By.id("ctl00_cphMaster_MessageBox_btnOk")).click();
	}
	
	
	
}
