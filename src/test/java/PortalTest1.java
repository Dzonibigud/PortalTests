import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import org.openqa.selenium.Keys;
import java.time.Duration;


public class PortalTest1 {
        WebDriver driver;
        private static final String BASE_URL = "https://stage.portal.acolin.com/sign-in";
        private static int j = 0;
        private static int k = 0;
        public void logIn() throws InterruptedException {
            Actions action = new Actions(driver);
            WebElement emailField = driver.findElement(By.xpath("//input[@name='email']"));
            action.sendKeys(emailField,"nikola.jankovic@acolin.com").perform();
            WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
            action.sendKeys(passwordField,"").perform();
            WebElement signInButton = driver.findElement(By.xpath("//button[text()='Sign In']"));
            signInButton.click();

        }

        @BeforeEach
        void setUp () {
            driver = new ChromeDriver();
            driver.get(BASE_URL);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
        }

        @AfterEach
        void tearDown() throws InterruptedException{
            Thread.sleep(2000);
            driver.quit();
        }
        @Test // Testing of just opening the page
        void openHomePageTest(){
            String currentUrl = driver.getCurrentUrl();
            Assertions.assertEquals(BASE_URL, currentUrl);

        }
        @Test // Testing if it redirects us to the home page after login in
        void logInTest() throws InterruptedException {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            logIn();
            WebElement clientPortal = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1[text()='Client Portal']"))));
            Assertions.assertEquals("Client Portal", clientPortal.getText());
        }
        @Test // Testing if redirects us to sign in page after logging out
        void logOutTest() throws InterruptedException {
            logIn();
            WebElement logOut = driver.findElement(By.xpath("//i[@data-icon='logout-1']"));
            logOut.click();
            Assertions.assertEquals("https://stage.portal.acolin.com/sign-in",driver.getCurrentUrl());
        }


        @Test // Testing of all tabs that are located on the left side of the page
        void openTabs() throws InterruptedException {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Actions action = new Actions(driver);
            WebElement clientPortal = driver.findElement(By.xpath("//h1[text()='Client Portal']"));
            wait.until(ExpectedConditions.visibilityOf(clientPortal));
            logIn();
            List<WebElement> tabs = driver.findElements(By.xpath("//dd[@class ='acl-menu-list__item']"));

            for(WebElement t : tabs){
                action.moveToElement(t.findElement(By.linkText(t.getText()))).click().perform();
                Assertions.assertTrue(driver.getCurrentUrl().equals(t.findElement(By.xpath(".//a")).getAttribute("href")));
            }
        }
        @Test //Testing of Funds page if there are all named filetrs that should be there(Fund name, ISIN, LifeCycle)
        void fundsTabTest() throws InterruptedException{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            boolean check=false;
            logIn();
            WebElement funds = driver.findElement(By.xpath("//a[text() = 'Funds']"));
            funds.click();
            Thread.sleep(1200);
            //wait.until(ExpectedConditions.textToBe(By.xpath("//h1[@data-test-product-name]"), "Fund Name"));
            List<WebElement> filters = driver.findElements(By.xpath("//span[@class = 'k-cell-inner']"));
            for(WebElement f :filters){
                if(driver.getPageSource().contains(f.getText())){
                    check=true;
                }
            }
            Assertions.assertTrue(check);
        }
        @Test // Testing of Collapse All button and its functionality
        void fundsCollapseAllTest() throws InterruptedException {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            boolean check = false;
            logIn();
            WebElement funds = driver.findElement(By.xpath("//a[text() = 'Funds']"));
            funds.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()= 'Collapse all']")));
            WebElement collapseAllButton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[text()= 'Collapse all']"))));
            collapseAllButton.click();
            List<WebElement> selektor = driver.findElements(By.xpath("//span[@class='k-icon k-svg-icon k-svg-i-caret-alt-right k-treelist-toggle']"));
            for(WebElement dd : selektor){
                if(dd.findElement(By.xpath("./..")).getAttribute("aria-expanded").equals("false")){
                    check = true;
                }
            }
            Assertions.assertTrue(check);
        }
        @Test // Testing of Back To Top button adn its functionality
        void fundsBackToTopTest() throws InterruptedException {
            Actions action = new Actions(driver);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            logIn();
            WebElement funds = driver.findElement(By.xpath("//a[text() = 'Funds']"));
            funds.click();
            WebElement footer = driver.findElement(By.xpath("//footer[@class='acl-footer']"));
            action.scrollToElement(footer).perform();
            WebElement backToTopButton = driver.findElement(By.xpath("//div[@class = 'acl-back-to-top acl-back-to-top--show']"));
            backToTopButton.click();
            Thread.sleep(1000);
            WebElement logo = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[@title='Acolin Client Portal']"))));
            logo.click();
            Assertions.assertEquals("https://stage.portal.acolin.com/home", driver.getCurrentUrl());
        }

        @RepeatedTest(4) //Testing of filters Fund Name, Isin, Net Asset Value etc. if they function
        void filterFundsTest() throws InterruptedException {
            j += 4;
            k++;
            Actions action = new Actions(driver);
            logIn();
            WebElement funds = driver.findElement(By.xpath("//a[text() = 'Funds']"));
            funds.click();
            List<WebElement> shareClassFundName = driver.findElements(By.xpath("//tr[@class = 'acl-page-funds__tree-list-isin-row']//td[@data-grid-col-index = '0']"));
            String searchFundName = shareClassFundName.get(j).getText();
            WebElement fundNameField = driver.findElement(By.xpath("//td[@aria-label='Filter']"));
            action.sendKeys(fundNameField, searchFundName).perform();
            WebElement afterSearch = driver.findElement(By.xpath("//tr[@class = 'acl-page-funds__tree-list-isin-row']//td[@data-grid-col-index = '0']"));
            Assertions.assertEquals(searchFundName, afterSearch.getText());
            driver.navigate().refresh();
            List<WebElement> shareClassISIN = driver.findElements(By.xpath("//tr[@class = 'acl-page-funds__tree-list-isin-row']//td[@data-grid-col-index = '1']"));
            String searchISIN = shareClassISIN.get(j).getText();
            WebElement isinField = driver.findElement(By.xpath("//input[@id =':rc:']"));
            action.sendKeys(isinField, searchISIN).perform();
            WebElement afterSearch1 = driver.findElement(By.xpath("//tr[@class = 'acl-page-funds__tree-list-isin-row']//td[@data-grid-col-index = '1']"));
            Assertions.assertEquals(searchISIN, afterSearch1.getText());
            driver.navigate().refresh();
            List<WebElement> shareClassNETValue = driver.findElements(By.xpath("//tr[@class = 'acl-page-funds__tree-list-isin-row']//td[@data-grid-col-index = '3']"));
            String searchNETValue = shareClassNETValue.get(j).getText();
            WebElement NETValueField = driver.findElement(By.xpath("//input[@id =':rg:']"));
            if (!searchNETValue.isEmpty()) {
                action.sendKeys(NETValueField, searchNETValue).perform();
                WebElement afterSearch2 = driver.findElement(By.xpath("//tr[@class = 'acl-page-funds__tree-list-isin-row']//td[@data-grid-col-index = '3']"));
                Assertions.assertEquals(searchNETValue, afterSearch2.getText());
            }
            driver.navigate().refresh();
            List<WebElement> filterFields = driver.findElements(By.xpath("//td[@aria-label='Filter']"));
            action.sendKeys(filterFields.get(k-1), "asd").perform();
            WebElement noRecords = driver.findElement(By.xpath("//td[text()='No records available']"));
            Assertions.assertEquals("No records available", noRecords.getText());
        }
}



