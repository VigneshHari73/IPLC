package Design;

import java.text.ParseException;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;





public interface Element {
	
	/**
	 * This method will click the element and take snap
	 * @param ele   - The Webelement (button/link/element) to be clicked
	 * @see locateElement method in Browser Class
	 * @author Vignesh - Unique Force
	 * @throws StaleElementReferenceException
	 */
	
	public void click(WebElement ele);
	
	
	/**
	 * This method will enter the value in the given text field 
	 * @param ele   - The Webelement (text field) in which the data to be entered
	 * @param data  - The data to be sent to the webelement
	 * @see locateElement method in Browser Class
	 * @author Vignesh - Unique Force
	 * @throws ElementNotInteractable,IllegalArgumentException(throws if keysToSend is null)	
	 */
	public void append(WebElement ele, String data);
	
	/**
	 * This method will clear the value in the given text field 
	 * @param ele   - The Webelement (text field) in which the data to be entered
	 * @see locateElement method in Browser Class
	 * @author Ganesh Kumar - Unique Force
	 * @throws InvalidElementStateException	(throws if not user-editable element)	 
	 */
	public void clear(WebElement ele);
	
	/**
	 * This method will clear and type the value in the given text field 
	 * @param ele   - The Webelement (text field) in which the data to be entered
	 * @param data  - The data to be sent to the webelement
	 * @see locateElement method in Browser Class
	 * @author Lavanya - Unique Force
	 * @throws ElementNotInteractable,IllegalArgumentException(throws if keysToSend is null)		 
	 */
	public void clearAndType(WebElement ele,String data);
	
	/**
	 * This method will get the visible text of the element
	 * @param ele   - The Webelement (button/link/element) in which text to be retrieved
	 * @author Kowsalya - Unique Force
	 * @see locateElement method in Browser Class
	 */
	public String getElementText(WebElement ele);	
	
	/**
	 * This method will get the Color values of the element
	 * @param ele   - The Webelement (button/link/element) in which text to be retrieved
	 * @see locateElement method in Browser Class
	 * @author Ganesh Kumar - Unique Force	
	 * @return The visible text of this element.
	 */
	public String getBackgroundColor(WebElement ele);
	
	/**
	 * This method will get the text of the element textbox
	 * @param ele   - The Webelement (button/link/element) in which text to be retrieved
	 * @see locateElement method in Browser Class
	 * @author Lavanya - Unique Force
	 * @return The attribute/property's current value (or) null if the value is not set.
	 */
	public String getTypedText(WebElement ele);
	

	/**
	 * This method will select the drop down visible text
	 * @param ele   - The Webelement (dropdown) to be selected
	 * @param value The value to be selected (visibletext) from the dropdown
	 * @see locateElement method in Browser Class 
	 * @author Kowsalya - Unique Force
	 * @throws NoSuchElementException
	 */
	public void selectDropDownUsingText(WebElement ele, String value) ;
	
	/**
	 * This method will select the drop down using index
	 * @param ele   - The Webelement (dropdown) to be selected
	 * @param index The index to be selected from the dropdown
	 * @see locateElement method in Browser Class
	 * @author Vignesh - Unique Force
	 * @throws NoSuchElementException
	 */
	public void selectDropDownUsingIndex(WebElement ele, int index) ;
	
	/**
	 * This method will select the drop down using index
	 * @param ele   - The Webelement (dropdown) to be selected
	 * @param value - The value to be selected (value) from the dropdown 
	 * @see locateElement method in Browser Class
	 * @author Ganesh Kumar - Unique Force
	 * @throws NoSuchElementException
	 */
	public void selectDropDownUsingValue(WebElement ele, String value) ;
	
	/**
	 * This method will verify exact given text with actual text on the given element
	 * @param ele   - The Webelement in which the text to be need to be verified
	 * @param expectedText  - The expected text to be verified
	 * @author Kowsalya - Unique Force
	 * @see locateElement method in Browser Class
	 * @return true if the given object represents a String equivalent to this string, false otherwise
	 */
	public boolean verifyExactText(WebElement ele, String expectedText); 
	/* public boolean amountValidation(String lcAmtTxt); */
	
	/**
	 * This method will verify given text contains actual text on the given element
	 * @param ele   - The Webelement in which the text to be need to be verified
	 * @param expectedText  - The expected text to be verified
	 * @author Lavanya - Unique Force
	 * @see locateElement method in Browser Class
	 * @return true if this String represents the same sequence of characters as the specified string, false otherwise
	 */
	public boolean verifyPartialText(WebElement ele, String expectedText);

	/**
	 * This method will verify exact given attribute's value with actual value on the given element
	 * @param ele   - The Webelement in which the attribute value to be need to be verified
	 * @param attribute  - The attribute to be checked (like value, href etc)
	 * @param value  - The value of the attribute
	 * @author Vignesh - Unique Force
	 * @see locateElement method in Browser Class
	 * @return true if this String represents the same sequence of characters as the specified value, false otherwise
	 */
	public boolean verifyExactAttribute(WebElement ele, String attribute, String value);
	
	/**
	 * This method will verify partial given attribute's value with actual value on the given element
	 * @param ele   - The Webelement in which the attribute value to be need to be verified
	 * @param attribute  - The attribute to be checked (like value, href etc)
	 * @param value  - The value of the attribute
	 * @author Ganesh Kumar - Unique Force
	 * @see locateElement method in Browser Class
	 * @return true if this String represents the same sequence of characters as the specified value, false otherwise
	 * 
	 */
	public void verifyPartialAttribute(WebElement ele, String attribute, String value);
	
	/**
	 * This method will verify if the element is visible in the DOM
	 * @param ele   - The Webelement to be checked
	 * @author Kowsalya - Unique Force
	 * @see locateElement method in Browser Class
	 * @return true if the element is displayed or false otherwise
	 */
	public boolean verifyDisplayed(WebElement ele);
	
	/**
	 * This method will checking the element to be invisible
	 * @param ele   - The Webelement to be checked
	 * @author Lavanya - Unique Force
	 * @see locateElement method in Browser Class
	 */
	public boolean verifyDisappeared(WebElement ele);	
	
	/**
	 * This method will verify if the input element is Enabled
	 * @param ele   - The Webelement (Radio button, Checkbox) to be verified
	 * @return true - if the element is enabled else false
	 * @author Vignesh - Unique Force
	 * 
	 * @see locateElement method in Browser Class
	 * @return True if the element is enabled, false otherwise.
	 */
	public boolean verifyEnabled(WebElement ele);	
	
	/**
	 * This method will verify if the element (Radio button, Checkbox) is selected
	 * @param ele   - The Webelement (Radio button, Checkbox) to be verified
	 * @author Ganesh Kumar - Unique Force
	 * @see locateElement method in Browser Class
	 * @return True if the element is currently selected or checked, false otherwise.
	 */
	public void verifySelected(WebElement ele);

	public String trimNumbers(String value);
	
	public String value(String value);
	
	public String log(String result);
	
	public boolean lengthValidation(WebElement ele, int limit, String data, String ssName) throws InterruptedException;	
	
	public boolean lengthValidation2(WebElement ele, String limit, String data, String ssName) throws InterruptedException;
	
	public boolean isMandatory(WebElement ele, String methodname) throws InterruptedException;
	
	public boolean verifyNarrativeEnable(WebElement eleNarrative, String value, String methodName);
	
    public boolean verifyNarrativeDisable(WebElement eleNarrative, String value, String methodName);	
    
    public void dateCheck(WebElement ele, String data, String dateName ) throws InterruptedException, ParseException ;   
    
    public void albhaCheck(WebElement ele, String data, String dateName);
    
    public boolean negativeValue(Double Amount, String ssName);
    
    public void alphaValueCheck(WebElement ele, String ssName) throws InterruptedException;
      
    public void manualEntry(WebElement eleBank,String bankliaAcc);
    
    public void datevalidations(WebElement ele,String elenarrative) throws InterruptedException;
    
    public void confirm() throws InterruptedException;
    
    public void cancel() throws InterruptedException;
    
	public void release(String data) throws InterruptedException;
	
	public void catalog(String data) throws InterruptedException;
	
	public void swiftContent(String exp, String act, String ssName);
	
	public void swiftTag(String act, String exp, String ssName);
	
	public String curSts(String functionName);
	
	public String nxtSts(String functionName);
	
	public void gapiContent(String act, String exp, String ssName);
	
	public void gapiValidation(String functionName) throws InterruptedException;
	
	public String adviceBic(String functionName);
		
	public String releatedRef(String functionName);
	
	public String narrtive(String functionName);
	
	public void swiftValidation(String functionName) throws InterruptedException;
	
	public String subMess(String functionName);
	
	
	
}
	
