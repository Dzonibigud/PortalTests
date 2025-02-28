package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DistributionNetworkPage extends BasePage {

    @FindBy(xpath = "//input[@type='text']")
    private List<WebElement> placeHolders;
    @FindBy(xpath = "//span[@class='k-icon k-svg-icon k-svg-i-plus']")
    private List<WebElement> plusExpandable;
    @FindBy(xpath = "//i[@data-testid]//..")
    private List<WebElement> countries;
    @FindBy(xpath = "//td[@class='city-filter']")
    private List<WebElement> cityFilter;
    @FindBy(xpath = "//span[@class='acl-page-distributors__name']")
    private List<WebElement> distributionPartners;
    @FindBy(xpath = "//div[@class='chip warning acl-page-distributors__chip']")
    private List<WebElement> status;
    @FindBy(xpath = "//td[@class='k-table-td']")
    private List<WebElement> categories;

    public DistributionNetworkPage(WebDriver driver) {
        super(driver, 5);
        PageFactory.initElements(driver, this);
    }

    public void selectContinent(){
        placeHolders.get(0).click();
        action.sendKeys(Keys.ENTER,Keys.ESCAPE).perform();
    }
    public void selectCountries(){
        placeHolders.get(1).click();
        action.sendKeys(Keys.ENTER,Keys.ESCAPE).perform();
    }
    public void searchForCity(){
        placeHolders.get(2).click();
        action.sendKeys("Geneva").perform();
    }
    public void searchForDistributor(){
        placeHolders.get(3).click();
        action.sendKeys("Erste").perform();
    }

    public void selectRelationshipStatus(){
        placeHolders.get(4).click();
        action.sendKeys(Keys.DOWN, Keys.ENTER,Keys.ESCAPE).perform();
    }
    public void selectCategory(){
        placeHolders.get(5).click();
        action.sendKeys(Keys.DOWN,Keys.DOWN, Keys.ENTER,Keys.ESCAPE).perform();
    }

    public int getNumOfCountries(){
        return plusExpandable.size();
    }
    public String getCountriesText(){
        return countries.get(0).getText();
    }
    public int getNumOfCities(){
        return cityFilter.size();
    }
    public String getDistributionPartnersText(){
        return distributionPartners.get(0).getText();
    }
    public String getStatusText(){
        return status.get(0).getText();
    }
    public String getCategoryText(){
        return categories.get(0).getText();
    }
}
