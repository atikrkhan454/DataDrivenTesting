package applyingForm;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestLogin {

    WebDriver driver;
    @Test
    void testPage() throws IOException {

        File file = new File("src/test/java/applyingForm/user.xlsx");
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++){

            XSSFCell cell = sheet.getRow(i).getCell(0);
            DataFormatter formatter = new DataFormatter();
            String username = formatter.formatCellValue(cell);
            cell = sheet.getRow(i).getCell(1);
            String password = formatter.formatCellValue(cell);


            driver.findElement(By.name("userName")).sendKeys(username);
            driver.findElement(By.name("password")).sendKeys(password);
            driver.findElement(By.name("submit")).click();
            driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
            Assert.assertTrue(driver.findElement(By.linkText("SIGN-OFF")).isDisplayed());
            driver.findElement(By.linkText("SIGN-OFF")).click();

        }


    }

    @BeforeTest
    void openurl() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://demo.guru99.com/test/newtours/index.php");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @AfterTest
    void closeBrowser() {
        //driver.quit();
    }
}
