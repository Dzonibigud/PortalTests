package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PortalPage extends HomePage{
    protected WebDriver driver;

    public PortalPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//i[@data-icon='logout-1']") private WebElement logoutButton;
    public void logout(){
        logout(logoutButton);
    }

}