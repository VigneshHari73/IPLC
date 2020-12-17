package com.unique.StepDef;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.aventstack.extentreports.ExtentTest;
import com.unique.Utility.DataLibrary;
import com.unique.base.ProjectBase;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class IplcSteps extends ProjectBase {

	DataLibrary data = new DataLibrary();
	ExtentTest test;

	static HashMap<String, WebElement> hash_map2 = new HashMap<String, WebElement>();

	static HashMap<String, String> Discrepancies_DATA_MAP = new HashMap<String, String>();
	static HashMap<String, String> Settlement_DATA_MAP = new HashMap<String, String>();

	@Given("^Open the EE Application$")
	public void open_the_EE_Application() throws IOException {
		startApp(value("Url"));
		String uCode = data.getCellValue("Scenario 1", 1, 2);
		WebElement eleUcode = locateElement("xpath", "(//table[@cellspacing='0']//input)[2]");
		append(eleUcode, uCode);
		String uiD = data.getCellValue("Scenario 1", 1, 3);
		WebElement eleUid = locateElement("xpath", "(//input[@class='login'])[2]");
		append(eleUid, uiD);
		String pWord = data.getCellValue("Scenario 1", 1, 4);
		WebElement elepWord = locateElement("xpath", "(//input[@type='password'])[1]");
		append(elepWord, pWord);
		WebElement elelogIn = locateElement("xpath", "//input[@value='Login']");
		click(elelogIn);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = new Date();
		String format2 = formatter.format(date1);
		System.out.println(format2);
		ISSSWI_DATA_MAP.put("DtRecvd", format2);
		MT707_DATA_MAP.put("DtRecvd", format2);
		ISSSWI_DATA_MAP.put("MSG_TYPE", "IMLC.Template");
		MT707_DATA_MAP.put("MSG_TYPE", "IMLC.Template");
	}

	@Given("^Click On Import Letter Of Credit$")
	public void click_On_Import_Letter_Of_Credit() throws InterruptedException {
		Thread.sleep(2000);
		switchToFrame("FunctionList");
		WebElement eleIPLCModuleName = locateElement("xpath", "//li[text()='Import Letter of Credit']");
		eleIPLCModuleName.click();
	}

	@Given("^Click on Iplc Issuance$")
	public void click_on_Iplc_Issuance() throws InterruptedException {
		WebElement eleIssuance = locateElement("Name", "IPLC Issuance");
		eleIssuance.click();
	}

	@Given("^Click On Register Letter Of Credit$")
	public void click_On_Register_Letter_Of_Credit() {
		WebElement eleRegisterLC = locateElement("Name", "G49082300272F05030702010");
		eleRegisterLC.click();
	}

	@Given("^Verify the LC Number$")
	public void validate_the_Reference_Numer_length() throws InterruptedException {
		Thread.sleep(3000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		Thread.sleep(2000);
		WebElement eleRef = locateElement("id", "C_MAIN_REF");
		Thread.sleep(3000);
		String typedText = getTypedText(eleRef);
//			System.out.println(typedText);
		Thread.sleep(3000);
		hash_map.put("referenceNo", typedText);
		ISSSWI_DATA_MAP.put("LcNb", typedText);
		MT707_DATA_MAP.put("LcNb", typedText);
		ISSSWI_DATA_MAP.put("BkMainRef", typedText);
		MT707_DATA_MAP.put("BkMainRef", typedText);
//			System.out.println(hash_map.get("referenceNo"));
		if (getTypedText(eleRef).length() <= 12) {
			log("Reference Number Length Check :Pass");
		} else {
			log("Reference Number Length Check :Fail");
			takesnap("Reference Number Length Check");
		}

	}

	@And("^Select the Place of Expiry$")
	public void select_the_Place_of_Expiry() throws IOException, InterruptedException {
		String placeOfEx = data.getCellValue("Scenario 1", 2, 8);
		if (!placeOfEx.isEmpty()) {
			WebElement elePlaceOfExpiry = locateElement("id", "EXPIRY_PLC");
			selectDropDownUsingText(elePlaceOfExpiry, placeOfEx);
			WebElement elePlaceOfExpiryNar = locateElement("id", "EXPIRY_PLC");

			/* elePlaceOfExpiryNar.sendKeys("2222"); */
			String POE_Text = getTypedText(elePlaceOfExpiry);
			ISSSWI_DATA_MAP.put("31D_2", POE_Text.toUpperCase());
			ISSSWI_DATA_MAP.put("PlcOfExp", POE_Text);
			if (POE_Text.equalsIgnoreCase("other")) {
				verifyNarrativeEnable(elePlaceOfExpiryNar, "Other", "Place of Expiry Onchange validation");
			} else {
				verifyNarrativeDisable(elePlaceOfExpiryNar, "Other", "Place of Expiry Onchange validation");
			}
		}
	}

	@And("^Select the Form of LC$")
	public void select_the_Form_of_LC() throws IOException, InterruptedException {
		String FormOfLc = data.getCellValue("Scenario 1", 2, 9);
		if (!FormOfLc.isEmpty()) {
			WebElement eleFormOfLc = locateElement("id", "FORM_OF_LC");
			selectDropDownUsingText(eleFormOfLc, FormOfLc);
			isMandatory(eleFormOfLc, "Form of Lc Mandatory");
			ISSSWI_DATA_MAP.put("40A", getTypedText(eleFormOfLc));
			ISSSWI_DATA_MAP.put("TpOfLc", getTypedText(eleFormOfLc));
		}
	}

	@And("^Select the Applicable Rules$")
	public void select_the_Applicable_Rules() throws IOException {
		String AppRules = data.getCellValue("Scenario 1", 2, 10);
		if (!AppRules.isEmpty()) {
			WebElement eleAppRules = locateElement("id", "APLB_RULE");
			selectDropDownUsingText(eleAppRules, AppRules);
			ISSSWI_DATA_MAP.put("40E", getTypedText(eleAppRules));
			String AR_Text = getTypedText(eleAppRules);
			WebElement eleAR_Nar = locateElement("name", "APLB_RULE_NARR");
			if (AR_Text.equalsIgnoreCase("OTHER")) {
				verifyNarrativeEnable(eleAR_Nar, "OTHER", "Applicable Rules Onchange");
			} else {
				verifyNarrativeDisable(eleAR_Nar, "OTHER", "Applicable Rules Onchange");
			}
		}
	}

	@And("^Enter the Date of Expiry$")
	public void enter_the_Date_of_Expiry() throws IOException, InterruptedException, ParseException {
		try {
			String expiryDate = data.getCellValue("Scenario 1", 2, 11);
			WebElement eleExDate = locateElement("ID", "EXPIRY_DT");
			append(eleExDate, expiryDate);
			String typedText = getTypedText(eleExDate).replace("-", "").substring(2);
			ISSSWI_DATA_MAP.put("31D_1", typedText);
			ISSSWI_DATA_MAP.put("DtOfExp", getTypedText(eleExDate));
			ISSSWI_DATA_MAP.put("DtOfExp", getTypedText(eleExDate));
			ISSSWI_DATA_MAP.put("DueDt", getTypedText(eleExDate));
			MT707_DATA_MAP.put("DueDt", getTypedText(eleExDate));
		} catch (Exception e) {

		}
	}

	@And("^Select the Available By$")
	public void select_the_Available_By() throws IOException, InterruptedException {
		String availableBy = data.getCellValue("Scenario 1", 2, 12);
		WebElement eleavailableBy = locateElement("id", "AVAL_BY");
		selectDropDownUsingText(eleavailableBy, availableBy);
		String value = getTypedText(eleavailableBy);
		hash_map.put("AvailableBy", value);
//			System.out.println(hash_map.get("AvailableBy"));
		WebElement eleTenorDays = locateElement("id", "TENOR_DAYS");
		String tdColour = getBackgroundColor(eleTenorDays);
		WebElement eleTenorType = locateElement("id", "TENOR_TYPE");
		String ttColour = getBackgroundColor(eleTenorType);
		if (value.equalsIgnoreCase("By Payment") || value.equalsIgnoreCase("BY MIXED PYMT")) {
			if (ttColour.contains(Protected) && tdColour.contains(Protected)) {
				log("Available By onchanges When Sight Payment : Pass");
			} else {
				log("Available By onchanges When Sight Payment : Fail");
				takesnap("Available By onchanges When Sight Payment");
			}
		} else {
			if (ttColour.contains(Optional) && tdColour.contains(Optional)) {
				log("Available By onchanges When Term Payment : Pass");
			} else {
				log("Available By onchanges When Term Payment : Fail");
				takesnap("Available By onchanges When Term Payment");
			}
		}
		ISSSWI_DATA_MAP.put("AvalBy", value);
		ISSSWI_DATA_MAP.put("PmtTp", value);
		MT707_DATA_MAP.put("AvalBy", value);
		MT707_DATA_MAP.put("PmtTp", value);
	}

	@And("^Enter the Tenor Days$")
	public void enter_the_Tenor_Days() throws IOException, InterruptedException {
		String tenorDays = data.getCellValue("Scenario 1", 2, 13);
		WebElement eleTenorDays = locateElement("id", "TENOR_DAYS");
		if (!tenorDays.isEmpty()) {

			clearAndType(eleTenorDays, tenorDays);
//				eleTenorDays.click();
//				eleTenorDays.clear();
//				append(eleTenorDays, tenorDays);
		}
	}

	@And("^Select the Tenor Type$")
	public void select_the_Tenor_Type() throws IOException {
		String tenorType = data.getCellValue("Scenario 1", 2, 14);
		WebElement eleTenorType = locateElement("id", "TENOR_TYPE");
		if (!tenorType.isEmpty()) {
			selectDropDownUsingText(eleTenorType, tenorType);
		}
		eleTenorType.sendKeys(Keys.TAB);
	}

	@And("^Select the LC Currency$")
	public void select_the_LC_Currency() throws IOException, InterruptedException {
		String lcCCY = data.getCellValue("Scenario 1", 2, 15).substring(0, 3);
		/* Thread.sleep(3000); */
		WebElement eleLcCcy = locateElement("id", "LC_CCY");
		/* Thread.sleep(3000); */
		selectDropDownUsingText(eleLcCcy, lcCCY);
		ISSSWI_DATA_MAP.put("32B_1", getTypedText(eleLcCcy));
		ISSSWI_DATA_MAP.put("LcCcy", getTypedText(eleLcCcy));
		MT707_DATA_MAP.put("LcCcy", getTypedText(eleLcCcy));

	}

	@And("^Enter the LC Amount$")
	public void enter_the_LC_Amount() throws IOException, InterruptedException {
		String lcAmt = data.getCellValue("Scenario 1", 2, 15).trim().substring(4);

		WebElement eleLcAmt = locateElement("id", "LC_AMT");
		/* Thread.sleep(3000); */

//			clearAndType(eleLcAmt, lcAmt); 
		eleLcAmt.clear();
		eleLcAmt.click();
		append(eleLcAmt, lcAmt);

		/*
		 * if (!getTypedText(eleLcAmt).isEmpty()) { eleLcAmt.sendKeys(Keys.TAB);
		 * verifyfieldLength(eleLcAmt, 18, "LC Amount"); }
		 * 
		 * if (!getTypedText(eleLcAmt).isEmpty()) { if (parseAmount < 0) {
		 * eleLcAmt.sendKeys(Keys.TAB); negativeValue(parseAmount, "LC Amount"); } }
		 */
		ISSSWI_DATA_MAP.put("32B_2", getTypedText(eleLcAmt));
		eleLcAmt.sendKeys(Keys.TAB);
		ISSSWI_DATA_MAP.put("LcAmt", getTypedText(eleLcAmt).replaceAll(",", ""));
		MT707_DATA_MAP.put("LcAmt", getTypedText(eleLcAmt).replaceAll(",", ""));

	}

	@And("^Select the LC Tolerence$")
	public void select_the_LC_Tolerence() throws IOException {
		String tolerence = data.getCellValue("Scenario 1", 2, 19);
		WebElement eletolerence = locateElement("id", "AMT_SPEC");
		if (tolerence == "NOT EXCEEDING") {
			selectDropDownUsingText(eletolerence, tolerence);
		}
		hash_map.put("Tolerence", getTypedText(eletolerence));
		String posColour = locateElement("id", "POS_TOL").getCssValue("background-color");
		String negTolColour = locateElement("id", "NEG_TOL").getCssValue("background-color");
		if (getTypedText(eletolerence).equalsIgnoreCase("NOT EXCEEDING")) {
			if (posColour.contains(Protected) && negTolColour.contains(Protected)) {
				log("Tolerence Onchanges : Pass");
			} else {
				log("Tolerence Onchanges : Fail");
			}
		}
	}

	@And("^Enter the posive Tolerence$")
	public void enter_the_posive_Tolerence() throws IOException {
		String posTolerence = data.getCellValue("Scenario 1", 2, 19);
		WebElement elePosTolerence = locateElement("id", "POS_TOL");
		if (!posTolerence.isEmpty()) {
			elePosTolerence.clear();
			elePosTolerence.click();
			append(elePosTolerence, posTolerence);
			String posTol = getTypedText(elePosTolerence);
			ISSSWI_DATA_MAP.put("39A_1", posTol);
			ISSSWI_DATA_MAP.put("PostveTlrnce", posTol);
			MT707_DATA_MAP.put("PostveTlrnce", posTol);
			elePosTolerence.sendKeys(Keys.TAB);
		} else {
			String posTol = getTypedText(elePosTolerence);
//				System.out.println(posTol);
			ISSSWI_DATA_MAP.put("PostveTlrnce", posTol);
			if (posTol.equalsIgnoreCase("0")) {
				posTol = "00";
			}
			ISSSWI_DATA_MAP.put("39A_1", posTol);

			System.out.println(posTol);
		}
	}

	@And("^Enter the Negative Tolerence$")
	public void enter_the_Negative_Tolerence() throws IOException {
		String negTolerence = data.getCellValue("Scenario 1", 2, 20);
		WebElement eleNegTilerence = locateElement("id", "NEG_TOL");
		if (!negTolerence.isEmpty()) {
			eleNegTilerence.clear();
			eleNegTilerence.click();
			append(eleNegTilerence, negTolerence);
			String negTol = getTypedText(eleNegTilerence);
			ISSSWI_DATA_MAP.put("39A_2", negTol);
			eleNegTilerence.sendKeys(Keys.TAB);
		}

		else {
			String negTol = getTypedText(eleNegTilerence);
//				System.out.println(negTol);
			ISSSWI_DATA_MAP.put("NegtveTlrnce", negTol);
			MT707_DATA_MAP.put("NegtveTlrnce", negTol);
			if (negTol.equalsIgnoreCase("0")) {
				negTol = "00";
			}
			ISSSWI_DATA_MAP.put("39A_2", negTol);
//				System.out.println(negTol);
		}
	}

	@Then("^ChecK LC Balance$")
	public void check_LC_Balance() throws Throwable {
		/* Thread.sleep(3000); */
		WebElement elelcBalance = locateElement("ID", "LC_BAL");
		elelcBalance.click();
		ISSSWI_DATA_MAP.put("LcBala", getTypedText(elelcBalance));
		MT707_DATA_MAP.put("LcBala", getTypedText(elelcBalance));
		String attribute = elelcBalance.getAttribute("original_value");
		String replace = attribute.replace(",", "");
		String trimedLcBalance = replace.replace(".00", " ").trim();
		Double actualBalance = Double.valueOf(trimedLcBalance);

		double actualLcAmt = Double.valueOf(trimedLcBalance);
		String posTolerence = data.getCellValue("Scenario 1", 2, 19);
		String pasTol = posTolerence.replace(".0", "").trim();
		int parse = Integer.valueOf(pasTol);
		if (posTolerence.isEmpty()) {
			parse = 1;
		}
		double expLcBalance = actualLcAmt + ((parse / 100) * actualLcAmt);
		WebElement eletolerence = locateElement("id", "AMT_SPEC");
		String tolerence = getTypedText(eletolerence);
		if (!tolerence.equalsIgnoreCase("NOT EXCEEDING")) {
			if (actualBalance != expLcBalance) {
				log("LC Balance Check: Fail");
				takesnap("Balance Check");
			} else {
				log("LC Balance Check: Pass");
			}

		} else {
			if (actualLcAmt == actualBalance) {
				log("LC Balance Check When Not Exceeding: Pass");
			} else {
				log("LC Balance Check When Not Exceeding: Fail");
				takesnap("Balance Check(NotExceeding)");
			}
		}
	}

	@Then("^Check Currency and Balance$")
	public void check_Currency_and_Balance() throws Throwable {
		WebElement elelcAmt = locateElement("id", "LC_BAL");
		String lcBal = getTypedText(elelcAmt);
		String replace1 = lcBal.replace(",", "");
		String trimedLcBal = replace1.replace(".00", " ").trim();
		double actualLcAmt = Double.valueOf(trimedLcBal);
		WebElement elelocalRate = locateElement("id", "LOCAL_RATE");
		String lRate = elelocalRate.getAttribute("value");
		double localRate = Double.valueOf(lRate);
		double expectedLocalBalance = localRate * actualLcAmt;
		if (actualLcAmt != 0) {
			String posTolerence = data.getCellValue("Scenario 1", 2, 17).trim().substring(4);
			double actalLcBalance = Double.valueOf(posTolerence);
			if (actalLcBalance == expectedLocalBalance) {
				log("Currency and Balance (Local) Check : Pass ");
			} else {
				log("Currency and Balance (Local) Check : Fail ");
				takesnap("Currency and Balance (Local) Check");
			}
		}
	}

	@And("^Enter the Additional Amounts Covered$")
	public void enter_the_Additional_Amounts_Covered() throws Throwable {
		WebElement eleaddCovered = locateElement("id", "ADD_AMT_COVRD");
		String addCovered = data.getCellValue("Scenario 1", 2, 21);
		append(eleaddCovered, addCovered);
		ISSSWI_DATA_MAP.put("39C", getTypedText(eleaddCovered).toUpperCase());
		ISSSWI_DATA_MAP.put("AdditnlAmtCovrd", getTypedText(eleaddCovered));
		MT707_DATA_MAP.put("AdditnlAmtCovrd", getTypedText(eleaddCovered));

	}

	@And("^Select the Send MT(\\d+)$")
	public void select_the_Send_MT(int arg1) throws Throwable {
		WebElement eleSendMT705 = locateElement("id", "SEND_MT705_FLG");
		String send = data.getCellValue("Scenario 1", 2, 22);
		selectDropDownUsingText(eleSendMT705, send);
		/* Thread.sleep(3000); */
		String typeconsant = getTypedText(eleSendMT705);
		WebElement tab = locateElement("id", "H");
		hash_map2.put("tab", tab);
		if (typeconsant.equalsIgnoreCase("Yes")) {
			if (tab.isDisplayed()) {
				log("SendMT705 onchanges : Pass");
			} else {
				log("SendMT705 onchanges : Fail");
				takesnap("SendMT705 onchanges");
			}
			if (typeconsant.equalsIgnoreCase("No")) {
				if (tab.isDisplayed()) {
					log("SendMT705 onchanges [SendMT705 = No] : Fail");
					takesnap("SendMT705 onchanges [SendMT705 = No]");
				} else {
					log("SendMT705 onchanges [SendMT705 = No] : Pass");
				}
			}
		}
	}

	@Given("^click on parties tab$")
	public void click_on_parties_tab() throws Throwable {
		WebElement partiestab = locateElement("id", "C");
		partiestab.click();
	}

	@Given("^Enter App Details$")
	public void enter_App_Details() throws Throwable {
		String appID = data.getCellValue("Scenario 1", 2, 23);
		WebElement eleappID = locateElement("id", "APPL_ID");
		append(eleappID, appID);
		eleappID.sendKeys(Keys.TAB);
		ISSSWI_DATA_MAP.put("CustId", getTypedText(eleappID));
		MT707_DATA_MAP.put("CustId", getTypedText(eleappID));
		ISSSWI_DATA_MAP.put("ApplId", getTypedText(eleappID).toUpperCase());
		MT707_DATA_MAP.put("ApplId", getTypedText(eleappID));
		Thread.sleep(3030);
		WebElement appName = locateElement("name", "APPL_NM");
		ISSSWI_DATA_MAP.put("50_1", getTypedText(appName).toUpperCase());
		ISSSWI_DATA_MAP.put("ApplNm", getTypedText(appName));
		MT707_DATA_MAP.put("ApplNm", getTypedText(appName));

		WebElement add1 = locateElement("name", "APPL_ADD1");
		ISSSWI_DATA_MAP.put("50_2", getTypedText(add1).toUpperCase());
		WebElement add2 = locateElement("name", "APPL_ADD2");
		ISSSWI_DATA_MAP.put("50_3", getTypedText(add2).toUpperCase());
		WebElement add3 = locateElement("name", "APPL_ADD3");
		ISSSWI_DATA_MAP.put("50_4", getTypedText(add3).toUpperCase());

//			System.out.println("name" + ISSSWI_DATA_MAP.get("50_1"));
//			System.out.println("add1" + ISSSWI_DATA_MAP.get("50_2"));
//			System.out.println("add2" + ISSSWI_DATA_MAP.get("50_3"));
//			System.out.println("add3" + ISSSWI_DATA_MAP.get("50_4"));

		Thread.sleep(2000);
		String appAoc = data.getCellValue("Scenario 1", 2, 25);
		WebElement eleappAoc = locateElement("Name", "AC_OFFICER_CODE");
		append(eleappAoc, appAoc);
		/*
		 * if (Value == null || Value.isEmpty()) {
		 * driver.findElementByName("APPL_ID_BTN").click(); acceptAlert();
		 * Thread.sleep(3000); Set<String> windowId = driver.getWindowHandles();
		 * Iterator<String> itererator = windowId.iterator(); String mainWinID =
		 * itererator.next(); String newAdwinID = itererator.next();
		 * driver.switchTo().window(newAdwinID);
		 * driver.findElementByName("SEARCH_VALUE").sendKeys("UNIQUE");
		 * driver.findElementByXPath("/html/body/form[3]/table/tbody/tr/td[8]/a/b").
		 * click(); driver.findElementByXPath("//*[@id=\"0\"]/td[2]/a").click();
		 * driver.switchTo().window(mainWinID); } else { WebElement appID =
		 * locateElement("id", "APPL_ID"); append(appID, Value);
		 * driver.findElementByName("AC_OFFICER_CODE").click(); }
		 */
	}

	@Given("^Enter Bene Details$")
	public void enter_Bene_Details() throws Throwable {
		String beneID = data.getCellValue("Scenario 1", 2, 24);
		WebElement elebeneID = locateElement("id", "BENE_ID");
		append(elebeneID, beneID);
		elebeneID.sendKeys(Keys.TAB);
		Thread.sleep(3030);
		ISSSWI_DATA_MAP.put("BnfcryNm", getTypedText(elebeneID));
		MT707_DATA_MAP.put("BnfcryNm", getTypedText(elebeneID));
		Thread.sleep(3030);
		WebElement beneName = locateElement("name", "BENE_NM");
		String upperCase = getTypedText(beneName).toUpperCase();
		ISSSWI_DATA_MAP.put("59_1", upperCase);
		WebElement add1 = locateElement("name", "BENE_ADD1");
		String upperCase2 = getTypedText(add1).toUpperCase();
		ISSSWI_DATA_MAP.put("59_2", upperCase2);
		ISSSWI_DATA_MAP.put("BnfcryAdr1", getTypedText(add1));
		MT707_DATA_MAP.put("BnfcryAdr1", getTypedText(add1));
		WebElement add2 = locateElement("name", "BENE_ADD2");
		String upperCase3 = getTypedText(add2).toUpperCase();
		ISSSWI_DATA_MAP.put("59_3", upperCase3);
		ISSSWI_DATA_MAP.put("BnfcryAdr2", getTypedText(add2));
		MT707_DATA_MAP.put("BnfcryAdr2", getTypedText(add2));
		WebElement add3 = locateElement("name", "BENE_ADD3");
		String upperCase4 = getTypedText(add3).toUpperCase();
		ISSSWI_DATA_MAP.put("59_4", upperCase4);
		ISSSWI_DATA_MAP.put("BnfcryAdr3", getTypedText(add3));

//			System.out.println("name" + ISSSWI_DATA_MAP.get("59_1"));
//			System.out.println("add1" + ISSSWI_DATA_MAP.get("59_2"));
//			System.out.println("add2" + ISSSWI_DATA_MAP.get("59_3"));
//			System.out.println("add3" + ISSSWI_DATA_MAP.get("59_4"));
	}

	@Given("^Enter For the Account of Details$")
	public void enter_For_the_Account_of_Details() throws Throwable {
		String Sappflag = data.getCellValue("Scenario 1", 2, 26);
		WebElement eleSappflag = locateElement("id", "SAME_AS_APPL_FLG");
		selectDropDownUsingText(eleSappflag, Sappflag);
		;
	}

	@Given("^Enter Advbk Details$")
	public void enter_Advbk_Details() throws Throwable {
		String AdvbkID = data.getCellValue("Scenario 1", 2, 27);
		WebElement eleAdvbkID = locateElement("id", "ADV_BK_ID");
		append(eleAdvbkID, AdvbkID);
		eleAdvbkID.sendKeys(Keys.TAB);
		Thread.sleep(2000);
		String swift = getTypedText(locateElement("name", "ADV_BK_SW_ADD"));
		ISSSWI_DATA_MAP.put("B2", swift);
		ISSSWI_DATA_MAP.put("BnfcryBkSwAdr", swift);
		MT707_DATA_MAP.put("BnfcryBkSwAdr", swift);
		ISSSWI_DATA_MAP.put("BnfcryBkNm", getTypedText(locateElement("name", "ADV_BK_NM")));
		MT707_DATA_MAP.put("BnfcryBkNm", getTypedText(locateElement("name", "ADV_BK_NM")));
		ISSSWI_DATA_MAP.put("BnfcryBkAdr1", getTypedText(locateElement("name", "ADV_BK_ADD1")));
		MT707_DATA_MAP.put("BnfcryBkAdr1", getTypedText(locateElement("name", "ADV_BK_ADD1")));
		ISSSWI_DATA_MAP.put("BnfcryBkAdr2", getTypedText(locateElement("name", "ADV_BK_ADD2")));
		ISSSWI_DATA_MAP.put("BnfcryBkAdr3", getTypedText(locateElement("name", "ADV_BK_ADD3")));
		MT707_DATA_MAP.put("BnfcryBkAdr3", getTypedText(locateElement("name", "ADV_BK_ADD3")));
		ISSSWI_DATA_MAP.put("BnfcryBkId", getTypedText(eleAdvbkID));
		MT707_DATA_MAP.put("BnfcryBkId", getTypedText(eleAdvbkID));

	}

	@Given("^Enter Advthbk Details$")
	public void enter_Advthbk_Details() throws Throwable {
		String AdvthbkID = data.getCellValue("Scenario 1", 2, 28);
		WebElement eleAdvthbkID = locateElement("id", "ADV_THU_BK_ID");
		append(eleAdvthbkID, AdvthbkID);
		eleAdvthbkID.sendKeys(Keys.TAB);
		Thread.sleep(3000);
		String Adswift = getTypedText(locateElement("name", "ADV_THU_BK_SW_ADD"));
		ISSSWI_DATA_MAP.put("57A", Adswift);
		ISSSWI_DATA_MAP.put("49", getTypedText(locateElement("id", "CONF_INSTR")));
		ISSSWI_DATA_MAP.put("ConfInstr", getTypedText(locateElement("id", "CONF_INSTR")));

	}

	@Given("^click on Risk tab$")
	public void click_on_Risk_tab() throws Throwable {
		defaultContent();
		switchToFrame("work");
		/* Thread.sleep(5000); */
		WebElement eleRisk = locateElement("id", ("B"));
		eleRisk.click();
	}

	@Given("^Enter Bank Liability Acccount$")
	public void Enter_Bank_Liability_Account() throws IOException, InterruptedException {
		String bankliaAcc = data.getCellValue("Scenario 1", 2, 29);
		WebElement eleBank = locateElement("id", ("ASSET_ACNO"));
		manualEntry(eleBank, bankliaAcc);
	}

	@Given("^Enter Customer Liability Account$")
	public void enter_Customer_Liability_Account() throws Throwable {
		String custliaAcc = data.getCellValue("Scenario 1", 2, 30);
		WebElement eleCust = locateElement("id", ("LIAB_ACNO"));
		manualEntry(eleCust, custliaAcc);

	}

	@Given("^Click the charges tab$")
	public void click_the_charges_tab() throws Throwable {
		/* Thread.sleep(3000); */
		defaultContent();
		switchToFrame("work");
		WebElement eleCharges = locateElement("id", "F");
		eleCharges.click();

	}

	@Given("^Select the Paid at value$")
	public void select_the_Paid_at_value() throws Throwable {
		String paidAt = data.getCellValue("Scenario 1", 2, 32).toUpperCase();
		WebElement elePaidAt = locateElement("id", "CHG_FLD_ALL_CHARGE_AT");
		selectDropDownUsingText(elePaidAt, paidAt);
		/* Thread.sleep(3000); */
	}

	@Given("^Enter the Acc No$")
	public void enter_the_Acc_No() throws IOException, InterruptedException {
		String AccNo = data.getCellValue("Scenario 1", 2, 33);
		WebElement eleAccno = locateElement("id", "CHG_FLD_LOCAL_CUST_AC_NO");
		manualEntry(eleAccno, AccNo);
		/* Thread.sleep(10000); */
	}

	@Given("^click on Notes tab$")
	public void click_on_Notes_tab() throws Throwable {
		defaultContent();
		switchToFrame("work");
		/* Thread.sleep(5000); */
		WebElement eleNotes = locateElement("id", "D");
		eleNotes.click();

	}

	@Given("^Enter Notes$")
	public void Enter_Notes_() throws Throwable {
		String eleNote = data.getCellValue("Scenario 1", 2, 37);
		WebElement eleNotes = locateElement("id", ("NOTES"));
		append(eleNotes, eleNote);
		String typedText = getTypedText(eleNotes);

		/* Thread.sleep(3000); */
		hash_map.put("Notes", typedText);
	}

	@Given("^click on Diary tab$")
	public void click_on_Diary_tab() throws Throwable {
		defaultContent();
		switchToFrame("work");
		/* Thread.sleep(5000); */
		WebElement eleDiary = locateElement("id", "E");
		eleDiary.click();

	}

	@Given("^Enter Related Reference$")
	public void Enter_Related_Reference() throws Throwable {
		String rReference = hash_map.get("referenceNo");
		WebElement eleReRef = locateElement("name", ("DIARY_RELATED_REF"));
		append(eleReRef, rReference);
		String attribute1 = getTypedText(eleReRef);
		hash_map.put("RegExpectedRef", attribute1);
		/* Thread.sleep(3000); */
		if (getTypedText(eleReRef).length() <= 16) {
			log("Verify Related Reference Length:Pass");
		} else {
			log("Verify Related Reference Length:Fail");
			takesnap("Verify Related Reference Length");
		}
	}

	@Given("^Enter Diary Narrative$")
	public void enter_Diary_Narrative() throws Throwable {
		WebElement eleNarrative = locateElement("name", ("DIARY_NARRATIVE"));
		String narrative = data.getCellValue("Scenario 1", 2, 40);
		datevalidations(eleNarrative, narrative);
		String attribute2 = getTypedText(eleNarrative);
		hash_map.put("RegExpectedNarrative", attribute2);

	}

	@Given("^click on MT(\\d+)S tab$")
	public void click_on_MT_S_tab(int arg1) throws Throwable {
		WebElement mt705Tab = locateElement("id", "H");
		mt705Tab.click();
	}

	@Given("^Enter the Available With Bank details$")
	public void enter_the_Available_With_Bank_details() throws Throwable {

		String availableWithBank = data.getCellValue("Scenario 1", 2, 41);
		if (hash_map2.get("tab").isDisplayed() || !availableWithBank.isEmpty()) {
			WebElement eleAvailableBank = locateElement("id", "AC_WT_BK_ID_MT705");
			getTypedText(eleAvailableBank);
			append(eleAvailableBank, availableWithBank);
			eleAvailableBank.sendKeys(Keys.TAB);
			WebElement swift = locateElement("name", "AC_WT_BK_SW_ADD_MT705");
			ISSSWI_DATA_MAP.put("41A_1", getTypedText(swift));

		}

	}

	@Given("^Enter the Latest Date of Shipmen$")
	public void enter_the_Latest_Date_of_Shipmen() throws Throwable {
		String latestShopmentdate = data.getCellValue("Scenario 1", 2, 45);
		if (hash_map2.get("tab").isDisplayed() || !latestShopmentdate.isEmpty()) {
			WebElement eleshipmentDate = locateElement("id", "X720_LTSTSHIPDT44C_MT705");
			dateCheck(eleshipmentDate, latestShopmentdate, "Latest Shipment Date");
			String typedText = getTypedText(eleshipmentDate).replace("-", "").substring(2);
			ISSSWI_DATA_MAP.put("44C", typedText);
			/* Thread.sleep(3000); */
		}
	}

	@Given("^Enter the Place of Taking in Charge$")
	public void enter_the_Place_of_Taking_in_Charge() throws Throwable {
		String placeOfTakingCharge = data.getCellValue("Scenario 1", 2, 42);
		/* Thread.sleep(3000); */
		if (hash_map2.get("tab").isDisplayed() || !placeOfTakingCharge.isEmpty()) {
			WebElement PlaceOfTakingCharge = locateElement("id", "LOAD_PLACE_MT705");
			lengthValidation(PlaceOfTakingCharge, 65, placeOfTakingCharge, "placeOfTakingCharge");
			String typedText = getTypedText(PlaceOfTakingCharge).toUpperCase();
			ISSSWI_DATA_MAP.put("44A", typedText);
			/* Thread.sleep(3000); */
		}
	}

	@Given("^Enter the Port of Loading$")
	public void enter_the_Port_of_Loading() throws Throwable {
		String portOfLoading = data.getCellValue("Scenario 1", 2, 46);
		if (hash_map2.get("tab").isDisplayed() || !portOfLoading.isEmpty()) {
			WebElement eleportOfLoading = locateElement("id", "LOAD_PORT_MT705");
			lengthValidation(eleportOfLoading, 65, portOfLoading, "portOfLoading");
			String typedText = getTypedText(eleportOfLoading).toUpperCase();
			ISSSWI_DATA_MAP.put("44E", typedText);
			/* Thread.sleep(3000); */
		}
	}

	@Given("^Enter the Port of Discharge$")
	public void enter_the_Port_of_Discharge() throws Throwable {
		String portOfDischarge = data.getCellValue("Scenario 1", 2, 43);
		if (hash_map2.get("tab").isDisplayed() || !portOfDischarge.isEmpty()) {
			WebElement eleportOfDischarge = locateElement("id", "DEST_PORT_MT705");
			lengthValidation(eleportOfDischarge, 65, portOfDischarge, "portOfDischarge");
			String typedText = getTypedText(eleportOfDischarge).toUpperCase();
			ISSSWI_DATA_MAP.put("44F", typedText);
			/* Thread.sleep(3000); */
		}
	}

	@Given("^Enter Place of Final Destination$")
	public void enter_Place_of_Final_Destination() throws Throwable {
		String placeOfFinalDest = data.getCellValue("Scenario 1", 2, 47);
		if (hash_map2.get("tab").isDisplayed() || !placeOfFinalDest.isEmpty()) {
			WebElement eleplaceOfFinalDest = locateElement("id", "DEST_PLACE_MT705");
			lengthValidation(eleplaceOfFinalDest, 65, placeOfFinalDest, "placeOfFinalDest");
			String typedText = getTypedText(eleplaceOfFinalDest).toUpperCase();
			ISSSWI_DATA_MAP.put("44B", typedText);
		}
	}

	@Given("^Enter Description of Goods and/or Services$")
	public void enter_description_of_Goods_and_or_Services() throws Throwable {
		String descriptionOfGoods = data.getCellValue("Scenario 1", 2, 48);
		if (hash_map2.get("tab").isDisplayed() || !descriptionOfGoods.isEmpty()) {
			WebElement eledescriptionOfGoods = locateElement("id", "GOODS_DESC_MT705");
			lengthValidation2(eledescriptionOfGoods, "100*65", descriptionOfGoods, "descriptionOfGoods");
			String typedText = getTypedText(eledescriptionOfGoods).toUpperCase();
			ISSSWI_DATA_MAP.put("45A", typedText);
		}
	}

	@Given("^Enter Narrative$")
	public void enter_Narrative() throws Throwable {
		String narrative = data.getCellValue("Scenario 1", 2, 49);
		if (hash_map2.get("tab").isDisplayed() || !narrative.isEmpty()) {
			WebElement eleNarrative = locateElement("id", "INCOMING_TAG_79_MT705");
			append(eleNarrative, narrative);
			eleNarrative.sendKeys(Keys.TAB);
			Thread.sleep(300);
			// lengthValidation2(eleNarrative, "35*50", narrative, "narrative");
			String typedText = getTypedText(eleNarrative).toUpperCase();
			ISSSWI_DATA_MAP.put("narrative705", typedText.replaceAll("\r\n", " ").replaceAll("\\s", ""));
//			System.out.println(ISSSWI_DATA_MAP.get("narrative705"));
			Thread.sleep(1000);
		}
	}

	@Given("^Enter Sender to Receiver Information$")
	public void enter_Sender_to_Receiver_Information() throws Throwable {
		String senderToReceiver = data.getCellValue("Scenario 1", 2, 44);
		if (hash_map2.get("tab").isDisplayed() || !senderToReceiver.isEmpty()) {
			WebElement elesenderToReceiver = locateElement("id", "BK_TO_BK_INFO_MT705");
			lengthValidation2(elesenderToReceiver, "6*35", senderToReceiver, "senderToReceiver");
			String typedText = getTypedText(elesenderToReceiver).toUpperCase();
			ISSSWI_DATA_MAP.put("72Z", typedText);
			Thread.sleep(1000);
		}
	}

	@Given("^Click Confirm button$")
	public void click_Confirm_button() throws Throwable {
		confirm();
	}

	@Then("^verify voucher is generate$")
	public void verify_voucher_is_generate() throws Throwable {
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		WebElement voucher = locateElement("name", "Voucher");
		String paidAt = data.getCellValue("Scenario 1", 2, 32).toUpperCase();
		if (paidAt.equalsIgnoreCase("TRANSACTION")) {
			if (voucher.isDisplayed()) {
				log("Output : Voucher - Pass");
			} else {
				log("Output : Voucher - Fail");
			}
		}
	}

	@Then("^verify Swift is generate$")
	public void verify_Swift_is_generate() throws Throwable {
		String send = data.getCellValue("Scenario 1", 2, 22);
		if (send.equalsIgnoreCase("Yes")) {
			WebElement swift = locateElement("name", "Swift");
			if (swift.isDisplayed()) {
				log("Output : Swift - Pass");
			} else {
				log("Output : Swift - Fail");
			}
		}

	}

	@Then("^Click On swift button$")
	public void click_on_swift() throws Throwable {

		if (locateElement("name", "Swift").isDisplayed()) {
			click(locateElement("name", "Swift"));
			Thread.sleep(6000);
			switchToWindow(1);
			driver.manage().window().maximize();
			WebElement eleView = locateElement("xpath",
					"//*[@id=\"ext-gen6\"]/form/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/div/table/tbody/tr[4]/td/table/tbody/tr[2]/td[3]/a");
			click(eleView);
		}
	}

	@Then("^Validate the swift_MT(\\d+)$")
	public void validate_the_swift_MT(int arg1) throws Throwable {
		Thread.sleep(6000);
		switchToWindow(2);
		driver.manage().window().maximize();
		Thread.sleep(6000);
		WebElement table = driver.findElement(
				By.xpath("/html/body/table/tbody/tr[5]/td/table/tbody/tr[1]/td[2]/div/table[1]/tbody/tr[4]/td/table"));
		List<WebElement> column = table.findElements(By.tagName("td"));
		List<String> value = new ArrayList<String>();
		for (int j = 0; j < column.size(); j++) {
			value.add(column.get(j).getText());
		}

		// Outgoing SWIFT Header 1
		int headerIntex = value.indexOf("Outgoing SWIFT Header 1");
		// TagCheck
		String headerTag = value.get(headerIntex - 1);
		swiftTag(headerTag, "B1:", "MT705_Outgoing SWIFT Header 1");

		// Receiver's BIC Code
		int recBicIntex = value.indexOf("Receiver's BIC Code");
		// TagCheck
		String recBicTag = value.get(recBicIntex - 1);
		swiftTag(recBicTag, "B2:", "MT705_Receiver's BIC Code");
		// ContentCheck
		String recBicCon = value.get(recBicIntex + 2);
		String swiftBic = ISSSWI_DATA_MAP.get("B2");
//			System.out.println("Before = " + swiftBic);
		Thread.sleep(300);
		while (swiftBic.length() < 12) {
			Thread.sleep(300);
			swiftBic = swiftBic.concat("X");
		}
//			System.out.println("After = " + swiftBic);
		String ExpRecBicCon = "I705" + swiftBic + "N2";
//			System.out.println("Act - " + recBicCon + " | Exp" + ExpRecBicCon);
		swiftContent(ExpRecBicCon, recBicCon, "MT705_Receiver's BIC Code");

		// Form of Documentary Credit
		if (!ISSSWI_DATA_MAP.get("40A").isEmpty()) {
			if (value.contains("Form of Documentary Credit")) {
				log(" Form of Documentary Credit check - Pass");
				int formOfLcIntex = value.indexOf("Form of Documentary Credit");
				// TagCheck
				String formOfLCTag = value.get(formOfLcIntex - 1);
				swiftTag(formOfLCTag, "40A:", "MT705_Form of Documentary Credit");
				// ContentCheck
				String formOfLcCon = value.get(formOfLcIntex + 2);
//			System.out.println("Act - " + formOfLcCon + " | Exp" + ISSSWI_DATA_MAP.get("40A"));
				swiftContent(ISSSWI_DATA_MAP.get("40A"), formOfLcCon, "MT705_Form of Documentary Credit");
			} else {
				log("MT705_Presence of Form of Documentary Credit check - Fail");
			}
		}
		// Documentary Credit Number
		if (!hash_map.get("referenceNo").isEmpty()) {
			if (value.contains("Documentary Credit Number")) {
				log("MT705_Presence of Documentary Credit Number check - Pass");
				int lcNumberIntex = value.indexOf("Documentary Credit Number");
				// TagCheck
				String lcNumberTag = value.get(lcNumberIntex - 1);
				swiftTag(lcNumberTag, "20:", "MT705_Documentary Credit Number");
				// ContentCheck
				String lcNumberCon = value.get(lcNumberIntex + 2);
				swiftContent(hash_map.get("referenceNo"), lcNumberCon, "MT705_Documentary Credit Number");
			} else {
				log("MT705_Presence of Documentary Credit Number check - Fail");
			}
		}
		// Date and Place of Expiry
		if (!ISSSWI_DATA_MAP.get("31D_1").isEmpty() && !ISSSWI_DATA_MAP.get("31D_2").isEmpty()) {
			if (value.contains("Date and Place of Expiry")) {
				log("MT705_Presence of Date and Place of Expiry check - Pass");
				int datePlaceExIntex = value.indexOf("Date and Place of Expiry");
				// TagCheck
				String datePlaceExTag = value.get(datePlaceExIntex - 1);
				swiftTag(datePlaceExTag, "31D:", "MT705_Date and Place of Expiry");
				// ContentCheck
				String datePlaceExCon = value.get(datePlaceExIntex + 2);
				String expDatePlaceExCon = ISSSWI_DATA_MAP.get("31D_1") + ISSSWI_DATA_MAP.get("31D_2");
//			System.out.println("Act - " + datePlaceExCon + " | Exp" + expDatePlaceExCon);
				swiftContent(expDatePlaceExCon, datePlaceExCon, "MT705_Date and Place of Expiry");
			} else {
				log("MT705_Presence of Date and Place of Expiry check - Fail");
			}
		}
//	 	Applicant
		if (value.contains("Applicant")) {
			log("MT705_Presence of Applicant check - Pass");
			int applicantInt = value.indexOf("Applicant");
			// TagCheck
			String applicantTag = value.get(applicantInt - 1);
			swiftTag(applicantTag, "50:", "MT705_Applicant");
			// ContentCheck
			String applicantCon = value.get(applicantInt + 2).replaceAll("\n", "").replaceAll(" ", "");
			String expapplicantCon = ISSSWI_DATA_MAP.get("50_1") + "\n" + ISSSWI_DATA_MAP.get("50_2") + "\n"
					+ ISSSWI_DATA_MAP.get("50_3") + "\n" + ISSSWI_DATA_MAP.get("50_4");
			Thread.sleep(300);

			String expapplicantCon2 = expapplicantCon.replaceAll("\n", "").replaceAll(" ", "");
//			System.out.println("Act - " + applicantCon + " | Exp" + expapplicantCon);
			swiftContent(expapplicantCon2, applicantCon, "MT705_Applicant");
		} else {
			log("MT705_Presence of Applicant check - Fail");
		}

		// Beneficiary
		if (value.contains("Beneficiary")) {
			log("MT705_Presence of Beneficiary check - Pass");
			int beneficiaryInt = value.indexOf("Beneficiary");
			// TagCheck
			String beneTag = value.get(beneficiaryInt - 1);
			swiftTag(beneTag, "59:", "MT705_Beneficiary");
			// ContentCheck
			String beneCont = value.get(beneficiaryInt + 2).replaceAll("\n", "").replaceAll(" ", "");
			;
			String expbeneCont = ISSSWI_DATA_MAP.get("59_1") + "\n" + ISSSWI_DATA_MAP.get("59_2") + "\n"
					+ ISSSWI_DATA_MAP.get("59_3") + "\n" + ISSSWI_DATA_MAP.get("59_4");
			String expbeneCont2 = expbeneCont.replaceAll("\n", "").replaceAll(" ", "");
//			System.out.println("Act - " + beneCont + " | Exp" + expbeneCont);
			swiftContent(expbeneCont2, beneCont, "MT705_Beneficiary");
		} else {
			log("MT705_Presence of Beneficiary check - Fail");
		}

		// Currency Code, Amount
		if (value.contains("Currency Code, Amount")) {
			log("MT705_Presence of Currency Code, Amount check - Pass");
			int ccyCodeInt = value.indexOf("Currency Code, Amount");
			// TagCheck
			String ccyCodeTag = value.get(ccyCodeInt - 1);
			swiftTag(ccyCodeTag, "32B:", "MT705_Currency Code, Amount");
			// ContentCheck
			String ccyCodeCont = value.get(ccyCodeInt + 2);
			String expCcyCodeCont = ISSSWI_DATA_MAP.get("32B_1") + ISSSWI_DATA_MAP.get("32B_2");
//			System.out.println("Act - " + ccyCodeCont + " | Exp" + expCcyCodeCont.concat(",00"));
			swiftContent(expCcyCodeCont.concat(",00"), ccyCodeCont, "MT705_Currency Code, Amount");
		} else {
			log("MT705_Presence of Currency Code, Amount check - Fail");
		}

		// Percentage Credit Amount Tolerance

		if (!hash_map.get("Tolerence").isEmpty()) {
			if (value.contains("Percentage Credit Amount Tolerance")) {
				log("MT705_Presence of Percentage Credit Amount Tolerance check - Pass");

				int perCreAmountInt = value.indexOf("Percentage Credit Amount Tolerance");
				// TagCheck
				String percentTag = value.get(perCreAmountInt - 1);
				swiftTag(percentTag, "39A:", "MT705_Percentage Credit Amount Tolerance");
				// ContentCheck
				String percentTagCon = value.get(perCreAmountInt + 2);
				String exppercentTagCon = ISSSWI_DATA_MAP.get("39A_1") + "/" + ISSSWI_DATA_MAP.get("39A_2");
//			System.out.println("Act - " + percentTagCon + " | Exp" + exppercentTagCon);
				swiftContent(exppercentTagCon, percentTagCon, "MT705_Percentage Credit Amount Tolerance");
			} else {
				log("MT705_Presence of Percentage Credit Amount Tolerance check - Fail");
			}
		}
		// Additional Amounts Covered
		if (!ISSSWI_DATA_MAP.get("39C").isEmpty()) {
			if (value.contains("Additional Amounts Covered")) {
				log("MT705_Presence of Additional Amounts Covered check - Pass");
				int addAmtCovInt = value.indexOf("Additional Amounts Covered");
				// TagCheck
				String addAmtTag = value.get(addAmtCovInt - 1);
				swiftTag(addAmtTag, "39C:", "MT705_Percentage Credit Amount Tolerance");
				// ContentCheck
				String addAmtCov = value.get(addAmtCovInt + 2).replaceAll("\n", "").replaceAll(" ", "");
				String expAddAmtCov = ISSSWI_DATA_MAP.get("39C").replaceAll("\n", "").replaceAll(" ", "");
//			System.out.println("Act - " + addAmtCov + " | Exp - " + expAddAmtCov);
				swiftContent(expAddAmtCov, addAmtCov, "MT705_Additional Amounts Covered");
			} else {
				log("MT705_Presence of Additional Amounts Covered check - Fail");
			}
		}

		// Available With...By...
		if (value.contains("Available With...By...")) {
			log("MT705_Presence of Available With...By... check - Pass");
			int availableByInt = value.indexOf("Available With...By...");
			// TagCheck
			String availableTag = value.get(availableByInt - 1);
			swiftTag(availableTag, "41A:", "MT705_Available With...By...");
			// ContentCheck
			String avaiCon = value.get(availableByInt + 2);
			String expAvail = ISSSWI_DATA_MAP.get("41A_1") + "\n" + hash_map.get("AvailableBy");
//			System.out.println(expAvail);
			swiftContent(expAvail, avaiCon, "MT705_Available With...By...");
		} else {
			log("MT705_Presence of Available With...By... check - Fail");
		}

		// Loading on Board/Dispatch/Taking
		if (!ISSSWI_DATA_MAP.get("44A").isEmpty()) {
			if (value.contains("Loading on Board/Dispatch/Taking")) {
				log("MT705_Presence of Loading on Board/Dispatch/Taking check - Pass");
				int loadingInt = value.indexOf("Loading on Board/Dispatch/Taking");
				// TagCheck
				String loadingTag = value.get(loadingInt - 1);
				swiftTag(loadingTag, "44A:", "MT705_Loading on Board/Dispatch/Taking");
				// ContentCheck
				String loadinngCon = value.get(loadingInt + 2);
				String expLoadingCon = ISSSWI_DATA_MAP.get("44A");
				swiftContent(expLoadingCon, loadinngCon, "MT705_Loading on Board/Dispatch/Taking");
			} else {
				log("MT705_Presence of Loading on Board/Dispatch/Taking check - Fail");
			}
		}
		// Port of Loading/Airport of Departure
		if (!ISSSWI_DATA_MAP.get("44E").isEmpty()) {
			if (value.contains("Port of Loading/Airport of Departure")) {
				log("MT705_Presence of Port of Loading/Airport of Departure check - Pass");
				int loadingPortInt = value.indexOf("Port of Loading/Airport of Departure");
				// TagCheck
				String loadingPortTag = value.get(loadingPortInt - 1);
				swiftTag(loadingPortTag, "44E:", "MT705_Port of Loading/Airport of Departure");
				// ContentCheck
				String loadingPortCont = value.get(loadingPortInt + 2);
				String exploadingPortCont = ISSSWI_DATA_MAP.get("44E");
				swiftContent(exploadingPortCont, loadingPortCont, "MT705_Port of Loading/Airport of Departure");
			} else {
				log("MT705_Presence of Port of Loading/Airport of Departure check - Fail");
			}
		}
		// Port of Discharge/Airport of Destination
		if (!ISSSWI_DATA_MAP.get("44F").isEmpty()) {
			if (value.contains("Port of Discharge/Airport of Destination")) {
				log("MT705_Presence of Port of Discharge/Airport of Destination check - Pass");
				int disPortInt = value.indexOf("Port of Discharge/Airport of Destination");
				// TagCheck
				String disPortTag = value.get(disPortInt - 1);
				swiftTag(disPortTag, "44F:", "MT705_Port of Loading/Airport of Departure");
				// ContentCheck
				String disPortCont = value.get(disPortInt + 2);
				String expDisPortCont = ISSSWI_DATA_MAP.get("44F");
				swiftContent(expDisPortCont, disPortCont, "MT705_Port of Loading/Airport of Departure");
			} else {
				log("MT705_Presence of Port of Discharge/Airport of Destination check - Fail");
			}
		}
		// For Transportation to...
		if (!ISSSWI_DATA_MAP.get("44B").isEmpty()) {
			if (value.contains("For Transportation to...")) {
				log("MT705_Presence of For Transportation to... check - Pass");
				int forTrnsTo = value.indexOf("For Transportation to...");
				// TagCheck
				String forTrnsToTag = value.get(forTrnsTo - 1);
				swiftTag(forTrnsToTag, "44B:", "MT705_For Transportation to...");
				// ContentCheck
				String forTrnsToCont = value.get(forTrnsTo + 2);

				String ExpforTrnsToCont = ISSSWI_DATA_MAP.get("44B");
//			System.out.println("Act - " + forTrnsToCont + " | Exp - " + ExpforTrnsToCont);
				swiftContent(ExpforTrnsToCont, forTrnsToCont, "MT705_For Transportation to...");
			} else {
				log("MT705_Presence of For Transportation to... check - Fail");
			}
		}
		// Latest Date of Shipment
		if (!ISSSWI_DATA_MAP.get("44C").isEmpty()) {
			if (value.contains("Latest Date of Shipment")) {
				log("MT705_Presence of Latest Date of Shipment check - Pass");
				int latShipDate = value.indexOf("Latest Date of Shipment");
				// TagCheck
				String latShipDateTag = value.get(latShipDate - 1);
				swiftTag(latShipDateTag, "44C:", "MT705_Latest Date of Shipment");
				// ContentCheck
				String latShipDateCont = value.get(latShipDate + 2);
				String ExplatShipDateCont = ISSSWI_DATA_MAP.get("44C");
//			System.out.println("Act - " + latShipDateCont + " | Exp - " + ExplatShipDateCont);
				swiftContent(ExplatShipDateCont, latShipDateCont, "MT705_Latest Date of Shipment");
			} else {
				log("MT705_Presence of Latest Date of Shipment check - Fail");
			}
		}
		// Description of Goods and/or Services
		if (!ISSSWI_DATA_MAP.get("45A").isEmpty()) {
			if (value.contains("Description of Goods and/or Services")) {
				log("MT705_Presence of Description of Goods and/or Services check - Pass");
				int desOfGoodsSer = value.indexOf("Description of Goods and/or Services");
				// TagCheck
				String desOfGoodsSerSerTag = value.get(desOfGoodsSer - 1);
				swiftTag(desOfGoodsSerSerTag, "45A:", "MT705_Description of Goods and/or Services");
				// ContentCheck
				String desOfGoodsSerCont = value.get(desOfGoodsSer + 2).replaceAll("\n", "").replaceAll(" ", "");
				String ExpdesOfGoodsSerCont = ISSSWI_DATA_MAP.get("45A").replaceAll("\n", "").replaceAll(" ", "");
				swiftContent(ExpdesOfGoodsSerCont, desOfGoodsSerCont, "MT705_Description of Goods and/or Services");
			} else {
				log("MT705_Presence of Description of Goods and/or Services check - Fail");
			}
		}
		// Second Advising Bank
		if (!ISSSWI_DATA_MAP.get("57A").isEmpty()) {
			if (value.contains("Second Advising Bank")) {
				log("MT705_Presence of Second Advising Bank check - Pass");
				int secAdvBank = value.indexOf("Second Advising Bank");
				// TagCheck
				String secAdvBankTag = value.get(secAdvBank - 1);
				swiftTag(secAdvBankTag, "57A:", "MT705_Second Advising Bank");
				// ContentCheck
				String secAdvBankCont = value.get(secAdvBank + 2).replaceAll("\n", "").replaceAll(" ", "");
				Thread.sleep(300);
				String ExpsecAdvBankCont = ISSSWI_DATA_MAP.get("57A").replaceAll("\n", "").replaceAll(" ", "");
//			System.out.println("Act - " + secAdvBankCont + " | Exp - " + ExpsecAdvBankCont);
				swiftContent(ExpsecAdvBankCont, secAdvBankCont, "MT705_Second Advising Bank");
			} else {
				log("MT705_Presence of Second Advising Bank check - Fail");
			}
		}
		// Narrative
		if (!data.getCellValue("Scenario 1", 2, 49).isEmpty()) {
			if (value.contains("Narrative")) {
				log("MT705_Presence of Narrative check - Pass");
				int narrativeIntex = value.indexOf("Narrative");
				// TagCheck
				String narrativeTag = value.get(narrativeIntex - 1);
				swiftTag(narrativeTag, "79Z:", "MT705_Narrative");
				// ContentCheck
//				String narrativeContent = value.get(narrativeIntex + 2).replaceAll(" ", "").replaceAll("\n", " ");
//				String expNarrativeContent = ISSSWI_DATA_MAP.get("narrative705").toUpperCase();
				// System.out.println("Act - " + narrativeContent + " | Exp - " +
				// expNarrativeContent);
//				swiftContent(expNarrativeContent, narrativeContent, "MT705_Narrative");
			} else {
				log("MT705_Presence of Narrative check - Fail");
			}
		}
		// Sender to Receiver Information
		if (!data.getCellValue("Scenario 1", 2, 44).isEmpty()) {
			if (value.contains("Sender to Receiver Information")) {
				log("MT705_Presence of Sender to Receiver Information check - Pass");
				int senderToRecInt = value.indexOf("Sender to Receiver Information");
				// TagCheck
				String bkToBkTag = value.get(senderToRecInt - 1);
				swiftTag(bkToBkTag, "72Z:", "MT705_Sender to Receiver Information");
				// ContentCheck
//			String bkToBkContent = value.get(senderToRecInt + 2).replaceAll("\n", " ");
//			Thread.sleep(300);
//			String expBkToBkContent = data.getCellValue("Scenario 1", 2, 44).toUpperCase();
//			System.out.println("Act - " + bkToBkContent + " | Exp - " + expBkToBkContent);
//			swiftContent(expBkToBkContent, bkToBkContent, "MT705_Sender to Receiver Information");
			} else {
				log("MT705_Presence of Sender to Receiver Information check - Fail");
			}
		}
	}

	@And("^Click cancel button$")
	public void click_cancel_button() throws Throwable {
		switchToWindow("China SystemsEximbills Enterprise");
		Thread.sleep(1000);
		cancel();
	}

	@And("^Release the transaction$")
	public void release_the_transaction() throws Throwable {
		String refNo = hash_map.get("referenceNo");
		release(refNo);
		cancel();
	}

	@Given("^click on Issue Letter Of Credit$")
	public void click_on_Issue_Letter_Of_Credit() throws Throwable {
		driver.switchTo().defaultContent();
		driver.switchTo().frame("FunctionList");
		WebElement eleIssueLC = locateElement("Name", "G49082300272F05030702015");
		eleIssueLC.click();
		/* Thread.sleep(3000); */
		String refNum = hash_map.get("referenceNo");
		/* Thread.sleep(3000); */
//		System.out.println("IP003232BEDM");
		catalog(refNum);
	}

	@Given("^Select the Revolving LC$")
	public void select_the_Revolving_LC() throws Throwable {
		Thread.sleep(2000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		Thread.sleep(2000);
		String revlcflag = data.getCellValue("Scenario 1", 3, 72);
		WebElement eleRevlcflag = locateElement("id", "REV_LC");
		Thread.sleep(2000);
		selectDropDownUsingText(eleRevlcflag, revlcflag);
		ISSSWI_DATA_MAP.put("31C", getTypedText(locateElement("name", "ISSUE_DT")).replace("-", "").substring(2));
		String typedText = getTypedText(locateElement("name", "ISSUE_DT"));
		ISSSWI_DATA_MAP.put("DtOfIsse", typedText);

	}

	@Given("^Select the Cumulative\\?$")
	public void select_the_Cumulative() throws Throwable {
		String revlcflag = data.getCellValue("Scenario 1", 3, 72);
		if (revlcflag.equalsIgnoreCase("Yes")) {
			String cumflag = data.getCellValue("Scenario 1", 3, 73);
			WebElement eleCumflag = locateElement("id", "CUMULATIVE");
			selectDropDownUsingText(eleCumflag, cumflag);
		}
	}

	@Given("^Select the Auto Renewal\\?$")
	public void select_the_Auto_Renewal() throws Throwable {
		String revlcflag = data.getCellValue("Scenario 1", 3, 72);
		if (revlcflag.equalsIgnoreCase("Yes")) {
			String arflag = data.getCellValue("Scenario 1", 3, 74);
			WebElement eleArflag = locateElement("id", "EVERGREEN");
			selectDropDownUsingText(eleArflag, arflag);
		}
	}

	@Given("^Select the Revolve Periods$")
	public void select_the_Revolve_Periods() throws Throwable {
		String revlcflag = data.getCellValue("Scenario 1", 3, 72);
		if (revlcflag.equalsIgnoreCase("Yes")) {
			String rpflag = data.getCellValue("Scenario 1", 3, 75);
			WebElement eleRpflag = locateElement("id", "REV_EVENT");
			selectDropDownUsingText(eleRpflag, rpflag);
		}
	}

	@Given("^Enter No\\. of Revolve Periods$")
	public void enter_No_of_Revolve_Periods() throws Throwable {
		String revlcflag = data.getCellValue("Scenario 1", 3, 72);
		if (revlcflag.equalsIgnoreCase("Yes")) {
			String rp = data.getCellValue("Scenario 1", 3, 76);
			WebElement eleRp = locateElement("id", "NO_PRD");
			append(eleRp, rp);
		}
	}

	@Given("^Enter Next Revolve Date$")
	public void enter_next_Revolve_Date() throws Throwable {
		String revlcflag = data.getCellValue("Scenario 1", 3, 72);
		if (revlcflag.equalsIgnoreCase("Yes")) {
			String nrd = data.getCellValue("Scenario 1", 3, 77);
			WebElement eleNrd = locateElement("id", "NXT_REV_DT");
			append(eleNrd, nrd);
		}
	}

	@Given("^click on partiesII tab$")
	public void click_on_partiesII_tab() throws Throwable {
		WebElement parties2tab = locateElement("id", "C");
		parties2tab.click();
	}

	@Given("^Select Reimbursement\\. Authority Required\\?$")
	public void select_Reimbursement_Authority_Required() throws Throwable {
		String reimflag = data.getCellValue("Scenario 1", 3, 83);
		WebElement eleReimflag = locateElement("id", "REIM_BK_AUTH_REQ");
		Thread.sleep(2000);
		selectDropDownUsingText(eleReimflag, reimflag);
	}

	@Given("^Enter Reimbursing Bank Details$")
	public void enter_Reimbursing_Bank_Details() throws Throwable {
		String reimflag = data.getCellValue("Scenario 1", 3, 83);
		if (reimflag.equalsIgnoreCase("Yes")) {
			String reimID = data.getCellValue("Scenario 1", 3, 84);
			WebElement eleReimID = locateElement("id", "REIM_BK_ID");
			append(eleReimID, reimID);
			ISSSWI_DATA_MAP.put("53A", getTypedText(eleReimID));
			eleReimID.sendKeys(Keys.TAB);
			Thread.sleep(1000);
			ISSSWI_DATA_MAP.put("B2_REIM", getTypedText(locateElement("name", "REIM_BK_SW_ADD")));
		}
	}

	@Given("^Select Reimbursement Charge$")
	public void select_Reimbursement_Charge() throws Throwable {
		String reimflag = data.getCellValue("Scenario 1", 3, 83);
		if (reimflag.equalsIgnoreCase("Yes")) {
			String reimCh = data.getCellValue("Scenario 1", 3, 85);
			WebElement eleReimCh = locateElement("id", "REIM_BK_CHRG_DESC");
			selectDropDownUsingText(eleReimCh, reimCh);
			ISSSWI_DATA_MAP.put("71A", getTypedText(eleReimCh));
		}
	}

	@Given("^Select Applicable Rules$")
	public void select_Applicable_Rules() throws Throwable {
		String reimflag = data.getCellValue("Scenario 1", 3, 83);
		if (reimflag.equalsIgnoreCase("Yes")) {
			String reimAppr = data.getCellValue("Scenario 1", 3, 86);
			WebElement eleReimAppr = locateElement("id", "APLB_RULE_40F");
			selectDropDownUsingText(eleReimAppr, reimAppr);
			ISSSWI_DATA_MAP.put("40F", getTypedText(eleReimAppr));
		}
	}

	@Given("^Enter Reimbursing Bank's Reference$")
	public void enter_Reimbursing_Bank_s_Reference() throws Throwable {
		String refNo = hash_map.get("referenceNo");
		WebElement eleReimRef = locateElement("id", "REIM_BK_REF");
		append(eleReimRef, refNo);

	}

	@Given("^Enter Charges$")
	public void enter_Charges() throws Throwable {
		String chr = data.getCellValue("Scenario 1", 3, 88);
		WebElement eleChr = locateElement("id", "REIM_BK_OTH_CHG_NARR");
		append(eleChr, chr);
		ISSSWI_DATA_MAP.put("71D_Reim", getTypedText(eleChr));
	}

	@Given("^Enter Reim Sender to Receiver Information$")
	public void enter_Reim_Sender_to_Receiver_Information() throws Throwable {
		String stri = data.getCellValue("Scenario 1", 3, 89);
		WebElement elestri = locateElement("id", "BK_TO_BK_INFO");
		append(elestri, stri);
		ISSSWI_DATA_MAP.put("72Z_Reim", getTypedText(elestri));
	}

	@Given("^Enter Reim Narrative$")
	public void enter_Reim_Narrative() throws Throwable {
		String reimNarr = data.getCellValue("Scenario 1", 3, 90);
		WebElement elereimNarr = locateElement("id", "REIM_NARR_TAG_77");
		append(elereimNarr, reimNarr);
	}

	@Given("^Applicant Bank$")
	public void applicant_Bank() throws Throwable {
		String appBk = data.getCellValue("Scenario 1", 3, 91);
		WebElement eleappBk = locateElement("id", "APPL_BK_ID");
		append(eleappBk, appBk);
		eleappBk.sendKeys(Keys.TAB);
		Thread.sleep(3000);
		ISSSWI_DATA_MAP.put("ForAcctOfId", getTypedText(eleappBk));
		ISSSWI_DATA_MAP.put("APPL_BK_ID", getTypedText(eleappBk));
		ISSSWI_DATA_MAP.put("ForAcctOfNm", getTypedText(locateElement("name", "APPL_BK_NM")));

	}

	@Given("^Enter Requested Confirmation Party$")
	public void enter_Requested_Confirmation_Party() throws Throwable {
		String rcpID = data.getCellValue("Scenario 1", 3, 92);
		WebElement elercpID = locateElement("id", "CONF_BK_ID");
		append(elercpID, rcpID);
		ISSSWI_DATA_MAP.put("58A", getTypedText(elercpID));
		elercpID.sendKeys(Keys.TAB);
	}

	@Given("^Click on Tenor tab$")
	public void click_on_Tenor_tab() throws Throwable {
		/*
		 * Thread.sleep(3000); driver.switchTo().parentFrame();
		 * driver.switchTo().frame("work");
		 */
		/* Thread.sleep(2000); */
		WebElement eleTenor = locateElement("id", "D");
		click(eleTenor);
	}

	@Then("^Verify values are displayed$")
	public void verify_values_are_displayed() throws Throwable {
		String expAvailableBy = data.getCellValue("Scenario 1", 3, 93).toUpperCase();
		String exptenorDays = data.getCellValue("Scenario 1", 3, 94);
		String exptenorType = data.getCellValue("Scenario 1", 3, 95);
		String availableBy = getTypedText(locateElement("id", "AVAL_BY"));
		String tenorType = getTypedText(locateElement("id", "TENOR_TYPE"));
		ISSSWI_DATA_MAP.put("TenorTp", tenorType);
		String tenorDays = getTypedText(locateElement("id", "TENOR_DAYS"));
		ISSSWI_DATA_MAP.put("TenorDays", tenorDays);
		String draftAt = getTypedText(locateElement("id", "DRAFTS_AT"));
		hash_map.put("Draft At", draftAt);
		ISSSWI_DATA_MAP.put("DraftAt", draftAt);
		if (expAvailableBy.equalsIgnoreCase(availableBy) && exptenorDays.equalsIgnoreCase(tenorDays)
				&& exptenorType.equalsIgnoreCase(tenorType)) {
			log("Tenor information retrival : Pass ");
		} else {
			log("Tenor information retrival : Fail ");
			takesnap("Tenor information retrival");
		}

		String concat = tenorDays + " " + tenorType;
		if (concat.trim().toLowerCase().equalsIgnoreCase(draftAt.trim().toLowerCase())) {
			log("Draft At field value check: Pass");
		} else {
			log("Draft At field value check: Fail");
			takesnap("Draft At field value check");
		}

	}

	@Then("^Verify values are available by onchanges$")
	public void Verify_values_are_available_by_onchanges() throws Throwable {
		String draftAt = getTypedText(locateElement("id", "DRAFTS_AT"));
		String ttColour = getBackgroundColor(locateElement("id", "TENOR_TYPE"));
		String tdColour = getBackgroundColor(locateElement("id", "TENOR_DAYS"));
		String defColour = getBackgroundColor(locateElement("id", "DEF_PMT_DET"));
		ISSSWI_DATA_MAP.put("DeferdPmtTerms", getTypedText(locateElement("id", "DEF_PMT_DET")));

		String mixColour = getBackgroundColor(locateElement("id", "MIX_PMT_DETL"));
		String draftColour = getBackgroundColor(locateElement("id", "DRAFTS_AT"));
		switch (draftAt.toUpperCase()) {
		case "BY ACCEPTANCE":
			if (ttColour.contains(Mandatory) && tdColour.contains(Optional) && defColour.contains(Protected)
					&& mixColour.contains(Protected) && draftColour.contains(Mandatory)) {
				log("Available by onchanges When By Acceptance : Pass");
			} else {
				log("Available by onchanges When By Acceptance : Fail");
				takesnap("Available by onchanges When By Acceptance");
			}
			break;
		}
	}

	@Then("^Enter Drawee ID$")
	public void enter_Drawee_ID() throws Throwable {
		WebElement eleDrawee = locateElement("id", "DRWE_ID");
		String darweeId = data.getCellValue("Scenario 1", 3, 98);
		append(eleDrawee, darweeId);
		eleDrawee.sendKeys(Keys.TAB);
		Thread.sleep(1000);
		ISSSWI_DATA_MAP.put("42A", getTypedText(locateElement("name", "DRWE_SW_ADD")));

	}

	@Then("^Available With Bank$")
	public void available_With_Bank() throws Throwable {
		String availableWith = data.getCellValue("Scenario 1", 3, 97);
		WebElement eleAvailableWith = locateElement("id", "AVAL_WT_BK_OPT");
		selectDropDownUsingText(eleAvailableWith, availableWith);
	}

	@Then("^verify Available Bank details are displayed$")
	public void verify_Available_Bank_details_are_displayed() throws Throwable {
		String advisngBank = data.getCellValue("Scenario 1", 2, 27);
		WebElement eleAvailableWith = locateElement("id", "AVAL_WT_BK_OPT");
		WebElement eleAvailableBank = locateElement("id", "AVAL_WT_BK_ID");
		String availableBankId = getTypedText(eleAvailableBank);
		switch (getTypedText(eleAvailableWith).toLowerCase()) {
		case "advising bank":
			if (advisngBank.equalsIgnoreCase(availableBankId)) {
				log("Available With Bank Onchanges When Available With Bank = Advise Bank : Pass");
			} else {
				log("Available With Bank Onchanges When Available With Bank = Advise Bank : Fail");
				takesnap("Available With Bank Onchanges When Available With Bank = Advise Bank ");
			}
			break;
		}

	}

	@Given("^click on Goods tab$")
	public void click_on_Goods_tab() throws Throwable {
		WebElement goodstab = locateElement("id", "E");
		goodstab.click();
	}

	@Given("^Select Partial Shipment$")
	public void select_Partial_Shipment() throws Throwable {
		String psflag = data.getCellValue("Scenario 1", 3, 99);
		if (psflag.equalsIgnoreCase("NOT ALLOWED") || psflag.equalsIgnoreCase("ALLOWED")) {
			WebElement elePsflag = locateElement("id", "PARTIAL_SHIP");
			/* Thread.sleep(2000); */
			selectDropDownUsingText(elePsflag, psflag);
		} else {
			if (psflag.equalsIgnoreCase("CONDITIONAL")) {
				WebElement elePsflag = locateElement("id", "PARTIAL_SHIP");
//					Thread.sleep(2000);
				selectDropDownUsingText(elePsflag, psflag);
				String psnarrative = data.getCellValue("Scenario 1", 4, 99);
				WebElement elePsnarrative = locateElement("id", "PARTIAL_SHIP_NARR");
				append(elePsnarrative, psnarrative);
			}
		}
		WebElement elePsflag = locateElement("id", "PARTIAL_SHIP");
		ISSSWI_DATA_MAP.put("43P", getTypedText(elePsflag));
		ISSSWI_DATA_MAP.put("PrtlShipmnt", getTypedText(elePsflag));
	}

	@Given("^Select Transhipment$")
	public void select_Transhipment() throws Throwable {
		String trflag = data.getCellValue("Scenario 1", 3, 100);
		if (trflag.equalsIgnoreCase("NOT ALLOWED") || trflag.equalsIgnoreCase("ALLOWED")) {
			WebElement eleTrflag = locateElement("id", "TNSHIP");
			/* Thread.sleep(2000) ; */
			selectDropDownUsingText(eleTrflag, trflag);
		} else {
			if (trflag.equalsIgnoreCase("CONDITIONAL")) {
				WebElement eleTrflag = locateElement("id", "TNSHIP");
				/* Thread.sleep(2000); */
				selectDropDownUsingText(eleTrflag, trflag);
				String trnarrative = data.getCellValue("Scenario 1", 4, 100);
				WebElement eleTrnarrative = locateElement("id", "TNSHIP_NARR");
				append(eleTrnarrative, trnarrative);
			}
		}
		WebElement eleTrflag = locateElement("id", "TNSHIP");
		ISSSWI_DATA_MAP.put("43T", getTypedText(eleTrflag));
		ISSSWI_DATA_MAP.put("Transhipment", getTypedText(eleTrflag));

	}

	@Given("^Enter Iss Place of Taking in Charge$")
	public void enter_Iss_Place_of_Taking_in_Charge() throws Throwable {
		String plTakchar = data.getCellValue("Scenario 1", 3, 101);
		WebElement elePlTakchar = locateElement("id", "LOAD_PLACE");
		append(elePlTakchar, plTakchar);
		ISSSWI_DATA_MAP.put("ShipFr", getTypedText(elePlTakchar));
	}

	@Given("^Enter Iss Place of Final Destination$")
	public void enter_Iss_Place_of_Final_Destination() throws Throwable {
		String plFindest = data.getCellValue("Scenario 1", 3, 102);
		WebElement elePlFindest = locateElement("id", "DEST_PLACE");
		append(elePlFindest, plFindest);
		ISSSWI_DATA_MAP.put("ShipTo", getTypedText(elePlFindest));
	}

	@Given("^Enter Iss Port of Loading$")
	public void enter_Iss_Port_of_Loading() throws Throwable {
		String ptload = data.getCellValue("Scenario 1", 3, 103);
		WebElement elePtload = locateElement("id", "LOAD_PORT");
		append(elePtload, ptload);
		elePtload.sendKeys(Keys.TAB);
		ISSSWI_DATA_MAP.put("PortOfLoadng", getTypedText(elePtload));
	}

	@Given("^Enter Iss Port of Discharge$")
	public void enter_Iss_Port_of_Discharge() throws Throwable {
		String ptdis = data.getCellValue("Scenario 1", 3, 104);
		WebElement elePtdis = locateElement("id", "DEST_PORT");
		append(elePtdis, ptdis);
		elePtdis.sendKeys(Keys.TAB);
		ISSSWI_DATA_MAP.put("PortOfDschrge", getTypedText(elePtdis));
	}

	@Given("^Enter Iss Latest Shipment Date$")
	public void enter_Iss_Latest_Shipment_Date() throws Throwable {
		String lsdt = data.getCellValue("Scenario 1", 3, 106);
		WebElement eleLsdt = locateElement("id", "LTST_SHIP_DT");
		append(eleLsdt, lsdt);
		ISSSWI_DATA_MAP.put("44C_1", getTypedText(eleLsdt).replace("-", "").substring(2));
		ISSSWI_DATA_MAP.put("LatstShipmntDt", getTypedText(eleLsdt));
	}

	@Given("^Enter Iss Shipment Period$")
	public void enter_Iss_Shipment_Period() throws Throwable {

	}

	@Given("^Enter Iss Commodity Code$")
	public void enter_Iss_Commodity_Code() throws Throwable {
		try {
			Thread.sleep(3000);
			String comcode = data.getCellValue("Scenario 1", 3, 107);
			WebElement eleComcode = locateElement("id", "CMDTY_CD");
			append(eleComcode, comcode);
		} catch (Exception e) {

		}

	}

	@Given("^Enter Iss Description of Goods and/or Services$")
	public void enter_Iss_Description_of_Goods_and_or_Services() throws Throwable {
		String issDes = data.getCellValue("Scenario 1", 3, 108);
		WebElement eleDesGoods = locateElement("name", "GOODS_DESC");
		append(eleDesGoods, issDes);
		ISSSWI_DATA_MAP.put("45A_1", getTypedText(eleDesGoods));
		ISSSWI_DATA_MAP.put("DescOfGoods", getTypedText(eleDesGoods));
	}

	@Given("^Click Documents Tab$")
	public void click_Documents_Tab() throws Throwable {
		/* Thread.sleep(3000); */
		defaultContent();
		switchToFrame("work");
		WebElement eleDocs = locateElement("id", "F");
		eleDocs.click();
	}

	@Given("^Enter values$")
	public void enter_values() throws Throwable {
		String Incoterms = data.getCellValue("Scenario 1", 3, 109);
		WebElement eleIncoterms = locateElement("xpath", "//*[@id=\"INCOTERMS\"]");
		selectDropDownUsingText(eleIncoterms, Incoterms);
		eleIncoterms.sendKeys(Keys.TAB);
		ISSSWI_DATA_MAP.put("Incotrms", getTypedText(eleIncoterms));
		ISSSWI_DATA_MAP.put("IncotrmsInstr", getTypedText(locateElement("name", "INCOTERM_INST")));
		WebElement locateElement = locateElement("name", "INCOTERM_INST");
		ISSSWI_DATA_MAP.put("45A_1_2", getTypedText(locateElement));
		ISSSWI_DATA_MAP.put("46A", getTypedText(locateElement("id", "DOC_REQ")));

	}

	@Given("^Click Instructions tab$")
	public void click_Instructions_tab() throws Throwable {
		/* Thread.sleep(3000); */
		defaultContent();
		switchToFrame("work");
		WebElement eleInst = locateElement("id", "G");
		eleInst.click();
	}

	@Given("^Enter all the values$")
	public void enter_all_the_values() throws Throwable {

		String SEND_TO_RCV_INFO = data.getCellValue("Scenario 1", 3, 114);
		WebElement eleSEND_TO_RCV_INFO = locateElement("id", "SEND_TO_RCV_INFO");
		lengthValidation2(eleSEND_TO_RCV_INFO, "6*35", SEND_TO_RCV_INFO, "sendertoReceiverInfo");
		ISSSWI_DATA_MAP.put("72Z_1", getTypedText(eleSEND_TO_RCV_INFO));

		String CHARGES = data.getCellValue("Scenario 1", 3, 115);
		WebElement eleCHARGES = locateElement("id", "CHARGES");
		lengthValidation2(eleCHARGES, "6*35", CHARGES, "chargesInfo");
		ISSSWI_DATA_MAP.put("71D", getTypedText(eleCHARGES));

		String INSTR_TO_PAY_BK = data.getCellValue("Scenario 1", 3, 116);
		WebElement eleINSTR_TO_PAY_BK = locateElement("id", "INSTR_TO_PAY_BK");
		lengthValidation2(eleINSTR_TO_PAY_BK, "12*65", INSTR_TO_PAY_BK, "InstrtoPayBank");
		ISSSWI_DATA_MAP.put("78", getTypedText(eleINSTR_TO_PAY_BK));

		String PAY_COND_TO_BENE = data.getCellValue("Scenario 1", 3, 117);
		WebElement elePAY_COND_TO_BENE = locateElement("id", "PAY_COND_TO_BENE");
		lengthValidation2(elePAY_COND_TO_BENE, "100*65", PAY_COND_TO_BENE, "PayCondtoBene");
		ISSSWI_DATA_MAP.put("49G", getTypedText(elePAY_COND_TO_BENE));

		String ADDIT_CONDITION = data.getCellValue("Scenario 1", 3, 118);
		WebElement eleADDIT_CONDITION = locateElement("id", "ADDIT_CONDITION");
		lengthValidation2(eleADDIT_CONDITION, "100*65", ADDIT_CONDITION, "AdditionalCondition");
		ISSSWI_DATA_MAP.put("47A", getTypedText(eleADDIT_CONDITION));
		ISSSWI_DATA_MAP.put("AdditnlCondtin",
				getTypedText(eleADDIT_CONDITION).replaceAll("\n", "").replaceAll(" ", ""));

		String PAY_COND_TO_REV_BK = data.getCellValue("Scenario 1", 3, 119);
		WebElement elePAY_COND_TO_REV_BK = locateElement("id", "PAY_COND_TO_REV_BK");
		lengthValidation2(elePAY_COND_TO_REV_BK, "100*65", PAY_COND_TO_REV_BK, "PayCondtoRecvBank");
		ISSSWI_DATA_MAP.put("49H", getTypedText(elePAY_COND_TO_REV_BK));
		ISSSWI_DATA_MAP.put("48", getTypedText(locateElement("id", "PRES_PRD_TXT")));

	}

	@Given("^Click Iss charges tab$")
	public void click_Iss_charges_tab() throws Throwable {
		/* Thread.sleep(3000); */
		defaultContent();
		switchToFrame("work");
		WebElement eleIssCharges = locateElement("id", "H");
		eleIssCharges.click();

	}

	@Given("^Select Iss Paid at value$")
	public void select_Iss_Paid_at_value() throws Throwable {
		String IsspaidAt = data.getCellValue("Scenario 1", 3, 121).toUpperCase();
		WebElement eleIssPaidAt = locateElement("id", "CHG_FLD_ALL_CHARGE_AT");
		append(eleIssPaidAt, IsspaidAt);
		/* Thread.sleep(3000); */
	}

	@Given("^Enter Iss Acc No$")
	public void enter_Iss_Acc_No() throws Throwable {
		String IssAccNo = data.getCellValue("Scenario 1", 3, 122);
		WebElement eleIssAccno = locateElement("id", "CHG_FLD_LOCAL_CUST_AC_NO");
		manualEntry(eleIssAccno, IssAccNo);
	}

	@Given("^Click on Advice tab$")
	public void click_on_Advice_tab() throws Throwable {
		WebElement eleAdvice = locateElement("id", "J");
		click(eleAdvice);
	}

	@Given("^click add button and verify onchanges$")
	public void click_add_button_and_verify_onchanges() throws Throwable {
		WebElement eleAdd = locateElement("id", "ext-gen91");
		click(eleAdd);
	}

	@Given("^select type of message$")
	public void select_type_of_message() throws Throwable {
		String TOM = data.getCellValue("Scenario 1", 3, 128).replaceAll(" ", "");
		if (!TOM.isEmpty()) {
			switchToFrame("frame.AdivceForBankCust");
			WebElement eleTOM = locateElement("id", "MESG_TYPE_BANK");
			selectDropDownUsingText(eleTOM, TOM);
			String typeOfMessage = getTypedText(eleTOM);
			Thread.sleep(1000);
			String swiftTagColour = getBackgroundColor(locateElement("name", "SEND_TO_BK_SW_ADD"));
			String rReferenceColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_REF"));
			String languageColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_LANG"));
			String mailColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_POST_ADD"));
			String faxColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_FAX"));
			String emailColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_EMAIL"));
			String sNarrativeColour = getBackgroundColor(locateElement("name", "BANK_NARR_TAG_79"));
			String mNarrativeColour = getBackgroundColor(locateElement("name", "BANK_NARR_MAIL"));
			String nameColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_NM"));

			if (typeOfMessage.equalsIgnoreCase("MT199") || typeOfMessage.equalsIgnoreCase("MT299")
					|| typeOfMessage.equalsIgnoreCase("MT799") || typeOfMessage.equalsIgnoreCase("MT999")) {
				if (swiftTagColour.contains(Mandatory) && rReferenceColour.contains(Mandatory)
						&& languageColour.contains(Mandatory) && sNarrativeColour.contains(Mandatory)
						&& mailColour.contains(Optional) && faxColour.contains(Optional)
						&& emailColour.contains(Optional) && mNarrativeColour.contains(Protected)) {
					log("Type Of Message Onchange(MT n99) : Pass");
				} else {
					log("Type Of Message Onchange(MT n99): Fail");
					takesnap("Type Of Message Onchange(MT n99)");
				}
			}
			if (typeOfMessage.equalsIgnoreCase("Mail")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Mandatory)
						&& faxColour.contains(Optional) && emailColour.contains(Optional)
						&& mNarrativeColour.contains(Mandatory)) {
					log("Type Of Message Onchange(Mail) : Pass");
				} else {
					log("Type Of Message Onchange(Mail) : Fail");
					takesnap("Type Of Message Onchange(Mail)");
				}
			}

			if (typeOfMessage.equalsIgnoreCase("Email")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Optional)
						&& faxColour.contains(Optional) && emailColour.contains(Mandatory)
						&& mNarrativeColour.contains(Mandatory)) {
					log("Type Of Message Onchange(email) : Pass");
				} else {
					log("Type Of Message Onchange(email) : Fail");
					takesnap("Type Of Message Onchange(email)");
				}
			}

			if (typeOfMessage.equalsIgnoreCase("Fax")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Optional)
						&& faxColour.contains(Mandatory) && emailColour.contains(Optional)
						&& mNarrativeColour.contains(Mandatory)) {
					log("Type Of Message Onchange(Fax) : Pass");
				} else {
					log("Type Of Message Onchange(Fax) : Fail");
					takesnap("Type Of Message Onchange(Fax)");
				}
			}
		}
	}

	@Given("^Enter Bank ID$")
	public void enter_Bank_ID() throws Throwable {
		String bId = data.getCellValue("Scenario 1", 3, 129);
		if (!bId.isEmpty()) {
			WebElement eleBankId = locateElement("name", "SEND_TO_BANK_ID");
			append(eleBankId, bId);
			eleBankId.sendKeys(Keys.TAB);
			Thread.sleep(10000);
			MT707_DATA_MAP.put("ISS_ADV_RECE_CODE", getTypedText(locateElement("name", "SEND_TO_BK_SW_ADD")));
			MT707_DATA_MAP.put("ISS_ADV_RELD_REF", getTypedText(locateElement("name", "SEND_TO_BANK_REF")));
		}
	}

	@Given("^select Mail Method$")
	public void select_Mail_Method() throws Throwable {
		String mMethod = data.getCellValue("Scenario 1", 3, 133);
		if (!mMethod.isEmpty()) {
			WebElement eleMailMethod = locateElement("Name", "MAIL_MTHD_BANK");
			selectDropDownUsingText(eleMailMethod, mMethod);
		}
	}

	@Given("^Enter Narrative \\(MT n(\\d+) Tag (\\d+)Z\\)$")
	public void enter_Narrative_MT_n_Tag_Z(int arg1, int arg2) throws Throwable {
		String narrative = data.getCellValue("Scenario 1", 3, 134);
		if (!narrative.isEmpty()) {
			WebElement eleNarrative = locateElement("name", "BANK_NARR_TAG_79");
			lengthValidation2(eleNarrative, "35*50", narrative, "Narrative(MTn99Tag79Z) Length Check");
			eleNarrative.sendKeys(Keys.TAB);
			MT707_DATA_MAP.put("ISS_ADV_NARR_REF", getTypedText(eleNarrative));
		}
	}

	@Given("^Enter Narrative \\(Mail\\)$")
	public void enter_Narrative_Mail() throws Throwable {
		String mailNarrative = data.getCellValue("Scenario 1", 3, 135);
		if (!mailNarrative.isEmpty()) {
			WebElement eleNarrative = locateElement("name", "BANK_NARR_MAIL");
			append(eleNarrative, mailNarrative);
		}
	}

	@Given("^Click on customer subtab$")
	public void click_on_customer_subtab() throws Throwable {
		WebElement eleCustomer = locateElement("ID", "B");
		click(eleCustomer);
	}

	@Given("^select type of message and verify onchanges$")
	public void select_type_of_message_and_verify_onchanges() throws Throwable {
		String custTom = data.getCellValue("Scenario 1", 3, 136);
		if (!custTom.isEmpty()) {
			WebElement eleTom = locateElement("id", "MESG_TYPE_CUST");
			selectDropDownUsingText(eleTom, custTom);
			String custTomText = getTypedText(eleTom);
			String nameColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_NM"));
			String languageColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_LANG"));
			String emailColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_EMAIL"));
			String faxColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_FAX"));
			String narrColour = getBackgroundColor(locateElement("id", "CUST_NARR_TAG_79"));
			String mailColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_POST_ADD"));
			if (custTomText.equalsIgnoreCase("Fax")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Optional) && faxColour.contains(Mandatory)
						&& narrColour.contains(Mandatory) && mailColour.contains(Optional)) {
					log("Customer - Type Of Message Onchange(Fax) : Pass");
				} else {
					log("Customer - Type Of Message Onchange(Fax) : Fail");
					takesnap("Customer - Type Of Message Onchange(Fax)");
				}
			}

			if (custTomText.equalsIgnoreCase("Mail")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Optional) && faxColour.contains(Optional)
						&& narrColour.contains(Mandatory) && mailColour.contains(Mandatory)) {
					log("Customer - Type Of Message Onchange(Mail) : Pass");
				} else {
					log("Customer - Type Of Message Onchange(Mail) : Fail");
					takesnap("Customer - Type Of Message Onchange(Mail)");
				}
			}

			if (custTomText.equalsIgnoreCase("Email")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Mandatory) && faxColour.contains(Optional)
						&& narrColour.contains(Mandatory) && mailColour.contains(Optional)) {
					log("Customer - Type Of Message Onchange(Email) : Pass");
				} else {
					log("Customer - Type Of Message Onchange(Email) : Fail");
					takesnap("Customer - Type Of Message Onchange(Email)");
				}
			}
		}
	}

	@Given("^Enter Customer ID$")
	public void enter_Customer_ID() throws Throwable {
		String custId = data.getCellValue("Scenario 1", 3, 137);
		if (!custId.isEmpty()) {
			WebElement eleCustId = locateElement("name", "SEND_TO_CUST_ID");
			append(eleCustId, custId);
			Thread.sleep(1000);
			eleCustId.sendKeys(Keys.TAB);

		}
	}

	@Given("^select Customer Mail Method$")
	public void select_Customer_Mail_Method() throws Throwable {
		String mMethod = data.getCellValue("Scenario 1", 3, 138);
		if (!mMethod.isEmpty()) {
			WebElement eleMailMethod = locateElement("Name", "MAIL_MTHD_CUST");
			selectDropDownUsingText(eleMailMethod, mMethod);

		}
	}

	@Given("^Enter narrative$")
	public void enter_narrative() throws Throwable {
		String custNarrative = data.getCellValue("Scenario 1", 3, 139);
		if (!custNarrative.isEmpty()) {
			WebElement eleCustId = locateElement("name", "CUST_NARR_TAG_79");
			append(eleCustId, custNarrative);
			Thread.sleep(1000);
		}
	}

	@Given("^Click on save$")
	public void click_on_save() throws Throwable {
		driver.switchTo().defaultContent();
		driver.switchTo().frame("work");
		WebElement elesave = locateElement("xpath", "//button[text()='Save and Next']");
		click(elesave);
		Thread.sleep(10000);
		WebElement eleClose = locateElement("id", "ext-gen294");
		click(eleClose);
	}

	@Given("^Click on Notes tab$")
	public void Click_on_Notes_tab() throws Throwable {
		defaultContent();
		switchToFrame("work");
		/* Thread.sleep(5000); */
		WebElement eleNote = locateElement("id", "L");
		eleNote.click();
	}

	@And("^Verify Notes details$")
	public void Verify_Notes_details() throws Throwable {
		WebElement elenotes = locateElement("id", ("NOTES"));
		String note = getTypedText(elenotes);
//		String nNotes = hash_map.get("Notes");
		if (note.equalsIgnoreCase(hash_map.get("Notes"))) {

			log("Verify Notes details:Pass");
		} else {
			log("Verify Notes details:Fail");
			takesnap("Verify Notes details");
		}
	}

	@Given("^Click Diary tab$")
	public void Click_Diary_tab() throws Throwable {
		defaultContent();
		switchToFrame("work");
		/* Thread.sleep(2000); */
		WebElement eleDiary = locateElement("id", "M");
		eleDiary.click();

	}

	@Given("^Enter related reference$")
	public void Enter_related_reference() throws Throwable {
//		String rReference = hash_map.get("referenceNo");
		WebElement eleReRef = locateElement("name", ("DIARY_RELATED_REF"));
		append(eleReRef, "IP003044BEDM");
		/* Thread.sleep(3000); */
		if (getTypedText(eleReRef).length() <= 16) {
			log("Verify Related Reference Length:Pass");
		} else {
			log("Verify Related Reference Length:Fail");
			takesnap("Verify Related Reference Length");
		}
	}

	@Given("^Enter diary narrative$")
	public void Enter_diary_narrative() throws Throwable {
		WebElement eleNarrative = locateElement("name", ("DIARY_NARRATIVE"));
		String narrative = data.getCellValue("Scenario 1", 3, 144);
		append(eleNarrative, narrative);
		Actions actions = new Actions(driver);
		/* Thread.sleep(3000); */
		actions.moveToElement(eleNarrative)
				.moveToElement(
						driver.findElementByXPath("//*[@id=\"M_div\"]/table/tbody/tr[2]/td/table/tbody/tr[2]/td[3]"))
				.click().build().perform();
	}

	@Given("^click viewhistory button and verify the diary details$")
	public void click_viewhistory_button_and_verify_the_diary_details() throws Throwable {
		WebElement eleviewhistory = locateElement("id", ("view_1"));
		eleviewhistory.click();
		Set<String> windowId = driver.getWindowHandles();
		Iterator<String> itererator = windowId.iterator();
		String mainWinID = itererator.next();
		String newAdwinID = itererator.next();
		driver.switchTo().window(newAdwinID);
		/* Thread.sleep(3000); */
		WebElement eleReReference = locateElement("id", ("_id_0RELATED_REFERENCE"));
		String RegActualref = getTypedText(eleReReference);
//		String nRef = hash_map.get("RegExpectedRef");
		WebElement elenNarrative = locateElement("id", ("DIARY_NARRATIVE"));
		String RegActualNarrative = getTypedText(elenNarrative);
//		String Rnarrative = hash_map.get("RegExpectedNarrative");
		if (RegActualref.equalsIgnoreCase(hash_map.get("RegExpectedRef"))
				|| RegActualNarrative.equalsIgnoreCase(hash_map.get("RegExpectedNarrative"))) {

			log("Verify Diary details:Pass");
		} else {
			log("Verify Diary details:Fail");
			takesnap("Verify Diary details");
		}
		/* Thread.sleep(3000); */
		driver.switchTo().window(mainWinID);
		switchToFrame("work");

	}

	@Then("^validate the GAPI$")
	public void validate_the_gapi() throws Throwable {
		driver.switchTo().parentFrame();
		switchToFrame("eeToolbar");
		WebElement gapi = locateElement("id", "_pregapi");
		click(gapi);
		gapiValidation("Issue Letter Of Credit");
		Thread.sleep(3000);
		driver.close();
		switchToWindow(1);
		driver.close();
		switchToWindow(0);
		driver.manage().window().maximize();

	}

	@Then("^Verify GAPI is generated$")
	public void verify_gapi_is_generated() throws Throwable {
		Thread.sleep(2000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		Thread.sleep(2000);
		if (locateElement("name", "Gapi").isDisplayed()) {
			log("Output : GAPI - Pass");
		} else {
			log("Output : GAPI - Fail");
		}

	}

//	@When("^validate the Issue gapi$")
//	public void validate_the_iss_gapi() throws Throwable {
//		WebElement gapi = locateElement("name", "Gapi");
//		if (gapi.isDisplayed()) {
//			click(gapi);
//			gapiValidation("Issue Letter Of Credit");
//			switchToWindow(1);
//		}
//	}

	@Then("^Verify Voucher is generated$")
	public void verify_voucher_is_generated() throws Throwable {
		try {
			String paidAt = data.getCellValue("Scenario 1", 3, 121).toUpperCase();
			if (paidAt.equalsIgnoreCase("TRANSACTION")) {
				if (locateElement("name", "Voucher").isDisplayed()) {
					log("Output : Voucher - Pass");
				} else {
					log("Output : Voucher - Fail");
				}
			}
		} catch (Exception e) {
		}

	}

	@Then("^Verify Swift is generated$")
	public void Verify_Swift_is_generated() throws Throwable {
		try {
			String send = data.getCellValue("Scenario 1", 3, 64);
			if (send.equalsIgnoreCase("Yes")) {
				if (locateElement("name", "Swift").isDisplayed()) {
					log("Output : Swift - Pass");
				} else {
					log("Output : Swift - Fail");

				}
			}
		} catch (Exception e) {

		}

	}

	@When("^Click On issswift button$")
	public void click_On_issswift_button() throws Throwable {
		if (locateElement("name", "Swift").isDisplayed()) {
			click(locateElement("name", "Swift"));
			Thread.sleep(6000);
			switchToWindow(1);
			driver.manage().window().maximize();
			WebElement eleView = locateElement("xpath",
					"//*[@id=\"ext-gen6\"]/form/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/div/table/tbody/tr[4]/td/table/tbody/tr[3]/td[3]/a");
			click(eleView);
		}
	}

	@Then("^Validate the issswift MT(\\d+)$")
	public void validate_the_issswift_MT(int arg1) throws Throwable {
		Thread.sleep(6000);
		switchToWindow(2);
		driver.manage().window().maximize();
		Thread.sleep(6000);
		WebElement table = driver.findElement(
				By.xpath("/html/body/table/tbody/tr[5]/td/table/tbody/tr[1]/td[2]/div/table[1]/tbody/tr[4]/td/table"));
		List<WebElement> column = table.findElements(By.tagName("td"));
		List<String> MT700 = new ArrayList<String>();
		for (int j = 0; j < column.size(); j++) {
			MT700.add(column.get(j).getText());
		}
		// Outgoing SWIFT Header 1
		int headerIntex = MT700.indexOf("Outgoing SWIFT Header 1");
		// TagCheck
		String headerTag = MT700.get(headerIntex - 1);
		swiftTag(headerTag, "B1:", "MT700_Outgoing SWIFT Header 1");

		// Receiver's BIC Code
		int recBicIntex = MT700.indexOf("Receiver's BIC Code");
		// TagCheck
		String recBicTag = MT700.get(recBicIntex - 1);
		swiftTag(recBicTag, "B2:", "MT700_Receiver's BIC Code");
		// ContentCheck
		String recBicCon = MT700.get(recBicIntex + 2);
		String swiftBic = ISSSWI_DATA_MAP.get("B2");
//					System.out.println("Before = " + swiftBic);
		Thread.sleep(300);
		while (swiftBic.length() < 12) {
			Thread.sleep(300);
			swiftBic = swiftBic.concat("X");
		}
//					System.out.println("After = " + swiftBic);
		String ExpRecBicCon = "I700" + swiftBic + "N2";
//					System.out.println("Act - " + recBicCon + " | Exp" + ExpRecBicCon);
		swiftContent(ExpRecBicCon, recBicCon, "MT700_Receiver's BIC Code");

		// Sequence of Total
		if (MT700.contains("Sequence of Total")) {
			log("MT700_Presence of Sequence of Total check - Pass");
			int seqTotalIntex = MT700.indexOf("Sequence of Total");
			// Tag Check
			String seqTag = MT700.get(seqTotalIntex - 1);
			swiftTag(seqTag, "27:", "MT700_Sequence of Total");
			// Content Check
			String seqContent = MT700.get(seqTotalIntex + 2);
			String expSeqTol = "1/1";
			swiftContent(expSeqTol, seqContent, "MT700_Sequence of Total");
		}

		// Form of Documentary Credit
		if (!ISSSWI_DATA_MAP.get("40A").isEmpty()) {
			if (MT700.contains("Form of Documentary Credit")) {
				log("MT700_Presence of Form of Documentary Credit check - Pass");
				int formOfLcIntex = MT700.indexOf("Form of Documentary Credit");
				// TagCheck
				String formOfLCTag = MT700.get(formOfLcIntex - 1);
				swiftTag(formOfLCTag, "40A:", "MT700_Form of Documentary Credit");
				// ContentCheck
				String formOfLcCon = MT700.get(formOfLcIntex + 2);
//					System.out.println("Act - " + formOfLcCon + " | Exp" + ISSSWI_DATA_MAP.get("40A"));
				swiftContent(ISSSWI_DATA_MAP.get("40A"), formOfLcCon, "MT700_Form of Documentary Credit");
			} else {
				log("MT700_Presence of Form of Documentary Credit check - Fail");
			}
		}

		// Documentary Credit Number
		if (!hash_map.get("referenceNo").isEmpty()) {
			if (MT700.contains("Documentary Credit Number")) {
				log("MT700_Presence of Documentary Credit Number check - Pass");
				int lcNumberIntex = MT700.indexOf("Documentary Credit Number");
				// TagCheck
				String lcNumberTag = MT700.get(lcNumberIntex - 1);
				swiftTag(lcNumberTag, "20:", "MT700_Documentary Credit Number");
				// ContentCheck
				String lcNumberCon = MT700.get(lcNumberIntex + 2);
				swiftContent(hash_map.get("referenceNo"), lcNumberCon, "MT700_Documentary Credit Number");
			} else {
				log("MT700_Presence of Documentary Credit Number check - Fail");
			}
		}

		// Reference to Pre-Advice
		if (MT700.contains("Reference to Pre-Advice")) {
			log("MT700_Presence of Reference to Pre-Advice check - Pass");
			int reftopreadvIntex = MT700.indexOf("Reference to Pre-Advice");
			// Tag Check
			String reftopreadvTag = MT700.get(reftopreadvIntex - 1);
			swiftTag(reftopreadvTag, "23:", "MT700_Reference to Pre-Advice");
			// Content Check
			String reftopreadvContent = MT700.get(reftopreadvIntex + 2);
			String expreftopreadv = "PREADV";
			swiftContent(expreftopreadv, reftopreadvContent, "MT700_Reference to Pre-Advice");
		}

		// Date of Issue
		if (!ISSSWI_DATA_MAP.get("31C").isEmpty()) {
			if (MT700.contains("Date of Issue")) {
				log("MT700_Presence of Date of Issue check - Pass");
				int dtofISSIntex = MT700.indexOf("Date of Issue");
				// TagCheck
				String dtofISSTag = MT700.get(dtofISSIntex - 1);
				swiftTag(dtofISSTag, "31C:", "MT700_Date of Issue");
				// ContentCheck
				String dtofISSCon = MT700.get(dtofISSIntex + 2);
//					System.out.println("Act - " + dtofISSCon + " | Exp" + ISSSWI_DATA_MAP.get("31C"));
				swiftContent(ISSSWI_DATA_MAP.get("31C"), dtofISSCon, "MT700_Date of Issue");
			} else {
				log("MT700_Presence of Date of Issue check - Fail");
			}
		}

		// Applicable Rules
		if (!ISSSWI_DATA_MAP.get("40E").isEmpty()) {
			if (MT700.contains("Applicable Rules")) {
				log("MT700_Presence of Applicable Rules check - Pass");
				int appRulesIntex = MT700.indexOf("Applicable Rules");
				// TagCheck
				String appRulesTag = MT700.get(appRulesIntex - 1);
				swiftTag(appRulesTag, "40E:", "MT700_Applicable Rules");
				// ContentCheck
				String appRulesCon = MT700.get(appRulesIntex + 2);
//					System.out.println("Act - " + appRulesCon + " | Exp" + ISSSWI_DATA_MAP.get("40E"));
				swiftContent(ISSSWI_DATA_MAP.get("40E"), appRulesCon, "MT700_Applicable Rules");
			} else {
				log("MT700_Presence of Applicable Rules - Fail");
			}
		}

		// Date and Place of Expiry
		if (!ISSSWI_DATA_MAP.get("31D_1").isEmpty() && !ISSSWI_DATA_MAP.get("31D_2").isEmpty()) {
			if (MT700.contains("Date and Place of Expiry")) {
				log("MT700_Presence of Date and Place of Expiry check - Pass");
				int datePlaceExIntex = MT700.indexOf("Date and Place of Expiry");
				// TagCheck
				String datePlaceExTag = MT700.get(datePlaceExIntex - 1);
				swiftTag(datePlaceExTag, "31D:", "MT700_Date and Place of Expiry");
				// ContentCheck
				String datePlaceExCon = MT700.get(datePlaceExIntex + 2);
				String expDatePlaceExCon = ISSSWI_DATA_MAP.get("31D_1") + ISSSWI_DATA_MAP.get("31D_2");
//					System.out.println("Act - " + datePlaceExCon + " | Exp" + expDatePlaceExCon);
				swiftContent(expDatePlaceExCon, datePlaceExCon, "MT700_Date and Place of Expiry");
			} else {
				log("MT700_Presence of Date and Place of Expiry check - Fail");
			}
		}

		// Applicant Bank
		if (!ISSSWI_DATA_MAP.get("APPL_BK_ID").isEmpty()) {
			if (MT700.contains("Applicant Bank")) {
				log("MT700_Presence of Applicant Bank check - Pass");
				int appBKIntex = MT700.indexOf("Applicant Bank");
				// TagCheck
				String appBKTag = MT700.get(appBKIntex - 1);
				swiftTag(appBKTag, "APPL_BK_ID:", "MT700_Applicant Bank");
				// ContentCheck
				String appBKCon = MT700.get(appBKIntex + 2);
//					System.out.println("Act - " + appBKCon + " | Exp" + ISSSWI_DATA_MAP.get("APPL_BK_ID"));
				swiftContent(ISSSWI_DATA_MAP.get("APPL_BK_ID"), appBKCon, "MT700_Applicant Bank");
			} else {
				log("MT700_Presence of Applicant Bank - Fail");
			}
		}

		// Applicant
		if (MT700.contains("Applicant")) {
			log("MT700_Presence of Applicant check - Pass");
			int applicantInt = MT700.indexOf("Applicant");
			// TagCheck
			String applicantTag = MT700.get(applicantInt - 1);
			swiftTag(applicantTag, "50:", "MT700_Applicant");
			// ContentCheck
			String applicantCon = MT700.get(applicantInt + 2).replaceAll("\n", "").replaceAll(" ", "");
			String expapplicantCon = ISSSWI_DATA_MAP.get("50_1") + "\n" + ISSSWI_DATA_MAP.get("50_2") + "\n"
					+ ISSSWI_DATA_MAP.get("50_3") + "\n" + ISSSWI_DATA_MAP.get("50_4");
			Thread.sleep(300);

			String expapplicantCon2 = expapplicantCon.replaceAll("\n", "").replaceAll(" ", "");
//					System.out.println("Act - " + applicantCon + " | Exp" + expapplicantCon);
			swiftContent(expapplicantCon2, applicantCon, "MT700_Applicant");
		} else {
			log("MT700_Presence of Applicant check - Fail");
		}

		// Beneficiary
		if (MT700.contains("Beneficiary")) {
			log("MT700_Presence of Beneficiary check - Pass");
			int beneficiaryInt = MT700.indexOf("Beneficiary");
			// TagCheck
			String beneTag = MT700.get(beneficiaryInt - 1);
			swiftTag(beneTag, "59:", "MT700_Beneficiary");
			// ContentCheck
			String beneCont = MT700.get(beneficiaryInt + 2).replaceAll("\n", "").replaceAll(" ", "");
			;
			String expbeneCont = ISSSWI_DATA_MAP.get("59_1") + "\n" + ISSSWI_DATA_MAP.get("59_2") + "\n"
					+ ISSSWI_DATA_MAP.get("59_3") + "\n" + ISSSWI_DATA_MAP.get("59_4");
			String expbeneCont2 = expbeneCont.replaceAll("\n", "").replaceAll(" ", "");
//					System.out.println("Act - " + beneCont + " | Exp" + expbeneCont);
			swiftContent(expbeneCont2, beneCont, "MT700_Beneficiary");
		} else {
			log("MT700_Presence of Beneficiary check - Fail");
		}

		// Currency Code, Amount
		if (MT700.contains("Currency Code, Amount")) {
			log("MT700_Presence of Currency Code, Amount check - Pass");
			int ccyCodeInt = MT700.indexOf("Currency Code, Amount");
			// TagCheck
			String ccyCodeTag = MT700.get(ccyCodeInt - 1);
			swiftTag(ccyCodeTag, "32B:", "MT700_Currency Code, Amount");
			// ContentCheck
			String ccyCodeCont = MT700.get(ccyCodeInt + 2);
			String expCcyCodeCont = ISSSWI_DATA_MAP.get("32B_1") + ISSSWI_DATA_MAP.get("32B_2");
//					System.out.println("Act - " + ccyCodeCont + " | Exp" + expCcyCodeCont.concat(",00"));
			swiftContent(expCcyCodeCont.concat(",00"), ccyCodeCont, "MT700_Currency Code, Amount");
		} else {
			log("MT700_Presence of Currency Code, Amount check - Fail");
		}

		// Percentage Credit Amount Tolerance
		if (!hash_map.get("Tolerence").isEmpty()) {
			if (MT700.contains("Percentage Credit Amount Tolerance")) {
				log("MT700_Presence of Percentage Credit Amount Tolerance check - Pass");

				int perCreAmountInt = MT700.indexOf("Percentage Credit Amount Tolerance");
				// TagCheck
				String percentTag = MT700.get(perCreAmountInt - 1);
				swiftTag(percentTag, "39A:", "MT700_Percentage Credit Amount Tolerance");
				// ContentCheck
				String percentTagCon = MT700.get(perCreAmountInt + 2);
				String exppercentTagCon = ISSSWI_DATA_MAP.get("39A_1") + "/" + ISSSWI_DATA_MAP.get("39A_2");
//					System.out.println("Act - " + percentTagCon + " | Exp" + exppercentTagCon);
				swiftContent(exppercentTagCon, percentTagCon, "MT700_Percentage Credit Amount Tolerance");
			} else {
				log("MT700_Presence of Percentage Credit Amount Tolerance check - Fail");
			}
		}
		// Additional Amounts Covered
		if (!ISSSWI_DATA_MAP.get("39C").isEmpty()) {
			if (MT700.contains("Additional Amounts Covered")) {
				log("MT700_Presence of Additional Amounts Covered check - Pass");
				int addAmtCovInt = MT700.indexOf("Additional Amounts Covered");
				// TagCheck
				String addAmtTag = MT700.get(addAmtCovInt - 1);
				swiftTag(addAmtTag, "39C:", "MT700_Additional Amounts Covered");
				// ContentCheck
				String addAmtCov = MT700.get(addAmtCovInt + 2).replaceAll("\n", "").replaceAll(" ", "");
				String expAddAmtCov = ISSSWI_DATA_MAP.get("39C").replaceAll("\n", "").replaceAll(" ", "");
//					System.out.println("Act - " + addAmtCov + " | Exp - " + expAddAmtCov);
				swiftContent(expAddAmtCov, addAmtCov, "MT700_Additional Amounts Covered");
			} else {
				log("MT700_Presence of Additional Amounts Covered check - Fail");
			}
		}

		// Available With...By...
		if (MT700.contains("Available With ... By ...")) {
			log("MT700_Presence of Available With ... By ... check - Pass");
			int availableByInt = MT700.indexOf("Available With ... By ...");
			// TagCheck
			String availableTag = MT700.get(availableByInt - 1);
			swiftTag(availableTag, "41A:", "MT700_Available With ... By ...");
			// ContentCheck
			String avaiCon = MT700.get(availableByInt + 2);
			String expAvail = ISSSWI_DATA_MAP.get("41A_1") + "\n" + hash_map.get("AvailableBy");
//					System.out.println(expAvail);
			swiftContent(expAvail, avaiCon, "MT700_Available With ... By ...");
		} else {
			log("MT700_Presence of Available With ... By ... check - Fail");
		}

		// Drafts at
		if (!ISSSWI_DATA_MAP.get("DraftAt").isEmpty()) {
			if (MT700.contains("Drafts at")) {
				log("MT700_Presence of Drafts at check - Pass");
				int drAtIntex = MT700.indexOf("Drafts at");
				// TagCheck
				String drAtTag = MT700.get(drAtIntex - 1);
				swiftTag(drAtTag, "DraftAt:", "MT700_Drafts at");
				// ContentCheck
				String drAtCon = MT700.get(drAtIntex + 2);
//					System.out.println("Act - " + drAtCon + " | Exp" + ISSSWI_DATA_MAP.get("DraftAt"));
				swiftContent(ISSSWI_DATA_MAP.get("DraftAt"), drAtCon, "MT700_Drafts at");
			} else {
				log("MT700_Presence of Drafts at - Fail");
			}
		}

		// Drawee
		if (!ISSSWI_DATA_MAP.get("42A").isEmpty()) {
			if (MT700.contains("Drawee")) {
				log("MT700_Presence of Drawee check - Pass");
				int drweIntex = MT700.indexOf("Drawee");
				// TagCheck
				String drweTag = MT700.get(drweIntex - 1);
				swiftTag(drweTag, "42A:", "MT700_Drawee");
				// ContentCheck
				String drweCon = MT700.get(drweIntex + 2);
				System.out.println("Act dra- " + drweCon + " | Exp drw" + ISSSWI_DATA_MAP.get("42A"));
				swiftContent(ISSSWI_DATA_MAP.get("42A"), drweCon, "MT700_Drawee");
			} else {
				log("MT700_Presence of Drawee - Fail");
			}
		}

		// Partial Shipments
		if (!ISSSWI_DATA_MAP.get("43P").isEmpty()) {
			if (MT700.contains("Partial Shipments")) {
				log("MT700_Presence of Partial Shipments check - Pass");
				int prShpIntex = MT700.indexOf("Partial Shipments");
				// TagCheck
				String prShpTag = MT700.get(prShpIntex - 1);
				swiftTag(prShpTag, "43P:", "MT700_Partial Shipments");
				// ContentCheck
				String prShpCon = MT700.get(prShpIntex + 2);
//					System.out.println("Act - " + prShpCon + " | Exp" + ISSSWI_DATA_MAP.get("43P"));
				swiftContent(ISSSWI_DATA_MAP.get("43P"), prShpCon, "MT700_Partial Shipments");
			} else {
				log("MT700_Presence of Partial Shipments - Fail");
			}
		}

		// Transhipment
		if (!ISSSWI_DATA_MAP.get("43T").isEmpty()) {
			if (MT700.contains("Transhipment")) {
				log("MT700_Presence of Transhipment check - Pass");
				int trShpIntex = MT700.indexOf("Transhipment");
				// TagCheck
				String trShpTag = MT700.get(trShpIntex - 1);
				swiftTag(trShpTag, "43T:", "MT700_Transhipment");
				// ContentCheck
				String trShpCon = MT700.get(trShpIntex + 2);
//					System.out.println("Act - " + trShpCon + " | Exp" + ISSSWI_DATA_MAP.get("43T"));
				swiftContent(ISSSWI_DATA_MAP.get("43P"), trShpCon, "MT700_Transhipment");
			} else {
				log("MT700_Presence of Transhipment - Fail");
			}
		}

		// Place of Taking in Charge/Dispatch from .../Place of Receipt
		if (!ISSSWI_DATA_MAP.get("44A").isEmpty()) {
			if (MT700.contains("Place of Taking in Charge/Dispatch from .../Place of Receipt")) {
				log("MT700_Presence of Place of Taking in Charge/Dispatch from .../Place of Receipt check - Pass");
				int loadingInt = MT700.indexOf("Place of Taking in Charge/Dispatch from .../Place of Receipt");
				// TagCheck
				String loadingTag = MT700.get(loadingInt - 1);
				swiftTag(loadingTag, "44A:", "MT700_Place of Taking in Charge/Dispatch from .../Place of Receipt");
				// ContentCheck
				String loadinngCon = MT700.get(loadingInt + 2);
				String expLoadingCon = ISSSWI_DATA_MAP.get("44A");
				swiftContent(expLoadingCon, loadinngCon,
						"MT700_Place of Taking in Charge/Dispatch from .../Place of Receipt");
			} else {
				log("MT700_Presence of Place of Taking in Charge/Dispatch from .../Place of Receipt check - Fail");
			}
		}

		// Port of Loading/Airport of Departure
		if (!ISSSWI_DATA_MAP.get("44E").isEmpty()) {
			if (MT700.contains("Port of Loading/Airport of Departure")) {
				log("MT700_Presence of Port of Loading/Airport of Departure check - Pass");
				int loadingPortInt = MT700.indexOf("Port of Loading/Airport of Departure");
				// TagCheck
				String loadingPortTag = MT700.get(loadingPortInt - 1);
				swiftTag(loadingPortTag, "44E:", "MT700_Port of Loading/Airport of Departure");
				// ContentCheck
				String loadingPortCont = MT700.get(loadingPortInt + 2);
				String exploadingPortCont = ISSSWI_DATA_MAP.get("44E");
				swiftContent(exploadingPortCont, loadingPortCont, "MT700_Port of Loading/Airport of Departure");
			} else {
				log("MT700_Presence of Port of Loading/Airport of Departure check - Fail");
			}
		}
		// Port of Discharge/Airport of Destination
		if (!ISSSWI_DATA_MAP.get("44F").isEmpty()) {
			if (MT700.contains("Port of Discharge/Airport of Destination")) {
				log("MT700_Presence of Port of Discharge/Airport of Destination check - Pass");
				int disPortInt = MT700.indexOf("Port of Discharge/Airport of Destination");
				// TagCheck
				String disPortTag = MT700.get(disPortInt - 1);
				swiftTag(disPortTag, "44F:", "MT700_Port of Loading/Airport of Departure");
				// ContentCheck
				String disPortCont = MT700.get(disPortInt + 2);
				String expDisPortCont = ISSSWI_DATA_MAP.get("44F");
				swiftContent(expDisPortCont, disPortCont, "MT700_Port of Loading/Airport of Departure");
			} else {
				log("MT700_Presence of Port of Discharge/Airport of Destination check - Fail");
			}
		}
		// Place of Final Destination/For Transportation to .../Place of Delivery
		if (!ISSSWI_DATA_MAP.get("44B").isEmpty()) {
			if (MT700.contains("Place of Final Destination/For Transportation to .../Place of Delivery")) {
				log("MT700_Presence of Place of Final Destination/For Transportation to .../Place of Delivery check - Pass");
				int forTrnsTo = MT700.indexOf("Place of Final Destination/For Transportation to .../Place of Delivery");
				// TagCheck
				String forTrnsToTag = MT700.get(forTrnsTo - 1);
				swiftTag(forTrnsToTag, "44B:",
						"MT700_Place of Final Destination/For Transportation to .../Place of Delivery");
				// ContentCheck
				String forTrnsToCont = MT700.get(forTrnsTo + 2);

				String ExpforTrnsToCont = ISSSWI_DATA_MAP.get("44B");
//					System.out.println("Act - " + forTrnsToCont + " | Exp - " + ExpforTrnsToCont);
				swiftContent(ExpforTrnsToCont, forTrnsToCont,
						"MT700_Place of Final Destination/For Transportation to .../Place of Delivery");
			} else {
				log("MT700_Presence of Place of Final Destination/For Transportation to .../Place of Delivery check - Fail");
			}
		}
		// Latest Date of Shipment
		if (!ISSSWI_DATA_MAP.get("44C_1").isEmpty()) {
			if (MT700.contains("Latest Date of Shipment")) {
				log("MT700_Presence of Latest Date of Shipment check - Pass");
				int latShipDate = MT700.indexOf("Latest Date of Shipment");
				// TagCheck
				String latShipDateTag = MT700.get(latShipDate - 1);
				swiftTag(latShipDateTag, "44C:", "MT700_Latest Date of Shipment");
				// ContentCheck
				String latShipDateCont = MT700.get(latShipDate + 2);
				String ExplatShipDateCont = ISSSWI_DATA_MAP.get("44C_1");
//					System.out.println("Act - " + latShipDateCont + " | Exp - " + ExplatShipDateCont);
				swiftContent(ExplatShipDateCont, latShipDateCont, "MT700_Latest Date of Shipment");
			} else {
				log("MT700_Presence of Latest Date of Shipment check - Fail");
			}
		}

		// Description of Goods and/or Services
		if (!ISSSWI_DATA_MAP.get("45A_1").isEmpty()) {
			if (MT700.contains("Description of Goods and/or Services")) {
				log("MT700_Presence of Description of Goods and/or Services check - Pass");
				int desofGoodsIndex = MT700.indexOf("Description of Goods and/or Services");
				// TagCheck
				String desofGoodsTag = MT700.get(desofGoodsIndex - 1);
				swiftTag(desofGoodsTag, "45A:", "MT700_Description of Goods and/or Services");
				// ContentCheck
				String desofGoodsCont = MT700.get(desofGoodsIndex + 2).replaceAll("\n", "").replaceAll(" ", "");
				String ExpdesofGoodsCont = ISSSWI_DATA_MAP.get("45A_1").replaceAll("\n", "").replaceAll(" ", "")
						.concat(ISSSWI_DATA_MAP.get("45A_1_2").replaceAll("\n", "").replaceAll(" ", "")).toUpperCase();

				System.out.println("Act - " + desofGoodsCont + " | Exp - " + ExpdesofGoodsCont);
				swiftContent(ExpdesofGoodsCont, desofGoodsCont, "MT700_Description of Goods and/or Services");
			} else {
				log("MT700_Presence of Description of Goods and/or Services check - Fail");
			}
		}

		// Documents Required
		if (!ISSSWI_DATA_MAP.get("46A").isEmpty()) {
			if (MT700.contains("Documents Required")) {
				log("MT700_Presence of Documents Required check - Pass");
				int docReqIndex = MT700.indexOf("Documents Required");
				// TagCheck
				String docReqTag = MT700.get(docReqIndex - 1);
				swiftTag(docReqTag, "46A:", "MT700_Documents Required");
				// ContentCheck
				String docReqCont = MT700.get(docReqIndex + 2).replaceAll("\n", "").replaceAll(" ", "");
				String ExpdocReqCont = ISSSWI_DATA_MAP.get("46A").replaceAll("\n", "").replaceAll(" ", "");
//					System.out.println("Act - " + docReqCont + " | Exp - " + ExpdocReqCont);
				swiftContent(ExpdocReqCont, docReqCont, "MT700_Documents Required");
			} else {
				log("MT700_Presence of Documents Required check - Fail");
			}
		}

		// Additional Conditions
		if (!ISSSWI_DATA_MAP.get("47A").isEmpty()) {
			if (MT700.contains("Additional Conditions")) {
				log("MT700_Presence of Additional Conditions check - Pass");
				int addconIndex = MT700.indexOf("Additional Conditions");
				// TagCheck
				String addconTag = MT700.get(addconIndex - 1);
				swiftTag(addconTag, "47A:", "MT700_Additional Conditions");
				// ContentCheck
				String addconCont = MT700.get(addconIndex + 2).replaceAll("\n", "").replaceAll(" ", "");
				String ExpaddconCont = ISSSWI_DATA_MAP.get("47A").replaceAll("\n", "").replaceAll(" ", "");
//					System.out.println("Act - " + addconCont + " | Exp - " + ExpaddconCont);
				swiftContent(ExpaddconCont, addconCont, "MT700_Additional Conditions");
			} else {
				log("MT700_Presence of Additional Conditions check - Fail");
			}
		}

		// Special Payment Conditions for Beneficiary
		if (!ISSSWI_DATA_MAP.get("49G").isEmpty()) {
			if (MT700.contains("Special Payment Conditions for Beneficiary")) {
				log("MT700_Presence of Special Payment Conditions for Beneficiary check - Pass");
				int splpycondIndex = MT700.indexOf("Special Payment Conditions for Beneficiary");
				// TagCheck
				String splpycondTag = MT700.get(splpycondIndex - 1);
				swiftTag(splpycondTag, "49G:", "MT700_Special Payment Conditions for Beneficiary");
				// ContentCheck
				String splpycondCont = MT700.get(splpycondIndex + 2).replaceAll("\n", "").replaceAll(" ", "");
				String ExpsplpycondCont = ISSSWI_DATA_MAP.get("49G").replaceAll("\n", "").replaceAll(" ", "");
//					System.out.println("Act - " + splpyconCont + " | Exp - " + ExpsplpyconCont);
				swiftContent(ExpsplpycondCont, splpycondCont, "MT700_Special Payment Conditions for Beneficiary");
			} else {
				log("MT700_Presence of Special Payment Conditions for Beneficiary check - Fail");
			}
		}

		// Special Payment Conditions for Bank Only
		if (!ISSSWI_DATA_MAP.get("49H").isEmpty()) {
			if (MT700.contains("Special Payment Conditions for Bank Only")) {
				log("MT700_Presence of Special Payment Conditions for Bank Only check - Pass");
				int splpybkIndex = MT700.indexOf("Special Payment Conditions for Bank Only");
				// TagCheck
				String splpybkTag = MT700.get(splpybkIndex - 1);
				swiftTag(splpybkTag, "49H:", "MT700_Special Payment Conditions for Bank Only");
				// ContentCheck
				String splpybkCont = MT700.get(splpybkIndex + 2).replaceAll("\n", "").replaceAll(" ", "");
				String ExpsplpybkCont = ISSSWI_DATA_MAP.get("49H").replaceAll("\n", "").replaceAll(" ", "");
//					System.out.println("Act - " + splpybkCont + " | Exp - " + ExpsplpybkCont);
				swiftContent(ExpsplpybkCont, splpybkCont, "MT700_Special Payment Conditions for Bank Only");
			} else {
				log("MT700_Presence of Special Payment Conditions for Bank Only check - Fail");
			}
		}

		// Charges
		if (!ISSSWI_DATA_MAP.get("71D").isEmpty()) {
			if (MT700.contains("Charges")) {
				log("MT700_Presence of Charges check - Pass");
				int chrgsIndex = MT700.indexOf("Charges");
				// TagCheck
				String chrgsTag = MT700.get(chrgsIndex - 1);
				swiftTag(chrgsTag, "71D:", "MT700_Charges");
				// ContentCheck
				String chrgsCont = MT700.get(chrgsIndex + 2).replaceAll("\n", "").replaceAll(" ", "");
				String ExpchrgsCont = ISSSWI_DATA_MAP.get("71D").replaceAll("\n", "").replaceAll(" ", "");
//					System.out.println("Act - " + chrgsCont + " | Exp - " + ExpchrgsCont);
				swiftContent(ExpchrgsCont, chrgsCont, "MT700_Charges");
			} else {
				log("MT700_Presence of Charges check - Fail");
			}
		}

		// Period for Presentation in Days
		if (!ISSSWI_DATA_MAP.get("48").isEmpty()) {
			if (MT700.contains("Period for Presentation in Days")) {
				log("MT700_Presence of Period for Presentation in Days check - Pass");
				int prdpreIndex = MT700.indexOf("Period for Presentation in Days");
				// TagCheck
				String prdpreTag = MT700.get(prdpreIndex - 1);
				swiftTag(prdpreTag, "48:", "MT700_Period for Presentation in Days");
				// ContentCheck
				String prdpreCont = MT700.get(prdpreIndex + 2).replaceAll("\n", "").replaceAll(" ", "");
				String ExpprdpreCont = ISSSWI_DATA_MAP.get("48").replaceAll("\n", "").replaceAll(" ", "");
//					System.out.println("Act - " + prdpreCont + " | Exp - " + ExpprdpreCont);
				swiftContent(ExpprdpreCont, prdpreCont, "MT700_Period for Presentation in Days");
			} else {
				log("MT700_Presence of Period for Presentation in Days check - Fail");
			}
		}

		// Confirmation Instructions
		if (!ISSSWI_DATA_MAP.get("49").isEmpty()) {
			if (MT700.contains("Confirmation Instructions")) {
				log("MT700_Presence of Confirmation Instructions check - Pass");
				int confinsIndex = MT700.indexOf("Confirmation Instructions");
				// TagCheck
				String confinsTag = MT700.get(confinsIndex - 1);
				swiftTag(confinsTag, "49:", "MT700_Confirmation Instructions");
				// ContentCheck
				String confinsCont = MT700.get(confinsIndex + 2).replaceAll("\n", "").replaceAll(" ", "");
				String ExpconfinsCont = ISSSWI_DATA_MAP.get("49").replaceAll("\n", "").replaceAll(" ", "");
//					System.out.println("Act - " + confinsCont + " | Exp - " + ExpconfinsCont);
				swiftContent(ExpconfinsCont, confinsCont, "MT700_Confirmation Instructions");
			} else {
				log("MT700_Presence of Confirmation Instructions check - Fail");
			}
		}

		// Requested Confirmation Party
		if (!ISSSWI_DATA_MAP.get("58A").isEmpty()) {
			if (MT700.contains("Requested Confirmation Party")) {
				log("MT700_Presence of Requested Confirmation Party check - Pass");
				int reqconfprtyIndex = MT700.indexOf("Requested Confirmation Party");
				// TagCheck
				String reqconfprtyTag = MT700.get(reqconfprtyIndex - 1);
				swiftTag(reqconfprtyTag, "58A:", "MT700_Requested Confirmation Party");
				// ContentCheck
				String reqconfprtyCont = MT700.get(reqconfprtyIndex + 2).replaceAll("\n", "").replaceAll(" ", "");
				String ExpreqconfprtyCont = ISSSWI_DATA_MAP.get("58A").replaceAll("\n", "").replaceAll(" ", "");
//					System.out.println("Act - " + reqconfprtyCont + " | Exp - " + ExpreqconfprtyCont);
				swiftContent(ExpreqconfprtyCont, reqconfprtyCont, "MT700_Requested Confirmation Party");
			} else {
				log("MT700_Presence of Requested Confirmation Party check - Fail");
			}
		}

		// Reimbursing Bank
		if (!ISSSWI_DATA_MAP.get("53A").isEmpty()) {
			if (MT700.contains("Reimbursing Bank")) {
				log("MT700_Presence of Reimbursing Bank check - Pass");
				int reimBKIndex = MT700.indexOf("Reimbursing Bank");
				// TagCheck
				String reimBKTag = MT700.get(reimBKIndex - 1);
				swiftTag(reimBKTag, "53A:", "MT700_Reimbursing Bank");
				// ContentCheck
				String reimBKCont = MT700.get(reimBKIndex + 2).replaceAll("\n", "").replaceAll(" ", "");
				String ExpreimBKCont = ISSSWI_DATA_MAP.get("B2_REIM").replaceAll("\n", "").replaceAll(" ", "");
				System.out.println("Act reim - " + reimBKCont + " | Exp - reim " + ExpreimBKCont);
				swiftContent(ExpreimBKCont, reimBKCont, "MT700_Reimbursing Bank");
			} else {
				log("MT700_Presence of Reimbursing Bank check - Fail");
			}
		}

		// Instructions to the Paying/Accepting/Negotiating Bank
		if (!ISSSWI_DATA_MAP.get("78").isEmpty()) {
			if (MT700.contains("Instructions to the Paying/Accepting/Negotiating Bank")) {
				log("MT700_Presence of Instructions to the Paying/Accepting/Negotiating Bank check - Pass");
				int instopayaccIndex = MT700.indexOf("Instructions to the Paying/Accepting/Negotiating Bank");
				// TagCheck
				String instopayaccTag = MT700.get(instopayaccIndex - 1);
				swiftTag(instopayaccTag, "78:", "MT700_Instructions to the Paying/Accepting/Negotiating Bank");
				// ContentCheck
				String instopayaccCont = MT700.get(instopayaccIndex + 2).replaceAll("\n", "").replaceAll(" ", "");
				String ExpinstopayaccCont = ISSSWI_DATA_MAP.get("78").replaceAll("\n", "").replaceAll(" ", "");
//					System.out.println("Act - " + instopayaccCont + " | Exp - " + ExpinstopayaccCont);
				swiftContent(ExpinstopayaccCont, instopayaccCont,
						"MT700_Instructions to the Paying/Accepting/Negotiating Bank");
			} else {
				log("MT700_Presence of Instructions to the Paying/Accepting/Negotiating Bank check - Fail");
			}
		}

		// 'Advise Through' Bank
		if (!ISSSWI_DATA_MAP.get("57A").isEmpty()) {
			if (MT700.contains("'Advise Through' Bank")) {
				log("MT700_Presence of 'Advise Through' Bank check - Pass");
				int AdvtrBankIndex = MT700.indexOf("'Advise Through' Bank");
				// TagCheck
				String AdvtrBankTag = MT700.get(AdvtrBankIndex - 1);
				swiftTag(AdvtrBankTag, "57A:", "MT700_'Advise Through' Bank");
				// ContentCheck
				String AdvtrBankCont = MT700.get(AdvtrBankIndex + 2).replaceAll("\n", "").replaceAll(" ", "");
				Thread.sleep(300);
				String ExpAdvtrBankCont = ISSSWI_DATA_MAP.get("57A").replaceAll("\n", "").replaceAll(" ", "");
//					System.out.println("Act - " + AdvtrBankCont + " | Exp - " + ExpAdvtrBankCont);
				swiftContent(ExpAdvtrBankCont, AdvtrBankCont, "MT700_'Advise Through' Bank");
			} else {
				log("MT700_Presence of 'Advise Through' Bank check - Fail");
			}
		}

		// Sender to Receiver Information
		if (!ISSSWI_DATA_MAP.get("72Z_1").isEmpty()) {
			if (MT700.contains("Sender to Receiver Information")) {
				log("MT700_Presence of Sender to Receiver Information check - Pass");
				int sndtorcvbkinfIndex = MT700.indexOf("Sender to Receiver Information");
				// TagCheck
				String sndtorcvbkinfTag = MT700.get(sndtorcvbkinfIndex - 1);
				swiftTag(sndtorcvbkinfTag, "72Z:", "MT700_Sender to Receiver Information");
				// ContentCheck
				String sndtorcvbkinfCont = MT700.get(sndtorcvbkinfIndex + 2).replaceAll("\n", "").replaceAll(" ", "");
				Thread.sleep(300);
				String ExpsndtorcvbkinfCont = ISSSWI_DATA_MAP.get("72Z_1").replaceAll("\n", "").replaceAll(" ", "");
//					System.out.println("Act - " + sndtorcvbkinfCont + " | Exp - " + ExpsndtorcvbkinfCont);
				swiftContent(ExpsndtorcvbkinfCont, sndtorcvbkinfCont, "MT700_Sender to Receiver Information");
			} else {
				log("MT700_Presence of Sender to Receiver Information check - Fail");
			}
		}
		driver.close();
	}

	@When("^Click On issreimswift button$")
	public void click_On_issreimswift_button() throws Throwable {
		switchToWindow(1);
		driver.manage().window().maximize();
		WebElement eleView = locateElement("xpath",
				"//*[@id=\"ext-gen6\"]/form/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/div/table/tbody/tr[4]/td/table/tbody/tr[2]/td[3]/a");
		click(eleView);
		Thread.sleep(6000);
		switchToWindow(2);
		driver.manage().window().maximize();
	}

	@Then("^Validate the issreimswift MT(\\d+)$")
	public void validate_the_issreimswift_MT(int arg1) throws Throwable {
		Thread.sleep(6000);
		WebElement table = driver.findElement(
				By.xpath("/html/body/table/tbody/tr[5]/td/table/tbody/tr[1]/td[2]/div/table[1]/tbody/tr[4]/td/table"));
		List<WebElement> column = table.findElements(By.tagName("td"));
		List<String> MT740 = new ArrayList<String>();
		for (int j = 0; j < column.size(); j++) {
			MT740.add(column.get(j).getText());
		}
		// Outgoing SWIFT Header 1
		int headerIntex = MT740.indexOf("Outgoing SWIFT Header 1");
		// TagCheck
		String headerTag = MT740.get(headerIntex - 1);
		swiftTag(headerTag, "B1:", "MT740_Outgoing SWIFT Header 1");

		// Receiver's BIC Code
		int recBicIntex = MT740.indexOf("Receiver's BIC Code");
		// TagCheck
		String recBicTag = MT740.get(recBicIntex - 1);
		swiftTag(recBicTag, "B2:", "MT740_Receiver's BIC Code");
		// ContentCheck
		String recBicCon = MT740.get(recBicIntex + 2);
		String swiftBic = ISSSWI_DATA_MAP.get("B2_REIM");
//			System.out.println("Before = " + swiftBic);
		Thread.sleep(300);
		while (swiftBic.length() < 12) {
			Thread.sleep(300);
			swiftBic = swiftBic.concat("X");
		}
//			System.out.println("After = " + swiftBic);
		String ExpRecBicCon = "I740" + swiftBic + "N2";
		System.out.println("Act bic - " + recBicCon + " | Exp bic " + ExpRecBicCon);
		swiftContent(ExpRecBicCon, recBicCon, "MT740_Receiver's BIC Code");

		// Transaction Reference Number
		if (!hash_map.get("referenceNo").isEmpty()) {
			if (MT740.contains("Transaction Reference Number")) {
				log("MT740_Presence of Transaction Reference Number check - Pass");
				int lcNumberIntex = MT740.indexOf("Transaction Reference Number");
				// TagCheck
				String lcNumberTag = MT740.get(lcNumberIntex - 1);
				swiftTag(lcNumberTag, "20:", "MT740_Transaction Reference Number");
				// ContentCheck
				String lcNumberCon = MT740.get(lcNumberIntex + 2);
				swiftContent(hash_map.get("referenceNo"), lcNumberCon, "MT740_Transaction Reference Number");
			} else {
				log("MT740_Presence of Transaction Reference Number check - Fail");
			}
		}

		// Applicable Rules
		if (!ISSSWI_DATA_MAP.get("40F").isEmpty()) {
			if (MT740.contains("Applicable Rules")) {
				log("MT740_Presence of Applicable Rules check - Pass");
				int appRulesIntex = MT740.indexOf("Applicable Rules");
				// TagCheck
				String appRulesTag = MT740.get(appRulesIntex - 1);
				swiftTag(appRulesTag, "40F:", "MT740_Applicable Rules");
				// ContentCheck
				String appRulesCon = MT740.get(appRulesIntex + 2);
//			System.out.println("Act - " + appRulesCon + " | Exp" + ISSSWI_DATA_MAP.get("40E"));
				swiftContent(ISSSWI_DATA_MAP.get("40F"), appRulesCon, "MT740_Applicable Rules");
			} else {
				log("MT740_Presence of Applicable Rules - Fail");
			}
		}

		// Date and Place of Expiry
		if (!ISSSWI_DATA_MAP.get("31D_1").isEmpty() && !ISSSWI_DATA_MAP.get("31D_2").isEmpty()) {
			if (MT740.contains("Date and Place of Expiry")) {
				log("MT740_Presence of Date and Place of Expiry check - Pass");
				int datePlaceExIntex = MT740.indexOf("Date and Place of Expiry");
				// TagCheck
				String datePlaceExTag = MT740.get(datePlaceExIntex - 1);
				swiftTag(datePlaceExTag, "31D:", "MT740_Date and Place of Expiry");
				// ContentCheck
				String datePlaceExCon = MT740.get(datePlaceExIntex + 2);
				String expDatePlaceExCon = ISSSWI_DATA_MAP.get("31D_1") + ISSSWI_DATA_MAP.get("31D_2");
//			System.out.println("Act - " + datePlaceExCon + " | Exp" + expDatePlaceExCon);
				swiftContent(expDatePlaceExCon, datePlaceExCon, "MT740_Date and Place of Expiry");
			} else {
				log("MT740_Presence of Date and Place of Expiry check - Fail");
			}
		}

		// Beneficiary
		if (MT740.contains("Beneficiary")) {
			log("MT740_Presence of Beneficiary check - Pass");
			int beneficiaryInt = MT740.indexOf("Beneficiary");
			// TagCheck
			String beneTag = MT740.get(beneficiaryInt - 1);
			swiftTag(beneTag, "59:", "MT740_Beneficiary");
			// ContentCheck
			String beneCont = MT740.get(beneficiaryInt + 2).replaceAll("\n", "").replaceAll(" ", "");
			String expbeneCont = ISSSWI_DATA_MAP.get("59_1") + "\n" + ISSSWI_DATA_MAP.get("59_2") + "\n"
					+ ISSSWI_DATA_MAP.get("59_3") + "\n" + ISSSWI_DATA_MAP.get("59_4");
			String expbeneCont2 = expbeneCont.replaceAll("\n", "").replaceAll(" ", "");
//			System.out.println("Act - " + beneCont + " | Exp" + expbeneCont);
			swiftContent(expbeneCont2, beneCont, "MT740_Beneficiary");
		} else {
			log("MT740_Presence of Beneficiary check - Fail");
		}

		// Credit Amount
		if (MT740.contains("Credit Amount")) {
			log("MT740_Presence of Credit Amount check - Pass");
			int crAmtInt = MT740.indexOf("Credit Amount");
			// TagCheck
			String crAmtTag = MT740.get(crAmtInt - 1);
			swiftTag(crAmtTag, "32B:", "MT740_Credit Amount");
			// ContentCheck
			String crAmtCont = MT740.get(crAmtInt + 2);
			String expcrAmtCont = ISSSWI_DATA_MAP.get("32B_1") + ISSSWI_DATA_MAP.get("32B_2");
//			System.out.println("Act - " + crAmtCont + " | Exp" + expcrAmtCont.concat(",00"));
			swiftContent(expcrAmtCont.concat(",00"), crAmtCont, "MT740_Credit Amount");
		} else {
			log("MT740_Presence of Credit Amount check - Fail");
		}

		// Percentage Credit Amount Tolerance
		if (!hash_map.get("Tolerence").isEmpty()) {
			if (MT740.contains("Percentage Credit Amount Tolerance")) {
				log("MT740_Presence of Percentage Credit Amount Tolerance check - Pass");

				int perCreAmountInt = MT740.indexOf("Percentage Credit Amount Tolerance");
				// TagCheck
				String percentTag = MT740.get(perCreAmountInt - 1);
				swiftTag(percentTag, "39A:", "MT740_Percentage Credit Amount Tolerance");
				// ContentCheck
				String percentTagCon = MT740.get(perCreAmountInt + 2);
				String exppercentTagCon = ISSSWI_DATA_MAP.get("39A_1") + "/" + ISSSWI_DATA_MAP.get("39A_2");
//			System.out.println("Act - " + percentTagCon + " | Exp" + exppercentTagCon);
				swiftContent(exppercentTagCon, percentTagCon, "MT740_Percentage Credit Amount Tolerance");
			} else {
				log("MT740_Presence of Percentage Credit Amount Tolerance check - Fail");
			}
		}

		// Additional Amounts Covered
		if (!ISSSWI_DATA_MAP.get("39C").isEmpty()) {
			if (MT740.contains("Additional Amounts Covered")) {
				log("MT740_Presence of Additional Amounts Covered check - Pass");
				int addAmtCovInt = MT740.indexOf("Additional Amounts Covered");
				// TagCheck
				String addAmtTag = MT740.get(addAmtCovInt - 1);
				swiftTag(addAmtTag, "39C:", "MT740_Additional Amounts Covered");
				// ContentCheck
				String addAmtCov = MT740.get(addAmtCovInt + 2).replaceAll("\n", "").replaceAll(" ", "");
				String expAddAmtCov = ISSSWI_DATA_MAP.get("39C").replaceAll("\n", "").replaceAll(" ", "");
//			System.out.println("Act - " + addAmtCov + " | Exp - " + expAddAmtCov);
				swiftContent(expAddAmtCov, addAmtCov, "MT740_Additional Amounts Covered");
			} else {
				log("MT740_Presence of Additional Amounts Covered check - Fail");
			}
		}

		// Available With...By...
		if (MT740.contains("Available With...By...")) {
			log("MT740_Presence of Available With...By... check - Pass");
			int availableByInt = MT740.indexOf("Available With...By...");
			// TagCheck
			String availableTag = MT740.get(availableByInt - 1);
			swiftTag(availableTag, "41A:", "MT740_Available With...By...");
			// ContentCheck
			String avaiCon = MT740.get(availableByInt + 2);
			String expAvail = ISSSWI_DATA_MAP.get("41A_1") + "\n" + hash_map.get("AvailableBy");
//			System.out.println(expAvail);
			swiftContent(expAvail, avaiCon, "MT740_Available With...By...");
		} else {
			log("MT740_Presence of Available With...By... check - Fail");
		}

		// Drafts at
		if (!ISSSWI_DATA_MAP.get("DraftAt").isEmpty()) {
			if (MT740.contains("Drafts at")) {
				log("MT740_Presence of Drafts at check - Pass");
				int drAtIntex = MT740.indexOf("Drafts at");
				// TagCheck
				String drAtTag = MT740.get(drAtIntex - 1);
				swiftTag(drAtTag, "DraftAt:", "MT740_Drafts at");
				// ContentCheck
				String drAtCon = MT740.get(drAtIntex + 2);
//			System.out.println("Act - " + drAtCon + " | Exp" + ISSSWI_DATA_MAP.get("DraftAt"));
				swiftContent(ISSSWI_DATA_MAP.get("DraftAt"), drAtCon, "MT740_Drafts at");
			} else {
				log("MT740_Presence of Drafts at - Fail");
			}
		}

		// Drawee
		if (!ISSSWI_DATA_MAP.get("42A").isEmpty()) {
			if (MT740.contains("Drawee")) {
				log("MT740_Presence of Drawee check - Pass");
				int drweIntex = MT740.indexOf("Drawee");
				// TagCheck
				String drweTag = MT740.get(drweIntex - 1);
				swiftTag(drweTag, "42A:", "MT740_Drawee");
				// ContentCheck
				String drweCon = MT740.get(drweIntex + 2);
				System.out.println("Act - " + drweCon + " | Exp" + ISSSWI_DATA_MAP.get("42A"));
				swiftContent(ISSSWI_DATA_MAP.get("42A"), drweCon, "MT740_Drawee");
			} else {
				log("MT740_Presence of Drawee - Fail");
			}
		}

		// Reimbursing Bank's Charges
		if (!ISSSWI_DATA_MAP.get("71A").isEmpty()) {
			if (MT740.contains("Reimbursing Bank's Charges")) {
				log("MT740_Presence of Reimbursing Bank's Charges check - Pass");
				int reimChrgIntex = MT740.indexOf("Reimbursing Bank's Charges");
				// TagCheck
				String reimChrgTag = MT740.get(reimChrgIntex - 1);
				swiftTag(reimChrgTag, "71A:", "MT740_Reimbursing Bank's Charges");
				// ContentCheck
				String reimChrgCon = MT740.get(reimChrgIntex + 2);
//			System.out.println("Act - " + reimChrgCon + " | Exp" + ISSSWI_DATA_MAP.get("71A"));
				swiftContent(ISSSWI_DATA_MAP.get("71A"), reimChrgCon, "MT740_Reimbursing Bank's Charges");
			} else {
				log("MT740_Presence of Reimbursing Bank's Charges - Fail");
			}
		}

		// Other Charges
		if (!ISSSWI_DATA_MAP.get("71D_Reim").isEmpty()) {
			if (MT740.contains("Other Charges")) {
				log("MT740_Presence of Other Charges check - Pass");
				int chrgsIndex = MT740.indexOf("Other Charges");
				// TagCheck
				String chrgsTag = MT740.get(chrgsIndex - 1);
				swiftTag(chrgsTag, "71D:", "MT740_Other Charges");
				// ContentCheck
				String chrgsCont = MT740.get(chrgsIndex + 2).replaceAll("\n", "").replaceAll(" ", "");
				String ExpchrgsCont = ISSSWI_DATA_MAP.get("71D_Reim").replaceAll("\n", "").replaceAll(" ", "");
//			System.out.println("Act - " + chrgsCont + " | Exp - " + ExpchrgsCont);
				swiftContent(ExpchrgsCont, chrgsCont, "MT740_Other Charges");
			} else {
				log("MT740_Presence of Other Charges check - Fail");
			}
		}

		// Sender to Receiver Information
		if (!ISSSWI_DATA_MAP.get("72Z_Reim").isEmpty()) {
			if (MT740.contains("Sender to Receiver Information")) {
				log("MT740_Presence of Sender to Receiver Information check - Pass");
				int sndtorcvbkinfIndex = MT740.indexOf("Sender to Receiver Information");
				// TagCheck
				String sndtorcvbkinfTag = MT740.get(sndtorcvbkinfIndex - 1);
				swiftTag(sndtorcvbkinfTag, "72Z:", "MT740_Sender to Receiver Information");
				// ContentCheck
				String sndtorcvbkinfCont = MT740.get(sndtorcvbkinfIndex + 2).replaceAll("\n", "").replaceAll(" ", "");
				Thread.sleep(300);
				String ExpsndtorcvbkinfCont = ISSSWI_DATA_MAP.get("72Z_Reim").replaceAll("\n", "").replaceAll(" ", "");
//			System.out.println("Act - " + sndtorcvbkinfCont + " | Exp - " + ExpsndtorcvbkinfCont);
				swiftContent(ExpsndtorcvbkinfCont, sndtorcvbkinfCont, "MT740_Sender to Receiver Information");
			} else {
				log("MT740_Presence of Sender to Receiver Information check - Fail");
			}
		}
		driver.close();

	}

	@When("^Click On IssueAdvice Swift button$")
	public void click_on_issueadvice_swift_button() throws Throwable {
		switchToWindow(1);
		driver.manage().window().maximize();
		WebElement eleView = locateElement("xpath", "(//A)[3]");
		click(eleView);
		Thread.sleep(6000);
		switchToWindow(2);
		driver.manage().window().maximize();
	}

	@Then("^Validate the IssueAdvice Swift$")
	public void validate_the_issueadvice_swift() throws Throwable {
		swiftValidation("Issue Letter of Credit");
		driver.close();
		switchToWindow(0);
	}

	@And("^Release The Transaction$")
	public void release_the_Issue_transaction() throws Throwable {
		driver.switchTo().defaultContent();
		driver.switchTo().frame("FunctionList");
		driver.findElementByName("IPLC Maintenance").click();
		String Refnumber = hash_map.get("referenceNo");
		Thread.sleep(3000);
		release(Refnumber);
		cancel();
	}

	@Given("^click on Register Amendment$")
	public void click_on_Register_Amendment() throws Throwable {
		/* Thread.sleep(3000); */
		driver.switchTo().defaultContent();
		driver.switchTo().frame("FunctionList");
		WebElement eleIPLCAmd = locateElement("name", "IPLC Amendment");
		eleIPLCAmd.click();
		/* Thread.sleep(3000); */
		WebElement eleRegAmd = locateElement("name", "G49082300296F05030702018");
		eleRegAmd.click();
		String refNum = hash_map.get("referenceNo");
		/* Thread.sleep(3000); */
		catalog(refNum);
	}

	@And("^Enter Increase Amount$")
	public void enter_Increase_Amount() throws Throwable {
		Thread.sleep(2000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		Thread.sleep(2000);
		WebElement eleInAmt = locateElement("name", "INC_AMT");
		String InAmd = data.getCellValue("Scenario 1", 3, 163);
		if (!InAmd.isEmpty()) {
			/* Thread.sleep(1000); */
			eleInAmt.clear();
			eleInAmt.click();
			append(eleInAmt, InAmd);
			eleInAmt.sendKeys(Keys.TAB);
			ISSSWI_DATA_MAP.put("IncAmt", getTypedText(eleInAmt));
		} else {
			ISSSWI_DATA_MAP.put("IncAmt", getTypedText(eleInAmt));
		}
	}

	@And("^Enter Decrease Amount$")
	public void enter_Decrease_Amount() throws Throwable {
		String DeAmd = data.getCellValue("Scenario 1", 3, 164);
		WebElement eleNegAmt = locateElement("name", "DEC_AMT");
		if (!DeAmd.isEmpty()) {
			eleNegAmt.clear();
			eleNegAmt.click();
			append(eleNegAmt, DeAmd);
			eleNegAmt.sendKeys(Keys.TAB);
			ISSSWI_DATA_MAP.put("DecAmt", getTypedText(eleNegAmt).replaceAll(",", ""));
		} else {
			ISSSWI_DATA_MAP.put("DecAmt", getTypedText(eleNegAmt).replaceAll(",", ""));
		}
	}

	@And("^Enter New Tolerance$")
	public void enter_New_Tolerance() throws Throwable {
		String tol = data.getCellValue("Scenario 1", 3, 165);
		WebElement eletolerence = locateElement("name", "AMT_SPEC");
		if (tol == "NOT EXCEEDING") {
			selectDropDownUsingText(eletolerence, tol);
		}
		String posColour = locateElement("name", "NEW_POS_TOL").getCssValue("background-color");
		String negTolColour = locateElement("name", "NEW_NEG_TOL").getCssValue("background-color");
		if (getTypedText(eletolerence).equalsIgnoreCase("NOT EXCEEDING")) {
			if (posColour.contains(Protected) && negTolColour.contains(Protected)) {
				log("New Tolerence Onchanges : Pass");
			} else {
				log(" New Tolerence Onchanges : Fail");
			}
		}
		WebElement elePos = locateElement("name", "NEW_POS_TOL");
		String posTol = data.getCellValue("Scenario 1", 3, 166);
		MT707_DATA_MAP.put("PostveTlrnce", getTypedText(elePos));
		if (!posTol.isEmpty()) {
			append(elePos, posTol);
			elePos.sendKeys(Keys.TAB);
			MT707_DATA_MAP.put("PostveTlrnce", getTypedText(elePos));
		}
		String negTol = data.getCellValue("Scenario 1", 3, 167);
		WebElement eleNeg = locateElement("name", "NEW_NEG_TOL");
		MT707_DATA_MAP.put("NegtveTlrnce", getTypedText(eleNeg));
		if (!negTol.isEmpty()) {
			append(eleNeg, negTol);
			eleNeg.sendKeys(Keys.TAB);
			MT707_DATA_MAP.put("NegtveTlrnce", getTypedText(eleNeg));
		}
	}

	@Then("^Verify New LC Balance and New LC Amount$")
	public void verify_NewLCBalance_Amount() throws Throwable {
		String lc_Bal = locateElement("name", "LC_BAL").getAttribute("value");
		double lCBal = convertToDouble(lc_Bal);
		String LC_AMT = locateElement("name", "LC_AMT").getAttribute("value");
		double lcAmt = convertToDouble(LC_AMT);
		String INC_AMT = locateElement("name", "INC_AMT").getAttribute("value");
		double inAmd = convertToDouble(INC_AMT);
		String DEC_AMT = locateElement("name", "DEC_AMT").getAttribute("value");
		double deAmt = convertToDouble(DEC_AMT);
		String NEW_POS_TOL = locateElement("name", "NEW_POS_TOL").getAttribute("value");
		double newPos = convertToDouble(NEW_POS_TOL);
//		String NEW_NEG_TOL = locateElement("name", "NEW_NEG_TOL").getAttribute("value");
//		double newNeg = convertToDouble(NEW_NEG_TOL);
		double expNewBalance = (lCBal + (inAmd - deAmt)) * (1 + (newPos / 100));
		String ActBal = locateElement("name", "NEW_LC_BAL").getAttribute("value");
		double actNewBal = convertToDouble(ActBal);
		if (expNewBalance == actNewBal) {
			log("New LC Balance Check : Pass");
		} else {
			log("New LC Balance Check : Fail");
			takesnap("New LC Balance Check");
		}

		double expLcAmt = lcAmt + (inAmd - deAmt);
		String actnewAmt = locateElement("name", "NEW_LC_AMT").getAttribute("value");
		double actNewAmt = convertToDouble(actnewAmt);

		if (expLcAmt == actNewAmt) {
			log("New LC Balance Check : Pass");
		} else {
			log("New LC Balance Check : Fail");
			takesnap("New LC Balance Check");
		}
		MT707_DATA_MAP.put("LcBala", getTypedText(locateElement("name", "NEW_LC_BAL")).replaceAll(",", ""));
		MT707_DATA_MAP.put("LcAmt", getTypedText(locateElement("name", "NEW_LC_AMT")).replaceAll(",", ""));

	}

	@And("^Enter New Date of Expiry$")
	public void enter_newDateOfExpiry() throws Throwable {
		String newDOE = data.getCellValue("Scenario 1", 3, 168);
		WebElement eleNewDOE = locateElement("name", "NEW_EXPIRY_DT");
		if (!newDOE.isEmpty()) {
			append(eleNewDOE, newDOE);
			MT707_DATA_MAP.put("DtOfExp", getTypedText(eleNewDOE));
			ISSSWI_DATA_MAP.put("NewDtOfExp", getTypedText(eleNewDOE));
			MT707_DATA_MAP.put("DueDt", getTypedText(eleNewDOE));
		}

		System.out.println("----------------/" + getTypedText(eleNewDOE) + "/-----------------");
	}

	@And("^Enter New Place Of Expiry$")
	public void enter_newExpiryPlace() throws Throwable {
		String newPOE = data.getCellValue("Scenario 1", 3, 169);
		if (!newPOE.isEmpty()) {
			WebElement eleNewPOE = locateElement("name", "NEW_EXPIRY_PLC");
			selectDropDownUsingText(eleNewPOE, newPOE);
			String newPOE_Text = getTypedText(eleNewPOE);
			WebElement eleNewNarr = locateElement("name", "NEW_EXPIRY_PLC_NA");
			if (newPOE_Text.equalsIgnoreCase("OTHER")) {
				verifyNarrativeEnable(eleNewNarr, "OTHER", "Applicable Rules Onchange");
			} else {
				verifyNarrativeDisable(eleNewNarr, "OTHER", "Applicable Rules Onchange");
			}
		}
	}

	@And("^Enter Detrimental Flag$")
	public void enter_detrimentalFlag() throws Throwable {
		String newDFlag = data.getCellValue("Scenario 1", 3, 170);
		if (!newDFlag.isEmpty()) {
			WebElement eleDeFlag = locateElement("name", "DETRMNTL_FLG");
			selectDropDownUsingText(eleDeFlag, newDFlag);

		}
	}

	@And("^Enter Date of Amendment$")
	public void enter_dateofAmendment() throws Throwable {
		/*
		 * String newDFlag = data.getCellValue("Scenario 1", 3, 171); if
		 * (!newDFlag.isEmpty()) { WebElement eleDOA = locateElement("name",
		 * "DETRMNTL_FLG"); selectDropDownUsingText(eleDOA, newDFlag); }
		 */
	}

	@And("^Enter New Additional Amounts Covered$")
	public void enter_newAdditionalAmountsCovered() throws Throwable {
		String newaAMC = data.getCellValue("Scenario 1", 3, 172);
		if (!newaAMC.isEmpty()) {
			WebElement eleDeAAC = locateElement("name", "NEW_ADDIT_COV_AMT");
			append(eleDeAAC, newaAMC);
			MT707_DATA_MAP.put("AdditnlAmtCovrd", getTypedText(locateElement("name", "NEW_ADDIT_COV_AMT")));
		}
	}

	@And("^Click On ParitesI tab$")
	public void click_OnParitesItab() throws Throwable {
		WebElement elePar1 = locateElement("id", "C");
		click(elePar1);
	}

	@And("^Enter the New Beneficiary$")
	public void enter_theNewBeneficiary() throws Throwable {
		String newBen = data.getCellValue("Scenario 1", 2, 176);
		if (!newBen.isEmpty()) {
			WebElement eleDeFlag = locateElement("name", "NEW_BENE_ID");
			append(eleDeFlag, newBen);
			eleDeFlag.sendKeys(Keys.TAB);
			MT707_DATA_MAP.put("BnfcryNm", getTypedText(eleDeFlag));
			MT707_DATA_MAP.put("BnfcryAdr1", getTypedText(locateElement("name", "NEW_BENE_ADD1")));
			MT707_DATA_MAP.put("BnfcryAdr2", getTypedText(locateElement("name", "NEW_BENE_ADD2")));
			MT707_DATA_MAP.put("BnfcryAdr3", getTypedText(locateElement("name", "NEW_BENE_ADD3")));

		}
	}

	@And("^Click On Notes tab$")
	public void click_OnNotestab() throws Throwable {
		WebElement eleNotes = locateElement("id", "E");
		click(eleNotes);
	}

	@And("^Verify notes are diaplayed$")
	public void Verify_notesAreDiaplayed() throws Throwable {
		WebElement elenotes = locateElement("id", ("NOTES"));
		String AMDnote = getTypedText(elenotes);
		if (AMDnote.equalsIgnoreCase(hash_map.get("Notes"))) {
			log("Verify Notes details: PASS");
		} else {
			log("Verify Notes details: FAIL");
			takesnap("Verify Notes details");
		}
	}

	@And("^Click On Diary tab$")
	public void click_OnDiarytab() throws Throwable {
		WebElement eleDiary = locateElement("id", "F");
		click(eleDiary);
	}

	@And("^Enter RegAmd Related Reference$")
	public void enter_RelatedReference() throws Throwable {
		String rReference = hash_map.get("referenceNo");
		WebElement eleReRef = locateElement("name", ("DIARY_RELATED_REF"));
		append(eleReRef, rReference);
//		Thread.sleep(3000);
		String re = getTypedText(eleReRef);

		hash_map.put("RegAmdReRef", re);

		if (getTypedText(eleReRef).length() <= 16) {
			log("Verify Related Reference Length: PASS");
		} else {
			log("Verify Related Reference Length: FAIL");
			takesnap("Verify Related Reference Length");
		}
	}

	@And("^Enter RegAmd Narrative$")
	public void enter_RegAmdNarrative() throws Throwable {
		WebElement eleNarrative = locateElement("name", "DIARY_NARRATIVE");
		String narrative = data.getCellValue("Scenario 1", 2, 188);

		append(eleNarrative, narrative);
		String regAmdNarr = getTypedText(eleNarrative);
		hash_map.put("RegAmdNarrative", regAmdNarr);
		Actions actions = new Actions(driver);
//		Thread.sleep(5000);
		actions.moveToElement(eleNarrative)
				.moveToElement(
						driver.findElementByXPath("(//div[@id='F_div']//table)[2]/tbody[1]/tr[2]/td[2]/input[1]"))
				.click().build().perform();
	}

	@And("^Verify RegAmd Diary History$")
	public void verify_DiaryHistory() throws Throwable {
		String Rnarrative = hash_map.get("RegExpectedNarrative");
		String expIssNar = hash_map.get("IssNarrative");
		WebElement eleviewhistory = locateElement("id", ("view_1"));
		eleviewhistory.click();
		Set<String> windowId = driver.getWindowHandles();
		Iterator<String> itererator = windowId.iterator();
		String mainWinID = itererator.next();
		String newAdwinID = itererator.next();
		driver.switchTo().window(newAdwinID);

//		Thread.sleep(5000);
		if (Rnarrative != null) {
			WebElement eleReReference = locateElement("id", ("_id_1C_MAIN_REF"));
			String RegActualref = getTypedText(eleReReference);
			String nRef = hash_map.get("RegExpectedRef");
			WebElement elenNarrative = locateElement("id", ("DIARY_NARRATIVE"));
			String RegActualNarrative = getTypedText(elenNarrative);
			if (RegActualref.equalsIgnoreCase(nRef) || RegActualNarrative.equalsIgnoreCase(Rnarrative)) {
				log("Verify Issue Diary details: PASS");
			} else {
				log("Verify Issue Diary details: FAIL");
				takesnap("Verify Reg Diary details");
			}
		}

		if (expIssNar != null) {

			WebElement eleIssReReference = locateElement("id", ("_id_0RELATED_REFERENCE"));
			String IssActualref = getTypedText(eleIssReReference);
			String expIssRef = hash_map.get("issRef");
			if (expIssRef.equals(IssActualref)) {
				log("Verify Register Diary details : Pass");
			} else {
				log("Verify Register Diary details : Fail");
			}

		}
//		Thread.sleep(5000);
		driver.switchTo().window(mainWinID);
		/* System.out.println(driver.getTitle()); */
		switchToFrame("work");
	}

	@And("^Release the RegAmd transaction$")
	public void release_the_RegAmd_transaction() throws Throwable {
		driver.switchTo().defaultContent();
		driver.switchTo().frame("FunctionList");
		driver.findElementByName("IPLC Maintenance").click();
		String Refnumber = hash_map.get("referenceNo");
		release(Refnumber);
		cancel();
	}

	@Given("^click on Issue Amendment$")
	public void clickonIssueAmendment() throws Throwable {
		driver.switchTo().defaultContent();
		driver.switchTo().frame("FunctionList");
		WebElement eleIssAmd = locateElement("name", "G49082300296F05030702021");
		eleIssAmd.click();
		String refNum = hash_map.get("referenceNo");
		catalog(refNum);

	}

	@Given("^Click On PartiesII tab$")
	public void click_On_PartiesII_tab() throws Throwable {
		Thread.sleep(2000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		Thread.sleep(2000);
		MT707_DATA_MAP.put("31C_1", getTypedText(locateElement("name", "ISSUE_DT")).replace("-", "").substring(2));
		System.out.println("issue " + MT707_DATA_MAP.get("31C_1"));
		ISSSWI_DATA_MAP.put("NoOfAmdmnt", getTypedText(locateElement("name", "NO_OF_AMD")));
		MT707_DATA_MAP.put("31D_2", getTypedText(locateElement("name", "NEW_EXPIRY_PLC")));
		MT707_DATA_MAP.put("31D_1",
				getTypedText(locateElement("name", "NEW_EXPIRY_DT")).replaceAll("-", "").substring(2));
		MT707_DATA_MAP.put("22A", getTypedText(locateElement("name", "PURP_OF_MESS")));
		String DOA = getTypedText(locateElement("id", "AMD_DT"));
		MT707_DATA_MAP.put("30", DOA.replaceAll("-", "").substring(2));
		System.out.println("date Amd" + MT707_DATA_MAP.get("30"));
		MT707_DATA_MAP.put("33B_1", getTypedText(locateElement("name", "LOCAL_CCY")));
		MT707_DATA_MAP.put("33B_2",
				getTypedText(locateElement("name", "DEC_AMT")).replaceAll(",", "").replace(".0", ",0"));

		System.out.println(MT707_DATA_MAP.get("33B_2").replace(".0", ",0"));

		WebElement eleParties1 = locateElement("id", "C");
		click(eleParties1);
		MT707_DATA_MAP.put("59", getTypedText(locateElement("name", "NEW_BENE_NM")));
		MT707_DATA_MAP.put("59_1", getTypedText(locateElement("name", "NEW_BENE_ADD1")));
		MT707_DATA_MAP.put("59_2", getTypedText(locateElement("name", "NEW_BENE_ADD2")));
		MT707_DATA_MAP.put("59_3", getTypedText(locateElement("name", "NEW_BENE_ADD3")));
//		Thread.sleep(3000);
		WebElement eleParties2 = locateElement("id", "C");
		click(eleParties2);
	}

	@And("^Enter Issuing Bank ID$")
	public void enter_Issuing_Bank_ID() throws Throwable {
		WebElement eleIssBankID = locateElement("name", "ISSUE_BK_ID");
		String issBankId = data.getCellValue("Scenario 1", 2, 213);
		append(eleIssBankID, issBankId);
		eleIssBankID.sendKeys(Keys.TAB);
	}

	@And("^Enter the Issuing Bank's Reference$")
	public void enter_the_Issuing_Bank_s_Reference() throws Throwable {
		WebElement eleIssBankRef = locateElement("name", "ISSUE_BK_REF");
		String issBankId = data.getCellValue("Scenario 1", 2, 214);
		append(eleIssBankRef, issBankId);
		String issTag = "52".concat(getTypedText(locateElement("name", "ISSUE_BK_SW_TAG")));
		MT707_DATA_MAP.put("52A", issTag);
		System.out.println("Issue Tahg: " + MT707_DATA_MAP.get("52A"));
		String ref = getTypedText(eleIssBankRef).toUpperCase();
		String issRef = getTypedText(locateElement("name", "ISSUE_BK_SW_ADD"));
		MT707_DATA_MAP.put("52C", issRef);
		MT707_DATA_MAP.put("23", ref);
		MT707_DATA_MAP.put("21", getTypedText(locateElement("name", "ADV_BK_REF")));
		MT707_DATA_MAP.put("49", getTypedText(locateElement("name", "NEW_CONF_INSTR")));

	}

	@And("^Enter New Confirmation Instruction$")
	public void enter_New_Confirmation_Instruction() throws Throwable {
		String newConInst = data.getCellValue("Scenario 1", 2, 215);
		if (!newConInst.isEmpty()) {
			WebElement eleIssBankRef = locateElement("name", "NEW_CONF_INSTR");
			selectDropDownUsingText(eleIssBankRef, newConInst);
		}
	}

	@And("^Select New Applicable Rules$")
	public void select_Applicable_Rules1() throws Throwable {
		String appRules = data.getCellValue("Scenario 1", 2, 223);
		if (!appRules.isEmpty()) {
			WebElement eleIssBankRef = locateElement("name", "NEW_APLB_RULE_40F");
			selectDropDownUsingText(eleIssBankRef, appRules);
		}
	}

	@And("^Select the Reimburement Charges$")
	public void select_Reimburement_Charges() throws Throwable {
		String reimCharge = data.getCellValue("Scenario 1", 2, 224);
		if (!reimCharge.isEmpty()) {
			WebElement eleReimCharge = locateElement("name", "NEW_REIM_BK_CHG_DESC");
			selectDropDownUsingText(eleReimCharge, reimCharge);

		}
	}

	@And("^Enter the Reimbursing Bank's Reference$")
	public void entertheReimbursingBankReference() throws Throwable {
		String reimRef = data.getCellValue("Scenario 1", 2, 225);
		MT707_DATA_MAP.put("Reimswift", getTypedText(locateElement("name", "NEW_REIM_BK_SW_ADD")));
		MT707_DATA_MAP.put("53A_T", "53" + getTypedText(locateElement("name", "NEW_REIM_BK_SW_TAG")));
		if (!reimRef.isEmpty()) {
			WebElement eleReimRef = locateElement("name", "NEW_REIM_BK_REF");
			eleReimRef.clear();
			append(eleReimRef, reimRef);
		}
	}

	@And("^Enter IssAmend Charges$")
	public void enterIssAmendCharges() throws Throwable {
		String charges = data.getCellValue("Scenario 1", 2, 226);
		if (!charges.isEmpty()) {
			WebElement eleIssAmdCharges = locateElement("name", "NEW_REIM_BK_OTH_CHG_NARR");
			eleIssAmdCharges.clear();
			lengthValidation2(eleIssAmdCharges, "6*35", charges, "ISSAMD_CHARGES");
		}
	}

	@And("^Enter the Sender to Receiver Information$")
	public void entertheSendertoReceiverInformation() throws Throwable {
		String senToRec = data.getCellValue("Scenario 1", 2, 227);
		if (!senToRec.isEmpty()) {
			WebElement elesenToRec = locateElement("name", "NEW_BK_TO_BK_INFO");
			elesenToRec.clear();
			lengthValidation2(elesenToRec, "6*35", senToRec, "IssAmd_Sender to Receiver Information");
		}
	}

	@And("^Enter (\\d+) Narrative$")
	public void enter_Narrative(int arg1) throws Throwable {
		String Amdnarrtive = data.getCellValue("Scenario 1", 2, 228);
		if (!Amdnarrtive.isEmpty()) {
			WebElement eleNarrative = locateElement("name", "NEW_REIM_NARR_TAG_77");
			eleNarrative.clear();
			lengthValidation2(eleNarrative, "20*35", Amdnarrtive, "IssAmd_Narrative");
		}
	}

	@And("^Enter New Requested Confirmation Party$")
	public void enter_New_Requested_Confirmation_Party() throws Throwable {
		String newReqConParty = data.getCellValue("Scenario 1", 2, 232);
		if (!newReqConParty.isEmpty()) {
			WebElement eleNewReqConParty = locateElement("name", "NEW_CONF_BK_ID");
			append(eleNewReqConParty, newReqConParty);
			eleNewReqConParty.sendKeys(Keys.TAB);
		}
	}

	@And("^Enter New 'Advise Through' Bank$")
	public void enter_New_Advise_Through_Bank() throws Throwable {
		String advThrhBank = data.getCellValue("Scenario 1", 2, 231);
		if (!advThrhBank.isEmpty()) {
			WebElement eleAdvThrhBank = locateElement("name", "NEW_ADV_THU_BK_ID");
			append(eleAdvThrhBank, advThrhBank);
			eleAdvThrhBank.sendKeys(Keys.TAB);
		}
	}

	@And("^Enter Non-Bank Issuer ID$")
	public void enter_Non_Bank_Issuer_ID() throws Throwable {
		String nonBankIss = data.getCellValue("Scenario 1", 2, 230);
		if (!nonBankIss.isEmpty()) {
			WebElement eleNonBankIss = locateElement("name", "NON_BANK_ISSUER_ID");
			append(eleNonBankIss, nonBankIss);
			eleNonBankIss.sendKeys(Keys.TAB);
		}

	}

	@Given("^Click on the Tenor tab$")
	public void ClickontheTenortab() throws Throwable {
		WebElement eleTenor = locateElement("id", "O");
		click(eleTenor);

	}

	@And("^Amend Available By$")
	public void amend_Available_By() throws Throwable {
		String NewAvaBy = data.getCellValue("Scenario 1", 3, 233);
		if (!NewAvaBy.isEmpty()) {
			WebElement eleNewAvaBy = locateElement("name", "NEW_AVAL_BY");
			selectDropDownUsingText(eleNewAvaBy, NewAvaBy);
			MT707_DATA_MAP.put("N_41A_2", "NEW_AVAL_WT_BK_SW_ADD");

		}
	}

	@And("^Enter Tenor Days$")
	public void enter_Tenor_Days() throws Throwable {
		String AmdTenorDays = data.getCellValue("Scenario 1", 3, 234);
		if (!AmdTenorDays.isEmpty()) {
			WebElement eleAmdTenorDays = locateElement("name", "NEW_TENOR_DAYS");
			append(eleAmdTenorDays, AmdTenorDays);

		}
	}

	@And("^Enter Tenor Type$")
	public void enter_Tenor_Type() throws Throwable {
		String AmdTenorType = data.getCellValue("Scenario 1", 3, 235);
		if (!AmdTenorType.isEmpty()) {
			WebElement eleAmdTenorType = locateElement("name", "NEW_TENOR_TYPE");
			selectDropDownUsingText(eleAmdTenorType, AmdTenorType);
		}
	}

	@Then("^Verify Amend Available By Onchanges$")
	public void verify_Amend_Available_By_Onchanges() throws Throwable {

		String draftAt = getTypedText(locateElement("name", "NEW_DRAFTS_AT"));
		String ttColour = getBackgroundColor(locateElement("name", "NEW_TENOR_TYPE"));
		String tdColour = getBackgroundColor(locateElement("name", "NEW_TENOR_DAYS"));
		String defColour = getBackgroundColor(locateElement("id", "NEW_DEF_PMT_DET"));
		String mixColour = getBackgroundColor(locateElement("id", "NEW_MIX_PMT_DETL"));
		String draftColour = getBackgroundColor(locateElement("id", "NEW_DRAFTS_AT"));
		String tenorType = getTypedText(locateElement("id", "NEW_TENOR_TYPE"));
		String tenorDays = getTypedText(locateElement("id", "NEW_TENOR_DAYS"));

		switch (draftAt.toUpperCase()) {
		case "BY ACCEPTANCE":
			if (ttColour.contains(Mandatory) && tdColour.contains(Optional) && defColour.contains(Protected)
					&& mixColour.contains(Protected) && draftColour.contains(Mandatory)) {
				log("IssAmd_Available by_Onchanges(By Acceptance): PASS");
			} else {
				log("IssAmd_Available by_Onchanges(By Acceptance): FAIL");
				takesnap("IssAmd_Available by_Onchanges(By Acceptance)");
			}
			break;
		}
		if (draftAt.toUpperCase().equalsIgnoreCase("BY ACCEPTANCE")) {
			String concat = tenorDays + " " + tenorType;
			if (concat.trim().toLowerCase().equalsIgnoreCase(draftAt.trim().toLowerCase())) {
				log("IssAmd_Draft At_Field Value_Check: PASS");
			} else {
				log("IssAmd_Draft At_Field Value_Check: FAIL");
				takesnap("IssAmd_Draft At_Field Value_Check");
			}
		}
	}

	@And("^Enter the Amend Available With Bank$")
	public void enter_the_Amend_Available_With_Bank() throws Throwable {
		String amdAvaWithBanl = data.getCellValue("Scenario 1", 3, 238);
		if (!amdAvaWithBanl.isEmpty()) {
			WebElement eleAmdAvaWithBk = locateElement("name", "NEW_AVAL_WT_BK_OPT");
			selectDropDownUsingText(eleAmdAvaWithBk, amdAvaWithBanl);
			eleAmdAvaWithBk.sendKeys(Keys.TAB);
			MT707_DATA_MAP.put("N_41A_1", getTypedText(locateElement("name", "NEW_AVAL_WT_BK_SW_ADD")));
		}
	}

	@And("^Enter the Amend Drawee$")
	public void enterTheAmendDrawee() throws Throwable {
		String amdDrawee = data.getCellValue("Scenario 1", 3, 237);
		if (!amdDrawee.isEmpty()) {
			WebElement eleAmdDrawee = locateElement("name", "NEW_DRWE_ID");
			append(eleAmdDrawee, amdDrawee);
			eleAmdDrawee.sendKeys(Keys.TAB);

		}
	}

	@Given("^Click On Shipment tab$")
	public void click_On_Shipment_tab() throws Throwable {
		WebElement eleShipment = locateElement("id", "D");
		eleShipment.click();
	}

	@And("^Select Amend Partial Shipment$")
	public void select_Amend_Partial_Shipment() throws Throwable {
		String partialShipment = data.getCellValue("Scenario 1", 3, 240);
		if (!partialShipment.isEmpty()) {
			WebElement eleAmdTenorDays = locateElement("name", "NEW_PARTIAL_SHIP");
			selectDropDownUsingText(eleAmdTenorDays, partialShipment);
			MT707_DATA_MAP.put("43P", getTypedText(eleAmdTenorDays));
		}
	}

	@And("^Select Amend Transhipment$")
	public void select_Amend_Transhipment() throws Throwable {
		String amdTrspmt = data.getCellValue("Scenario 1", 3, 241);
		if (!amdTrspmt.isEmpty()) {
			WebElement eleAmdTrspmt = locateElement("name", "NEW_TNSHIP");
			selectDropDownUsingText(eleAmdTrspmt, amdTrspmt);
			MT707_DATA_MAP.put("43T", getTypedText(eleAmdTrspmt));
		}
	}

	@And("^Enter Amend Place of Taking in Charge$")
	public void enter_Amend_Place_of_Taking_in_Charge_Dispatch_from_Place_of_Receipt() throws Throwable {
		String amdPlTakeDis = data.getCellValue("Scenario 1", 3, 244);
		if (!amdPlTakeDis.isEmpty()) {
			WebElement eleAmdPlTakeDis = locateElement("name", "NEW_LOAD_PLACE");
			append(eleAmdPlTakeDis, amdPlTakeDis);
			MT707_DATA_MAP.put("44A", getTypedText(eleAmdPlTakeDis));
		}
	}

	@And("^Enter Amend Place of Final Destination$")
	public void enter_Amend_Place_of_Final_Destination_For_Transportation_to_Place_of_Delivery() throws Throwable {
		String amdPlFinDes = data.getCellValue("Scenario 1", 3, 246);
		if (!amdPlFinDes.isEmpty()) {
			WebElement eleAmdFinalDes = locateElement("name", "NEW_DEST_PLACE");
			append(eleAmdFinalDes, amdPlFinDes);
			MT707_DATA_MAP.put("44B", getTypedText(eleAmdFinalDes));
		}
	}

	@And("^Enter Amend Port of Loading$")
	public void enter_Amend_Port_of_Loading_Airport_of_Departure() throws Throwable {
		String amdLoading = data.getCellValue("Scenario 1", 3, 245);
		if (!amdLoading.isEmpty()) {
			WebElement eleAmdPortLoading = locateElement("name", "NEW_LOAD_PORT");
			append(eleAmdPortLoading, amdLoading);
			MT707_DATA_MAP.put("44E", getTypedText(eleAmdPortLoading));
		}
	}

	@And("^Enter Amend Port of Discharge$")
	public void enter_Amend_Port_of_Discharge_Airport_of_Destination() throws Throwable {
		String amdPortOfDis = data.getCellValue("Scenario 1", 3, 247);
		if (!amdPortOfDis.isEmpty()) {
			WebElement eleAmdPortOfDis = locateElement("name", "NEW_DEST_PORT");
			append(eleAmdPortOfDis, amdPortOfDis);
			MT707_DATA_MAP.put("44F", getTypedText(eleAmdPortOfDis));
		}

	}

	@And("^Enter AmendLatest Shipment Date$")
	public void enter_AmendLatest_Shipment_Date() throws Throwable {
		String amdLatShpDate = data.getCellValue("Scenario 1", 3, 243);
		if (!amdLatShpDate.isEmpty()) {
			WebElement eleAmdLatShpDate = locateElement("name", "NEW_LTST_SHIP_DT");
			append(eleAmdLatShpDate, amdLatShpDate);
			MT707_DATA_MAP.put("44C", getTypedText(eleAmdLatShpDate).replaceAll("-", "").substring(2));
		}

	}

	@And("^Enter AmendIncoterms$")
	public void enter_AmendIncoterms() throws Throwable {
		String amdIncoterm = data.getCellValue("Scenario 1", 3, 242);
		if (!amdIncoterm.isEmpty()) {
			WebElement eleAmdIncoterm = locateElement("name", "NEW_INCOTERMS");
			selectDropDownUsingText(eleAmdIncoterm, amdIncoterm);
		}

	}

	@And("^Enter AmendShipment Period$")
	public void enter_AmendShipment_Period() throws Throwable {
		String periodColour = getBackgroundColor(locateElement("name", "NEW_SHIP_PRD"));
		if (!periodColour.contains(Protected)) {
			String amdShpmtPeriod = data.getCellValue("Scenario 1", 3, 248);
			WebElement eleAmdShpmtPeriod = locateElement("name", "NEW_SHIP_PRD");
			lengthValidation2(eleAmdShpmtPeriod, "6*65", amdShpmtPeriod, "IssAmd_Amend Shipment Period");
		}

	}

	@Given("^Click On Goods tab$")
	public void click_On_Goods_tab() throws Throwable {
		WebElement eleGoods = locateElement("id", "E");
		click(eleGoods);
	}

	@And("^Enter New Description of Goods and/or Services$")
	public void enter_New_Description_of_Goods_and_or_Services() throws Throwable {
		String newDesGoods = data.getCellValue("Scenario 1", 2, 249);
		if (!newDesGoods.isEmpty()) {
			WebElement eleNewDesGoods = locateElement("name", "AMD_DESC_OF_GOODS");
			lengthValidation2(eleNewDesGoods, "100*65", newDesGoods, "IssAmd_New Description of Goods and/or Services");
			MT707_DATA_MAP.put("45B", getTypedText(eleNewDesGoods));
		}

	}

	@Given("^Click On Documents tab$")
	public void click_On_Documents_tab() throws Throwable {
		WebElement eleDocuments = locateElement("id", "F");
		click(eleDocuments);

	}

	@And("^Enter New Documents Required$")
	public void enter_New_Documents_Required() throws Throwable {
		String newDocReq = data.getCellValue("Scenario 1", 2, 250);
		if (!newDocReq.isEmpty()) {
			WebElement eleNewDocReq = locateElement("name", "AMD_DOC_REQ");
			lengthValidation2(eleNewDocReq, "100*65", newDocReq, "IssAmd_New Documents Required");
			MT707_DATA_MAP.put("46B", getTypedText(eleNewDocReq));
		}

	}

	@And("^Click On Additional Condition$")
	public void click_On_Additional_Condition() throws Throwable {
		WebElement eleAddCon = locateElement("id", "G");
		click(eleAddCon);

	}

	@And("^Enter Presentation Days$")
	public void enter_Presentation_Days() throws Throwable {
		String PreDays = data.getCellValue("Scenario 1", 3, 251);
		if (!PreDays.isEmpty()) {
			WebElement elePreDays = locateElement("name", "AMD_PRES_DAYS");
			append(elePreDays, PreDays);
		}

	}

	@And("^Select Presentation Type$")
	public void select_Presentation_Type() throws Throwable {
		String preType = data.getCellValue("Scenario 1", 3, 252);
		if (!preType.isEmpty()) {
			WebElement elePreType = locateElement("name", "AMD_PRES_TYPE");
			selectDropDownUsingText(elePreType, preType);

		}
	}

	/*
	 * @Then("^Check Period for Presentation in Days$") public void
	 * check_Period_for_Presentation_in_Days() throws Throwable { String preInDays =
	 * data.getCellValue("Scenario 1", 3, 2); if (!preInDays.isEmpty()) { WebElement
	 * elePreType = locateElement("name", "AMD_PRES_TYPE"); String preType =
	 * getTypedText(elePreType); WebElement elePreDays = locateElement("name",
	 * "AMD_PRES_DAYS"); String preDays = getTypedText(elePreDays); WebElement
	 * elePreInDays = locateElement("name", "AMD_PRES_PRD_TXT"); String actaulPrDays
	 * = getTypedText(elePreInDays); String concat = preDays + " " + preType; if
	 * (concat.trim().toLowerCase().equalsIgnoreCase(actaulPrDays.trim().toLowerCase
	 * ())) { log("Period for Presentation in Days value check: Pass"); } else {
	 * log("Period for Presentation in Days value Check: FAIL");
	 * takesnap("Period for Presentation in Days value check"); }
	 * 
	 * } }
	 */

	@And("^Enter New Sender to Receiver Information$")
	public void enterNewSendertoReceiverInformation() throws Throwable {
		String bkToBk = data.getCellValue("Scenario 1", 3, 254);
		if (!bkToBk.isEmpty()) {
			WebElement eleBkToBk = locateElement("name", "AMD_SEND_TO_RCV_INFO");
			lengthValidation2(eleBkToBk, "6*35", bkToBk, "IssAmd_ADD_New Sender to Receiver Information");
			MT707_DATA_MAP.put("72Z", getTypedText(eleBkToBk));
		}

	}

	@And("^Enter New Charges$")
	public void enterNewCharges() throws Throwable {
		String newCharges = data.getCellValue("Scenario 1", 3, 255);
		if (!newCharges.isEmpty()) {
			WebElement eleNewCharges = locateElement("name", "AMD_CHARGES");
			lengthValidation2(eleNewCharges, "6*35", newCharges, "IssAmd_New Charges");

		}

	}

	@And("^Select Amendment Charge Payable By$")
	public void select_Amendment_Charge_Payable_By() throws Throwable {
		String newChargePayble = data.getCellValue("Scenario 1", 3, 256);
		if (!newChargePayble.isEmpty()) {
			WebElement eleNewChargePayble = locateElement("name", "AMD_CHG_PAY_BY");
			selectDropDownUsingText(eleNewChargePayble, newChargePayble);
			MT707_DATA_MAP.put("71N", getTypedText(eleNewChargePayble));

		}

	}

	@And("^Amendment Charge Payable By Onchange$")
	public void amendment_Charge_Payable_By_Onchange() throws Throwable {
		WebElement eleNewCharges = locateElement("name", "AMD_CHG_PAY_BY");
		String chargePayable = getTypedText(eleNewCharges);
		WebElement eleAmendChargePaybleNarr = locateElement("name", "AMD_CHG_PAY_BY_NARR");
		if (chargePayable.equalsIgnoreCase("OTHER")) {
			verifyNarrativeEnable(eleAmendChargePaybleNarr, "OTHER", "IssAmd_Amendment Charge Payable By_Onchange");
		} else {
			verifyNarrativeDisable(eleAmendChargePaybleNarr, "OTHER", "IssAmd_Amendment Charge Payable By_Onchange");
		}

	}

	@And("^Enter Instructions to Paying Bank$")
	public void enter_Instructions_to_Paying_Bank() throws Throwable {
		String insToPayBank = data.getCellValue("Scenario 1", 3, 257);
		if (!insToPayBank.isEmpty()) {
			WebElement eleInsToPayBank = locateElement("name", "AMD_INSTR_TO_PAY_BK");
			lengthValidation2(eleInsToPayBank, "12*65", insToPayBank, "IssAmd_Instructions to Paying Bank");
			MT707_DATA_MAP.put("78", getTypedText(eleInsToPayBank));
		}

	}

	@And("^Enter Additional Conditions$")
	public void enter_Additional_Conditions() throws Throwable {
		String addCon = data.getCellValue("Scenario 1", 3, 259);
		if (!addCon.isEmpty()) {
			WebElement eleAddCon = locateElement("name", "AMD_ADDIT_CONDITION");
			lengthValidation2(eleAddCon, "100*65", addCon, "IssAmd_Additional Conditions");
			MT707_DATA_MAP.put("47B", getTypedText(eleAddCon));
		}

	}

	@And("^Enter Special Payment Conditions for Beneficiary$")
	public void enter_Special_Payment_Conditions_for_Beneficiary() throws Throwable {
		String splPmtConBen = data.getCellValue("Scenario 1", 3, 258);
		if (!splPmtConBen.isEmpty()) {
			WebElement eleInsToPayBene = locateElement("name", "AMD_PAY_COND_TO_BENE");
			lengthValidation2(eleInsToPayBene, "100*65", splPmtConBen,
					"IssAmd_Special Payment Conditions for Beneficiary");
			MT707_DATA_MAP.put("49M", getTypedText(eleInsToPayBene));

		}

	}

	@And("^Enter Special Payment Conditions for Bank Only$")
	public void enter_Special_Payment_Conditions_for_Bank_Only() throws Throwable {
		String splPmtConBank = data.getCellValue("Scenario 1", 3, 260);
		if (!splPmtConBank.isEmpty()) {
			WebElement eleSplPmtConBank = locateElement("name", "AMD_PAY_COND_TO_REV_BK");
			lengthValidation2(eleSplPmtConBank, "100*65", splPmtConBank,
					"IssAmd_Special Payment Conditions for Bank Only");
			MT707_DATA_MAP.put("49N", getTypedText(eleSplPmtConBank));
		}

	}

	@Given("^Click On Charges tab$")
	public void click_On_Charges_tab() throws Throwable {
		WebElement eleCharges = locateElement("id", "I");
		click(eleCharges);
	}

	@And("^Enter Paid At$")
	public void enter_Paid_At() throws Throwable {
		String paidAt = data.getCellValue("Scenario 1", 2, 262).toUpperCase();
		if (!paidAt.isEmpty()) {
			WebElement elePaidAt = locateElement("name", "CHG_FLD_ALL_CHARGE_AT");
			selectDropDownUsingText(elePaidAt, paidAt);
		}
	}

	@And("^Enter Paid By$")
	public void enter_Paid_By() throws Throwable {
		String paidAt = data.getCellValue("Scenario 1", 2, 261);
		if (!paidAt.isEmpty()) {
			WebElement elePaidAt = locateElement("name", "CHG_FLD_ALL_CHARGE_FOR");
			selectDropDownUsingText(elePaidAt, paidAt);
		}
	}

	@And("^Enter AC/NO$")
	public void enter_AC_NO() throws Throwable {
		String AcNo = data.getCellValue("Scenario 1", 2, 263);
		if (!AcNo.isEmpty()) {
			WebElement eleAcNo = locateElement("name", "CHG_FLD_LOCAL_CUST_AC_NO");
			append(eleAcNo, AcNo);
		}
	}

	@Given("^Click on IssAmd Advice tab$")
	public void click_on_IssAmd_Advice_tab() throws Throwable {
		WebElement eleAdvise = locateElement("id", "H");
		click(eleAdvise);
	}

	@Given("^click on add button$")
	public void click_on_add_button() throws Throwable {
		WebElement eleAdd = locateElement("id", "ext-gen91");
		click(eleAdd);
	}

	@And("^Select the type of message$")
	public void select_the_type_of_message() throws Throwable {
		String TOM = data.getCellValue("Scenario 1", 3, 128).replaceAll(" ", "");
		if (!TOM.isEmpty()) {
			switchToFrame("frame.AdivceForBankCust");
			WebElement eleTOM = locateElement("id", "MESG_TYPE_BANK");
			selectDropDownUsingText(eleTOM, TOM);
			String typeOfMessage = getTypedText(eleTOM);
//			Thread.sleep(1000);
			String swiftTagColour = getBackgroundColor(locateElement("name", "SEND_TO_BK_SW_ADD"));
			String rReferenceColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_REF"));
			String languageColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_LANG"));
			String mailColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_POST_ADD"));
			String faxColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_FAX"));
			String emailColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_EMAIL"));
			String sNarrativeColour = getBackgroundColor(locateElement("name", "BANK_NARR_TAG_79"));
			String mNarrativeColour = getBackgroundColor(locateElement("name", "BANK_NARR_MAIL"));
			String nameColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_NM"));

			if (typeOfMessage.equalsIgnoreCase("MT199") || typeOfMessage.equalsIgnoreCase("MT299")
					|| typeOfMessage.equalsIgnoreCase("MT799") || typeOfMessage.equalsIgnoreCase("MT999")) {
				if (swiftTagColour.contains(Mandatory) && rReferenceColour.contains(Mandatory)
						&& languageColour.contains(Mandatory) && sNarrativeColour.contains(Mandatory)
						&& mailColour.contains(Optional) && faxColour.contains(Optional)
						&& emailColour.contains(Optional) && mNarrativeColour.contains(Protected)) {
					log("IssAmd_Type Of Message_Onchange(MT n99): PASS");
				} else {
					log("IssAmd_Type Of Message_Onchange(MT n99): FAIL");
					takesnap("IssAmd_Type Of Message_Onchange(MT n99)");
				}
			}
			if (typeOfMessage.equalsIgnoreCase("Mail")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Mandatory)
						&& faxColour.contains(Optional) && emailColour.contains(Optional)
						&& mNarrativeColour.contains(Mandatory)) {
					log("IssAmd_Type Of Message_Onchange(Mail): PASS");
				} else {
					log("IssAmd_Type Of Message_Onchange(Mail): FAIL");
					takesnap("IssAmd_Type Of Message Onchange(Mail)");
				}
			}

			if (typeOfMessage.equalsIgnoreCase("Email")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Optional)
						&& faxColour.contains(Optional) && emailColour.contains(Mandatory)
						&& mNarrativeColour.contains(Mandatory)) {
					log("IssAmd_Type Of Message_Onchange(email): Pass");
				} else {
					log("IssAmd_Type Of Message_Onchange(email): Fail");
					takesnap("IssAmd_Type Of Message_Onchange(email)");
				}
			}

			if (typeOfMessage.equalsIgnoreCase("Fax")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Optional)
						&& faxColour.contains(Mandatory) && emailColour.contains(Optional)
						&& mNarrativeColour.contains(Mandatory)) {
					log("IssAmd_Type Of Message_Onchange(Fax): Pass");
				} else {
					log("IssAmd_Type Of Message_Onchange(Fax): Fail");
					takesnap("IssAmd_Type Of Message_Onchange(Fax)");
				}
			}
		}
	}

	@And("^Enter the Bank ID$")
	public void enter_the_Bank_ID() throws Throwable {
		String bId = data.getCellValue("Scenario 1", 3, 129);
		if (!bId.isEmpty()) {
			WebElement eleBankId = locateElement("name", "SEND_TO_BANK_ID");
			append(eleBankId, bId);
			eleBankId.sendKeys(Keys.TAB);
			Thread.sleep(3000);
			MT707_DATA_MAP.put("AMD_ADV_RECE_CODE", getTypedText(locateElement("name", "SEND_TO_BK_SW_ADD")));
			MT707_DATA_MAP.put("AMD_ADV_RELD_REF", getTypedText(locateElement("name", "SEND_TO_BANK_REF")));
		}
	}

	@And("^Select the  Mail Method$")
	public void select_the_Mail_Method() throws Throwable {
		String mMethod = data.getCellValue("Scenario 1", 3, 133);
		if (!mMethod.isEmpty()) {
			WebElement eleMailMethod = locateElement("Name", "MAIL_MTHD_BANK");
			selectDropDownUsingText(eleMailMethod, mMethod);
		}
	}

	@And("^Enter the  Narrative \\(MT n(\\d+) Tag (\\d+)Z\\)$")
	public void enter_the_Narrative_MT_n_Tag_Z(int arg1, int arg2) throws Throwable {
		String narrative = data.getCellValue("Scenario 1", 3, 134);
		if (!narrative.isEmpty()) {
			WebElement eleNarrative = locateElement("name", "BANK_NARR_TAG_79");
			lengthValidation2(eleNarrative, "35*50", narrative, "IssAmd_Narrative(MT n99 Tag 79Z)");
			eleNarrative.sendKeys(Keys.TAB);
			MT707_DATA_MAP.put("AMD_ADV_NARR_REF", getTypedText(locateElement("name", "BANK_NARR_TAG_79")));

		}
	}

	@And("^Enter the Narrative \\(Mail\\)$")
	public void enter_the_Narrative_Mail() throws Throwable {
		String mailNarrative = data.getCellValue("Scenario 1", 3, 135);
		if (!mailNarrative.isEmpty()) {
			WebElement eleNarrative = locateElement("name", "BANK_NARR_MAIL");
			append(eleNarrative, mailNarrative);
		}
	}

	@Given("^Click on the customer subtab$")
	public void click_on_the_customer_subtab() throws Throwable {
		WebElement eleCustomer = locateElement("ID", "B");
		click(eleCustomer);
	}

	@And("^select  the type of message and verify onchanges$")
	public void select_the_type_of_message_and_verify_onchanges() throws Throwable {

		String custTom = data.getCellValue("Scenario 1", 3, 136);
		if (!custTom.isEmpty()) {
			WebElement eleTom = locateElement("id", "MESG_TYPE_CUST");
			selectDropDownUsingText(eleTom, custTom);
			String custTomText = getTypedText(eleTom);
			String nameColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_NM"));
			String languageColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_LANG"));
			String emailColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_EMAIL"));
			String faxColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_FAX"));
			String narrColour = getBackgroundColor(locateElement("id", "CUST_NARR_TAG_79"));
			String mailColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_POST_ADD"));
			if (custTomText.equalsIgnoreCase("Fax")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Optional) && faxColour.contains(Mandatory)
						&& narrColour.contains(Mandatory) && mailColour.contains(Optional)) {
					log("IssAmd_Customer_Type Of Message_Onchange(Fax): PASS");
				} else {
					log("IssAmd_Customer_Type Of Message_Onchange(Fax): FAIL");
					takesnap("IssAmd_Customer_Type Of Message_Onchange(Fax):");
				}
			}

			if (custTomText.equalsIgnoreCase("Mail")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Optional) && faxColour.contains(Optional)
						&& narrColour.contains(Mandatory) && mailColour.contains(Mandatory)) {
					log("IssAmd_Customer_Type Of Message_Onchange(Mail): PASS");
				} else {
					log("IssAmd_Customer_Type Of Message _Onchange(Mail): FAIL");
					takesnap("IssAmd_Customer_Type Of Message _Onchange(Mail)");
				}
			}

			if (custTomText.equalsIgnoreCase("Email")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Mandatory) && faxColour.contains(Optional)
						&& narrColour.contains(Mandatory) && mailColour.contains(Optional)) {
					log("IssAmd_Customer_Customer_Type Of Message Onchange(Email): PASS");
				} else {
					log("IssAmd_Customer_Customer_Type Of Message Onchange(Email): FAIL");
					takesnap("IssAmd_Customer_Customer_Type Of Message Onchange(Email)");
				}
			}
		}
	}

	@And("^select the Customer Mail Method$")
	public void select_the_Customer_Mail_Method() throws Throwable {
		String mMethod = data.getCellValue("Scenario 1", 3, 138);
		if (!mMethod.isEmpty()) {
			WebElement eleMailMethod = locateElement("Name", "MAIL_MTHD_CUST");
			selectDropDownUsingText(eleMailMethod, mMethod);
		}
	}

	@And("^Enter the Customer ID$")
	public void enter_the_Customer_ID() throws Throwable {
		String custId = data.getCellValue("Scenario 1", 3, 137);
		if (!custId.isEmpty()) {
			WebElement eleCustId = locateElement("name", "SEND_TO_CUST_ID");
			append(eleCustId, custId);
			eleCustId.sendKeys(Keys.TAB);
		}
	}

	@And("^Enter the narrative$")
	public void enter_the_narrative() throws Throwable {
		String custNarrative = data.getCellValue("Scenario 1", 3, 139);
		if (!custNarrative.isEmpty()) {
			WebElement eleCustId = locateElement("name", "CUST_NARR_TAG_79");
			append(eleCustId, custNarrative);
		}
	}

	@Given("^Click On IssAmd Notes tab$")
	public void click_On_IssAmd_Notes_tab() throws Exception {
		WebElement eleNotes = locateElement("id", "J");
		click(eleNotes);

	}

	@Then("^Verify Iss AMD notes$")
	public void verify_Iss_AMD_notes() throws Exception {
		WebElement elenotes = locateElement("id", ("NOTES"));
		String note = getTypedText(elenotes);
//		String nNotes = hash_map.get("Notes");
		if (note.equalsIgnoreCase(hash_map.get("Notes"))) {

			log("Verify Notes details: PASS");
		} else {
			log("Verify Notes details: FAIL");
			takesnap("Verify Notes details");
		}
	}

	@Given("^Click On IssAmd Diary tab$")
	public void click_On_IssAmd_Diary_tab() throws Exception {
		WebElement eleDiary = locateElement("id", "M");
		click(eleDiary);
	}

	@Given("^Enter the IssAmd Releated Reference$")
	public void enter_the_IssAmd_Releated_Reference() throws Throwable {
		String rReference = hash_map.get("referenceNo");

		WebElement eleReRef = locateElement("name", ("DIARY_RELATED_REF"));
		append(eleReRef, rReference);
		String typedText = getTypedText(eleReRef);
		hash_map.put("IssAmdReRef", typedText);
//		Thread.sleep(3000);
		if (getTypedText(eleReRef).length() <= 16) {
			log("Verify Related Reference Length: PASS");
		} else {
			log("Verify Related Reference Length: FAIL");
			takesnap("Verify Related Reference Length");
		}
		WebElement eleNarrative = locateElement("name", "DIARY_NARRATIVE");
		String narrative = data.getCellValue("Scenario 1", 2, 188);
		append(eleNarrative, narrative);
		String regAmdNarr = getTypedText(eleNarrative);
		hash_map.put("IssAmdNarrative", regAmdNarr);
		eleNarrative.sendKeys(Keys.TAB);
	}

	@Then("^Verify Diary History$")
	public void verify_Diary_History() throws Throwable {
		String Rnarrative = hash_map.get("RegExpectedNarrative");
		String expIssNar = hash_map.get("IssNarrative");
		String expRegAmdNar = hash_map.get("RegAmdNarrative");
		WebElement eleviewhistory = locateElement("id", ("view_1"));
		eleviewhistory.click();
		Set<String> windowId = driver.getWindowHandles();
		Iterator<String> itererator = windowId.iterator();
		String mainWinID = itererator.next();
		String newAdwinID = itererator.next();
		driver.switchTo().window(newAdwinID);
		/* System.out.println(driver.getTitle()); */
//		Thread.sleep(5000);
		if (Rnarrative != null) {
			WebElement eleReReference = locateElement("id", ("_id_2C_MAIN_REF"));
			String RegActualref = getTypedText(eleReReference);
			String nRef = hash_map.get("RegExpectedRef");
			/* System.out.println("test" + nRef); */
			WebElement elenNarrative = locateElement("id", ("DIARY_NARRATIVE"));
			String RegActualNarrative = getTypedText(elenNarrative);
			/* System.out.println("test" + Rnarrative); */
			if (RegActualref.equalsIgnoreCase(nRef) || RegActualNarrative.equalsIgnoreCase(Rnarrative)) {
				log("Verify Register Diary details: PASS");
			} else {
				log("Verify Register Diary details: FAIL");
				takesnap("Verify Reg Diary details");
			}
		}

		if (expIssNar != null) {
//			Thread.sleep(2000);
			WebElement eleIssReReference = locateElement("id", ("_id_1C_MAIN_REF"));
			String IssActualref = getTypedText(eleIssReReference);
			String expIssRef = hash_map.get("issRef");
			if (expIssRef.equals(IssActualref)) {
				log("Verify Issue  Diary details : Pass");
			} else {
				log("Verify Issue Diary details : Fail");
			}
		}

		if (expRegAmdNar != null) {

			WebElement eleIssReReference = locateElement("id", ("_id_0C_MAIN_REF"));
			String RegAmdActualref = getTypedText(eleIssReReference);
			String expRegAmdRef = hash_map.get("RegAmdReRef");
			if (expRegAmdRef.equals(RegAmdActualref)) {
				log("Verify Register Amendment Diary details : Pass");
			} else {
				log("Verify Register Amendment Diary details : Fail");
			}
		}

//		Thread.sleep(5000);
		driver.switchTo().window(mainWinID);
		/* System.out.println(driver.getTitle()); */
		switchToFrame("work");
	}

	@Then("^Issue Amd - Output - Swift Check$")
	public void verify_amd_swift_is_generate() throws Throwable {
		Thread.sleep(2000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		Thread.sleep(2000);
		if (locateElement("name", "Swift").isDisplayed()) {
			log("IssAmd_Output - Swift: PASS");
		} else {
			log("IssAmd_Output - Swift: FAIL");
		}
	}

	@Then("^Validate the MT(\\d+)_Content$")
	public void validate_the_MT__Content(int arg1) throws Throwable {
		WebElement swift = locateElement("name", "Swift");
		if (swift.isDisplayed()) {
			click(swift);
			Thread.sleep(6000);
			switchToWindow(1);
			driver.manage().window().maximize();
			WebElement eleview = locateElement("xpath",
					"/html/body/form/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/div/table/tbody/tr[4]/td/table/tbody/tr[2]/td[3]/a");
			click(eleview);
			Thread.sleep(6000);
			switchToWindow(2);
			driver.manage().window().maximize();
			WebElement table = locateElement("xpath",
					"/html/body/table/tbody/tr[5]/td/table/tbody/tr[1]/td[2]/div/table[1]/tbody/tr[4]/td/table");
			List<WebElement> column = table.findElements(By.tagName("td"));
			List<String> mt707 = new ArrayList<String>();
			for (int j = 0; j < column.size(); j++) {
				mt707.add(column.get(j).getText());
				System.out.println(column.get(j).getText());
			}
// Outgoing SWIFT Header 1
			int headerIndex = mt707.indexOf("Outgoing SWIFT Header 1");
			// TagCheck
			String headerTag = mt707.get(headerIndex - 1);
			swiftTag(headerTag, "B1:", "MT705_Outgoing SWIFT Header 1");

// Receiver's BIC Code
			int recBicIndex = mt707.indexOf("Receiver's BIC Code");
			// TagCheck
			String recBicTag = mt707.get(recBicIndex - 1);
			swiftTag(recBicTag, "B2:", "MT705_Receiver's BIC Code");
			// ContentCheck
//		String recBicCon = mt707.get(recBicIndex + 2);
//		String swiftBic = ISSSWI_DATA_MAP.get("B2");
//		Thread.sleep(300);
//		while (swiftBic.length() < 12) {
//			Thread.sleep(300);
//			swiftBic = swiftBic.concat("X");
//		}
//		String ExpRecBicCon = "I707" + swiftBic + "N2";
//		swiftContent(ExpRecBicCon, recBicCon, "MT705_Receiver's BIC Code");

// 	Sequence of Total	
			if (mt707.contains("Sequence of Total")) {
				log("MT707_Presence of Sequence of Total Check: PASS");
				int seqTotalIndex = mt707.indexOf("Sequence of Total");
				// Tag Check
				String seqTag = mt707.get(seqTotalIndex - 1);
				swiftTag(seqTag, "27:", "MT707_Sequence of Total");
				// Content Check
				String seqContent = mt707.get(seqTotalIndex + 2);
				String expSeqTol = "1/1";
				swiftContent(expSeqTol, seqContent, "MT707_Sequence of Total");
			}
//Sender's Reference		
			int sendersRef = mt707.indexOf("Sender's Reference");
			// Tag Check
			String senTag = mt707.get(sendersRef - 1);
			swiftTag(senTag, "20:", "MT707_Sender's Reference");
			// Content Check
			String sendersCont = mt707.get(sendersRef + 2);
			String expSenderRef = hash_map.get("referenceNo");
			System.out.println("Act - " + sendersCont + " | Exp - " + expSenderRef);
			swiftContent(expSenderRef, sendersCont, "MT707_Sender's Reference");

//Receiver's Reference		
			int recRefInt = mt707.indexOf("Receiver's Reference");
			// Tag Check
			String recRefTag = mt707.get(recRefInt - 1);
			swiftTag(recRefTag, "21:", "MT707_Receiver's Reference");
			// Content Check
			String recRefCont = mt707.get(recRefInt + 2);
			String expRecRefCont = MT707_DATA_MAP.get("21");
			System.out.println("Act - " + recRefCont + " | Exp - " + expRecRefCont);
			swiftContent(expRecRefCont, recRefCont, "MT707_Receiver's Reference");

//Issuing Bank's Reference
			if (!MT707_DATA_MAP.get("23").isEmpty()) {
				if (mt707.contains("Issuing Bank's Reference")) {
					log("MT707_Presence of Issuing Bank's Reference Check: PASS");
					int issBankRef = mt707.indexOf("Issuing Bank's Reference");
					// Tag Check
					String issBankRefTag = mt707.get(issBankRef - 1);
					swiftTag(issBankRefTag, "23:", "MT707_Issuing Bank's Reference");
					// Content Check
					String issBankRefCont = mt707.get(issBankRef + 2);
					String expIssBankRefCont = MT707_DATA_MAP.get("23");
					System.out.println("Act - " + issBankRefCont + " | Exp - " + expIssBankRefCont);
					swiftContent(expIssBankRefCont, issBankRefCont, "MT707_Issuing Bank's Reference");
				} else {
					log("MT707_Presence of Issuing Bank's Reference Check: FAIL");
				}
			}
//Issuing Bank
			if (!MT707_DATA_MAP.get("52C").isEmpty()) {
				if (mt707.contains("Issuing Bank")) {
					log("MT707_Presence of Issuing Bank Check: PASS");
					int issBank = mt707.indexOf("Issuing Bank");
					// Tag Check
					String issBankTag = mt707.get(issBank - 1);
					System.out.println("Act - " + issBankTag + " | Exp - " + MT707_DATA_MAP.get("52A").concat(":"));
					swiftTag(issBankTag, MT707_DATA_MAP.get("52A").concat(":"), "MT707_Issuing Bank");
					// Content Check
					String issBankCont = mt707.get(issBank + 2);
					String expIssBankCont = MT707_DATA_MAP.get("52C");
					System.out.println("Act - " + issBankCont + " | Exp - " + expIssBankCont);
					swiftContent(expIssBankCont, issBankCont, "MT707_Issuing Bank");
				} else {
					log("MT707_Presence of Issuing Bank Check: FAIL");
				}
			}
//Date of Issue	
			if (!MT707_DATA_MAP.get("31C_1").isEmpty()) {
				if (mt707.contains("Date of Issue")) {
					log("MT707_Presence of Date of Issue Check: PASS");
					int dateOfIss = mt707.indexOf("Date of Issue");
					// Tag Check
					String dateOfIssTag = mt707.get(dateOfIss - 1);
					System.out.println("Act - " + dateOfIssTag + " | Exp - " + MT707_DATA_MAP.get("31C"));
					swiftTag(dateOfIssTag, "31C:", "MT707_Date of Issue");
					// Content Check
					String dateOfIssCont = mt707.get(dateOfIss + 2);
					String expDateOfIssCont = MT707_DATA_MAP.get("31C_1");
					System.out.println("Act - " + dateOfIssCont + " | Exp - " + expDateOfIssCont);
					swiftContent(expDateOfIssCont, dateOfIssCont, "MT707_Date of Issue");
				} else {
					log("MT707_Presence of Date of Issue Check: FAIL");
				}
			}

//Number of Amendment
			if (!ISSSWI_DATA_MAP.get("NoOfAmdmnt").isEmpty()) {
				if (mt707.contains("Number of Amendment")) {
					log("MT707_Presence of Number of Amendment Check: PASS");
					int noOfAmd = mt707.indexOf("Number of Amendment");
					// Tag Check
					String noOfAmdTag = mt707.get(noOfAmd - 1);
					swiftTag(noOfAmdTag, "NoOfAmdmnt:", "MT707_Number of Amendment");
					// Content Check
					String noOfAmdCont = mt707.get(noOfAmd + 2);
					String expnoOfAmdCont1 = ISSSWI_DATA_MAP.get("NoOfAmdmnt");
					if (expnoOfAmdCont1.length() == 1) {
						expnoOfAmdCont1 = "0".concat(expnoOfAmdCont1);
					}
					System.out.println("Act - " + noOfAmdCont + " | Exp - " + expnoOfAmdCont1);
					swiftContent(expnoOfAmdCont1, noOfAmdCont, "MT707_Number of Amendment");
				} else {
					log("MT707_Presence of Number of Amendment Check: FAIL");
				}
			}

//Date of Amendment
			if (!MT707_DATA_MAP.get("30").isEmpty()) {
				if (mt707.contains("Date of Amendment")) {
					log("MT707_Presence of Date of Amendment Check: PASS");
					int dateOfAmd = mt707.indexOf("Date of Amendment");
					// Tag Check
					String dateOfAmdTag = mt707.get(dateOfAmd - 1);
					swiftTag(dateOfAmdTag, "30:", "MT707_Date of Amendment");
					// Content Check
					String dateOfAmdCont = mt707.get(dateOfAmd + 2);
					String expdateOfAmdCont = MT707_DATA_MAP.get("30");
					System.out.println("Act - " + dateOfAmdCont + " | Exp - " + expdateOfAmdCont);
					swiftContent(expdateOfAmdCont, dateOfAmdCont, "MT707_Date of Amendment");
				} else {
					log("MT707_Presence of Date of Amendment Check: FAIL");
				}
			}
//Purpose of Message
			if (!MT707_DATA_MAP.get("22A").isEmpty()) {
				if (mt707.contains("Purpose of Message")) {
					log("MT707_Presence of Purpose of Message Check: PASS");
					int purposeMess = mt707.indexOf("Purpose of Message");
					// Tag Check
					String purposeMessTag = mt707.get(purposeMess - 1);
					swiftTag(purposeMessTag, "22A:", "MT707_Purpose of Message");
					// Content Check
					String purposeMessCont = mt707.get(purposeMess + 2);
					String expPurposeMessCont = MT707_DATA_MAP.get("22A");
					System.out.println("Act - " + purposeMessCont + " | Exp - " + expPurposeMessCont);
					swiftContent(expPurposeMessCont, purposeMessCont, "MT707_Purpose of Message");
				} else {
					log("MT707_Presence of Purpose of Message Check: FAIL");
				}
			}
//Date and Place of Expiry
			if (mt707.contains("Date and Place of Expiry")) {
				log("MT707_Presence of Purpose of Message Check: PASS");
				int datePlaceEcpiry = mt707.indexOf("Date and Place of Expiry");
				// Tag Check
				String datePlaceEcpiryTag = mt707.get(datePlaceEcpiry - 1);
				swiftTag(datePlaceEcpiryTag, "31D:", "MT707_Date and Place of Expiry");
				// Content Check
				String datePlaceEcpiryCont = mt707.get(datePlaceEcpiry + 2);
				String expDatePlaceEcpiryCont = MT707_DATA_MAP.get("31D_1")
						.concat(MT707_DATA_MAP.get("31D_2").toUpperCase());
				System.out.println("Act - " + datePlaceEcpiryCont + " | Exp - " + expDatePlaceEcpiryCont);
				swiftContent(expDatePlaceEcpiryCont, datePlaceEcpiryCont, "MT707_Date and Place of Expiry");
			} else {
				log("MT707_Presence of Date and Place of Expiry Check: FAIL");
			}
//Beneficiary
			if (mt707.contains("Date and Place of Expiry")) {
				log("MT707_Presence of Beneficiary Check: PASS");
				int Beneficiary = mt707.indexOf("Beneficiary");
				// Tag Check
				String beneficiaryTag = mt707.get(Beneficiary - 1);
				swiftTag(beneficiaryTag, "59:", "MT707_Beneficiary");
				// Content Check
				String beneficiaryCont = mt707.get(Beneficiary + 2).replaceAll("\n", "").replaceAll(" ", "");
				String expbeneficiaryCont = MT707_DATA_MAP.get("59").toUpperCase()
						+ MT707_DATA_MAP.get("59_1").toUpperCase() + MT707_DATA_MAP.get("59_2").toUpperCase()
						+ MT707_DATA_MAP.get("59_3").toUpperCase();
				System.out.println("Act - " + beneficiaryCont + " | Exp - " + expbeneficiaryCont);
				swiftContent(expbeneficiaryCont.replaceAll(" ", ""), beneficiaryCont, "MT707_Beneficiary");
			} else {
				log("MT707_Presence of Beneficiary Check: FAIL");
			}

//	Decrease of Documentary Credit Amount
			if ((!MT707_DATA_MAP.get("33B_2").isEmpty())) {
				if (mt707.contains("Decrease of Documentary Credit Amount")) {
					log("MT707_Presence of Decrease of Documentary Credit Amount Check: PASS");
					int decreaseAmount = mt707.indexOf("Decrease of Documentary Credit Amount");
					// Tag Check
					String decreaseTag = mt707.get(decreaseAmount - 1);
					swiftTag(decreaseTag, "33B:", "MT707_Decrease of Documentary Credit Amount");
					// Content Check
					String decreaseAmountCont = mt707.get(decreaseAmount + 2);
					String expDecreaseAmountCont = MT707_DATA_MAP.get("33B_1") + (MT707_DATA_MAP.get("33B_2"));
					System.out.println("Act - " + decreaseAmountCont + " | Exp - " + expDecreaseAmountCont);
					swiftContent(expDecreaseAmountCont, decreaseAmountCont,
							"MT707_Decrease of Documentary Credit Amount");
				} else {
					log("MT707_Presence of Decrease of Documentary Credit Amount Check: FAIL");
				}
			}
//Available With ... By ...	

			String NewAvaBy = data.getCellValue("Scenario 1", 3, 233);
			if (NewAvaBy.isEmpty()) {
				if (mt707.contains("Available With ... By ...")) {
					log("MT707_Presence of Available With...By... Check: PASS");
					int availableByInt = mt707.indexOf("Available With ... By ...");
					// TagCheck
					String availableTag = mt707.get(availableByInt - 1);
					swiftTag(availableTag, "41A:", "MT707_Available With...By...");
					// ContentCheck
					String avaiCon = mt707.get(availableByInt + 2);
					String expAvail = ISSSWI_DATA_MAP.get("41A_1") + "\n" + hash_map.get("AvailableBy");
					System.out.println("Act - " + avaiCon + " | Exp - " + expAvail);
					swiftContent(expAvail, avaiCon, "MT707_Available With...By...");
				} else {
					log("MT707_Presence of Available With...By... Check: FAIL");
				}
			}

//Partial Shipments

			if ((!MT707_DATA_MAP.get("43P").isEmpty())) {
				if (mt707.contains("Partial Shipments")) {
					log("MT707_Presence of Partial Shipments Check: PASS");
					int partialShipment = mt707.indexOf("Partial Shipments");
					// Tag Check
					String partShipTag = mt707.get(partialShipment - 1);
					swiftTag(partShipTag, "43P:", "MT707_Partial Shipments");
					// Content Check
					String partShipCont = mt707.get(partialShipment + 2);
					String exppartShipCont = MT707_DATA_MAP.get("43P");
					System.out.println("Act - " + partShipCont + " | Exp - " + exppartShipCont);
					swiftContent(exppartShipCont, partShipCont, "MT707_Partial Shipments");
				} else {
					log("MT707_Presence of Partial Shipments Check: FAIL");
				}
			}

//Place of Taking in Charge/Dispatch from .../Place of Receipt

			if ((!MT707_DATA_MAP.get("44A").isEmpty())) {
				if (mt707.contains("Place of Taking in Charge/Dispatch from .../Place of Receipt")) {
					log("MT707_Presence of Place of Taking in Charge/Dispatch from .../Place of Receipt Check: PASS");
					int takingInCharge = mt707.indexOf("Place of Taking in Charge/Dispatch from .../Place of Receipt");
					// Tag Check
					String takingInChargeTag = mt707.get(takingInCharge - 1);
					swiftTag(takingInChargeTag, "44A:",
							"MT707_Place of Taking in Charge/Dispatch from .../Place of Receipt");
					// Content Check
					String takingInChargeCont = mt707.get(takingInCharge + 2);
					String expTakingInChargeCont = MT707_DATA_MAP.get("44A").toUpperCase();
					System.out.println("Act - " + takingInChargeCont + " | Exp - " + expTakingInChargeCont);

					swiftContent(expTakingInChargeCont, takingInChargeCont,
							"MT707_Place of Taking in Charge/Dispatch from .../Place of Receipt");
				} else {
					log("MT707_Presence of Place of Taking in Charge/Dispatch from .../Place of Receipt Check: FAIL");
				}
			}
//Port of Loading/Airport of Departure
			if ((!MT707_DATA_MAP.get("44E").isEmpty())) {
				if (mt707.contains("Port of Loading/Airport of Departure")) {
					log("MT707_Presence of Port of Loading/Airport of Departure Check: PASS");
					int portOfLoading = mt707.indexOf("Port of Loading/Airport of Departure");
					// Tag Check
					String portOfLoadingTag = mt707.get(portOfLoading - 1);
					swiftTag(portOfLoadingTag, "44E:",
							"MT707_Place of Taking in Charge/Dispatch from .../Place of Receipt");
					// Content Check
					String portOfLoadingCont = mt707.get(portOfLoading + 2);
					String expportOfLoadingCont = MT707_DATA_MAP.get("44E").toUpperCase();
					System.out.println("Act - " + portOfLoadingCont + " | Exp - " + expportOfLoadingCont);
					swiftContent(expportOfLoadingCont, portOfLoadingCont, "MT707_Port of Loading/Airport of Departure");
				} else {
					log("MT707_Presence of Port of Loading/Airport of Departure Check: FAIL");
				}
			}
//Port of Discharge/Airport of Destination		
			if ((!MT707_DATA_MAP.get("44F").isEmpty())) {
				if (mt707.contains("Port of Discharge/Airport of Destination")) {
					log("MT707_Presence of Port of Discharge/Airport of Destination Check: PASS");
					int portOfDis = mt707.indexOf("Port of Discharge/Airport of Destination");
					// Tag Check
					String portOfDisTag = mt707.get(portOfDis - 1);
					swiftTag(portOfDisTag, "44F:", "MT707_Port of Discharge/Airport of Destination");
					// Content Check
					String portOfDisCont = mt707.get(portOfDis + 2);
					String expPortOfDisCont = MT707_DATA_MAP.get("44F").toUpperCase();
					System.out.println("Act - " + portOfDisCont + " | Exp - " + expPortOfDisCont);
					swiftContent(expPortOfDisCont, portOfDisCont, "MT707_Port of Discharge/Airport of Destination");
				} else {
					log("MT707_Presence of Port of Discharge/Airport of Destination Check: FAIL");
				}
			}
//Place of Final Destination/For Transportation to .../Place of Delivery		
			if ((!MT707_DATA_MAP.get("44B").isEmpty())) {
				if (mt707.contains("Place of Final Destination/For Transportation to .../Place of Delivery")) {
					log("MT707_Presence of Place of Final Destination/For Transportation to .../Place of Delivery Check: PASS");
					int portOfFinal = mt707
							.indexOf("Place of Final Destination/For Transportation to .../Place of Delivery");
					// Tag Check
					String portOfFinalTag = mt707.get(portOfFinal - 1);
					swiftTag(portOfFinalTag, "44B:",
							"MT707_Place of Taking in Charge/Dispatch from .../Place of Receipt");
					// Content Check
					String portOfFinalCont = mt707.get(portOfFinal + 2);
					String expPortOfFinalCont = MT707_DATA_MAP.get("44B").toUpperCase();
					System.out.println("Act - " + portOfFinalCont + " | Exp - " + expPortOfFinalCont);
					swiftContent(expPortOfFinalCont, portOfFinalCont,
							"MT707_Place of Final Destination/For Transportation to .../Place of Delivery");
				} else {
					log("MT707_Presence of Place of Final Destination/For Transportation to .../Place of Delivery Check: FAIL");
				}
			}
//Latest Date of Shipment	
			if ((!MT707_DATA_MAP.get("44C").isEmpty())) {
				if (mt707.contains("Latest Date of Shipment")) {
					log("MT707_Presence of Latest Date of Shipment Check: PASS");
					int lateShiDate = mt707.indexOf("Latest Date of Shipment");
					// Tag Check
					String lateShiDateTag = mt707.get(lateShiDate - 1);
					swiftTag(lateShiDateTag, "44C:", "MT707_Latest Date of Shipment");
					// Content Check
					String lateShiDateCont = mt707.get(lateShiDate + 2);
					String expLateShiDateCont = MT707_DATA_MAP.get("44C");
					System.out.println("Act - " + lateShiDateCont + " | Exp - " + expLateShiDateCont);
					swiftContent(expLateShiDateCont, lateShiDateCont, "MT707_Latest Date of Shipment");
				} else {
					log("MT707_Presence of Latest Date of Shipment Check: FAIL");
				}
			}
//Description of Goods and/or Services	
			if ((!MT707_DATA_MAP.get("45B").isEmpty())) {
				if (mt707.contains("Description of Goods and/or Services")) {
					log("MT707_Presence of Description of Goods and/or Services Check: PASS");
					int desOfGoods = mt707.indexOf("Description of Goods and/or Services");
					// Tag Check
					String desOfGoodsTag = mt707.get(desOfGoods - 1);
					swiftTag(desOfGoodsTag, "45B:", "MT707_Description of Goods and/or Services");
					// Content Check
					String desOfGoodsCont = mt707.get(desOfGoods + 2);
					String expDesOfGoodsCont = MT707_DATA_MAP.get("45B").toUpperCase();
					System.out.println("Act - " + desOfGoodsCont + " | Exp - " + expDesOfGoodsCont);
					swiftContent(expDesOfGoodsCont, desOfGoodsCont, "MT707_Description of Goods and/or Services");
				} else {
					log("MT707_Presence of Description of Goods and/or Services Check: FAIL");
				}
			}
//Documents Required	
			if ((!MT707_DATA_MAP.get("46B").isEmpty())) {
				if (mt707.contains("Documents Required")) {
					log("MT707_Presence of Documents Required Check: PASS");
					int docRequ = mt707.indexOf("Documents Required");
					// Tag Check
					String docRequTag = mt707.get(docRequ - 1);
					swiftTag(docRequTag, "46B:", "MT707_Documents Required");
					// Content Check
					String docRequCont = mt707.get(docRequ + 2).replaceAll("\n", " ").replaceAll(" ", "");
					String expdocRequCont = MT707_DATA_MAP.get("46B").toUpperCase().replaceAll("\n", "").replaceAll(" ",
							"");
					;
					System.out.println("Act - " + docRequCont + " | Exp - " + expdocRequCont);
					swiftContent(expdocRequCont, docRequCont, "MT707_Documents Required");
				} else {
					log("MT707_Presence of Documents Required Check: FAIL");
				}
			}
//Additional Conditions	
			if ((!MT707_DATA_MAP.get("47B").isEmpty())) {
				if (mt707.contains("Additional Conditions")) {
					log("MT707_Presence of Additional Conditions	 Check: PASS");
					int addCondition = mt707.indexOf("Additional Conditions");
					// Tag Check
					String addConditionTag = mt707.get(addCondition - 1);
					swiftTag(addConditionTag, "47B:", "MT707_Additional Conditions");
					// Content Check
					String addConditionCont = mt707.get(addCondition + 2).replace("\n", "").toLowerCase();
					String expAddConditionCont = MT707_DATA_MAP.get("47B").toLowerCase().replaceAll("\n", "").trim();
					System.out.println("Act - " + addConditionCont + " | Exp - " + expAddConditionCont);
//					if (addConditionCont.contentEquals(expAddConditionCont)) {
//						log("MT707_Additional Conditions_Content Check : Pass");
//					} else {
//						log("MT707_Additional Conditions Check : Fail");
//						takesnap("MT707_Additional Conditions_Content Check");
//					}
				} else {
					log("MT707_Presence of Additional Conditions Check: FAIL");
				}
			}

//Special Payment Conditions for Beneficiary
			if ((!MT707_DATA_MAP.get("49M").isEmpty())) {
				if (mt707.contains("Special Payment Conditions for Beneficiary")) {
					log("MT707_Presence of Special Payment Conditions for Beneficiary Check: PASS");
					int spcPayCool = mt707.indexOf("Special Payment Conditions for Beneficiary");
					// Tag Check
					String spcPayCoolTag = mt707.get(spcPayCool - 1);
					swiftTag(spcPayCoolTag, "49M:", "MT707_Special Payment Conditions for Beneficiary");
					// Content Check
					String spcPayCoolCont = mt707.get(spcPayCool + 2).replaceAll("\n", " ");
					String expSpcPayCoolCont = MT707_DATA_MAP.get("49M").toUpperCase();
					System.out.println("Act - " + spcPayCoolCont + " | Exp - " + expSpcPayCoolCont);
					swiftContent(expSpcPayCoolCont, spcPayCoolCont, "MT707_Special Payment Conditions for Beneficiary");
				} else {
					log("MT707_Presence of Special Payment Conditions for Beneficiary Check: FAIL");
				}

			}
//Special Payment Conditions for Bank Only
			if ((!MT707_DATA_MAP.get("49N").isEmpty())) {
				if (mt707.contains("Special Payment Conditions for Bank Only")) {
					log("MT707_Presence of Special Payment Conditions for Bank Only Check: PASS");
					int spcPayCoolforBank = mt707.indexOf("Special Payment Conditions for Bank Only");
					// Tag Check
					String spcPayCoolBankTag = mt707.get(spcPayCoolforBank - 1);
					swiftTag(spcPayCoolBankTag, "49N:", "MT707_Special Payment Conditions for Bank Only");
					// Content Check
					String spcPayCoolBankCont = mt707.get(spcPayCoolforBank + 2).replaceAll("\n", " ");
					String expSpcPayCoolBankCont = MT707_DATA_MAP.get("49N");
					System.out.println("Act - " + spcPayCoolBankCont + " | Exp - " + expSpcPayCoolBankCont);
					swiftContent(expSpcPayCoolBankCont, spcPayCoolBankCont,
							"MT707_Special Payment Conditions for Bank Only");
				} else {
					log("MT707_Presence of Special Payment Conditions for Bank Only Check: FAIL");
				}
			}
//Amendment Charge Payable By
			if ((!MT707_DATA_MAP.get("71N").isEmpty())) {
				if (mt707.contains("Amendment Charge Payable By")) {
					log("MT707_Presence of Amendment Charge Payable By Check: PASS");
					int amdCharPay = mt707.indexOf("Amendment Charge Payable By");
					// Tag Check
					String amdCharPayTag = mt707.get(amdCharPay - 1);
					swiftTag(amdCharPayTag, "71N:", "MT707_Amendment Charge Payable By");
					// Content Check
					String amdCharPayCont = mt707.get(amdCharPay + 2).replaceAll("\n", " ");
					String expAmdCharPayCont = MT707_DATA_MAP.get("71N");
					System.out.println("Act - " + amdCharPayCont + " | Exp - " + expAmdCharPayCont);
					swiftContent(expAmdCharPayCont, amdCharPayCont, "MT707_Amendment Charge Payable By");
				} else {
					log("MT707_Presence of Amendment Charge Payable By Check: FAIL");
				}
			}
//Confirmation Instructions
			if ((!MT707_DATA_MAP.get("49").isEmpty())) {
				if (mt707.contains("Confirmation Instructions")) {
					log("MT707_Presence of Confirmation Instructions Check: PASS");
					int confInst = mt707.indexOf("Confirmation Instructions");
					// Tag Check
					String confInstTag = mt707.get(confInst - 1);
					swiftTag(confInstTag, "49:", "MT707_Confirmation Instructions");
					// Content Check
					String confInstCont = mt707.get(confInst + 2).replaceAll("\n", " ");
					String expconfInstCont = MT707_DATA_MAP.get("49");
					System.out.println("Act - " + confInstCont + " | Exp - " + expconfInstCont);
					swiftContent(expconfInstCont, confInstCont, "MT707_Confirmation Instructions");
				} else {
					log("MT707_Presence of Confirmation Instructions Check: FAIL");
				}
			}

//Reimbursing Bank
			if ((!MT707_DATA_MAP.get("Reimswift").isEmpty())) {
				if (mt707.contains("Reimbursing Bank")) {
					log("MT707_Presence of Reimbursing Bank Check: PASS");
					int reimBank = mt707.indexOf("Reimbursing Bank");
					// Tag Check
					String reimBankTag = mt707.get(reimBank - 1);
					swiftTag(reimBankTag, MT707_DATA_MAP.get("53A_T") + ":", "MT707_Reimbursing Bank");
					// Content Check
					String reimBankCont = mt707.get(reimBank + 2).replaceAll("\n", " ");
					String expReimBankCont = MT707_DATA_MAP.get("Reimswift");
					System.out.println("Act - " + reimBankCont + " | Exp - " + expReimBankCont);
					swiftContent(expReimBankCont, reimBankCont, "MT707_Reimbursing Bank");
				} else {
					log("MT707_Presence of Reimbursing Bank Check: FAIL");
				}
			}
//Instructions to the Paying/Accepting/Negotiating Bank
			if ((!MT707_DATA_MAP.get("78").isEmpty())) {
				if (mt707.contains("Instructions to the Paying/Accepting/Negotiating Bank")) {
					log("MT707_Presence of Instructions to the Paying/Accepting/Negotiating Bank Check: PASS");
					int InsTPayBk = mt707.indexOf("Instructions to the Paying/Accepting/Negotiating Bank");
					// Tag Check
					String InsTPayBkTag = mt707.get(InsTPayBk - 1);
					swiftTag(InsTPayBkTag, "78:", "MT707_Instructions to the Paying/Accepting/Negotiating Bank");
					// Content Check
					String insTPayBkCont = mt707.get(InsTPayBk + 2).replaceAll("\n", " ");
					String expInsTPayBkCont = MT707_DATA_MAP.get("78");
					System.out.println("Act - " + insTPayBkCont + " | Exp - " + expInsTPayBkCont);
					swiftContent(expInsTPayBkCont, insTPayBkCont,
							"MT707_Instructions to the Paying/Accepting/Negotiating Bank");
				} else {
					log("MT707_Presence of Instructions to the Paying/Accepting/Negotiating Bank Check: FAIL");
				}
			}

			driver.close();
		}
	}

	@Then("^Validate the Advice Swift$")
	public void validate_advice_swift() throws Throwable {
		switchToWindow(1);
		Thread.sleep(5000);
		WebElement eleView = locateElement("xpath",
				"/html/body/form/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/div/table/tbody/tr[4]/td/table/tbody/tr[3]/td[3]/a");
		click(eleView);
		Thread.sleep(5000);
		switchToWindow(2);
		driver.manage().window().maximize();
		swiftValidation("Issue Amendment");
	}

	@Given("^Click On Beneficiary Response Function$")
	public void clickOnBeneficiaryResponseFunction() throws Throwable {
//		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("FunctionList");
		WebElement eleRegAmd = locateElement("name", "G49082300296F05030702059");
		eleRegAmd.click();
		String refNum = hash_map.get("referenceNo");
		/* Thread.sleep(3000); */
		catalog(refNum);
	}

	@And("^Enter the Beneficiary Decision$")
	public void entertheBeneficiaryDecision() throws Throwable {
		Thread.sleep(3000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		Thread.sleep(2000);
		WebElement eleRes = locateElement("id", "BENE_CONS_FLG");
		String BeneCon = data.getCellValue("Scenario 1", 3, 292);
		selectDropDownUsingText(eleRes, BeneCon);
		ISSSWI_DATA_MAP.put("AccptRjct", getTypedText(eleRes));

	}

	@Given("^Click on BR Advice tab$")
	public void click_on_BR_Advice_tab() throws Throwable {
		WebElement eleAdvice = locateElement("id", "E");
		click(eleAdvice);
	}

	@Given("^click on add button in BR$")
	public void click_on_add_button_in_BR() throws Throwable {
		WebElement eleAdd = locateElement("id", "ext-gen91");
		click(eleAdd);
	}

	@Given("^Select the type of message in BR$")
	public void select_the_type_of_message_in_BR() throws Throwable {
		String TOM = data.getCellValue("Scenario 1", 2, 298).replaceAll(" ", "");

		if (!TOM.isEmpty()) {
			switchToFrame("frame.AdivceForBankCust");
			WebElement eleTOM = locateElement("id", "MESG_TYPE_BANK");
			selectDropDownUsingText(eleTOM, TOM);
			String typeOfMessage = getTypedText(eleTOM);
			Thread.sleep(1000);
			String swiftTagColour = getBackgroundColor(locateElement("name", "SEND_TO_BK_SW_ADD"));
			String rReferenceColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_REF"));
			String languageColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_LANG"));
			String mailColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_POST_ADD"));
			String faxColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_FAX"));
			String emailColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_EMAIL"));
			String sNarrativeColour = getBackgroundColor(locateElement("name", "BANK_NARR_TAG_79"));
			String mNarrativeColour = getBackgroundColor(locateElement("name", "BANK_NARR_MAIL"));
			String nameColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_NM"));

			if (typeOfMessage.equalsIgnoreCase("MT199") || typeOfMessage.equalsIgnoreCase("MT299")
					|| typeOfMessage.equalsIgnoreCase("MT799") || typeOfMessage.equalsIgnoreCase("MT999")) {
				if (swiftTagColour.contains(Mandatory) && rReferenceColour.contains(Mandatory)
						&& languageColour.contains(Mandatory) && sNarrativeColour.contains(Mandatory)
						&& mailColour.contains(Optional) && faxColour.contains(Optional)
						&& emailColour.contains(Optional) && mNarrativeColour.contains(Protected)) {
					log("Type Of Message Onchange(MT n99) : Pass");
				} else {
					log("Type Of Message Onchange(MT n99): Fail");
					takesnap("Type Of Message Onchange(MT n99)");
				}
			}
			if (typeOfMessage.equalsIgnoreCase("Mail")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Mandatory)
						&& faxColour.contains(Optional) && emailColour.contains(Optional)
						&& mNarrativeColour.contains(Mandatory)) {
					log("Type Of Message Onchange(Mail) : Pass");
				} else {
					log("Type Of Message Onchange(Mail) : Fail");
					takesnap("Type Of Message Onchange(Mail)");
				}
			}

			if (typeOfMessage.equalsIgnoreCase("Email")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Optional)
						&& faxColour.contains(Optional) && emailColour.contains(Mandatory)
						&& mNarrativeColour.contains(Mandatory)) {
					log("Type Of Message Onchange(email) : Pass");
				} else {
					log("Type Of Message Onchange(email) : Fail");
					takesnap("Type Of Message Onchange(email)");
				}
			}

			if (typeOfMessage.equalsIgnoreCase("Fax")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Optional)
						&& faxColour.contains(Mandatory) && emailColour.contains(Optional)
						&& mNarrativeColour.contains(Mandatory)) {
					log("Type Of Message Onchange(Fax) : Pass");
				} else {
					log("Type Of Message Onchange(Fax) : Fail");
					takesnap("Type Of Message Onchange(Fax)");
				}
			}
		}
	}

	@Given("^Enter the Bank ID in BR$")
	public void enter_the_Bank_ID_in_BR() throws Throwable {
		String bId = data.getCellValue("Scenario 1", 2, 299);
		if (!bId.isEmpty()) {
			WebElement eleBankId = locateElement("name", "SEND_TO_BANK_ID");
			append(eleBankId, bId);
			eleBankId.sendKeys(Keys.TAB);
			Thread.sleep(3000);
			MT707_DATA_MAP.put("BENRES_ADV_RECE_CODE", getTypedText(locateElement("name", "SEND_TO_BK_SW_ADD")));
			MT707_DATA_MAP.put("BENRES_ADV_RELD_REF", getTypedText(locateElement("name", "SEND_TO_BANK_REF")));

		}
	}

	@Given("^Select the  Mail Method in BR$")
	public void select_the_Mail_Method_in_BR() throws Throwable {
		String mMethod = data.getCellValue("Scenario 1", 2, 301);
		if (!mMethod.isEmpty()) {
			WebElement eleMailMethod = locateElement("Name", "MAIL_MTHD_BANK");
			selectDropDownUsingText(eleMailMethod, mMethod);
		}
	}

	@Given("^Enter the  Narrative \\(MT n(\\d+) Tag (\\d+)Z\\) in BR$")
	public void enter_the_Narrative_MT_n_Tag_Z_in_BR(int arg1, int arg2) throws Throwable {
		String narrative = data.getCellValue("Scenario 1", 2, 302);
		if (!narrative.isEmpty()) {
			WebElement eleNarrative = locateElement("name", "BANK_NARR_TAG_79");
			lengthValidation2(eleNarrative, "35*50", narrative, "Narrative(MTn99Tag79Z) Length Check");
			MT707_DATA_MAP.put("BENRES_ADV_NARR_REF", getTypedText(locateElement("name", "BANK_NARR_TAG_79")));
		}
	}

	@Given("^Enter the Narrative \\(Mail\\) in BR$")
	public void enter_the_Narrative_Mail_in_BR() throws Throwable {
		String mailNarrative = data.getCellValue("Scenario 1", 2, 300);
		if (!mailNarrative.isEmpty()) {
			WebElement eleNarrative = locateElement("name", "BANK_NARR_MAIL");
			append(eleNarrative, mailNarrative);
		}
	}

	@Given("^Click on the customer subtab in BR$")
	public void click_on_the_customer_subtab_in_BR() throws Throwable {
		WebElement eleCustomer = locateElement("ID", "B");
		click(eleCustomer);
	}

	@Given("^select  the type of message and verify onchanges in BR$")
	public void select_the_type_of_message_and_verify_onchanges_in_BR() throws Throwable {
		String custTom = data.getCellValue("Scenario 1", 2, 303);
		if (!custTom.isEmpty()) {
			WebElement eleTom = locateElement("id", "MESG_TYPE_CUST");
			selectDropDownUsingText(eleTom, custTom);
			String custTomText = getTypedText(eleTom);
			String nameColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_NM"));
			String languageColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_LANG"));
			String emailColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_EMAIL"));
			String faxColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_FAX"));
			String narrColour = getBackgroundColor(locateElement("id", "CUST_NARR_TAG_79"));
			String mailColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_POST_ADD"));
			if (custTomText.equalsIgnoreCase("Fax")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Optional) && faxColour.contains(Mandatory)
						&& narrColour.contains(Mandatory) && mailColour.contains(Optional)) {
					log("Customer - Type Of Message Onchange(Fax) : Pass");
				} else {
					log("Customer - Type Of Message Onchange(Fax) : Fail");
					takesnap("Customer - Type Of Message Onchange(Fax)");
				}
			}

			if (custTomText.equalsIgnoreCase("Mail")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Optional) && faxColour.contains(Optional)
						&& narrColour.contains(Mandatory) && mailColour.contains(Mandatory)) {
					log("Customer - Type Of Message Onchange(Mail) : Pass");
				} else {
					log("Customer - Type Of Message Onchange(Mail) : Fail");
					takesnap("Customer - Type Of Message Onchange(Mail)");
				}
			}

			if (custTomText.equalsIgnoreCase("Email")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Mandatory) && faxColour.contains(Optional)
						&& narrColour.contains(Mandatory) && mailColour.contains(Optional)) {
					log("Customer - Type Of Message Onchange(Email) : Pass");
				} else {
					log("Customer - Type Of Message Onchange(Email) : Fail");
					takesnap("Customer - Type Of Message Onchange(Email)");
				}
			}
		}
	}

	@Given("^select the Customer Mail Method in BR$")
	public void select_the_Customer_Mail_Method_in_BR() throws Throwable {
		String mMethod = data.getCellValue("Scenario 1", 2, 305);
		if (!mMethod.isEmpty()) {
			WebElement eleMailMethod = locateElement("Name", "MAIL_MTHD_CUST");
			selectDropDownUsingText(eleMailMethod, mMethod);
		}
	}

	@Given("^Enter the Customer ID in BR$")
	public void enter_the_Customer_ID_in_BR() throws Throwable {
		String custId = data.getCellValue("Scenario 1", 2, 304);
		if (!custId.isEmpty()) {
			WebElement eleCustId = locateElement("name", "SEND_TO_CUST_ID");
			append(eleCustId, custId);
			eleCustId.sendKeys(Keys.TAB);

		}
	}

	@Given("^Enter the narrative in BR$")
	public void enter_the_narrative_in_BR() throws Throwable {
		String custNarrative = data.getCellValue("Scenario 1", 2, 306);
		if (!custNarrative.isEmpty()) {
			WebElement eleCustId = locateElement("name", "CUST_NARR_TAG_79");
			append(eleCustId, custNarrative);
		}
	}

	@Then("^Verify the Notes in BR$")
	public void verfy_the_Notes_in_Beneficiary_Response_in_BR() throws Throwable {
		WebElement eleNotes = locateElement("id", "E");
		click(eleNotes);
		WebElement elenotes = locateElement("id", ("NOTES"));
		String note = getTypedText(elenotes);
		/* System.out.println("tester" + note); */
//		String nNotes = hash_map.get("Notes");
		/* System.out.println("tester" + nNotes); */
		if (note.equalsIgnoreCase(hash_map.get("Notes"))) {

			log("Verify Notes details in BR: PASS");
		} else {
			log("Verify Notes details in BR: FAIL");
			takesnap("Verify Notes details in BR");
		}
	}

	@Then("^Enter the Diary details in BR$")
	public void enter_the_Diary_details_in_BR() throws Throwable {
		WebElement eleDiary = locateElement("id", "M");
		click(eleDiary);
		String rReference = hash_map.get("referenceNo");
		// System.out.println(rReference);
		WebElement eleReRef = locateElement("name", ("DIARY_RELATED_REF"));
		append(eleReRef, rReference);
		Thread.sleep(3000);
		if (getTypedText(eleReRef).length() <= 16) {
			log("Verify Related Reference Length: PASS");
		} else {
			log("Verify Related Reference Length: FAIL");
			takesnap("Verify Related Reference Length");
		}
		WebElement eleNarrative = locateElement("name", "DIARY_NARRATIVE");
		String narrative = data.getCellValue("Scenario 1", 2, 311);
		append(eleNarrative, narrative);
		String regAmdNarr = getTypedText(eleNarrative);
		hash_map.put("IssAmdNarrative", regAmdNarr);
		eleNarrative.sendKeys(Keys.TAB);

	}

	@Then("^Verify the Diary History in BR$")
	public void verify_the_Diary_History_in_BR() throws Throwable {
		String Rnarrative = hash_map.get("RegExpectedNarrative");
		String expIssNar = hash_map.get("IssNarrative");
		String expRegAmdNar = hash_map.get("RegAmdNarrative");
		String expIssAmdNar = hash_map.get("IssAmdNarrative");

		WebElement eleviewhistory = locateElement("id", ("view_1"));
		eleviewhistory.click();
		Set<String> windowId = driver.getWindowHandles();
		Iterator<String> itererator = windowId.iterator();
		String mainWinID = itererator.next();
		String newAdwinID = itererator.next();
		driver.switchTo().window(newAdwinID);

		Thread.sleep(3000);
		if (Rnarrative != null) {
			WebElement eleReReference = locateElement("id", ("_id_3C_MAIN_REF"));
			String RegActualref = getTypedText(eleReReference);
			String nRef = hash_map.get("RegExpectedRef");
			WebElement elenNarrative = locateElement("id", ("DIARY_NARRATIVE"));
			String RegActualNarrative = getTypedText(elenNarrative);
			/* System.out.println("test" + Rnarrative); */
			if (RegActualref.equalsIgnoreCase(nRef) || RegActualNarrative.equalsIgnoreCase(Rnarrative)) {
				log("Verify Register Diary details: PASS");
			} else {
				log("Verify Register Diary details: FAIL");
				takesnap("Verify Reg Diary details");
			}
		}

		if (expIssNar != null) {
			Thread.sleep(2000);
			WebElement eleIssReReference = locateElement("id", ("_id_2C_MAIN_REF"));
			String IssActualref = getTypedText(eleIssReReference);
			String expIssRef = hash_map.get("issRef");
			if (expIssRef.equals(IssActualref)) {
				log("Verify Issue  Diary details : Pass");
			} else {
				log("Verify Issue Diary details : Fail");
			}
		}

		if (expRegAmdNar != null) {

			WebElement eleRegAmdReReference = locateElement("id", ("_id_1C_MAIN_REF"));
			String RegAmdActualref = getTypedText(eleRegAmdReReference);
			String expRegAmdRef = hash_map.get("RegAmdReRef");
			if (expRegAmdRef.equals(RegAmdActualref)) {
				log("Verify Register Amendment Diary details : Pass");
			} else {
				log("Verify Register Amendment Diary details : Fail");
			}

			if (expIssAmdNar != null) {

				WebElement eleIssAmdReReference = locateElement("id", ("_id_0C_MAIN_REF"));
				String IssAmdActualref = getTypedText(eleIssAmdReReference);
//				String expIssAmdReRef = hash_map.get("IssAmdReRef");
				if (IssAmdActualref.equals(IssAmdActualref)) {
					log("Verify Register Amendment Diary details : Pass");
				} else {
					log("Verify Register Amendment Diary details : Fail");
				}
			}
		}

		Thread.sleep(5000);
		driver.switchTo().window(mainWinID);
		/* System.out.println(driver.getTitle()); */
		switchToFrame("work");
	}

	@Then("^validate the GAPI in BR$")
	public void validate_the_gapi_in_BR() throws Throwable {
		driver.switchTo().parentFrame();
		switchToFrame("eeToolbar");
		WebElement gapi = locateElement("id", "_pregapi");
		click(gapi);
		gapiValidation("Beneficiary Response");
		Thread.sleep(3000);
		driver.close();
		switchToWindow(1);
		driver.close();
		switchToWindow(0);
		driver.manage().window().maximize();

	}

	@Then("BR - Output - GAPI Check$")
	public void br_output_gapi_check() throws Throwable {
		Thread.sleep(2000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		Thread.sleep(2000);
		if (locateElement("name", "Gapi").isDisplayed()) {
			log("BR_Output - GAPI: PASS");
		} else {
			log("BR_Output - GAPI: FAIL");
		}
	}

	@Then("BR - Output - Swift Check$")
	public void br_output_swift_check() throws Throwable {
		if (locateElement("name", "Swift").isDisplayed()) {
			log("IssAmd_Output - Swift: PASS");
		} else {
			log("IssAmd_Output - Swift: FAIL");
		}
	}

	@Then("validate the Swift in BR$")
	public void click_swift_button() throws Throwable {
		if (locateElement("name", "Swift").isDisplayed()) {
			click(locateElement("name", "Swift"));
			Thread.sleep(6000);
			switchToWindow(1);
			driver.manage().window().maximize();
			WebElement eleview = locateElement("xpath", "(//a)[1]");
			click(eleview);
			Thread.sleep(6000);
			switchToWindow(2);
			driver.manage().window().maximize();
			swiftValidation("Beneficiary Response");
		}
	}

	@Given("^click on IPLC Presentation$")
	public void click_on_IPLC_Presentation() throws Throwable {
		driver.switchTo().defaultContent();
		driver.switchTo().frame("FunctionList");
		WebElement eleIPLCPres = locateElement("Name", "IPLC Presentation");
		eleIPLCPres.click();
	}

	@Given("^click on Register Document LC$")
	public void click_on_Register_Document_LC() throws Throwable {
		WebElement eleRegDocLC = locateElement("Name", "G49082300273F05030701981");
		eleRegDocLC.click();
		Thread.sleep(3000);
		String refNO = hash_map.get("referenceNo");
		Thread.sleep(3000);
		catalog(refNO);

	}

	@Given("^Enter Presentation Amount$")
	public void Enter_Presenation_Amount() throws Throwable {
		Thread.sleep(3000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		Thread.sleep(2000);
		String typedText = getTypedText(locateElement("name", "DRAWING_REF"));
		ISSSWI_DATA_MAP.put("DrawgRefNb", typedText);
		int index = typedText.indexOf("/");
		String drawingNo = typedText.substring(index);
		System.out.println(drawingNo);

		String drawingNo2 = drawingNo.substring(drawingNo.indexOf("1"));
		System.out.println("------------index--------" + drawingNo.indexOf(1) + "------index----------");
		System.out.println("------------drawNo--------------------------" + drawingNo2
				+ "------------drawNo--------------------------");

		ISSSWI_DATA_MAP.put("NoOfDrawg", drawingNo2);
		String preamt = data.getCellValue("Scenario 1", 3, 321);
		WebElement elePreAmt = locateElement("id", "PRES_AMT");
		elePreAmt.clear();
		elePreAmt.click();
		append(elePreAmt, preamt);
		elePreAmt.sendKeys(Keys.TAB);
		ISSSWI_DATA_MAP.put("PrestinAmt", getTypedText(elePreAmt).replaceAll(",", ""));
		ISSSWI_DATA_MAP.put("PrestinBala", getTypedText(elePreAmt).replaceAll(",", ""));

	}

	@Given("^Enter Additional Amounts$")
	public void Enter_Additional_Amounts() throws Throwable {
		String addamt = data.getCellValue("Scenario 1", 3, 322);
		WebElement eleAddAmt = locateElement("id", "ADDIT_PRES_BK_AMTS");
		eleAddAmt.clear();
		eleAddAmt.click();
		append(eleAddAmt, addamt);
		eleAddAmt.sendKeys(Keys.TAB);
		ISSSWI_DATA_MAP.put("AdditnlPrestinBkChrgs", getTypedText(eleAddAmt).replaceAll(",", ""));

	}

	@Given("^Enter Charges Deducted$")
	public void Enter_Charges_Deducted() throws Throwable {
		String chargedect = data.getCellValue("Scenario 1", 3, 323);
		WebElement eleChargeDec = locateElement("id", "CHGS_DEDUCTED");
		eleChargeDec.clear();
		eleChargeDec.click();
		append(eleChargeDec, chargedect);
		eleChargeDec.sendKeys(Keys.TAB);

	}

	@Given("^Enter Presenter Charges$")
	public void Enter_Presenter_Charges() throws Throwable {
		String precharg = data.getCellValue("Scenario 1", 3, 324);
		WebElement elePresCharges = locateElement("id", "PRES_BK_CHGS");
		elePresCharges.clear();
		elePresCharges.click();
		append(elePresCharges, precharg);
		elePresCharges.sendKeys(Keys.TAB);
		System.out.println("---------------------AdditnlAmtCovrd--------------" + MT707_DATA_MAP.get("AdditnlAmtCovrd")
				+ "---------------------------AdditnlAmtCovrd------------------------------");

	}

	@Given("^Enter Presenter Reference$")
	public void Enter_Presenter_Reference() throws Throwable {
		String PreRef = hash_map.get("referenceNo");
		WebElement elepreref = locateElement("id", ("PRES_BK_REF"));
		append(elepreref, PreRef);
	}

	@Given("^Verify Total Amount to be Paid$")
	public void verify_Total_Amount_to_be_Paid() throws Throwable {
		Thread.sleep(3000);
		WebElement elePreAmt = locateElement("id", "PRES_AMT");
		String preamt = getTypedText(elePreAmt);
		double ExpecPreamt = convertToDouble(preamt);
		WebElement eleAddAmt = locateElement("id", "ADDIT_PRES_BK_AMTS");
		String addamt = getTypedText(eleAddAmt);
		double Expecaddamt = convertToDouble(addamt);
		WebElement eleChargeDec = locateElement("id", "CHGS_DEDUCTED");
		String chargedec = getTypedText(eleChargeDec);
		double Expecchargedec = convertToDouble(chargedec);
		WebElement elePresCharges = locateElement("id", "PRES_BK_CHGS");
		String prescharge = getTypedText(elePresCharges);
		double Expecprescharge = convertToDouble(prescharge);
		double ExpecTotal = (ExpecPreamt + Expecaddamt - Expecchargedec + Expecprescharge);
		WebElement eleTotalAmt = locateElement("id", "TOTAL_AMT");
		String Totalamt = getTypedText(eleTotalAmt);
		double ActualTotalamt = convertToDouble(Totalamt);
		ISSSWI_DATA_MAP.put("TtlAmtPayab", Totalamt.replaceAll(",", ""));
		if (ExpecTotal == ActualTotalamt) {
			log("Verify Total amount to be claimed: PASS");

		} else {
			log("Verify Total amount to be claimed: FAIL");
			takesnap("Verify Total amount to be claimed");

		}
		WebElement locateElement = locateElement("name", "PRES_DT");
		ISSSWI_DATA_MAP.put("DtChkd", getTypedText(locateElement));
		ISSSWI_DATA_MAP.put("PrestinCcy", getTypedText(locateElement("name", "PRES_CCY")));

	}

	@Given("^Click on Documents Tab$")
	public void click_on_Documents_Tab() throws Throwable {
		Thread.sleep(3000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		Thread.sleep(2000);
		WebElement eleDoc = locateElement("id", "C");
		eleDoc.click();
	}

	@Given("^Enter the document details$")
	public void enter_the_document_details() throws Throwable {
		String draft1 = data.getCellValue("Scenario 1", 3, 333);
		WebElement eleDraft1 = locateElement("id", "DRAFT_1");
		append(eleDraft1, draft1);

		String draft2 = data.getCellValue("Scenario 1", 4, 333);
		WebElement eleDraft2 = locateElement("id", "DRAFT_2");
		append(eleDraft2, draft2);

		String invoice1 = data.getCellValue("Scenario 1", 3, 334);
		WebElement eleInvoice1 = locateElement("id", "INVOICE_1");
		append(eleInvoice1, invoice1);

		String invoice2 = data.getCellValue("Scenario 1", 4, 334);
		WebElement eleInvoice2 = locateElement("id", "INVOICE_2");
		append(eleInvoice2, invoice2);

		ISSSWI_DATA_MAP.put("DocDecsin", getTypedText(locateElement("name", "DOC_PRES")));
		ISSSWI_DATA_MAP.put("DocPres", getTypedText(locateElement("name", "DOC_PRES")));

	}

	@Given("^Click on Reg Docs Advice tab$")
	public void click_on_Reg_Docs_Advice_tab() throws Throwable {
		WebElement eleRDAdvice = locateElement("id", "D");
		click(eleRDAdvice);
	}

	@Given("^click Reg Docs add button and verify onchanges$")
	public void click_Reg_Docs_add_button_and_verify_onchanges() throws Throwable {
		WebElement eleRDAdd = locateElement("id", "ext-gen91");
		click(eleRDAdd);
	}

	@Given("^select Reg Docs type of message$")
	public void select_Reg_Docs_type_of_message() throws Throwable {
		String TOM = data.getCellValue("Scenario 1", 2, 335).replaceAll(" ", "");

		if (!TOM.isEmpty()) {
			switchToFrame("frame.AdivceForBankCust");
			WebElement eleTOM = locateElement("id", "MESG_TYPE_BANK");
			selectDropDownUsingText(eleTOM, TOM);
			String typeOfMessage = getTypedText(eleTOM);

			String swiftTagColour = getBackgroundColor(locateElement("name", "SEND_TO_BK_SW_ADD"));
			String rReferenceColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_REF"));
			String languageColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_LANG"));
			String mailColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_POST_ADD"));
			String faxColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_FAX"));
			String emailColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_EMAIL"));
			String sNarrativeColour = getBackgroundColor(locateElement("name", "BANK_NARR_TAG_79"));
			String mNarrativeColour = getBackgroundColor(locateElement("name", "BANK_NARR_MAIL"));
			String nameColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_NM"));

			if (typeOfMessage.equalsIgnoreCase("MT199") || typeOfMessage.equalsIgnoreCase("MT299")
					|| typeOfMessage.equalsIgnoreCase("MT799") || typeOfMessage.equalsIgnoreCase("MT999")) {
				if (swiftTagColour.contains(Mandatory) && rReferenceColour.contains(Mandatory)
						&& languageColour.contains(Mandatory) && sNarrativeColour.contains(Mandatory)
						&& mailColour.contains(Optional) && faxColour.contains(Optional)
						&& emailColour.contains(Optional) && mNarrativeColour.contains(Protected)) {
					log("Type Of Message Onchange(MT n99) : Pass");
				} else {
					log("Type Of Message Onchange(MT n99): Fail");
					takesnap("Type Of Message Onchange(MT n99)");
				}
			}
			if (typeOfMessage.equalsIgnoreCase("Mail")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Mandatory)
						&& faxColour.contains(Optional) && emailColour.contains(Optional)
						&& mNarrativeColour.contains(Mandatory)) {
					log("Type Of Message Onchange(Mail) : Pass");
				} else {
					log("Type Of Message Onchange(Mail) : Fail");
					takesnap("Type Of Message Onchange(Mail)");
				}
			}

			if (typeOfMessage.equalsIgnoreCase("Email")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Optional)
						&& faxColour.contains(Optional) && emailColour.contains(Mandatory)
						&& mNarrativeColour.contains(Mandatory)) {
					log("Type Of Message Onchange(email) : Pass");
				} else {
					log("Type Of Message Onchange(email) : Fail");
					takesnap("Type Of Message Onchange(email)");
				}
			}

			if (typeOfMessage.equalsIgnoreCase("Fax")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Optional)
						&& faxColour.contains(Mandatory) && emailColour.contains(Optional)
						&& mNarrativeColour.contains(Mandatory)) {
					log("Type Of Message Onchange(Fax) : Pass");
				} else {
					log("Type Of Message Onchange(Fax) : Fail");
					takesnap("Type Of Message Onchange(Fax)");
				}
			}
		}

	}

	@Given("^Enter Reg Docs Bank ID$")
	public void enter_Reg_Docs_Bank_ID() throws Throwable {
		String bId = data.getCellValue("Scenario 1", 2, 336);
		if (!bId.isEmpty()) {
			WebElement eleBankId = locateElement("name", "SEND_TO_BANK_ID");
			append(eleBankId, bId);
			eleBankId.sendKeys(Keys.TAB);
			Thread.sleep(3000);
			MT707_DATA_MAP.put("REGDOC_ADV_RECE_CODE", getTypedText(locateElement("name", "SEND_TO_BK_SW_ADD")));
			MT707_DATA_MAP.put("REGDOC_ADV_RELD_REF", getTypedText(locateElement("name", "SEND_TO_BANK_REF")));
		}
	}

	@Given("^select Reg Docs Mail Method$")
	public void select_Reg_Docs_Mail_Method() throws Throwable {
		String mMethod = data.getCellValue("Scenario 1", 2, 338);
		if (!mMethod.isEmpty()) {
			WebElement eleMailMethod = locateElement("Name", "MAIL_MTHD_BANK");
			selectDropDownUsingText(eleMailMethod, mMethod);
		}
	}

	@Given("^Enter Reg Docs Narrative \\(MT n(\\d+) Tag (\\d+)Z\\)$")
	public void enter_Reg_Docs_Narrative_MT_n_Tag_Z(int arg1, int arg2) throws Throwable {
		String narrative = data.getCellValue("Scenario 1", 2, 339);
		if (!narrative.isEmpty()) {
			WebElement eleNarrative = locateElement("name", "BANK_NARR_TAG_79");
			lengthValidation2(eleNarrative, "35*50", narrative, "Narrative(MTn99Tag79Z) Length Check");
			eleNarrative.sendKeys(Keys.TAB);
			MT707_DATA_MAP.put("REGDOC_ADV_NARR_REF", getTypedText(locateElement("name", "BANK_NARR_TAG_79")));
		}
	}

	@Given("^Enter Reg Docs Narrative \\(Mail\\)$")
	public void enter_Reg_Docs_Narrative_Mail() throws Throwable {
		String mailNarrative = data.getCellValue("Scenario 1", 2, 340);
		if (!mailNarrative.isEmpty()) {
			WebElement eleNarrative = locateElement("name", "BANK_NARR_MAIL");
			append(eleNarrative, mailNarrative);

		}
	}

	@Given("^Click on Reg Docs customer subtab$")
	public void click_on_Reg_Docs_customer_subtab() throws Throwable {
		WebElement eleCustomer = locateElement("ID", "B");
		click(eleCustomer);
	}

	@Given("^select Reg Docs type of message and verify onchanges$")
	public void select_Reg_Docs_type_of_message_and_verify_onchanges() throws Throwable {
		String custTom = data.getCellValue("Scenario 1", 2, 341);
		if (!custTom.isEmpty()) {
			WebElement eleTom = locateElement("id", "MESG_TYPE_CUST");
			selectDropDownUsingText(eleTom, custTom);
			String custTomText = getTypedText(eleTom);
			String nameColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_NM"));
			String languageColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_LANG"));
			String emailColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_EMAIL"));
			String faxColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_FAX"));
			String narrColour = getBackgroundColor(locateElement("id", "CUST_NARR_TAG_79"));
			String mailColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_POST_ADD"));
			if (custTomText.equalsIgnoreCase("Fax")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Optional) && faxColour.contains(Mandatory)
						&& narrColour.contains(Mandatory) && mailColour.contains(Optional)) {
					log("Customer - Type Of Message Onchange(Fax) : Pass");
				} else {
					log("Customer - Type Of Message Onchange(Fax) : Fail");
					takesnap("Customer - Type Of Message Onchange(Fax)");
				}
			}

			if (custTomText.equalsIgnoreCase("Mail")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Optional) && faxColour.contains(Optional)
						&& narrColour.contains(Mandatory) && mailColour.contains(Mandatory)) {
					log("Customer - Type Of Message Onchange(Mail) : Pass");
				} else {
					log("Customer - Type Of Message Onchange(Mail) : Fail");
					takesnap("Customer - Type Of Message Onchange(Mail)");
				}
			}

			if (custTomText.equalsIgnoreCase("Email")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Mandatory) && faxColour.contains(Optional)
						&& narrColour.contains(Mandatory) && mailColour.contains(Optional)) {
					log("Customer - Type Of Message Onchange(Email) : Pass");
				} else {
					log("Customer - Type Of Message Onchange(Email) : Fail");
					takesnap("Customer - Type Of Message Onchange(Email)");
				}
			}
		}

	}

	@Given("^Enter Reg Docs Customer ID$")
	public void enter_Reg_Docs_Customer_ID() throws Throwable {
		String custId = data.getCellValue("Scenario 1", 2, 342);
		if (!custId.isEmpty()) {
			WebElement eleCustId = locateElement("name", "SEND_TO_CUST_ID");
			append(eleCustId, custId);
			eleCustId.sendKeys(Keys.TAB);

		}
	}

	@Given("^select Reg Docs Customer Mail Method$")
	public void select_Reg_Docs_Customer_Mail_Method() throws Throwable {
		String mMethod = data.getCellValue("Scenario 1", 2, 343);
		if (!mMethod.isEmpty()) {
			WebElement eleMailMethod = locateElement("Name", "MAIL_MTHD_CUST");
			selectDropDownUsingText(eleMailMethod, mMethod);
		}
	}

	@Given("^Enter Reg Docs narrative$")
	public void enter_Reg_Docs_narrative() throws Throwable {
		String custNarrative = data.getCellValue("Scenario 1", 2, 344);
		if (!custNarrative.isEmpty()) {
			WebElement eleCustId = locateElement("name", "CUST_NARR_TAG_79");
			append(eleCustId, custNarrative);
		}
	}

	@Given("^click on notes tab$")
	public void click_on_notes_tab() throws Throwable {
		defaultContent();
		switchToFrame("work");
		Thread.sleep(5000);
		WebElement elenotes = locateElement("id", "E");
		elenotes.click();

	}

	@And("^Verify notes details$")
	public void Verify_notes_details() throws Throwable {
		WebElement eleRnote = locateElement("id", ("NOTES"));
		String note1 = getTypedText(eleRnote);
		if (note1.equalsIgnoreCase(hash_map.get("Notes"))) {

			log("Verify Notes details: PASS");
		} else {
			log("Verify Notes details: FAIL");
			takesnap("Verify Notes details");
		}
	}

	@Given("^click on diary Tab$")
	public void click_on_diary_Tab() throws Throwable {
		defaultContent();
		switchToFrame("work");
		Thread.sleep(5000);
		WebElement eleDiary1 = locateElement("id", "F");
		eleDiary1.click();
	}

	@Given("^Enter Related reference$")
	public void Enter_Related_reference() throws Throwable {
		String rReference = hash_map.get("referenceNo");
		// System.out.println(rReference);
		WebElement eleReRef = locateElement("name", ("DIARY_RELATED_REF"));
		append(eleReRef, rReference);
		Thread.sleep(3000);
		if (getTypedText(eleReRef).length() <= 16) {
			log("Verify Related Reference Length: PASS");
		} else {
			log("Verify Related Reference Length: FAIL");
			takesnap("Verify Related Reference Length");
		}
	}

	@Given("^Enter Diary narrative$")
	public void Enter_Diary_narrative() throws Throwable {
		WebElement eleNarrative = locateElement("name", ("DIARY_NARRATIVE"));
		String narrative = data.getCellValue("Scenario 1", 2, 349);
		append(eleNarrative, narrative);
		eleNarrative.sendKeys(Keys.TAB);
	}

	@Given("^click Viewhistory button and verify the diary details$")
	public void click_Viewhistory_button_and_verify_the_diary_details() throws Throwable {
		WebElement eleviewhistory = locateElement("id", ("view_1"));
		eleviewhistory.click();
		Set<String> windowId = driver.getWindowHandles();
		Iterator<String> itererator = windowId.iterator();
		String mainWinID = itererator.next();
		String newAdwinID = itererator.next();
		driver.switchTo().window(newAdwinID);
		Thread.sleep(5000);
		WebElement eleReReference = locateElement("id", ("_id_0RELATED_REFERENCE"));
		String RegActualref1 = getTypedText(eleReReference);
//		String nRef1 = hash_map.get("RegExpectedRef1");
		WebElement elenNarrative = locateElement("id", ("DIARY_NARRATIVE"));
		String RegActualNarrative1 = getTypedText(elenNarrative);
//		String Rnarrative1 = hash_map.get("RegExpectedNarrative1");
		if (RegActualref1.equalsIgnoreCase(hash_map.get("RegExpectedRef1"))
				|| RegActualNarrative1.equalsIgnoreCase(hash_map.get("RegExpectedNarrative1"))) {

			log("Verify Diary details: PASS");
		} else {
			log("Verify Diary details: FAIL");
			takesnap("Verify Diary details");
		}
		Thread.sleep(5000);
		driver.switchTo().window(mainWinID);
		switchToFrame("work");
	}

	@Then("^validate the GAPI in RegDoc$")
	public void validate_the_gapi_in_RegDoc() throws Throwable {
		driver.switchTo().parentFrame();
		switchToFrame("eeToolbar");
		WebElement gapi = locateElement("id", "_pregapi");
		click(gapi);
		gapiValidation("Register Document LC");
		Thread.sleep(3000);
		driver.close();
		switchToWindow(1);
		driver.close();
		switchToWindow(0);
		driver.manage().window().maximize();

	}

	@Then("^Verify RegDoc GAPI is generated$")
	public void verify_RegDoc_GAPI_is_generated() throws Throwable {
		Thread.sleep(3000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		if (locateElement("name", "Gapi").isDisplayed()) {
			log("RegDoc_Output - Gapi: Pass");
		} else {
			log("RegDoc_Output - Gapi: Fail");
		}
	}

	@Then("^Verify RegDoc Swift is generated$")
	public void verify_RegDoc_swift_is_generated() throws Throwable {
		Thread.sleep(2000);
		if (locateElement("name", "Swift").isDisplayed()) {
			log("RegDoc_Output - Swift: PASS");
		} else {
			log("RegDoc_Output - Swift: FAIL");
		}
	}

	@Then("^validate the Swift in RegDoc$")
	public void validate_the_swift_in_regdoc() throws Throwable {
		if (locateElement("name", "Swift").isDisplayed()) {
			click(locateElement("name", "Swift"));
			Thread.sleep(6000);
			switchToWindow(1);
			driver.manage().window().maximize();
			WebElement eleview = locateElement("xpath", "(//a)[1]");
			click(eleview);
			Thread.sleep(6000);
			switchToWindow(2);
			driver.manage().window().maximize();
			swiftValidation("Register Document LC");
			switchToWindow(0);
		}
	}

	@Given("^Click on Check Document$")
	public void Click_on_Check_Document() throws Throwable {
		driver.switchTo().defaultContent();
		driver.switchTo().frame("FunctionList");
		WebElement eleCheckDoc = locateElement("Name", "G49082300273F05030701982");
		eleCheckDoc.click();
		Thread.sleep(2000);
		String RefNum = hash_map.get("referenceNo");
		Thread.sleep(2000);
		catalog(RefNum);

	}

	@Given("^Select Mail contains$")
	public void Select_Mail_contains() throws Throwable {
		Thread.sleep(1000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		Thread.sleep(1000);
		String mailcont = data.getCellValue("Scenario 1", 3, 365);
		if (!mailcont.isEmpty()) {
			WebElement eleMainCont = locateElement("css", "select#MAIL_CONT");
			selectDropDownUsingText(eleMainCont, mailcont);
			// isMandatory(eleMainCont, "Mail Contains");
			Thread.sleep(1000);
		}
	}

	@Given("^Select Document Status$")
	public void Select_Document_Status() throws Throwable {
		String docstat = data.getCellValue("Scenario 1", 3, 366);
		if (!docstat.isEmpty()) {
			WebElement eleDocStat = locateElement("id", "DOC_STAT");
			selectDropDownUsingText(eleDocStat, docstat);
			isMandatory(eleDocStat, "DocumentStatus");
			Thread.sleep(1000);
		}
	}

	@Given("^Enter Discrepancies Noted Field$")
	public void enter_Discrepancies_Noted_Field() throws Throwable {
		String desNoted = data.getCellValue("Scenario 1", 3, 371);
		WebElement eleDesNoted = locateElement("id", "DISC_DET");
		append(eleDesNoted, desNoted);
		ISSSWI_DATA_MAP.put("DiscrpcsNote", getTypedText(eleDesNoted));

	}

	@Given("^Click documents tab$")
	public void click_documents_tab() throws Throwable {
		WebElement docsTab = locateElement("id", "C");
		docsTab.click();

	}

	@Given("^click on notes tab in CheckDoc$")
	public void click_on_notes_tab_in_CheckDoc() throws Throwable {
		WebElement elenotes = locateElement("id", "E");
		elenotes.click();

	}

	@Given("^Verify notes details in CheckDoc$")
	public void verify_notes_details_in_CheckDoc() throws Throwable {
		WebElement eleRnote = locateElement("id", ("NOTES"));
		String note1 = getTypedText(eleRnote);
//		String rnote = hash_map.get("nNotes");
		// System.out.println("tester" + rnote);
		if (note1.equalsIgnoreCase(hash_map.get("Notes"))) {
			log("Verify Notes details: PASS");
		} else {
			log("Verify Notes details: FAIL");
			takesnap("Verify Notes details");
		}

	}

	@Given("^click on diary Tab in CheckDoc$")
	public void click_on_diary_Tab_in_CheckDoc() throws Throwable {
		WebElement eleDiary1 = locateElement("id", "F");
		eleDiary1.click();

	}

	@Given("^Enter Related reference in CheckDoc$")
	public void enter_Related_reference_in_CheckDoc() throws Throwable {
		String rReference = hash_map.get("referenceNo");
		// System.out.println(rReference);
		WebElement eleReRef = locateElement("name", ("DIARY_RELATED_REF"));
		append(eleReRef, rReference);
		/* Thread.sleep(3000); */
		if (getTypedText(eleReRef).length() <= 16) {
			log("Verify Related Reference Length: PASS");
		} else {
			log("Verify Related Reference Length: FAIL");
			takesnap("Verify Related Reference Length");
		}
	}

	@Given("^Enter Diary narrative in CheckDoc$")
	public void enter_Diary_narrative_in_CheckDoc() throws Throwable {
		WebElement eleNarrative = locateElement("name", ("DIARY_NARRATIVE"));
		String narrative = data.getCellValue("Scenario 1", 2, 376);
		append(eleNarrative, narrative);
		eleNarrative.sendKeys(Keys.TAB);
//			Actions actions = new Actions(driver);
		Thread.sleep(5000);
//			actions.moveToElement(eleNarrative).moveToElement(driver.findElementByXPath("//*[@id=\"F_div\"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td[3]")).click().build().perform();

	}

	/*
	 * @Given("^click Viewhistory button and verify the diary details in CheckDoc$")
	 * public void
	 * click_Viewhistory_button_and_verify_the_diary_details_in_CheckDoc() throws
	 * Throwable { WebElement eleviewhistory = locateElement("id", ("view_1"));
	 * eleviewhistory.click(); Set<String> windowId = driver.getWindowHandles();
	 * Iterator<String> itererator = windowId.iterator(); String mainWinID =
	 * itererator.next(); String newAdwinID = itererator.next();
	 * driver.switchTo().window(newAdwinID); System.out.println(driver.getTitle());
	 * Thread.sleep(5000); WebElement eleReReference = locateElement("id",
	 * ("_id_0RELATED_REFERENCE")); String RegActualref1 =
	 * getTypedText(eleReReference); String nRef1 = hash_map.get("RegExpectedRef1");
	 * System.out.println("test" + nRef1); WebElement elenNarrative =
	 * locateElement("id", ("DIARY_NARRATIVE")); String RegActualNarrative1 =
	 * getTypedText(elenNarrative); String Rnarrative1 =
	 * hash_map.get("RegExpectedNarrative1"); System.out.println("test" +
	 * Rnarrative1); if
	 * (RegActualref1.equalsIgnoreCase(hash_map.get("RegExpectedRef1")) ||
	 * RegActualNarrative1.equalsIgnoreCase( hash_map.get("RegExpectedNarrative1"))
	 * ) {
	 * 
	 * log("Verify Diary details: PASS"); } else {
	 * log("Verify Diary details: FAIL"); takesnap("Verify Diary details"); }
	 * Thread.sleep(5000); driver.switchTo().window(mainWinID);
	 * System.out.println(driver.getTitle()); switchToFrame("work");
	 * 
	 * }
	 */

	@Then("^validate the GAPI in CheckDoc$")
	public void validate_the_gapi_in_checkDoc() throws Throwable {
		driver.switchTo().parentFrame();
		switchToFrame("eeToolbar");
		WebElement gapi = locateElement("id", "_pregapi");
		click(gapi);
		gapiValidation("Check Document");
		Thread.sleep(3000);
		driver.close();
		switchToWindow(1);
		driver.close();
		switchToWindow(0);
		driver.manage().window().maximize();

	}

	@Then("^Then Verify CheckDoc GAPI is generated$")
	public void then_Verify_CheckDoc_GAPI_is_generated() throws Throwable {
		try {
			driver.switchTo().parentFrame();
			driver.switchTo().frame("work");
			if (locateElement("name", "Gapi").isDisplayed()) {
				log("Output : Gapi - Pass");
			} else {
				log("Output : Gapi - Fail");
			}
		} catch (Exception e) {

		}

	}

	@Given("^click on IPLC Discrepancies$")
	public void click_on_IPLC_Discrepancies() throws Throwable {
		driver.switchTo().defaultContent();
		driver.switchTo().frame("FunctionList");
		Thread.sleep(3000);
		WebElement eleIPLCDis = locateElement("Name", "IPLC Discrepancies");
		eleIPLCDis.click();
	}

	@And("^click on Register Discrepancies$")
	public void click_on_Register_Discrepancies() throws Throwable {
		WebElement eleRegDes = locateElement("Name", "G49082300322F05030701990");
		eleRegDes.click();
		Thread.sleep(3000);
		String RegDrefN = hash_map.get("referenceNo");
		Thread.sleep(3000);
		catalog(RegDrefN);
	}

	/*
	 * @And("^Select Mail Contains$") public void Select_Mail_Contains() throws
	 * Throwable { Thread.sleep(3000); driver.switchTo().parentFrame();
	 * driver.switchTo().frame("work"); Thread.sleep(2000); String MailCont =
	 * data.getCellValue("Scenario 1", 3, 377); if(!MailCont .isEmpty()) {
	 * WebElement elemailcont = locateElement("id", "MAIL_CONT");
	 * selectDropDownUsingText(elemailcont, MailCont);
	 * isMandatory(elemailcont,"Mail Contains"); Thread.sleep(3000); }
	 */
	@Given("^Verify LC CCY$")
	public void Verify_LC_CCY() throws Throwable {
		WebElement eleLCccy = locateElement("id", "LC_CCY");
		String LCCCY = getTypedText(eleLCccy);
		Discrepancies_DATA_MAP.put("RegDis33A_1", LCCCY);
	}

	@Given("^Verify LC Number$")
	public void Verify_LC_Number() throws Throwable {
		Thread.sleep(3000);
		defaultContent();
		switchToFrame("work");
		Thread.sleep(2000);
		WebElement elelcnum = locateElement("id", "C_MAIN_REF");
		String LCNo = getTypedText(elelcnum);
		Discrepancies_DATA_MAP.put("RegDis20", LCNo);

	}

	@Given("^Verify Presenter Reference$")
	public void Verify_Presenter_Reference() throws Throwable {
		WebElement elePreRef = locateElement("id", "PRES_BK_REF");
		String PreRef = getTypedText(elePreRef);
		Discrepancies_DATA_MAP.put("RegDis21", PreRef);

	}

	@Given("^Verify Presentation Date$")
	public void Verify_Presentation_Date() throws Throwable {
		WebElement elePreDate = locateElement("id", "PRES_DT");
		String Predate = getTypedText(elePreDate).replace("-", "").substring(2);
		Discrepancies_DATA_MAP.put("RegDis32A", Predate);

	}

	@Given("^Verify Presentation Currency and Amount$")
	public void Verify_Presentation_Currency_and_Amount() throws Throwable {
		WebElement elePreCcy = locateElement("id", "PRES_CCY");
		String Preccy = getTypedText(elePreCcy);
		Discrepancies_DATA_MAP.put("RegDis32A_1", Preccy);
		WebElement elePreAmt = locateElement("id", "PRES_AMT");
		String PreAmt = getTypedText(elePreAmt).replace(".00", " ").trim();
		Discrepancies_DATA_MAP.put("RegDis32A_2", PreAmt.replace(",", ""));

	}

	/*
	 * @Given("^Verify Presentation Amount$") public void
	 * Verify_Presentation_Amount() throws Throwable {
	 * 
	 * }
	 */
	@Given("^Verify Total amount to be paid$")
	public void Verify_Total_amount_to_be_paid() throws Throwable {
		WebElement eleTotalamt = locateElement("id", "TOTAL_AMT");
		String TotalAmt = getTypedText(eleTotalamt).replace(".00", " ").trim();
		Discrepancies_DATA_MAP.put("RegDis33A_2", TotalAmt.replace(",", ""));
	}

	@And("^click on Parties tab$")
	public void click_on_Parties_tab() throws Throwable {
		defaultContent();
		switchToFrame("work");
		Thread.sleep(5000);
		WebElement eleparties = locateElement("id", "B");
		eleparties.click();
	}

	@Given("^Select Documents Presented By$")
	public void select_Documents_Presented_By() throws Throwable {
		String DocPreby = data.getCellValue("Scenario 1", 3, 395);
		WebElement eledocpresby = locateElement("id", "DOC_PRES_BY");
		selectDropDownUsingText(eledocpresby, DocPreby);
		// Thread.sleep(3000);
	}

	@And("^Verify Presenter Bank Swift Address$")
	public void verify_Presenter_Bank_Swift_Addresss() throws Throwable {
		WebElement eleDocpresbyswiftAdd = locateElement("id", "PRES_BK_SW_ADD");
		String Swift = getTypedText(eleDocpresbyswiftAdd);
		Discrepancies_DATA_MAP.put("RegDisB2", Swift);

	}

	@And("^click on Discrepancies tab$")
	public void click_on_Discrepancies_tab() throws Throwable {
		/*
		 * defaultContent(); switchToFrame("work"); Thread.sleep(5000);
		 */
		WebElement eleDis = locateElement("id", "C");
		eleDis.click();

	}

	@And("^Select Advise Applicant$")
	public void Select_Advise_Applicant() throws Throwable {
		String AdvApp = data.getCellValue("Scenario 1", 3, 398);
		if (!AdvApp.isEmpty()) {
			WebElement eleadvapp = locateElement("id", "ADV_APPL_FLG");
			selectDropDownUsingText(eleadvapp, AdvApp);
			isMandatory(eleadvapp, "Advise Applicant");
			Thread.sleep(3000);
		}
	}

	@And("^Select Document Released in Trust$")
	public void Select_Document_Released_in_Trust() throws Throwable {
		String DocRel = data.getCellValue("Scenario 1", 3, 399);
		if (!DocRel.isEmpty()) {
			WebElement eleDocrel = locateElement("id", "REL_IN_TRUST_FLG");
			selectDropDownUsingText(eleDocrel, DocRel);
			isMandatory(eleDocrel, "Document Released in Trust");
			Thread.sleep(3000);
		}
	}

	@And("^Select Advise Presenter$")
	public void Select_Advise_Presenter() throws Throwable {
		String AdvPres = data.getCellValue("Scenario 1", 3, 400);
		if (!AdvPres.isEmpty()) {
			WebElement eleadvpre = locateElement("id", "ADV_PRES_BY");
			selectDropDownUsingText(eleadvpre, AdvPres);

		}
	}

	@And("^Enter Sender to Receiver information$")
	public void Enter_Sender_to_Receiver_Information() throws IOException, InterruptedException {
		String SendtoRe = data.getCellValue("Scenario 1", 3, 401);
		WebElement elesendtoRe = locateElement("id", ("SEND_TO_RCV_INFO"));
		append(elesendtoRe, SendtoRe);
	}

	@And("^Select Document Disposal$")
	public void Select_Document_Disposal() throws IOException, InterruptedException {
		String Docdisp = data.getCellValue("Scenario 1", 3, 402);
		if (!Docdisp.isEmpty()) {
			WebElement eleDocDis = locateElement("id", ("DOC_DISP_FLG"));
			selectDropDownUsingText(eleDocDis, Docdisp);
			String DocDis = getTypedText(eleDocDis);
			Discrepancies_DATA_MAP.put("RegDis77B", DocDis);

		}
	}

	@And("^Enter Charges Claimed$")
	public void Enter_Charges_Claimed() throws IOException, InterruptedException {
		String Chargeclaimed = data.getCellValue("Scenario 1", 3, 403);
		WebElement eleChargclaimd = locateElement("id", ("CHGS_CLAIMED_TXT"));
		append(eleChargclaimd, Chargeclaimed);
		String chargclaimed = getTypedText(eleChargclaimd);
		Discrepancies_DATA_MAP.put("RegDis73A", chargclaimed.toUpperCase());

	}

	@And("^Verify Discrepancies Noted$")
	public void Verify_Discrepancies_Noted() throws IOException, InterruptedException {
		// String Disnoted = data.getCellValue("Scenario 1", 3, 457);
		WebElement eleDisNoted = locateElement("id", ("DISC_DET"));
		// eleDisNoted.clear();
		// append(eleDisNoted,Disnoted);
		String DisNoted = getTypedText(eleDisNoted);
		Discrepancies_DATA_MAP.put("RegDis77J", DisNoted.toUpperCase());

	}

	@And("^Enter Account with Bank ID$")
	public void Enter_Account_with_Bank_ID() throws IOException, InterruptedException {
		String AccbankID = data.getCellValue("Scenario 1", 3, 404);
		WebElement eleAccBankID = locateElement("id", ("AC_WT_BK_ID_MT734"));
		append(eleAccBankID, AccbankID);
		eleAccBankID.sendKeys(Keys.TAB);

	}

	@Given("^Click on RegDis Charges Tab$")
	public void click_on_RegDis_Charges_Tab() throws Throwable {
		/*
		 * Thread.sleep(3000); defaultContent(); switchToFrame("work");
		 */
		WebElement elerdCharges = locateElement("id", "D");
		elerdCharges.click();
	}

	@Given("^Select Paid At$")
	public void select_Paid_At() throws Throwable {
		String rdpaidAt = data.getCellValue("Scenario 1", 2, 412).toUpperCase();
		WebElement elerdPaidAt = locateElement("id", "CHG_FLD_ALL_CHARGE_AT");
		selectDropDownUsingText(elerdPaidAt, rdpaidAt);
		Thread.sleep(3000);
	}

	@Given("^Enter the chrg Acc No$")
	public void enter_the_chrg_Acc_No() throws Throwable {
		String rdAccNo = data.getCellValue("Scenario 1", 2, 413);
		WebElement elerdAccno = locateElement("id", "CHG_FLD_LOCAL_CUST_AC_NO");
		manualEntry(elerdAccno, rdAccNo);
	}

	@Given("^Click on RegDis Advice tab$")
	public void click_on_RegDis_Advice_tab() throws Throwable {
		WebElement eleAdvice = locateElement("id", "E");
		click(eleAdvice);
	}

	@Given("^click RegDis add button and verify onchanges$")
	public void click_RegDis_add_button_and_verify_onchanges() throws Throwable {
		WebElement eleAdd = locateElement("id", "ext-gen91");
		click(eleAdd);
	}

	@Given("^select RegDis type of message$")
	public void select_RegDis_type_of_message() throws Throwable {
		String TOM = data.getCellValue("Scenario 1", 2, 417).replaceAll(" ", "");

		if (!TOM.isEmpty()) {
			switchToFrame("frame.AdivceForBankCust");
			WebElement eleTOM = locateElement("id", "MESG_TYPE_BANK");
			selectDropDownUsingText(eleTOM, TOM);
			String typeOfMessage = getTypedText(eleTOM);
			String swiftTagColour = getBackgroundColor(locateElement("name", "SEND_TO_BK_SW_ADD"));
			String rReferenceColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_REF"));
			String languageColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_LANG"));
			String mailColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_POST_ADD"));
			String faxColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_FAX"));
			String emailColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_EMAIL"));
			String sNarrativeColour = getBackgroundColor(locateElement("name", "BANK_NARR_TAG_79"));
			String mNarrativeColour = getBackgroundColor(locateElement("name", "BANK_NARR_MAIL"));
			String nameColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_NM"));

			if (typeOfMessage.equalsIgnoreCase("MT199") || typeOfMessage.equalsIgnoreCase("MT299")
					|| typeOfMessage.equalsIgnoreCase("MT799") || typeOfMessage.equalsIgnoreCase("MT999")) {
				if (swiftTagColour.contains(Mandatory) && rReferenceColour.contains(Mandatory)
						&& languageColour.contains(Mandatory) && sNarrativeColour.contains(Mandatory)
						&& mailColour.contains(Optional) && faxColour.contains(Optional)
						&& emailColour.contains(Optional) && mNarrativeColour.contains(Protected)) {
					log("Type Of Message Onchange(MT n99) : Pass");
				} else {
					log("Type Of Message Onchange(MT n99): Fail");
					takesnap("Type Of Message Onchange(MT n99)");
				}
			}
			if (typeOfMessage.equalsIgnoreCase("Mail")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Mandatory)
						&& faxColour.contains(Optional) && emailColour.contains(Optional)
						&& mNarrativeColour.contains(Mandatory)) {
					log("Type Of Message Onchange(Mail) : Pass");
				} else {
					log("Type Of Message Onchange(Mail) : Fail");
					takesnap("Type Of Message Onchange(Mail)");
				}
			}

			if (typeOfMessage.equalsIgnoreCase("Email")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Optional)
						&& faxColour.contains(Optional) && emailColour.contains(Mandatory)
						&& mNarrativeColour.contains(Mandatory)) {
					log("Type Of Message Onchange(email) : Pass");
				} else {
					log("Type Of Message Onchange(email) : Fail");
					takesnap("Type Of Message Onchange(email)");
				}
			}

			if (typeOfMessage.equalsIgnoreCase("Fax")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Optional)
						&& faxColour.contains(Mandatory) && emailColour.contains(Optional)
						&& mNarrativeColour.contains(Mandatory)) {
					log("Type Of Message Onchange(Fax) : Pass");
				} else {
					log("Type Of Message Onchange(Fax) : Fail");
					takesnap("Type Of Message Onchange(Fax)");
				}
			}
		}

	}

	@Given("^Enter RegDis Bank ID$")
	public void enter_RegDis_Bank_ID() throws Throwable {
		String bId = data.getCellValue("Scenario 1", 2, 418);
		if (!bId.isEmpty()) {
			WebElement eleBankId = locateElement("name", "SEND_TO_BANK_ID");
			append(eleBankId, bId);
			eleBankId.sendKeys(Keys.TAB);
			Thread.sleep(3000);
			MT707_DATA_MAP.put("REGDIS_ADV_RECE_CODE", getTypedText(locateElement("name", "SEND_TO_BK_SW_ADD")));
			MT707_DATA_MAP.put("REGDIS_ADV_RELD_REF", getTypedText(locateElement("name", "SEND_TO_BANK_REF")));
		}
	}

	@Given("^select RegDis Mail Method$")
	public void select_RegDis_Mail_Method() throws Throwable {
		String bId = data.getCellValue("Scenario 1", 2, 420);
		if (!bId.isEmpty()) {
			WebElement eleBankId = locateElement("name", "SEND_TO_BANK_ID");
			append(eleBankId, bId);
			eleBankId.sendKeys(Keys.TAB);
		}
	}

	@Given("^Enter RegDis Narrative \\(MT n(\\d+) Tag (\\d+)Z\\)$")
	public void enter_RegDis_Narrative_MT_n_Tag_Z(int arg1, int arg2) throws Throwable {
		String bId = data.getCellValue("Scenario 1", 2, 421);
		if (!bId.isEmpty()) {
			WebElement eleBankId = locateElement("name", "BANK_NARR_TAG_79");
			append(eleBankId, bId);
			eleBankId.sendKeys(Keys.TAB);
			MT707_DATA_MAP.put("REGDIS_ADV_NARR_REF", getTypedText(locateElement("name", "BANK_NARR_TAG_79")));
		}
	}

	@Given("^Enter RegDis Narrative \\(Mail\\)$")
	public void enter_RegDis_Narrative_Mail() throws Throwable {
		String bId = data.getCellValue("Scenario 1", 2, 422);
		if (!bId.isEmpty()) {
			WebElement eleBankId = locateElement("name", "SEND_TO_BANK_ID");
			append(eleBankId, bId);
			eleBankId.sendKeys(Keys.TAB);
		}
	}

	@Given("^Click on RegDis customer subtab$")
	public void click_on_RegDis_customer_subtab() throws Throwable {
		WebElement eleCustomer = locateElement("ID", "B");
		click(eleCustomer);
	}

	@Given("^select RegDis type of message and verify onchanges$")
	public void select_RegDis_type_of_message_and_verify_onchanges() throws Throwable {
		String custTom = data.getCellValue("Scenario 1", 2, 423);
		if (!custTom.isEmpty()) {
			WebElement eleTom = locateElement("id", "MESG_TYPE_CUST");
			selectDropDownUsingText(eleTom, custTom);
			String custTomText = getTypedText(eleTom);
			String nameColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_NM"));
			String languageColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_LANG"));
			String emailColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_EMAIL"));
			String faxColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_FAX"));
			String narrColour = getBackgroundColor(locateElement("id", "CUST_NARR_TAG_79"));
			String mailColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_POST_ADD"));
			if (custTomText.equalsIgnoreCase("Fax")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Optional) && faxColour.contains(Mandatory)
						&& narrColour.contains(Mandatory) && mailColour.contains(Optional)) {
					log("Customer - Type Of Message Onchange(Fax) : Pass");
				} else {
					log("Customer - Type Of Message Onchange(Fax) : Fail");
					takesnap("Customer - Type Of Message Onchange(Fax)");
				}
			}

			if (custTomText.equalsIgnoreCase("Mail")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Optional) && faxColour.contains(Optional)
						&& narrColour.contains(Mandatory) && mailColour.contains(Mandatory)) {
					log("Customer - Type Of Message Onchange(Mail) : Pass");
				} else {
					log("Customer - Type Of Message Onchange(Mail) : Fail");
					takesnap("Customer - Type Of Message Onchange(Mail)");
				}
			}

			if (custTomText.equalsIgnoreCase("Email")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Mandatory) && faxColour.contains(Optional)
						&& narrColour.contains(Mandatory) && mailColour.contains(Optional)) {
					log("Customer - Type Of Message Onchange(Email) : Pass");
				} else {
					log("Customer - Type Of Message Onchange(Email) : Fail");
					takesnap("Customer - Type Of Message Onchange(Email)");
				}
			}
		}
	}

	@Given("^select RegDis Customer Mail Method$")
	public void select_RegDis_Customer_Mail_Method() throws Throwable {
		String mMethod = data.getCellValue("Scenario 1", 2, 425);
		if (!mMethod.isEmpty()) {
			WebElement eleMailMethod = locateElement("Name", "MAIL_MTHD_CUST");
			selectDropDownUsingText(eleMailMethod, mMethod);
		}
	}

	@Given("^Enter RegDis Customer ID$")
	public void enter_RegDis_Customer_ID() throws Throwable {
		String custId = data.getCellValue("Scenario 1", 2, 424);
		if (!custId.isEmpty()) {
			WebElement eleCustId = locateElement("name", "SEND_TO_CUST_ID");
			append(eleCustId, custId);
			Thread.sleep(300);
			eleCustId.sendKeys(Keys.TAB);
			Thread.sleep(300);
		}
	}

	@Given("^Enter RegDis narrative$")
	public void enter_RegDis_narrative() throws Throwable {
		String custNarrative = data.getCellValue("Scenario 1", 2, 426);
		if (!custNarrative.isEmpty()) {
			WebElement eleCustNarr = locateElement("name", "CUST_NARR_TAG_79");
			append(eleCustNarr, custNarrative);

		}
	}

	@Given("^click on notes tab in RegDis$")
	public void click_on_notes_tab_in_RegDis() throws Throwable {
		/*
		 * defaultContent(); switchToFrame("work"); Thread.sleep(5000);
		 */
		WebElement eleRegDisNotes = locateElement("id", "F");
		eleRegDisNotes.click();
	}

	@Given("^Verify notes details in RegDis$")
	public void verify_notes_details_in_RegDis() throws Throwable {
		WebElement elenotes = locateElement("id", ("NOTES"));
		String note = getTypedText(elenotes);
		hash_map.put("RegDisNotes", note);
		System.out.println(hash_map.get("RegDisNotes"));
		// String Regnote = hash_map.get("RegDNotes");
		// System.out.println("tester" + Regnote);
		/*
		 * String Disnotes = getTypedText(elenotes); System.out.println(Disnotes);
		 * Thread.sleep(3000); hash_map.put("RegDisNotes", Disnotes);
		 * System.out.println(hash_map.get("RegDisNotes"));
		 */

		if (note.equalsIgnoreCase(hash_map.get("Notes"))) {

			log("Verify Notes details in RegDis:Pass");
		} else {
			log("Verify Notes details in RegDis:Fail");
			takesnap("Verify Notes details in RegDis");
		}
	}

	@Given("^click on diary Tab in RegDis$")
	public void click_on_diary_Tab_in_RegDis() throws Throwable {
		/*
		 * defaultContent(); switchToFrame("work"); Thread.sleep(5000);
		 */ WebElement eleRegDisDiary = locateElement("id", "G");
		eleRegDisDiary.click();

	}

	@Given("^Enter Related reference in RegDis$")
	public void Enter_Related_reference_in_RegDis() throws Throwable {
		String RRefRegDis = hash_map.get("referenceNo");
		WebElement eleReRef = locateElement("name", ("DIARY_RELATED_REF"));
		append(eleReRef, RRefRegDis);
		Thread.sleep(3000);
		if (getTypedText(eleReRef).length() <= 16) {
			log("Verify Related Reference Length in RegDis:Pass");
		} else {
			log("Verify Related Reference Length in RegDis:Fail");
			takesnap("Verify Related Reference Length in RegDis");
		}
	}

	@Given("^Enter Diary narrative in RegDis$")
	public void Enter_Diary_narrative_in_RegDis() throws Throwable {
		WebElement eleNarrative = locateElement("name", ("DIARY_NARRATIVE"));
		String narrative = data.getCellValue("Scenario 1", 2, 431);
		append(eleNarrative, narrative);
		eleNarrative.sendKeys(Keys.TAB);

	}

	/*
	 * @Given("^click Viewhistory button and verify the diary details in RegDis$")
	 * public void click_viewhistory_button_and_verify_the_diary_details_in_RegDis()
	 * throws Throwable { WebElement eleviewhistory = locateElement("id",
	 * ("view_1")); eleviewhistory.click(); Set<String> windowId =
	 * driver.getWindowHandles(); Iterator<String> itererator = windowId.iterator();
	 * String mainWinID = itererator.next(); String newAdwinID = itererator.next();
	 * driver.switchTo().window(newAdwinID); System.out.println(driver.getTitle());
	 * Thread.sleep(5000); WebElement eleReReference = locateElement("id",
	 * ("_id_0RELATED_REFERENCE")); String RegDisActualref =
	 * getTypedText(eleReReference); if
	 * (RegDisActualref.equalsIgnoreCase(hash_map.get("CheckDocExpecRef"))) {
	 * 
	 * log("Verify Diary details in RegDis:Pass"); } else {
	 * log("Verify Diary details in RegDis:Fail");
	 * takesnap("Verify Diary details in RegDis"); } Thread.sleep(5000);
	 * driver.switchTo().window(mainWinID); System.out.println(driver.getTitle());
	 * switchToFrame("work");
	 * 
	 * }
	 */

	@Then("^Verify RegDis Voucher is generated$")
	public void Verify_RegDis_voucher_is_generated() throws Throwable {
		try {
			driver.switchTo().parentFrame();
			driver.switchTo().frame("work");
			WebElement voucher = locateElement("name", "Voucher");
			String paidAt = data.getCellValue("Scenario 1", 2, 412).toUpperCase();
			if (paidAt.equalsIgnoreCase("TRANSACTION")) {
				if (voucher.isDisplayed()) {
					log("Output : Voucher - Pass");
				} else {
					log("Output : Voucher - Fail");

				}
			}
		} catch (Exception e) {

		}

	}

	@Then("^Verify RegDis Swift is generated$")
	public void Verify_RegDis_Swift_is_generate() throws Throwable {
		try {
			String send = data.getCellValue("Scenario 1", 3, 400);
			if (send.equalsIgnoreCase("MT734")) {
				WebElement swift = locateElement("name", "Swift");
				if (swift.isDisplayed()) {
					log("Output : Swift - Pass");
				} else {
					log("Output : Swift - Fail");
				}
			}
		} catch (Exception e) {

		}
	}

	@Then("^click on swift button$")
	public void click_on_swift_button() throws Throwable {
		Thread.sleep(3000);
		WebElement swift = locateElement("name", "Swift");

		if (swift.isDisplayed()) {
			click(swift);
			Thread.sleep(6000);
			switchToWindow(1);
			driver.manage().window().maximize();
			WebElement eleView = locateElement("xpath",
					"//*[@id=\"ext-gen6\"]/form/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/div/table/tbody/tr[4]/td/table/tbody/tr[2]/td[3]/a");
			click(eleView);
		}
	}

	@Then("^validate swift_MT(\\d+)$")
	public void validate_swift_MT(int arg1) throws Throwable {
		Thread.sleep(6000);
		switchToWindow(2);
		driver.manage().window().maximize();
		Thread.sleep(6000);
		WebElement table = driver.findElement(
				By.xpath("/html/body/table/tbody/tr[5]/td/table/tbody/tr[1]/td[2]/div/table[1]/tbody/tr[4]/td/table"));
		List<WebElement> column = table.findElements(By.tagName("td"));
		List<String> value = new ArrayList<String>();
		for (int j = 0; j < column.size(); j++) {
			value.add(column.get(j).getText());
			// System.out.println(column.get(j).getText());
		}
		// Outgoing SWIFT Header 1
		int headerIntex = value.indexOf("Outgoing SWIFT Header 1");
		// TagCheck
		String headerTag = value.get(headerIntex - 1);
		swiftTag(headerTag, "B1:", "MT734_Outgoing SWIFT Header 1");

		// Receiver's BIC Code
		int recBicIntex = value.indexOf("Receiver's BIC Code");
		// TagCheck
		String recBicTag = value.get(recBicIntex - 1);
		swiftTag(recBicTag, "B2:", "MT734_Receiver's BIC Code");
		// ContentCheck
		String recBicCon = value.get(recBicIntex + 2);
		String swiftBic = Discrepancies_DATA_MAP.get("RegDisB2");
//				System.out.println("Before = " + swiftBic);
		Thread.sleep(300);
		while (swiftBic.length() < 12) {
			Thread.sleep(300);
			swiftBic = swiftBic.concat("X");
		}
//				System.out.println("After = " + swiftBic);
		String ExpRecBicCon = "I734" + swiftBic + "N2";
//				System.out.println("Act - " + recBicCon + " | Exp" + ExpRecBicCon);
		swiftContent(ExpRecBicCon, recBicCon, "MT734_Receiver's BIC Code");

		// Senders TRN
		if (!Discrepancies_DATA_MAP.get("RegDis20").isEmpty()) {
			if (value.contains("Sender's TRN")) {
				log("Senders TRN check - Pass");
				int SendersTRNIntex = value.indexOf("Sender's TRN");
				// TagCheck
				String SendersTRNTag = value.get(SendersTRNIntex - 1);
				swiftTag(SendersTRNTag, "20:", "MT734_Sender's TRN");
				// ContentCheck
				String SendersTRNCon = value.get(SendersTRNIntex + 2);
				// System.out.println("Act - " + SendersTRNCon + " | Exp" +
				// Discrepacies_DATA_MAP.get("DisRes20"));
				swiftContent(Discrepancies_DATA_MAP.get("RegDis20"), SendersTRNCon, "MT734_Sender's TRN");
			} else {
				log("MT734_Senders TRN check - Fail");
			}
		}

		if (!Discrepancies_DATA_MAP.get("RegDis21").isEmpty()) {
			if (value.contains("Presenting Bank's Reference")) {
				log("Presenter Reference check - Pass");
				int PreRefIntex = value.indexOf("Presenting Bank's Reference");
				// TagCheck
				String PreRefTag = value.get(PreRefIntex - 1);
				swiftTag(PreRefTag, "21:", "MT734_Presenting Bank's Reference");
				// ContentCheck
				String PreRefCon = value.get(PreRefIntex + 2);
				// System.out.println("Act - " + PreRefCon + " | Exp" +
				// Discrepacies_DATA_MAP.get("DisRes21"));
				swiftContent(Discrepancies_DATA_MAP.get("RegDis21"), PreRefCon, "MT734_Presenting Bank's Reference");
			} else {
				log("MT734_Presenter Reference check - Fail");
			}
		}

		// Presentation Date, Amount
		if (value.contains("Date and Amount of Utilization")) {
			log("Presentation Date, Amount check - Pass");
			int PreDateIntex = value.indexOf("Date and Amount of Utilization");
			// TagCheck
			String PreDateTag = value.get(PreDateIntex - 1);
			swiftTag(PreDateTag, "32A:", "MT734_Date and Amount of Utilization");
			// ContentCheck
			String PreDateCont = value.get(PreDateIntex + 2);
			String expPreDateCont = Discrepancies_DATA_MAP.get("RegDis32A") + Discrepancies_DATA_MAP.get("RegDis32A_1")
					+ Discrepancies_DATA_MAP.get("RegDis32A_2");
			// System.out.println("Act - " + PreDateCont + " | Exp" +
			// expPreDateCont.concat(",00"));
			swiftContent(expPreDateCont.concat(",00"), PreDateCont, "MT734_Date and Amount of Utilization");
		} else {
			log("MT734_Presentation Date, Amount check - Fail");
		}

		// Discrepancies Noted
		if (!Discrepancies_DATA_MAP.get("RegDis77J").isEmpty()) {
			if (value.contains("Discrepancies")) {
				log("Discrepancies Noted check - Pass");
				int DisNotedIntex = value.indexOf("Discrepancies");
				// TagCheck
				String DisNotedTag = value.get(DisNotedIntex - 1);
				swiftTag(DisNotedTag, "77J:", "MT734_Discrepancies Noted");
				// ContentCheck
				String DisnotedCon = value.get(DisNotedIntex + 2);
				// System.out.println("Act - " + DisnotedCon + " | Exp" +
				// Discrepacies_DATA_MAP.get("DisRes77J"));
				swiftContent(Discrepancies_DATA_MAP.get("RegDis77J"), DisnotedCon, "MT734_Discrepancies Noted");
			} else {
				log("MT734_Discrepancies Notedcheck - Fail");
			}
		}

		// Document Disposal
		if (!Discrepancies_DATA_MAP.get("RegDis77B").isEmpty()) {
			if (value.contains("Disposal of Documents")) {
				log("Document Disposal check - Pass");
				int DocDispIntex = value.indexOf("Disposal of Documents");
				// TagCheck
				String DocDisTag = value.get(DocDispIntex - 1);
				swiftTag(DocDisTag, "77B:", "MT734_Document Disposal");
				// ContentCheck
				String DocDisCon = value.get(DocDispIntex + 2);
				// System.out.println("Act - " + DocDisCon + " | Exp" +
				// Discrepacies_DATA_MAP.get("DisRes77B"));
				swiftContent(Discrepancies_DATA_MAP.get("RegDis77B"), DocDisCon, "MT734_Document Disposal");
			} else {
				log("MT734_Document Disposal check - Fail");
			}
		}
		driver.close();
	}

	@And("^validate the Advice Swift in RegDis$")
	public void validate_the_advice_swift_in_regdis() throws Throwable {
//		WebElement swift = locateElement("name", "Swift");
//		if (swift.isDisplayed()) {
//			click(swift);
//			Thread.sleep(6000);
//			switchToWindow(1);

		switchToWindow(1);
		Thread.sleep(5000);
		driver.manage().window().maximize();
		WebElement eleview = locateElement("xpath", "(//a)[2]");
		click(eleview);
		Thread.sleep(6000);
		switchToWindow(2);
		driver.manage().window().maximize();
		swiftValidation("Register Discrepancies");
		switchToWindow(0);
	}
//	}

	@Given("^click on Discrepancy Response MT752$")
	public void click_on_Discrepancy_Response_MT752() throws Throwable {
		driver.switchTo().defaultContent();
		driver.switchTo().frame("FunctionList");
		Thread.sleep(3000);
		WebElement eleRDisRes = locateElement("Name", "G49082300322F05030701991");
		eleRDisRes.click();
		Thread.sleep(3000);
		String DisResN = hash_map.get("referenceNo");
		Thread.sleep(3000);
		catalog(DisResN);
	}

	@Given("^Select Applicant Response$")
	public void select_Applicant_Response() throws Throwable {
		Thread.sleep(3000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		Thread.sleep(2000);
		String appRes = data.getCellValue("Scenario 1", 3, 445);
		if (!appRes.isEmpty()) {
			WebElement eleappRes = locateElement("id", "APPL_RESP_DISC");
			/* Thread.sleep(2000); */
			selectDropDownUsingText(eleappRes, appRes);
		}
	}

	@Given("^Select Further Identification$")
	public void select_Further_Identification() throws Throwable {
		String furId = data.getCellValue("Scenario 1", 3, 446);
		if (!furId.isEmpty()) {
			WebElement elefurId = locateElement("id", "X752_FUR_IDEN_23");
			selectDropDownUsingText(elefurId, furId);
		}
	}

	@Given("^Select Date of Advise of Discrepancy$")
	public void select_Date_of_Advise_of_Discrepancy() throws Throwable {

		String advDisDate = data.getCellValue("Scenario 1", 3, 447);
		WebElement eleadvDisDate = locateElement("ID", "ADV_DIS_DT");
		/* dateCheck(eleadvDisDate, advDisDate, "Advise Discrepancy Date"); */
		append(eleadvDisDate, advDisDate);

	}

	@And("^click on Discrepancies tab in DisRes$")
	public void click_on_Discrepancies_tab_in_DisRes() throws Throwable {

		// defaultContent(); switchToFrame("work"); Thread.sleep(5000);

		WebElement eleDis = locateElement("id", "C");
		eleDis.click();
	}

	@And("^Select Document disposal in DisRes$")
	public void Select_Document_disposal() throws IOException, InterruptedException {
		String DocDisp = data.getCellValue("Scenario 1", 3, 451);
		WebElement eleDocDis = locateElement("id", ("DOC_DISP_FLG"));
		selectDropDownUsingText(eleDocDis, DocDisp);
	}

	@Given("^click on notes tab in DisResp$")
	public void click_on_notes_tab_in_DisResp() throws Throwable {
		// defaultContent(); switchToFrame("work"); Thread.sleep(5000);
		WebElement eleDesResNotes = locateElement("id", "D");
		eleDesResNotes.click();

	}

	@Given("^Verify notes details in DisResp$")
	public void verify_notes_details_in_DisResp() throws Throwable {
		WebElement elenotes = locateElement("id", ("NOTES"));
		String note = getTypedText(elenotes);

		if (note.equalsIgnoreCase(hash_map.get("RegDisNotes"))) {

			log("Verify Notes details in DisRes:Pass");
		} else {
			log("Verify Notes details in DisRes:Fail");
			takesnap("Verify Notes details in DisRes");
		}
	}

	@Given("^click on diary Tab in DisRes$")
	public void click_on_diary_Tab_in_DisRes() throws Throwable {
		// defaultContent(); switchToFrame("work"); Thread.sleep(5000);
		WebElement eleRegDisDiary = locateElement("id", "E");
		eleRegDisDiary.click();

	}

	@Given("^Enter Related reference in DisRes$")
	public void enter_Related_reference_in_DisRes() throws Throwable {
		String RRefRegDis = hash_map.get("referenceNo");
		WebElement eleReRef = locateElement("name", ("DIARY_RELATED_REF"));
		append(eleReRef, RRefRegDis);
		/* Thread.sleep(3000); */
		String Attribute1 = getTypedText(eleReRef);
		hash_map.put("DisResRef", Attribute1);
		if (getTypedText(eleReRef).length() <= 16) {
			log("Verify Related Reference Length in RegDis:Pass");
		} else {
			log("Verify Related Reference Length in RegDis:Fail");
			takesnap("Verify Related Reference Length in RegDis");
		}
	}

	@Given("^Enter Diary narrative in DisRes$")
	public void enter_Diary_narrative_in_DisRes() throws Throwable {
		WebElement eleNarrative = locateElement("name", ("DIARY_NARRATIVE"));
		String narrative = data.getCellValue("Scenario 1", 2, 464);
		append(eleNarrative, narrative);
		eleNarrative.sendKeys(Keys.TAB);
	}

	/*
	 * @Given("^click Viewhistory button and verify the diary details in DisRes$")
	 * public void click_Viewhistory_button_and_verify_the_diary_details_in_DisRes()
	 * throws Throwable { WebElement eleviewhistory = locateElement("id",
	 * ("view_1")); eleviewhistory.click(); Set<String> windowId =
	 * driver.getWindowHandles(); Iterator<String> itererator = windowId.iterator();
	 * String mainWinID = itererator.next(); String newAdwinID = itererator.next();
	 * driver.switchTo().window(newAdwinID); System.out.println(driver.getTitle());
	 * Thread.sleep(5000); WebElement eleReReference = locateElement("id",
	 * ("_id_0RELATED_REFERENCE")); String RegDisActualref =
	 * getTypedText(eleReReference); if
	 * (RegDisActualref.equalsIgnoreCase(hash_map.get("DisResRef"))) {
	 * log("Verify Diary details in DisRes:Pass"); } else {
	 * log("Verify Diary details in DisRes:Fail");
	 * takesnap("Verify Diary details in DisRes"); } Thread.sleep(5000);
	 * driver.switchTo().window(mainWinID); System.out.println(driver.getTitle());
	 * switchToFrame("work");
	 * 
	 * }
	 */

	@Then("^Verify DisRes Swift is generated$")
	public void verify_DisRes_Swift_is_generated() throws Throwable {
		try {
			String send = data.getCellValue("Scenario 1", 3, 449);
			if (send.equalsIgnoreCase("MT734")) {
				WebElement swift = locateElement("name", "Swift");
				if (swift.isDisplayed()) {
					log("Output : Swift - Pass");
				} else {
					log("Output : Swift - Fail");
				}
			}
		} catch (Exception e) {

		}

	}

	@Then("^click on swift button in DisRes$")
	public void click_on_swift_button_in_disRes() throws Throwable {
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		if (locateElement("name", "Swift").isDisplayed()) {
			click(locateElement("name", "Swift"));
			Thread.sleep(6000);
			switchToWindow(1);
			driver.manage().window().maximize();
			WebElement eleView = locateElement("xpath",
					"//*[@id=\"ext-gen6\"]/form/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/div/table/tbody/tr[4]/td/table/tbody/tr[2]/td[3]/a");
			click(eleView);
			Thread.sleep(6000);
			switchToWindow(2);
			driver.manage().window().maximize();
			Thread.sleep(6000);
			WebElement table = driver.findElement(By.xpath(
					"/html/body/table/tbody/tr[5]/td/table/tbody/tr[1]/td[2]/div/table[1]/tbody/tr[4]/td/table"));
			List<WebElement> column = table.findElements(By.tagName("td"));
			List<String> value = new ArrayList<String>();
			for (int j = 0; j < column.size(); j++) {
				value.add(column.get(j).getText());
				// System.out.println(column.get(j).getText());
			}

//Outgoing SWIFT Header 1
			int OutSwiftheaderIntex = value.indexOf("Outgoing SWIFT Header 1");
			// TagCheck
			String OutSwiftheaderTag = value.get(OutSwiftheaderIntex - 1);
			swiftTag(OutSwiftheaderTag, "B1:", "MT734_Outgoing SWIFT Header 1");

//Receiver's BIC Code
			int recBiccodeIntex = value.indexOf("Receiver's BIC Code");
			// TagCheck
			String recBiccodeTag = value.get(recBiccodeIntex - 1);
			swiftTag(recBiccodeTag, "B2:", "MT734_Receiver's BIC Code");
			// ContentCheck
			String recBicCon = value.get(recBiccodeIntex + 2);
			String swiftBic = Discrepancies_DATA_MAP.get("RegDisB2");
			System.out.println("Before = " + swiftBic);
			Thread.sleep(300);

			while (swiftBic.length() < 12) {
				Thread.sleep(300);
				swiftBic = swiftBic.concat("X");
			}
			System.out.println("After = " + swiftBic);
			String ExpRecBicCon = "I734" + swiftBic + "N2";
			System.out.println("Act - " + recBicCon + " | Exp" + ExpRecBicCon);
			swiftContent(ExpRecBicCon, recBicCon, "MT734_Receiver's BIC Code");

			// Senders TRN
			if (!Discrepancies_DATA_MAP.get("RegDis20").isEmpty()) {
				if (value.contains("Sender's TRN")) {
					log("Senders TRN check - Pass");
					int SendersTRNIntex = value.indexOf("Sender's TRN");
					// TagCheck
					String SendersTRNTag = value.get(SendersTRNIntex - 1);
					swiftTag(SendersTRNTag, "20:", "MT734_Sender's TRN");
					// ContentCheck
					String SendersTRNCon = value.get(SendersTRNIntex + 2);
					// System.out.println("Act - " + SendersTRNCon + " | Exp" +
					// Discrepacies_DATA_MAP.get("DisRes20"));
					swiftContent(Discrepancies_DATA_MAP.get("RegDis20"), SendersTRNCon, "MT734_Sender's TRN");
				} else {
					log("MT734_Senders TRN check - Fail");
				}
			}

			// Presenter Reference
			if (!Discrepancies_DATA_MAP.get("RegDis21").isEmpty()) {
				if (value.contains("Presenting Bank's Reference")) {
					log("Presenter Reference check - Pass");
					int PreRefIntex = value.indexOf("Presenting Bank's Reference");
					// TagCheck
					String PreRefTag = value.get(PreRefIntex - 1);
					swiftTag(PreRefTag, "21:", "MT734_Presenting Bank's Reference");
					// ContentCheck
					String PreRefCon = value.get(PreRefIntex + 2);
					// System.out.println("Act - " + PreRefCon + " | Exp" +
					// Discrepacies_DATA_MAP.get("DisRes21"));
					swiftContent(Discrepancies_DATA_MAP.get("RegDis21"), PreRefCon,
							"MT734_Presenting Bank's Reference");
				} else {
					log("MT734_Presenter Reference check - Fail");
				}
			}

			// Presentation Date, Amount
			if (value.contains("Date and Amount of Utilization")) {
				log("Presentation Date, Amount check - Pass");
				int PreDateIntex = value.indexOf("Date and Amount of Utilization");
				// TagCheck
				String PreDateTag = value.get(PreDateIntex - 1);
				swiftTag(PreDateTag, "32A:", "MT734_Date and Amount of Utilization");
				// ContentCheck
				String PreDateCont = value.get(PreDateIntex + 2);
				String expPreDateCont = Discrepancies_DATA_MAP.get("RegDis32A")
						+ Discrepancies_DATA_MAP.get("RegDis32A_1") + Discrepancies_DATA_MAP.get("RegDis32A_2");
				// System.out.println("Act - " + PreDateCont + " | Exp" +
				// expPreDateCont.concat(",00"));
				swiftContent(expPreDateCont.concat(",00"), PreDateCont, "MT734_Date and Amount of Utilization");
			} else {
				log("MT734_Presentation Date, Amount check - Fail");
			}

			// Discrepancies Noted
			if (!Discrepancies_DATA_MAP.get("RegDis77J").isEmpty()) {
				if (value.contains("Discrepancies")) {
					log("Discrepancies Noted check - Pass");
					int DisNotedIntex = value.indexOf("Discrepancies");
					// TagCheck
					String DisNotedTag = value.get(DisNotedIntex - 1);
					swiftTag(DisNotedTag, "77J:", "MT734_Discrepancies Noted");
					// ContentCheck
					String DisnotedCon = value.get(DisNotedIntex + 2);
					// System.out.println("Act - " + DisnotedCon + " | Exp" +
					// Discrepacies_DATA_MAP.get("DisRes77J"));
					swiftContent(Discrepancies_DATA_MAP.get("RegDis77J"), DisnotedCon, "MT734_Discrepancies Noted");
				} else {
					log("MT734_Discrepancies Notedcheck - Fail");
				}
			}

			// Document Disposal
			if (!Discrepancies_DATA_MAP.get("DisRes77B").isEmpty()) {
				if (value.contains("Disposal of Documents")) {
					log("Document Disposal check - Pass");
					int DocDispIntex = value.indexOf("Disposal of Documents");
					// TagCheck
					String DocDisTag = value.get(DocDispIntex - 1);
					swiftTag(DocDisTag, "77B:", "MT734_Document Disposal");
					// ContentCheck
					String DocDisCon = value.get(DocDispIntex + 2);
					// System.out.println("Act - " + DocDisCon + " | Exp" +
					// Discrepacies_DATA_MAP.get("DisRes77B"));
					swiftContent(Discrepancies_DATA_MAP.get("DisRes77B"), DocDisCon, "MT734_Document Disposal");
				} else {
					log("MT734_Document Disposal check - Fail");
				}
			}

			// Charges Claimed
			if (!Discrepancies_DATA_MAP.get("RegDis73A").isEmpty()) {
				if (value.contains("Charges Claimed")) {
					log("Charges Claimed check - Pass");
					int chargclaimedIntex = value.indexOf("Charges Claimed");
					// TagCheck
					String ChargClaimdTag = value.get(chargclaimedIntex - 1);
					swiftTag(ChargClaimdTag, "73A:", "MT734_Charges Claimed");
					// ContentCheck
					String ChrgclmdCon = value.get(chargclaimedIntex + 2).replaceAll("\n", "").replaceAll(" ", "");
					// System.out.println("Act - " + ChrgclmdCon + " | Exp" + Expchargclimd );
					String Expchargclimd = Discrepancies_DATA_MAP.get("RegDis73A").replaceAll("\n", "").replaceAll(" ",
							"");
					swiftContent(Expchargclimd, ChrgclmdCon, "MT734_Charges Claimed");
				} else {
					log("MT734_Charges Claimed check - Fail");
				}
			}
			// Total Amount Claimed
			if (value.contains("Total Amount Claimed")) {
				log("Total Amount Claimed check - Pass");
				int ToatalamtIntex = value.indexOf("Total Amount Claimed");
				// TagCheck
				String TotalamtTag = value.get(ToatalamtIntex - 1);
				swiftTag(TotalamtTag, "33A:", "MT734_Total Amount Claimed");
				// ContentCheck
				String TotalamtCont = value.get(ToatalamtIntex + 2);
				String expTotalamtCont = Discrepancies_DATA_MAP.get("RegDis32A")
						+ Discrepancies_DATA_MAP.get("RegDis33A_1") + Discrepancies_DATA_MAP.get("RegDis33A_2");
				// System.out.println("Act - " + TotalamtCont + " | Exp" +
				// expTotalamtCont.concat(",00"));
				swiftContent(expTotalamtCont.concat(",00"), TotalamtCont, "MT734_Total Amount Claimed");
			} else {
				log("MT734_Total Amount Claimed check - Fail");
			}
		}
	}
	/*
	 * //Sender to Receiver Information if
	 * (!Discrepancies_DATA_MAP.get("DisRes73A").isEmpty()) { if
	 * (value.contains("Charges Claimed")) {
	 * log("Sender to Receiver Information check - Pass"); int chargclaimedIntex =
	 * value.indexOf("Charges Claimed"); // TagCheck String ChargClaimdTag =
	 * value.get(chargclaimedIntex - 1); swiftTag(ChargClaimdTag, "73A:",
	 * "MT734_Sender to Receiver Information "); // ContentCheck String ChrgclmdCon
	 * = value.get(chargclaimedIntex + 2).replaceAll("\n", "").replaceAll(" ", "");
	 * //System.out.println("Act - " + ChrgclmdCon + " | Exp" + Expchargclimd );
	 * String Expchargclimd =
	 * Discrepancies_DATA_MAP.get("DisRes73A").replaceAll("\n", "").replaceAll(" ",
	 * ""); swiftContent(Expchargclimd, ChrgclmdCon,
	 * "MT734_Sender to Receiver Information "); } else {
	 * log("MT734_Sender to Receiver Information check - Fail"); } }
	 */

	@Given("^click on IPLC settlement$")
	public void click_on_IPLC_Settlement() throws Throwable {
		driver.switchTo().defaultContent();
		driver.switchTo().frame("FunctionList");
		WebElement eleIPLCSettle = locateElement("Name", "IPLC Settlement");
		eleIPLCSettle.click();
	}

	@Given("^Click on Pay/Accept function$")
	public void click_on_Pay_Accept_function() throws Throwable {
		WebElement elePayAcpt = locateElement("Name", "G49082300323F05030703386");
		elePayAcpt.click();
		Thread.sleep(3000);
		String PayrefNO = hash_map.get("referenceNo");
		Thread.sleep(3000);
		catalog(PayrefNO);
	}

	@Given("^Select PayAcc Tenor Start Date$")
	public void select_Tenor_Start_Date() throws Throwable {
		Thread.sleep(2000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		Thread.sleep(2000);
		WebElement elePresCcy = locateElement("name", "PRES_CCY");
		String presCcy = getTypedText(elePresCcy);
		hash_map.put("PresCcy", presCcy);
		WebElement elePresAmt = locateElement("name", "PRES_AMT");
		String preAmt = getTypedText(elePresAmt);
		hash_map.put("PresAmt", preAmt);
		String tenorStartDays = data.getCellValue("Scenario 1", 3, 488);
		WebElement eleTenorStartDays = locateElement("id", "TENOR_START_DT");
		if (!tenorStartDays.isEmpty()) {
			eleTenorStartDays.click();
			eleTenorStartDays.clear();
			append(eleTenorStartDays, tenorStartDays);
		}
	}

	@Given("^Enter PayAcc Advising Bank Charges$")
	public void enter_Advising_Bank_Charges() throws Throwable {
		String advBnkChrg = data.getCellValue("Scenario 1", 3, 483);
		WebElement eleadvBnkChrg = locateElement("id", "ADV_BK_CHGS");
		eleadvBnkChrg.clear();
		eleadvBnkChrg.click();
		append(eleadvBnkChrg, advBnkChrg);
		eleadvBnkChrg.sendKeys(Keys.TAB);
	}

	@Given("^Enter PayAccReimbursement Bank Charges$")
	public void enter_Reimbursement_Bank_Charges() throws Throwable {
		String reimBnkChrg = data.getCellValue("Scenario 1", 3, 484);
		WebElement elereimBnkChrg = locateElement("id", "REIM_BK_CHG");
		elereimBnkChrg.clear();
		elereimBnkChrg.click();
		append(elereimBnkChrg, reimBnkChrg);
		elereimBnkChrg.sendKeys(Keys.TAB);

	}

	@Given("^Click on Acceptance tab$")
	public void click_on_Aceptance_tab() throws Throwable {
		/*
		 * Thread.sleep(2000); driver.switchTo().parentFrame();
		 * driver.switchTo().frame("work"); Thread.sleep(2000);
		 */ WebElement eleAccept = locateElement("id", "C");
		eleAccept.click();

	}

	@Given("^verify Acceptance tab is displayed$")
	public void verify_Aceptance_tab_is_displayed() throws Throwable {

		/*
		 * WebElement eleAcctab = locateElement("id", "AVAL_BY"); String Availableby =
		 * getTypedText(eleAcctab);
		 */
		if (hash_map.get("AvailableBy").equalsIgnoreCase(("By Acceptance"))
				|| hash_map.get("AvailableBy").equalsIgnoreCase("By Negotiation")
				|| hash_map.get("AvailableBy").equalsIgnoreCase("By Def Payment")
				|| hash_map.get("AvailableBy").equalsIgnoreCase("BY MIXED PYMT")) {
			// if (hash_map.get("AvailableBy")||(!Availableby.isDisplayed())) {
			log("Verify Acceptance tab is displayed: PASS");
		} else {
			log("Verify Acceptance tab is displayed: FAIL");
			takesnap("Verify Acceptance tab is displayed");

		}
		if (hash_map.get("AvailableBy").equalsIgnoreCase("By Payment")) {
			log("Verify Acceptance tab is displayed: FAIL");
			takesnap("Verify Acceptance tab is displayed");

		} else {
			log("Verify Acceptance tab is displayed: PASS");

		}
	}

	@Given("^Select acceptance Msg$")
	public void Select_Acceptance_Msg() throws Throwable {
		String AcceptMsg = data.getCellValue("Scenario 1", 3, 491);
		if (!AcceptMsg.isEmpty()) {
			WebElement eleacceptmsg = locateElement("name", "ACPT_MSG");
			selectDropDownUsingText(eleacceptmsg, AcceptMsg);
			isMandatory(eleacceptmsg, "Acceptance Msg");
		}
	}

	@And("^Click On PayAcc Charges tab$")
	public void click_on_PayAcc_charges_tab() throws Throwable {
		WebElement eleCharges = locateElement("id", "G");
		click(eleCharges);
	}

	@And("^Select PayAcc Paid By$")
	public void Select_PayAcc_Paid_By() throws Throwable {
		String paidBy = data.getCellValue("Scenario 1", 3, 497);
		WebElement elePaidBy = locateElement("id", "CHG_FLD_ALL_CHARGE_FOR");
		selectDropDownUsingText(elePaidBy, paidBy);
	}

	@And("^Select PayAcc Paid At$")
	public void select_the_PayAcc_Paid_At() throws Throwable {
		String paidAt = data.getCellValue("Scenario 1", 3, 498).toUpperCase();
		WebElement elePaidAt = locateElement("id", "CHG_FLD_ALL_CHARGE_AT");
		selectDropDownUsingText(elePaidAt, paidAt);
	}

	@And("^Enter PayAcc Account Number$")
	public void enter_PayAcc_Ac_No() throws Throwable {
		String acNo = data.getCellValue("Scenario 1", 3, 499);
		WebElement eleAcNo = locateElement("id", "CHG_FLD_LOCAL_CUST_AC_NO");
		append(eleAcNo, acNo);
	}

	@And("^Click On Payment tab$")
	public void click_on_payment_tab() throws Throwable {
		WebElement elePayment = locateElement("id", "D");
		click(elePayment);
	}

	@And("^Check the Payment details$")
	public void check_the_payment_details() throws Throwable {
		WebElement elePayPresCcy = locateElement("name", "CPYT_C_TRX_CCY");
		String actPayPresCcy = getTypedText(elePayPresCcy);
		WebElement eleTotalAmount = locateElement("name", "CPYT_N_PAY_TTL_AMT_TXCCY");
		String actTotalAmount = getTypedText(eleTotalAmount);
		WebElement eleMixPayment = locateElement("name", "CPYT_C_MIX_PAY_DETAIL");
		String mixPayment = getTypedText(eleMixPayment);
		// Transaction Currency Ccy
		if (actPayPresCcy.equalsIgnoreCase(hash_map.get("PresCcy"))) {
			log("Transaction Currency field value Check: PASS");
		} else {
			log("Transaction Currency field value Check: FAIL");
			takesnap("Transaction Currency field value check(Pay/Acc)");
		}
		// Total Amount (Trx CCY)
		if (actTotalAmount.equalsIgnoreCase(hash_map.get("PresAmt"))) {
			log("Total Amount (Trx CCY) field value Check: PASS");
		} else {
			log("Total Amount (Trx CCY) field value Check: FAIL");
			takesnap("Total Amount (Trx CCY) field value check(Pay/Acc)");
		}
		// Mixed Payment Details
		switch (hash_map.get("AvailableBy").toUpperCase()) {
		case "BY ACCEPTANCE":
			String expMixedPaymentDetails = "100 PEC AT " + hash_map.get("Draft At");
			if (mixPayment.equalsIgnoreCase(expMixedPaymentDetails)) {
				log("Mixed Payment Details fielld value Check: PASS");
			} else {
				log("Mixed Payment Details fielld value Check: FAIL");
				takesnap("Mixed Payment Details fielld value check(Pay/Acc)");
			}
			break;
		}
	}

	@Given("^Click on Pay/Accept Advice tab$")
	public void Click_on_Pay_Accept_Advice_tab() throws Throwable {
		WebElement eleAdvice = locateElement("id", "K");
		click(eleAdvice);
	}

	@Given("^click Pay/Accept add button and verify onchanges$")
	public void click_Pay_Accept_add_button_and_verify_onchanges() throws Throwable {
		WebElement eleAdd = locateElement("id", "ext-gen91");
		click(eleAdd);
	}

	@Given("^select Pay/Accept type of message$")
	public void select_Pay_Accept_type_of_message() throws Throwable {
		String TOM = data.getCellValue("Scenario 1", 3, 503).replaceAll(" ", "");

		if (!TOM.isEmpty()) {
			switchToFrame("frame.AdivceForBankCust");
			WebElement eleTOM = locateElement("id", "MESG_TYPE_BANK");
			selectDropDownUsingText(eleTOM, TOM);
			String typeOfMessage = getTypedText(eleTOM);
			String swiftTagColour = getBackgroundColor(locateElement("name", "SEND_TO_BK_SW_ADD"));
			String rReferenceColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_REF"));
			String languageColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_LANG"));
			String mailColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_POST_ADD"));
			String faxColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_FAX"));
			String emailColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_EMAIL"));
			String sNarrativeColour = getBackgroundColor(locateElement("name", "BANK_NARR_TAG_79"));
			String mNarrativeColour = getBackgroundColor(locateElement("name", "BANK_NARR_MAIL"));
			String nameColour = getBackgroundColor(locateElement("name", "SEND_TO_BANK_NM"));

			if (typeOfMessage.equalsIgnoreCase("MT199") || typeOfMessage.equalsIgnoreCase("MT299")
					|| typeOfMessage.equalsIgnoreCase("MT799") || typeOfMessage.equalsIgnoreCase("MT999")) {
				if (swiftTagColour.contains(Mandatory) && rReferenceColour.contains(Mandatory)
						&& languageColour.contains(Mandatory) && sNarrativeColour.contains(Mandatory)
						&& mailColour.contains(Optional) && faxColour.contains(Optional)
						&& emailColour.contains(Optional) && mNarrativeColour.contains(Protected)) {
					log("Type Of Message Onchange(MT n99) : Pass");
				} else {
					log("Type Of Message Onchange(MT n99): Fail");
					takesnap("Type Of Message Onchange(MT n99)");
				}
			}
			if (typeOfMessage.equalsIgnoreCase("Mail")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Mandatory)
						&& faxColour.contains(Optional) && emailColour.contains(Optional)
						&& mNarrativeColour.contains(Mandatory)) {
					log("Type Of Message Onchange(Mail) : Pass");
				} else {
					log("Type Of Message Onchange(Mail) : Fail");
					takesnap("Type Of Message Onchange(Mail)");
				}
			}

			if (typeOfMessage.equalsIgnoreCase("Email")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Optional)
						&& faxColour.contains(Optional) && emailColour.contains(Mandatory)
						&& mNarrativeColour.contains(Mandatory)) {
					log("Type Of Message Onchange(email) : Pass");
				} else {
					log("Type Of Message Onchange(email) : Fail");
					takesnap("Type Of Message Onchange(email)");
				}
			}

			if (typeOfMessage.equalsIgnoreCase("Fax")) {
				if (nameColour.contains(Mandatory) && swiftTagColour.contains(Optional)
						&& rReferenceColour.contains(Optional) && languageColour.contains(Mandatory)
						&& sNarrativeColour.contains(Protected) && mailColour.contains(Optional)
						&& faxColour.contains(Mandatory) && emailColour.contains(Optional)
						&& mNarrativeColour.contains(Mandatory)) {
					log("Type Of Message Onchange(Fax) : Pass");
				} else {
					log("Type Of Message Onchange(Fax) : Fail");
					takesnap("Type Of Message Onchange(Fax)");
				}
			}
		}

	}

	@Given("^Enter Pay/Accept Bank ID$")
	public void enter_Pay_Accept_Bank_ID() throws Throwable {
		String bId = data.getCellValue("Scenario 1", 3, 504);
		if (!bId.isEmpty()) {
			WebElement eleBankId = locateElement("name", "SEND_TO_BANK_ID");
			append(eleBankId, bId);
			eleBankId.sendKeys(Keys.TAB);
			Thread.sleep(3000);
			MT707_DATA_MAP.put("PAYACC_ADV_RECE_CODE", getTypedText(locateElement("name", "SEND_TO_BK_SW_ADD")));
			MT707_DATA_MAP.put("PAYACC_ADV_RELD_REF", getTypedText(locateElement("name", "SEND_TO_BANK_REF")));
		}
	}

	@Given("^select Pay/Accept Mail Method$")
	public void select_Pay_Accept_Mail_Method() throws Throwable {
		String MMethod = data.getCellValue("Scenario 1", 3, 506);
		if (!MMethod.isEmpty()) {
			WebElement eleMailmethod = locateElement("Name", "MAIL_MTHD_BANK");
			selectDropDownUsingText(eleMailmethod, MMethod);
		}
	}

	@Given("^Enter Pay/Accept Narrative \\(MT n(\\d+) Tag (\\d+)Z\\)$")
	public void enter_Pay_Accept_Narrative_MT_n_Tag_Z(int arg1, int arg2) throws Throwable {
		String bId = data.getCellValue("Scenario 1", 3, 507);
		if (!bId.isEmpty()) {
			WebElement eleBankId = locateElement("name", "BANK_NARR_TAG_79");
			append(eleBankId, bId);
			eleBankId.sendKeys(Keys.TAB);
			MT707_DATA_MAP.put("PAYACC_ADV_SEND_REF", getTypedText(locateElement("name", "BANK_NARR_TAG_79")));
		}
	}

	@Given("^Enter Pay/Accept Narrative \\(Mail\\)$")
	public void enter_Pay_Accept_Narrative_Mail() throws Throwable {
		String bId = data.getCellValue("Scenario 1", 3, 508);
		if (!bId.isEmpty()) {
			WebElement eleBankId = locateElement("name", "SEND_TO_BANK_ID");
			append(eleBankId, bId);
			eleBankId.sendKeys(Keys.TAB);
		}
	}

	@Given("^Click on Pay/Accept customer subtab$")
	public void click_on_Pay_Accept_customer_subtab() throws Throwable {
		WebElement eleCustomer = locateElement("ID", "B");
		click(eleCustomer);
	}

	@Given("^select Pay/Accept type of message and verify onchanges$")
	public void select_Pay_Accept_type_of_message_and_verify_onchanges() throws Throwable {
		String custTom = data.getCellValue("Scenario 1", 3, 509);
		if (!custTom.isEmpty()) {
			WebElement eleTom = locateElement("id", "MESG_TYPE_CUST");
			selectDropDownUsingText(eleTom, custTom);
			String custTomText = getTypedText(eleTom);
			String nameColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_NM"));
			String languageColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_LANG"));
			String emailColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_EMAIL"));
			String faxColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_FAX"));
			String narrColour = getBackgroundColor(locateElement("id", "CUST_NARR_TAG_79"));
			String mailColour = getBackgroundColor(locateElement("id", "SEND_TO_CUST_POST_ADD"));
			if (custTomText.equalsIgnoreCase("Fax")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Optional) && faxColour.contains(Mandatory)
						&& narrColour.contains(Mandatory) && mailColour.contains(Optional)) {
					log("Customer - Type Of Message Onchange(Fax) : Pass");
				} else {
					log("Customer - Type Of Message Onchange(Fax) : Fail");
					takesnap("Customer - Type Of Message Onchange(Fax)");
				}
			}

			if (custTomText.equalsIgnoreCase("Mail")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Optional) && faxColour.contains(Optional)
						&& narrColour.contains(Mandatory) && mailColour.contains(Mandatory)) {
					log("Customer - Type Of Message Onchange(Mail) : Pass");
				} else {
					log("Customer - Type Of Message Onchange(Mail) : Fail");
					takesnap("Customer - Type Of Message Onchange(Mail)");
				}
			}

			if (custTomText.equalsIgnoreCase("Email")) {
				if (nameColour.contains(Mandatory) && languageColour.contains(Mandatory)
						&& emailColour.contains(Mandatory) && faxColour.contains(Optional)
						&& narrColour.contains(Mandatory) && mailColour.contains(Optional)) {
					log("Customer - Type Of Message Onchange(Email) : Pass");
				} else {
					log("Customer - Type Of Message Onchange(Email) : Fail");
					takesnap("Customer - Type Of Message Onchange(Email)");
				}
			}
		}
	}

	@Given("^select Pay/Accept Customer Mail Method$")
	public void select_Pay_Accept_Customer_Mail_Method() throws Throwable {
		String mMethod = data.getCellValue("Scenario 1", 3, 511);
		if (!mMethod.isEmpty()) {
			WebElement eleMailMethod = locateElement("Name", "MAIL_MTHD_CUST");
			selectDropDownUsingText(eleMailMethod, mMethod);
		}
	}

	@Given("^Enter Pay/Accept Customer ID$")
	public void enter_Pay_Accept_Customer_ID() throws Throwable {
		String custId = data.getCellValue("Scenario 1", 3, 510);
		if (!custId.isEmpty()) {
			WebElement eleCustId = locateElement("name", "SEND_TO_CUST_ID");
			append(eleCustId, custId);
			Thread.sleep(300);
			eleCustId.sendKeys(Keys.TAB);
			Thread.sleep(300);
		}
	}

	@Given("^Enter Pay/Accept narrative$")
	public void enter_Pay_Accept_narrative() throws Throwable {
		String custNarrative = data.getCellValue("Scenario 1", 3, 512);
		if (!custNarrative.isEmpty()) {
			WebElement eleCustNarr = locateElement("name", "CUST_NARR_TAG_79");
			append(eleCustNarr, custNarrative);
		}
	}

	@Given("^click on notes tab in PayAccept$")
	public void click_on_notes_tab_in_PayAccept() throws Throwable {
		WebElement elePayAcceptNotes = locateElement("id", "I");
		elePayAcceptNotes.click();

	}

	@Given("^Verify notes details in PayAccept$")
	public void verify_notes_details_in_PayAccept() throws Throwable {
		WebElement elenotes = locateElement("id", ("NOTES"));
		String note = getTypedText(elenotes);
		hash_map.put("PayAcceptNotes", note);
		if (note.equalsIgnoreCase(hash_map.get("RegDisNotes"))) {

			log("Verify Notes details in PayAccept: PASS");
		} else {
			log("Verify Notes details in PayAccept: FAIL");
			takesnap("Verify Notes details in PayAccept");
		}
	}

	@Given("^click on diary Tab in PayAccept$")
	public void click_on_diary_Tab_in_PayAccept() throws Throwable {
		WebElement elePayAcceptDiary = locateElement("id", "J");
		elePayAcceptDiary.click();

	}

	@Given("^Enter Related reference in PayAccept$")
	public void Enter_Related_reference_in_PayAccept() throws Throwable {
		String RRefPayAccept = hash_map.get("referenceNo");
		WebElement eleReRef = locateElement("name", ("DIARY_RELATED_REF"));
		append(eleReRef, RRefPayAccept);
		if (getTypedText(eleReRef).length() <= 16) {
			log("Verify Related Reference Length in PayAccept: PASS");
		} else {
			log("Verify Related Reference Length in PayAccept: FAIL");
			takesnap("Verify Related Reference Length in PayAccept");
		}
	}

	@Given("^Enter Diary Narrative in PayAccept$")
	public void Enter_Diary_narrative_in_PayAccept() throws Throwable {
		WebElement eleNarrative = locateElement("name", ("DIARY_NARRATIVE"));
		String narrative = data.getCellValue("Scenario 1", 3, 517);
		append(eleNarrative, narrative);
		eleNarrative.sendKeys(Keys.TAB);
	}

	@Then("^Verify Swift is generated in Payacc$")
	public void verify_swift_is_generated_in_payacc() throws Throwable {
		Thread.sleep(2000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		Thread.sleep(2000);
		WebElement swift = locateElement("name", "Swift");
		if (swift.isDisplayed()) {
			log("PayAcc_Output - Swift: PASS");
		} else {
			log("PayAcc_Output - Swift: FAIL");
		}
	}

	@Then("^validate the Advice Swift in payacc$")
	public void validate_the_advice_swift_in_payacc() throws Throwable {
		WebElement swift = locateElement("name", "Swift");
		if (swift.isDisplayed()) {
			click(swift);
			Thread.sleep(6000);
			switchToWindow(1);
			driver.manage().window().maximize();
			WebElement eleview = locateElement("xpath", "(//a)[1]");
			click(eleview);
			Thread.sleep(6000);
			switchToWindow(2);
			driver.manage().window().maximize();
			swiftValidation("Pay/Accept");
			switchToWindow(0);
		}
	}

	@Given("^click on Payment at Maturity$")
	public void click_on_Payment_at_Maturity() throws Throwable {
		driver.switchTo().defaultContent();
		driver.switchTo().frame("FunctionList");
		WebElement elePayatMat = locateElement("Name", "G49082300323F05030701994");
		elePayatMat.click();
		String PayrefNum = hash_map.get("referenceNo");
		catalog(PayrefNum);
	}

	@Given("^Select Take charges separately$")
	public void Select_Take_charges_separately() throws Throwable {
		Thread.sleep(2000);
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		Thread.sleep(2000);
		String Takechsep = data.getCellValue("Scenario 1", 3, 531);
		WebElement eleTakecharsep = locateElement("id", "SEPARATE_CHG_FLG");
		selectDropDownUsingText(eleTakecharsep, Takechsep);
		WebElement eledebitAmount = locateElement("name", "TTL_DR_AMT");
		String debitAmount = getTypedText(eledebitAmount);
		hash_map.put("ExpDebitAmount", debitAmount);

	}

	@Given("^Verify LC number$")
	public void Verify_LC_number() throws Throwable {
		WebElement eleLCNum = locateElement("id", "C_MAIN_REF");
		String LCNum = getTypedText(eleLCNum);
		Settlement_DATA_MAP.put("Pay20", LCNum);

	}

	@Given("^Verify Total amount claimed$")
	public void Verify_Total_amount_claimed() throws Throwable {
		WebElement eleSetamt = locateElement("id", "STL_AMT");
		String setAmt = getTypedText(eleSetamt);
		double ExpecSettamt = convertToDouble(setAmt);
		WebElement eleAddAmt = locateElement("id", "ADDIT_PRES_BK_AMTS");
		String addamt = getTypedText(eleAddAmt);
		double Expecaddamt = convertToDouble(addamt);
		WebElement eleChargeDec = locateElement("id", "CHGS_DEDUCTED");
		String chargedec = getTypedText(eleChargeDec);
		double Expecchargedec = convertToDouble(chargedec);
		WebElement elePresCharges = locateElement("id", "PRES_BK_CHGS");
		String prescharge = getTypedText(elePresCharges);
		double Expecprescharge = convertToDouble(prescharge);
		WebElement eleAdvBCharg = locateElement("id", "ADV_BK_CHGS");
		String advBchrg = getTypedText(eleAdvBCharg);
		double ExpecAdvBCharg = convertToDouble(advBchrg);
		WebElement eleReimBcharg = locateElement("id", "REIM_BK_CHG");
		String Reimbchrg = getTypedText(eleReimBcharg);
		double ExpecReimBCharg = convertToDouble(Reimbchrg);
		double ExpecTotal = (ExpecSettamt + Expecaddamt - Expecchargedec + Expecprescharge + ExpecAdvBCharg
				+ ExpecReimBCharg);
		WebElement eleTotalAmt = locateElement("id", "TOTAL_AMT");
		String Totalamt = getTypedText(eleTotalAmt);
		double ActualTotalamt = convertToDouble(Totalamt);
		if (ExpecTotal == ActualTotalamt) {
			log("Verify Total amount claimed:Pass");

		} else {
			log("Verify Total amount claimed:Fail");
			takesnap("Verify Total amount claimed");

		}
	}

	@Given("^Verify Debit amount$")
	public void verify_Debit_amount() throws Throwable {
		WebElement eleTakecharsep = locateElement("id", "SEPARATE_CHG_FLG");
		String Takechgsep = getTypedText(eleTakecharsep);
		if (Takechgsep.equalsIgnoreCase("No")) {
			// Thread.sleep(3000);
			WebElement eleTotalAmt = locateElement("id", "TOTAL_AMT");
			String Totalamt = getTypedText(eleTotalAmt);
			double ExpecTotalamt = convertToDouble(Totalamt);
			WebElement eleOurcharApp = locateElement("id", "OUR_CHGS_APPL");
			String OurChrgFApp = getTypedText(eleOurcharApp);
			double ExpecOurChrgFApp = convertToDouble(OurChrgFApp);
			double ExpecTotal = (ExpecTotalamt + ExpecOurChrgFApp);
			WebElement eleDebitAmt = locateElement("id", "TTL_DR_AMT");
			String DebitAmt = getTypedText(eleDebitAmt);
			double ActualDebitAmt = convertToDouble(DebitAmt);
			if (ExpecTotal == ActualDebitAmt) {
				log("Verify Debit amount with charges:Pass");

			} else {
				log("Verify Debit amount with charges:Fail");
				takesnap("Verify Debit amount with charges");

			}
		}

		if (Takechgsep.equalsIgnoreCase("Yes")) {
			WebElement eleTotalamt = locateElement("id", "TOTAL_AMT");
			String TotalAmt = getTypedText(eleTotalamt);
			double ExpecTotalAmt = convertToDouble(TotalAmt);
			WebElement eleOurCharApp = locateElement("id", "OUR_CHGS_APPL");
			String OurChrgFapp = getTypedText(eleOurCharApp);
			double ExpectOurChrgFApp = convertToDouble(OurChrgFapp);
			double ExpectTotal = (ExpecTotalAmt + ExpectOurChrgFApp);
			WebElement eleDebitamt = locateElement("id", "TTL_DR_AMT");
			String Debitamt = getTypedText(eleDebitamt);
			double ActualdebitAmt = convertToDouble(Debitamt);
			if (ExpectTotal == ActualdebitAmt) {
				log("Verify Debit amount without charges:Pass");

			} else {
				log("Verify Debit amount without charges:Fail");
				takesnap("Verify Debit amount without charges");

			}
		}
	}

	@Given("^Verify Credit amount$")
	public void Verify_Credit_amount() throws Throwable {
		// Thread.sleep(3000);
		WebElement eleNetAmtPaid = locateElement("id", "NET_AMT");
		String netAmtPaid = getTypedText(eleNetAmtPaid);
		double ExpecNetamtpaid = convertToDouble(netAmtPaid);
		WebElement eleBenCharg = locateElement("id", "OUR_CHGS_BENE");
		String BenCharg = getTypedText(eleBenCharg);
		double ExpecBenCharg = convertToDouble(BenCharg);
		double ExpecTotal = (ExpecNetamtpaid - ExpecBenCharg);
		WebElement eleCreditAmt = locateElement("id", "TTL_CR_AMT");
		String CreditAmt = getTypedText(eleCreditAmt);
		double ActualCreditAmt = convertToDouble(CreditAmt);
		if (ExpecTotal == ActualCreditAmt) {
			log("Verify Credit amount:Pass");

		} else {
			log("Verify Credit amount:Fail");
			takesnap("Verify Credit amount");

		}

	}

	@Given("^Pay at Maturity Charges Tab validation$")
	public void pay_at_Maturity_Charges_Tab_validation() throws Throwable {
		/*
		 * driver.switchTo().parentFrame(); switchToFrame("work"); Thread.sleep(3000);
		 */
		WebElement eletkchrsep = locateElement("id", "SEPARATE_CHG_FLG");
		String tkChrgSep = getTypedText(eletkchrsep);
		hash_map.put("tkchrgsep", tkChrgSep);
		if (tkChrgSep == hash_map.get(tkChrgSep)) {
			// Click On PayAcc Charges tab
			WebElement eleCharges = locateElement("id", "G");
			click(eleCharges);
			// Select PayMat Paid By
			String paidBy = data.getCellValue("Scenario 1", 3, 548);
			WebElement elePaidBy = locateElement("id", "CHG_FLD_ALL_CHARGE_FOR");
			selectDropDownUsingText(elePaidBy, paidBy);
			// Select PayMat Paid At
			String paidAt = data.getCellValue("Scenario 1", 3, 549).toUpperCase();
			WebElement elePaidAt = locateElement("id", "CHG_FLD_ALL_CHARGE_AT");
			selectDropDownUsingText(elePaidAt, paidAt);
			// Enter PayMat Account Number
			String acNo = data.getCellValue("Scenario 1", 3, 550);
			WebElement eleAcNo = locateElement("id", "CHG_FLD_LOCAL_CUST_AC_NO");
			append(eleAcNo, acNo);
		} else {
			log("Charges tab is not enabled ");
		}
	}

	@When("^Click On Payment tab at Payment at Maturity$")
	public void click_on_payment_tab_at_Payment_at_maturity_function() throws Throwable {
		WebElement elePaymentTab = locateElement("id", "D");
		click(elePaymentTab);

	}

	@Then("^Verify the debit details$")
	public void verify_the_payment_details() throws Throwable {
		// click debit
		click(locateElement("id", "do_PaymentDebitHeader_Tab"));
		WebElement eleTotalCcyAmt = locateElement("name", "CPYT_DR_TTL_AMT_TTLCCY");
		String actTotalCcyAmount = getTypedText(eleTotalCcyAmt);
		hash_map.put("actTotalCcyAmount", actTotalCcyAmount);
		if (hash_map.get("ExpDebitAmount").equalsIgnoreCase(actTotalCcyAmount)) {
			log("Total Debit CCY/Amt field value check(PayAtMat) : Pass");
		} else {
			log("Total Debit CCY/Amt field value check(PayAtMat) : Fail");
			takesnap("Total Debit CCY/Amt field value check(PayAtMat)");
		}
	}

	@When("^Add the Debit Entry$")
	public void add_the_debit_entry() throws Throwable {
		click(locateElement("id", "PaymentDebit_ADD"));
	}

	@Then("^Enter Debit Percentage and verify onchange$")
	public void enter_debit_percentage() throws Throwable {
		try {
			String percentage = data.getCellValue("Scenario 1", 3, 551);
			if (!percentage.isEmpty()) {
				WebElement elePercentge = locateElement("name", "CPYT_DR_PER");
				elePercentge.sendKeys(Keys.CONTROL + "a");
				elePercentge.sendKeys(Keys.DELETE);
				elePercentge.sendKeys(percentage);
				double typedPercentage = convertToDouble(getTypedText(elePercentge));
				double totalDebitAmount = convertToDouble(hash_map.get("actTotalCcyAmount"));
				double expCcyAmt = totalDebitAmount * (typedPercentage / 100);
				double actCcyAmt = convertToDouble(getTypedText(locateElement("name", "CPYT_DR_AMT_DRCCY")));
				if (actCcyAmt == expCcyAmt) {
					log("Debit Amount Percentage Onchgange in Payment At Maturity : Pass");
				} else {
					log("Debit Amount Percentage Onchgange in Payment At Maturity : Fail");
					takesnap("Debit Amount Percentage Onchgange in Payment At Maturity");
				}
			}
		} catch (Exception e) {

		}

	}

	@And("^Enter the DebitCcy$")
	public void enter_the_debitCcy() throws Throwable {
		String ccy = data.getCellValue("Scenario 1", 3, 552).substring(0, 3);
		WebElement eleCcy = locateElement("name", "CPYT_DR_CCY");
		selectDropDownUsingText(eleCcy, ccy);
	}

	@Then("^Enter the CcyAmount$")
	public void enter_the_CcyAmount_and_verify_onchanges() throws Throwable {
		String ccyAmount = data.getCellValue("Scenario 1", 3, 552).substring(4);
		WebElement eleCcyAmount = locateElement("name", "CPYT_DR_AMT_DRCCY");
		eleCcyAmount.sendKeys(Keys.CONTROL.SHIFT.ARROW_RIGHT);
		eleCcyAmount.sendKeys(ccyAmount);
		eleCcyAmount.sendKeys(Keys.TAB);

	}

	@Then("^Verify Debit Amount in Trx CCY$")
	public void enter_the_percentage_and_verify_onchanges() throws Throwable {
		String ccyAndAmount = getTypedText(locateElement("name", "CPYT_DR_AMT_DRCCY"));
		double ccyAndAmtDouble = convertToDouble(ccyAndAmount);
		WebElement eleExRate = locateElement("name", "CPYT_DR_BUY_RATE");
		String typedText = getTypedText(eleExRate);
		String replace = typedText.replace(".000000", "");
		double exRateDouble = Double.parseDouble(replace);
		WebElement eleAmtTrxccy = locateElement("name", "CPYT_DR_AMT_TXCCY");
		double actAmountInTrxCcy = convertToDouble(getTypedText(eleAmtTrxccy));
		double expAmountInTrxCcy = ccyAndAmtDouble / exRateDouble;
		if (expAmountInTrxCcy == actAmountInTrxCcy) {
			log("Amount in Trx CCY field value check(Debit)(PayAtMat) : Pass");
		} else {
			log("Amount in Trx CCY field value check(Debit)(PayAtMat) : Fail");
			takesnap("Amount in Trx CCY field value check(Debit)(PayAtMat)");
		}
	}

	@And("^Enter the Debit Value Date$")
	public void enter_the_debit_value_date() throws Throwable {
		String ccyAmount = data.getCellValue("Scenario 1", 3, 556);
		WebElement eleDebitValueDate = locateElement("name", "CPYT_DR_VAL_DATE");
		append(eleDebitValueDate, ccyAmount);
	}

	@And("^Enter the Account Type$")
	public void enter_the_account_Type() throws Throwable {
		String accType = data.getCellValue("Scenario 1", 3, 557).toUpperCase();
		WebElement eleAccType = locateElement("name", "CPYT_DR_AC_TYPE");
		selectDropDownUsingText(eleAccType, accType);
		WebElement eleAccId = locateElement("name", "CPYT_DR_ID");
		String accId = data.getCellValue("Scenario 1", 3, 558);
		append(eleAccId, accId);
		eleAccId.sendKeys(Keys.TAB);
	}

	@And("^Click Save button$")
	public void click_save_button() throws Throwable {
		WebElement eleSave = locateElement("id", "PaymentDebit_SAVE");
		click(eleSave);
	}

	@When("^Click Credit subtab$")
	public void click_Credit_subtab() throws Throwable {
		WebElement eleCredit = locateElement("id", "do_PaymentCreditHeader_Tab");
		click(eleCredit);
	}

	@And("^Click the Add button$")
	public void click_the_add_button() throws Throwable {
		String totalCcyAmount = getTypedText(locateElement("name", "CPYT_CR_TTL_AMT_TTLCCY"));
		hash_map.put("totalCreditAmount", totalCcyAmount);
		WebElement eleCredit = locateElement("id", "PaymentCredit_ADD");
		click(eleCredit);
	}

	@Then("^Enter Credit Percentage and verify onchange$")
	public void verify_credit_percentage_onchanges() throws Throwable {
		try {
			String percentage = data.getCellValue("Scenario 1", 3, 561);
			if (!percentage.isEmpty()) {
				WebElement elePercentge = locateElement("name", "CPYT_CR_PER");
				elePercentge.sendKeys(Keys.CONTROL + "a");
				elePercentge.sendKeys(Keys.DELETE);
				elePercentge.sendKeys(percentage);
				double typedPercentage = convertToDouble(getTypedText(elePercentge));
				double totalCreditAmount = convertToDouble(hash_map.get("totalCreditAmount"));
				double expCcyAmt = totalCreditAmount * (typedPercentage / 100);
				double actCcyAmt = convertToDouble(getTypedText(locateElement("name", "CPYT_CR_AMT_CRCCY")));
				if (actCcyAmt == expCcyAmt) {
					log("Credit Amount Percentage Onchgange in Payment At Maturity : Pass");
				} else {
					log("Credit Amount Percentage Onchgange in Payment At Maturity : Fail");
					takesnap("Credit Amount Percentage Onchgange in Payment At Maturity");
				}
			}
		} catch (Exception e) {

		}

	}

	@And("^Enter the CreditCcy$")
	public void enter_the_creditCcy() throws Throwable {
		String ccy = data.getCellValue("Scenario 1", 3, 562).substring(0, 3);
		WebElement eleCcy = locateElement("name", "CPYT_CR_CCY");
		selectDropDownUsingText(eleCcy, ccy);
		String Creditccy = getTypedText(eleCcy);
		Settlement_DATA_MAP.put("Pay32A_2", Creditccy);

	}

	@Then("^Enter the Credit CcyAmount$")
	public void enter_the_Credit_CcyAmount() throws Throwable {
		String ccyAmount = data.getCellValue("Scenario 1", 3, 562).substring(4);
		WebElement eleCcyAmount = locateElement("name", "CPYT_CR_AMT_CRCCY");
		eleCcyAmount.sendKeys(Keys.CONTROL.SHIFT.ARROW_RIGHT);
		eleCcyAmount.sendKeys(ccyAmount);
		eleCcyAmount.sendKeys(Keys.TAB);
		String CreditAmt = getTypedText(eleCcyAmount).replace(".00", " ").trim();
		Settlement_DATA_MAP.put("Pay32A_3", CreditAmt.replace(",", ""));

	}

	@Then("^Verify Credit Amount in Trx CCY$")
	public void verify_credit_amount_in_trxccy() throws Throwable {
		String ccyAndAmount = getTypedText(locateElement("name", "CPYT_CR_AMT_CRCCY"));
		double ccyAndAmtDouble = convertToDouble(ccyAndAmount);
		WebElement eleExRate = locateElement("name", "CPYT_CR_BUY_RATE");
		double exRateDouble = convertToDouble(getTypedText(eleExRate));
		WebElement eleAmtTrxccy = locateElement("name", "CPYT_CR_AMT_TXCCY");
		double actAmountInTrxCcy = convertToDouble(getTypedText(eleAmtTrxccy));
		double expAmountInTrxCcy = ccyAndAmtDouble / exRateDouble;
		if (expAmountInTrxCcy == actAmountInTrxCcy) {
			log("Credit Amount in Trx CCY field value check(PayAtMat) : Pass");
		} else {
			log("Credit Amount in Trx CCY field value check(PayAtMat) : Fail");
			takesnap("Credit Amount in Trx CCY field value check(PayAtMat)");
		}
	}

	@And("^Enter the Credit Value Date$")
	public void enter_the_credit_value_date() throws Throwable {
		String creditdate = data.getCellValue("Scenario 1", 3, 566);
		WebElement eleCreditValueDate = locateElement("name", "CPYT_CR_VAL_DATE");
		append(eleCreditValueDate, creditdate);
		String creditvalueDate = getTypedText(eleCreditValueDate).replace("-", "").substring(2);
		Settlement_DATA_MAP.put("Pay32A_1", creditvalueDate);

	}

	@When("^Enter the Credit Account Type$")
	public void enter_the_credit_account_Type() throws Throwable {
		String accType = data.getCellValue("Scenario 1", 3, 567).toUpperCase();
		WebElement eleAccType = locateElement("name", "CPYT_CR_AC_TYPE");
		selectDropDownUsingText(eleAccType, accType);
		WebElement eleAccId = locateElement("name", "CPYT_ASSGN_ID");
		String accId = data.getCellValue("Scenario 1", 3, 568);
		append(eleAccId, accId);
		eleAccId.sendKeys(Keys.TAB);
	}

	@And("^Select Payment Advice Message$")
	public void select_payment_advice_message() throws Throwable {
		String accType = data.getCellValue("Scenario 1", 3, 570);
		if (!accType.isEmpty()) {
			WebElement eleAccType = locateElement("name", "CPYT_PAY_ADV_MSG");
			selectDropDownUsingText(eleAccType, accType);
			String message = getTypedText(eleAccType);
			switch (message.replace(" ", "")) {
			case "MT103":
				WebElement ele103Tab = locateElement("xpath", "//td[text()='MT103']");
				if (ele103Tab.isDisplayed()) {
					log("Payment Advice Message Onchanges(MT103)(PayAtMat) - Pass");
				} else {
					log("Payment Advice Message Onchanges(MT103)(PayAtMat) - Fail");
					takesnap("Payment Advice Message Onchanges(MT103)(PayAtMat)");
				}
				break;

			default:
				break;
			}
		}
	}

	@And("^select GPI Member$")
	public void select_GBI_member() throws Throwable {
		String accType = data.getCellValue("Scenario 1", 3, 571);
		WebElement eleAccType = locateElement("name", "IS_GPI_MEMBER");
		selectDropDownUsingText(eleAccType, accType);
		WebElement elegpisertype = locateElement("name", "SERVICE_TYPE_ID_GPI_111");
		String gpiserviceIden = getTypedText(elegpisertype);
		Settlement_DATA_MAP.put("Pay111", gpiserviceIden);

	}

	@Then("^Select Payment Cover Message$")
	public void select_payment_cover_message() throws Throwable {
		String accType = data.getCellValue("Scenario 1", 3, 572);
		if (!accType.isEmpty()) {
			WebElement eleAccType = locateElement("name", "CPYT_PAY_COV_MSG");
			selectDropDownUsingText(eleAccType, accType);
			String message = getTypedText(eleAccType);
			switch (message.replace(" ", "")) {
			case "MT202":
				WebElement ele202Tab = locateElement("xpath", "//td[text()='MT202']");
				if (ele202Tab.isDisplayed()) {
					log("Payment Advice Message Onchanges(MT202)(PayAtMat) - Pass");
				} else {
					log("Payment Advice Message Onchanges(MT202)(PayAtMat) - Fail");
					takesnap("Payment Advice Message Onchanges(MT202)(PayAtMat)");
				}
				break;

			default:
				break;
			}
		}
	}

	@When("^Click On 103 sub-tab$")
	public void click_on_103_tab() throws Throwable {
		WebElement ele103tab = locateElement("xpath", "//td[text()='MT103']");
		click(ele103tab);
	}
	/*
	 * @And("^Verify Credit CCY and Amount$") public void Verify_Credit_CCY_Amount()
	 * throws Throwable { WebElement eleCreditccy = locateElement("name",
	 * "X103_INSTR_CCY_33B"); String Creditccy = getTypedText(eleCreditccy);
	 * Settlement_DATA_MAP.put("Pay33B_1", Creditccy); WebElement eleCreditAmt =
	 * locateElement("name", "X103_INSTR_AMT_33B"); String CreditAmt =
	 * getTypedText(eleCreditAmt).replace(".00", " ").trim();
	 * Settlement_DATA_MAP.put("Pay33B_2", CreditAmt.replace(",", "")); }
	 */

	/*
	 * @And("^Verify Credit Ccy and Amount$") public void Verify_Credit_Ccy_Amount()
	 * throws Throwable { WebElement eleCreditccy = locateElement("name",
	 * "X103_SETT_CCY_32A"); String Creditccy = getTypedText(eleCreditccy);
	 * Settlement_DATA_MAP.put("Pay32A_2", Creditccy); WebElement eleCreditAmt =
	 * locateElement("name", "X103_SETT_AMT_32A"); String CreditAmt =
	 * getTypedText(eleCreditAmt).replace(".00", " ").trim();
	 * Settlement_DATA_MAP.put("Pay32A_3", CreditAmt.replace(",", ""));
	 * 
	 * 
	 * }
	 * 
	 */ @And("^Enter Receiver ID$")
	public void enter_receiver_id() throws Throwable {
		String receiverID = data.getCellValue("Scenario 1", 3, 573);
		if (!receiverID.isEmpty()) {
			WebElement eleOrderingCustomerId = locateElement("name", "X103_ADV_BKID_B2");
			append(eleOrderingCustomerId, receiverID);
			eleOrderingCustomerId.sendKeys(Keys.TAB);
			WebElement receiverswiftAdd = locateElement("name", "X103_ADV_BKSW_B2");
			String RecswiftAdd = getTypedText(receiverswiftAdd);
			Settlement_DATA_MAP.put("PayB2", RecswiftAdd);
			// System.out.println("Rec" + RecswiftAdd);

		}
	}

	@Then("^Select ordering customer type and veriy onchanges$")
	public void select_orderingCustomer_type_and_verify_oncganges() throws Throwable {
		String orCustType = data.getCellValue("Scenario 1", 3, 574);
		if (!orCustType.isEmpty()) {
			WebElement eleOrCustType = locateElement("css",
					"div#do_PaymentCredit>div:nth-of-type(3)>table>tbody>tr:nth-of-type(2)>td>table>tbody>tr:nth-of-type(3)>td:nth-of-type(4)>select");
			selectDropDownUsingText(eleOrCustType, orCustType);
			WebElement eleswift = locateElement("xpath", "//input[@name='X103_ORDCU_SW_50A']");
			String swiftAttribute = eleswift.getAttribute("class");
			WebElement eletag = locateElement("css",
					"div#do_PaymentCredit>div:nth-of-type(3)>table>tbody>tr:nth-of-type(2)>td>table>tbody>tr:nth-of-type(6)>td:nth-of-type(4)>select");
			String tagAttribute = eletag.getAttribute("class");
			String type = getTypedText(eleOrCustType);
			switch (type.toLowerCase()) {
			case "customer":
				if (swiftAttribute.equalsIgnoreCase("CHAR_P") && tagAttribute.equalsIgnoreCase("CHAR_P")) {
					log("103 Ordering Customer type Onchange(Customer)(PayAtMat) : Pass");
				} else {
					log("103 Ordering Customer type Onchange(Customer)(PayAtMat) : Fail");
					takesnap("103 Ordering Customer type Onchange(Customer)(PayAtMat)");
				}
				break;
			case "bank":
				if (swiftAttribute.equalsIgnoreCase("CHAR_O") && tagAttribute.equalsIgnoreCase("CHAR_M")) {
					log("103 Ordering Customer type Onchange(Bank)(PayAtMat) : Pass");
				} else {
					log("103 Ordering Customer type Onchange(Bank)(PayAtMat) : Fail");
					takesnap("103 Ordering Customer type Onchange(Bank)(PayAtMat)");

				}
				break;
			}
		}
	}

	@And("^Enter the ordering customer ID$")
	public void enter_the_ordering_customer_id() throws Throwable {
		String orCustId = data.getCellValue("Scenario 1", 3, 575);
		if (!orCustId.isEmpty()) {
			WebElement eleOrderingCustomerId = locateElement("name", "X103_ORDCU_ID_50A");
			append(eleOrderingCustomerId, orCustId);
			eleOrderingCustomerId.sendKeys(Keys.TAB);
			WebElement Orderingcustname = locateElement("name", "X103_ORDCU_NM_50A");
			String orderingcustN = getTypedText(Orderingcustname).toUpperCase();
			Settlement_DATA_MAP.put("Pay50K", orderingcustN);
			WebElement OrderingcustAdd1 = locateElement("name", "X103_ORDCUADD1_50A");
			String orderingcustadd1 = getTypedText(OrderingcustAdd1).toUpperCase();
			Settlement_DATA_MAP.put("Pay50K_1", orderingcustadd1);
			WebElement OrderingcustAdd2 = locateElement("name", "X103_ORDCUADD2_50A");
			String orderingcustadd2 = getTypedText(OrderingcustAdd2).toUpperCase();
			Settlement_DATA_MAP.put("Pay50K_2", orderingcustadd2);
		}
	}

	@And("^Enter SenderCorrespondent ID$")
	public void enter_SenderCorrespondent_id() throws Throwable {
		String senderId = data.getCellValue("Scenario 1", 3, 576);
		if (!senderId.isEmpty()) {
			WebElement eleSenderCorrrId = locateElement("name", "X103_SENDCORRID53A");
			append(eleSenderCorrrId, senderId);
			eleSenderCorrrId.sendKeys(Keys.TAB);
			WebElement eleSendercoresTag = locateElement("name", "X103_TAG_53A");
			Settlement_DATA_MAP.put("Pay53A", getTypedText(eleSendercoresTag));
			WebElement eleSendercoreAcc = locateElement("name", "X103SENDCORACNO53A");
			Settlement_DATA_MAP.put("Pay53A_1", getTypedText(eleSendercoreAcc));
			WebElement eleSendercoreswiftAdd = locateElement("name", "X103_SENDCORRSW53A");
			Settlement_DATA_MAP.put("Pay53A_2", getTypedText(eleSendercoreswiftAdd));
		}
	}

	@And("^Enter Receivers Correspondent ID$")
	public void enter_ReceiversCorrespondent_id() throws Throwable {
		String receiverID = data.getCellValue("Scenario 1", 3, 577);
		if (!receiverID.isEmpty()) {
			WebElement eleRecevCorrrId = locateElement("name", "X103_RECCORRID_54A");
			append(eleRecevCorrrId, receiverID);
			eleRecevCorrrId.sendKeys(Keys.TAB);
			WebElement eleReceivercoresTag = locateElement("name", "X103_TAG_54A");
			Settlement_DATA_MAP.put("Pay54A", getTypedText(eleReceivercoresTag));
			WebElement eleReceivercoreswiftAdd = locateElement("name", "X103_RECCORRSW_54A");
			Settlement_DATA_MAP.put("Pay54A_1", getTypedText(eleReceivercoreswiftAdd));
		}
	}

	@And("^Enter Intermediatory Institution ID$")
	public void enter_intermediatory_institution_id() throws Throwable {
		String intermediaryInsID = data.getCellValue("Scenario 1", 3, 578);
		if (!intermediaryInsID.isEmpty()) {
			WebElement elentermediaryInsId = locateElement("name", "X103_MEDI_BKID_56A");
			append(elentermediaryInsId, intermediaryInsID);
			elentermediaryInsId.sendKeys(Keys.TAB);
			WebElement eleInterInsTag = locateElement("name", "X103_TAG_56A");
			Settlement_DATA_MAP.put("Pay56A", getTypedText(eleInterInsTag));
			WebElement eleIntermediaryinsAccNo = locateElement("name", "X103_MEDIBKACNO56A");
			Settlement_DATA_MAP.put("Pay56A_1", getTypedText(eleIntermediaryinsAccNo));
			WebElement eleIntermediaryinsswiftAdd = locateElement("name", "X103_MEDI_BKSW_56A");
			Settlement_DATA_MAP.put("Pay56A_2", getTypedText(eleIntermediaryinsswiftAdd));

		}
	}

	@And("^Enter Account with Institution ID$")
	public void enter_account_with_institution_id() throws Throwable {
		String accWithIns = data.getCellValue("Scenario 1", 3, 579);
		if (!accWithIns.isEmpty()) {
			WebElement eleaccWithInsId = locateElement("name", "X103_ACC_BKID_57A");
			append(eleaccWithInsId, accWithIns);
			eleaccWithInsId.sendKeys(Keys.TAB);
			WebElement eleAccwithInsTag = locateElement("name", "X103_TAG_57A");
			Settlement_DATA_MAP.put("Pay57A", getTypedText(eleAccwithInsTag));
			WebElement eleAccwithInsAccNo = locateElement("name", "X103_ACC_BKACNO57A");
			Settlement_DATA_MAP.put("Pay57A_1", getTypedText(eleAccwithInsAccNo));
			WebElement eleAccwithInsswiftAdd = locateElement("name", "X103_ACC_BKSW_57A");
			Settlement_DATA_MAP.put("Pay57A_2", getTypedText(eleAccwithInsswiftAdd));

		}
	}

	@And("^Enter Beneficiary Customer ID$")
	public void enter_beneficiary_customer_id() throws Throwable {
		String beneCustId = data.getCellValue("Scenario 1", 3, 580);
		if (!beneCustId.isEmpty()) {
			WebElement eleBeneCustId = locateElement("name", "X103_BENECU_ID_59A");
			append(eleBeneCustId, beneCustId);
			eleBeneCustId.sendKeys(Keys.TAB);
			WebElement eleBeneficiaryCustN = locateElement("name", "X103_BENECU_NM_59A");
			String BeneCustName = getTypedText(eleBeneficiaryCustN);
			Settlement_DATA_MAP.put("Pay59_1", BeneCustName.toUpperCase());
			WebElement eleBeneficiaryCustAdd1 = locateElement("name", "X103BENECUADD1_59A");
			String BeneCustAdd = getTypedText(eleBeneficiaryCustAdd1);
			Settlement_DATA_MAP.put("Pay59_2", BeneCustAdd.toUpperCase());
			WebElement eleBeneficiaryCustAdd2 = locateElement("name", "X103BENECUADD2_59A");
			Settlement_DATA_MAP.put("Pay59_3", getTypedText(eleBeneficiaryCustAdd2));

		}
	}

	@And("^Select Bank Opreation Code$")
	public void select_bank_opreation_code() throws Throwable {
		String bankOpreationCode = data.getCellValue("Scenario 1", 3, 581);
		if (!bankOpreationCode.isEmpty()) {
			WebElement eleBanOpreCode = locateElement("name", "X103_BKOP_CODE_23B");
			selectDropDownUsingText(eleBanOpreCode, bankOpreationCode);
			String BankOperatcode = getTypedText(eleBanOpreCode);
			Settlement_DATA_MAP.put("Pay23B", BankOperatcode);

		}
	}

	@And("^Select Details of Charges$")
	public void select_details_of_charges() throws Throwable {
		String detailsOfCharges = data.getCellValue("Scenario 1", 3, 582);
		if (!detailsOfCharges.isEmpty()) {
			WebElement eledetailsOfCharges = locateElement("name", "X103_DET_CHG_71A");
			selectDropDownUsingText(eledetailsOfCharges, detailsOfCharges);
			Settlement_DATA_MAP.put("Pay71A", getTypedText(eledetailsOfCharges));

		}
	}

	@And("^Enter Sender Charges$")
	public void enter_sender_charges() throws Throwable {
		String senderCharges = data.getCellValue("Scenario 1", 3, 583);
		if (!senderCharges.isEmpty()) {
			WebElement elesenderCharges = locateElement("name", "X103_SENDCHGAMT71F");
			elesenderCharges.clear();
			elesenderCharges.click();
			append(elesenderCharges, senderCharges);
			WebElement eleSenderchrgsCCy = locateElement("name", "X103_SENDCHGCCY71F");
			Settlement_DATA_MAP.put("Pay71F_1", getTypedText(eleSenderchrgsCCy));
			Settlement_DATA_MAP.put("Pay71F_2", getTypedText(elesenderCharges));

		}
	}

	@When("^Click On MT202 tab$")
	public void click_on_mt202_tab() throws Throwable {
		WebElement ele202Tab = locateElement("id", "do_PaymentMT202_Tab");
		click(ele202Tab);
	}

	@Then("^Verify MT(\\d+) Related Reference$")
	public void verify_MT_Related_Reference(int arg1) throws Throwable {
		WebElement eleRelRef = locateElement("name", "X202_RELATEDNO_21");

		String relRef = getTypedText(eleRelRef);
		Settlement_DATA_MAP.put("PayatMat21", relRef);
	}

	@And("^Enter the Receiver Bank Id$")
	public void receiver_bank_id() throws Throwable {
		String receiverBankId = data.getCellValue("Scenario 1", 3, 592);
		if (!receiverBankId.isEmpty()) {
			WebElement eleReceiverBankId = locateElement("name", "X202_ADV_BKID_B2");
			append(eleReceiverBankId, receiverBankId);
			eleReceiverBankId.sendKeys(Keys.TAB);
			WebElement ReceiverBankname = locateElement("name", "X202_ADV_BKNM_B2");
			String RecBankName = getTypedText(ReceiverBankname).toUpperCase();
			Settlement_DATA_MAP.put("PayatMatB2", RecBankName);
			WebElement ReceiverBankAdd1 = locateElement("name", "X202_ADV_BKADD1_B2");
			String RecBankAdd1 = getTypedText(ReceiverBankAdd1).toUpperCase();
			Settlement_DATA_MAP.put("PayatMatB2_1", RecBankAdd1);
			WebElement ReceiverBankAdd2 = locateElement("name", "X202_ADV_BKADD2_B2");
			String RecBankAdd2 = getTypedText(ReceiverBankAdd2).toUpperCase();
			Settlement_DATA_MAP.put("PayatMatB2_2", RecBankAdd2);
		}
	}

	@And("^Enter the Ordering Institution Id$")
	public void ordering_institution_id() throws Throwable {
		String orderingInsitutionId = data.getCellValue("Scenario 1", 3, 593);
		if (!orderingInsitutionId.isEmpty()) {
			WebElement eleorderingInsitutionId = locateElement("name", "X202_ORDBK_ID_52A");
			append(eleorderingInsitutionId, orderingInsitutionId);

			eleorderingInsitutionId.sendKeys(Keys.TAB);
			WebElement OrderingInstname = locateElement("name", "X202_ORDBK_NM_52A");
			String OrderInstName = getTypedText(OrderingInstname).toUpperCase();
			Settlement_DATA_MAP.put("PayatMat52A_1", OrderInstName);
			WebElement OrderingInstAdd1 = locateElement("name", "X202_ORDBKADD1_52A");
			String OrderInstAdd1 = getTypedText(OrderingInstAdd1).toUpperCase();
			Settlement_DATA_MAP.put("PayatMat52A_2", OrderInstAdd1);
			WebElement OrderingInstAdd2 = locateElement("name", "X202_ORDBKADD2_52A");
			String OrderInstAdd2 = getTypedText(OrderingInstAdd2).toUpperCase();
			Settlement_DATA_MAP.put("PayatMat52A_3", OrderInstAdd2);

		}
	}

	@And("^Enter the SenderCorrespondent ID$")
	public void enter_the_SenderCorrespondent_id() throws Throwable {
		String senderId = data.getCellValue("Scenario 1", 3, 594);
		if (!senderId.isEmpty()) {
			WebElement eleSenderCorrrId = locateElement("name", "X202_SENDCORRID53A");
			append(eleSenderCorrrId, senderId);

			eleSenderCorrrId.sendKeys(Keys.TAB);
			WebElement eleSendercoresTag = locateElement("name", "X202_TAG_53A");
			String SendCorrName = getTypedText(eleSendercoresTag);
			Settlement_DATA_MAP.put("PayatMat53A_1", SendCorrName);
			WebElement eleSendercoreAcc = locateElement("name", "X202SENDCORACNO53A");
			String SendCoreAcc = getTypedText(eleSendercoreAcc);
			Settlement_DATA_MAP.put("PayatMat53A_2", SendCoreAcc);
			WebElement eleSendercoreswiftAdd = locateElement("name", "X202_SENDCORRSW53A");
			String SendCoreSwift = getTypedText(eleSendercoreswiftAdd);
			Settlement_DATA_MAP.put("PayatMat53A_3", SendCoreSwift);

		}
	}

	@And("^Enter the Receivers Correspondent ID$")
	public void enter_the_ReceiversCorrespondent_id() throws Throwable {
		String receiverID = data.getCellValue("Scenario 1", 3, 595);
		if (!receiverID.isEmpty()) {
			WebElement eleRecevCorrrId = locateElement("name", "X202_RECCORRID_54A");
			append(eleRecevCorrrId, receiverID);

			eleRecevCorrrId.sendKeys(Keys.TAB);
			WebElement eleReceivercoresTag = locateElement("name", "X202_TAG_54A");
			String RecvCorrTag = getTypedText(eleReceivercoresTag);
			Settlement_DATA_MAP.put("PayatMat54A_1", RecvCorrTag);
			WebElement eleReceivercoreswiftAdd = locateElement("name", "X202_RECCORRSW_54A");
			String RecvCorrSwiftAdd = getTypedText(eleReceivercoreswiftAdd);
			Settlement_DATA_MAP.put("PayatMat54A_2", RecvCorrSwiftAdd);

		}
	}

	@And("^Enter the Intermediatory Institution ID$")
	public void enter_the_intermediatory_institution_id() throws Throwable {
		String intermediaryInsID = data.getCellValue("Scenario 1", 3, 596);
		if (!intermediaryInsID.isEmpty()) {
			WebElement elentermediaryInsId = locateElement("name", "X202_MEDI_BKID_56A");
			append(elentermediaryInsId, intermediaryInsID);

			elentermediaryInsId.sendKeys(Keys.TAB);
			WebElement eleInterInsTag = locateElement("name", "X202_TAG_56A");
			String InterInstTag = getTypedText(eleInterInsTag);
			Settlement_DATA_MAP.put("PayatMat56A_1", InterInstTag);
			WebElement eleIntermediaryinsAccNo = locateElement("name", "X202_MEDIBKACNO56A");
			String InterInstAcc = getTypedText(eleIntermediaryinsAccNo);
			Settlement_DATA_MAP.put("PayatMat56A_2", InterInstAcc);
			WebElement eleIntermediaryinsswiftAdd = locateElement("name", "X202_MEDI_BKSW_56A");
			String InterInstSwiftAdd = getTypedText(eleIntermediaryinsswiftAdd);
			Settlement_DATA_MAP.put("PayatMat56A_3", InterInstSwiftAdd);

		}
	}

	@And("^Enter the Account with Institution ID$")
	public void enter_the_account_with_institution_id() throws Throwable {
		String accWithIns = data.getCellValue("Scenario 1", 3, 597);
		if (!accWithIns.isEmpty()) {
			WebElement eleaccWithInsId = locateElement("name", "X202_ACC_BKID_57A");
			append(eleaccWithInsId, accWithIns);

			eleaccWithInsId.sendKeys(Keys.TAB);
			WebElement eleAccwithInsTag = locateElement("name", "X202_TAG_57A");
			String AccwithInstTag = getTypedText(eleAccwithInsTag);
			Settlement_DATA_MAP.put("PayatMat57A_1", AccwithInstTag);
			WebElement eleAccwithInsAccNo = locateElement("name", "X202_ACC_BKACNO57A");
			String AccwithInstAccno = getTypedText(eleAccwithInsAccNo);
			Settlement_DATA_MAP.put("PayatMat57A_2", AccwithInstAccno);
			WebElement eleAccwithInsswiftAdd = locateElement("name", "X202_ACCBKADD2_57A");
			String AccwithInstSwift = getTypedText(eleAccwithInsswiftAdd);
			Settlement_DATA_MAP.put("PayatMat57A_3", AccwithInstSwift);

		}
	}

	@And("^Enter the Beneficiary insitution ID$")
	public void enter_the_beneficiary_insitution_id() throws Throwable {
		String beneInsId = data.getCellValue("Scenario 1", 3, 598);
		if (!beneInsId.isEmpty()) {
			WebElement eleBeneInsId = locateElement("name", "X202_BENE_BKID_58A");
			append(eleBeneInsId, beneInsId);

			eleBeneInsId.sendKeys(Keys.TAB);
			WebElement BeneficiaryInstname = locateElement("name", "X202_BENE_BKNM_58A");
			String BeneInstName = getTypedText(BeneficiaryInstname).toUpperCase();
			Settlement_DATA_MAP.put("PayatMat58A_1", BeneInstName);
			WebElement BeneficiaryInstAdd1 = locateElement("name", "X202BENEBKADD1_58A");
			String BeneInstAdd1 = getTypedText(BeneficiaryInstAdd1).toUpperCase();
			Settlement_DATA_MAP.put("PayatMat58A_2", BeneInstAdd1);
			WebElement BeneficiaryInstAdd2 = locateElement("name", "X202BENEBKADD2_58A");
			String BeneInstAdd2 = getTypedText(BeneficiaryInstAdd2).toUpperCase();
			Settlement_DATA_MAP.put("PayatMat58A_3", BeneInstAdd2);

		}
	}

	@And("^Click Save Button$")
	public void click_on_save_button() throws Throwable {
		WebElement eleSave = locateElement("id", "PaymentCredit_SAVE");
		click(eleSave);
	}

	@Given("^click on Narrative tab$")
	public void click_on_Narrative_tab() throws Throwable {
		WebElement elePayatmatNar = locateElement("id", "F");
		elePayatmatNar.click();

	}

	@Given("^Select SWIFT Send To$")
	public void Select_SWIFT_Send_To() throws Throwable {
		String SwiftsTo = data.getCellValue("Scenario 1", 3, 601);
		WebElement eleSWIFTSTo = locateElement("id", "SW_SEND_TO_FLG");
		selectDropDownUsingText(eleSWIFTSTo, SwiftsTo);
	}

	@Given("^click on notes tab in Payment at Maturity$")
	public void click_on_notes_tab_in_Payment_at_Maturity() throws Throwable {
		WebElement elePayatMatNotes = locateElement("id", "I");
		elePayatMatNotes.click();

	}

	@Given("^Verify notes details in Payment at Maturity$")
	public void verify_notes_details_in_Payment_at_Maturity() throws Throwable {
		WebElement elenotes = locateElement("id", ("NOTES"));
		String note = getTypedText(elenotes);
		if (note.equalsIgnoreCase(hash_map.get("PayAcceptNotes"))) {

			log("Verify Notes details in Payment at Maturity:Pass");
		} else {
			log("Verify Notes details in Payment at Maturity:Fail");
			takesnap("Verify Notes details in Payment at Maturity");
		}
	}

	@Given("^click on diary Tab in Payment at Maturity$")
	public void click_on_diary_Tab_in_Payment_at_Maturity() throws Throwable {
		WebElement elePayatMatDiary = locateElement("id", "J");
		elePayatMatDiary.click();
	}

	@Given("^Enter Related reference in Payment at Maturity$")
	public void Enter_Related_reference_in_Payment_at_Maturity() throws Throwable {
		String RRefPayAtMat = hash_map.get("referenceNo");
		WebElement eleReRef = locateElement("name", ("DIARY_RELATED_REF"));
		append(eleReRef, RRefPayAtMat);
		if (getTypedText(eleReRef).length() <= 16) {
			log("Verify Related Reference Length in Payment at Maturity:Pass");
		} else {
			log("Verify Related Reference Length in Payment at Maturityt:Fail");
			takesnap("Verify Related Reference Length in  Payment at Maturity");
		}
	}

	@Given("^Enter Diary narrative in Payment at Maturity$")
	public void Enter_Diary_narrative_in_Payment_at_Maturity() throws Throwable {
		WebElement eleNarrative = locateElement("name", ("DIARY_NARRATIVE"));
		String narrative = data.getCellValue("Scenario 1", 2, 607);
		append(eleNarrative, narrative);
		eleNarrative.sendKeys(Keys.TAB);
	}

	@Then("^click on swift button in payment at Maturity$")
	public void click_on_swift_button_in_payment_at_Maturity() throws Throwable {
		driver.switchTo().parentFrame();
		driver.switchTo().frame("work");
		if (locateElement("name", "Swift").isDisplayed()) {
			click(locateElement("name", "Swift"));
			Thread.sleep(6000);
			switchToWindow(1);
			driver.manage().window().maximize();
			WebElement eleView = locateElement("xpath",
					"//*[@id=\"ext-gen6\"]/form/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/div/table/tbody/tr[4]/td/table/tbody/tr[2]/td[3]/a");
			click(eleView);
		}
	}

	@Then("^Validate the swift MT(\\d+)$")
	public void Validate_the_swift_MT(int arg1) throws Throwable {
		Thread.sleep(6000);
		switchToWindow(2);
		driver.manage().window().maximize();
		Thread.sleep(6000);
		WebElement table = driver.findElement(
				By.xpath("/html/body/table/tbody/tr[5]/td/table/tbody/tr[1]/td[2]/div/table[1]/tbody/tr[4]/td/table"));
		List<WebElement> column = table.findElements(By.tagName("td"));
		List<String> value = new ArrayList<String>();
		for (int j = 0; j < column.size(); j++) {
			value.add(column.get(j).getText());
			// System.out.println(column.get(j).getText());
		}

		// 103 Outgoing SWIFT Header 1
		int OutSwiftheaderIntex = value.indexOf("Outgoing SWIFT Header 1");
		// TagCheck
		String OutSwiftheaderTag = value.get(OutSwiftheaderIntex - 1);
		swiftTag(OutSwiftheaderTag, "B1:", "MT103_Outgoing SWIFT Header 1");

		// Receiver's BIC Code
		int recBitcodeIntex = value.indexOf("Receiver's BIC Code");
		// TagCheck
		String recBitcodeTag = value.get(recBitcodeIntex - 1);
		swiftTag(recBitcodeTag, "B2:", "MT103_Receiver's BIC Code");
		// ContentCheck
		String recBitcodeCon = value.get(recBitcodeIntex + 2);
		String swiftBitcode = Settlement_DATA_MAP.get("PayB2");
		// System.out.println("Before = " + swiftBitcode);
		Thread.sleep(300);

		while (swiftBitcode.length() < 12) {
			Thread.sleep(300);
			swiftBitcode = swiftBitcode.concat("X");
		}

		// System.out.println("After = " + swiftBitcode);
		String ExpRecBitCon = "I103" + swiftBitcode + "N2";
		// System.out.println("Act - " + recBitcodeCon + " | Exp" + ExpRecBitCon);
		swiftContent(ExpRecBitCon, recBitcodeCon, "MT103_Receiver's BIC Code");

		// Service Type Identifier
		if (!Settlement_DATA_MAP.get("Pay111").isEmpty()) {
			if (value.contains("Service Type Identifier")) {
				log("Service Type Identifier check - Pass");
				int SertypeidenIntex = value.indexOf("Service Type Identifier");
				// TagCheck
				String SerTypeTag = value.get(SertypeidenIntex - 1);
				swiftTag(SerTypeTag, "111:", "MT103_Service Type Identifier");
				// ContentCheck
				String ServicetypeCon = value.get(SertypeidenIntex + 2);
				// System.out.println("Act - " + ServicetypeCon + " | Exp" +
				// Settlement_DATA_MAP.get("Pay111"));
				swiftContent(Settlement_DATA_MAP.get("Pay111"), ServicetypeCon, "MT103_Service Type Identifier");
			} else {
				log("MT103_Service Type Identifier check - Fail");
			}
		}

		// Sender's Reference
		if (!Settlement_DATA_MAP.get("Pay20").isEmpty()) {
			if (value.contains("Sender's Reference")) {
				log("Sender's Reference check - Pass");
				int SendersRefIntex = value.indexOf("Sender's Reference");
				// TagCheck
				String SendersRefTag = value.get(SendersRefIntex - 1);
				swiftTag(SendersRefTag, "20:", "MT103_ Sender's Reference");
				// ContentCheck
				String SendersRefCon = value.get(SendersRefIntex + 2);
				// System.out.println("Act - " + SendersRefCon + " | Exp" +
				// Settlement_DATA_MAP.get("Pay20"));
				swiftContent(Settlement_DATA_MAP.get("Pay20"), SendersRefCon, "MT734_Sender's TRN");
			} else {
				log("MT103_Sender's Reference check - Fail");
			}
		}

		// Bank Operation Code

		if (!Settlement_DATA_MAP.get("Pay23B").isEmpty()) {
			if (value.contains("Bank Operation Code")) {
				log("Bank Operation Code check - Pass");
				int BankoprCodeIntex = value.indexOf("Bank Operation Code");
				// TagCheck
				String BankoperCodeTag = value.get(BankoprCodeIntex - 1);
				swiftTag(BankoperCodeTag, "23B:", "MT103_Bank Operation Code");
				// ContentCheck
				String bankOPerCodeCon = value.get(BankoprCodeIntex + 2);
				// System.out.println("Act - " + bankOPerCodeCon + " | Exp" +
				// Settlement_DATA_MAP.get("Pay23B"));
				swiftContent(Settlement_DATA_MAP.get("Pay23B"), bankOPerCodeCon, "MT103_Bank Operation Code");
			} else {
				log("MT103_Bank Operation Code check - Fail");
			}
		}

		// ValueDate/Currency/InterbankSettled Amount
		if (value.contains("ValueDate/Currency/InterbankSettled Amt")) {
			log("ValueDate/Currency/InterbankSettled Amt check - Pass");
			int interbanksetIntex = value.indexOf("ValueDate/Currency/InterbankSettled Amt");
			// TagCheck
			String interbankTag = value.get(interbanksetIntex - 1);
			swiftTag(interbankTag, "32A:", "MT103_ValueDate/Currency/InterbankSettled Amt");
			// ContentCheck
			String InterbankCont = value.get(interbanksetIntex + 2);
			String expinterbankCont = Settlement_DATA_MAP.get("Pay32A_1") + Settlement_DATA_MAP.get("Pay32A_2")
					+ Settlement_DATA_MAP.get("Pay32A_3");
			// System.out.println("Act - " + InterbankCont + " | Exp" +
			// expinterbankCont.concat(",00"));
			swiftContent(expinterbankCont.concat(",00"), InterbankCont,
					"MT103_ValueDate/Currency/InterbankSettled Amt");
		} else {
			log("MT103_ValueDate/Currency/InterbankSettled Amt check - Fail");
		}

		// Currency/Instructed Amount
		if (value.contains("Currency/Instructed Amount")) {
			log("Currency/Instructed Amount check - Pass");
			int CreditCCyandAmtIntex = value.indexOf("Currency/Instructed Amount");
			// TagCheck
			String CreditccyandAmtTag = value.get(CreditCCyandAmtIntex - 1);
			swiftTag(CreditccyandAmtTag, "33B:", "MT103_Currency/Instructed Amount");
			// ContentCheck
			String CreditccyandAmtCont = value.get(CreditCCyandAmtIntex + 2);
			String expCreditccyandAmtCont = Settlement_DATA_MAP.get("Pay32A_2") + Settlement_DATA_MAP.get("Pay32A_3");
			// System.out.println("Act - " + CreditccyandAmtCont + " | Exp" +
			// expCreditccyandAmtCont.concat(",00"));
			swiftContent(expCreditccyandAmtCont.concat(",00"), CreditccyandAmtCont, "MT103_Currency/Instructed Amount");
		} else {
			log("MT103_Currency/Instructed Amount check - Fail");
		}

		// Ordering Customer
		if (value.contains("Ordering Customer")) {
			log("MT103_ Ordering Customer check - Pass");
			int OrderingCustInt = value.indexOf("Ordering Customer");
			// TagCheck
			String OrderingCustTag = value.get(OrderingCustInt - 1);
			swiftTag(OrderingCustTag, "50K:", "MT103_Ordering Customer");
			// ContentCheck
			String OrdeingcustCont = value.get(OrderingCustInt + 2).replaceAll("\n", "").replaceAll(" ", "");
			Thread.sleep(300);
			String expOrderingCusCont = Settlement_DATA_MAP.get("Pay50K") + "\n" + Settlement_DATA_MAP.get("Pay50K_1")
					+ "\n" + Settlement_DATA_MAP.get("Pay50K_2");
			String ExpOrderingCustCont = expOrderingCusCont.replaceAll("\n", "").replaceAll(" ", "");
			// System.out.println("Act - " + OrdeingcustCont + " | Exp" +
			// ExpOrderingCustCont);
			swiftContent(ExpOrderingCustCont, OrdeingcustCont, "MT103_Ordering Customer");
		} else {
			log("MT103_Ordering Customer check - Fail");
		}

		// Sender's Correspondent
		if (value.contains("Sender's Correspondent")) {
			log("Sender's Correspondent check - Pass");
			int SenderCorresIntex = value.indexOf("Sender's Correspondent");
			// TagCheck
			String SenderCoresTag = value.get(SenderCorresIntex - 1);
			String SenderSwiftTag = Settlement_DATA_MAP.get("Pay53A");
			// System.out.println("Before = " + SenderSwiftTag);
			Thread.sleep(300);
			String ExpSenderSwift = "53" + SenderSwiftTag + ":";
			swiftTag(SenderCoresTag, ExpSenderSwift, "MT103_Sender's Correspondent");
			// ContentCheck
			String SenderCoresCon = value.get(SenderCorresIntex + 2).replaceAll("\n", "").replaceAll(" ", "");
			String expSenderCoresCon = Settlement_DATA_MAP.get("Pay53A_1")
					+ Settlement_DATA_MAP.get("Pay53A_2").replaceAll("\n", "").replaceAll(" ", "");
			// System.out.println("Act - " + SenderCoresCon + " | Exp" + expSenderCoresCon);
			swiftContent(expSenderCoresCon, SenderCoresCon, "MT103_Sender's Correspondent");
		} else {
			log("MT103_Sender's Correspondentcheck - Fail");
		}

		// Receivers Correspondent

		if (value.contains("Receiver's Correspondent")) {
			log("Receiver's Correspondent check - Pass");
			int ReceivercorsIntex = value.indexOf("Receiver's Correspondent");
			// TagCheck
			String ReceivercorsTag = value.get(ReceivercorsIntex - 1);
			String RecCorsSwiftTag = Settlement_DATA_MAP.get("Pay54A");
			// System.out.println("Before = " + RecCorsSwiftTag);
			Thread.sleep(300);
			String ExpRecCorsTag = "54" + RecCorsSwiftTag + ":";
			swiftTag(ReceivercorsTag, ExpRecCorsTag, "MT103_Receiver's Correspondent");
			// ContentCheck
			String ReceivercoresCon = value.get(ReceivercorsIntex + 2);
			// System.out.println("Act - " + ReceivercoresCon + " | Exp" +
			// Settlement_DATA_MAP.get("Pay54A"));
			swiftContent(Settlement_DATA_MAP.get("Pay54A_1"), ReceivercoresCon, "MT103_Receivers Correspondent");
		} else {
			log("MT103_ Receiver's Correspondent check - Fail");
		}

		// Intermediary Institution
		if (value.contains("Intermediary Institution")) {
			log("Intermediary Institution check - Pass");
			int InterInsIntex = value.indexOf("Intermediary Institution");
			// TagCheck
			String InterInsTag = value.get(InterInsIntex - 1);
			String InterSwiftTag = Settlement_DATA_MAP.get("Pay56A");
			// System.out.println("Before = " + InterSwiftTag);
			Thread.sleep(300);
			String ExpInterSwift = "56" + InterSwiftTag + ":";
			swiftTag(InterInsTag, ExpInterSwift, "MT103_Sender's Correspondent");
			// ContentCheck
			String InterInsCon = value.get(InterInsIntex + 2).replaceAll("\n", "").replaceAll(" ", "");
			String expInterinsCon = Settlement_DATA_MAP.get("Pay56A_1")
					+ Settlement_DATA_MAP.get("Pay56A_2").replaceAll("\n", "").replaceAll(" ", "");
			// System.out.println("Act - " + InterInsCon + " | Exp" + expInterinsCon);
			swiftContent(expInterinsCon, InterInsCon, "MT103_Intermediary Institution");
		} else {
			log("MT103_Intermediary Institution check - Fail");
		}

		// Account With Institution
		if (value.contains("Account With Institution")) {
			log("Account With Institution check - Pass");
			int AccwithInsIntex = value.indexOf("Account With Institution");
			// TagCheck
			String AccwithInsTag = value.get(AccwithInsIntex - 1);
			String AccwithinsSwiftTag = Settlement_DATA_MAP.get("Pay57A");
			// System.out.println("Before = " + AccwithinsSwiftTag);
			Thread.sleep(300);
			String ExpAccwithInsSwift = "57" + AccwithinsSwiftTag + ":";
			swiftTag(AccwithInsTag, ExpAccwithInsSwift, "MT103_Sender's Correspondent");
			// ContentCheck
			String AccwithInsCon = value.get(AccwithInsIntex + 2).replaceAll("\n", "").replaceAll(" ", "");
			String expAccwithInsCon = Settlement_DATA_MAP.get("Pay57A_1")
					+ Settlement_DATA_MAP.get("Pay57A_2").replaceAll("\n", "").replaceAll(" ", "");
			// System.out.println("Act - " + AccwithInsCon + " | Exp" + expAccwithInsCon);
			swiftContent(expAccwithInsCon, AccwithInsCon, "MT103_Account With Institution");
		} else {
			log("MT103_Account With Institution check - Fail");
		}

		// Beneficiary Customer
		if (value.contains("Beneficiary Customer")) {
			log("MT103_ Beneficiary Customer check - Pass");
			int BeneficiaryCustInt = value.indexOf("Beneficiary Customer");
			// TagCheck
			String BenCustTag = value.get(BeneficiaryCustInt - 1);
			swiftTag(BenCustTag, "59:", "MT103_Beneficiary Customer");
			// ContentCheck
			String BencustCont = value.get(BeneficiaryCustInt + 2).replaceAll("\n", "").replaceAll(" ", "");
			String expBenCustCont = Settlement_DATA_MAP.get("Pay59_1") + "\n" + Settlement_DATA_MAP.get("Pay59_2")
					+ "\n" + Settlement_DATA_MAP.get("Pay59_3");
			String expBeneficiaycustCont = expBenCustCont.replaceAll("\n", "").replaceAll(" ", "");
			// System.out.println("Act - " + BencustCont + " | Exp" + expBenCustCont);
			swiftContent(expBeneficiaycustCont, BencustCont, "MT103_Beneficiary Customer");
		} else {
			log("MT103_Beneficiary Customer check - Fail");
		}
		// Details of Charges

		if (!Settlement_DATA_MAP.get("Pay71A").isEmpty()) {
			if (value.contains("Details of Charges")) {
				log("Details of Charges check - Pass");
				int DetailsofchrgsIntex = value.indexOf("Details of Charges");
				// TagCheck
				String DetailsofchrsTag = value.get(DetailsofchrgsIntex - 1);
				swiftTag(DetailsofchrsTag, "71A:", "MT103_Details of Charges");
				// ContentCheck
				String DetailsofChrgsCon = value.get(DetailsofchrgsIntex + 2);
				// System.out.println("Act - " + DetailsofChrgsCon + " | Exp" +
				// Settlement_DATA_MAP.get("Pay71A"));
				swiftContent(Settlement_DATA_MAP.get("Pay71A"), DetailsofChrgsCon, "MT103_Details of Charges Code");
			} else {
				log("MT103_Details of Charges check - Fail");
			}
		}
		// Sender's Charges
		if (value.contains("Sender's Charges")) {
			log("Sender's Charges check - Pass");
			int SenderschrgsIntex = value.indexOf("Sender's Charges");
			// TagCheck
			String SenderschrgsTag = value.get(SenderschrgsIntex - 1);
			swiftTag(SenderschrgsTag, "71F:", "MT103_Sender's Charges");
			// ContentCheck
			String SenderschrgsCont = value.get(SenderschrgsIntex + 2);
			String expSenderschrgsCont = Settlement_DATA_MAP.get("Pay71F_1") + Settlement_DATA_MAP.get("Pay71F_2");
			// System.out.println("Act - " + SenderschrgsCont + " | Exp" +
			// expSenderschrgsCont.concat(",00"));
			swiftContent(expSenderschrgsCont.concat(",00"), SenderschrgsCont, "MT103_Sender's Charges");
		} else {
			log("MT103_Sender's Charges check - Fail");
		}
		driver.close();
	}

	@Then("^Validate Swift MT(\\d+)$")
	public void validate_Swift_MT(int arg1) throws Throwable {
		Thread.sleep(6000);
		switchToWindow(2);
		driver.manage().window().maximize();
		Thread.sleep(6000);
		WebElement table = driver.findElement(By.xpath(
				"/html/body/form/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/div/table/tbody/tr[4]/td/table/tbody/tr[3]/td[2]"));
		List<WebElement> column = table.findElements(By.tagName("td"));
		List<String> value = new ArrayList<String>();
		for (int j = 0; j < column.size(); j++) {
			value.add(column.get(j).getText());
			// System.out.println(column.get(j).getText());
		}

		// 202 Outgoing SWIFT Header 1
		int OutSwiftheaderIntex = value.indexOf("Outgoing SWIFT Header 1");
		// TagCheck
		String OutSwiftheaderTag = value.get(OutSwiftheaderIntex - 1);
		swiftTag(OutSwiftheaderTag, "B1:", "MT202_Outgoing SWIFT Header 1");

		// Receiver's BIC Code
		int recBitcodeIntex = value.indexOf("Receiver's BIC Code");
		// TagCheck
		String recBitcodeTag = value.get(recBitcodeIntex - 1);
		swiftTag(recBitcodeTag, "B2:", "MT202_Receiver's BIC Code");
		// ContentCheck
		String recBitcodeCon = value.get(recBitcodeIntex + 2);
		String swiftBitcode = Settlement_DATA_MAP.get("PayatMatB2");
		// System.out.println("Before = " + swiftBitcode);
		Thread.sleep(300);

		while (swiftBitcode.length() < 12) {
			Thread.sleep(300);
			swiftBitcode = swiftBitcode.concat("X");
		}

		// System.out.println("After = " + swiftBitcode);
		String ExpRecBitCon = "I202" + swiftBitcode + "N2";
		// System.out.println("Act - " + recBitcodeCon + " | Exp" + ExpRecBitCon);
		swiftContent(ExpRecBitCon, recBitcodeCon, "MT202_Receiver's BIC Code");

		// Transaction Reference Number
		if (!Settlement_DATA_MAP.get("Pay20").isEmpty()) {
			if (value.contains("Transaction Reference Number")) {
				log("Transaction Reference check - Pass");
				int TrxRefIntex = value.indexOf("Transaction Reference Number");
				// TagCheck
				String TrxRefTag = value.get(TrxRefIntex - 1);
				swiftTag(TrxRefTag, "20:", "MT202_ Transaction Reference Number");
				// ContentCheck
				String TrxRefCon = value.get(TrxRefIntex + 2);
				// System.out.println("Act - " + SendersRefCon + " | Exp" +
				// Settlement_DATA_MAP.get("Pay20"));
				swiftContent(Settlement_DATA_MAP.get("Pay20"), TrxRefCon, "MT202_ Transaction Reference Number");
			} else {
				log("MT202_Transaction Reference check - Fail");
			}
		}

		// Related Reference
		if (!Settlement_DATA_MAP.get("PayatMat21").isEmpty()) {
			if (value.contains("Related Reference")) {
				log("Related Reference check - Pass");
				int RelatedRefIntex = value.indexOf("Related Reference");
				// TagCheck
				String RelatedRefTag = value.get(RelatedRefIntex - 1);
				swiftTag(RelatedRefTag, "21:", "MT202_ Related Reference");
				// ContentCheck
				String RelatedRefCon = value.get(RelatedRefIntex + 2);
				// System.out.println("Act - " + SendersRefCon + " | Exp" +
				// Settlement_DATA_MAP.get("Pay20"));
				swiftContent(Settlement_DATA_MAP.get("PayatMat21"), RelatedRefCon, "MT202_ Related Reference");
			} else {
				log("MT103_Sender's Reference check - Fail");
			}
		}

		// ValueDate/Currency Code/Amount
		if (value.contains("ValueDate, Currency Code, Amount")) {
			log("ValueDate,Currency Code, Amt check - Pass");
			int CreAmtIntex = value.indexOf("ValueDate, Currency Code, Amount");
			// TagCheck
			String CreAmtTag = value.get(CreAmtIntex - 1);
			swiftTag(CreAmtTag, "32A:", "MT202_ValueDate, Currency Code, Amount");
			// ContentCheck
			String CreAmtCont = value.get(CreAmtIntex + 2);
			String expCreAmtCont = Settlement_DATA_MAP.get("Pay32A_1") + Settlement_DATA_MAP.get("Pay32A_2")
					+ Settlement_DATA_MAP.get("Pay32A_3");
			// System.out.println("Act - " + InterbankCont + " | Exp" +
			// expinterbankCont.concat(",00"));
			swiftContent(expCreAmtCont.concat(",00"), CreAmtCont, "MT202_ValueDate, Currency Code, Amount");
		} else {
			log("MT202_ValueDate, Currency Code, Amount check - Fail");
		}

		// Ordering Institution
		if (value.contains("Ordering Institution")) {
			log("MT202_ Ordering Institution check - Pass");
			int OrderingInstInt = value.indexOf("Ordering Institution");
			// TagCheck
			String OrderingInstTag = value.get(OrderingInstInt - 1);
			swiftTag(OrderingInstTag, "52A:", "MT202_Ordering Institution");
			// ContentCheck
			String OrdeingInstCont = value.get(OrderingInstInt + 2).replaceAll("\n", "").replaceAll(" ", "");
			Thread.sleep(300);
			String expOrderingInstCont = Settlement_DATA_MAP.get("PayatMat52A_1") + "\n"
					+ Settlement_DATA_MAP.get("PayatMat52A_1") + "\n" + Settlement_DATA_MAP.get("PayatMat52A_1");
			String ExpOrderingInstCont = expOrderingInstCont.replaceAll("\n", "").replaceAll(" ", "");
			// System.out.println("Act - " + OrdeingcustCont + " | Exp" +
			// ExpOrderingCustCont);
			swiftContent(ExpOrderingInstCont, OrdeingInstCont, "MT202_Ordering Institution");
		} else {
			log("MT202_Ordering Institution - Fail");
		}

		// Sender's Correspondent
		if (value.contains("Sender's Correspondent")) {
			log("Sender's Correspondent check - Pass");
			int SenderCorresIntex = value.indexOf("Sender's Correspondent");
			// TagCheck
			String SenderCoresTag = value.get(SenderCorresIntex - 1);
			String SenderSwiftTag = Settlement_DATA_MAP.get("PayatMat53A_1");
			// System.out.println("Before = " + SenderSwiftTag);
			Thread.sleep(300);
			String ExpSenderSwift = "53" + SenderSwiftTag + ":";
			swiftTag(SenderCoresTag, ExpSenderSwift, "MT202_Sender's Correspondent");
			// ContentCheck
			String SenderCoresCon = value.get(SenderCorresIntex + 2).replaceAll("\n", "").replaceAll(" ", "");
			String expSenderCoresCon = Settlement_DATA_MAP.get("PayatMat53A_2")
					+ Settlement_DATA_MAP.get("PayatMat53A_3").replaceAll("\n", "").replaceAll(" ", "");
			// System.out.println("Act - " + SenderCoresCon + " | Exp" + expSenderCoresCon);
			swiftContent(expSenderCoresCon, SenderCoresCon, "MT202_Sender's Correspondent");
		} else {
			log("MT202_Sender's Correspondentcheck - Fail");
		}

// Receivers Correspondent

		if (value.contains("Receiver's Correspondent")) {
			log("Receiver's Correspondent check - Pass");
			int ReceivercorsIntex = value.indexOf("Receiver's Correspondent");
			// TagCheck
			String ReceivercorsTag = value.get(ReceivercorsIntex - 1);
			String RecCorsSwiftTag = Settlement_DATA_MAP.get("PayatMat54A_1");
			// System.out.println("Before = " + RecCorsSwiftTag);
			Thread.sleep(300);
			String ExpRecCorsTag = "54" + RecCorsSwiftTag + ":";
			swiftTag(ReceivercorsTag, ExpRecCorsTag, "MT202_Receiver's Correspondent");
			// ContentCheck
			String ReceivercoresCon = value.get(ReceivercorsIntex + 2);
			// System.out.println("Act - " + ReceivercoresCon + " | Exp" +
			// Settlement_DATA_MAP.get("Pay54A"));
			swiftContent(Settlement_DATA_MAP.get("PayatMat54A_2"), ReceivercoresCon, "MT202_Receivers Correspondent");
		} else {
			log("MT202_ Receiver's Correspondent check - Fail");
		}

// Intermediary Institution
		if (value.contains("Intermediary Institution")) {
			log("Intermediary Institution check - Pass");
			int InterInsIntex = value.indexOf("Intermediary Institution");
			// TagCheck
			String InterInsTag = value.get(InterInsIntex - 1);
			String InterSwiftTag = Settlement_DATA_MAP.get("PayatMat56A_1");
			// System.out.println("Before = " + InterSwiftTag);
			Thread.sleep(300);
			String ExpInterSwift = "56" + InterSwiftTag + ":";
			swiftTag(InterInsTag, ExpInterSwift, "MT202_Sender's Correspondent");
			// ContentCheck
			String InterInsCon = value.get(InterInsIntex + 2).replaceAll("\n", "").replaceAll(" ", "");
			String expInterinsCon = Settlement_DATA_MAP.get("PayatMat56A_2")
					+ Settlement_DATA_MAP.get("PayatMat56A_2").replaceAll("\n", "").replaceAll(" ", "");
			// System.out.println("Act - " + InterInsCon + " | Exp" + expInterinsCon);
			swiftContent(expInterinsCon, InterInsCon, "MT202_Intermediary Institution");
		} else {
			log("MT202_Intermediary Institution check - Fail");
		}

//  Account With Institution
		if (value.contains("Account With Institution")) {
			log("Account With Institution check - Pass");
			int AccwithInsIntex = value.indexOf("Account With Institution");
// TagCheck
			String AccwithInsTag = value.get(AccwithInsIntex - 1);
			String AccwithinsSwiftTag = Settlement_DATA_MAP.get("PayatMat57A_1");
//System.out.println("Before = " + AccwithinsSwiftTag);
			Thread.sleep(300);
			String ExpAccwithInsSwift = "57" + AccwithinsSwiftTag + ":";
			swiftTag(AccwithInsTag, ExpAccwithInsSwift, "MT202_Sender's Correspondent");
// ContentCheck
			String AccwithInsCon = value.get(AccwithInsIntex + 2).replaceAll("\n", "").replaceAll(" ", "");
			String expAccwithInsCon = Settlement_DATA_MAP.get("PayatMat57A_2")
					+ Settlement_DATA_MAP.get("PayatMat57A_3").replaceAll("\n", "").replaceAll(" ", "");
//System.out.println("Act - " + AccwithInsCon + " | Exp" + expAccwithInsCon);
			swiftContent(expAccwithInsCon, AccwithInsCon, "MT202_Account With Institution");
		} else {
			log("MT202_Account With Institution check - Fail");
		}

//Beneficiary Customer	
		if (value.contains("Beneficiary Customer")) {
			log("MT202_ Beneficiary Customer check - Pass");
			int BeneficiaryCustInt = value.indexOf("Beneficiary Customer");
// TagCheck
			String BenCustTag = value.get(BeneficiaryCustInt - 1);
			swiftTag(BenCustTag, "58A:", "MT202_Beneficiary Customer");
// ContentCheck
			String BencustCont = value.get(BeneficiaryCustInt + 2).replaceAll("\n", "").replaceAll(" ", "");
			String expBenCustCont = Settlement_DATA_MAP.get("PayatMat58A_1") + "\n"
					+ Settlement_DATA_MAP.get("PayatMat58A_2") + "\n" + Settlement_DATA_MAP.get("PayatMat58A_2");
			String expBeneficiaycustCont = expBenCustCont.replaceAll("\n", "").replaceAll(" ", "");
// System.out.println("Act - " + BencustCont + " | Exp" + expBenCustCont);
			swiftContent(expBeneficiaycustCont, BencustCont, "MT202_Beneficiary Customer");
		} else {
			log("MT202_Beneficiary Customer check - Fail");
		}
		driver.close();
	}

}