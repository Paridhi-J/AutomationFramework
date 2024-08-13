package com.website.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.website.utils.XLS_Reader;

public class SelLibrary {
	public WebDriver driver;
	public Properties OR_OR;
	public Logger testLog= LogManager.getLogger("debugLogger:"+ Thread.currentThread().getId());
	public XLS_Reader datatable= null;
	public int status;
	public int random_bag_flag=-1;
	public int currentRow=0;
	public String sheet_name="";
	private DriverFactory driverFactory=DriverFactory.getInstance();
	WebDriverWait wait;
	public SelLibrary() {
		this.driver=driverFactory.getDriver();
		this.datatable=Base_Class_Browser.datatable.get();
		this.currentRow= Base_Class_Browser.currentRow.get();
		this.sheet_name=Base_Class_Browser.sheet_name.get();
		this.wait= new WebDriverWait(driver, Duration.ofSeconds(10));
		load_Obj_Repository();
	}
	
	
	
	
	
	public void load_Obj_Repository() {
		try {
			OR_OR= new Properties();
			FileInputStream fp=new FileInputStream(Base_Class_Browser.current_Dir+"/src/main/java/configlib/browser_xpath.properties");
		OR_OR.load(fp);
		}catch(IOException e)
		{
			
			testLog.error("Failed to load object repository"+ e.toString());
			e.printStackTrace();
			
		}
	}
	public void  assertCheck(String methodname,String errormsg)
	{
		Assert.assertEquals(1, 0,"MethodName:"+methodname+",Errormsg:"+errormsg);
	}
	public void  assertCheck(String methodname,String errormsg,String ObjName)
	{
		Assert.assertEquals(1, 0,"MethodName:"+methodname+",Errormsg:"+errormsg+",Object:"+ObjName);
	}
	public void  assertCheck(String methodname,String errormsg,String ObjName,Exception e)
	{
		Assert.assertEquals(1, 0,"MethodName:"+methodname+",Errormsg:"+errormsg+",Object:"+ObjName+",Exception:"+e);
	}
	public void ExplicitWait(String xpath, String ObjName,int time) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(time));
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			testLog.info("ExplicitWait Element Present:"+ ObjName);
			
		}
		catch(Exception e) {
			testLog.error("element not present"+ObjName);
			assertCheck("ExplicitWait","Element not present",ObjName,e);
		}
	}
	public void wait(int time) {
		try {
			Thread.sleep(time);
		}
		catch(InterruptedException e) {
			assertCheck("wait","Opps!! Error during waiting","",e);
		}
	}
	public void Click_Button_Xpath(String xpath, String ObjName) {
		try {
			wait(2000);
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			driver.findElement(By.xpath(xpath)).click();
			testLog.info("Element is found and clicked: "+ObjName);
			
		}
		catch(Exception ex) {
			try {
				wait(2000);
				WebElement element1 = driver.findElement(By.xpath(xpath));
				int x= element1.getLocation().getX();
				int y = element1.getLocation().getY();
				int division = 1;
				wait(2000);
				((JavascriptExecutor)driver).executeScript("window.scrollTo("+x /division +","+ y / division +")","");
				testLog.info("Scrolled To "+ ObjName);
				wait(3000);
				WebElement element = driver.findElement(By.xpath(xpath));
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
				testLog.info("Element is found and clicked: "+ObjName);
				
				
			}
			catch(Exception e) {
				testLog.error("element not found for clicking "+ObjName);
				assertCheck("Click_Button_Xpath","element not found for clicking",ObjName,e);
			}
			
		}
	}
	public void Clear_Text(String xpath,String ObjName) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			driver.findElement(By.xpath(xpath)).clear();
			testLog.info("Text Clear for :"+ObjName);
			
			
			
			
		}
		catch(Exception e) {
			testLog.error("Failed to clear text for"+ObjName);
			
			assertCheck("Clear_Text","Not able to clear field",ObjName,e);
			
		}
	}
	
	public void Sendkey_xpath(String xpath,String variable,String ObjName) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			driver.findElement(By.xpath(xpath)).clear();
			wait(1000);
			driver.findElement(By.xpath(xpath)).sendKeys(variable);
			testLog.info("Data entered for text box:"+ObjName);
			
			
		}
		catch(Exception e) {
			testLog.error("Text "+variable+" not entered: "+ObjName);
			assertCheck("Sendkey_xpath","Not able to enter text",ObjName,e);
			
		}
	}
	

}
