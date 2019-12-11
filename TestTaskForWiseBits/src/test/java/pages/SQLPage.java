package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class SQLPage {

    private WebDriver driver;
    private By runButton = By.cssSelector(".w3-green.w3-btn");
    private By result = By.cssSelector("div#divResultSQL");
    private By resultText = By.cssSelector("div#divResultSQL div");
    private By numberOfRecords = By.cssSelector("#divResultSQL div div");
    public SQLPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean runButtonExist() {
        return driver.findElement(runButton).isDisplayed();
    }

    public boolean resultExist() {
        return driver.findElement(result).isDisplayed();
    }

    public void setToSqlField(String sql) {
        ((JavascriptExecutor) driver).executeScript("window.editor.setValue('"+sql+"')");
    }

    public void clickOnRunButton(){
        driver.findElement(runButton).click();
    }

    public WebElement getTable () {
        return driver.findElement(result);
    }

    public WebElement getTrace (String string) {
        return driver.findElement(result).findElement(By.xpath(".//tr[td[text()='"+string+"']]"));
    }
    public  String getLocationByCustomName(String string) {
        return driver.findElement(result).findElement(By.xpath(".//tr[td[text()='"+string+"']]/td[4]")).getText();
    }

    public String getNumberOfRecords(){
        return driver.findElement(numberOfRecords).getText();
    }

    public List<WebElement> listOfTraces(){
        return driver.findElement(result).findElements(By.xpath(".//tr"));
    }

    public String getResultText(){
        return driver.findElement(resultText).getText();
    }
}