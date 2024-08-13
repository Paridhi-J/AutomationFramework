package com.website.utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.website.main.Base_Class_Browser;

public class Retry extends Base_Class_Browser implements IRetryAnalyzer {
	private int count = 0;
	private static int maxTry = 2;

	@Override
	public boolean retry(ITestResult iTestResult) {

		if (!iTestResult.isSuccess()) // check if test not succeed
		{
			if (count < maxTry) { // check if maxTry count is reached
				count++; // Increase the maxTry count by 1
				iTestResult.setStatus(ITestResult.FAILURE); // Mart Test as failure and take Bas64screenshot
//				try {
//					extendReportsFailOperationss(iTestResult);
//									}catch(Exception e) {
//										e.printStackTrace();
//										//ExtendReport fail operations
//									}
				return true;
			}
		} else {
			iTestResult.setStatus(ITestResult.SUCCESS); // if test passes ,TestNG marks it as pass
		}
		return false;
	}
	public void extendReportsFailOperations(ITestResult result) throws Exception{
		ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
		//Object testClass= result.getInstance();
		TakesScreenshot newScreen=(TakesScreenshot)driver;
		File scnShot= newScreen.getScreenshotAs(OutputType.FILE);
		String screenshotPath= System.getProperty("user.dir")+"\\ExtentReports\\Screenshot\\"+ result.getName()
		+".png";
		File DestFile= new File(screenshotPath);
		FileUtils.copyFile(scnShot, DestFile);
		test.get().fail(MarkupHelper.createLabel(result.getName()+"Test Case Failed due to below issues:",
				ExtentColor.RED));
		test.get().fail(result.getThrowable());
		test.get().fail("Snapshot below: ").addScreenCaptureFromPath(screenshotPath);
	}
	

}
