package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PageGenerator {
    public WebDriver driver;
    JavascriptExecutor js;

    public PageGenerator(WebDriver driver) {
        this.driver = driver;
        this.js = ((JavascriptExecutor) driver);
    }

    public <TPage extends BasePage> TPage GetInstance(Class<TPage> pageClass) {
        try {
            // Initialize the Page with its elements and return it.
            return PageFactory.initElements(driver, pageClass);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
