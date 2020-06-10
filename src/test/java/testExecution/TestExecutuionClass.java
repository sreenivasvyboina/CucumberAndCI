package testExecution;

import org.junit.runner.JUnitCore;
import MyRunner.TestRunner;

public class TestExecutuionClass {
	public static void main(String[] args) {
         JUnitCore.runClasses(TestRunner.class);
	//void resultReport(result);
}

	}
