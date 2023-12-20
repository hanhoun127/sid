package project;
import java.io.Serializable;

public class Article implements Serializable {
    private static final long serialVersionUID = 1L;
    private String barcode;
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    private String articleName;
    public String getArticleName() {
        return articleName;
    }
    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }
    private double articlePrice;
    public double getArticlePrice() {
        return articlePrice;
    }
    public void setArticlePrice(double articlePrice) {
        this.articlePrice = articlePrice;
    }
    private int stock;
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public Article(){
    }
}
