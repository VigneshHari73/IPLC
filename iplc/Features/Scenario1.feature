Feature: Scenario1 
Scenario: Reg Main tab validation 
	Given Open the EE Application 
	Given Click On Import Letter Of Credit 
	And Click on Iplc Issuance 
	And Click On Register Letter Of Credit 
	Then Verify the LC Number 
	And Select the Place of Expiry 
	And Select the Form of LC 
	And Select the Applicable Rules 
	And Enter the Date of Expiry 
	And Select the Available By 
	And Enter the Tenor Days 
	And Select the Tenor Type 
	And Select the LC Currency 
	And Enter the LC Amount 
	And Select the LC Tolerence 
	And Enter the posive Tolerence 
	And Enter the Negative Tolerence 
	Then ChecK LC Balance 
	Then Check Currency and Balance 
	And Enter the Additional Amounts Covered 
	Then Select the Send MT705 
	
Scenario: Reg Parties tab validation 
	Given click on parties tab 
	And Enter App Details 
	And Enter Bene Details 
	And Enter For the Account of Details 
	And Enter Advbk Details 
	And Enter Advthbk Details 
	
Scenario: Reg Risk tab validation 
	Given click on Risk tab 
	And Enter Bank Liability Acccount 
	And Enter Customer Liability Account 
	
Scenario: Reg Charges tab validation 
	Given Click the charges tab 
	And Select the Paid at value 
	And Enter the Acc No 
	
Scenario: Reg Notes tab validation 
	And click on Notes tab 
	And Enter Notes 
	
Scenario: Reg Diary tab validation 
	And click on Diary tab 
	And Enter Related Reference 
	And Enter Diary Narrative 
	
Scenario: Reg MT705 tab validation 
	And click on MT705S tab 
	And Enter the Available With Bank details 
	And Enter the Latest Date of Shipmen 
	And Enter the Place of Taking in Charge 
	And Enter the Port of Loading 
	And Enter the Port of Discharge 
	And Enter Place of Final Destination 
	And Enter Description of Goods and/or Services 
	And Enter Sender to Receiver Information 
	And Enter Narrative 
	
Scenario: Reg Output Validation	and Confirmation 
	And Click Confirm button 
	Then verify voucher is generate 
	Then verify Swift is generate 
#	When Click On swift button 
#	Then Validate the swift_MT705 
	And Click cancel button 
	
Scenario: Reg Supervisor Release 
	And Release the transaction 
	
Scenario: Iss Main tab validation 
	Given click on Issue Letter Of Credit 
	And Select the Revolving LC 
	And Select the Cumulative? 
	And Select the Auto Renewal? 
	And Select the Revolve Periods 
	And Enter No. of Revolve Periods 
	And Enter Next Revolve Date 
	
Scenario: Iss PartiesII tab validation 
	Given click on partiesII tab 
	And Select Reimbursement. Authority Required? 
	And Enter Reimbursing Bank Details 
	And Select Reimbursement Charge 
	And Select Applicable Rules 
	And Enter Reimbursing Bank's Reference 
	And Enter Charges 
	And Enter Reim Sender to Receiver Information 
	And Enter Reim Narrative 
	And Applicant Bank 
	And Enter Requested Confirmation Party 
	
Scenario: Tenor tab 
	Given Click on Tenor tab 
	Then Verify values are displayed 
	Then Verify values are available by onchanges 
	And Enter Drawee ID 
	And Available With Bank 
	Then verify Available Bank details are displayed 
	
	
Scenario: Iss Goods tab validation 
	Given click on Goods tab 
	And Select Partial Shipment 
	And Select Transhipment 
	And Enter Iss Place of Taking in Charge 
	And Enter Iss Place of Final Destination 
	And Enter Iss Port of Loading 
	And Enter Iss Port of Discharge 
	And Enter Iss Latest Shipment Date 
	And Enter Iss Shipment Period 
	And Enter Iss Commodity Code 
	And Enter Iss Description of Goods and/or Services 
	
	
Scenario: Iss Docuemnt Tab 
	Given Click Documents Tab 
	And Enter values 
	
Scenario: Iss Instructions tab 
	Given Click Instructions tab 
	And Enter all the values 
	
Scenario: Iss Charges Tab Validation 
	Given Click Iss charges tab 
	And Select Iss Paid at value 
	And Enter Iss Acc No 
	
#Scenario: Advice Tab 
#	Given Click on Advice tab 
#	And click add button and verify onchanges 
#	And select type of message 
#	And Enter Bank ID 
#	And select Mail Method 
#	And Enter Narrative (MT n99 Tag 79Z) 
#	And Enter Narrative (Mail) 
#	And Click on customer subtab 
#	And select type of message and verify onchanges 
#	And select Customer Mail Method 
#	And Enter Customer ID 
#	And Enter narrative 
#	And Click on save 
	
Scenario: Iss Notes tab validation 
	Given Click on Notes tab 
	And Verify Notes details 
	
Scenario: Iss Diary tab validation 
	And Click Diary tab 
	And Enter related reference 
	And Enter diary narrative 
	#	And click viewhistory button and verify the diary details 
	
Scenario: Iss Output Validation Confirmation 
    Then validate the GAPI
	And Click Confirm button 
	Then Verify GAPI is generated 
	Then Verify Voucher is generated 
	Then Verify Swift is generated 
#	When Click On issswift button 
#	Then Validate the issswift MT700 
#	When Click On issreimswift button 
#	Then Validate the issreimswift MT740 
#	When Click On IssueAdvice Swift button 
#	Then Validate the IssueAdvice Swift
	And Click cancel button 
	And Release The Transaction 
	
	
Scenario: Register Amendment 
	Given click on Register Amendment 
	And Enter Increase Amount 
	And Enter Decrease Amount 
	And Enter New Tolerance 
	Then Verify New LC Balance and New LC Amount 
	And Enter New Date of Expiry 
	And Enter New Place Of Expiry 
	And Enter Detrimental Flag 
	And Enter Date of Amendment 
	And Enter New Additional Amounts Covered 
	When Click On ParitesI tab 
	And Enter the New Beneficiary 
	When Click On Notes tab 
	Then Verify notes are diaplayed 
	When Click On Diary tab 
	And Enter RegAmd Related Reference 
	And Enter RegAmd Narrative 
	And Verify RegAmd Diary History 
	And Click Confirm button 
	And Click cancel button 
	And Release The Transaction 
	
	
Scenario: Issue Amendment 
	Given click on Issue Amendment 
	Given Click On PartiesII tab 
	And Enter Issuing Bank ID 
	And Enter the Issuing Bank's Reference 
	And Enter New Confirmation Instruction 
	And Select New Applicable Rules 
	And Select the Reimburement Charges 
	And Enter the Reimbursing Bank's Reference 
	And Enter IssAmend Charges 
	And Enter the Sender to Receiver Information 
	And Enter 77 Narrative 
	And Enter New Requested Confirmation Party 
	And Enter New 'Advise Through' Bank 
	And Enter Non-Bank Issuer ID 
	Given Click on the Tenor tab 
	And Amend Available By 
	And Enter Tenor Days 
	And Enter Tenor Type 
	Then Verify Amend Available By Onchanges 
	And Enter the Amend Available With Bank 
	And Enter the Amend Drawee 
	Given Click On Shipment tab 
	And Select Amend Partial Shipment 
	And Select Amend Transhipment 
	And Enter Amend Place of Taking in Charge 
	And Enter Amend Place of Final Destination 
	And Enter Amend Port of Loading 
	And Enter Amend Port of Discharge 
	And Enter AmendLatest Shipment Date 
	And Enter AmendIncoterms 
	And Enter AmendShipment Period 
	Given Click On Goods tab 
	And Enter New Description of Goods and/or Services 
	Given Click On Documents tab 
	And Enter New Documents Required 
	Given Click On Additional Condition 
	And Enter Presentation Days 
	And Select Presentation Type 
	And Enter New Sender to Receiver Information 
	And Enter New Charges 
	And Select Amendment Charge Payable By 
	Then Amendment Charge Payable By Onchange 
	And Enter Instructions to Paying Bank 
	And Enter Additional Conditions 
	And Enter Special Payment Conditions for Beneficiary 
	And Enter Special Payment Conditions for Bank Only 
	Given Click On Charges tab 
	And  Enter Paid At 
	And Enter Paid By 
	And Enter AC/NO 
#	Given Click on IssAmd Advice tab 
#	And click on add button 
#	And Select the type of message 
#	And Enter the Bank ID 
#	And Select the  Mail Method 
#	And Enter the  Narrative (MT n99 Tag 79Z) 
#	And Enter the Narrative (Mail) 
#	And Click on the customer subtab 
#	And select  the type of message and verify onchanges 
#	And select the Customer Mail Method 
#	And Enter the Customer ID 
#	And Enter the narrative 
#	And Click on save 
	Given Click On IssAmd Notes tab 
	Then Verify Iss AMD notes 
	Given Click On IssAmd Diary tab 
	And Click Confirm button 
#	Then Issue Amd - Output - Swift Check 
#	Then Validate the MT707_Content 
#	Then Validate the Advice Swift 
	And Click cancel button 
	And Release The Transaction 
	
Scenario: Beneficiary Response 
	Given Click On Beneficiary Response Function 
	And Enter the Beneficiary Decision 
#	Given Click on BR Advice tab 
#	And click on add button in BR 
#	And Select the type of message in BR 
#	And Enter the Bank ID in BR 
#	And Select the  Mail Method in BR 
#	And Enter the  Narrative (MT n99 Tag 79Z) in BR 
#	And Enter the Narrative (Mail) in BR 
#	And Click on the customer subtab in BR 
#	And select  the type of message and verify onchanges in BR 
#	And select the Customer Mail Method in BR 
#	And Enter the Customer ID in BR 
#	And Enter the narrative in BR 
#	And Click on save 
	Then Verify the Notes in BR 
	And Enter the Diary details in BR 
	#	Then Verify the Diary History in BR 
	Then validate the GAPI in BR
	And Click Confirm button 
	Then BR - Output - GAPI Check
#	Then BR - Output - Swift Check 
#	Then validate the Swift in BR 
	And Click cancel button 
	And Release The Transaction 
	
Scenario: RegDoc Main tab validation 
	Given click on IPLC Presentation 
	And click on Register Document LC 
	And Enter Presentation Amount 
	And Enter Additional Amounts 
	And Enter Charges Deducted 
	And Enter Presenter Charges 
	And Verify Total Amount to be Paid 
	And Enter Presenter Reference 
Scenario: RegDoc Documents tab 
	Given Click on Documents Tab 
	And Enter the document details 
	
#Scenario: Register Docs Advice Tab 
##	Given Click on Reg Docs Advice tab 
##	And click Reg Docs add button and verify onchanges 
##	And select Reg Docs type of message 
##	And Enter Reg Docs Bank ID 
##	And select Reg Docs Mail Method 
##	And Enter Reg Docs Narrative (MT n99 Tag 79Z) 
##	And Enter Reg Docs Narrative (Mail) 
##	
##	And Click on Reg Docs customer subtab 
##	And select Reg Docs type of message and verify onchanges 
##	And Enter Reg Docs Customer ID 
##	And select Reg Docs Customer Mail Method 
##	And Enter Reg Docs narrative 
##	And Click on save 
	
Scenario: RegDoc Notes tab validation 
	Given click on notes tab 
	And Verify notes details 
Scenario: RegDoc Diary tab validation 
	And click on diary Tab 
	And Enter Related reference 
	And Enter Diary narrative 
	#	And click Viewhistory button and verify the diary details 
	
Scenario: RegDoc Confirmation and Output Validation 
    Then validate the GAPI in RegDoc
	And Click Confirm button 
	#	Then Verify RegDoc GAPI is generated 
	Then Verify RegDoc GAPI is generated
#	Then Verify RegDoc Swift is generated 
#	Then validate the Swift in RegDoc 
	And Click cancel button 
Scenario: RegDoc Supervisor Release 
	And Release The Transaction 
	
Scenario: CheckDoc Main tab validation 
	Given Click on Check Document 
	And Select Mail contains 
	And Select Document Status 
	And Enter Discrepancies Noted Field 
Scenario: CheckDoc Documents Tab 
	Given Click documents tab 
Scenario: CheckDoc Notes tab validation 
	Given click on notes tab in CheckDoc 
	And Verify notes details in CheckDoc 
Scenario: CheckDoc Diary tab validation 
	And click on diary Tab in CheckDoc 
	And Enter Related reference in CheckDoc 
	And Enter Diary narrative in CheckDoc 
	#	And click Viewhistory button and verify the diary details in CheckDoc 
	
Scenario: CheckDoc Confirmation
    Then validate the GAPI in CheckDoc 
	And Click Confirm button 
	Then Then Verify CheckDoc GAPI is generated 
	And Click cancel button 
Scenario: CheckDoc Supervisor Release 
	And Release The Transaction 
Scenario: RegDis Main tab validation 
	Given click on IPLC Discrepancies 
	And click on Register Discrepancies 
	And Verify LC Number 
	And Verify LC CCY 
	And Verify Presenter Reference 
	And Verify Presentation Date 
	And Verify Presentation Currency and Amount 
	#And Verify Presentation Amount
	And Verify Total amount to be paid 
	
Scenario: RegDis Parties tab validation 
	And click on Parties tab 
	And Select Documents Presented By 
	And Verify Presenter Bank Swift Address 
	
Scenario: RegDis Discrepancies tab validation 
	And click on Discrepancies tab 
	And Select Advise Applicant 
	And Select Document Released in Trust 
	And Select Advise Presenter 
	And Enter Sender to Receiver information 
	And Select Document Disposal 
	And Enter Charges Claimed 
	And Verify Discrepancies Noted 
	And Enter Account with Bank ID 
	
Scenario: RegDis Charges tab validation 
	Given Click on RegDis Charges Tab 
	And Select Paid At 
	And Enter the chrg Acc No 
	
Scenario: RegDis Advice Tab 
	Given Click on RegDis Advice tab 
	And click RegDis add button and verify onchanges 
	And select RegDis type of message 
	And Enter RegDis Bank ID 
	And select RegDis Mail Method 
	And Enter RegDis Narrative (MT n99 Tag 79Z) 
	And Enter RegDis Narrative (Mail) 
	And Click on RegDis customer subtab 
	And select RegDis type of message and verify onchanges 
	And select RegDis Customer Mail Method 
	And Enter RegDis Customer ID 
	And Enter RegDis narrative 
	And Click on save 
	
	
	
Scenario: RegDis Notes tab validation 
	Given click on notes tab in RegDis 
	And Verify notes details in RegDis 
	
Scenario: RegDis Diary tab validation 
	And click on diary Tab in RegDis 
	And Enter Related reference in RegDis 
	And Enter Diary narrative in RegDis 
	#	And click Viewhistory button and verify the diary details in RegDis	
	
Scenario: RegDis Confirmation and Output Validation 
	And Click Confirm button 
	Then Verify RegDis Voucher is generated 
	Then Verify RegDis Swift is generated 
	When click on swift button 
	Then validate swift_MT734 
	Then validate the Advice Swift in RegDis 
	And Click cancel button 
	
	
Scenario: DisRes Main tab validation 
	And click on Discrepancy Response MT752 
	And Select Applicant Response 
	And Select Further Identification 
	And Select Date of Advise of Discrepancy 
	
Scenario: DisResp Discrepancies tab validation 
	Given click on Discrepancies tab in DisRes 
	And Select Document disposal in DisRes
	
	#Scenario: DisResp Notes tab validation
	#Given click on notes tab in DisResp
	#And Verify notes details in DisResp
	
	#Scenario: DisRes Diary tab validation 
	#And click on diary Tab in DisRes
	#And Enter Related reference in DisRes
	#And Enter Diary narrative in DisRes
	#And click Viewhistory button and verify the diary details in DisRes	
	
Scenario: DisRes Confirmation and Output Validation 
	And Click Confirm button 
	When click on swift button
	Then click on swift button in DisRes 
	And Click cancel button 
	
Scenario: Pay/Accept MainTab 
	Given click on IPLC settlement 
	And Click on Pay/Accept function 
	And Select PayAcc Tenor Start Date 
	And Enter PayAcc Advising Bank Charges 
	And Enter PayAccReimbursement Bank Charges 
	
Scenario: Pay/Accept Acceptance tab validation 
	And Click on Acceptance tab 
	And verify Acceptance tab is displayed 
	And Select acceptance Msg 
	
Scenario: Pay/Accept Charges tab 
	And Click On PayAcc Charges tab 
	And Select PayAcc Paid By 
	And Select PayAcc Paid At 
	And Enter PayAcc Account Number 
	
Scenario: Pay/Accept - Payment tab 
	And Click On Payment tab 
	Then Check the Payment details 
	
Scenario: Pay/Accept Advice Tab 
	Given Click on Pay/Accept Advice tab 
	And click Pay/Accept add button and verify onchanges 
	And select Pay/Accept type of message 
	And Enter Pay/Accept Bank ID 
	And select Pay/Accept Mail Method 
	And Enter Pay/Accept Narrative (MT n99 Tag 79Z) 
	And Enter Pay/Accept Narrative (Mail) 
	And Click on Pay/Accept customer subtab 
	And select Pay/Accept type of message and verify onchanges 
	And select Pay/Accept Customer Mail Method 
	And Enter Pay/Accept Customer ID 
	And Enter Pay/Accept narrative 
	And Click on save 
	
	
Scenario: Pay/Accept Notes tab validation 
	Given click on notes tab in PayAccept 
	And Verify notes details in PayAccept 
	
Scenario: Pay/Accept Diary tab validation 
	And click on diary Tab in PayAccept 
	And Enter Related reference in PayAccept 
	And Enter Diary Narrative in PayAccept 
	
Scenario: Pay/Accept - Confirm and Release 
	And Click Confirm button 
	Then Verify Swift is generated in Payacc 
	Then validate the Advice Swift in RegDis 
	And Click cancel button 
	And Release The Transaction 
	
Scenario: Payment at Maturity Main tab validation 
	Given click on Payment at Maturity 
	And Select Take charges separately 
	And Verify LC number 
	And Verify Total amount claimed 
	And Verify Debit amount 
	And Verify Credit amount 
	
Scenario: Payment At Maturity Charges tab 
	And Pay at Maturity Charges Tab validation 
	
Scenario: Payment at Maturity - Payment tab 
	When Click On Payment tab at Payment at Maturity 
	Then Verify the debit details 
	When Add the Debit Entry 
	Then Enter Debit Percentage and verify onchange 
	And Enter the DebitCcy 
	Then Enter the CcyAmount 
	Then Verify Debit Amount in Trx CCY 
	And Enter the Debit Value Date 
	And Enter the Account Type 
	And Click Save button 
	When Click Credit subtab 
	And Click the Add button 
	Then Enter Credit Percentage and verify onchange 
	And Enter the CreditCcy 
	Then Enter the Credit CcyAmount 
	Then Verify Credit Amount in Trx CCY 
	And Enter the Credit Value Date 
	And Enter the Credit Account Type 
	Then Select Payment Advice Message 
	And select GPI Member 
	Then Select Payment Cover Message 
	When Click On 103 sub-tab 
	#And Verify Credit CCY and Amount
	#And Verify Credit Ccy and Amount
	And Enter Receiver ID 
	Then Select ordering customer type and veriy onchanges 
	And Enter the ordering customer ID 
	And Enter SenderCorrespondent ID 
	And Enter Receivers Correspondent ID 
	And Enter Intermediatory Institution ID 
	And Enter Account with Institution ID 
	And Enter Beneficiary Customer ID 
	And Select Bank Opreation Code 
	And Select Details of Charges 
	And Enter Sender Charges 
	
	And Click On MT202 tab 
	And Enter the Receiver Bank Id 
	And Enter the Ordering Institution Id 
	And Enter the SenderCorrespondent ID 
	And Enter the Receivers Correspondent ID 
	And Enter the Intermediatory Institution ID 
	And Enter the Account with Institution ID 
	And Enter the Beneficiary insitution ID 
	And Click Save Button 
	
Scenario: Payment at Maturity Narrative tab validation 
	Given click on Narrative tab 
	And Select SWIFT Send To 
	
	#Scenario: Payment at Maturity Notes tab validation 
	#Given click on notes tab in Payment at Maturity 
	#And Verify notes details in Payment at Maturity 
	
	#Scenario: Payment at Maturity Diary tab validation 
	#And click on diary Tab in Payment at Maturity 
	#And Enter Related reference in Payment at Maturity 
	#And Enter Diary narrative in Payment at Maturity 
	
Scenario: Payment at Maturity - Confirm and Release 

	And  Click Confirm button 
	When click on swift button in payment at Maturity 
	Then Validate the swift MT103 
	Then Validate Swift MT202