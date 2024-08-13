package com.website.pages;

public class GetPages {
	
	private LoginPage loginPage = null;
	private HomePage homePage = null;
	private LandingPage landingPage = null;
	
	
	public LoginPage getLoginPage() {
		if(loginPage ==null) {
			return new LoginPage();
			
		}
		else {
			return loginPage;
		}
	}
	
	public HomePage getHomePage() {
		if(homePage ==null) {
			return new HomePage();
			
		}
		else {
			return homePage;
		}
	}
	
	
	public LandingPage getLandingPage() {
		if(landingPage ==null) {
			return new LandingPage();
			
		}
		else {
			return landingPage;
		}
	}
	

}
