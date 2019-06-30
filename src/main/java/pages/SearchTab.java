package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import utils.LogTest;

public class SearchTab extends BasePage {
    @FindBy(how = How.ID, using = "twotabsearchtextbox")
    public WebElement searchBox;

    @FindBy(how = How.XPATH, using = "//input[@value='Go']")
    public WebElement startSearch;

    public SearchTab(WebDriver driver) {
        super(driver);
    }

    /**
     * Search for Product
     *
     * @param nameOfProduct
     * @return searched for product
     */
    public SearchPage searchProduct(String nameOfProduct) {
        LogTest.log("Searching for - " + nameOfProduct);
        click(searchBox);
        clear(searchBox);
        writeText(searchBox, nameOfProduct);
        click(startSearch);
        return new PageFactory().initElements(driver, SearchPage.class);
    }
}
