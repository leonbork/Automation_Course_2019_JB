package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Default selenium actions in more readable and maintainable content
 */
public class BasePage extends PageGenerator {

    public BasePage(WebDriver driver) {
        super(driver);
    }

    WebDriverWait wait = new WebDriverWait(this.driver, 20);

    /**
     * Click
     *
     * @param elementAttr
     *            searched element
     */
    public <T> void click(T elementAttr) {
        if (elementAttr.getClass().getName().contains("By")) {
            driver.findElement((By) elementAttr).click();
        } else {
            ((WebElement) elementAttr).click();
        }
    }

    /**
     * Clear text
     *
     * @param elementAttr
     *            searched element
     */
    public <T> void clear(T elementAttr) {
        if (elementAttr.getClass().getName().contains("By")) {
            driver.findElement((By) elementAttr).clear();
        } else {
            ((WebElement) elementAttr).clear();
        }
    }

    /**
     * Write Text
     *
     * @param elementAttr
     *            searched element
     * @param text
     *            to input on element
     */
    public <T> void writeText(T elementAttr, String text) {
        if (elementAttr.getClass().getName().contains("By")) {
            driver.findElement((By) elementAttr).sendKeys(text);
        } else {
            ((WebElement) elementAttr).sendKeys(text);
        }
    }

    /**
     * Read text if element
     *
     * @param elementAttr
     *            searched element
     * @return String output of element
     */
    public <T> String readText(T elementAttr) {
        if (elementAttr.getClass().getName().contains("By")) {
            return driver.findElement((By) elementAttr).getText();
        } else {
            return ((WebElement) elementAttr).getText();
        }
    }

    /**
     * Scroll to element
     *
     * @param elementAttr
     *            searched element
     * @return String output of element
     */
    public <T> void scrollToElement(T elementAttr) {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        if (elementAttr.getClass().getName().contains("By")) {
            js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement((By) elementAttr));
        } else {
            js.executeScript("arguments[0].scrollIntoView(true);", ((WebElement) elementAttr));
        }
    }
}
