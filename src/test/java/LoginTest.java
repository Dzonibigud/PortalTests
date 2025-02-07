import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PortalPage;


public class LoginTest extends BaseTest {
    @Test
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("wrongUser@email.com", "wrongPass");
        Assertions.assertEquals("Wrong user email / password combination provided.",loginPage.getErrorMessage());
    }
    @Test
    @Story("Valid Login")
    @Description("Test verifies that a user with valid credentials can log in successfully.")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Executing testValidLogin")
    void testValidUsername(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("nikola.jankovic@acolin.com", "");
        String expectedUrl = "https://stage.portal.acolin.com/home";
        loginPage.waitForUrlToBe(expectedUrl);
        String actualUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expectedUrl, actualUrl);
        Allure.step("✅ User successfully logged in and redirected to dashboard.");
    }
    @Test
    @Story("Valid Logout")
    @Description("Test verifies that a user can logout and redirects him to sign in page.")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Executing logout button functionality")
    void testLogutButton(){
        LoginPage loginPage = new LoginPage(driver);
        PortalPage portalPage = new PortalPage(driver);
        loginPage.login("nikola.jankovic@acolin.com", "");
        portalPage.logout();
        String expectedUrl = "https://stage.portal.acolin.com/sign-in";
        loginPage.waitForUrlToBe(expectedUrl);
        String actualUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expectedUrl, actualUrl);
    }
}
