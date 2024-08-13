package com.website.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Reporting implements ITestListener {
	  static String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	  private static ExtentReports extentReports  = ExtentManager.createInstance(timeStamp);
	  private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	  private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	  @Override
	    public void onTestStart(ITestResult result) {
	        ExtentTest test = extentReports.createTest(result.getMethod().getMethodName());
	        extentTest.set(test);
	    }
	  @Override
	    public void onTestSuccess(ITestResult result) {
	        extentTest.get().log(Status.PASS, "Test Passed");
	    }
	  @Override
	    public void onTestFailure(ITestResult result) {
	        // Capture screenshot on test failure
	        WebDriver webDriver = driver.get();
	        if (webDriver != null) {
	            String screenshotPath = captureScreenshot(webDriver, result.getMethod().getMethodName());
	            extentTest.get().fail(result.getThrowable());
	            try {
	                extentTest.get().addScreenCaptureFromPath(screenshotPath, "Test Failure Screenshot");
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            extentTest.get().fail(result.getThrowable());
	        }
	    }

		@Override
	    public void onTestSkipped(ITestResult result) {
	        extentTest.get().log(Status.SKIP, "Test Skipped");
	    }
		 @Override
		    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		        // Optional: Implement logic for this event if needed
		    }

		    @Override
		    public void onStart(ITestContext context) {
		        // Optional: Implement logic for this event if needed
		    }

		    @Override
		    public void onFinish(ITestContext context) {
		        extentReports.flush();
		    }

		    private String captureScreenshot(WebDriver driver, String methodName) {
		        TakesScreenshot ts = (TakesScreenshot) driver;
		        File source = ts.getScreenshotAs(OutputType.FILE);
		        String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		        String screenshotPath = "target/screenshots/" + methodName + "_" + timestamp + ".png";
		        try {
		            Files.createDirectories(Paths.get("target/screenshots/"));
		            Files.copy(source.toPath(), Paths.get(screenshotPath));
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        return screenshotPath;
		    }

		    public static void setWebDriver(WebDriver driverInstance) {
		        driver.set(driverInstance);
		    }

		    public static WebDriver getWebDriver() {
		        return driver.get();
		    }

		    public static void removeWebDriver() {
		        driver.remove();
		    }

}
