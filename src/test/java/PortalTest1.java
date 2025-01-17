import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.util.List;
import java.time.Duration;



public class PortalTest1 {
        WebDriver driver;
        private static final String BASE_URL = "https://stage.portal.acolin.com/sign-in";
        private static int j = 0;
        private static int k = 0;
        public void logIn() {
            Actions action = new Actions(driver);
            WebElement emailField = driver.findElement(By.xpath("//input[@name='email']"));
            action.sendKeys(emailField,"nikola.jankovic@acolin.com").perform();
            WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
            action.sendKeys(passwordField,"").perform();
            WebElement signInButton = driver.findElement(By.xpath("//button[text()='Sign In']"));
            signInButton.click();
        }
        public boolean isFileDownloaded(String downloadPath, String fileName){
            File dir = new File(downloadPath);
            File[] dirContents = dir.listFiles();
            if (dirContents != null) {
                for (File dirContent : dirContents) {
                    if (dirContent.getName().contains(fileName)) {
                        dirContent.delete();
                        return true;
                    }
                }
            }
            return false;
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

        @Test // 1. Testing of just opening the page
        void openHomePageTest(){
            String currentUrl = driver.getCurrentUrl();
            Assertions.assertEquals(BASE_URL, currentUrl);
        }

        @Test // 2. Testing if it redirects us to the home page after login in
        void logInTest(){
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            logIn();
            WebElement clientPortal = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1[text()='Client Portal']"))));
            Assertions.assertEquals("Client Portal", clientPortal.getText());
        }

        @Test // 3. Testing if redirects us to sign in page after logging out
        void logOutTest(){
            logIn();
            WebElement logOut = driver.findElement(By.xpath("//i[@data-icon='logout-1']"));
            logOut.click();
            Assertions.assertEquals("https://stage.portal.acolin.com/sign-in",driver.getCurrentUrl());
        }

        @Test // 4. Testing of all tabs that are located on the left side of the page
        void openTabs(){
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

        @Test // 5. Testing of Funds page if there are all named filetrs that should be there(Fund name, ISIN, LifeCycle)
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

        @Test // 6. Testing of Collapse All button and its functionality
        void fundsCollapseAllTest() {
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

        @Test // 7. Testing of Back To Top button adn its functionality
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

        @RepeatedTest(4) // 8. Testing of filters Fund Name, Isin, Net Asset Value etc. if they function
        void filterFundsTest(){
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

        //@Test //9. Testing of funds/anyshareClass> Overview page if it is working
        void shareClassTestOverview() {
            boolean check = false;
            Actions action = new Actions(driver);
            logIn();
            WebElement funds = driver.findElement(By.xpath("//a[text() = 'Funds']"));
            funds.click();
            List<WebElement> shareClasses = driver.findElements(By.xpath("//tr[@class='acl-page-funds__tree-list-isin-row']"));
            for(WebElement s: shareClasses) {
                action.moveToElement(s).click().perform();// Issue with page RELOAD and WebElements are cleared so the one i stored is not functioning anymore
                List<WebElement> h2 = driver.findElements(By.xpath("//div[@class='cardHeader']"));// Find better solution for this issu
                for (WebElement h : h2) {

                    if (driver.getPageSource().contains(h.getText())) {
                        check = true;
                    }
                    Assertions.assertTrue(check);
                }
                WebElement back = driver.findElement(By.xpath("//span[text()='Back to Fund Universe']"));
                back.click();
            }
        }
        //10. Testing of funds/anyshareClass> Documents page if it is working
        //11. Testing of funds/anyshareClass> NAV History page if it is working
        //12. Testing of funds/anyshareClass> Distribution page if it is working

        @Test //This test is just a test if the file is downloaded and its hardcoded so needs to be updated
        void fileDownloadTest() throws InterruptedException {
            Actions action = new Actions(driver);
            logIn();
            WebElement funds = driver.findElement(By.xpath("//a[text() = 'Funds']"));
            funds.click();
            List<WebElement> shareClasses = driver.findElements(By.xpath("//tr[@class='acl-page-funds__tree-list-isin-row']"));
            action.moveToElement(shareClasses.get(0)).click().perform();
            WebElement nav = driver.findElement(By.xpath("//a[text()='NAV History']"));
            nav.click();
            WebElement export = driver.findElement(By.xpath("//button[text()='Export .xlsx']"));
            export.click();
            Thread.sleep(1500);
            Assertions.assertTrue(isFileDownloaded("C:\\Users\\NikolaJankovic\\Downloads\\", "LU1559516486_Acolin_NAV_history.xlsx"));
        }


        @Test  //13. Testing of distribution Dashboard tab, testing of all buttons functionality and export(file download)
        void distributionDashboardTest() throws InterruptedException {
            Actions action = new Actions(driver);
            logIn();
            List<WebElement> distribution = driver.findElements(By.className("acl-menu-list__item"));
            action.moveToElement(distribution.get(1)).click().perform();
            List<WebElement> showFilters = driver.findElements(By.xpath("//button[@class='acoButton primary']"));
            showFilters.get(0).click();

            Assertions.assertEquals("Hide Filters", showFilters.get(0).getText());
            showFilters.get(0).click();
            Assertions.assertEquals("Show Filters", showFilters.get(0).getText());
            showFilters.get(0).click();

            WebElement clear = driver.findElement(By.className("k-clear-value"));
            action.moveToElement(clear).click().perform();
            Thread.sleep(10000);
            List<WebElement> buttons = driver.findElements(By.xpath("//button[@class='acoButton primary']"));
            action.moveToElement(buttons.get(4)).click().perform();

            Assertions.assertEquals("Show Months", buttons.get(4).getText());

            action.moveToElement(buttons.get(3)).click().perform();
            Thread.sleep(1000);
            WebElement placeholder = driver.findElement(By.id(":rp:"));

            Assertions.assertTrue(placeholder.getAttribute("size").equals("1"));

            action.moveToElement(buttons.get(2)).click().perform();
            Thread.sleep(1200);
            Assertions.assertTrue(isFileDownloaded("C:\\Users\\NikolaJankovic\\Downloads\\", "Distributed Assets Dashboard"));

            action.moveToElement(buttons.get(1)).click().perform();
            Assertions.assertEquals("Show Network", buttons.get(1).getText());
            buttons.get(1).click();
            Assertions.assertEquals("Hide Network", buttons.get(1).getText());
        }
}
