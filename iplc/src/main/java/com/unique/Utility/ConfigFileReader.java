package com.unique.Utility;

import java.util.Properties;

public class ConfigFileReader {
	properFileReader obj = new properFileReader();
	Properties properties = obj.getconfigproperty();

	public String getReportConfigPath() 
	{
		String reportConfigPath = properties.getProperty("reportConfigPath");
		System.out.println(reportConfigPath);
		if (reportConfigPath != null)
			return reportConfigPath;
		else
			throw new RuntimeException(
					"Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");
	}
	
}
			