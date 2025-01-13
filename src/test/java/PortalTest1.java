import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class PortalTest1 {
        WebDriver driver;
        private static final String BASE_URL = "https://stage.portal.acolin.com/sign-in";
        @BeforeEach
        void setUp () {
            driver = new ChromeDriver();
        }

        @AfterEach
        void tearDown() throws InterruptedException{
            Thread.sleep(2000);
            driver.quit();
        }
        @Test
        public void openHomePageTest(){
            driver.get(BASE_URL);
            driver.manage().window().maximize();
            String curentUrl = driver.getCurrentUrl();
            Assertions.assertEquals(BASE_URL, curentUrl);
        }
}

