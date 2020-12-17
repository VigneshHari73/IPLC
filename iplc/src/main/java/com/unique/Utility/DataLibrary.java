package com.unique.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DataLibrary {
	static RemoteWebDriver driver;
	properFileReader obj = new properFileReader();
	Properties properties = obj.getproperty();

	public FileInputStream fis = null;
	public XSSFWorkbook workbook = null;
	public XSSFSheet sheet = null;
	public XSSFRow row = null;
	public XSSFCell cell = null;
	String XlFilepath;

	public String getCellValue(String SheetName, int colNum, int rowNum) throws IOException 
	{
		fis = new FileInputStream(properties.getProperty("xlpath"));
		workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(SheetName);
		XSSFRow row = sheet.getRow(rowNum);
		XSSFCell cell = row.getCell(colNum);

		if (cell.getCellTypeEnum() == CellType.STRING)
			return cell.getStringCellValue();
		else if (cell.getCellTypeEnum() == CellType.NUMERIC) 
		{
			String cellValue = String.valueOf(cell.getNumericCellValue());
			if (HSSFDateUtil.isCellDateFormatted(cell)) 
			{
				DateFormat df = new SimpleDateFormat("YYYY-MM-DD");
				Date date = cell.getDateCellValue();
				cellValue = df.format(date);
			}
			return cellValue;
		} else if (cell.getCellTypeEnum() == CellType.BLANK)
			return "";
		else
			return String.valueOf(cell.getBooleanCellValue());
	}
}