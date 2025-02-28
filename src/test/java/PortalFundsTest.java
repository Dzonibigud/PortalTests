import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;
import pages.FundsPage;
import pages.LoginPage;
import pages.PortalPage;
import pages.ShareClasses;
import utilities.ConfigReader;


@Epic("Funds")
@Feature("Funds Page Functionality")
public class PortalFundsTest extends BaseTest {
    @Test @Story("Opening Funds Tab")
    @Description("Test verifies that a user can open funds tab")
    @Severity(SeverityLevel.NORMAL)@Tag("Regression")
    void fundsTabTest()  {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        portalPage.openFundsTab();
        String actualURL = driver.getCurrentUrl();
        String expectedURL = "https://stage.portal.acolin.com/funds";
        Assertions.assertEquals(expectedURL, actualURL);
        Allure.step("✅ User successfully redirected to funds page after clicking on funds tab.");
    }

    @Test @Story("Collapse button functionality")
    @Description("Test verifies that collapse button functions") @Severity(SeverityLevel.NORMAL)
    @Tag("Regression")
    void collapseAllButtonTest()  {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        FundsPage fundsPage = portalPage.openFundsTab();
        fundsPage.collapseAllButton();
        Assertions.assertTrue(fundsPage.isCollapseButtonClicked());
        Allure.step("✅ User successfully collapsed shareClasses by clicking on Collapse Button.");
    }
    @Test @Story("Back To Top button functionality")
    @Description("Test verifies that back to top button functions") @Severity(SeverityLevel.NORMAL)
    @Tag("Regression")
    void backToTopButtonTest() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        FundsPage fundsPage = portalPage.openFundsTab();
        fundsPage.backToTopButton();
        Assertions.assertTrue(fundsPage.isBackToTopButtonDisplayed());
        Allure.step("✅ User successfully gets back to top of the page after clicking on Back to Top Button.");
    }

    @Test @Story("Fund Name Filter Functionality")
    @Description("Test verifies that Fund Name filter search functions")@Severity(SeverityLevel.NORMAL)
    @Tag("Regression")
    void fundNameFilterTest(){
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        FundsPage fundsPage = portalPage.openFundsTab();
        fundsPage.checkFundNameFilter();
        Assertions.assertTrue(fundsPage.numberOfShareClasses());
        Allure.step("✅ User gets desired Fund Name after searching in Fund name field");
    }
    @Test @Story("ISIN field Filter Functionality")
    @Description("Test verifies that ISIN filter search functions")
    @Severity(SeverityLevel.NORMAL)@Tag("Regression")
    void iSINFilterTest(){
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        FundsPage fundsPage = portalPage.openFundsTab();
        fundsPage.checkISINFilter();
        Assertions.assertTrue(fundsPage.numberOfShareClasses());
        Allure.step("✅ User gets desired ISIN number after searching in ISIN field");
    }
    @Test @Story("Bad input text display")
    @Description("Test verifies that if user enters invalid or shareClass that dont exist will get a proper text")
    @Severity(SeverityLevel.NORMAL)@Tag("Regression")
    void badInputTest(){
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        FundsPage fundsPage = portalPage.openFundsTab();
        fundsPage.checkBadInput();
        Assertions.assertTrue(fundsPage.isThereAnyRecords());
        Allure.step("✅ User gets desired text prompt after searching with wrong/bad parameters");
    }
    @Test @Story("Opening Share Class")
    @Description("Test verifies that user can open share class") @Severity(SeverityLevel.NORMAL)
    @Tag("Regression")
    void openShareClassTest(){
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        FundsPage fundsPage = portalPage.openFundsTab();
        fundsPage.openShareClass();
        Assertions.assertTrue(driver.getCurrentUrl().contains("share-classes"));
        Allure.step("✅ User gets redirected to the Share Class");
    }
}
