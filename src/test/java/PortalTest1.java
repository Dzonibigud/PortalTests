import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import javax.swing.*;
import java.time.Duration;


public class PortalTest1 {
        WebDriver driver;
        private static final String BASE_URL = "https://stage.portal.acolin.com/sign-in";

        @BeforeEach
        void setUp () {
            driver = new ChromeDriver();

        }

        @AfterEach
        void tearDown() throws InterruptedException{
            Thread.sleep(2000);
            driver.quit();
        }
        @Test
        void openHomePageTest(){
            driver.get(BASE_URL);
            driver.manage().window().maximize();
            String curentUrl = driver.getCurrentUrl();
            Assertions.assertEquals(BASE_URL, curentUrl);

        }
        @Test
        void openTabs() throws InterruptedException {
            Actions action = new Actions(driver);
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            driver.get(BASE_URL);
            driver.manage().window().maximize();
            WebElement clientPortal = driver.findElement(By.xpath("//h1[text()='Client Portal']"));
            longWait.until(ExpectedConditions.visibilityOf(clientPortal));
            WebElement emailField = driver.findElement(By.xpath("//input[@name='email']"));
            action.click(emailField).perform();
            Thread.sleep(1000);
            action.sendKeys(emailField,"nikola.jankovic@acolin.com").perform();
            Thread.sleep(2000);
            WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
            action.sendKeys(passwordField,"").perform();
            Thread.sleep(1000);
            WebElement signInButton = driver.findElement(By.xpath("//button[text()='Sign In']"));
            signInButton.click();
            Thread.sleep(19000);
            List<WebElement> tabs = driver.findElements(By.xpath("//dd[@class ='acl-menu-list__item']"));
            WebElement test = tabs.get(14).findElement(By.xpath(".//a"));
            action.moveToElement(tabs.get(14).findElement(By.linkText(tabs.get(14).getText()))).click().perform();
            Thread.sleep(2000);
            Assertions.assertTrue(driver.getCurrentUrl().equals(test.getAttribute("href")));
            Thread.sleep(2000);
        }
}


