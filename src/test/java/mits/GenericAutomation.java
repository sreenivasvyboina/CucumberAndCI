package mits;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import util.Constants;
import util.ResourceLoader;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import exception.CustomizedException;

public class GenericAutomation {

	/**
	 * @author SMBC STEP Workflow Automation Code: Megha Rama This code is used for
	 *         the Automation for SMBC trade finance
	 * 
	 */
	public static Logger logger = Logger.getLogger(GenericAutomation.class);
	static Workbook wbook;
	static WritableWorkbook wwbCopy;
	static String ExecutedTestCasesSheet;
	static WritableSheet shSheet;
	static WebDriver driver;
	public static ResourceLoader loader = new ResourceLoader();
	static Map<String, Object> map;
	static int column;
	static Map.Entry entry;
	public static String user = null;
	public static String pass = null;
	public static String casei = null;
	static String actions = null;
	static String mandatory;
	static String fieldvalue;
	static String value1;
	static String value2;
	static String fieldLabel;
	static String readonly1;
	static int row;
	static int rowcount;
	static String data1;

	/**
	 * Used to Read the Excel Sheet
	 */

	public static String readExcel() {
		String message = null;
		try {
			logger.info("Reading the Excel file");
			// reading the excel file
			wbook = Workbook.getWorkbook(new File(loader.getProperty(Constants.FileName)));
			wwbCopy = Workbook.createWorkbook(new File(loader.getProperty(Constants.CopyFileName)), wbook);
			shSheet = wwbCopy.getSheet(0);
			message = "Success";
			return message;
		} catch (Exception e) {
			// handling the exception
			logger.error("While reading the Excel error :" + e.getMessage());
			message = "Fail :: Error :: " + e.getLocalizedMessage();
			new CustomizedException(e, driver);
			return message;
		}
	}

	/**
	 * this method is used to open the Browser
	 * @throws InterruptedException 
	 * 
	 * @throws CustomizedException
	 **/

	public static void openBrowser() throws InterruptedException  {

		logger.info("Opening the browser window");
		// creating the instance for the firefox driver ..System.setProperty("webdriver.chrome.driver","D:\\sunita\\Drivers\\chromedriver.exe");
	
		
			System.setProperty(loader.getProperty(Constants.WEBDRIVER_KEY),loader.getProperty(Constants.WEBDRIVER_VALUE));
			
			//  Modified the setProperty method added the constants key and value.

			driver = new ChromeDriver();
			// wait for page to load
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			// navigate to the url
			driver.manage().window().maximize();
			driver.get(loader.getProperty(Constants.IcmUrl));
			// maximize the window
			logger.info("Maximize the ICM window browser");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		Thread.sleep(5000);
			
		}

	

	/**
	 * @param username
	 * @param password
	 */
	public static void login(String username, String password) {

		driver.findElement(By.id(loader.getProperty(Constants.UserName))).sendKeys(username);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.id(loader.getProperty(Constants.Password))).sendKeys(password);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.id(loader.getProperty(Constants.LoginID))).click();
	}

	/**
	 * this method is used to verify the login
	 */
	public static String loginVerify(String username, String password) {

		logger.info("in the Login Method ");
		logger.info("username is " + username);
		logger.info("password is " + password);

		try {
			// Enterring UserName
			driver.findElement(By.id(loader.getProperty(Constants.UserName))).sendKeys(username);
			// Enterring Password
			driver.findElement(By.id(loader.getProperty(Constants.Password))).sendKeys(password);
			// Wait For Page To Load
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			// Click on 'Login' button
			driver.findElement(By.id(loader.getProperty(Constants.LoginID))).click();
			WebDriverWait wait = new WebDriverWait(driver, 30);
			WebElement element = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.className(loader.getProperty(Constants.LoginCheck))));
			// Click Logout link
			// Click on 'Sign In' button
			return "Pass";
		} catch (Exception exception) {
			driver.close();
			return "Fail";
		}
	}

	/**
	 * This method is use to open the case
	 * 
	 * @param action
	 * @param caseID
	 * 
	 **/
	public static String openCase(String caseID, int row) {
		logger.info("The case id is--->>> " + caseID);
		try {
			int actrow = 1;
			for (int wtrow = 1; wtrow <= actrow; wtrow++) { // getting the table xpath
				WebElement htmltable = driver.findElement(By.xpath("//*[@id='gridx_Grid_0']/div[3]/div[2]/div[" + wtrow + "]/table/tbody"));
				// to scroll the view
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", htmltable);
				List<WebElement> rows = htmltable.findElements(By.tagName("tr"));
				for (int rnum = 0; rnum < rows.size(); rnum++) {
					actrow++;
					List<WebElement> columns = rows.get(rnum).findElements(By.tagName("td"));
					for (int cnum = 0; cnum < columns.size(); cnum++) {
						if (columns.get(cnum).getText().equalsIgnoreCase(caseID)) {
							logger.info("CASE ID--->>>" + columns.get(cnum).getText());
							actrow = 0;
							// *[@id='gridx_Grid_0']/div[3]/div[2]/div/table/tbody/tr/td[6]
							// to click the case
							driver.findElement(By.xpath(
									".//*[@id='gridx_Grid_0']/div[3]/div[2]/div[" + wtrow + "]/table/tbody/tr/td[2]"))
									.click();
							// .//*[@id='gridx_Grid_0']/div[3]/div[2]/div/table/tbody/tr/td[2]
						}
					}
				}
			}
			return "Pass";
		} catch (Exception exception) {

			new CustomizedException(exception, driver);
			column = 6;
			setValueIntoCell("Case Not Found", row, column);
			return "Case Not Found";
		}
	}

	/**
	 * This method is used to fill the mandatory data
	 */
	public static void mandatoryData() {
		try {
			Thread.sleep(10000);
			String header = (driver.findElement(By.className("acmwdgtHeaderWorkflowName")).getText());
			logger.info("Header: " + header);
			// if(!header.equals("Change of Bill Amount or Due Date (SSC)") ||
			// !header.equals("Pre Acceptance Discounting")|| !header.equals("Post
			// Acceptance Discounting (SSC)"))
			// {
			WebElement webelement = driver.findElement(By.id(loader.getProperty(Constants.MandatoryField)));
			String readonly = webelement.getAttribute("readonly");
			logger.info("The readonly property is " + readonly);
			if (readonly == null) {
				// String
				// Text=driver.findElement(By.id(loader.getProperty(Constants.MandatoryField))).getAttribute("value");
				// if(Text == null)
				// {
				driver.findElement(By.id(loader.getProperty(Constants.MandatoryField))).clear();
				driver.findElement(By.id(loader.getProperty(Constants.MandatoryField))).sendKeys(loader.getProperty(Constants.MandatoryData));
				// }
			}

			WebElement webelement1 = driver.findElement(By.id(loader.getProperty(Constants.MandatoryFieldFXRateType)));
			String readonly1 = webelement1.getAttribute("readonly");
			logger.info("The readonly property is " + readonly1);
			
			if (readonly1 == null) {
				String Text = driver.findElement(By.id(loader.getProperty(Constants.MandatoryFieldFXRateType)))
						.getAttribute("value");
				if (Text == null) {
					// driver.findElement(By.id(loader.getProperty(Constants.MandatoryFieldFXRateType))).clear();
					// Thread.sleep(3000);
					driver.findElement(By.id(loader.getProperty(Constants.MandatoryFieldFXRateType)))
							.sendKeys(loader.getProperty(Constants.MandatoryDataFXRateType));
				}
			}
			/*
			 * else { logger.info("calling the actionPerformance() from catch block");
			 * actionPerformance(actions); }
			 */
			// }
		} catch (Exception exception) {
			new CustomizedException(exception, driver);
		}
	}

	/**
	 * this is method is to click the action buttons
	 * 
	 */
	public static String actionPerformance(String Action) {
		logger.info("action performance");
		logger.info("the action to be performed is " + Action);
		try {
			logger.info("the action inside loop");
			Thread.sleep(3000);
			List<WebElement> elements = driver.findElements(By.cssSelector(loader.getProperty(Constants.Button)));
			for (int j = 0; j < elements.size(); j++) {
				WebElement element1 = elements.get(j);
				String rowvalue = element1.getText();
				System.out.println("action button:" + rowvalue);

				if (rowvalue.equalsIgnoreCase(Action)) {
					logger.info("the button clicked is " + elements.get(j).getText());
					elements.get(j).click();
					data1 = "Success:" + Action;
					break;
				} else {
					data1 = "Fail:" + Action;
				}

			}
			return data1;
		} catch (Exception e) {
			new CustomizedException(e, driver);
			data1 = "Fail:" + Action;
			return data1;
		}

	}

	private static void dialogMessage() throws InterruptedException {
		// For Comments Box
		logger.info("inside the dialogMessage()");
		int alt = 0;
		ArrayList<String> al = new ArrayList<String>();
		Thread.sleep(3000);
		for (int altd = 0; altd <= alt; altd++) {

			ArrayList<WebElement> elements = (ArrayList<WebElement>) driver
					.findElements(By.cssSelector(loader.getProperty(Constants.Button)));

			for (int element = 0; element < elements.size(); element++) {
				WebElement elementName = elements.get(element);
				String textboxName = elementName.getText();
				System.out.println("Dialog elements:" + textboxName);
				al.add(textboxName);
				if (textboxName.equalsIgnoreCase("Ok")) {
					driver.findElement(By.id(loader.getProperty(Constants.TextArea)))
							.sendKeys(loader.getProperty(Constants.Message));
					logger.info("the button clicked is " + elements.get(element).getText());
					elements.get(element).click();
				}
				/*
				 * if (al.lastIndexOf("Submit") > 25) { logger.info("inside the submit "
				 * +al.lastIndexOf("Ok"));
				 * driver.findElement(By.id(loader.getProperty(Constants.TextArea))).sendKeys(
				 * loader.getProperty(Constants.Message)); logger.info("wriring the content");
				 * logger.info("the button clicked is "+elements.get(element).getText()
				 * +"with the location "+elements.get(element).getLocation());
				 * elements.get(al.lastIndexOf("Submit")).click();
				 * logger.info("clicking the submit button"); break; }
				 */
				else if (al.lastIndexOf("Close") > 25) {
					logger.info("Clicking the close button");
					elements.get(al.lastIndexOf("Close")).click();

				}

			}

		}

	}

	/**
	 * This method is for alerts boxes
	 */
	public static void commentsBox() {
		// For Alerts
		logger.info("inside the CommentBox();");
		try {
			int alt = 0;
			for (int altd = 0; altd <= alt; altd++) {
				Thread.sleep(10000);
				List<WebElement> elements = driver.findElements(By.cssSelector(loader.getProperty(Constants.Button)));
				System.out.println(elements.size());
				for (int element = 0; element < elements.size(); element++) {
					WebElement elementName = elements.get(element);
					String textboxName = elementName.getText();
					if (textboxName.equalsIgnoreCase("yes")) {
						// logger.info("the button clicked is YES");
						elements.get(element).click();
						logger.info("the button clicked is " + elements.get(element).getText());
						alt++;
					} else if ((textboxName.equalsIgnoreCase("Discard"))) {
						elements.get(element).click();
						logger.info("Discard clicked");
					}
				}
			}
		} catch (Exception exception) {
			logger.info("calling the dialogMessage() from catch");
			new CustomizedException(exception, driver);
		}
		logger.info("executed CommentBox();");
	}

	/**
	 * This method is used to logout method
	 */
	public static void logOut() {
		try {
			Thread.sleep(10000);
			// Notificationuser.notefication();

			driver.findElement(By.id(loader.getProperty(Constants.DropDown))).click();

			// Select Logout from Logout menu
			Thread.sleep(3000);
			driver.findElement(By.id(loader.getProperty(Constants.MenuItem))).click();
			// Click Logout button in Alert message
			Thread.sleep(3000);
			// driver.findElement(By.id("dijit_form_Button_26_label")).click();
			List<WebElement> elements = driver.findElements(By.cssSelector(loader.getProperty(Constants.Button)));
			for (int j = 0; j < elements.size(); j++) {
				WebElement element1 = elements.get(j);
				String rowvalue = element1.getText();
				if (rowvalue.equalsIgnoreCase("Log Out")) {
					elements.get(j).click();
					break;
				}
			}
			// Close the browser
			driver.close();
		} catch (Exception exception) {
			driver.quit();
			logger.info("the message from logout is " + exception.getMessage());
		}
	}

	public static void setValueIntoCell(String strData, int row, int column) {
		logger.info("writing the data");
		WritableSheet wshTemp = wwbCopy.getSheet(0);
		Label labTemp = new Label(column, row, strData);
		try {
			wshTemp.addCell(labTemp);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	public static void closeFile() {
		try {
			// Closing the writable work book
			wwbCopy.write();
			wwbCopy.close();

			// Closing the original work book
			wbook.close();
		} catch (Exception e)

		{
			e.printStackTrace();
		}
	}

	public static void exceldata() throws IOException, WriteException, InterruptedException, CustomizedException {
		try {
			rowcount = shSheet.getRows() - 1;
			System.out.println("Row count:" + rowcount);
			
			for (int row = 1; row <= rowcount; row++) {
				// returning the login result from the browser
				System.out.println("LoginID" + (shSheet.getCell(0, row).getContents()));
				System.out.println("LoginPass" + (shSheet.getCell(1, row).getContents()));
				map = new HashMap<String, Object>();
				map.put("Username", shSheet.getCell(0, row).getContents());
				map.put("password", shSheet.getCell(1, row).getContents());
				map.put("caseID", shSheet.getCell(2, row).getContents());
				map.put("Action", shSheet.getCell(3, row).getContents());
				
				for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
					entry = (Map.Entry) it.next();
					if (entry.getKey().equals("Username")) {
						user = (String) entry.getValue();
					}
					if (entry.getKey().equals("password")) {
						pass = (String) entry.getValue();
					}
					if (entry.getKey().equals("caseID")) {
						casei = (String) entry.getValue();
					}
					if (entry.getKey().equals("Action")) {
						actions = (String) entry.getValue();
					}
				}
				openBrowser();
				String strData = loginVerify(user, pass);
				// genericAuto.closeFile();
				logger.info("strdata  " + strData);
				// column=4;
				setValueIntoCell(strData, row, 4);
				String openCaseStatus = openCase(casei, row);
				String data = actionPerformance(actions);
				setValueIntoCell(data, row, 5);
				if (data.contains("Reject")) {
					dialogMessage();
				} else {
					logger.info("action completed  " + actions);
				}
			}

		} catch (Exception e) {

		}
		rowcount = shSheet.getRows() - 1;
		System.out.println("Row count:" + rowcount);
		for (int row = 1; row <= rowcount; row++) {
			// returning the login result from the browser
			System.out.println("LoginID" + (shSheet.getCell(0, row).getContents()));
			System.out.println("LoginPass" + (shSheet.getCell(1, row).getContents()));
			
			map = new HashMap<String, Object>();
			map.put("Username", shSheet.getCell(0, row).getContents());
			map.put("password", shSheet.getCell(1, row).getContents());
			map.put("caseID", shSheet.getCell(2, row).getContents());
			map.put("Action", shSheet.getCell(3, row).getContents());
			
			for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
				entry = (Map.Entry) it.next();
				if (entry.getKey().equals("Username")) {
					user = (String) entry.getValue();
				}
				if (entry.getKey().equals("password")) {
					pass = (String) entry.getValue();
				}
				if (entry.getKey().equals("caseID")) {
					casei = (String) entry.getValue();
				}
				if (entry.getKey().equals("Action")) {
					actions = (String) entry.getValue();
				}
			}
			openBrowser();
			String strData = loginVerify(user, pass);
			// genericAuto.closeFile();
			logger.info("strdata  " + strData);
			// column=4;
			setValueIntoCell(strData, row, 4);
			String openCaseStatus = openCase(casei, row);
			String data = actionPerformance(actions);
			setValueIntoCell(data, row, 5);
			if (data.contains("Reject")) {
				dialogMessage();
			} else {
				logger.info("action completed  " + actions);
			}
		}
		wwbCopy.write();
		wwbCopy.close();
		driver.close();
	}
	/*
	 * public static void addNewCase() { try { Thread.sleep(10000); List<WebElement>
	 * fields = driver.findElements(By.cssSelector("*[class='tabLabel']")); //int
	 * count =
	 * driver.findElements(By.cssSelector("*[class='dijitReset dijitInputInner']")).
	 * size(); System.out.println("FieldSize is: "+fields.size()); for(int
	 * k=0;k<fields.size();k++) { WebElement fields1 = fields.get(k); String
	 * tabValue=fields1.getText(); if(tabValue.equalsIgnoreCase("Cases")) {
	 * fields.get(k).click(); }
	 * 
	 * 
	 * } }
	 * 
	 * catch(Exception e) { driver.quit();
	 * logger.info("the message from addNewCase is "+e.getMessage()); } }
	 */

	public static String writeInInput(String object, String data) {
		
		logger.info("Entering into the writeInInput() method");
		logger.info("Checking the username and password");
		try {
			// driver.findElement(By.id(object)).clear();
			Thread.sleep(500);
			driver.findElement(By.id(object)).sendKeys(data);
		} catch (Exception exception) {
			return Constants.KEYWORD_FAIL + " Unable to write " + exception.getMessage();

		}
		return Constants.KEYWORD_PASS;

	}

	/*
	 * Click the Login Id
	 */
	public static String clickButton(String object) throws InterruptedException {

		Thread.sleep(5000);
		logger.info("Entering into the clickButton() method.");
		
		try {
			driver.findElement(By.id(object)).click();
			Thread.sleep(8000);
			List<WebElement> elements = driver.findElements(By.xpath("//*[@id='dijit__TreeNode']"));
			
			logger.info("Entering into the try block.");
			
			for (int j = 0; j < elements.size(); j++) {
				WebElement element1 = elements.get(j);

				logger.info("clickButton() method inside" + elements.get(j).getAttribute("id"));

				String stringObject = element1.getText();
				logger.info("print the test--->>" + stringObject);

				elements.get(j).click();
				driver.findElement(By.xpath("//*[@id='dijit__TreeNode']")).getAttribute("id");

			}
			logger.info("closeing into the clickButton() method.");
		} catch (Exception exception) {
			logger.info("Close the click button method"+exception.getMessage());

		}
		return Constants.KEYWORD_FAIL;
	}
	
	/*
	 * 
	 */

	public static String clickLinkfromtable(String object1, String object, String data) throws InterruptedException {

		Thread.sleep(9000);
		logger.info("Clicking on link ");
		int actrow = 1, wtrow = 1;
		WebElement htmltable = driver.findElement(By.xpath(object1));
		// to scroll the view [‎6/‎25/‎2019 11:17 AM] Sreenivas Vyboina:
		// *[@id='gridx_Grid_0']/div[3]/div[2]/div[1]/table/tbody/tr/td[2]
		//// *[@id='gridx_Grid_1']/div[3]/div[2]/div[1]/table/tbody/tr/td[6]
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", htmltable);
		List<WebElement> rows = htmltable.findElements(By.tagName("tr"));

		for (int rnum = 0; rnum < rows.size(); rnum++) {
			actrow++;
			List<WebElement> columns = rows.get(rnum).findElements(By.tagName("td"));
			for (int cnum = 0; cnum < columns.size(); cnum++) {
				if (columns.get(cnum).getText().equalsIgnoreCase(data)) {
					logger.info("CASE ID" + columns.get(cnum).getText());
					actrow = 0;
					// *[@id='gridx_Grid_0']/div[3]/div[2]/div/table/tbody/tr/td[6]
					// to click the case
					// driver.findElement(By.xpath(object)).click();
					Actions action = new Actions(driver);
					WebElement element = driver.findElement(By.xpath(object));
					// Double click
					action.doubleClick(element).perform();
					logger.info("Clicking on link ");
				}
			}
		}

		return Constants.KEYWORD_PASS;
	}

	public static String clickButtonfromlist(String object, String data) throws InterruptedException {
		logger.info("Clicking the Button from the list and entering the clickButtonfromlist()");

		
		try {
			Thread.sleep(8000);
			List<WebElement> elements = driver.findElements(By.cssSelector(object));

			for (int j = 0; j < elements.size(); j++) 
			{

				WebElement element1 = elements.get(j);
				String rowValue = element1.getText();
				logger.info("action button---->>" + rowValue);

				if (rowValue.equalsIgnoreCase(data))
				{
					logger.info("the button clicked is " + elements.get(j).getText());
					elements.get(j).click();
					data1 = "Success:" + data;
					break;
				}

			}
		}catch(Exception exception) {
			logger.info("clicking the button from the list"+exception.getMessage());
			
		}
		return Constants.KEYWORD_PASS;
	}

	public static String ElementDisplayed(String object, String data, String tab) throws InterruptedException {

		Thread.sleep(5000);
		// driver.findElement(By.xpath(".//*[@id='dijit_form_Button_117_label']")).click();
		int start = 1;
		// List<WebElement>
		// dd=driver.findElements(By.xpath(".//*[contains(@id,'ecm_widget_DropDownButton')]"));
		List<WebElement> elements = driver.findElements(By.xpath(".//*[contains(@id,'dijit_form_Button')]"));
		for (int j = 0; j < elements.size(); j++) {
			WebElement element1 = elements.get(j);
			String rowvalue = element1.getText();
			System.out.println("action button:" + rowvalue);

			if (rowvalue.equalsIgnoreCase("Reset")) {
				logger.info("the button clicked is " + elements.get(j).getText());
				elements.get(j).click();
				data1 = "Success:" + data;
			}
		}
		/*
		 * if(tab.equalsIgnoreCase("Inbasket")) {
		 * driver.findElement(By.xpath(object)).click(); }
		 * if(tab.equalsIgnoreCase("Inbox")) {
		 * driver.findElement(By.xpath(data)).click(); }
		 */
		return Constants.KEYWORD_PASS;
	}

	public String verifyTextContains(String object, String data) {
		logger.info("Verifying the text");
		try {
			String actual = driver.findElement(By.xpath(object)).getText();
			System.out.println("actual....." + actual);
			String expected = data;
			System.out.println("expected....." + expected);
			if (actual.contains(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	//
	public static void selectText(String object, String data) throws CustomizedException{

		logger.info("Entering the selectText() method");
		try {

			String stringObj = object + "'" + data + "']";
			logger.info("Displaying the passed value" + stringObj);

			Thread.sleep(5000);
			driver.findElement(By.xpath(stringObj)).click();
			Thread.sleep(5000);

		} catch (Exception exception) {
			
			//throw new CustomizedException(exception, data);//removed the throws
		
			// 1st scenario
			/*try {
				throw new CustomizedException("The case id is not available---->>>>");
			} catch (CustomizedException e) {
				throw new CustomizedException("The case id is not found");
			}//removed the throws
			*/
			
			//2nd scenario
			throw new CustomizedException("The case id is not available in workplace");
			
			//new CustomizedException("The case id is not available in workplace");//removed throw and throws keyword
			
		}

	}

	public static void main(String[] args) {

		FeildValidation feildValidation = null;
		try {
			// GenericAutomation genericAuto = new GenericAutomation();
			readExcel();

			logger.info("calling the openBrowser();");
			openBrowser();
			logger.info("calling the loginVerify()");
			String strData = loginVerify(user, pass);
			// genericAuto.closeFile();
			logger.info("strdata  " + strData);
			column = 4;
			setValueIntoCell(strData, row, column);

			/*
			 * logger.info("calling addNewCase();"); addNewCase();
			 */
			/*
			 * if(strData=="Pass") { logger.info("calling the openCase();"); String
			 * openCaseStatus=openCase(casei,row); if(openCaseStatus=="Pass") {
			 * Thread.sleep(10000); FeildValidation.getting Data(driver,row,rowcount);
			 * Thread.sleep(5000);
			 * 
			 * logger.info("calling the mandatoryData();"); mandatoryData();
			 * logger.info("calling the actionPerformance();"); String data =
			 * actionPerformance(actions); setValueIntoCell(data, row, 5);
			 * logger.info("calling the commentsBox();"); commentsBox();
			 * //logger.info("calling the dialogMessage();");
			 * System.out.println("actions:"+actions);
			 * if(actions.equalsIgnoreCase("To Branch")) { dialogMessage(); }
			 * if(actions.equalsIgnoreCase("Submit")) { dialogMessage(); } }
			 * logger.info("calling the logOut();"); logOut(); } else if(strData=="Fail" &&
			 * row==rowcount) { feildValidation=new FeildValidation();
			 * 
			 * feildValidation.closeFileFieldvalidation(); } }
			 */
		}

		catch (Exception e) {
			logger.info("Exception from the main method is " + e.getMessage());
		} finally {
			try {
				closeFile();
				feildValidation = null;
			} catch (Exception e) {
				logger.info("message from finally block is " + e.getMessage());
			}
		}

	}
}
