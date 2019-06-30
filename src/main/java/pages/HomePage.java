package pages;

import org.openqa.selenium.WebDriver;
import utils.LogTest;

/**
   *  Main page to enter for amazon sites.
   */

    public class HomePage extends BasePage {

        public HomePage(WebDriver driver) {
            super(driver);
        }

        public void goToAmazonSite(AmazonSites site) {
            LogTest.log("Going to site - " + site.toString());
            driver.get(site.toString());
        }
}
