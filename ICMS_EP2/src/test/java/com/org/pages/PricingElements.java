package com.org.pages;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.org.utility.Helper;

public class PricingElements {
	public WebDriver driver;
	public String enq_number;
	public String enquiryNumber;

	public PricingElements(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(id = "txtusername") WebElement uname;
	@FindBy(id = "txtPassword") WebElement pass;
	@FindBy(id = "lblPOL") WebElement lblPOL;
	@FindBy(id = "txtPOD") WebElement POD;
	@FindBy(id = "btnSubmit") WebElement loginButton;
	@FindBy(className = "custom-navbar-toggler") WebElement bar;
	@FindBy(id = "searchInput") WebElement search;//
	@FindBy(id = "txtSearchValue") WebElement enqSearchtxtbox;
	@FindBy(id = "btnSearch") WebElement searchButton;
	@FindBy(xpath = "//*[@class='select2-search__field']") WebElement sendEmailtxtbox;
	@FindBy(xpath = "//*[@id='select2-ddlCarrier-results']") WebElement carrierDD;
	@FindBy(xpath = "//*[@id='select2-ddlSlotTerms-container']") WebElement containerSlotTerms;//
	@FindBy(xpath = "//*[@id='txtTransitDays']") WebElement transitDays;
	@FindBy(xpath = "//*[@id='txtRateValidity']") WebElement txtRateValidity;
	@FindBy(xpath = "//*[@id='select2-ddlRoute-container']") WebElement Route;//
	@FindBy(xpath = "//*[@aria-controls='select2-ddlRoute-results']") WebElement Routetxt;//
	@FindBy(xpath = "//*[@id='select2-ddlPricingTerm-container']") WebElement pricingTerm;
	@FindBy(xpath = "//*[@aria-controls='select2-ddlPricingTerm-results']") WebElement pricingTermtxt;
	@FindBy(xpath = "//*[@id='select2-ddlScope-container']") WebElement scope;
	@FindBy(xpath = "//*[@aria-controls='select2-ddlScope-results']") WebElement scopetxt;
	@FindBy(xpath = "//*[@data-select2-id='72']") WebElement buyingCharge; // btnFinish
	@FindBy(xpath = "//*[@id='select2-ddlBuyingChargeIncl-results']/li[1]") WebElement buyingChargeSelect;
	@FindBy(xpath = "//*[@id='btnStartPricing']") WebElement startpricingbtn;
	@FindBy(css = ".swal-button-container") WebElement okbtn;
	@FindBy(xpath = "//*[@id='btnSendMailCarrier']") WebElement sendEmail;
	@FindBy(xpath = "//*[@id='txtToMailAddress']") WebElement txtToMailAddress; //
	@FindBy(xpath = "//*[@id='txtCCMailAddress']") WebElement txtCCMailAddress;
	@FindBy(id = "btnGenerateQuote") WebElement btnGenerateQuote;
	@FindBy(id = "btnFinish") WebElement btnFinish;

	public void login(String username, String password) throws Exception {
		uname.sendKeys(username);
		pass.sendKeys(password);
		loginButton.click();
	}

	public void searchItem(String item) throws InterruptedException 
	{
		Thread.sleep(3000);

		bar.click();
		Thread.sleep(1000);
		search.sendKeys(item);

		List<WebElement> list = driver.findElements(By.id("sidebarMenu"));

		for (WebElement element : list) {
			if (element.getText().contains(item)) {
			element.findElement(By.linkText(item)).click();}
		}
	}

	public void startPricingForOneCarrier() throws Exception {

		File source = new File("./data/testdata.xlsx");
		FileInputStream fis = new FileInputStream(source);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet1 = wb.getSheetAt(0);
		enquiryNumber = sheet1.getRow(0).getCell(0).getStringCellValue();
		enqSearchtxtbox.sendKeys(enquiryNumber);
		searchButton.click();
		Thread.sleep(3000);
		JavascriptExecutor p22 = (JavascriptExecutor) driver; 
		p22.executeScript("window.scrollBy(0,600)");
		Thread.sleep(2000);   
		
		sendEmailtxtbox.click();
		Thread.sleep(1000);
		sendEmailtxtbox.sendKeys("mae");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='select2-results__options']/li")).click();
		Thread.sleep(1000);
		System.out.println("First Carrier Selected");
		Thread.sleep(1000);
		WebElement c1 = driver.findElement(By.cssSelector("a[class='nav-link icms__tab--nav-link active']"));
		Thread.sleep(1000);
		//System.out.println(c1.getAttribute("id")); //Getting ID for the selected Container
		System.out.println(c1.getText());
		driver.findElement(By.cssSelector("a[class='nav-link icms__tab--nav-link active']")).click();
		Thread.sleep(2000);
		containerSlotTerms.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@aria-controls='select2-ddlSlotTerms-results']")).sendKeys("Pre");
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		transitDays.sendKeys("15");
		
		Route.click();
		Routetxt.sendKeys("Dire");
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
	    p22.executeScript("window.scrollBy(0,300)");
		pricingTerm.click();
		pricingTermtxt.sendKeys("CYC");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		scope.click();
		scopetxt.sendKeys("own");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		Thread.sleep(1000);
		
		
		
        List<WebElement> slots = driver.findElements(By.xpath("//*[contains(@id,'txtSlotRate')]"));
		for (WebElement ele : slots) {
			if (ele.isDisplayed()) {
				ele.sendKeys("1000");
			}
		}
		List<WebElement> freightRequired = driver.findElements(By.xpath("//*[contains(@id,'txtFreightRequired')]"));

		for (WebElement ele1 : freightRequired) {
			if (ele1.isDisplayed()) {
				ele1.sendKeys("1000");
			}
		}
		startpricingbtn.click();
		Thread.sleep(1000);
		System.out.println(driver.findElement(By.className("swal-text")).getText());
		Thread.sleep(1000);
		okbtn.click();            
		Thread.sleep(1000);
		startpricingbtn.click();
		Thread.sleep(5000);
		
		//=====================================2nd Screen Starts==========================
	     p22.executeScript("window.scrollBy(0,600)");
		 Thread.sleep(6000);
		driver.findElement(By.id("btnGenerateQuote")).click();
		Thread.sleep(2000);
		System.out.println(driver.findElement(By.className("swal-text")).getText());
		Thread.sleep(2000);
		okbtn.click();  
		Thread.sleep(3000);
		driver.findElement(By.id("btnGenerateQuote")).click();
		Thread.sleep(5000);
		driver.findElement(By.id("divComparisionPopUp")).click();
		Thread.sleep(10000);
		driver.findElement(By.id("btnGenerateFinalQuote")).click();
		Thread.sleep(3000);
		p22.executeScript("window.scrollBy(0,800)");
		Thread.sleep(7000);
		driver.findElement(By.cssSelector("div[class='note-editable card-block']")).sendKeys("TEST Comment added at  "+Helper.getCurrentDateTime());
		Thread.sleep(2000);
		driver.findElement(By.id("btnFinish")).click();
		Thread.sleep(2000);
		System.out.println(driver.findElement(By.className("swal-text")).getText());
		Thread.sleep(2000);
		okbtn.click();
		Thread.sleep(1000);
		System.out.println("Final Quotation done successfully for one carriers at "+Helper.getCurrentDateTime());
		driver.findElement(By.id("txtToAddress")).clear();
		Thread.sleep(500);
		driver.findElement(By.id("txtToAddress")).sendKeys("mallikarjun.shivsharan@sharpitech.in");
		Thread.sleep(500);
		driver.findElement(By.id("txtCCAddress")).clear();
		Thread.sleep(500);
		driver.findElement(By.id("txtCCAddress")).sendKeys("mallikarjun.shivsharan@sharpitech.in");
		Thread.sleep(1000);
		driver.findElement(By.id("btnSendQuoteMail")).click();
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("swal-text")));
		Thread.sleep(1000);
		System.out.println(driver.findElement(By.className("swal-text")).getText());
		Thread.sleep(1000);
		okbtn.click();
    }
	
	//===========================================================================================================================
	
	public void startPricingForTwoCarrier() throws Exception 
	{
		System.out.println("from startPricingForTwoCarrier");
		Actions actions = new Actions(driver);// for scrolling up and down
		
		File source = new File("./data/testdata.xlsx");
		FileInputStream fis = new FileInputStream(source);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet1 = wb.getSheetAt(0);
		enquiryNumber = sheet1.getRow(0).getCell(0).getStringCellValue();
		enqSearchtxtbox.sendKeys(enquiryNumber);
		searchButton.click();
		Thread.sleep(3000);
		JavascriptExecutor p22 = (JavascriptExecutor) driver; 
		p22.executeScript("window.scrollBy(0,600)");
		Thread.sleep(2000);   
		
		sendEmailtxtbox.click();
		Thread.sleep(1000);
		sendEmailtxtbox.sendKeys("mae"); //Carrier 1
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='select2-results__options']/li")).click();
		Thread.sleep(1000);
		System.out.println("First Carrier Selected");
		Thread.sleep(1000);
		
		sendEmailtxtbox.sendKeys("cma");// Carrier 2
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='select2-results__options']/li")).click();
		Thread.sleep(1000);
		System.out.println("Second Carrier Selected");
		
		
		Thread.sleep(1000);
		containerSlotTerms.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@aria-controls='select2-ddlSlotTerms-results']")).sendKeys("Pre");
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		transitDays.sendKeys("15");
		txtRateValidity.clear();
		txtRateValidity.click();
		driver.findElement(By.cssSelector("td[class='today day']")).click();
		Route.click();
		Routetxt.sendKeys("Dire");
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
	    p22.executeScript("window.scrollBy(0,300)");
		pricingTerm.click();
		pricingTermtxt.sendKeys("CYC");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		scope.click();
		scopetxt.sendKeys("own");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		
		Thread.sleep(2000);
		
		
        List<WebElement> slots = driver.findElements(By.xpath("//*[contains(@id,'txtSlotRate')]"));
		for (WebElement ele : slots) {
			if (ele.isDisplayed()) {
				ele.sendKeys("1000");
			}
		}
		List<WebElement> freightRequired = driver.findElements(By.xpath("//*[contains(@id,'txtFreightRequired')]"));

		for (WebElement ele1 : freightRequired) {
			if (ele1.isDisplayed()) {
				ele1.sendKeys("1000");
			}
		}
		startpricingbtn.click();
		Thread.sleep(1000);
		System.out.println(driver.findElement(By.className("swal-text")).getText());
		Thread.sleep(1000);
		
        driver.findElement(By.cssSelector("button[class='swal-button swal-button--cancel']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[class='nav-link icms__tab--nav-link']")));
        
		JavascriptExecutor cursor = (JavascriptExecutor) driver; 
		cursor.executeScript("window.scrollBy(0,-600)");
		Thread.sleep(2000);
		//2nd carrier
		
		driver.findElement(By.cssSelector("a[class='nav-link icms__tab--nav-link']")).click();
		Thread.sleep(2000);
		
		containerSlotTerms.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@aria-controls='select2-ddlSlotTerms-results']")).sendKeys("Pre");
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		transitDays.sendKeys("15");
		txtRateValidity.clear();
		txtRateValidity.click();
		driver.findElement(By.cssSelector("td[class='today day']")).click();
		Route.click();
		Routetxt.sendKeys("Dire");
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
	    p22.executeScript("window.scrollBy(0,300)");
		pricingTerm.click();
		pricingTermtxt.sendKeys("CYC");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		scope.click();
		scopetxt.sendKeys("own");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		Thread.sleep(1000);
		
	//=============================================================================
		List<WebElement> slots1 = driver.findElements(By.xpath("//*[contains(@id,'txtSlotRate')]"));
		for (WebElement ele1 : slots1) {
			if (ele1.isDisplayed()) {
				ele1.sendKeys("1500");
			}
		}
		List<WebElement> freightRequired1 = driver.findElements(By.xpath("//*[contains(@id,'txtFreightRequired')]"));

		for (WebElement ele2 : freightRequired1) {
			if (ele2.isDisplayed()) {
				ele2.sendKeys("1500");
			}
		}
		
	//======================================================	
		
		startpricingbtn.click();
		Thread.sleep(1000);
		System.out.println(driver.findElement(By.className("swal-text")).getText());
		Thread.sleep(1000);
		okbtn.click();
		Thread.sleep(2000);
		startpricingbtn.click();
		
		//=====================================2nd Screen Starts==========================
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnChangeExRate")));
	
		Thread.sleep(7000);
		
		actions.moveToElement(driver.findElement(By.id("btnGenerateQuote")));
		actions.perform();
		
		Thread.sleep(3000);
		driver.findElement(By.id("btnGenerateQuote")).click();
		Thread.sleep(2000);
		System.out.println(driver.findElement(By.className("swal-text")).getText());
		Thread.sleep(1000);
		okbtn.click();
		Thread.sleep(3000);
		WebElement elementt = driver.findElement(By.id("txtRemarks"));
		
		actions.moveToElement(elementt);
		actions.perform();
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("a[class='nav-link icms__tab--nav-link']")).click();
		Thread.sleep(7000);

		actions.moveToElement(driver.findElement(By.id("btnGenerateQuote")));
		actions.perform();
	    Thread.sleep(3000);
		driver.findElement(By.id("btnGenerateQuote")).click();
		Thread.sleep(3000);
		
		Thread.sleep(2000);
		System.out.println(driver.findElement(By.className("swal-text")).getText());
		Thread.sleep(2000);
		okbtn.click();
		Thread.sleep(2000);
		
		driver.findElement(By.id("btnGenerateQuote")).click();
		Thread.sleep(5000);
		driver.findElement(By.id("divComparisionPopUp")).click();
		
		Thread.sleep(10000);
		
		driver.findElement(By.xpath("//*[@id='btnGenerateFinalQuote']")).click();
		Thread.sleep(3000);
		p22.executeScript("window.scrollBy(0,800)");
		Thread.sleep(7000);
		driver.findElement(By.cssSelector("div[class='note-editable card-block']")).sendKeys("TEST Comment added at  "+Helper.getCurrentDateTime());
		Thread.sleep(2000);
		driver.findElement(By.id("btnFinish")).click();
		Thread.sleep(2000);
		System.out.println(driver.findElement(By.className("swal-text")).getText());
		Thread.sleep(1000);
		okbtn.click();
		Thread.sleep(1000);
		System.out.println("Final Quotation done successfully for two carriers at "+Helper.getCurrentDateTime());
		driver.findElement(By.id("txtToAddress")).clear();
		driver.findElement(By.id("txtToAddress")).sendKeys("mallikarjun.shivsharan@sharpitech.in");
		driver.findElement(By.id("txtCCAddress")).clear();
		driver.findElement(By.id("txtCCAddress")).sendKeys("mallikarjun.shivsharan@sharpitech.in");
		Thread.sleep(1000);
		driver.findElement(By.id("btnSendQuoteMail")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("swal-text")));
		System.out.println(driver.findElement(By.className("swal-text")).getText());
		Thread.sleep(1000);
		okbtn.click();
		
				
	}
	
	//==========================================================================3==================
	public void startPricingForThreeCarrier() throws Exception 
	{
		System.out.println("from startPricingForThreeCarrier");
		
		File source = new File("./data/testdata.xlsx");
		FileInputStream fis = new FileInputStream(source);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet1 = wb.getSheetAt(0);
		
		enquiryNumber = sheet1.getRow(0).getCell(0).getStringCellValue();
		enqSearchtxtbox.sendKeys(enquiryNumber);
		searchButton.click();
		Thread.sleep(3000);
		JavascriptExecutor p22 = (JavascriptExecutor) driver; 
		p22.executeScript("window.scrollBy(0,600)");
		Thread.sleep(2000);   
		sendEmailtxtbox.click();
		Thread.sleep(2000);
		
		//First Carrier
		String fc = sheet1.getRow(8).getCell(5).getStringCellValue();
		System.out.println(fc);
		sendEmailtxtbox.sendKeys("mae"); //carrier 1
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@class='select2-results__options']/li")).click();
		System.out.println("First Carrier Selected");
		
		
		//Second Carrier
		sendEmailtxtbox.sendKeys("cma"); //Carrier 2
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@class='select2-results__options']/li")).click();
		Thread.sleep(1000);
		System.out.println("Second Carrier Selected");
		
		//Third Carrier
		sendEmailtxtbox.sendKeys("oocl"); //Carrier 3
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@class='select2-results__options']/li")).click();
		System.out.println("Third Carrier Selected");
		
		
		Thread.sleep(1000);
		containerSlotTerms.click();
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//*[@aria-controls='select2-ddlSlotTerms-results']")).sendKeys("Pre");
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		transitDays.sendKeys("15");
		txtRateValidity.clear();
		txtRateValidity.click();
		driver.findElement(By.cssSelector("td[class='today day']")).click();
		Route.click();
		Routetxt.sendKeys("Dire");
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
	    p22.executeScript("window.scrollBy(0,300)");
		pricingTerm.click();
		pricingTermtxt.sendKeys("CYC");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		scope.click();
		scopetxt.sendKeys("own");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		
		Thread.sleep(2000);
		
		
        List<WebElement> slots = driver.findElements(By.xpath("//*[contains(@id,'txtSlotRate')]"));
		for (WebElement ele : slots) {
			if (ele.isDisplayed()) {
				ele.sendKeys("1000");
			}
		}
		List<WebElement> freightRequired = driver.findElements(By.xpath("//*[contains(@id,'txtFreightRequired')]"));

		for (WebElement ele1 : freightRequired) {
			if (ele1.isDisplayed()) {
				ele1.sendKeys("1000");
			}
		}
		startpricingbtn.click();
		Thread.sleep(1000);
		System.out.println(driver.findElement(By.className("swal-text")).getText());
		
		Thread.sleep(1000);
        driver.findElement(By.cssSelector("button[class='swal-button swal-button--cancel']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[class='nav-link icms__tab--nav-link']")));
        
		JavascriptExecutor cursor = (JavascriptExecutor) driver; 
		cursor.executeScript("window.scrollBy(0,-600)");
		Thread.sleep(2000);

		driver.findElement(By.cssSelector("a[class='nav-link icms__tab--nav-link']")).click();
		Thread.sleep(2000);
		
		containerSlotTerms.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@aria-controls='select2-ddlSlotTerms-results']")).sendKeys("Pre");
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		transitDays.sendKeys("15");
		txtRateValidity.clear();
		txtRateValidity.click();
		driver.findElement(By.cssSelector("td[class='today day']")).click();
		Route.click();
		Routetxt.sendKeys("Dire");
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
	    p22.executeScript("window.scrollBy(0,300)");
		pricingTerm.click();
		pricingTermtxt.sendKeys("CYC");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		scope.click();
		scopetxt.sendKeys("own");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		Thread.sleep(1000);
		

        List<WebElement> slots3 = driver.findElements(By.xpath("//*[contains(@id,'txtSlotRate')]"));
		for (WebElement ele3 : slots3) {
			if (ele3.isDisplayed()) {
				ele3.sendKeys("2344");
			}
		}
		List<WebElement> freightRequired3 = driver.findElements(By.xpath("//*[contains(@id,'txtFreightRequired')]"));

		for (WebElement ele3 : freightRequired3) {
			if (ele3.isDisplayed()) {
				ele3.sendKeys("2300");
			}
		}
		
		startpricingbtn.click();
		Thread.sleep(1000);
		System.out.println(driver.findElement(By.className("swal-text")).getText());
		Thread.sleep(1000);
		
		Thread.sleep(1000);
        driver.findElement(By.cssSelector("button[class='swal-button swal-button--cancel']")).click();
        WebDriverWait wait1 = new WebDriverWait(driver, 120);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[class='nav-link icms__tab--nav-link']")));
        
		JavascriptExecutor cursor2 = (JavascriptExecutor) driver; 
		cursor2.executeScript("window.scrollBy(0,-600)");
		Thread.sleep(2000);

		driver.findElement(By.cssSelector("a[class='nav-link icms__tab--nav-link']")).click();
		Thread.sleep(2000);
		
		containerSlotTerms.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@aria-controls='select2-ddlSlotTerms-results']")).sendKeys("Pre");
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		transitDays.sendKeys("15");
		txtRateValidity.clear();
		txtRateValidity.click();
		driver.findElement(By.cssSelector("td[class='today day']")).click();
		Route.click();
		Routetxt.sendKeys("Dire");
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();

		pricingTerm.click();
		pricingTermtxt.sendKeys("CYC");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		scope.click();
		scopetxt.sendKeys("own");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='select2-results']/ul")).click();
		Thread.sleep(1000);
		
        List<WebElement> slots4 = driver.findElements(By.xpath("//*[contains(@id,'txtSlotRate')]"));
		for (WebElement ele4 : slots4) {
			if (ele4.isDisplayed()) {
				ele4.sendKeys("1000");
			}
		}
		List<WebElement> freightRequired4 = driver.findElements(By.xpath("//*[contains(@id,'txtFreightRequired')]"));

		for (WebElement ele4 : freightRequired4) {
			if (ele4.isDisplayed()) {
				ele4.sendKeys("1000");
			}
		}
		
	    startpricingbtn.click();
		Thread.sleep(1000);
		System.out.println(driver.findElement(By.className("swal-text")).getText());
		Thread.sleep(1000);
		okbtn.click();
		Thread.sleep(3000);
		startpricingbtn.click();

		//2nd screen============================
		Actions actions = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnChangeExRate")));
		Thread.sleep(7000);
		actions.moveToElement(driver.findElement(By.id("btnGenerateQuote")));
		actions.perform();
		//p22.executeScript("window.scrollBy(0,6000)");
		Thread.sleep(3000);
		driver.findElement(By.id("btnGenerateQuote")).click();
		Thread.sleep(2000);
		System.out.println(driver.findElement(By.className("swal-text")).getText());
		Thread.sleep(2000);
		okbtn.click();
		Thread.sleep(2000);
		//select 2nd carrier
		WebElement elementt = driver.findElement(By.id("txtRemarks"));
		
		actions.moveToElement(elementt);
		actions.perform();
		Thread.sleep(1000);
		driver.findElement(By.cssSelector("a[class='nav-link icms__tab--nav-link']")).click();
		Thread.sleep(7000);


		actions.moveToElement(driver.findElement(By.id("btnGenerateQuote")));
		actions.perform();
		    Thread.sleep(3000);
			driver.findElement(By.id("btnGenerateQuote")).click();
			
			Thread.sleep(2000);
			System.out.println(driver.findElement(By.className("swal-text")).getText());
			Thread.sleep(2000);
			
			okbtn.click();
			Thread.sleep(3000);
			
			actions.moveToElement(elementt);
			actions.perform();
			
			Thread.sleep(3000);
			driver.findElement(By.cssSelector("a[class='nav-link icms__tab--nav-link']")).click();
			Thread.sleep(7000);

				//p22.executeScript("window.scrollBy(0,6000)");
				actions.moveToElement(driver.findElement(By.id("btnGenerateQuote")));
				actions.perform();
				
			    Thread.sleep(3000);
				driver.findElement(By.id("btnGenerateQuote")).click(); //driver.findElement(By.id("btnGenerateQuote"))
				Thread.sleep(2000);
				System.out.println(driver.findElement(By.className("swal-text")).getText());
				Thread.sleep(2000);
			    okbtn.click();
			    Thread.sleep(3000);
				
				
			driver.findElement(By.id("btnGenerateQuote")).click();
			Thread.sleep(5000);
			driver.findElement(By.id("divComparisionPopUp")).click();
			
			Thread.sleep(10000);
			
			driver.findElement(By.xpath("//*[@id='btnGenerateFinalQuote']")).click();
			Thread.sleep(3000);
			p22.executeScript("window.scrollBy(0,800)");
			Thread.sleep(7000);
			driver.findElement(By.cssSelector("div[class='note-editable card-block']")).sendKeys("TEST Comment added at  "+Helper.getCurrentDateTime());
			Thread.sleep(2000);
			driver.findElement(By.id("btnFinish")).click();
			Thread.sleep(2000);
			System.out.println(driver.findElement(By.className("swal-text")).getText());
			Thread.sleep(2000);
			okbtn.click();
	        Thread.sleep(1000);
			System.out.println("Final Quotation done successfully for two carriers at "+Helper.getCurrentDateTime());
			driver.findElement(By.id("txtToAddress")).clear();
			Thread.sleep(1000);
			driver.findElement(By.id("txtToAddress")).sendKeys("mallikarjun.shivsharan@sharpitech.in");
			Thread.sleep(1000);
			driver.findElement(By.id("txtCCAddress")).clear();
			Thread.sleep(1000);
			driver.findElement(By.id("txtCCAddress")).sendKeys("mallikarjun.shivsharan@sharpitech.in");
			Thread.sleep(1000);
			driver.findElement(By.id("btnSendQuoteMail")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("swal-text")));
			Thread.sleep(1000);
			System.out.println(driver.findElement(By.className("swal-text")).getText());
			Thread.sleep(1000);
			okbtn.click();
			
		
	}
}























