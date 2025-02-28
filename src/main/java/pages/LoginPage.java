package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class LoginPage extends BasePage {

    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailField;
    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordField;
    @FindBy(xpath = "//button[text()='Sign In']")
    private WebElement signInButton;
    @FindBy(xpath = "//div[@class='error-message']")
    private WebElement errorMassage;

    public LoginPage(WebDriver driver) {
        super(driver,5);
        PageFactory.initElements(driver, this);
    }

    @Step("Entering username, password and clicking login button")
    public PortalPage login(String email, String password) {
        type(emailField, email);
        type(passwordField, password);
        waitAndClick(signInButton);
        wait.until(ExpectedConditions.textToBe(By.xpath("//h1"), "Client Portal"));
        return new PortalPage(driver);
    }

    public String getErrorMessage() {
        return getText(errorMassage);
    }
}
