package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class ShareClasses extends BasePage {

    @FindBy(xpath = "//h3")
    private List<WebElement> titles;
    @FindBy(xpath = "//a[@class='acl-link']")
    private List<WebElement> tabs;
    @FindBy(xpath = "//summary")
    private List<WebElement> countries;
    @FindBy(xpath = "//div[@class='css-ej4lv7-control']")
    private List<WebElement> filters;
    @FindBy(xpath = "//h5[@class='acl-share-class-documents__type-title']")
    private List<WebElement> types;
    @FindBy(xpath = "//button[@class='acoButton primary outlined']")
    private WebElement exportButton;
    @FindBy(xpath = "//input[@placeholder]")
    private WebElement distributionFilter;


    public ShareClasses(WebDriver driver) {
        super(driver, 5);
        PageFactory.initElements(driver, this);
    }

    @Step("Checking if overview page has all titles like Details/Key Facts/ Representatives/ Fees")
    public boolean openOverview() {
        boolean check = false;
        for (WebElement t : titles) {
            if (driver.getPageSource().contains(t.getText())) {
                System.out.println(t.getText());
                check = true;
            }
        }
        return check;
    }

    @Step("Clicking on documents tab")
    public void openDocumentsTab() {
        waitAndClick(tabs.get(1));
    }
    @Step("Clicking on NAV History tab")
    public void openNAVTab(){
        waitAndClick(tabs.get(2));
    }
    @Step("Clicking on Distribution Tab")
    public void openDistributionTab(){
        waitAndClick(tabs.get(3));
    }
    @Step("Clicking on Export.xlsx")
    public void clickOnExportButtonOnNavHistory(){
        waitAndClick(exportButton);
    }

    @Step("Clicking on Country Filter and selecting a country")
    public void countryFilterFunctionality() {
        action.moveToElement(filters.get(0)).click().sendKeys(Keys.ENTER).sendKeys(Keys.ESCAPE).perform();
        System.out.println(countries.size());
    }

    @Step("Clicking on Type Filter and selecting first type")
    public void typeFilterFunctionality() {
        action.moveToElement(filters.get(2)).click().sendKeys(Keys.ENTER).sendKeys(Keys.ESCAPE).perform();
    }
    @Step("Clicking on filter and searching for countries that start with a letter A")
    public void filterCointriesDistributionTab(){
        waitAndClick(distributionFilter);
        action.sendKeys(distributionFilter, "a").perform();
    }


    public boolean hasOneCountry() {
        return  countries.size() == 1;
    }
    public int getCountiresSize(){
        return countries.size();
    }

    public List<String> filterTypePresent() {
        List<String> result = new ArrayList<>();
        for (WebElement t : types) {
            result.add(t.getText());
        }
        return result;
    }

    @Override
    public boolean isFileDownloaded(String downloadPath, String fileName) {
        return super.isFileDownloaded(downloadPath, fileName);
    }
}
