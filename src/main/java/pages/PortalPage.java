package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class PortalPage extends BasePage {

    @FindBy(xpath = "//i[@data-icon='logout-1']")
    private WebElement logoutButton;
    @FindBy(xpath = "//dd[@class ='acl-menu-list__item']")
    private List<WebElement> tabs;


    public PortalPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Clicking on logout button")
    public void logout() {
        waitAndClick(logoutButton);
    }

    @Step("Opening Funds tab on home page")
    public FundsPage openFundsTab() {
        tabs.get(0).findElement(By.linkText(tabs.get(0).getText())).click();
        wait.until(ExpectedConditions.textToBe(By.xpath("//h3"), "Fund Universe"));
        return new FundsPage(driver);
    }

}