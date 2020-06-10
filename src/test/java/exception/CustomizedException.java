package exception;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class CustomizedException extends Exception {
	/**
	 * Customized exceptions
	 */
	private static final long serialVersionUID = 1L;
	public static Logger logger = Logger.getLogger(CustomizedException.class);

	String message=null;
	
	
	/**
	 * default Constructor
	 */
    public CustomizedException() {
        super();
    }
    
    /**
     * Contructor with String as arguments to throw the exception message
     * @param message --exceptionmessage
     */
    public CustomizedException(String message) {
        super(message);
        this.message = message;
        logger.info(message);
    }
   
	public CustomizedException(Exception exception, WebDriver driver) {
		// driver.quit();
		
		logger.info("The webdriver is not configured properly please check****** ");
		
	}

	public CustomizedException(Exception exception, String data) {
		logger.info("The case id is not available in workplace please check---->>>");

	}

	public CustomizedException(Exception exception) {
		// driver.quit();
		logger.info("The exception is  ");

	}
	
}
