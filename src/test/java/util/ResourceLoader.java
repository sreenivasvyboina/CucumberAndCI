package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/*
 * 
 * 
 */
public class ResourceLoader {

	public static Logger logger = Logger.getLogger(ResourceLoader.class);
	public static Properties properties = new Properties();

	public ResourceLoader() {
		// calling loadProperties method
		loadProperties();
	}

	private static void loadProperties() {

		InputStream inputStream = null;
		try {

			inputStream = ResourceLoader.class.getClassLoader().getResourceAsStream(Constants.CE_PROPERTIES_CONFIG);

			if (inputStream != null) {
				properties.load(inputStream);
			}

		} catch (java.io.IOException e) {

			logger.error("Error while loading the propery file:: " + e);

		} finally {

			if (inputStream != null) {

				try {

					inputStream.close();

				} catch (IOException e) {

					logger.error("Error while loading the propery file:: " + e);

				}

			}

		}

	}

	public String getProperty(String propertyName) {

		String value = "";

		if (properties != null) {

			value = properties.getProperty(propertyName);

			if (value != null) {

				value = value.trim();

			} else {

				logger.error("The property not available in ce.properties:: " + propertyName);
			}

		}

		return value;
	}
}
