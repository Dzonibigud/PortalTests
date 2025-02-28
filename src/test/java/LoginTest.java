import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.PortalPage;
import utilities.ConfigReader;

@Epic("User Authentication")
@Feature("Login Functionality")
public class LoginTest extends BaseTest {
    @Test
    @Story("Invalid Login")
    @Description("Test verifies that a user with invalid credentials will get an error message.")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Executing invalidLogin")
    @Tag("Regression")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("wrongUser@email.com", "wrongPass");
        Assertions.assertEquals("Wrong user email / password combination provided.", loginPage.getErrorMessage());
        Allure.step("✅ User gets invalid message after invalid input.");
    }

    @Test
    @Story("Valid Login")
    @Description("Test verifies that a user with valid credentials can log in successfully.")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Executing testValidLogin")
    @Tag("Regression")
    public void testValidUsername() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        String expectedUrl = "https://stage.portal.acolin.com/home";
        loginPage.waitForUrlToBe(expectedUrl);
        String actualUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expectedUrl, actualUrl);
        Allure.step("✅ User successfully logged in and redirected to home page.");
    }

    @Test
    @Story("Logout functionality")
    @Description("Test verifies that a user can logout and redirects him to sign in page.")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Executing logout button functionality")
    @Tag("Regression")
    public void testLogutButton() {
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = new PortalPage(driver);
        loginPage.login(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        portalPage.logout();
        String expectedUrl = "https://stage.portal.acolin.com/sign-in";
        loginPage.waitForUrlToBe(expectedUrl);
        String actualUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expectedUrl, actualUrl);
        Allure.step("✅ User successfully logged out and redirected to sign-in page.");
    }
}
