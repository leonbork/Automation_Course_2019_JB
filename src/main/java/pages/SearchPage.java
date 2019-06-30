package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import utils.LogTest;

import javax.management.RuntimeErrorException;
import java.util.List;
import java.util.Optional;

public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(how = How.ID, using = "s-result-count")
    public WebElement searchResultCount;

    @FindBy(how = How.ID, using = "pagnNextString")
    public WebElement nextPage;

    /**
     *
     * @return amount of search results
     */
    public int getSearchResultCount() {
        int resultOfSearchCount;
        String result = readText(searchResultCount);
        if (result.contains("over")) {
            resultOfSearchCount = Integer.parseInt(readText(searchResultCount).split(" ")[3].replace(",", ""));
        } else {
            resultOfSearchCount = Integer.parseInt(readText(searchResultCount).split(" ")[2]);
        }

        LogTest.log("Result count of search item - " + resultOfSearchCount);
        return resultOfSearchCount;
    }

    /**
     *
     * @param productName
     *            searched product
     * @return enter page of product
     * @throws Exception
     */
    public ProductPage enterProductPage(String productName) throws Exception {
        click(searchItemsPagesToFindProduct(productName));
        return new PageFactory().initElements(driver, ProductPage.class);
    }

    /**
     * Search each page till find product
     *
     * @param productName
     *            searched product name
     * @return WebElement of searched product
     */
    private WebElement searchItemsPagesToFindProduct(String productName) {
        List<WebElement> elements = driver.findElements(By.xpath("//li/div/div/div/div[2]/div[2]/div/a/h2"));
        Optional<WebElement> optionalItem = elements.stream().filter(it -> readText(it).contains(productName))
                .findFirst();
        if (optionalItem.isPresent()) {
            LogTest.log("Enter product page of - " + readText(optionalItem.get()));
            return optionalItem.get();
        } else {
            if (nextPage.isEnabled()) {
                click(nextPage);
                searchItemsPagesToFindProduct(productName);
            } else {
                LogTest.log("Didn't find product");
                throw new RuntimeErrorException(null, "Could not find " + productName);
            }
        }
        return null;
    }


}
