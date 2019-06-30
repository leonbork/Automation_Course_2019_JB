package pages;

public enum AmazonSites {
    AMAZON_COM("https://www.amazon.com"), AMAZON_CO_UK("https://www.amazon.co.uk/"), AMAZON_DE("https://www.amazon.de/"),;

    private final String siteUrl;

    AmazonSites(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    @Override
    public String toString() {
        return siteUrl;
    }

}
