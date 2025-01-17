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

import java.time.Duration;
import java.util.List;

public class fundPublicationTest {
    WebDriver driver;
    private static final String BASE_URL = "https://stage.fundpublications.com/";

    @BeforeEach
    void setUP(){
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
    void test(){
        Actions action = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        List<WebElement> client = driver.findElements(By.xpath("//input[@type='radio']"));
        action.moveToElement(client.get(1)).click().perform();
        WebElement agree = driver.findElement(By.id("agree"));
        action.moveToElement(agree).click().perform();
        WebElement confirm = driver.findElement(By.xpath("//button[text()='Confirm identification']"));
        action.moveToElement(confirm).click().perform();

        WebElement funds = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[text()='Funds']"))));
        funds.click();

        List<WebElement> shareClasses = driver.findElements(By.className("ctFmYayhVsct7TplANfS"));
        for(int i=0;i<shareClasses.size();i++){
            WebElement sc = driver.findElements(By.className("ctFmYayhVsct7TplANfS")).get(i);
            action.moveToElement(sc).click().perform();
            //WebElement details = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h3[text()='Details']"))));
            WebElement tabs = driver.findElement(By.xpath("//div[@class='SzIzgxeetVRZlB1UHWk5 ZZfYqpmGJ8Uz6r5QIwA7']"));
            action.moveToElement(tabs);
            List<WebElement> tabList = tabs.findElements(By.xpath(".//a"));
            for(int j=0;j< tabList.size();j++){
                WebElement t = driver.findElements(By.xpath("//div[@class='SzIzgxeetVRZlB1UHWk5 ZZfYqpmGJ8Uz6r5QIwA7']//a")).get(j);
                action.moveToElement(t).click().perform();
                Assertions.assertTrue(driver.getPageSource().contains(t.getText()));
            }
            driver.navigate().back();driver.navigate().back();driver.navigate().back();
        }
    }

}
/* for(WebElement sc: shareClasses){
            action.moveToElement(sc).click().perform();
            WebElement details = driver.findElement(By.xpath("//h3[text()='Details']"));
            Assertions.assertEquals("Details",details.getText());
            WebElement backToFunds = driver.findElement(By.className("MQ4X_aPTn6FF6zh0BNtZ"));
            action.moveToElement(backToFunds).click().perform();*/