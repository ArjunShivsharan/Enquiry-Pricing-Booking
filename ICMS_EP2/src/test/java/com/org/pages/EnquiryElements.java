package com.org.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EnquiryElements 
{
	public WebDriver driver;
	public String enq_number;
	
	public EnquiryElements(WebDriver driver) 
	{
		this.driver = driver;
	}
	
	@FindBy(id = "txtusername") WebElement uname;
	@FindBy(id = "txtPassword") WebElement pass;
	@FindBy(id = "btnSubmit")   WebElement loginButton;
	@FindBy(className = "custom-navbar-toggler") WebElement bar;
	@FindBy(id = "searchInput") WebElement search;
	@FindBy(id = "txtPOL")      WebElement POL;
	@FindBy(id = "txtPOD")      WebElement POD;
	@FindBy(id = "txtBusinessPort")  WebElement BusinessPort;
	@FindBy(id = "txtBusinessAgent") WebElement BookingForwarderAgent;
	@FindBy(id = "txtReadynessCargoDate") WebElement DateofReadyness;
	@FindBy(id = "select2-ddlCS-container") WebElement CustomerServiceName;
	@FindBy(id = "txtPlaceOfReceipt") WebElement PlaceofReceipt;
	@FindBy(id = "txtShipper")  WebElement ShipperName;
	@FindBy(id = "txtEmailId")  WebElement Email;
	@FindBy(id = "txtFreedaysExpGP") WebElement POLGPReqFreeDays;
	@FindBy(id = "txtFreedaysExpSP") WebElement POLSPReqFreeDays;
	@FindBy(id = "txtFreedaysImpGP") WebElement PODGPReqFreeDays;
	@FindBy(id = "txtFreedaysImpSP") WebElement PODSPReqFreeDays; //
	@FindBy(id = "DocumentUpload")   WebElement remarks;
	@FindBy(id = "txtRemarks") WebElement txtRemarks;
	@FindBy(id = "txtProduct") WebElement product;
	@FindBy(id = "txtLength")  WebElement length;
	@FindBy(id = "txtWidth")   WebElement width;
	@FindBy(id = "txtHeight")  WebElement height;
	@FindBy(id = "txtWeight")  WebElement weight;
	@FindBy(id = "txtQty")     WebElement Qty;
	@FindBy(id = "select2-ddlContainerSize-container") WebElement containerSize;
	@FindBy(id = "select2-ddlContainerType-container") WebElement containerType;
	@FindBy(id = "select2-ddlDimension-container") WebElement containerDimension;
	@FindBy(id = "txtIndication") WebElement indication;
	@FindBy(id = "btnAdd") WebElement addButton;
	@FindBy(id = "select2-ddlUOM-container") WebElement UOM;
	@FindBy(id = "btnSave") WebElement submitButton;
	@FindBy(css =".icmsPage-newLogin__overlay--box-title") WebElement xDeclinePage;
	@FindBy(css =".icmsPage-newLogin__overlay--box") WebElement yDeclinePage;
	@FindBy(css =".icmsPage-newLogin__overlay--box-declined active") WebElement zDeclinePage;

	

	
	public void login(String username, String password) throws Exception 
	{
		uname.clear();
		uname.sendKeys(username); 
		pass.clear();
		pass.sendKeys(password);
		loginButton.click();
	}
	
	//This method will search "Freight Request" in search bar
	
	public void searchItem(String item) throws InterruptedException 
	{
		bar.click();
		search.sendKeys(item);
	    List<WebElement> list = driver.findElements(By.id("sidebarMenu"));
		for (WebElement element : list) 
			{
				if (element.getText().contains(item)) { element.findElement(By.linkText(item)).click();}
			}
		}
	
	public void newInquiryPunch() throws Exception 
	{
		File src = new File("./data/DataSheetICMS.xlsx");
		FileInputStream fis1 = new FileInputStream(src);
		XSSFWorkbook wb1 = new XSSFWorkbook(fis1);
		XSSFSheet s1 =wb1.getSheetAt(0); 
				
		String polFromExcel = s1.getRow(1).getCell(1).getStringCellValue();  // Reading POL from excel
		POL.clear();
		POL.sendKeys(polFromExcel);
		Thread.sleep(1000);
		List<WebElement> listOfPOL =driver.findElements(By.xpath("//*[@id='ui-id-2']/li"));
		int polcount1 =0;
	    String polstring = polFromExcel;
	    for(int i = 0; i < polstring.length(); i++) // Length of excel POL
		   { 
			    if(polstring.charAt(i) != ' ')    
				polcount1++; 
		   }    
		for(WebElement singlePOL : listOfPOL) 
		{
			int polCount2 = 0;
			String string = singlePOL.getText();
			for(int i = 0; i < string.length(); i++)  // Length of nearest POL
				{    
	            if(string.charAt(i) != ' ')    
	            polCount2++;    
	            }    
					
			int x = polCount2 - polcount1;
			if(x <=4) 
			{singlePOL.click();
			Thread.sleep(1000);
		
			break;}
		}
		//============================end of logic for POL=======================
		
		//============================start of logic for POD=======================
		
		String podFromExcel = s1.getRow(2).getCell(1).getStringCellValue();// POD from Excel
		POD.clear();
		POD.sendKeys(podFromExcel);
		Thread.sleep(2000);
		List<WebElement> listOfPOD =driver.findElements(By.xpath("//*[@id='ui-id-3']/li"));
		int podCount1 =0;
	    String podstring = podFromExcel;
	    for(int i = 0; i < podstring.length(); i++) // Length of excel POD
		   { 
			    if(podstring.charAt(i) != ' ')    
				podCount1++; 
		   }    
			
		for(WebElement singlePOD : listOfPOD) 
		{
			int podCount2 = 0;
			String string2 = singlePOD.getText();
			for(int i = 0; i < string2.length(); i++) // Length of nearest POD
			{    
	            if(string2.charAt(i) != ' ')    
	            	podCount2++;    
	        }    
					
			int x = podCount2 - podCount1;
			if(x <=4) 
			{singlePOD.click(); break;}
		}
		
	//============================End of logic for POD=======================
	
	    BusinessPort.clear();
		BusinessPort.sendKeys("JNPT");
	    Thread.sleep(1000);
	    
		driver.findElement(By.xpath("//*[@id='Business-container']/ul")).click();
		BookingForwarderAgent.clear();
		Thread.sleep(1000);
		BookingForwarderAgent.sendKeys("Sarjak Container Lines Private Limited");
		Thread.sleep(3000);
		
		
		// Customer Service drop down
		   try {
			
			driver.findElement(By.xpath("//*[@id='select2-ddlCS-container']")).click();
			driver.findElement(By.xpath("//input[@class='select2-search__field']")).sendKeys("Kac");
			driver.findElement(By.cssSelector("span[class='select2-results']")).click();
			
		} catch (Exception e1) 
		
		{
		    System.out.println("Customer Service Auto Extender is not working.");
		}

				// Sales Person drop down
				try {
					driver.findElement(By.xpath("//*[@id='select2-ddlSalesPerson-container']")).click(); 
					driver.findElement(By.xpath("//span[@dir='ltr']/span/input")).sendKeys("Mita"); 
					driver.findElement(By.cssSelector("span[class='select2-results']")).click();
				} catch (Exception e1) 
				{
					System.out.println("Sales Person Auto Extender is not working.");
				}

				PlaceofReceipt.clear();
				PlaceofReceipt.sendKeys("Nhava Sheva,IN"); 
				ShipperName.sendKeys("DHL");
				driver.findElement(By.xpath("//*[@id='Shipper-container']/ul/li[6]")).click(); 
				Email.click(); Thread.sleep(1500);
				Email.sendKeys("mallikarjun.shivsharan@sharpitech.in");
				POLGPReqFreeDays.sendKeys("4");
				POLSPReqFreeDays.sendKeys("6");
				PODGPReqFreeDays.sendKeys("2");
				PODSPReqFreeDays.sendKeys("3");
				remarks.click();
				txtRemarks.sendKeys("This is remark which CS ENTERS when entering the enquiry in the system...!");
				JavascriptExecutor j = (JavascriptExecutor) driver;
				j.executeScript("window.scrollBy(0,1200)");
				
				//*************************ADD PRODUCT****************************
							
				DataFormatter df = new DataFormatter();
				XSSFCell cell1 = s1.getRow(3).getCell(1);
				String productCount = df.formatCellValue(cell1);
				int pc=Integer.parseInt(productCount);
							
				for(int i=1;i<=pc;i++) {
				
				product.sendKeys("Product"+i);
				length.sendKeys("1"+i*20);
				width.sendKeys("1"+i*10);
				height.sendKeys("1"+i*15);
				weight.sendKeys("1"+i*50);
				Qty.sendKeys("1");
				containerSize.click();
				//Size
				int size = (int) s1.getRow(1).getCell(4).getNumericCellValue();
			    driver.findElement(By.xpath("//*[@aria-controls='select2-ddlContainerSize-results']")).sendKeys(String.valueOf(size));
				List<WebElement> w = driver.findElements(By.xpath("//*[@id='select2-ddlContainerSize-results']"));
				for (WebElement ele : w) 
				{
					ele.click();
				}
				containerType.click();
				//Type
				driver.findElement(By.xpath("//*[@class='select2-search__field']")).sendKeys(s1.getRow(2).getCell(4).getStringCellValue());
				List<WebElement> type = driver.findElements(By.xpath("//*[@id='select2-ddlContainerType-results']"));
				for (WebElement e : type) 
				{
					e.click();
				}
				containerDimension.click();
				//Dimensions
				driver.findElement(By.xpath("//*[@aria-controls='select2-ddlDimension-results']")).sendKeys(s1.getRow(3).getCell(5).getStringCellValue());
				List<WebElement> dimension = driver.findElements(By.xpath("//*[@id='select2-ddlDimension-results']"));
				for (WebElement e : dimension) 
				{
					e.click();
				}
				indication.sendKeys("2000");
				JavascriptExecutor j1 = (JavascriptExecutor) driver;
				j1.executeScript("window.scrollBy(1200,0)");
				addButton.click();
				}
		       // ******************************Product Added**********************
				
				JavascriptExecutor j5 = (JavascriptExecutor) driver;
				j5.executeScript("window.scrollBy(0,600)");
				submitButton.click();
				Thread.sleep(2000);
				String enqNumber = driver.findElement(By.className("swal-text")).getText();
				String line = enqNumber;
		        String regex = "[^\\d]+"; // This will extract NUMBERS from String
		        String[] str = line.split(regex);
		        
		        for(String st: str)
		        {
		           enq_number = st;
		        }
		        System.out.println("Enquiry Generated Successfully: "+enq_number);
		        //*****Below Code in Try Catch Block is to send Inquiry ID to Excel, SO that we can use that for Pricing********
		        try 
		    	{
		        	Thread.sleep(100);
		        	String filePath = "./data/testdata.xlsx";
		        	Thread.sleep(100);
		    		File source = new File(filePath);
		    		Thread.sleep(100);
		    		FileInputStream fis = new FileInputStream(source);
		    		Thread.sleep(100);
		    		XSSFWorkbook wb = new XSSFWorkbook(fis);
		    		Thread.sleep(100);
		    		XSSFSheet sheet1 = wb.getSheet("Sheet1");
		    		Thread.sleep(100);
		    		Row row = sheet1.createRow(0);
		    		Thread.sleep(100);
		    		Cell cell = row.createCell(0);
		    		Thread.sleep(100);
		    		cell.setCellValue(enq_number);
		    		Thread.sleep(100);
		    		FileOutputStream fos = new FileOutputStream(filePath);
		    		Thread.sleep(100);
		    		wb.write(fos);
		    		Thread.sleep(100);
		    		fos.close();
		    		
		    	} 
		    	catch (Exception e) 
		        {
		    		System.out.println("Not able to Load Excel File : "+e.getMessage());
		    	}
		        driver.findElement(By.cssSelector(".swal-button-container")).click();
		        
		        Thread.sleep(10000);
		     }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
