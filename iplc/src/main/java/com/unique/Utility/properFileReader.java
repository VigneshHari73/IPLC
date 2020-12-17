package com.unique.Utility;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

public class properFileReader {
	static WebDriver driver;

	public Properties getproperty()
	{
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("Resources\\iplc-config.properties"));
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		return properties;
	}
	public Properties getconfigproperty() 
	{
		Properties properties = new Properties();
		try 
		{
			properties.load(new FileInputStream("Resources\\Configuration.properties"));
		} catch (Exception e) 
		{
			System.out.println("Exception: " + e);
		}
		return properties;
	}

}
