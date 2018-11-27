package test.usecases;
import org.junit.*;
import org.openqa.selenium.chrome.ChromeDriver;

import db.services.UserPersistenceService;
import db.services.impl.UserPersistenceServiceImpl;
import domain.user.User;
import test.utils.TestUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import org.openqa.selenium.WebElement;


public class LoginTestCase 
{
	WebDriver driver;
	
	@Test
	public void login() throws Exception { 
		User testUser = TestUtils.generateUser();
		UserPersistenceService userService = UserPersistenceServiceImpl.getInstance();
		userService.create(testUser);
		
		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
	    driver = new ChromeDriver();
	    driver.get("http://localhost:8080/CS6359_Project/login.jsp");
	    WebElement username = driver.findElement(By.name("username"));
	    WebElement password = driver.findElement(By.name("password"));
	    WebElement button = driver.findElement(By.name("submit"));         
	
	    username.sendKeys(testUser.getUsername());
	    password.sendKeys(testUser.getPassword());
	    button.click();
	    Assert.assertEquals("Home", driver.getTitle());		
	}
	

	@Test
	public void loginInactive() throws Exception { 
		User testUser = TestUtils.generateUser();
		testUser.setActive(false);
		UserPersistenceService userService = UserPersistenceServiceImpl.getInstance();
		userService.create(testUser);
		
		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
	    driver = new ChromeDriver();
	    driver.get("http://localhost:8080/CS6359_Project/login.jsp");
	    WebElement username = driver.findElement(By.name("username"));
	    WebElement password = driver.findElement(By.name("password"));
	    WebElement button = driver.findElement(By.name("submit")); 
	    
	    username.sendKeys(testUser.getUsername());
	    password.sendKeys(testUser.getPassword());
	    button.click();
	    Assert.assertEquals("Login", driver.getTitle());		
	}
	
	
	
	@After 
	public void closePage(){
		driver.quit();
	}
}