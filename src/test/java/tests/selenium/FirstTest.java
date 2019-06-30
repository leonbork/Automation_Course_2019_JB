package tests.selenium;

import java.io.IOException;
import java.util.HashMap;

import javax.money.convert.ExchangeRate;

import org.apache.commons.mail.EmailException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import annotioans.MaxRetryCount;
import pages.AmazonSites;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;
import pages.SearchTab;
import templates.BaseTest;
import templates.ProductAttributes;
import utils.EmailSender;
import utils.MoneyExchange;

@Listeners(listeners.TestListener.class)
public class FirstTest extends BaseTest {
	HomePage homePage;
	SearchTab searchTab;
	SearchPage searchPage;
	ProductPage productPage;
	HashMap<String, Integer> countPerSite;
	HashMap<String, ProductAttributes> products;
	StringBuilder stringBuilder;

	@BeforeClass
	@Override
	public void setupClass() throws IOException {
		super.setupClass();
		countPerSite = new HashMap<>();
		products = new HashMap<>();
	}

	@BeforeMethod
	@Override
	public void setupMethod() {
		super.setupMethod();
		homePage = page.GetInstance(HomePage.class);
		searchTab = page.GetInstance(SearchTab.class);
	}

	@MaxRetryCount(3)
	@Test
	public void chromeComTest() throws Exception {
		homePage.goToAmazonSite(AmazonSites.AMAZON_COM);
		searchPage = searchTab.searchProduct("Amazon Echo");
		countPerSite.put(AmazonSites.AMAZON_COM.toString(), searchPage.getSearchResultCount());
		productPage = searchPage.enterProductPage("Black");
		products.put(AmazonSites.AMAZON_COM.toString(), productPage.getProductInfo());

	}

	@MaxRetryCount(3)
	@Test
	public void chromeUkTest() throws Exception {
		homePage.goToAmazonSite(AmazonSites.AMAZON_CO_UK);
		searchPage = searchTab.searchProduct("Amazon Echo");
		countPerSite.put(AmazonSites.AMAZON_CO_UK.toString(), searchPage.getSearchResultCount());
		productPage = searchPage.enterProductPage("Black");
		products.put(AmazonSites.AMAZON_CO_UK.toString(), productPage.getProductInfo());

	}

	@MaxRetryCount(3)
	@Test
	public void chromeDETest() throws Exception {
		homePage.goToAmazonSite(AmazonSites.AMAZON_DE);
		searchPage = searchTab.searchProduct("Amazon Echo");
		countPerSite.put(AmazonSites.AMAZON_DE.toString(), searchPage.getSearchResultCount());
		productPage = searchPage.enterProductPage("Black");
		products.put(AmazonSites.AMAZON_DE.toString(), productPage.getProductInfo());

	}

	@AfterClass
	public void teardownClass() throws EmailException {
		stringBuilder = new StringBuilder();
		showProduct(AmazonSites.AMAZON_COM.toString(), stringBuilder);
		showProduct(AmazonSites.AMAZON_CO_UK.toString(), stringBuilder);
		showProduct(AmazonSites.AMAZON_DE.toString(), stringBuilder);
		compareBetweenProducts(stringBuilder);
		EmailSender.sendEmail(stringBuilder.toString());
		System.out.println();
	}

	private void showProduct(String site, StringBuilder stringBuilder) {
		stringBuilder.append("Product from site - " + site);
		stringBuilder.append(" Number of search results " + countPerSite.get(site));
		ExchangeRate rateForILS = MoneyExchange.getExchangeRate(products.get(site).getCurrency(), "ILS");
		stringBuilder.append(" Price in ILS - " + products.get(site).getPrice() * rateForILS.getFactor().doubleValue());
		if (!products.get(site).getCurrency().equals("USD")) {
			ExchangeRate rateForUSD = MoneyExchange.getExchangeRate(products.get(site).getCurrency(), "USD");
			stringBuilder
					.append(" Price in USD - " + products.get(site).getPrice() * rateForUSD.getFactor().doubleValue());
		} else {
			stringBuilder.append(" Price in USD - " + products.get(site).getPrice());
		}
		stringBuilder.append(" Stock Date - " + products.get(site).getDate());
	}

	private void compareBetweenProducts(StringBuilder stringBuilder) {
		ExchangeRate rateForILSUK = MoneyExchange
				.getExchangeRate(products.get(AmazonSites.AMAZON_CO_UK.toString()).getCurrency(), "ILS");
		ExchangeRate rateForILSCOM = MoneyExchange
				.getExchangeRate(products.get(AmazonSites.AMAZON_COM.toString()).getCurrency(), "ILS");
		double priceForUKILS = products.get(AmazonSites.AMAZON_CO_UK.toString()).getPrice()
				* rateForILSUK.getFactor().doubleValue();
		double priceForCOMILS = products.get(AmazonSites.AMAZON_COM.toString()).getPrice()
				* rateForILSCOM.getFactor().doubleValue();

		if (priceForCOMILS <= priceForUKILS) {
			stringBuilder.append(" Price in ").append(AmazonSites.AMAZON_COM.toString())
					.append(" is cheaper and it's stockdate is ")
					.append(products.get(AmazonSites.AMAZON_COM.toString()).getDate());

		} else {
			stringBuilder.append(" Price in ").append(AmazonSites.AMAZON_CO_UK.toString())
			.append(" is cheaper and it's stockdate is ")
			.append(products.get(AmazonSites.AMAZON_CO_UK.toString()).getDate());
		}

	}

}
