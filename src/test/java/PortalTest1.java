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
import java.time.Duration;


public class PortalTest1 {
        WebDriver driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        private static final String BASE_URL = "https://stage.portal.acolin.com/sign-in";
        private static int n = 0;
        public void logIn() throws InterruptedException {
            Actions action = new Actions(driver);
            WebElement emailField = driver.findElement(By.xpath("//input[@name='email']"));
            Thread.sleep(1000);
            action.sendKeys(emailField,"nikola.jankovic@acolin.com").perform();
            Thread.sleep(2000);
            WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
            action.sendKeys(passwordField,"").perform();
            Thread.sleep(1000);
            WebElement signInButton = driver.findElement(By.xpath("//button[text()='Sign In']"));
            signInButton.click();
            Thread.sleep(4000);
        }

        @BeforeEach
        void setUp () {
            driver = new ChromeDriver();
        }

        @AfterEach
        void tearDown() throws InterruptedException{
            Thread.sleep(2000);
            driver.quit();
        }
        @Test // Testing of just opening the page
        void openHomePageTest(){
            driver.get(BASE_URL);
            driver.manage().window().maximize();
            String currentUrl = driver.getCurrentUrl();
            Assertions.assertEquals(BASE_URL, currentUrl);

        }
        @Test // Testing if it redirects us to the home page after login in
        void logInTest() throws InterruptedException {
            driver.get(BASE_URL);
            driver.manage().window().maximize();
            logIn();
            Assertions.assertEquals("https://stage.portal.acolin.com/home", driver.getCurrentUrl());
        }
        @Test // Testing if redirects us to sign in page after logging out
        void logOutTest() throws InterruptedException {
            driver.get(BASE_URL);
            driver.manage().window().maximize();
            logIn();
            WebElement logOut = driver.findElement(By.xpath("//i[@data-icon='logout-1']"));
            logOut.click();
            Assertions.assertEquals("https://stage.portal.acolin.com/sign-in",driver.getCurrentUrl());
        }


        @RepeatedTest(17) // Testing of all tabs that are located on the left side of the page
        void openTabs() throws InterruptedException {
            n++;
            System.out.println("Running test number: " + n);
            Actions action = new Actions(driver);
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            driver.get(BASE_URL);
            driver.manage().window().maximize();
            WebElement clientPortal = driver.findElement(By.xpath("//h1[text()='Client Portal']"));
            longWait.until(ExpectedConditions.visibilityOf(clientPortal));
            logIn();
            List<WebElement> tabs = driver.findElements(By.xpath("//dd[@class ='acl-menu-list__item']"));
            WebElement test = tabs.get(n-1).findElement(By.xpath(".//a"));
            action.moveToElement(tabs.get(n-1).findElement(By.linkText(tabs.get(n-1).getText()))).click().perform();
            Thread.sleep(2000);
            Assertions.assertTrue(driver.getCurrentUrl().equals(test.getAttribute("href")));
            Thread.sleep(1000);
        }
        @Test //Testing of Funds page if there are all named filetrs that should be there(Fund name, ISIN, LifeCycle)
        void fundsTabTest() throws InterruptedException{
            boolean check=false;
            driver.get(BASE_URL);
            driver.manage().window().maximize();
            logIn();
            WebElement funds = driver.findElement(By.xpath("//a[text() = 'Funds']"));
            funds.click();
            //Thread.sleep(4000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class = 'k-cell-inner']")));
            List<WebElement> filters = driver.findElements(By.xpath("//span[@class = 'k-cell-inner']"));
            for(WebElement f :filters){
                System.out.println(f.getText());
                if(driver.getPageSource().contains(f.getText())){
                    check=true;
                }
            }
            Assertions.assertTrue(check);
        }
        @Test // Testing of Collapse All button and its functionality
        void fundsCollapseAllTest() throws InterruptedException {
            driver.get(BASE_URL);
            driver.manage().window().maximize();
            logIn();
            WebElement funds = driver.findElement(By.xpath("//a[text() = 'Funds']"));
            funds.click();
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@class = 'acl-page-funds__tree-list-isin-row']")));
            Thread.sleep(2000);
            WebElement collapseAllButton = driver.findElement(By.xpath("//button[text()= 'Collapse all']"));
            collapseAllButton.click();
            Thread.sleep(1000);
            List<WebElement> shareClass = driver.findElements(By.xpath("//tr[@class = 'acl-page-funds__tree-list-isin-row']"));
            System.out.println(shareClass.size());
            Assertions.assertTrue(shareClass.isEmpty());
        }
        @Test // Testing of Back To Top button adn its functionality
        void fundsBackToTopTest() throws InterruptedException {
            Actions action = new Actions(driver);
            boolean check = false;
            driver.get(BASE_URL);
            driver.manage().window().maximize();
            logIn();
            WebElement funds = driver.findElement(By.xpath("//a[text() = 'Funds']"));
            funds.click();
            Thread.sleep(2000);
            WebElement footer = driver.findElement(By.xpath("//footer[@class='acl-footer']"));
            action.scrollToElement(footer).perform();
            Thread.sleep(1000);
            WebElement backToTopButton = driver.findElement(By.xpath("//i[@data-icon = 'arrow-up-1']"));
            backToTopButton.click();
            Thread.sleep(3000);
            WebElement logo = driver.findElement(By.xpath("//a[@title='Acolin Client Portal']"));
            logo.click();
            Assertions.assertEquals("https://stage.portal.acolin.com/home", driver.getCurrentUrl());
        }

        @RepeatedTest(4) //Testing of filters Fund Name, Isin, Net Asset Value etc. if they function
        void filterFundsTest() throws InterruptedException {
            n+=4;
            driver.get(BASE_URL);
            driver.manage().window().maximize();
            Actions action = new Actions(driver);
            logIn();
            WebElement funds = driver.findElement(By.xpath("//a[text() = 'Funds']"));
            funds.click();
            Thread.sleep(2000);

            List<WebElement> shareClassFundName = driver.findElements(By.xpath("//tr[@class = 'acl-page-funds__tree-list-isin-row']//td[@data-grid-col-index = '0']"));
            String searchFundName = shareClassFundName.get(n).getText();
            List<WebElement> filterFields = driver.findElements(By.xpath("//td[@aria-label='Filter']"));
            action.sendKeys(filterFields.get(0),searchFundName).perform();
            Thread.sleep(2000);
            WebElement afterSearch = driver.findElement(By.xpath("//tr[@class = 'acl-page-funds__tree-list-isin-row']//td[@data-grid-col-index = '0']"));
            Assertions.assertEquals(searchFundName, afterSearch.getText());

            driver.navigate().refresh();
            Thread.sleep(2000);

            List<WebElement> shareClassISIN = driver.findElements(By.xpath("//tr[@class = 'acl-page-funds__tree-list-isin-row']//td[@data-grid-col-index = '1']"));
            String searchISIN = shareClassISIN.get(n).getText();
            WebElement isinField = driver.findElement(By.xpath("//input[@id =':rc:']"));
            action.sendKeys(isinField,searchISIN).perform();
            Thread.sleep(2000);
            WebElement afterSearch1 = driver.findElement(By.xpath("//tr[@class = 'acl-page-funds__tree-list-isin-row']//td[@data-grid-col-index = '1']"));
            Assertions.assertEquals(searchISIN, afterSearch1.getText());

            driver.navigate().refresh();
            Thread.sleep(2000);

            List<WebElement> shareClassNETValue = driver.findElements(By.xpath("//tr[@class = 'acl-page-funds__tree-list-isin-row']//td[@data-grid-col-index = '3']"));
            String searchNETValue = shareClassNETValue.get(n).getText();
            WebElement NETValueField = driver.findElement(By.xpath("//input[@id =':rg:']"));
            if(!searchNETValue.isEmpty()) {
                action.sendKeys(NETValueField, searchNETValue).perform();
                Thread.sleep(2000);
                WebElement afterSearch2 = driver.findElement(By.xpath("//tr[@class = 'acl-page-funds__tree-list-isin-row']//td[@data-grid-col-index = '3']"));
                Assertions.assertEquals(searchNETValue, afterSearch2.getText());
            }
        }
}



