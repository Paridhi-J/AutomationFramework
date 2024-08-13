package com.website.pages;

import com.website.main.FunLibrary;

public class LoginPage extends FunLibrary{
	
	
	public LoginPage enterUserName(String username) {
		wait(5000);
		Clear_Text(OR_OR.getProperty("Email_ID"), "Email_ID");
		Sendkey_xpath(OR_OR.getProperty("Email_ID"), username, "Email_ID");
		
		return this;
		
	}
	
	public LoginPage enterPassword(String password) {
		wait(5000);
		Clear_Text(OR_OR.getProperty("Password"), "Password");
		Sendkey_xpath(OR_OR.getProperty("Password"), password, "Password");
		
		return this;
		
	}
	
	public HomePage clickLogin() {
		Click_Button_Xpath(OR_OR.getProperty("Login_Button"), "Login_Button");
		wait(12000);
		return new HomePage();
		
		
	}

}
