package com.unique.base;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cucumber.listener.Reporter;
import com.google.common.io.Files;
import com.unique.Utility.properFileReader;

import Design.Browser;
import Design.Element;
import cucumber.api.Scenario;

public class ProjectBase implements Browser, Element {

	public static HashMap<String, String> MT707_DATA_MAP = new HashMap<String, String>();
	public static HashMap<String, String> hash_map = new HashMap<String, String>();
	public static HashMap<String, String> ISSSWI_DATA_MAP = new HashMap<String, String>();
	properFileReader obj = new properFileReader();
	Properties properties = obj.getproperty();
	public static RemoteWebDriver driver;
	public WebDriverWait wait;
	Scenario scn;
	int i = 1;

	public static String Mandatory = "153, 204, 255";
	public static String Optional = "255, 255, 255";
	public static String Protected = "242, 242, 242";

	public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public Date date = new Date();
	private String x;

	public static String regex = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$$";

	@Override
	public void click(WebElement ele) {
		String text = "";
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			text = ele.getText();
			ele.click();
		} catch (StaleElementReferenceException e) {
			throw new RuntimeException();
		}
	}

	public void clickWithNoSnap(WebElement ele) {
		String text = "";
		try {
			text = ele.getText();
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			ele.click();
		} catch (StaleElementReferenceException e) {

			throw new RuntimeException();
		} catch (Exception e) {
			System.err.println(e);
		}

	}

	@Override
	public void append(WebElement ele, String data) {
		ele.sendKeys(data);
	}

	@Override
	public void clear(WebElement ele) {
		try {
			ele.clear();
		} catch (ElementNotInteractableException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void clearAndType(WebElement ele, String data) {
		try {
			ele.clear();
			ele.sendKeys(data);
		} catch (ElementNotInteractableException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public String getElementText(WebElement ele) {
		String text = ele.getText();
		return text;
	}

	@Override
	public String getBackgroundColor(WebElement ele) {
		String cssValue = ele.getCssValue("background-color");
		return cssValue;
	}

	@Override
	public String getTypedText(WebElement ele) {
		String attributeValue = ele.getAttribute("value");
		return attributeValue;
	}

	@Override
	public void selectDropDownUsingText(WebElement ele, String value) {

		new Select(ele).selectByVisibleText(value);
	}

	@Override
	public void selectDropDownUsingIndex(WebElement ele, int index) {
		new Select(ele).selectByIndex(index);
	}

	@Override
	public void selectDropDownUsingValue(WebElement ele, String value) {
		new Select(ele).selectByValue(value);
	}

	@Override
	public boolean verifyExactText(WebElement ele, String expectedText) {
		try {
			if (ele.getText().equals(expectedText)) {
				return true;
			} else {

			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Text");
		}
		return false;
	}

	@Override
	public boolean verifyPartialText(WebElement ele, String expectedText) {
		try {
			if (ele.getText().contains(expectedText)) {
				return true;
			} else {
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Text");
		}

		return false;
	}

	@Override
	public boolean verifyExactAttribute(WebElement ele, String attribute, String value) {
		try {
			if (ele.getAttribute(attribute).equals(value)) {
				return true;
			} else {
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Attribute Text");
		}
		return false;
	}

	@Override
	public void verifyPartialAttribute(WebElement ele, String attribute, String value) {
		try {
			if (ele.getAttribute(attribute).contains(value)) {
			} else {
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Attribute Text");
		}

	}

	@Override
	public boolean verifyDisplayed(WebElement ele) {
		try {
			if (ele.isDisplayed()) {
				return true;
			} else {

			}
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}
		return false;

	}

	@Override
	public boolean verifyDisappeared(WebElement ele) {
		return false;
	}

	@Override
	public boolean verifyEnabled(WebElement ele) {
		try {
			if (ele.isEnabled()) {
				return true;
			} else {

			}
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}
		return false;
	}

	@Override
	public void verifySelected(WebElement ele) {
		try {
			if (ele.isSelected()) {
				// return true;
			} else {
			}
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}
		// return false;
	}

	@Override
	public RemoteWebDriver startApp(String url) {
		return startApp("chrome", url);
	}

	@Override
	public RemoteWebDriver startApp(String browser, String url) {
		try {
			if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
				driver = new ChromeDriver();
			} else if (browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
				driver = new FirefoxDriver();
			} else if (browser.equalsIgnoreCase("ie")) {
				System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
			driver.navigate().to(url);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return driver;

	}

	@Override
	public WebElement locateElement(String locatorType, String value) {
		try {
			switch (locatorType.toLowerCase()) {
			case "id":
				return driver.findElementById(value);
			case "name":
				return driver.findElementByName(value);
			case "class":
				return driver.findElementByClassName(value);
			case "link":
				return driver.findElementByLinkText(value);
			case "xpath":
				return driver.findElementByXPath(value);
			case "css":
				return driver.findElementByCssSelector(value);

			}
		} catch (NoSuchElementException e) {

			throw new RuntimeException();
		} catch (Exception e) {

		}
		return null;
	}

	@Override
	public WebElement locateElement(String value) {
		WebElement findElementById = driver.findElementById(value);
		return findElementById;
	}

	@Override
	public String trimNumbers(String value) {
		return value.substring(0, 6);
	}

	@Override
	public List<WebElement> locateElements(String type, String value) {
		try {
			switch (type.toLowerCase()) {
			case "id":
				return driver.findElementsById(value);
			case "name":
				return driver.findElementsByName(value);
			case "class":
				return driver.findElementsByClassName(value);
			case "link":
				return driver.findElementsByLinkText(value);
			case "xpath":
				return driver.findElementsByXPath(value);
			}

		} catch (NoSuchElementException e) {
			System.err.println("The Element with locator:" + type + " Not Found with value: " + value);
			throw new RuntimeException();
		}
		return null;
	}

	@Override
	public void switchToAlert() {
		driver.switchTo().alert();
	}

	@Override
	public void acceptAlert() {
		String text = "";
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			alert.accept();
		} catch (NoAlertPresentException e) {

		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}

	}

	@Override
	public void dismissAlert() {
		String text = "";
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			alert.dismiss();
			System.out.println("The alert " + text + " is accepted.");
		} catch (NoAlertPresentException e) {
			System.out.println("There is no alert present.");
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}

	}

	@Override
	public String getAlertText() {
		String text = "";
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
		} catch (NoAlertPresentException e) {
			System.out.println("There is no alert present.");
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}
		return text;
	}

	@Override
	public void typeAlert(String data) {
		driver.switchTo().alert().sendKeys(data);

	}

	@Override
	public void switchToWindow(int index) {
		try {
			Set<String> allWindows = driver.getWindowHandles();
			List<String> allhandles = new ArrayList<String>(allWindows);
			String exWindow = allhandles.get(index);
			driver.switchTo().window(exWindow);
			System.out.println("The Window With index: " + index + " switched successfully");
		} catch (NoSuchWindowException e) {
			System.err.println("The Window With index: " + index + " not found");
		}
	}

	@Override
	public void switchToWindow(String title) {
		try {
			Set<String> allWindows = driver.getWindowHandles();
			for (String eachWindow : allWindows) {
				driver.switchTo().window(eachWindow);
				if (driver.getTitle().equals(title)) {
					break;
				}
			}
			System.out.println("The Window With Title: " + title + "is switched ");
		} catch (NoSuchWindowException e) {
			System.err.println("The Window With Title: " + title + " not found");
		}
	}

	@Override
	public void switchToWindow() {
		try {
			Set<String> windowId = driver.getWindowHandles();
			Iterator<String> itererator = windowId.iterator();
			String mainWinID = itererator.next();
			String newAdwinID = itererator.next();
			driver.switchTo().window(newAdwinID);
			System.out.println("The Window switched successfully");
		} catch (NoSuchWindowException e) {
			System.err.println("The Window not found");
		}
	}

	@Override
	public void switchToFrame(int index) {
		driver.switchTo().frame(index);

	}

	@Override
	public void switchToFrame(WebElement ele) {
		driver.switchTo().frame(ele);

	}

	@Override
	public void switchToFrame(String idOrName) {
		driver.switchTo().frame(idOrName);

	}

	@Override
	public void defaultContent() {
		driver.switchTo().defaultContent();

	}

	@Override
	public boolean verifyUrl(String url) {
		if (driver.getCurrentUrl().equals(url)) {
			System.out.println("The url: " + url + " matched successfully");
			return true;
		} else {
			System.out.println("The url: " + url + " not matched");
		}
		return false;
	}

	@Override
	public boolean verifyTitle(String title) {
		if (driver.getTitle().equals(title)) {
			System.out.println("Page title: " + title + " matched successfully");
			return true;
		} else {
			System.out.println("Page url: " + title + " not matched");
		}
		return false;
	}

	@Override
	public void close() {
		driver.close();
	}

	@Override
	public void quit() {
		driver.quit();
	}

	@Override
	public void takesnap(String screenShotName) {
		try {
			TakesScreenshot scrnshot = (TakesScreenshot) driver;
			File sourcePath = scrnshot.getScreenshotAs(OutputType.FILE);
			// Building up the destination path for the screenshot to save
			// Also make sure to create a folder 'screenshots' with in the cucumber-report
			// folder
			File destinationPath = new File(
					System.getProperty("user.dir") + "/target/cucumber-reports/Screenshots/" + screenShotName + ".png");
			Files.copy(sourcePath, destinationPath);
			// This attach the specified screenshot to the test
			Reporter.addScreenCaptureFromPath(destinationPath.toString());
			/*
			 * Reporter.addScreenCaptureFromPath(
			 * "D:\\Vignesh\\Selenium\\New folder\\Programs\\iplc\\target\\cucumber-reports\\Screenshots"
			 * );
			 */
		} catch (IOException e) {

		}

	}

	@Override
	public String value(String value) {
		return properties.getProperty(value);
	}

	@Override
	public String log(String result) {
		Reporter.addStepLog((result + "<br>"));
		return result;

	}

	@Override
	public boolean lengthValidation(WebElement ele, int limit, String data, String ssName) throws InterruptedException {
		try {
			/*
			 * if (getTypedText(ele).length() > limit) { if (getAlertText() == null) {
			 * takesnap(fieldName + " Length check"); log(fieldName +
			 * " Length check: FAIL"); return false; } else { acceptAlert(); log(fieldName +
			 * " Length check: PASS"); }
			 * 
			 * }
			 */

			String maxLength = ele.getAttribute("maxlength");
			int length = Integer.valueOf(maxLength);
			if (length > limit) {
				log(ssName + " Length_Validation: FAIL");
				append(ele, data);
				takesnap(ssName + "_Length_Validation");
			} else {
				log(ssName + "_Length_Validation: PASS");
				append(ele, data);
			}
		} catch (Exception e) {

		}
		return true;
	}

	@Override
	public boolean lengthValidation2(WebElement ele, String limit, String data, String ssName)
			throws InterruptedException {
		try {
			/*
			 * if (getTypedText(ele).length() > limit) { if (getAlertText() == null) {
			 * takesnap(fieldName + " Length check"); log(fieldName +
			 * " Length check: FAIL"); return false; } else { acceptAlert(); log(fieldName +
			 * " Length check: PASS"); }
			 * 
			 * }
			 */
			String rows = ele.getAttribute("rows");
			String cols = ele.getAttribute("cols");
			String actual = rows + "*" + cols;
			if (actual.equalsIgnoreCase(limit)) {
				log(ssName + "_Length_Validation: PASS");
				append(ele, data);

			} else {
				log(ssName + "_Length_Validation: FAIL");
				takesnap(ssName + "_Length_Validation");
				Reporter.setTestRunnerOutput(ssName + "_Length_Validation: FAIL");
				append(ele, data);
			}
		} catch (Exception e) {

		}
		return true;
	}

	@Override
	public boolean isMandatory(WebElement ele, String methodname) throws InterruptedException {
		/* Thread.sleep(3000); */
		String backgroundColor = getBackgroundColor(ele);
		if (backgroundColor.contains(Mandatory)) {
			log(methodname + ": PASS");
			return true;
		} else {
			takesnap(methodname);
			log(methodname + ": FAIL");
		}
		return false;
	}

	@Override
	public boolean verifyNarrativeEnable(WebElement eleNarrative, String value, String methodName) {
		if (eleNarrative.isDisplayed()) {
			log(methodName + ": PASS");
			return true;
		} else {
			log(methodName + ": PASS");
			takesnap(methodName);
		}
		return false;
	}

	public boolean verifyNarrativeDisable(WebElement eleNarrative, String value, String methodName) {
		String narratieAttribute = eleNarrative.getAttribute("style");
		if (narratieAttribute.equalsIgnoreCase("visibility: visible;")) {
			log(methodName + " When field value is not set to" + value + ": FAIL");
			takesnap(methodName + " When field value is not set to" + value + ": FAIL");
			return true;
		} else {
			log(methodName + " When field value is not set to " + value + ": PASS");
			return false;
		}
	}

	public void dateCheck(WebElement ele, String data, String dateName) throws InterruptedException, ParseException {
		try {
			append(ele, data);
			/* Thread.sleep(3000); */
			String fieldDate = getTypedText(ele);
			if (fieldDate.trim().matches(regex) || fieldDate.equals("")) {
				log(dateName + " Format Check: PASS");
			} else {
				acceptAlert();
				log(dateName + " Format Check: FAIL");
				System.err.println("Please Enter the" + dateName + " in valid Format");
				takesnap(dateName + " Format Check");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void albhaCheck(WebElement ele, String data, String dateName) {
		try {
			if (!data.isEmpty())
				append(ele, data);
			/* Thread.sleep(3000); */
			if (getAlertText() == null) {
				log(dateName + "_Albha_Check: FAIL");
				System.err.println("Please Enter the valid data in " + dateName);
			} else {
				acceptAlert();
				log(dateName + "_Alpha_Check: PASS");
				System.err.println("Please Enter the valid data in " + dateName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean negativeValue(Double Amount, String ssName) {
		/* if (Amount < 0) { */
		if (getAlertText() == null) {
			log(ssName + "_Negative_Value_Check: FAIL");
			takesnap(ssName + "_Negative_Value_Check:");
			System.err.println("Please Enter the valid data in " + ssName + " field.");
			return false;
		} else {
			acceptAlert();
			log(ssName + "_Negative_Value_Check: PASS");
			System.err.println("Please Enter the valid data in " + ssName + " field.");
		}
		/* } */
		return true;
	}

	public void alphaValueCheck(WebElement ele, String ssName) throws InterruptedException {
		String alpha = "[a-zA-Z]*";
		try {
			if (getTypedText(ele).contains(alpha)) {
				log(ssName + "_Alpha_Value_Check: FAIL");
				takesnap(ssName + "_Alpha_Value_Check");
				System.err.println("Please Enter the valid data in " + ssName + " field.");
			} else {
				log(ssName + "_Alpha_Value_Check: PASS");

			}

		} catch (Exception e) {

		}

	}

	public double convertToDouble(String Amount) {
		String trim1 = Amount.replace(".00", " ").trim();
		String replace = trim1.replace(",", "");
		double parseAmount = Double.parseDouble(replace);
		return parseAmount;
	}

	@Override
	public void manualEntry(WebElement eleBank, String bankliaAcc) {
		append(eleBank, bankliaAcc);
	}

	@Override
	public void datevalidations(WebElement ele, String elenarrative) throws InterruptedException {
		/* Thread.sleep(3000); */
		append(ele, elenarrative);
		Actions actions = new Actions(driver);
		/* Thread.sleep(5000); */
		actions.moveToElement(ele)
				.moveToElement(
						driver.findElementByXPath("//*[@id=\"E_div\"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td[3]"))
				.click().build().perform();
		Date Current_date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		WebElement eleDiarydate = locateElement("id", ("DIARY_DT"));
		String date1 = formatter.format(Current_date);
		/* Thread.sleep(1000); */
		String date = eleDiarydate.getAttribute("value");
		if (date.equals(date1)) {
			log("Diary Due Date should be always Current date:PASS");
		} else {
			log("Diary Due Date is not current date: FAIL");
		}
		if (date.equals(date1)) {
			log("DiaryDueDate Format YYYY-MM-DD:PASS");
		} else {
			log("[W4423] Diary Due Date format is error, please use YYYY-MM-DD.");
			/* Thread.sleep(3000); */
			takesnap("Diary Due Date is invalid format");
		}
	}

	public void confirm() throws InterruptedException {
		driver.switchTo().parentFrame();
		switchToFrame("eeToolbar");
		WebElement confirm = locateElement("id", "_confirm");
		click(confirm);
	}

	public void swift() {
		driver.switchTo().parentFrame();
		switchToFrame("eeToolbar");
		WebElement swift = locateElement("id", "_preswift");
		click(swift);
	}

	public void cancel() throws InterruptedException {
		driver.switchTo().parentFrame();
		switchToFrame("eeToolbar");
		WebElement cancel = locateElement("id", "_cancel");
		click(cancel);
	}

	public void release(String data) throws InterruptedException {
		try {
			Thread.sleep(3000);
			// defaultContent();
			driver.switchTo().defaultContent();
			driver.switchTo().frame("FunctionList");
			driver.findElementByName("IPLC Maintenance").click();
			driver.findElementByName("G49082300274F05030702041").click();
			Thread.sleep(3000);
			driver.switchTo().defaultContent();
			driver.switchTo().frame("work");
			Thread.sleep(3000);
			WebElement eleRef = locateElement("xpath",
					"(//form[@name='catalog' ]//table)[2]/tbody[1]/tr[5]/td[4]/input[1]");
			append(eleRef, data);
			Thread.sleep(2000);
			driver.switchTo().parentFrame();
			switchToFrame("eeToolbar");
			Thread.sleep(2000);
			WebElement next = locateElement("id", "_next");
			click(next);
			Thread.sleep(2000);
			driver.switchTo().defaultContent();
			driver.switchTo().frame("work");
			Thread.sleep(2000);
			WebElement eleRelease = locateElement("id", "transaction");
			click(eleRelease);
			Thread.sleep(2000);
			driver.switchTo().parentFrame();
			switchToFrame("eeToolbar");
			Thread.sleep(2000);
			WebElement confirm = locateElement("id", "_confirm");
			click(confirm);

		} catch (Exception e) {

		}

	}

	public void catalog(String data) throws InterruptedException {
		// defaultContent();
		Thread.sleep(1000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("work");
		/* Thread.sleep(1000); */
		WebElement eleRef = locateElement("xpath",
				"(//form[@name='catalog' ]//table)[2]/tbody[1]/tr[5]/td[4]/input[1]");
		append(eleRef, data);
		Thread.sleep(3000);
		driver.switchTo().parentFrame();
		switchToFrame("eeToolbar");
		Thread.sleep(2000);
		WebElement next = locateElement("id", "_next");
		click(next);
		Thread.sleep(3000);
		WebElement next1 = locateElement("id", "_next");
		click(next1);
	}

	public void swiftContent(String exp, String act, String ssName) {
		try {
			if (act.equalsIgnoreCase(exp)) {
				log(ssName + "_Content_Check: PASS");
			} else {
				log(ssName + "_Content_Check: FAIL");
				takesnap(ssName + "_Content Check");
			}
		} catch (Exception e) {

		}

	}

	public void swiftTag(String act, String exp, String ssName) {
		try {
			if (act.replaceAll("\\s\\s", "").equals(exp)) {
				log(ssName + "_Tag_Check: PASS");
			} else {
				log(ssName + "_Tag_Check: FAIL");
				takesnap(ssName + "_Tag_Check");
			}
		} catch (Exception e) {

		}
	}

	@Override
	public String curSts(String functionName) {
		try {
			String curSts;
			switch (functionName.toLowerCase().replaceAll(" ", "")) {
			case "issueletterofcredit":
				return curSts = "IPLC_ISS_LC";
			case "registerdocumentlc":
				return curSts = "IPLC_DocumentRegister";
			case "checkdocument":
				return curSts = "IPLC_DocumentCheck";
			case "beneficiaryresponse":
				return curSts = "IPLC_BeneficiaryResponse";
			}
		} catch (Exception e) {

		}
		return null;
	}

	@Override
	public String subMess(String functionName) {
		try {
			String SUB_MSG_TYPE = "";
			switch (functionName.toLowerCase().replaceAll(" ", "")) {
			case "issueletterofcredit":
				return SUB_MSG_TYPE = "IMLC.004.ImpLcIsse";
			case "registerdocumentlc":
				return SUB_MSG_TYPE = "IMLC.011.DocPres";
			case "checkdocument":
				return SUB_MSG_TYPE = "IMLC.012.DiscrpcsNtfctn";
			case "beneficiaryresponse":
				return SUB_MSG_TYPE = "IMLC.008.Amd";
			}
		} catch (Exception e) {

		}
		return null;
	}

	@Override
	public String nxtSts(String functionName) {
		try {
			String nxtSts;
			switch (functionName.toLowerCase().replaceAll(" ", "")) {
			case "issueletterofcredit":
				return nxtSts = "AmdLC";
			case "beneficiaryresponse":
				return nxtSts = "AmdLC";
			}
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void gapiContent(String act, String exp, String ssName) {
		
			if (act.contains(exp)) {
				Reporter.addStepLog((ssName + "_Content_Check: PASS" + "<br>"));
			} else {
				System.out.println("Act - " + act + " | Exp" + exp);
				Reporter.addStepLog((ssName + "_Content_Check: FAIL" + "<br>"));
				Reporter.setTestRunnerOutput(ssName+"_Content_Check: FAIL"+"<br>");
			}
	}

	public void gapiValidation(String functionName) throws InterruptedException {
		Thread.sleep(6000);
		switchToWindow(1);
		driver.manage().window().maximize();
		Thread.sleep(6000);
		if (locateElement("xpath", "(//a)[1]").isDisplayed()) {
			log(functionName + "Output - EEOUT_GAPI - PASS ");
			click(locateElement("xpath", "(//a)[1]"));
			Thread.sleep(6000);
			switchToWindow(2);
			driver.manage().window().maximize();
			Thread.sleep(6000);

			WebElement table = locateElement("xpath",
					"/html/body/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/div/table/tbody/tr[4]/td/div/table");
			List<WebElement> column = table.findElements(By.tagName("td"));
			List<String> listGapi = new ArrayList<String>();
			for (int i = 0; i < column.size(); i++) {
				listGapi.add(column.get(i).getText());

			}
			String functionName1 = functionName.replaceAll(" ", "").toLowerCase();
			for (int i = 1; i < listGapi.size(); i = i + 2) {
				if (i == listGapi.indexOf("Transhipment") || i == listGapi.indexOf("TpOfLc")
						|| i == listGapi.indexOf("ShipTo") || i == listGapi.indexOf("ShipFr")
						|| i == listGapi.indexOf("PrtlShipmnt") || i == listGapi.indexOf("PortOfLoadng")
						|| i == listGapi.indexOf("PortOfDschrge") || i == listGapi.indexOf("PlcOfExp")
						|| i == listGapi.indexOf("NxtSts") || i == listGapi.indexOf("LatstShipmntDt")
						|| i == listGapi.indexOf("Incotrms") || i == listGapi.indexOf("ForAcctOfId")
						|| i == listGapi.indexOf("DraftAt") || i == listGapi.indexOf("ConfInstr")
						|| i == listGapi.indexOf("BnfcryBkAdr2") || i == listGapi.indexOf("AdditnlCondtin")
						|| i == listGapi.indexOf("DtOfIsse") || i == listGapi.indexOf("DescOfGoods")
						|| i == listGapi.indexOf("ForAcctOfNm")) {
					if (functionName1.equalsIgnoreCase("issueletterofcredit")
							|| functionName1.equalsIgnoreCase("beneficiaryresponse")) {
						int index = listGapi.indexOf(listGapi.get(i));
						String actual = listGapi.get(index + 1);
						String expec = ISSSWI_DATA_MAP.get(listGapi.get(i));
                        if(i == listGapi.indexOf("DescOfGoods")||i == listGapi.indexOf("AdditnlCondtin")) {
                        	actual = actual.replaceAll("\n", "").replaceAll(" ", "");
                        	expec = expec.replaceAll("\n", "").replaceAll(" ", "");
                        }
						
						if (i == listGapi.indexOf("NxtSts")) {
							expec = nxtSts(functionName1);
						}

						gapiContent(actual, expec, curSts(functionName1) + "_EEOUT_" + listGapi.get(i));
						System.out.println("Act - " + actual + " | Exp" + expec);
						continue;
					}
				}
				if (i == listGapi.indexOf("TtlAmtPayab") || i == listGapi.indexOf("PrestinCcy")
						|| i == listGapi.indexOf("PrestinBala") || i == listGapi.indexOf("PrestinAmt")
						|| i == listGapi.indexOf("NoOfDrawg") || i == listGapi.indexOf("DrawgRefNb")
						|| i == listGapi.indexOf("DocDecsin") || i == listGapi.indexOf("DocPres")
						|| i == listGapi.indexOf("AdditnlPrestinBkChrgs") || i == listGapi.indexOf("DtChkd")) {
					if (functionName1.equalsIgnoreCase("registerdocumentlc")
							|| functionName1.equalsIgnoreCase("checkdocument")) {
						int index = listGapi.indexOf(listGapi.get(i));
						String actual = listGapi.get(index + 1);
						String expec = ISSSWI_DATA_MAP.get(listGapi.get(i));
						if(i == listGapi.indexOf("DocDecsin")||i == listGapi.indexOf("DocPres")) {
							actual = actual.replaceAll("\r\n", "").replaceAll("-", "").replaceAll(" ", "");
							expec = expec.replaceAll("\r\n", "").replaceAll("-", "").replaceAll(" ", "");
						}
						gapiContent(actual, expec, curSts(functionName1) + "_EEOUT_" + listGapi.get(i));
						System.out.println("Act - " + actual + " | Exp" + expec);
						continue;
					}
				}
				if (i == listGapi.indexOf("CurSts")) {
					if (functionName1.equalsIgnoreCase("issueletterofcredit")
							|| functionName1.equalsIgnoreCase("registerdocumentlc")
							|| functionName1.equalsIgnoreCase("checkdocument")) {
						int index = listGapi.indexOf(listGapi.get(i));
						String actual = listGapi.get(index + 1);
						String expec = curSts(functionName1);
						gapiContent(actual, expec, curSts(functionName1) + "_EEOUT_" + listGapi.get(i));
						System.out.println("Act - " + actual + " | Exp" + expec);
						continue;
					}
				}
				if (i == listGapi.indexOf("IncotrmsInstr") || i == listGapi.indexOf("TenorTp")
						|| i == listGapi.indexOf("TenorDays")) {
					if (functionName1.equalsIgnoreCase("issueletterofcredit")) {
						int index = listGapi.indexOf(listGapi.get(i));
						String actual = listGapi.get(index + 1);
						String expec = ISSSWI_DATA_MAP.get(listGapi.get(i));
						gapiContent(actual, expec, curSts(functionName1) + "_EEOUT_" + listGapi.get(i));
						System.out.println("Act - " + actual + " | Exp" + expec);
						continue;
					}
				}
				if (i == listGapi.indexOf("DiscrpcsNote")) {
					if (functionName1.equalsIgnoreCase("checkdocument")) {
						int index = listGapi.indexOf(listGapi.get(i));
						String actual = listGapi.get(index + 1);
						String expec = ISSSWI_DATA_MAP.get(listGapi.get(i));
						gapiContent(actual, expec, curSts(functionName1) + "_EEOUT_" + listGapi.get(i));
						System.out.println("Act - " + actual + " | Exp" + expec);
						continue;
					}
				}
				if (i == listGapi.indexOf("NewDtOfExp") || i == listGapi.indexOf("DecAmt")
						|| i == listGapi.indexOf("AccptRjct") || i == listGapi.indexOf("IncAmt")
						|| i == listGapi.indexOf("NoOfAmdmnt")) {
					if (functionName1.equalsIgnoreCase("beneficiaryresponse")) {
						int index = listGapi.indexOf(listGapi.get(i));
						String actual = listGapi.get(index + 1);
						String expec = ISSSWI_DATA_MAP.get(listGapi.get(i));
						gapiContent(actual, expec, curSts(functionName1) + "_EEOUT_" + listGapi.get(i));
						System.out.println("Act - " + actual + " | Exp" + expec);
						continue;
					}
				}
				if (i == listGapi.indexOf("MSG_TYPE") || i == listGapi.indexOf("CustId")
						|| i == listGapi.indexOf("LcNb") || i == listGapi.indexOf("ApplId")
						|| i == listGapi.indexOf("DtOfExp") || i == listGapi.indexOf("BnfcryNm")
						|| i == listGapi.indexOf("LcCcy") || i == listGapi.indexOf("LcAmt")
						|| i == listGapi.indexOf("AdditnlAmtCovrd") || i == listGapi.indexOf("ApplNm")
						|| i == listGapi.indexOf("BkMainRef") || i == listGapi.indexOf("BnfcryAdr1")
						|| i == listGapi.indexOf("BnfcryAdr2") || i == listGapi.indexOf("BnfcryBkAdr1")
						|| i == listGapi.indexOf("BnfcryBkAdr3") || i == listGapi.indexOf("BnfcryBkId")
						|| i == listGapi.indexOf("BnfcryBkNm") || i == listGapi.indexOf("BnfcryBkSwAdr")
						|| i == listGapi.indexOf("DtRecvd") || i == listGapi.indexOf("DueDt")
						|| i == listGapi.indexOf("LcBala") || i == listGapi.indexOf("NegtveTlrnce")
						|| i == listGapi.indexOf("PmtTp") || i == listGapi.indexOf("PostveTlrnce")
						|| i == listGapi.indexOf("SUB_MSG_TYPE") || i == listGapi.indexOf("AvalBy")) {
					int index = listGapi.indexOf(listGapi.get(i));
					String actual = listGapi.get(index + 1);
					String expec = "";
					if (functionName1.equalsIgnoreCase("checkdocument")
							|| functionName1.equalsIgnoreCase("registerdocumentlc")) {
//						//With Amendment
						if (!ISSSWI_DATA_MAP.get("NoOfAmdmnt").equals(null)
								|| !ISSSWI_DATA_MAP.get("NoOfAmdmnt").isEmpty()) {
							// Accepted
							if (ISSSWI_DATA_MAP.get("AccptRjct").equalsIgnoreCase("Accepted")) {
								expec = MT707_DATA_MAP.get(listGapi.get(i));
								if(i == listGapi.indexOf("AdditnlAmtCovrd")) {
		                        	actual = actual.replaceAll("\n", "").replaceAll(" ", "").toLowerCase();
		                        	expec = expec.replaceAll("\n", "").replaceAll(" ", "").toLowerCase();
		                        }
								if (i == listGapi.indexOf("SUB_MSG_TYPE")) {
									expec = subMess(functionName1);
								}
							}
							// Rejected
							if (ISSSWI_DATA_MAP.get("AccptRjct").equalsIgnoreCase("Rejected")) {
								expec = ISSSWI_DATA_MAP.get(listGapi.get(i));
								if(i == listGapi.indexOf("AdditnlAmtCovrd")) {
		                        	actual = actual.replaceAll("\n", "").replaceAll(" ", "").toLowerCase();
		                        	expec = expec.replaceAll("\n", "").replaceAll(" ", "").toLowerCase();
		                        }
								if (i == listGapi.indexOf("SUB_MSG_TYPE")) {
									expec = subMess(functionName1);
								}
							}
						}
					// Without Amendment
						if (ISSSWI_DATA_MAP.get("NoOfAmdmnt").equals(null)
								|| ISSSWI_DATA_MAP.get("NoOfAmdmnt").isEmpty()) {
							expec = ISSSWI_DATA_MAP.get(listGapi.get(i));
							
							if(i == listGapi.indexOf("AdditnlAmtCovrd")) {
	                        	actual = actual.replaceAll("\n", "").replaceAll(" ", "").toLowerCase();
	                        	expec = expec.replaceAll("\n", "").replaceAll(" ", "").toLowerCase();
	                        }
							if (i == listGapi.indexOf("SUB_MSG_TYPE")) {
								expec = subMess(functionName1);
							}
						}
					} else {
						expec = ISSSWI_DATA_MAP.get(listGapi.get(i));
						if(i == listGapi.indexOf("AdditnlAmtCovrd")) {
                        	actual = actual.replaceAll("\n", "").replaceAll(" ", "").toLowerCase();
                        	expec = expec.replaceAll("\n", "").replaceAll(" ", "").toLowerCase();
                        }
						if (i == listGapi.indexOf("SUB_MSG_TYPE")) {
							expec = subMess(functionName1);
						}
					}
					gapiContent(actual, expec, curSts(functionName1) + "_EEOUT_" + listGapi.get(i));
					System.out.println("Act - " + actual + " | Exp" + expec);
				}
			}
		}

		else {
			log(functionName + "Output - EEOUT_GAPI - FAIL");
		}
	}

	@Override
	public String adviceBic(String functionName) {
		try {
			switch (functionName.toLowerCase().replaceAll(" ", "")) {
			case "registerletterofcredit":
				return MT707_DATA_MAP.get("REG_ADV_RECE_CODE");
			case "issueletterofcredit":
				return MT707_DATA_MAP.get("ISS_ADV_RECE_CODE");
			case "issueletterofcreditonestep":
				return MT707_DATA_MAP.get("ISSONE_ADV_RECE_CODE");
			case "issueamendment":
				return MT707_DATA_MAP.get("AMD_ADV_RECE_CODE");
			case "issueamendmentonestep":
				return MT707_DATA_MAP.get("AMDONE_ADV_RECE_CODE");
			case "beneficiaryresponse":
				return MT707_DATA_MAP.get("BENRES_ADV_RECE_CODE");
			case "registerdocumentlC":
				return MT707_DATA_MAP.get("REGDOC_ADV_RECE_CODE");
			case "registediscrepancies":
				return MT707_DATA_MAP.get("REGDIS_ADV_RECE_CODE");
			case "pay/accept":
				return MT707_DATA_MAP.get("PAYACC_ADV_RECE_CODE");
			case "paymentatmaturity":
				return MT707_DATA_MAP.get("PAYMAT_ADV_RECE_CODE");
			}

		} catch (Exception e) {

		}
		return null;

	}

	@Override
	public String releatedRef(String functionName) {
		try {
			switch (functionName.toLowerCase().replaceAll(" ", "")) {
			case "registerletterofcredit":
				return MT707_DATA_MAP.get("REG_ADV_RELD_REF");
			case "issueletterofcredit":
				return MT707_DATA_MAP.get("ISS_ADV_RELD_REF");
			case "issueletterofcreditonestep":
				return MT707_DATA_MAP.get("ISSONE_ADV_RELD_REF");
			case "issueamendment":
				return MT707_DATA_MAP.get("AMD_ADV_RELD_REF");
			case "issueamendmentonestep":
				return MT707_DATA_MAP.get("AMDONE_ADV_RELD_REF");
			case "beneficiaryresponse":
				return MT707_DATA_MAP.get("BENRES_ADV_RELD_REF");
			case "registerdocumentlC":
				return MT707_DATA_MAP.get("REGDOC_ADV_RELD_REF");
			case "registediscrepancies":
				return MT707_DATA_MAP.get("REGDIS_ADV_RELD_REF");
			case "pay/accept":
				return MT707_DATA_MAP.get("PAYACC_ADV_RELD_REF");
			case "paymentatmaturity":
				return MT707_DATA_MAP.get("PAYMAT_ADV_RELD_REF");
			}

		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public String narrtive(String functionName) {
		try {
			switch (functionName.toLowerCase().replaceAll(" ", "")) {
			case "registerletterofcredit":
				return MT707_DATA_MAP.get("REG_ADV_NARR_REF");
			case "issueletterofcredit":
				return MT707_DATA_MAP.get("ISS_ADV_NARR_REF");
			case "issueletterofcreditonestep":
				return MT707_DATA_MAP.get("ISSONE_ADV_NARR_REF");
			case "issueamendment":
				return MT707_DATA_MAP.get("AMD_ADV_NARR_REF");
			case "issueamendmentonestep":
				return MT707_DATA_MAP.get("AMDONE_ADV_NARR_REF");
			case "beneficiaryresponse":
				return MT707_DATA_MAP.get("BENRES_ADV_NARR_REF");
			case "registerdocumentlC":
				return MT707_DATA_MAP.get("REGDOC_ADV_NARR_REF");
			case "registerdiscrepancies":
				return MT707_DATA_MAP.get("REGDIS_ADV_NARR_REF");
			case "pay/accept":
				return MT707_DATA_MAP.get("PAYACC_ADV_NARR_REF");
			case "paymentatmaturity":
				return MT707_DATA_MAP.get("PAYMAT_ADV_NARR_REF");
			}

		} catch (Exception e) {

		}
		return null;
	}

	@Override
	public void swiftValidation(String functionName) throws InterruptedException {
		try {
			WebElement table = locateElement("xpath",
					"/html/body/table/tbody/tr[5]/td/table/tbody/tr[1]/td[2]/div/table[1]/tbody/tr[4]/td/table");
			List<WebElement> column = table.findElements(By.tagName("td"));
			List<String> advice = new ArrayList<String>();
			for (int j = 0; j < column.size(); j++) {
				advice.add(column.get(j).getText());
				System.out.println(column.get(j).getText());
			}
// Outgoing SWIFT Header 1
			int headerIndex = advice.indexOf("Outgoing SWIFT Header 1");
			// TagCheck
			String headerTag = advice.get(headerIndex - 1);
			String string = advice.get(0).toUpperCase();
			System.out.println("name" + string);
			String swiftName = string.substring(0, 6);
			swiftTag(headerTag, "B1:", swiftName + "_" + functionName + "_Outgoing SWIFT Header 1");
// Receiver's BIC Code
			int recBicIndex = advice.indexOf("Receiver's BIC Code");
			// TagCheck
			String recBicTag = advice.get(recBicIndex - 1);
			swiftTag(recBicTag, "B2:", swiftName + "_" + functionName + "_Receiver's BIC Code");
			// ContentCheck
			String recBicCon = advice.get(recBicIndex + 2);
			String bic = adviceBic(functionName);
			System.out.println(functionName + " ad bic" + bic);
			Thread.sleep(300);
			while (bic.length() < 12) {
				Thread.sleep(300);
				bic = bic.concat("X");
			}
			String ExpRecBicCon = "I" + swiftName.substring(3) + bic + "N";
			System.out.println("Act - " + recBicCon + " | Exp - " + ExpRecBicCon);
			swiftContent(ExpRecBicCon, recBicCon, swiftName + "_" + functionName + "_Receiver's BIC Code");

//Transaction Reference Number
			System.out.println("check 1");
			if (advice.contains("Transaction Reference Number")) {
				log(swiftName + "_" + functionName + "Presence of Transaction Reference Number Check: PASS");
				int refNoIndex = advice.indexOf("Transaction Reference Number");
				// TagCheck
				String refNoTag = advice.get(refNoIndex - 1);
				System.out.println();
				swiftTag(refNoTag, "20:", swiftName + "_" + functionName + "_Transaction Reference Number");
				// ContentCheck
				String refNoCont = advice.get(refNoIndex + 2);
				String expectRef = hash_map.get("referenceNo");
				swiftContent(expectRef, refNoCont, swiftName + "_" + functionName + "_Transaction Reference Number");
				System.out.println("Act - " + refNoCont + " | Exp - " + expectRef);
			} else {
				log(swiftName + "_" + functionName + "_" + "Presence of Transaction Reference Number Check: FAIL");
			}

//Related Reference Number
			System.out.println("check 2");
			if (advice.contains("Related Reference Number")) {
				log(swiftName + "_" + functionName + "_" + "Presence of Related Reference Number Check: PASS");
				int reRefIndex = advice.indexOf("Related Reference Number");
				// TagCheck
				String reRefTag = advice.get(reRefIndex - 1);
				swiftTag(reRefTag, "21:", swiftName + "_" + functionName + "_Related Reference Number");
				// ContentCheck
				String reRefCont = advice.get(reRefIndex + 2);
				String expReRef = releatedRef(functionName);
				swiftContent(expReRef, reRefCont, swiftName + "_" + functionName + "_Related Reference Number");
				System.out.println("Act - " + reRefCont + " | Exp - " + expReRef);
			} else {
				log(swiftName + "_" + functionName + "_" + "Presence of Related Reference Number Check: FAIL");
			}

//Narrative
			System.out.println("check 3");
			if (advice.contains("Narrative")) {
				log(swiftName + "_" + functionName + "_" + "Presence of Narrative Check: PASS");
				int narrIndex = advice.indexOf("Narrative");
				// TagCheck
				String narrTag = advice.get(narrIndex - 1);
				swiftTag(narrTag, "79:", swiftName + "_" + functionName + "_Narrative");
				// ContentCheck
				String narrCont = advice.get(narrIndex + 2);
				String expNarrCont = narrtive(functionName).replaceAll("\n", "").toUpperCase();
				swiftContent(expNarrCont, narrCont, swiftName + "_" + functionName + "_Narrative");
				System.out.println("Act - " + narrCont + " | Exp - " + expNarrCont);
			} else {
				log(swiftName + "_" + functionName + "_" + "Presence of Narrative Check: FAIL");

			}
		} catch (Exception e) {

		}
	}

}
