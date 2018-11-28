package test.usecases;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import db.services.impl.UserPersistenceServiceImpl;
import domain.user.User;
import test.utils.TestUtils;


public class ViewPagesTestCase {
	
	private WebDriver driver;
	private User testUser;
	
	@Before
	public void login() throws Exception {		
		testUser = TestUtils.generateUser();
		UserPersistenceServiceImpl.getInstance().create(testUser);
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
	    driver.get("http://localhost:8080/CS6359_Project/login.jsp");
	    driver.findElement(By.name("username")).sendKeys(testUser.getUsername());
	    driver.findElement(By.name("password")).sendKeys(testUser.getPassword());
	    driver.findElement(By.name("submit")).click();   
	    Assert.assertEquals("Home", driver.getTitle());
	}
	
	@Test
	public void viewPage() throws Exception { 
	    driver.findElement(By.name("menutransactions")).click();
		Assert.assertEquals("Transactions", driver.getTitle());
		driver.findElement(By.name("menufaq")).click();
		Assert.assertEquals("FAQ", driver.getTitle());
		driver.findElement(By.name("menuabout")).click();
		Assert.assertEquals("About",driver.getTitle());		
	}

	@After 
	public void closePage(){
		driver.quit();
	}
}
