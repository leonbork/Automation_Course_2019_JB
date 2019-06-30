package templates;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.PageGenerator;

import java.io.File;
import java.io.IOException;

/**
 * Base Class to be used as extension for other classes of tests
 */
public class BaseTest {
    public WebDriver driver;
    public WebDriverWait wait;
    public PageGenerator page;

    @BeforeClass
    public void setupClass() throws IOException {
        System.setProperty("webdriver.chrome.driver",
                new File("./src/test/resources/drivers/chromedriver.exe").getCanonicalPath());
    }

    @BeforeMethod
    public void  setupMethod() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 15);
        driver.manage().window().maximize();
        page = new PageGenerator(driver);
    }

    @AfterMethod
    public void teardownMethod() {
        driver.quit();
    }
}

