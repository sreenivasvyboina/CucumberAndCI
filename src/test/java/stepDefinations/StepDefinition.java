package stepDefinations;
import com.cucumber.listener.Reporter; 
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import cucumber.api.java.en.Then;
import mits.DriverMethods;
import util.Constants;
import util.ResourceLoader;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
public class StepDefinition {
	public static int rowcount=0;
	 DriverMethods drivermethods = new DriverMethods();
	 
	public static Logger logger = Logger.getLogger(StepDefinition.class);
	public static ResourceLoader loader=new ResourceLoader();
	
	
	
/* Driver open the browser by calling drverdetails */
	 @Given("^Driver will open Browser$")
	 public void driver_will_open_Browser() throws Throwable {
		 System.out.println("test console :::: "+loader.getProperty(Constants.WEBDRIVER_KEY));
		 
	 logger.info("open's the webdriver and Enters the URL");
		 /* Driver opens the browser */
			drivermethods.driverDetails(loader.getProperty(Constants.WEBDRIVER_KEY),loader.getProperty(Constants.WEBDRIVER_VALUE));
	Reporter.addStepLog("Opened Browser");	
	Reporter.addScenarioLog("Open browser scenario log");
	 }
	 
/* Load the url and login into application by entering credentials */
	 @Given("^Enters the URL with \"([^\"]*)\" and \"([^\"]*)\"$")
	 public void enters_the_URL_with_and(String username, String password) throws Throwable {
		 /*Driver load the URL*/
		 drivermethods.getURL(loader.getProperty(Constants.IcmUrl));
		 logger.info("enters username"+username);
/* enter th username by calling entetText method in drivermethods */	 
		 drivermethods.enterText("xpath", loader.getProperty(Constants.UserName), username);	
		 logger.info("entered Username");
		 logger.info("Enters password"+password);
 /* enter th password by calling entetText method in drivermethods */	
		 drivermethods.enterText("xpath", loader.getProperty(Constants.Password), password);
		 logger.info("entered password");
		 
/* Clicks on login button and checks the Add element in home page */
		 drivermethods.clickOnLocator("xpath", loader.getProperty(Constants.LoginID),loader.getProperty(Constants.Add));
		 logger.info("Logged in");
		 Reporter.addStepLog("logged in");	
		 Reporter.addScenarioLog("logged in");
		}
	 
/* Delete opertion will perform if there are already added row */
	 @Then("^selects all rows and clicks on subtract button$")
	 public void selects_all_rows_and_clicks_on_subtract_button() throws Throwable {
/* condition to check the rows present */	 
	if(drivermethods.isDisplyedElement("xpath",loader.getProperty(Constants.allRows))){
		logger.info("found element");
/* If there are rows, selects all the rows and performs delete operation */
		 drivermethods.clickOnLocator("xpath",loader.getProperty(Constants.SelectAll));
		 logger.info("selcted all");
	   drivermethods.ListElementClick("xpath", loader.getProperty(Constants.Buttons),"-");
	   logger.info("clicked on subtract");
	   drivermethods.ListElementClick("xpath", loader.getProperty(Constants.Buttons),"Yes");}
	else{
		logger.info("As there are no elemetns to delete it skips this scenario");
	}
	//
	Reporter.addScenarioLog("Second Scenario ");
	Reporter.addStepLog("Second Scenario Step");
	 }
	 
/*Read data from feature file and populate the values in properties from application */
	 @Then("^User enters data in porperties$")
	 public void user_enters_data_in_porperties(DataTable Values) throws Throwable {
/*List gets the total rows data */
		 List<Map<String,String>> values=Values.asMaps(String.class, String.class);
		 for(Map<String, String> map:values) {
/*getting key and value for each row */
			 rowcount++;
			 drivermethods.ListElementClick("xpath",loader.getProperty(Constants.Buttons),"+"); 
/*User performs add row operation*/
		 for(String key:map.keySet()){
			 String value=map.get(key);
			 logger.info(key+" "+value);
/* Send the data to the properties by calling Enter_Details_For_All_Properties */
drivermethods.Enter_Details_For_All_Properties("xpath",loader.getProperty(Constants.Header),loader.getProperty(Constants.CellIdentifier), value ,key,rowcount);
		 }
		// drivermethods.ListElementClick("xpath",loader.getProperty(Constants.Buttons),"Save");

		// drivermethods.clickOnLocator("xpath", "//*[@id='CANCEL_ecm_widget_dialog_MessageDialog_0']");
		 }}
//	 *[@id="CANCEL_ecm_widget_dialog_MessageDialog_0"]
 /*	 Selects save button after adding the value */	
	 
	@Given("^User Clicks save button$")
public void user_Clicks_save_button() throws Throwable {
	 drivermethods.ListElementClick("xpath",loader.getProperty(Constants.Buttons),"Save");
   
}

	
/* Closes the browser */
	@Then("^Driver closes the browser$")
	public void driver_closes_the_browser() throws Throwable {
   drivermethods.closeBrowser();
	 }
	
/* verifying login with invalid credentials */
	 @Given("^Enters the URL with invalid username \"([^\"]*)\" and \"([^\"]*)\"$")
	 public void enters_the_URL_with_invalid_username_and(String username, String password) throws Throwable {
		drivermethods.driverDetails(loader.getProperty(Constants.WEBDRIVER_KEY),loader.getProperty(Constants.WEBDRIVER_VALUE));
 /*Driver load the URL*/
		 drivermethods.getURL(loader.getProperty(Constants.IcmUrl));
		 logger.info("enters username"+username);
/* enter th username by calling entetText method in drivermethods */	
		 drivermethods.enterText("xpath", loader.getProperty(Constants.UserName), username);	
		 logger.info("entered Username");
		 logger.info("Enters password"+password);
/* enter th password by calling entetText method in drivermethods */			 
		 drivermethods.enterText("xpath", loader.getProperty(Constants.Password), password);
		 logger.info("entered password");
/* enter th password by calling entetText method in drivermethods */
		 drivermethods.clickOnLocator("xpath", loader.getProperty(Constants.LoginID));
		 logger.info("Logged in");
/* get the invalid details text by checking the element */
		 drivermethods.checksElements("xpath",loader.getProperty(Constants.homepageelement),loader.getProperty(Constants.InvalidCredentila),username,password);
	 }
	}

