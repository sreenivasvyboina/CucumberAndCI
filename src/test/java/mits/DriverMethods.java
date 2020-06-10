package mits;

import java.awt.AWTException;
import java.util.Date;	
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import junit.framework.Assert;
import com.cucumber.listener.Reporter;

import exception.CustomizedException;

import org.openqa.selenium.Cookie;	
public class DriverMethods {
	static WebDriver driver;
	Properties prop;
	boolean b;
	String b1;
	Actions action;
	Robot robot;
	org.openqa.selenium.Alert al;
	Logger log = Logger.getLogger("devpinoyLogger");
	WebDriverWait wait;
	static int cellcount=0;
	
 //ExtentReports extre;
// ExtentTest test;
	
/* this method uses for load the url */	
	public void getURL(String Url) throws InterruptedException {
		
		driver.get(Url);
		Thread.sleep(2500);
		log.info("launching URL");
		Reporter.addScenarioLog(" URL loaded");
		Reporter.addStepLog(" URL loaded");
		//log.info("Invalid Credentials");

	//	extre.setTestRunnerOutput("pass");
}
/* This method uses for check the element present or not */	
	public void checkvisibility(String locatorType, String value) throws CustomizedException {
	try{wait=new WebDriverWait(driver, 40);
	By locator = locatorValue(locatorType, value);
	wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	
	}catch(Exception e){
		log.info(e);
		throw new CustomizedException(value +"element not found");}
	}

/* This method uses for comparing with list of elements and perform click operation */
public void ListElementClick(String locatorType, String value, String text) throws InterruptedException, CustomizedException {
	try {
		By locator = locatorValue(locatorType, value);
		List<WebElement> wb = driver.findElements(locator);
		log.info("List of elements"+wb.size());
		log.info(wb.size());
		for (WebElement wb2 : wb) {
			log.info(wb2.getText());
			if (wb2.getText().equalsIgnoreCase(text)) {
			wb2.click();
			}
		}
	log.info("click performed ");
	}catch(Exception e){
		log.info(e);
		throw new CustomizedException(text +"unable to click");}
	}
/* This method uses for entering data to field */
public void enterText(String locatorType, String value, String text) throws InterruptedException, CustomizedException { // 6
	Exception message=null;
	try{wait=new WebDriverWait(driver, 80);
	By locator = locatorValue(locatorType, value);
	wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	WebElement element = driver.findElement(locator);
	element.sendKeys(text);
	}
	catch(Exception e){
		message = e;
	throw new CustomizedException(text +"unable to enter text");	
	}
}

/* This method uses when comparing list of elements and performs enter data operation */
public void ListElementSendKeys(String locatorType, String value, String text1) throws InterruptedException, CustomizedException { 
	try{
	By locator = locatorValue(locatorType, value);// 3
	List<WebElement> wb = driver.findElements(locator);

	for (WebElement wb2 : wb) {
		if (wb2.getText().equalsIgnoreCase(text1)) {

			wb2.sendKeys(text1);
		}
	}
	log.info("entered value");
	} catch (Exception e) {
		log.info(e);
		throw new CustomizedException(text1 +"unable to enter text");
	}
}

/* This method uses for closing the browser */
public void closeBrowser() throws CustomizedException { // 8
	// wait = new WebDriverWait(driver,2);
	try{driver.close();
	} catch (Exception e) {
		log.info(e);
		throw new CustomizedException("Driver not closed");
	
	}
}

/* This method uses for check the element present and returns boolean value */	
public boolean isDisplyedElement(String locatorType, String value) {
	try {
		By locator = locatorValue(locatorType, value);
		driver.findElement(locator).isDisplayed();
		b = true;
		log.info(b);
		return b;
	} catch (Exception e) {
		b = false;
		return b;
	}
}

/* This method uses for uploading th documents */
public void uploadDocuments(String FileLoc) throws AWTException, InterruptedException {
	robot = new Robot();
	StringSelection sc = new StringSelection(FileLoc);
	Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sc, null);
log.info("Documents uploaded");
	robot.keyPress(KeyEvent.VK_CONTROL);
	robot.keyPress(KeyEvent.VK_V);
	robot.keyRelease(KeyEvent.VK_V);
	robot.keyRelease(KeyEvent.VK_CONTROL);
robot.keyPress(KeyEvent.VK_ENTER);
	robot.keyRelease(KeyEvent.VK_ENTER);
}

/* This method uses for switch to Alert and accepts the alert */
public void Alert() {try{
	al = driver.switchTo().alert();
	al.accept();
	log.info("accepted aleart");}
catch(Exception e){
	log.info(e);
}
}

/* 
 This method uses when move to particular element has to perform 
 */

public void MoveToElement(String locatorType, String value) throws InterruptedException {
	try{
	action = new Actions(driver);
	By locator = locatorValue(locatorType, value);
	action.moveToElement(driver.findElement(locator)).click().build().perform();
	log.info("Action performed");
	}
	catch(Exception e){
		log.info(e);
	}

}

/* Driver opens the browser and handles unexpected alerts */
public void driverDetails(String WEBDRIVER_KEY, String WEBDRIVER_Value) // 5
{final DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
chromeCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
final ChromeOptions chromeOptions = new ChromeOptions();
chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);	
System.setProperty(WEBDRIVER_KEY,
		WEBDRIVER_Value);
driver = new ChromeDriver(chromeCapabilities);
	driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
}

/* this will checks the value and switches to that locator type */
private By locatorValue(String locatorType, String value) {
	By by;
	switch (locatorType) {
	case "id":
		by = By.id(value);
		break;
	case "name":
		by = By.name(value);
		break;
	case "xpath":
		by = By.xpath(value);
		break;
	case "css":
		by = By.cssSelector(value);
		break;
	case "linkText":
		by = By.linkText(value);
		break;
	case "partialLinkText":
		by = By.partialLinkText(value);
		break;
	case "tagName":
		by = By.tagName(value);
		break;
	default:
		by = null;
		break;
	}
	return by;
}
/* This method uses for click operation */
public String clickOnLocator(String locatorType, String value) throws InterruptedException, CustomizedException { // 7
	String message = null;
	try{
By locator = locatorValue(locatorType, value);
	WebElement element = driver.findElement(locator);
	wait=new WebDriverWait(driver, 40);
	wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	element.click();
	Thread.sleep(500);
	message = "Success";
return message;
}
	catch(Exception e){
		throw new CustomizedException("element click failed");	}
}


	  
/* This method will checks the elements for positive and nagative */
public String checksElements(String locatorType,String Value1, String value, String username, String password) throws CustomizedException {
			try {By locator = locatorValue(locatorType, value);
			wait=new WebDriverWait(driver, 40);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			String b1 = driver.findElement(locator).getText();
			Reporter.addStepLog("username or Password"+username+"or"+password+" "+b1);
						}catch(Exception e){
							wait=new WebDriverWait(driver, 40);
						By locator = locatorValue(locatorType, Value1);
						wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
						boolean b = driver.findElement(locator).isDisplayed();
						
						//log.info(e);
				 throw new CustomizedException("username or Password"+username+"or"+password+"login successful");
			}
			return b1;
			}

/* This method uses for click operation and checks element present */
public void clickOnLocator(String locatorType, String value,String value1) throws Exception { // 7
	try {
		wait = new WebDriverWait(driver, 40);
		By locator = locatorValue(locatorType, value);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		WebElement element = driver.findElement(locator);
		
		element.click();
		By locator1=locatorValue(locatorType, value1);
		WebElement element1=driver.findElement(locator1);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator1));
	Assert.assertTrue(element1.isDisplayed());
		
		//throw new CustomizedException(text +"Invalid data");
	} catch (Exception e) {
		
		throw new CustomizedException("Invalid data");
	}

}


/* This method uses for entering data to properties in tables */
		
	public void Enter_Details_For_All_Properties(String locatorType, String value, String value1, String TextValue,
			String Key1, int rowcount) throws CustomizedException {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			By locator = locatorValue(locatorType, value);
/* Gets all the headers in table */
			List<WebElement> headers = driver.findElements(locator);

			log.info(headers.size() + "headercount");
/* gets each header text by iterating loop */
			for (WebElement eachheader : headers) {
				js.executeScript("arguments[0].scrollIntoView();", eachheader);
/* Checking header value 
 * is it having value or null
 */
				if (eachheader.getText().isEmpty()) {
									log.info(cellcount);
				} else {
				log.info(eachheader.getText());				
					log.info(cellcount);                  
					log.info(eachheader.getText().replace(" ", ""));
/* Comparing header value with key(from Feature file header) */
				if (eachheader.getText().replace(" ", "").equalsIgnoreCase(Key1)) {
						log.info(value1 +rowcount+"]/table/tbody/tr/td[" + cellcount + "]");		
/* Enter data to cells by calling Enters_Data */					
						Enters_Data(locatorType, value1+rowcount+"]/table/tbody/tr/td[" +--cellcount + "]", TextValue);
						log.info(cellcount + "increase count value");

						break;
					} else {
						// counter++;
					}		
				}
				cellcount++;} 
			cellcount=0;}	 catch (Exception e) {
			log.info(e.getMessage());
			throw new CustomizedException(TextValue +"data enter failed ");
	}
	}

/* This method will perform double click and enters data using clipboard */
	public void Enters_Data(String locatorType, String value, String Text) throws InterruptedException, CustomizedException {
		try{
			JavascriptExecutor js = (JavascriptExecutor) driver;
		
		action = new Actions(driver);
		wait=new WebDriverWait(driver, 60);
		By locator = locatorValue(locatorType, value);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		WebElement element = driver.findElement(locator);
		js.executeScript("arguments[0].scrollIntoView();", element);
		action.moveToElement(element).doubleClick().perform();
	
		Thread.sleep(1500);

		try {
			String text = Text;
			
			StringSelection stringSelection = new StringSelection(text);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, stringSelection);
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			log.info("Entered"+text);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
		
			log.info(e);
			throw new CustomizedException("Value not entered");
		}

		Thread.sleep(1000);}
	catch(Exception e){
		log.info(e);
		throw new CustomizedException(Text +"data enter failed ");
	}
	
		
	}
	
	
	
	
	

	
	
	/*
	
		
	
	  public void getcookies()	
	  {
	        // create file named Cookies to store Login Information		
	        File file = new File("Cookies.data");							
	        try		
	        {	  
	            // Delete old file if exists
				file.delete();		
	            file.createNewFile();			
	            FileWriter fileWrite = new FileWriter(file);							
	            BufferedWriter Bwrite = new BufferedWriter(fileWrite);							
	            // loop for getting the cookie information 		
	            	
	            // loop for getting the cookie information 		
	            for(Cookie ck : driver.manage().getCookies())							
	            {			
	                Bwrite.write((ck.getName()+";"+ck.getValue()+";"+ck.getDomain()+";"+ck.getPath()+";"+ck.getExpiry()+";"+ck.isSecure()));																									
	                Bwrite.newLine();             
	            }			
	            Bwrite.close();			
	            fileWrite.close();	
	            
	        }
	        catch(Exception ex)					
	        {		
	            ex.printStackTrace();			
	        }		
	    }
	  public void readwritecookies() {
		  Set<Cookie> allCookies = driver.manage().getCookies();

	        driver.close();

	        //open a new browser window
//driverDetails();

	        //restore all cookies from previous session
	        for(Cookie cookie : allCookies) {
	            driver.manage().addCookie(cookie);
	        }
	  }
	  public void usecookies() {
		  try{
			  File file = new File("Cookies.data");							
		        FileReader fileReader = new FileReader(file);							
		        BufferedReader Buffreader = new BufferedReader(fileReader);							
		        String strline;			
		        while((strline=Buffreader.readLine())!=null){									
		        StringTokenizer token = new StringTokenizer(strline,";");									
		        while(token.hasMoreTokens()){					
		        String name = token.nextToken();					
		        String value = token.nextToken();					
		        String domain = token.nextToken();					
		        String path = token.nextToken();					
		        Date expiry = null;					
		        		
		        String val;			
		        if(!(val=token.nextToken()).equals("null"))
				{		
		        	expiry = new Date(val);					
		        }		
		        Boolean isSecure = new Boolean(token.nextToken()).								
		        booleanValue();		
		        Cookie ck = new Cookie(name,value,domain,path,expiry,isSecure);			
		        System.out.println(ck);
		        driver.manage().addCookie(ck); // This will add the stored cookie to your current session					
		        }		
		        }			
	        }catch(Exception ex){					
	        ex.printStackTrace();			
	        }		
}	

	
	
	
	
	public void ListElementClickDrodown(String locatorType, String value, String text) throws InterruptedException { // 2
		By locator = locatorValue(locatorType, value);// 3
		List<WebElement> wb = driver.findElements(locator);
		System.out.println(wb.size() + "lent");
		for (WebElement wb2 : wb) {
			Thread.sleep(2000);
			try {
				robot = new Robot();

				while (wb2.getText().equalsIgnoreCase(text)) {
					robot.keyPress(KeyEvent.VK_DOWN);
					robot.keyRelease(KeyEvent.VK_DOWN);
				}
				log.info("Drop-down value selected");
			} catch (Exception e) {
				log.info(e);
			}
		}

	}

	
	public void ListBYTagNameSIN(String locatorType, String value, String text, String tg1)
			throws InterruptedException {
		By locator = locatorValue(locatorType, value);
		WebElement ls0 = driver.findElement(locator);
		List<WebElement> rows2 = ls0.findElements(locator);
		for (WebElement ss : rows2) {
			ss.sendKeys(text);
			log.info("entred text");

		}
	}

	public void ListBYTagNameMULwithALERT(String locatorType, String value, String text, String tg1, String tg2)
			throws InterruptedException {
		By locator = locatorValue(locatorType, value);
		WebElement ls = driver.findElement(locator);

		List<WebElement> rows = ls.findElements(By.tagName(tg1));
		//
		log.info(rows.size() + "=first lookup");
		for (int i1 = 0; i1 < rows.size(); i1++) {

			List<WebElement> cols = ls.findElements(By.tagName(tg2));
			log.info(cols.size() + "=size of cols");
			for (WebElement a2 : cols) {
				if (a2.getText().equalsIgnoreCase(text)) {
					a2.click();
					driver.switchTo().alert().accept();

					// log.info(a2.getText());
					break;

				}
			}
		}

	}

	public void ListBYTagNameMULwithoutALERT(String locatorType, String value, String text, String tg1, String tg2)
			throws InterruptedException {
		By locator = locatorValue(locatorType, value);
		WebElement ls = driver.findElement(locator);

		List<WebElement> rows = ls.findElements(By.tagName(tg1));
		//
		log.info(rows.size() + "=first lookup");
		for (int i1 = 0; i1 < rows.size(); i1++) {

			List<WebElement> cols = ls.findElements(By.tagName(tg2));
			log.info(cols.size() + "=size of cols");
			for (WebElement a2 : cols) {
				if (a2.getText().equalsIgnoreCase(text)) {
					a2.click();

					// log.info(a2.getText());
					break;
				}
			}
		}

	}
/*
	public boolean isDisplyedElement(String locatorType, String value) throws CustomizedException {
	try {
		By locator = locatorValue(locatorType, value);
		boolean b = driver.findElement(locator).isDisplayed();
	}catch(Exception e){
		log.info(e);
		 new CustomizedException(value+"element not found");
	}
	return b;
	}
*/
/*
	
	public void MoveTOElementAndSendKeys(String locatorType, String value, String keys) throws InterruptedException {
		try{action = new Actions(driver);
		By locator = locatorValue(locatorType, value);
		action.moveToElement(driver.findElement(locator)).sendKeys(keys).perform();
		log.info("action performed");
		}
		catch(Exception e){
			log.info(e);
		}
	}



	
		public void click1(String locatortype, String value) throws CustomizedException{
			try {
			wait = new WebDriverWait(driver, 40);
			By locator = locatorValue(locatortype, value);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

			WebElement element = driver.findElement(locator);
			
			element.click();
			} catch (Exception e) {
				
				throw new CustomizedException("Click on eleemt has failed");
			}
		}
	

		
		

	
	public void Select_A_DropDwon_Value(String locatorType, String value, String value1, String Text)
			throws InterruptedException, AWTException, CustomizedException {
		try{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		action = new Actions(driver);
		wait=new WebDriverWait(driver, 40);
		By locator = locatorValue(locatorType, value);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		WebElement element = driver.findElement(locator);
		js.executeScript("arguments[0].scrollIntoView();", element);
		action.doubleClick(element).perform();

		try {

			Thread.sleep(1500);
			Robot rb = new Robot();

			rb.keyPress(KeyEvent.VK_DOWN);
			rb.keyRelease(KeyEvent.VK_DOWN);
			log.info("Selects"+Text);

		} catch (Exception e) {
			// TODO Auto-generated catch block
		
			throw new CustomizedException(e);
		}
		Thread.sleep(1500);
		ListElementClick(locatorType, value1, Text);
		log.info("Element Clicked"+Text);}
		catch(Exception e){
			throw new CustomizedException(Text +"unable to select element from drop-down");
			
		}
	}

	public String getTitle() {
		String s=driver.getTitle();
		return s;
		
		
	}

	


	public void Enters_Data_1(String locatorType, String value, String Text) throws InterruptedException, CustomizedException {
		try{

		int count = 0;
		boolean clicked = false;
		while (count < 4 && !clicked) {
			try {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				action = new Actions(driver);
				wait=new WebDriverWait(driver, 40);
				By locator = locatorValue(locatorType, value);
				wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

				WebElement element = driver.findElement(locator);
				js.executeScript("arguments[0].scrollIntoView();", element);
				action.moveToElement(element).doubleClick().perform();
				Thread.sleep(1500);

				try {
					String text = Text;
					StringSelection stringSelection = new StringSelection(text);
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(stringSelection, stringSelection);

					robot = new Robot();
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					log.info("Entered"+Text);
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					log.info(e);
					throw new CustomizedException(e, driver);
				}

				Thread.sleep(1000);

				
				 clicked = true;
			} catch (StaleElementReferenceException e) {
				e.toString();
				System.out.println("Trying to recover from a stale element :" + e.getMessage());
				count = count + 1;
				log.info(e);
			throw new CustomizedException(e, driver);
			}
		}
		}
		catch(Exception e){
			log.info(e);
			throw new CustomizedException(e, driver);
		}
	}
	*/
	
	
//	public Sheet Excel(String FileNameWithArea, String sheetName1) throws IOException { // 4
//		File f = new File(FileNameWithArea);
//		FileInputStream f1 = new FileInputStream(f);
//		XSSFWorkbook xb = new XSSFWorkbook(f1);
//		Sheet s = xb.getSheet(sheetName1);
//		return s;
//
//	}
//	
//	public void select_A_DropDown_value_1(String locatorType, String value, String value1, String Text) throws InterruptedException {
//
//		int count = 0;
//		boolean clicked = false;
//		while (count < 4 && !clicked) {
//			try {
//				JavascriptExecutor js = (JavascriptExecutor) driver;
//				action = new Actions(driver);
//				wait=new WebDriverWait(driver, 40);
//				By locator = locatorValue(locatorType, value);
//				wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//
//				WebElement element = driver.findElement(locator);
//				js.executeScript("arguments[0].scrollIntoView();", element);
//				action.doubleClick(element).perform();
//
//				try {
//
//					Thread.sleep(1500);
//					Robot rb = new Robot();
//
//					rb.keyPress(KeyEvent.VK_DOWN);
//					rb.keyRelease(KeyEvent.VK_DOWN);
//
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					
//					log.info(e);
//				}
//				Thread.sleep(1500);
//				ListElementClick(locatorType, value1, Text);
//				
//				 clicked = true;
//			} catch (StaleElementReferenceException e) {
//				e.toString();
//				System.out.println("Trying to recover from a stale element :" + e.getMessage());
//				count = count + 1;
//				log.info(e);
//			}
//		}
//	}
//
//	
//	public void select3(String locatorType, String value, String value1, String Text, String value2, String Text2)
//			throws InterruptedException {
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		action = new Actions(driver);
//		By locator = locatorValue(locatorType, value);
//		WebElement element = driver.findElement(locator);
//		js.executeScript("arguments[0].scrollIntoView();", element);
//		action.moveToElement(element).doubleClick().perform();
//		Thread.sleep(1500);
//		action.click().perform();
//		Thread.sleep(500);
//		enterText(locatorType, value1, Text);
//		Thread.sleep(500);
//
//		ListElementClick(locatorType, value2, Text2);
//
//	}

	


//	public void select4(String locatorType, String value, String Text) throws InterruptedException {
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		action = new Actions(driver);
//		By locator = locatorValue(locatorType, value);
//		WebElement element = driver.findElement(locator);
//		js.executeScript("arguments[0].scrollIntoView();", element);
//
//		action.moveToElement(element).doubleClick().perform();
//		Thread.sleep(2000);
//		action.doubleClick().doubleClick().perform();
//		Thread.sleep(500);
//
//		try {
//			String text = Text;
//			StringSelection stringSelection = new StringSelection(text);
//			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//			clipboard.setContents(stringSelection, stringSelection);
//
//			robot = new Robot();
//			robot.keyPress(KeyEvent.VK_CONTROL);
//			robot.keyPress(KeyEvent.VK_V);
//			robot.keyRelease(KeyEvent.VK_V);
//			robot.keyRelease(KeyEvent.VK_CONTROL);
//			robot.keyPress(KeyEvent.VK_TAB);
//			robot.keyRelease(KeyEvent.VK_TAB);
//			robot.keyPress(KeyEvent.VK_ENTER);
//			robot.keyRelease(KeyEvent.VK_ENTER);
//		} catch (AWTException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		/// enterText(locatorType, value1, Text);
//		Thread.sleep(500);
//
//	}

	

	
	// Url(String Url)
	// ListElementClick(String locatorType, String value,String text)
	// WebElement(String locatorType, String value)
	// driverDetails()
	// Excel(String FileNameWithArea)
	// clickOnLocator(String locatorType, String value)
	// closeBrowser()
	public void WebdriverMethods1(String propertiesDoc) throws IOException {
		FileInputStream ss9 = new FileInputStream(propertiesDoc);
		prop = new Properties();
		prop.load(ss9);

	}
}
