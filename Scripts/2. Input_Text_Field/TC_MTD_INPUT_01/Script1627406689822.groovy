import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.testobject.ConditionType as ConditionType

WebUI.openBrowser(GlobalVariable.URL)

for (def rowNum = 1; rowNum <= findTestData('NewTodoList').getRowNumbers(); rowNum++) {
	//Create new to-do
	String newText = findTestData('NewTodoList').getValue(1, rowNum)

	WebUI.setText(findTestObject('My-todos_OR/Page_vue-todos/input_My to-dos_form-control'), newText)

	WebUI.sendKeys(findTestObject('My-todos_OR/Page_vue-todos/input_My to-dos_form-control'), Keys.chord(Keys.ENTER))

	//Verify New Text
	WebUI.verifyElementText(findTestObject('My-todos_OR/Page_vue-todos/li_activity', [('activity') : newText]), newText)

	//Verify Edit Button
	String xpathEdit = ('(.//*[normalize-space(text()) and normalize-space(.)="' + newText) + '"])[1]/following::button[1]'

	TestObject editButton = new TestObject('editButton')

	editButton.addProperty('xpath', ConditionType.EQUALS, xpathEdit)

	WebUI.verifyElementPresent(editButton, 0)

	//Verify Delete Button
	String xpathDelete = ('(.//*[normalize-space(text()) and normalize-space(.)="' + newText) + '"])[1]/following::button[2]'

	TestObject deleteButton = new TestObject('deleteButton')

	deleteButton.addProperty('xpath', ConditionType.EQUALS, xpathDelete)

	WebUI.verifyElementPresent(deleteButton, 0)
}

WebUI.delay(3)

WebUI.closeBrowser()

