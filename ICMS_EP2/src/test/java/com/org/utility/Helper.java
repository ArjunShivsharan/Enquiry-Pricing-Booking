package com.org.utility;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class Helper 
{
//	public static String captureScreenShot(WebDriver driver) {
//		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//		String ScreenShotPath = System.getProperty("user.dir")+"/screenshots/a"+getCurrentDateTime()+ ".png";
//
//		try {
//			FileHandler.copy(src, new File(ScreenShotPath));
//		} catch (Exception e) {
//            System.out.println("Unable to capture ScrreShot"+e.getMessage());
//		}
//
//		return ScreenShotPath;
//	}

	public static String getCurrentDateTime() {
		DateTimeFormatter customFormat =  DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		return customFormat.format(now);
	}
}
