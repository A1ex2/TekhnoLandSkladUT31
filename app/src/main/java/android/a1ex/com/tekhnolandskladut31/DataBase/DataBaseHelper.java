package android.a1ex.com.tekhnolandskladut31.DataBase;

import android.a1ex.com.tekhnolandskladut31.Directories.Cell;
import android.a1ex.com.tekhnolandskladut31.Directories.Product;
import android.a1ex.com.tekhnolandskladut31.Documents.Document;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context) {
        super(context, "MyBD.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Document.TABLE_NAME + " ("
                + Document.COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + Document.COLUM_TYPE + " TEXT NOT NULL, "
                + Document.COLUM_LAST_DATE_CREATE + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + Cell.TABLE_NAME + " ("
                + Cell.COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + Cell.COLUM_NAME + " TEXT NOT NULL, "
                + Cell.COLUM_ADDRESS + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + Product.TABLE_NAME + " ("
                + Product.COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + Product.COLUM_NAME + " TEXT NOT NULL, "
                + Product.COLUM_VENDOR_CODE + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + ProductsOfDocuments.TABLE_NAME + "("
                + ProductsOfDocuments.COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"

                + ProductsOfDocuments.COLUM_ID_DOCUMENT + " INTEGER NOT NULL,"
                + ProductsOfDocuments.COLUM_ID_CELL + " INTEGER NOT NULL,"
                + ProductsOfDocuments.COLUM_ID_PRODUCT + " INTEGER NOT NULL,"

                + ProductsOfDocuments.COLUM_ID_GUANTITY + " INTEGER NOT NULL,"

                + "FOREIGN KEY (" + ProductsOfDocuments.COLUM_ID_DOCUMENT + ") REFERENCES "
                + Document.TABLE_NAME + "(" + Document.COLUM_ID + "), "

                + "FOREIGN KEY (" + ProductsOfDocuments.COLUM_ID_CELL + ") REFERENCES "
                + Cell.TABLE_NAME + "(" + Cell.COLUM_ID + "),"

                + "FOREIGN KEY (" + ProductsOfDocuments.COLUM_ID_PRODUCT + ") REFERENCES "
                + Product.TABLE_NAME + "(" + Product.COLUM_ID + ")" + ")");
    }

    public long insertCell(Cell cell){
        SQLiteDatabase db = getReadableDatabase();
        long id = 0;

        try {
            ContentValues values = new ContentValues();

            values.put(Cell.COLUM_NAME, cell.name);
            values.put(Cell.COLUM_ADDRESS, cell.address);

            id = db.insert(Cell.TABLE_NAME, null, values);
        } catch (Exception e){
            e.printStackTrace();
        }

        return id;
    }

    public ArrayList<Cell> getCells(){
        ArrayList<Cell> cells = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Cell.TABLE_NAME,null,null,null,null,null,null);

            if (cursor.moveToNext()){
                while (!cursor.isAfterLast()){
                    Cell cell = new Cell();
                    cell.setId(cursor.getLong(cursor.getColumnIndex(Cell.COLUM_ID)));
                    cell.setName(cursor.getString(cursor.getColumnIndex(Cell.COLUM_NAME)));
                    cell.setAddress(cursor.getString(cursor.getColumnIndex(Cell.COLUM_NAME)));

                    cells.add(cell);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (cursor != null){
                cursor.close();
            }
        }

        return cells;
    }

    public long insertProduct(Product product){
        SQLiteDatabase db = getReadableDatabase();
        long id = 0;

        try {
            ContentValues values = new ContentValues();

            values.put(Product.COLUM_NAME, product.getName());
            values.put(Product.COLUM_VENDOR_CODE, product.getVendorCode());

            id = db.insert(Product.TABLE_NAME, null, values);
        } catch (Exception e){
            e.printStackTrace();
        }

        return id;
    }

    public ArrayList<Product> getProducts(){
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Product.TABLE_NAME,null,null,null,null,null,null);

            if (cursor.moveToNext()){
                while (!cursor.isAfterLast()){
                    Product product = new Product();
                    product.setId(cursor.getLong(cursor.getColumnIndex(Product.COLUM_ID)));
                    product.setName(cursor.getString(cursor.getColumnIndex(Product.COLUM_NAME)));
                    product.setVendorCode(cursor.getString(cursor.getColumnIndex(Product.COLUM_VENDOR_CODE)));

                    products.add(product);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (cursor != null){
                cursor.close();
            }
        }

        return products;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
