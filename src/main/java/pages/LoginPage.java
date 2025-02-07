package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class LoginPage extends HomePage {
    protected WebDriver driver;

    public LoginPage(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//input[@name='email']") private WebElement emailField;
    @FindBy(xpath = "//input[@name='password']") private WebElement passwordField;
    @FindBy(xpath = "//button[text()='Sign In']") private WebElement signInButton;
    @FindBy(xpath = "//div[@class='error-message']") private WebElement errorMassage;


    public void login(String email, String password){
        type(emailField,email);
        type(passwordField,password);
        click(signInButton);
    }
    public String getErrorMessage() {
        return getText(errorMassage);
    }
}
