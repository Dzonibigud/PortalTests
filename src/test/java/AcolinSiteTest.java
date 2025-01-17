import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class AcolinSiteTest {
    WebDriver driver;
    private static final String BASE_URL = "https://acolin.com/";

    @BeforeEach
    void setUp () {
        driver = new ChromeDriver();
        driver.get(BASE_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
    }
    @AfterEach
    void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }

    @Test
    void emailTest() throws InterruptedException {
        Actions action = new Actions(driver);
        WebElement submit = driver.findElement(By.className("wpforms-submit"));
        action.moveToElement(submit).perform();
        Thread.sleep(3000);
        WebElement cookies = driver.findElement(By.xpath("//button[text()='Accept']"));
        cookies.click();

        WebElement fName = driver.findElement(By.id("wpforms-1900-field_11"));
        action.moveToElement(fName).sendKeys(fName, "Nikola").perform();

        WebElement lName = driver.findElement(By.id("wpforms-1900-field_11-last"));
        action.moveToElement(lName).sendKeys(lName, "Jankovic").perform();

        WebElement mail = driver.findElement(By.id("wpforms-1900-field_2"));
        action.moveToElement(mail).sendKeys(mail, "nikola.jankovic@acolin.com").perform();

        WebElement companyName = driver.findElement(By.id("wpforms-1900-field_3"));
        action.moveToElement(companyName).sendKeys(companyName, "acolin").perform();

        WebElement phNumber = driver.findElement(By.id("wpforms-1900-field_4"));
        action.moveToElement(phNumber).sendKeys(phNumber, "12345677689").perform();

        WebElement question = driver.findElement(By.id("wpforms-1900-field_5"));
        action.moveToElement(question).sendKeys(question, "How Are YOOU").perform();

        WebElement checkBox = driver.findElement(By.xpath("//input[@type='checkbox']"));
        checkBox.click();

        WebElement reCaptcha = driver.findElement(By.id("checkbox"));
        reCaptcha.click();

        submit.click();
    }


}
