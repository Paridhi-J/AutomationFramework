package com.website.main;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	private static String current_Dir=System.getProperty("user.dir");
	private static DriverFactory instance =null;
	public static ThreadLocal<WebDriver> driver= new ThreadLocal<WebDriver>();
	private static String firefox_exe;
	private static String chrome_exe;
	//constructor creation
	private DriverFactory()
	{
	
	}
	public static DriverFactory getInstance()
	{
		if (instance==null)
		{
			instance =new DriverFactory();
		}
		return instance;
	}
	public WebDriver getDriver()
	{
		return driver.get();
		}
	public void  setDriver(String browser)
	{
		
	firefox_exe= System.getProperty("FIREFOX_EXE");
	chrome_exe= System.getProperty("CHROME_EXE");
	try {
		switch(browser)
		{
		case "Chrome":
			System.getProperty(current_Dir+ "/src/main/java.configlib/log4j_Chrome.properties");
			Properties chromeLogProperties= new Properties();	
				chromeLogProperties.load(new FileInputStream(current_Dir+ "/src/main/java/configlib/log4j_Chrome.properties"));
//		PropertyConfigurator.configure(chromeLogProperties);
				System.setProperty("webdriver.chrome.driver", chrome_exe);
//				WebDriverManager.chromedriver().setup();
				driver.set(new ChromeDriver());
				getDriver().manage().window().maximize();
				break;
				
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		}
		
	
		 



}
