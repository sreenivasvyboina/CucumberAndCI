package mits;

import util.ResourceLoader;

public class ConfigFileReader {
	 static ResourceLoader loader=new ResourceLoader();
	public static String getReportConfigPath(){
		String reportConfigPath = loader.getProperty("reportConfigPath");
		if(reportConfigPath!= null) return reportConfigPath;
		else throw new RuntimeException("Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}
}
