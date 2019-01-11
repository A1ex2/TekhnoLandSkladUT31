package android.a1ex.com.sklad_tsd.Directories;

public class Product {
    public static final String TABLE_NAME = "Products";

    public static final String COLUM_ID = "_id";
    public static final String COLUM_NAME = "Name";
    public static final String COLUM_VENDOR_CODE = "VendorCode";
    public static final String COLUM_VENDOR_BARCODE = "Barcode";

    public long id;
    public String name;
    public String vendorCode;
    public String barcode;

    public Product() {
    }

    public Product(String name, String vendorCode) {
        this.name = name;
        this.vendorCode = vendorCode;
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return vendorCode + " / " + name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }
}
