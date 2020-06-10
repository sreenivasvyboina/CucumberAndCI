package MyRunner;

import java.io.File;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;


/*import org.testng.annotations.Test;*/
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
/*
import cucumber.api.testng.AbstractTestNGCucumberTests;
*/

@RunWith(Cucumber.class)
@CucumberOptions(
		features="C:\\Users\\vybha\\git\\CucumberAndCI\\src\\test\\java\\features\\GenericAutomation.feature",
		glue= {"stepDefinations"},
	 plugin = {"com.cucumber.listener.ExtentCucumberFormatter: target/cucumber-reports/report.html", "html:target/cucumber-html-report1","json:target/cucumber-report/cucumber.json1"},
	 monochrome = true,
	 dryRun=false
		)

public class TestRunner 
{				     
	@AfterClass
	public static void writeExtentReport() {
		       Reporter.loadXMLConfig(new File("C:\\Users\\vybha\\git\\CucumberAndCI\\extent-config.xml"));
	}			
}

/* 
		        "json:target/cucumber.json", "pretty:target/cucumber-pretty.txt",
		        "usage:target/cucumber-usage.json", "junit:target/cucumber-results.xml",*/

