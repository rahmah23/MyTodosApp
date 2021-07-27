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

String selectedList = findTestData('NewTodoList').getValue(1, rowNum)

//Get to-do list xpath
String xpath = ('(.//*[normalize-space(text()) and normalize-space(.)="' + selectedList) + '"])[1]/following::button[3]'

TestObject completeList = new TestObject('completeList')

completeList.addProperty('xpath', ConditionType.EQUALS, xpath)

//Mark list to-do as complete
WebUI.click(completeList)

//Get CSS 'text-decoration' propery of complete list
String textDecoration = WebUI.getCSSValue(completeList, 'text-decoration')

//Verify CSS 'text-decoration' propery value contains line-through
WebUI.verifyMatch(textDecoration, '.*(line-through).*', true)

WebUI.delay(3)

//Mark complete list to-do as incomplete
WebUI.click(completeList)

WebUI.delay(1)

//Get CSS 'text-decoration' propery of incomplete list
String textDecoration2 = WebUI.getCSSValue(completeList, 'text-decoration')

//Verify CSS 'text-decoration' propery value doesn't contain line-through
WebUI.verifyNotMatch(textDecoration2, '.*(line-through).*', true)

WebUI.delay(3)

WebUI.closeBrowser()

