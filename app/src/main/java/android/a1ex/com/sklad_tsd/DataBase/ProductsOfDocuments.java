package android.a1ex.com.sklad_tsd.DataBase;

public class ProductsOfDocuments {
    public static final String TABLE_NAME = "ProductsOfDocuments";

    public static final String COLUM_ID = "_id";
    public static final String COLUM_ID_DOCUMENT = "_idDocument";
    public static final String COLUM_ID_CELL = "_idCell";
    public static final String COLUM_ID_PRODUCT = "_idProduct";
    public static final String COLUM_ID_GUANTITY = "quantity";

    public long id;
    public long idCell;
    public long idDocument;
    public long quantity;


}
