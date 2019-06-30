package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import templates.ProductAttributes;
import utils.LogTest;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(how = How.XPATH, using = "//div[@id='availability']/span")
    public WebElement stockDate;

    @FindBy(how = How.ID, using = "priceblock_ourprice")
    public WebElement price;

    private String getStockDate() {
        String stockDateString = readText(stockDate);
        LogTest.log("Product Stock date - " + stockDateString);
        return stockDateString;
    }

    private String getPrice() {
        String priceToString = readText(price);
        LogTest.log("Product Stock price - " + priceToString);
        return priceToString;
    }

    public ProductAttributes getProductInfo() {
        String priceString = getPrice().trim();
        ProductAttributes productAttributes = new ProductAttributes();

        if (priceString.startsWith("$")) {
            productAttributes.setCurrency("USD");
        } else if (priceString.startsWith("₪")){
            productAttributes.setCurrency("NIS");
        } else if (priceString.startsWith("�")) {
            productAttributes.setCurrency("GBP");
        } else {
            productAttributes.setCurrency("EUR");
        }

        productAttributes.setDate(getStockDate());
        productAttributes.setPrice(Double.parseDouble(priceString.substring(1)));
        return productAttributes;
    }
}
