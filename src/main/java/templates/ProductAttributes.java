package templates;

public class ProductAttributes {

    private String date;
    private double price;
    private String currency;

    public ProductAttributes() {

    }
    public ProductAttributes(String date, double price, String currency) {
        super();
        this.date = date;
        this.price = price;
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
