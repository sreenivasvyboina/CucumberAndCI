package mits;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.Constants;

public class Testsynchrony {
static WebDriver driver;
	static int count() throws AWTException{
	List<WebElement> element = driver.findElements(By.xpath("//*[contains(@id,'dojox_grid_EnhancedGrid_1Hdr')]/div"));

	System.out.println(element.size()+"headercount");
	int count1=0;
	for (WebElement element1 : element) {
	JavascriptExecutor js = (JavascriptExecutor) driver;

	js.executeScript("arguments[0].scrollIntoView();", element1);
//	int count1=0;

	if(element1.getText().isEmpty()){
	count1++;

	}
	else{
	
	System.out.println(element1.getText());
WebElement elementcel=driver.findElement(By.xpath("//*[@id='dojox_grid__View_5']/div/div/div/div/table/tbody/tr/td["+count1+"]"));
elementcel.click();

Actions action=new Actions(driver);
action.doubleClick();
/*
StringSelection stringSelection = new StringSelection("Gap");
Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
clipboard.setContents(stringSelection, stringSelection);

Robot robot= new Robot();
robot.keyPress(KeyEvent.VK_CONTROL);
robot.keyPress(KeyEvent.VK_V);
robot.keyRelease(KeyEvent.VK_V);
robot.keyRelease(KeyEvent.VK_CONTROL);
*/
elementcel.sendKeys("Gap");
count1++;
	}
	}
	System.out.println("count-------------->"+count1);
	return count1;
	}
	
	/*

	WebElement element=driver.findElement(By.xpath("//*[@id='dojox_grid__View_5']/div/div/div/div/table/tbody/tr"));
	List<WebElement> element1 = element.findElements(By.tagName("td"));
	JavascriptExecutor js = (JavascriptExecutor) driver;

	System.out.println(element1.size()+"headercount");
	for (WebElement element2 : element1) {
	js.executeScript("arguments[0].scrollIntoView();", element1);
	int count=0;
	int count1=0;
	if(element2.isDisplayed()){
	System.out.println(++count);

	}
	else{
	count1++;

	}
	}}
	catch(Exception e){
	System.out.println(e.getMessage());
	}
	}

	}


	
	*/
	
	
	public static void main(String[] args) throws AWTException {
		// TODO Auto-generated method stub
		/*
		final DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
		chromeCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
		final ChromeOptions chromeOptions = new ChromeOptions();
		
		chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);	
	//	WebDriver driver;
*/
		System.setProperty("webdriver.chrome.driver",
				"D:\\chromedriver.exe");
	//	driver = new ChromeDriver(chromeCapabilities);
		
	driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
driver.get("http://172.16.8.115:9080/navigator/?desktop=Loyalty&feature=SynchonySegmentMatrixFeature&ID=SYF1234");
WebDriverWait wait=new WebDriverWait(driver, 40);
wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='ecm_widget_layout_NavigatorMainLayout_0_LoginPane_username']")));
driver.findElement(By.xpath("//*[@id='ecm_widget_layout_NavigatorMainLayout_0_LoginPane_username']")).sendKeys("p8admin");
// Enterring Password
driver.findElement(By.xpath("//*[@id='ecm_widget_layout_NavigatorMainLayout_0_LoginPane_password']")).sendKeys("mits123$");
// Wait For Page To Load
driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
// Click on 'Login' button
driver.findElement(By.xpath("//*[@id='ecm_widget_layout_NavigatorMainLayout_0_LoginPane_LoginButton']")).click();
//logger.info("Logged in");

List<WebElement> elements1 = driver.findElements(By.xpath("//*[contains(@id,'dojox_grid_EnhancedGrid_1Hdr')]"));
System.out.println(elements1.size());
List<WebElement> elements2 = driver.findElements(By.xpath("//*[contains(@id,'dojox_grid_EnhancedGrid_1Hdr')]/div"));
//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", elements2);
try {

for (int cnum = 0; cnum < elements2.size(); cnum++) {
	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", elements2.get(cnum));
	System.out.println("value " + elements2.get(cnum).getText());

}
}catch(Exception e){
	System.out.println(e);
}
count();

WebElement element=driver.findElement(By.xpath("//*[@id='dojox_grid__View_5']/div/div/div/div/table/tbody/tr"));
List<WebElement> gridcels = element.findElements(By.tagName("td"));
JavascriptExecutor js = (JavascriptExecutor) driver;
//*[@id="dojox_grid__View_5"]/div/div/div/div[2]/table/tbody/tr/td[1]
//*[@id="dojox_grid__View_5"]/div/div/div/div[2]/table/tbody/tr/td[3]
//*[@id="dojox_grid__View_5"]/div/div/div/div[1]/table/tbody/tr/td[2]
//*[@id="dojox_grid__View_5"]/div/div/div/div[1]/table/tbody/tr/td[4]
//*[@id="dojox_grid__View_5"]/div/div/div/div[2]/table/tbody/tr/td[4]
System.out.println(gridcels.size()+"headercount");

	for (int cnum = 0; cnum < gridcels.size(); cnum++) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", gridcels.get(cnum));
		System.out.println("cell" + gridcels.get(cnum).getText());

	
}}}
