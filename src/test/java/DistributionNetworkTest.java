import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.DistributionNetworkPage;
import pages.LoginPage;
import pages.PortalPage;
import utilities.ConfigReader;

@Epic("Distribution Network Tab")
@Feature("Distribution Network Tab Functionality")
public class DistributionNetworkTest extends BaseTest {

    @Test @Story("Distribution Network Tab landing page TEST")
    @Description("Test verifies that Distribution Network Tab opens") @Severity(SeverityLevel.NORMAL)
    @Tag("Regression") @Tag("Smoke")
    void DistributionNetworkOpenTest() {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        DistributionNetworkPage netPage = portalPage.openDistributionNetworkTab();
        Allure.step("Clicking on distribution network tab");
        String expectedUrl = "https://stage.portal.acolin.com/distribution-network";
        Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
        Allure.step("✅ Distribution Network page opened and correct url is displayed ");
    }

    @Test @Story("Distribution Network Tab continent filter TEST")
    @Description("Test verifies that Distribution Network Continent filter works as expected")
    @Severity(SeverityLevel.NORMAL)
    @Tag("Regression")
    void DistNetworkContinentTest(){
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        DistributionNetworkPage netPage = portalPage.openDistributionNetworkTab();
        Allure.step("Clicking on distribution network Continent tab and selecting first value");
        int numBefore = netPage.getNumOfCountries();
        netPage.selectContinent();
        int numAfter = netPage.getNumOfCountries();
        Assertions.assertTrue(numAfter != numBefore);
        Allure.step("✅Continent selected and associated countries displayed");
    }
    @Test @Story("Distribution Network Tab Countries filter TEST")
    @Description("Test verifies that Distribution Network Countries filter works as expected")
    @Severity(SeverityLevel.NORMAL) @Tag("Regression")
    void DistNetworkCountriesTest(){
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        DistributionNetworkPage netPage = portalPage.openDistributionNetworkTab();
        Allure.step("Clicking on distribution network Countries tab and selecting first value");
        netPage.selectCountries();
        Assertions.assertEquals("Austria", netPage.getCountriesText());
        Allure.step("✅Continent selected and associated countries displayed");
    }
    @Test @Story("Distribution Network Tab City filter TEST")
    @Description("Test verifies that Distribution Network City filter works as expected")
    @Severity(SeverityLevel.NORMAL)@Tag("Regression")
    void DistNetworkCityFilterTest(){
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        DistributionNetworkPage netPage = portalPage.openDistributionNetworkTab();
        Allure.step("Clicking on distribution network City filter, and writing 'Geneva'");
        netPage.searchForCity();
        Assertions.assertTrue(netPage.getNumOfCities()>0);
        Allure.step("✅We get all geneva cities under City filter");
    }
    @Test @Story("Distribution Network Tab Distributor/Sub-Distributor filter TEST")
    @Description("Test verifies that Distribution Network Distributor/Sub-Distributor filter works as expected")
    @Severity(SeverityLevel.NORMAL)@Tag("Regression")
    void DistNetworkDistributorFilterTest(){
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        DistributionNetworkPage netPage = portalPage.openDistributionNetworkTab();
        Allure.step("Clicking on distribution network Distributor/Sub-Distributor filter,and writing 'Erste'");
        netPage.searchForDistributor();
        Assertions.assertTrue(netPage.getNumOfCities()>0 && netPage.getDistributionPartnersText().equals("Erste Group Bank AG"));
        Allure.step("✅We get Erste distributors under Distributor Partner filter");
    }
    @Test @Story("Distribution Network Tab Relationship Status filter TEST")
    @Description("Test verifies that Distribution Network Relationship Status filter works as expected")
    @Severity(SeverityLevel.NORMAL)@Tag("Regression")
    void DistNetworkRelationshipStatusFilterTest(){
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        DistributionNetworkPage netPage = portalPage.openDistributionNetworkTab();
        Allure.step("Clicking on distribution network Relationship Status filter,and selecting 'Available'");
        netPage.selectRelationshipStatus();
        Assertions.assertEquals(driver.findElement
                (By.xpath("//div[@class='css-9jq23d']")).getText(),netPage.getStatusText());
        Allure.step("✅We get all Available Statuses");
    }
    @Test@Story("Distribution Network Tab Category filter TEST")
    @Description("Test verifies that Distribution Network Category filter works as expected")
    @Severity(SeverityLevel.NORMAL)@Tag("Regression")
    void DistNetworkCategoryFilterTest(){
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        DistributionNetworkPage netPage = portalPage.openDistributionNetworkTab();
        Allure.step("Clicking on distribution network Category filter,and selecting 'Broker Dealer' ");
        netPage.selectCategory();
        Assertions.assertEquals(driver.findElement
                (By.xpath("//div[@class='css-9jq23d']")).getText(), netPage.getCategoryText());
        Allure.step("✅Under Category we only get ones that are 'Broker Dealer' ");
    }
}
