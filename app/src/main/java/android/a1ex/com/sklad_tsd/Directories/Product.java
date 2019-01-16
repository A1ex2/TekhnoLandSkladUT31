package android.a1ex.com.sklad_tsd.Directories;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.vendorCode);
        dest.writeString(this.barcode);
    }

    protected Product(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.vendorCode = in.readString();
        this.barcode = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
