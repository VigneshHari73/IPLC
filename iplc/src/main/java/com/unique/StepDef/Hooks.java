package com.unique.StepDef;

import com.cucumber.listener.Reporter;
import com.unique.base.ProjectBase;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks extends ProjectBase {

	@Before
	public void beforeScenario(Scenario scenario) {

		if (scenario.getName().equals("Reg Main tab validation")
				|| scenario.getName().equals("Reg Output Validation	and Confirmation")
				|| scenario.getName().equals("Reg MT705 tab validation") || scenario.getName().equals("Issue Tenor tab")
				|| scenario.getName().equals("Issue Advice Tab") || scenario.getName().equals("Register Amendment")
				|| scenario.getName().equals("Issue Amendment") || scenario.getName().equals("Pay/Accept Charges tab")
				|| scenario.getName().equals("Pay/Accept - Payment tab")
				|| scenario.getName().equals("Beneficiary Response")
				|| scenario.getName().equals(" Pay/Accept - Confirm and Release")
				|| scenario.getName().equals("Payment at Maturity - Payment tab")
				|| scenario.getName().equals("Payment at Maturity - Confirm and Release")
				|| scenario.getName().equals("Click On swift button")
				|| scenario.getName().equals("Validate the swift_MT705")) {
			Reporter.assignAuthor("Vignesh");
		}

		if (scenario.getName().equals("Parties tab validation") || scenario.getName().equals("Iss Main tab validation")
				|| scenario.getName().equals("Iss PartiesII tab validation")
				|| scenario.getName().equals("Iss Goods tab validation")) {
			Reporter.assignAuthor("Ganesh Kumar");
		}
		if (scenario.getName().equals("Reg Risk tab validation")
				|| scenario.getName().equals("Reg Notes tab validation")
				|| scenario.getName().equals("Reg Diary tab validation")
				|| scenario.getName().equals("Iss Notes tab validation")
				|| scenario.getName().equals("Iss Diary tab validation")
				|| scenario.getName().equals("Iss Output Validation and Confirmation")
				|| scenario.getName().equals("RegDoc Main tab Validation")
				|| scenario.getName().equals("RegDoc Notes tab Validation")
				|| scenario.getName().equals("RegDoc Diary tab Validation")
				|| scenario.getName().equals("RegDoc - Confirm and Release")
				|| scenario.getName().equals("CheckDoc Main tab Validation")
				|| scenario.getName().equals("RegDis Parties tab Validation")
				|| scenario.getName().equals("RegDis Discrepancies tab Validation")
				|| scenario.getName().equals("RegDis Notes tab Validation")
				|| scenario.getName().equals("RegDis Diary tab Validation")
				|| scenario.getName().equals("RegDis Confirmation and Output Validation")
				|| scenario.getName().equals("Pay/Accept Acceptance tab Validation")
				|| scenario.getName().equals("Pay/Accept Notes tab Validation")
				|| scenario.getName().equals("Pay/Accept Diary tab Validation")
				|| scenario.getName().equals("Payment at Maturity Main tab validation")
				|| scenario.getName().equals("Payment at Maturity Narrative tab Validation")
				|| scenario.getName().equals("Payment at Maturity Notes tab Validation")
				|| scenario.getName().equals("Payment at Maturity Diary tab Validation")) {

			Reporter.assignAuthor("Kowsalya");
		}
		if (scenario.getName().equals("Charges tab validation") || scenario.getName().equals("Iss Docuemnt Tab")
				|| scenario.getName().equals("Iss Instructions tab")
				|| scenario.getName().equals("Iss Charges Tab Validation")
				|| scenario.getName().equals("RegDoc Documents tab ")
				|| scenario.getName().equals("Register Docs Advice Tab")
				|| scenario.getName().equals("RegDoc Confirmation and Output Validation")
				|| scenario.getName().equals("CheckDoc Documents Tab")
				|| scenario.getName().equals("CheckDoc Notes tab validation")
				|| scenario.getName().equals("CheckDoc Diary tab validation")
				|| scenario.getName().equals("CheckDoc Confirmation")
				|| scenario.getName().equals("RegDis Charges tab validation")
				|| scenario.getName().equals("DisRes Main tab validation")
				|| scenario.getName().equals("DisRes Confirmation and Output Validation")
				|| scenario.getName().equals("Pay/Accept MainTab") || scenario.getName().equals("Pay/Accept Advice Tab")
				|| scenario.getName().equals("Payment At Maturity Charges tab")) {
			Reporter.assignAuthor("Lavanya");
		}
	}

	@After
	public void afterScenario(Scenario scenario) throws InterruptedException {
		if (scenario.getName().equals("Payment at Maturity - Confirm and Release")) {
			Thread.sleep(3000);
			quit();
		}
	}

}
