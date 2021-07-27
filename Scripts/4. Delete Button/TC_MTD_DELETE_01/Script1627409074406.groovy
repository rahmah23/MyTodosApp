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

//Add to-do list
for (def rowNum = 1; rowNum <= findTestData('NewTodoList').getRowNumbers(); rowNum++) {
	//Create new to-do
	String newText = findTestData('NewTodoList').getValue(1, rowNum)

	WebUI.setText(findTestObject('My-todos_OR/Page_vue-todos/input_My to-dos_form-control'), newText)

	WebUI.sendKeys(findTestObject('My-todos_OR/Page_vue-todos/input_My to-dos_form-control'), Keys.chord(Keys.ENTER))
}

//Get random list in to-do list
Random rand = new Random()

int rowCount = findTestData('NewTodoList').getRowNumbers()

rowNum = (rand.nextInt(rowCount - 1) + 1)

String deletedList = findTestData('NewTodoList').getValue(1, rowNum)

//Click Delete button
String xpathDelete = ('(.//*[normalize-space(text()) and normalize-space(.)="' + deletedList) + '"])[1]/following::button[2]'

TestObject deleteButton = new TestObject('deleteButton')

deleteButton.addProperty('xpath', ConditionType.EQUALS, xpathDelete)

WebUI.delay(1)

WebUI.click(deleteButton)

WebUI.clickOffset(findTestObject('My-todos_OR/Page_vue-todos/blank_space'), 100, 100)

//Verify deleted list
String xpathList = ('(.//*[normalize-space(text()) and normalize-space(.)="' + deletedList) + '"])[1]/following::li[1]'

TestObject removedList = new TestObject('removedList')

removedList.addProperty('xpath', ConditionType.EQUALS, xpathList)

String xpathEdit = ('(.//*[normalize-space(text()) and normalize-space(.)="' + deletedList) + '"])[1]/following::button[1]'

TestObject editButton = new TestObject('editButton')

editButton.addProperty('xpath', ConditionType.EQUALS, xpathEdit)

WebUI.verifyElementNotPresent(removedList, 0)

WebUI.verifyElementNotPresent(deleteButton, 0)

WebUI.verifyElementNotPresent(editButton, 0)

WebUI.delay(3)

WebUI.closeBrowser()