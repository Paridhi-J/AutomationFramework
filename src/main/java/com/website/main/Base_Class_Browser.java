package com.website.main;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Properties;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import java.io.FileInputStream;
import java.io.IOException;

import com.website.pages.GetPages;
import com.website.utils.XLS_Reader;

public class Base_Class_Browser {

	private DriverFactory driverFactory = DriverFactory.getInstance();
	public GetPages getpages;
	public FunLibrary funLibrary;
	public SelLibrary selLibrary;
	public static String current_Dir = System.getProperty("user.dir");
	public static ThreadLocal<String> method_name = new ThreadLocal<String>();
	public static ThreadLocal<String> sheet_name = new ThreadLocal<String>();
	public Logger testLog = LogManager.getLogger("debugLogger:" + Thread.currentThread().getId());
	public WebDriver driver;
	public static ThreadLocal<Integer> currentRow = new ThreadLocal<Integer>();
	public static ThreadLocal<XLS_Reader> datatable = new ThreadLocal<XLS_Reader>();
	private static String data_file = "";
	private static String url = "";
	private String currentWindow = "";

	@BeforeClass(alwaysRun = true)
	@Parameters({ "browser", "sheetname" })
	synchronized public void beforeClass(String browser, String sheetname) {
		getProperties();
//		System.out.println("data file" + data_file);
		driverFactory.setDriver(browser);
		datatable.set(new XLS_Reader(current_Dir + "/src/main/java/" + data_file));
		
//		System.out.println("Sheetname is "+sheetname);
		sheet_name.set(sheetname);
//		System.out.println("new sheet name"+sheet_name);
		driver = driverFactory.getDriver();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method) {
		launchApplicationUrl();
		String m = method.getName();
		method_name.set(m);
		if (isTCIDFound()) {
			testLog.info("Test Started  " + method_name.get());
			getpages = new GetPages();
			funLibrary = new FunLibrary();
			selLibrary = new SelLibrary();
		} else {
			throw new SkipException("Test Case-" + method_name.get() + "is Not Present In the Sheet");
		}
		currentWindow = driver.getWindowHandle();

	}

	@AfterMethod
	public void afterMethod() {
		driver.switchTo().window(currentWindow);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
	}

	@AfterTest(alwaysRun = true)
	public void afterTest() {
		driver.quit();
		
	}

	public boolean isTCIDFound() {
	  String sheetname=sheet_name.get();
	  System.out.println("Sheetname is "+sheetname);
	  for(int i=2;i<=datatable.get().getRowCount(sheetname);i++)
	{
		if(datatable.get().getCellData(sheetname, "TestMethodName", i).equals(method_name.get())){
          testLog.info("Test case id match found");
			currentRow.set(i);
			return true;
		}
	}
	return false;
	}
	public void setDataTable() {
		datatable.set(new XLS_Reader(current_Dir+"\\src\\main\\java\\"+data_file));
	}

	
	private void getProperties() {
		// TODO Auto-generated method stub
		Properties logProperties = new Properties();
		try {
			logProperties.load(new FileInputStream(current_Dir+"/src/main/java/baseConfig.properties"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		for( String name :logProperties.stringPropertyNames()) {
			String value = logProperties.getProperty(name);
			System.setProperty(name, value);
		}
		url = System.getProperty("ENV");
		data_file = System.getProperty("DataFile");
		
	}
	public  void launchApplicationUrl() {
		// TODO Auto-generated method stub
		
		try {
			driver.get(url);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			Thread.sleep(3000);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}


}
