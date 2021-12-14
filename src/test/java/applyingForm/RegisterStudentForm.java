package applyingForm;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RegisterStudentForm {

        WebDriver driver;
        @Test
        void registrationForm() throws IOException {


            File file = new File("src/test/java/applyingForm/DataForRegistrationForm.xlsx");
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            XSSFCell cell = sheet.getRow(i).getCell(0);
            DataFormatter formatter = new DataFormatter();
            String FirstName = formatter.formatCellValue(cell);
            cell = sheet.getRow(i).getCell(1);
            String LastName = formatter.formatCellValue(cell);
            cell = sheet.getRow(i).getCell(2);
            String Email = formatter.formatCellValue(cell);
            cell = sheet.getRow(i).getCell(3);
            String MobileNo = formatter.formatCellValue(cell);
            cell = sheet.getRow(i).getCell(4);
            String Subjects = formatter.formatCellValue(cell);

            driver.findElement(By.id("firstName")).sendKeys(FirstName);
            driver.findElement(By.id("lastName")).sendKeys(LastName);
            driver.findElement(By.id("userEmail")).sendKeys(Email);
            driver.findElement(By.xpath("//div[@id='genterWrapper']/div[2]/div[1]")).click();
            driver.findElement(By.id("userNumber")).sendKeys(MobileNo);
            driver.findElement(By.id("dateOfBirthInput")).click();
            new Select(driver.findElement(By.className("react-datepicker__month-select"))).selectByVisibleText("January");
            new Select(driver.findElement(By.className("react-datepicker__year-select"))).selectByVisibleText("1999");
            driver.findElement(By.cssSelector(".react-datepicker__day--009")).click();
            driver.findElement(By.id("subjectsInput")).sendKeys(Subjects);
            driver.findElement(By.cssSelector(".custom-checkbox:nth-child(3) > .custom-control-label")).click();
            Actions actions=new Actions(driver);
            actions.sendKeys(Keys.PAGE_DOWN).perform();
            driver.findElement(By.xpath("//*[@id=\"state\"]/div/div[1]")).click();
            driver.findElement(By.id("react-select-3-option-2")).click();
            driver.findElement(By.id("submit")).click();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//div[@class='modal-footer']/button")).click();


        }
    }

   @BeforeTest
    void openurl() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/automation-practice-form");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"close-fixedban\"]/img")).click();
    }

    @AfterTest
    void closeBrowser() {
        System.out.println("Registration Successfully");
        driver.quit();
    }
}