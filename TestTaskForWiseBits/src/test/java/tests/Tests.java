package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.SQLPage;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Tests {

    private SQLPage sqlPage;
    private WebDriver driver;

    @BeforeTest
    public void setup() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterTest
    public void close() {
        driver.close();
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get("https://www.w3schools.com/sql/trysql.asp?filename=trysql_select_all");
        sqlPage = new SQLPage(driver);
    }

    @Test(groups = "Sql tests")
    public void quickCheckUI(){
        Assert.assertTrue(sqlPage.runButtonExist(),"no run button");
        Assert.assertTrue(sqlPage.resultExist(), "no result field");
    }

    @Test(groups = "Sql tests", priority = 1)
    public void One() {
        String sqlString = "SELECT * FROM Customers";
        sqlPage.setToSqlField(sqlString);
        sqlPage.clickOnRunButton();
        Assert.assertEquals(sqlPage.getLocationByCustomName("Giovanni Rovelli"),"Via Ludovico il Moro 22", sqlPage.getLocationByCustomName("Giovanni Rovelli") + " not equal " + "Via Ludovico il Moro 22");

    }

    @Test(groups = "Sql tests", priority = 2)
    public void Two() {
        String sqlString = "SELECT * FROM Customers WHERE city=\"London\"";
        sqlPage.setToSqlField(sqlString);
        sqlPage.clickOnRunButton();
        int size = sqlPage.listOfTraces().size();
        Assert.assertEquals(sqlPage.getNumberOfRecords(),"Number of Records: 6");
        Assert.assertEquals(size , 7, "size: " + size + " is equal 7");

    }

    @Test(groups = "Sql tests", priority = 3)
    public void Three() {
        String sqlString = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES (\"Wolverine\", \"Logan\", \"X-Men base\", \"Boston\", \"123456\", \"USA\")";
        sqlPage.setToSqlField(sqlString);
        sqlPage.clickOnRunButton();
        sqlString = "SELECT * FROM Customers WHERE CustomerName=\"Wolverine\"";
        sqlPage.setToSqlField(sqlString);
        sqlPage.clickOnRunButton();
        Assert.assertEquals(sqlPage.getNumberOfRecords(),"Number of Records: 1");
        Assert.assertEquals(sqlPage.getTrace("Logan").findElement(By.xpath("./td[4]")).getText(), "X-Men base");
    }

    @Test(groups = "Sql tests", priority = 4)
    public void Four() {
        String sqlString = "UPDATE Customers SET ContactName = \"Alfred Schmidt\", City= \"Frankfurt\" WHERE CustomerID = 1";
        sqlPage.setToSqlField(sqlString);
        sqlPage.clickOnRunButton();
        sqlString = "SELECT * FROM Customers WHERE CustomerName=\"Wolverine\"";
        sqlPage.setToSqlField(sqlString);
        sqlPage.clickOnRunButton();
        Assert.assertTrue(sqlPage.getNumberOfRecords().contains("1"));
        Assert.assertEquals(sqlPage.getTrace("Logan").findElement(By.xpath("./td[4]")).getText(), "X-Men base");
    }


    @Test(groups = "SQL tests", priority = 5)
    public void Five() {
        String sqlString = "DELETE FROM Customers WHERE CustomerName=\"Wolverine\"";
        sqlPage.setToSqlField(sqlString);
        sqlPage.clickOnRunButton();

        sqlString = "SELECT * FROM Customers WHERE CustomerName=\"Wolverine\"";
        sqlPage.setToSqlField(sqlString);
        sqlPage.clickOnRunButton();

        Assert.assertEquals(sqlPage.getResultText(), "No result.");
    }
}