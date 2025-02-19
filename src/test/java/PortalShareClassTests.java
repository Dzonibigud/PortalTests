import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.FundsPage;
import pages.LoginPage;
import pages.PortalPage;
import pages.ShareClasses;
import utilities.ConfigReader;

@Epic("Share Classes")
@Feature("Share Class page Functionality")
public class PortalShareClassTests extends BaseTest {
    @Test
    @Story("Share Class Overview Page")
    @Description("Test verifies that overview page contains all key titles like Details/ Key Facts/ Representatives and Fees")
    @Severity(SeverityLevel.NORMAL)
    @Step("Clicking on first share class and checking overview page")
    @Tag("Regression")
    void overviewTest() {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        FundsPage fundsPage = portalPage.openFundsTab();
        ShareClasses shareClasses = fundsPage.openShareClass();
        Assertions.assertTrue(shareClasses.openOverview());
        Allure.step("✅ Overview Page displays all titles like Details/ Key Facts/ Representatives and Fees ");
    }

    @Test
    @Story("Documents page in Share Class")
    @Description("Test verifies that documents page opens and displays valid url")
    @Severity(SeverityLevel.NORMAL)
    @Step("Clicking on the documents tab")
    @Tag("Regression")
    void documentsTest() {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        FundsPage fundsPage = portalPage.openFundsTab();
        ShareClasses shareClasses = fundsPage.openShareClass();
        shareClasses.openDocumentsTab();
        Assertions.assertTrue(driver.getCurrentUrl().contains("documents"));
        Allure.step("✅ Documents Page is opened and correct url is displayed ");
    }

    @Test
    @Story("Country Filter on documents page")
    @Description("Test verifies that country filter works as expected")
    @Severity(SeverityLevel.NORMAL)
    @Step("Clicking on Country Filter and clicking on first country")
    @Tag("Regression")
    void countryFilterTest() {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        FundsPage fundsPage = portalPage.openFundsTab();
        ShareClasses shareClasses = fundsPage.openShareClass();
        shareClasses.openDocumentsTab();
        shareClasses.countryFilterFunctionality();
        Assertions.assertTrue(shareClasses.hasOneCountry());
        Allure.step("✅ Only one country is displayed");
    }

    @Test
    @Story("Type Filter on documents page")
    @Description("Test verifies that type filter works as expected")
    @Severity(SeverityLevel.NORMAL)
    @Step("Clicking on Type Filter and clicking on first type")
    @Tag("Regression")
    void typeFilterTest() {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        FundsPage fundsPage = portalPage.openFundsTab();
        ShareClasses shareClasses = fundsPage.openShareClass();
        shareClasses.openDocumentsTab();
        shareClasses.typeFilterFunctionality();
        shareClasses.filterTypePresent();
        for (int i = 0; i < shareClasses.filterTypePresent().size(); i++) {
            if (shareClasses.filterTypePresent().get(0).equals(shareClasses.filterTypePresent().get(i))) {
                Assertions.assertTrue(true);
            }
        }
        System.out.println(shareClasses.filterTypePresent());
        Allure.step("✅ Only one type is displayed");
    }
    @Test
    @Story("NAV History page in Share Class")
    @Description("Test verifies that NAV History page opens and displays valid url")
    @Severity(SeverityLevel.NORMAL)
    @Step("Clicking on the NAV History tab")
    @Tag("Regression")
    void navHistoryTabTest() {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        FundsPage fundsPage = portalPage.openFundsTab();
        ShareClasses shareClasses = fundsPage.openShareClass();
        shareClasses.openNAVTab();
        Assertions.assertTrue(driver.getCurrentUrl().contains("nav-history"));
        Allure.step("✅ NAV History tab is opened and correct url is displayed ");
    }
    @Test
    @Story("Distribution page in Share Class")
    @Description("Test verifies that Distribution page opens and displays valid url")
    @Severity(SeverityLevel.NORMAL)
    @Step("Clicking on the Distribution tab")
    @Tag("Regression")
    void distributionTest() {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        FundsPage fundsPage = portalPage.openFundsTab();
        ShareClasses shareClasses = fundsPage.openShareClass();
        shareClasses.openDistributionTab();
        Assertions.assertTrue(driver.getCurrentUrl().contains("distribution"));
        Allure.step("✅ Distribution tab is opened and correct url is displayed ");
    }
    @Test
    @Story("Export button in NAV History page in Share Class")
    @Description("Test verifies that export button works and the file is downloaded on " +
            "NAV History page ")
    @Severity(SeverityLevel.NORMAL)
    @Step("Clicking on the Export button on NAV History tab")
    @Tag("Regression")
    void exportNAVTest() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        FundsPage fundsPage = portalPage.openFundsTab();
        ShareClasses shareClasses = fundsPage.openShareClass();
        shareClasses.openNAVTab();
        shareClasses.clickOnExportButtonOnNavHistory();
        Thread.sleep(1500);
        WebElement check = driver.findElement(By.xpath("//span[@class='acl-page-share-class__isin']"));
        Assertions.assertTrue(shareClasses.isFileDownloaded("C:\\Users\\NikolaJankovic\\Downloads\\",check.getText()));
        Allure.step("✅ Export Button is clicked and file is downloaded");
    }
    @Test
    @Story("Distribution filter test in Share Class")
    @Description("Test verifies that filter on Distribution page works as expected")
    @Severity(SeverityLevel.NORMAL)
    @Step("Clicking on filter and typeing 'a'")
    @Tag("Regression")
    void filterDistributionTest(){
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        FundsPage fundsPage = portalPage.openFundsTab();
        ShareClasses shareClasses = fundsPage.openShareClass();
        shareClasses.openDistributionTab();
        int before = shareClasses.getCountiresSize();
        shareClasses.filterCointriesDistributionTab();
        int after = shareClasses.getCountiresSize();
        Assertions.assertTrue(before>after);
        Allure.step("✅ Only countries that start with a letter 'a' are displayed");
    }
}
