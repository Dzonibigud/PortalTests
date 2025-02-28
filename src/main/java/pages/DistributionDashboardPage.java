package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DistributionDashboardPage extends BasePage {

    @FindBy(xpath = "//button[@type='button']")
    private List<WebElement> buttons;
    @FindBy(className = "css-ej4lv7-control")
    private List<WebElement> placeHolders;
    @FindBy(xpath = "//div[@class='css-10idubs']")
    private List<WebElement> selected;


    public DistributionDashboardPage(WebDriver driver) {
        super(driver, 5);
        PageFactory.initElements(driver, this);
    }

    @Step("Click on Show Filters button")
    public void showFiltersButton() {
        waitAndClick(buttons.get(0));
    }

    public void hideNetworkButton() {
        waitAndClick(buttons.get(1));
    }

    public void clickOnShowQuartersButton() {
        moveToElement(buttons.get(4));
        buttons.get(4).click();
    }

    public void clickOnExportButton() {
        waitAndClick(buttons.get(2));
    }

    public String getFilterButtonText() {
        return buttons.get(0).getText();
    }

    public String getNetworkButtonText() {
        return buttons.get(1).getText();
    }

    public String getShowQuartersButtonText() {
        return buttons.get(4).getText();
    }

    @Override
    public boolean isFileDownloaded(String downloadPath, String fileName) {
        return super.isFileDownloaded(downloadPath, fileName);
    }

    public void filterISIN() {
//        for(int i =0;i<placeHolders.size();i++){
//            moveToElement(placeHolders.get(i));
//            placeHolders.get(i).click();
//            action.sendKeys(Keys.ENTER).perform();
//        }
        moveToElement(placeHolders.get(3));
        placeHolders.get(3).click();
        action.sendKeys(Keys.ENTER, Keys.DOWN, Keys.ENTER, Keys.ESCAPE).perform();
    }

    public String getISINText() {
        return selected.get(0).getText();
    }

    public void clickOnResetFilter() {
        moveToElement(buttons.get(3));
        buttons.get(3).click();
    }

    public int getSlectedSize() {
        return selected.size();
    }
}
