package com.app.tests;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * This class contains tests for Filpkartwebsite
 * @author ashok.kumar
 *
 */
public class FilpKartTests {

	private static WebDriver driver;

	private static Logger log = Logger.getLogger(FilpKartTests.class.getName());

	/**
	 * Initialize the driver object
	 */
	@BeforeTest
	public void beforeTest() {
		log.info("Initiaizing the driver object..");
		System.setProperty("webdriver.chrome.driver", TestUtils.DRIVER_PATH);
		log.info("<================================================================>");
		log.info("Launching the Browser");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
	}

	/**
	 * 
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void flipkartLocusTest() throws InterruptedException {
		//open the url in browser
		driver.get(TestUtils.URL);
		log.info("PAGE TITLE IS ========>" + driver.getTitle());
		log.info("<================================================================>");
		log.info("ACTUAL   TITLE---------------->" + TestUtils.FKTITLE);
		log.info("EXPECTED TITLE---------------->" + driver.getTitle());
		//verify the page title
		assertEquals(TestUtils.FKTITLE, driver.getTitle());
		Actions action = new Actions(driver);
		// pressing ESC to close the log in tab
		action.sendKeys(Keys.ESCAPE).build().perform();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestUtils.SEARCH_INPUT)));
		element.sendKeys(TestUtils.SHOES, Keys.ENTER);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestUtils.SEARCH_TITLE)));
		log.info("ACTUAL   ---------------->" + TestUtils.FKTITLE_SHOES);
		log.info("EXPECTED ---------------->" + element.getText());
		//verify products page title
		assertEquals(TestUtils.FKTITLE_SHOES, element.getText());
		element = driver.findElement(By.xpath(TestUtils.PRICESB));
		Select se = new Select(element);
		se.selectByValue(TestUtils.SETPRICE);
		Thread.sleep(2000);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestUtils.FILTER_BRAND)));
		element = driver.findElement(By.xpath(TestUtils.FILTER_BRAND));
		element.clear();
		element.sendKeys(TestUtils.SBRAND);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestUtils.CHKBOX_BRAND)));
		element.click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestUtils.CHKBOX_BRAND)));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		log.info("After Filter Applying------");
		log.info(driver.findElement(By.xpath(TestUtils.VERIFY_PRICESB)).getText());
		log.info(driver.findElement(By.xpath(TestUtils.VERIFY_BRAND)).getText());
		log.info("ACTUAL   ---------------->" + driver.findElement(By.xpath(TestUtils.VERIFY_BRAND)).getText());
		log.info("EXPECTED ---------------->" + TestUtils.SBRAND);
		// verifying the Brand selection
		assertEquals(driver.findElement(By.xpath(TestUtils.VERIFY_BRAND)).getText(), TestUtils.SBRAND);
		log.info("ACTUAL   ---------------->" + TestUtils.EPRICE);
		log.info("EXPECTED ---------------->" + driver.findElement(By.xpath(TestUtils.VERIFY_PRICESB)).getText());
		// verifing the Price selction 
		assertEquals(driver.findElement(By.xpath(TestUtils.VERIFY_PRICESB)).getText(), TestUtils.EPRICE);
		List<WebElement> we = driver
				.findElements(By.xpath(TestUtils.PRODLIST));
		String currentWindowHandle = driver.getWindowHandle();
		we.get(0).click();
		ArrayList<String> windowHandles = new ArrayList<String>(driver.getWindowHandles());
		for (int i = 0; i < windowHandles.size(); i++) {
			log.info(windowHandles.get(i).toString());
		}
		log.info("Moving to Product Detail Page ");
		driver.switchTo().window(windowHandles.get(1));
		log.info("------->Product Detail Page<--------------- ");
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestUtils.SETSIZE)));
		driver.findElement(By.xpath(TestUtils.SETSIZE)).click();
		js.executeScript("window.scrollBy(0,1000)");
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestUtils.BUYBUTTON)));
		driver.findElement(By.xpath(TestUtils.BUYBUTTON)).click();
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestUtils.LOGINPAGETITLE)));
		String exp = driver.findElement(By.xpath(TestUtils.LOGINPAGETITLE)).getText();
		driver.switchTo().window(currentWindowHandle);
		log.info("ACTUAL   ---------------->" + TestUtils.LOGINTITLE);
		log.info("EXPECTED ---------------->" + exp);
		// Verifying login or sing up page 
		assertEquals(TestUtils.LOGINTITLE, exp);
		

	}

	/**
	 * close the driver object
	 */
	@AfterTest
	public void afterTest() {
		log.info("Closing the browser............");
		driver.quit();
		log.info("<================================================================>");
		
		
	}

}
