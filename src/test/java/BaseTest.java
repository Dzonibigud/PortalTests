import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import utilities.ConfigReader;
import utilities.WebDriverFactory;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = WebDriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.get(ConfigReader.getProperty("portal_url"));
    }

    @AfterEach
    public void tearDown() {
        WebDriverFactory.quitDriver();
    }
}
