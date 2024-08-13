package com.website.pages;

import com.website.main.FunLibrary;

public class LandingPage extends FunLibrary{
	
	public LoginPage clickLoginSignup() {
		
		driver.manage().deleteAllCookies();
		ExplicitWait(OR_OR.getProperty("Login_button_Link"),"Login_button_Link",5);
		Click_Button_Xpath(OR_OR.getProperty("Login_button_Link"),"Login_button_Link");
		
		return new LoginPage();
		
	}

}
