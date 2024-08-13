package com.website.tests;

import org.testng.annotations.Test;

import com.website.main.Base_Class_Browser;

public class LoginTests extends Base_Class_Browser{
	@Test(description="WAP_AUTO01 - Verify user logged in successfully with valid user")
	public void validateUserLoginWithValidCredentials() {
		getpages.getLandingPage().clickLoginSignup().enterUserName(funLibrary.username).
		enterPassword(funLibrary.password).clickLogin();
		
		
		
	}

}
