import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.Constants;
import pages.DistributionDashboardPage;
import pages.LoginPage;
import pages.PortalPage;
import utilities.ConfigReader;

@Epic("Distribution Dashboard Tab")
@Feature("Distribution Dashboard Tab Functionality")
public class DistributionDashboardPageTest extends BaseTest {
    @Test
    @Story("Distribution Dashboard Tab landing page")
    @Description("Test verifies that Distribution Dashboard Tab opens")
    @Severity(SeverityLevel.NORMAL)
    @Tag("Regression")
    @Tag("Smoke")
    void DistributionDashboardOpenTest() {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        DistributionDashboardPage disPage = portalPage.openDistributionDashboardTab();
        Allure.step("Clicking on distribution dashboard tab");
        String expectedUrl = "https://stage.portal.acolin.com/distribution-dashboard";
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
        Allure.step("✅ Distribution Dashboards page opened and correct url is displayed ");
    }

    @Test
    @Story("Distribution Dashboard Show Filter button functionality")
    @Description("Test verifies that Distribution Dashboard show filter functionality works as expected")
    @Severity(SeverityLevel.NORMAL)
    @Tag("Regression")
    void DistributionDashboardShoFilterTest() {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        DistributionDashboardPage disPage = portalPage.openDistributionDashboardTab();
        disPage.showFiltersButton();
        Allure.step("Clicking on Show Filter button two times");
        Assertions.assertEquals("Hide Filters", disPage.getFilterButtonText());
        Allure.step("✅Distribution dashboard show filter button is clicked first time and it shows 'Hide Filters'");
        disPage.showFiltersButton();
        Assertions.assertEquals("Show Filters", disPage.getFilterButtonText());
        Allure.step("✅Distribution dashboard show filter button is clicked second time and now shows 'Show Filters' again");

    }

    @Test
    @Story("Distribution Dashboard Hide Network button functionality")
    @Description("Test verifies that Distribution Dashboard Hide Network functionality works as expected")
    @Severity(SeverityLevel.NORMAL)
    @Tag("Regression")
    void DistributionDashboardHideNetworkTest() {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        DistributionDashboardPage disPage = portalPage.openDistributionDashboardTab();
        disPage.hideNetworkButton();
        Allure.step("Clicking on Hide Network button two times");
        Assertions.assertEquals("Show Network", disPage.getNetworkButtonText());
        Allure.step("✅Distribution dashboard hide network button is clicked first time and it shows 'Show Network'");
        disPage.hideNetworkButton();
        Assertions.assertEquals("Hide Network", disPage.getNetworkButtonText());
        Allure.step("✅Distribution dashboard show network button is clicked second time and now shows 'Hide Network' again");
    }

    @Test
    @Story("Distribution Dashboard Export button functionality and checking if the file is downloaded")
    @Description("Test verifies that Distribution Dashboard export button functionality works as expected and file is downloaded")
    @Severity(SeverityLevel.NORMAL)
    @Tag("Regression")
    void DistributionDashboardExportTest() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        DistributionDashboardPage disPage = portalPage.openDistributionDashboardTab();
        Allure.step("Clicking on Export button");
        disPage.clickOnExportButton();
        Thread.sleep(500);
        Assertions.assertTrue(disPage.isFileDownloaded(Constants.DOWNLOAD_PATH, "Distributed Assets Dashboard"));
        Allure.step("✅ Button is clicked and file is downloaded");
    }

    @Test
    @Story("Distribution Dashboard Show Quarters/Months button functionality")
    @Description("Test verifies that Distribution Dashboard Show Quarters/Months button functionality works as expected")
    @Severity(SeverityLevel.NORMAL)
    @Tag("Regression")
    void distributionDashboardShowQuartersTest() {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        DistributionDashboardPage disPage = portalPage.openDistributionDashboardTab();
        Allure.step("Clicking on Show Quarters/Months button twice");
        disPage.showFiltersButton();
        disPage.clickOnShowQuartersButton();
        Assertions.assertEquals("Show Months", disPage.getShowQuartersButtonText());
        Allure.step("✅ Show Quarters button is clicked");
        disPage.clickOnShowQuartersButton();
        Assertions.assertEquals("Show Quarters", disPage.getShowQuartersButtonText());
        Allure.step("✅ Show Months button is clicked");
    }

    @Test
    @Story("Distribution Dashboard ISIN Filter selecting 2 isins")
    @Description("Test verifies that Distribution Dashboard ISIN filter shows correct output")
    @Severity(SeverityLevel.NORMAL)
    @Tag("Regression")
    void distributionDashboardISINFilterTest() {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        DistributionDashboardPage disPage = portalPage.openDistributionDashboardTab();
        Allure.step("Clicking on ISIN Filter and selecting first 2 isins");
        disPage.showFiltersButton();
        disPage.filterISIN();
        Assertions.assertEquals("2 selected", disPage.getISINText());
        Allure.step("✅ Isin filter displays correct output of 2 selected isins");
    }

    @Test
    @Story("Distribution Dashboard Reset Filter functionality")
    @Description("Test verifies that Distribution Dashboard Reset Filter resets data of all filters to default values")
    @Severity(SeverityLevel.NORMAL)
    @Tag("Regression")
    void distributionDashboardResetFilterFunctionality() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        DistributionDashboardPage disPage = portalPage.openDistributionDashboardTab();
        Allure.step("Select 2 isins then click on reset filter");
        disPage.showFiltersButton();
        disPage.filterISIN();
        disPage.clickOnResetFilter();
        Assertions.assertEquals(1, disPage.getSlectedSize());
        Allure.step("✅ After clicking on reset filter button we get default values");
    }
}
