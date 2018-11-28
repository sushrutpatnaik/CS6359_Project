package test.usecases;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import db.services.impl.CraftPersistenceServiceImpl;
import db.services.impl.PaintingPersistenceServiceImpl;
import db.services.impl.SculpturePersistenceServiceImpl;
import db.services.impl.UserPersistenceServiceImpl;
import domain.product.Craft;
import domain.product.Painting;
import domain.product.Sculpture;
import domain.user.User;
import test.utils.TestUtils;

public class AddProductToCartTest {

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
	    
		Painting painting = TestUtils.generatePainting();
		Sculpture sculpt = TestUtils.generateSculpture();
		Craft craft = TestUtils.generateCraft();
		PaintingPersistenceServiceImpl.getInstance().create(painting, testUser.getInventory().getInvnId());
		SculpturePersistenceServiceImpl.getInstance().create(sculpt, testUser.getInventory().getInvnId());
		CraftPersistenceServiceImpl.getInstance().create(craft, testUser.getInventory().getInvnId());
	}

	@After 
	public void closePage(){
		driver.quit();
	}

	@Test
	public void addPaintingToCart() throws Exception {
	    driver.findElement(By.name("menucategory")).click();
		driver.findElement(By.name("paintings")).click();
		driver.findElement(By.name("ViewDetails")).click();
		driver.findElement(By.name("AddToCart")).click();
		Assert.assertEquals("Cart", driver.getTitle());		
	}
	
	@Test
	public void addSculptureToCart() throws Exception {
		driver.findElement(By.name("menucategory")).click();
		driver.findElement(By.name("sculptures")).click();
		driver.findElement(By.name("ViewDetails")).click();
		driver.findElement(By.name("AddToCart")).click();
		Assert.assertEquals("Cart", driver.getTitle());		
	}
	
	@Test
	public void addCraftToCart() throws Exception {
		driver.findElement(By.name("menucategory")).click();
		driver.findElement(By.name("crafts")).click();
		driver.findElement(By.name("ViewDetails")).click();
		driver.findElement(By.name("AddToCart")).click();
		Assert.assertEquals("Cart", driver.getTitle());		
	}

}
