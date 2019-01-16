package android.a1ex.com.sklad_tsd.Documents.DataDocument;

import android.a1ex.com.sklad_tsd.Directories.Cell;
import android.a1ex.com.sklad_tsd.Directories.Product;
import android.os.Parcel;
import android.os.Parcelable;

public class ProductsOfDocument implements Parcelable {
    public static final String TABLE_NAME = "ProductsOfDocument";

    public static final String COLUM_ID = "_id";
    public static final String COLUM_ID_DOCUMENT = "_idDocument";
    public static final String COLUM_ID_CELL = "_idCell";
    public static final String COLUM_ID_PRODUCT = "_idProduct";
    public static final String COLUM_ID_GUANTITY = "quantity";

    private long id;
    private long idCell;
    private long idDocument;
    private Product mProduct;
    private Cell mCell;
    private long quantity;

    public ProductsOfDocument() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdCell() {
        return idCell;
    }

    public void setIdCell(long idCell) {
        this.idCell = idCell;
    }

    public long getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(long idDocument) {
        this.idDocument = idDocument;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return mProduct;
    }

    public void setProduct(Product product) {
        mProduct = product;
    }

    public Cell getCell() {
        return mCell;
    }

    public void setCell(Cell cell) {
        mCell = cell;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.idCell);
        dest.writeLong(this.idDocument);
        dest.writeParcelable(this.mProduct, flags);
        dest.writeParcelable(this.mCell, flags);
        dest.writeLong(this.quantity);
    }

    protected ProductsOfDocument(Parcel in) {
        this.id = in.readLong();
        this.idCell = in.readLong();
        this.idDocument = in.readLong();
        this.mProduct = in.readParcelable(Product.class.getClassLoader());
        this.mCell = in.readParcelable(Cell.class.getClassLoader());
        this.quantity = in.readLong();
    }

    public static final Creator<ProductsOfDocument> CREATOR = new Creator<ProductsOfDocument>() {
        @Override
        public ProductsOfDocument createFromParcel(Parcel source) {
            return new ProductsOfDocument(source);
        }

        @Override
        public ProductsOfDocument[] newArray(int size) {
            return new ProductsOfDocument[size];
        }
    };
}
