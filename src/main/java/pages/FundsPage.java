package pages;


import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class FundsPage extends BasePage {

    //Pronalazenje web lokatora
    @FindBy(xpath = "//button[text()= 'Collapse all']")
    private WebElement collapseAllButton;
    @FindBy(xpath = "//span[@class='k-icon k-svg-icon k-svg-i-caret-alt-right k-treelist-toggle']")
    private List<WebElement> dropDownSelektor;
    @FindBy(xpath = "//tr[@class = 'acl-page-funds__tree-list-isin-row']//td[@data-grid-col-index = '0']")
    private List<WebElement> shareClassFundNames;
    @FindBy(xpath = "//tr[@class = 'acl-page-funds__tree-list-isin-row']//td[@data-grid-col-index = '1']")
    private List<WebElement> shareClassISINs;
    @FindBy(xpath = "//td[@aria-label='Filter']")
    private List<WebElement> filters;
    @FindBy(xpath = "//td[text()='No records available']")
    private WebElement noRecordsFound;
    @FindBy(xpath = "//div[@class = 'acl-back-to-top acl-back-to-top--show']")
    private List<WebElement> backToTopButton;

    //konstruktor
    public FundsPage(WebDriver driver) {
        super(driver, 5);
        PageFactory.initElements(driver, this);
    }

    @Step("Clicking on Collapse Button on funds tab")
    public void collapseAllButton() {
        collapseAllButton.click();
    }

    @Step("Clicking on Back to Top button on funds tab")
    public void backToTopButton() throws InterruptedException {
        action.scrollToElement(shareClassFundNames.get(shareClassFundNames.size() - 1)).perform();
        waitAndClick(backToTopButton.get(0));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h1")));
        Thread.sleep(1000);
    }

    @Step("Sending input of first shareClass Name on Fund Name Filter and checking if that shareCLass is displayed")
    public void checkFundNameFilter() {
        String shareClassName = shareClassFundNames.get(0).getText();
        filters.get(0).click();
        action.sendKeys(filters.get(0), shareClassName).perform();
    }

    @Step("Sending input of first share class ISIN value on ISIN Field Filter and checking if that shareClass is displayed")
    public void checkISINFilter() {
        String shareClassIsin = shareClassISINs.get(0).getText();
        filters.get(1).click();
        action.sendKeys(filters.get(1), shareClassIsin).perform();
    }

    @Step("Sending wrong/bad input into Fund Name Filter and checking if No Records available is displayed")
    public void checkBadInput() {
        filters.get(0).click();
        action.sendKeys(filters.get(0), "asdfghjjkkqwertyu1234567").perform();
    }

    @Step("Clicking on the first share Class")
    public ShareClasses openShareClass() {
        moveToElement(shareClassFundNames.get(0));
        shareClassFundNames.get(0).click();
        wait.until(ExpectedConditions.textToBe(By.xpath("//h3"), "Details"));
        return new ShareClasses(driver);
    }

    public boolean isCollapseButtonClicked() {
        boolean check = false;
        for (WebElement dd : dropDownSelektor) {
            if (dd.findElement(By.xpath("./..")).getAttribute("aria-expanded").equals("false")) {
                check = true;
            }
        }
        return check;
    }

    public boolean numberOfShareClasses() {
        return shareClassFundNames.size() == 1;
    }

    public boolean isThereAnyRecords() {
        return noRecordsFound.getText().equals("No records available");
    }

    public boolean isBackToTopButtonDisplayed() {
        return backToTopButton.isEmpty();
    }
}
