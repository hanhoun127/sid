package project;
import java.io.Serializable;

public class Medicament implements Serializable {
    private static final long serialVersionUID = 1L;
    private String barcode;
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    private String MedicamentName;
    public String getMedicamentName() {
        return MedicamentName;
    }
    public void setMedicamentName(String MedicamentName) {
        this.MedicamentName = MedicamentName;
    }
    private double MedicamentPrice;
    public double getMedicamentPrice() {
        return MedicamentPrice;
    }
    public void setMedicamentPrice(double MedicamentPrice) {
        this.MedicamentPrice = MedicamentPrice;
    }
    private int stock;
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public Medicament(){
    }
}
