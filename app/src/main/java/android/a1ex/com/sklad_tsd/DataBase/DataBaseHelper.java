package android.a1ex.com.sklad_tsd.DataBase;

import android.a1ex.com.sklad_tsd.Directories.Cell;
import android.a1ex.com.sklad_tsd.Directories.Product;
import android.a1ex.com.sklad_tsd.Documents.DataDocument.ProductsOfDocument;
import android.a1ex.com.sklad_tsd.Documents.Document;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context) {
        super(context, "MyBD.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Document.TABLE_NAME + " ("
                + Document.COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Document.COLUM_TYPE + " TEXT NOT NULL, "
                + Document.COLUM_DATE_CREATE + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + Cell.TABLE_NAME + " ("
                + Cell.COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Cell.COLUM_NAME + " TEXT NOT NULL, "
                + Cell.COLUM_ADDRESS + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + Product.TABLE_NAME + " ("
                + Product.COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Product.COLUM_NAME + " TEXT NOT NULL, "
                + Product.COLUM_VENDOR_CODE + " TEXT NOT NULL)");

//        db.execSQL("CREATE TABLE " + CellsOfDocument.TABLE_NAME + "("
//                + CellsOfDocument.COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + CellsOfDocument.COLUM_ID_DOCUMENT + " INTEGER NOT NULL,"
//                + "FOREIGN KEY (" + CellsOfDocument.COLUM_ID_DOCUMENT + ") REFERENCES "
//                + Document.TABLE_NAME + "(" + Document.COLUM_ID + ")" + ")");

        db.execSQL("CREATE TABLE " + ProductsOfDocument.TABLE_NAME + "("
                + ProductsOfDocument.COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ProductsOfDocument.COLUM_ID_DOCUMENT + " INTEGER NOT NULL,"
                + ProductsOfDocument.COLUM_ID_CELL + " INTEGER NOT NULL,"
                + ProductsOfDocument.COLUM_ID_PRODUCT + " INTEGER NOT NULL,"
                + ProductsOfDocument.COLUM_GUANTITY + " INTEGER NOT NULL,"
                + "FOREIGN KEY (" + ProductsOfDocument.COLUM_ID_DOCUMENT + ") REFERENCES "
                + Document.TABLE_NAME + "(" + Document.COLUM_ID + "), "
                + "FOREIGN KEY (" + ProductsOfDocument.COLUM_ID_CELL + ") REFERENCES "
                + Cell.TABLE_NAME + "(" + Cell.COLUM_ID + "),"
                + "FOREIGN KEY (" + ProductsOfDocument.COLUM_ID_PRODUCT + ") REFERENCES "
                + Product.TABLE_NAME + "(" + Product.COLUM_ID + ")" + ")");
    }

    public long insertDocument(Document document) {
        SQLiteDatabase db = getReadableDatabase();
        long id = 0;

        try {
            ContentValues values = new ContentValues();

            values.put(Document.COLUM_TYPE, document.getType());
            values.put(Document.COLUM_DATE_CREATE, dateToFormat(document.getDateCreate()));

            id = db.insert(Document.TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public ArrayList<Document> getDocuments(String typeDoc) {
        ArrayList<Document> documents = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            String select = Document.TABLE_NAME + "." + Document.COLUM_TYPE + "='" + typeDoc + "'";
            cursor = db.query(Document.TABLE_NAME, null, select, null, null, null, null);

            if (cursor.moveToNext()) {
                while (!cursor.isAfterLast()) {
                    Document document = new Document();

                    //cursor.getColumnNames();

                    document.id = cursor.getLong(cursor.getColumnIndex(Document.COLUM_ID));
                    document.type = cursor.getString(cursor.getColumnIndex(Document.COLUM_TYPE));
                    document.dateCreate = stringToDate(cursor.getString(cursor.getColumnIndex(Document.COLUM_DATE_CREATE)));
                    documents.add(document);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return documents;
    }

    public long insertCell(Cell cell) {
        SQLiteDatabase db = getReadableDatabase();
        long id = 0;

        try {
            ContentValues values = new ContentValues();

            values.put(Cell.COLUM_NAME, cell.name);
            values.put(Cell.COLUM_ADDRESS, cell.address);

            id = db.insert(Cell.TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public ArrayList<Cell> getCells() {
        ArrayList<Cell> cells = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Cell.TABLE_NAME, null, null, null, null, null, null);

            if (cursor.moveToNext()) {
                while (!cursor.isAfterLast()) {
                    Cell cell = new Cell();
                    cell.setId(cursor.getLong(cursor.getColumnIndex(Cell.COLUM_ID)));
                    cell.setName(cursor.getString(cursor.getColumnIndex(Cell.COLUM_NAME)));
                    cell.setAddress(cursor.getString(cursor.getColumnIndex(Cell.COLUM_ADDRESS)));

                    cells.add(cell);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return cells;
    }

    public Cell getCell(long id) {
        Cell cell = new Cell();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Cell.TABLE_NAME, null, Cell.COLUM_ID + " = " + id, null, null, null, null);

            if (cursor.moveToNext()) {
                cell.setId(cursor.getLong(cursor.getColumnIndex(Cell.COLUM_ID)));
                cell.setName(cursor.getString(cursor.getColumnIndex(Cell.COLUM_NAME)));
                cell.setAddress(cursor.getString(cursor.getColumnIndex(Cell.COLUM_NAME)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return cell;
    }

    public Cell getCellToBarCode(String barCode) {
        Cell cell = new Cell();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            String select = Cell.TABLE_NAME + "." + Cell.COLUM_ADDRESS + " = '" + barCode + "'";
            cursor = db.query(Cell.TABLE_NAME, null, select, null, null, null, null);

            if (cursor.moveToNext()) {
                cell.setId(cursor.getLong(cursor.getColumnIndex(Cell.COLUM_ID)));
                cell.setName(cursor.getString(cursor.getColumnIndex(Cell.COLUM_NAME)));
                cell.setAddress(cursor.getString(cursor.getColumnIndex(Cell.COLUM_NAME)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return cell;
    }

    public ArrayList<Cell> getCellsDocument(long id) {
        ArrayList<Cell> cells = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            String select = ProductsOfDocument.TABLE_NAME + "." + ProductsOfDocument.COLUM_ID_DOCUMENT + "=" + id;
            cursor = db.query(ProductsOfDocument.TABLE_NAME, null, select, null, null, null, null);

            if (cursor.moveToNext()) {
                while (!cursor.isAfterLast()) {

                    long idCell = cursor.getLong(cursor.getColumnIndex(ProductsOfDocument.COLUM_ID_CELL));
                    Cell cell = getCell(idCell);
                    cells.add(cell);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return cells;
    }

    public long insertProduct(Product product) {
        SQLiteDatabase db = getReadableDatabase();
        long id = 0;

        try {
            ContentValues values = new ContentValues();

            values.put(Product.COLUM_NAME, product.getName());
            values.put(Product.COLUM_VENDOR_CODE, product.getVendorCode());

            id = db.insert(Product.TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public long insertProductCell(long idDocument, long idCell, long idProduct, long quantity) {
        SQLiteDatabase db = getReadableDatabase();
        long id = 0;

        try {
            ContentValues values = new ContentValues();

            values.put(ProductsOfDocument.COLUM_ID_DOCUMENT, idDocument);
            values.put(ProductsOfDocument.COLUM_ID_CELL, idCell);
            values.put(ProductsOfDocument.COLUM_ID_PRODUCT, idProduct);
            values.put(ProductsOfDocument.COLUM_GUANTITY, quantity);

            id = db.insert(ProductsOfDocument.TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public ArrayList<ProductsOfDocument> getProductsCellDocument(long idDocument, long idCell) {
        ArrayList<ProductsOfDocument> products = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            String select = ProductsOfDocument.TABLE_NAME + "."
                    + ProductsOfDocument.COLUM_ID_DOCUMENT + " = " + idDocument + " AND "
                    + ProductsOfDocument.COLUM_ID_CELL + " = " + idCell;
            ;
            cursor = db.query(ProductsOfDocument.TABLE_NAME, null, select, null, null, null, null);

            if (cursor.moveToNext()) {
                while (!cursor.isAfterLast()) {
                    ProductsOfDocument product = new ProductsOfDocument();

                    Product mProduct = getProduct(cursor.getLong(cursor.getColumnIndex(ProductsOfDocument.COLUM_ID_PRODUCT)));
                    product.setProduct(mProduct);

                    Cell mCell = getCell(cursor.getLong(cursor.getColumnIndex(ProductsOfDocument.COLUM_ID_CELL)));
                    product.setCell(mCell);

                    product.setQuantity(cursor.getLong(cursor.getColumnIndex(ProductsOfDocument.COLUM_GUANTITY)));

                    products.add(product);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return products;
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Product.TABLE_NAME, null, null, null, null, null, null);

            if (cursor.moveToNext()) {
                while (!cursor.isAfterLast()) {
                    Product product = new Product();
                    product.setId(cursor.getLong(cursor.getColumnIndex(Product.COLUM_ID)));
                    product.setName(cursor.getString(cursor.getColumnIndex(Product.COLUM_NAME)));
                    product.setVendorCode(cursor.getString(cursor.getColumnIndex(Product.COLUM_VENDOR_CODE)));

                    products.add(product);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return products;
    }

    public Product getProductToBarCode(String barCode) {
        Product product = new Product();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            String select = Product.TABLE_NAME + "." + Product.COLUM_VENDOR_CODE + " = '" + barCode + "'";
            cursor = db.query(Product.TABLE_NAME, null, select, null, null, null, null);

            if (cursor.moveToNext()) {
                product.setId(cursor.getLong(cursor.getColumnIndex(Product.COLUM_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(Product.COLUM_NAME)));
                product.setVendorCode(cursor.getString(cursor.getColumnIndex(Product.COLUM_VENDOR_CODE)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return product;
    }

    public Product getProduct(long id) {
        Product product = new Product();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Product.TABLE_NAME, null, Product.COLUM_ID + " = " + id, null, null, null, null);

            if (cursor.moveToNext()) {
                product.setId(cursor.getLong(cursor.getColumnIndex(Product.COLUM_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(Product.COLUM_NAME)));
                product.setVendorCode(cursor.getString(cursor.getColumnIndex(Product.COLUM_VENDOR_CODE)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return product;
    }

    private String dateToFormat(Date date) {
        String retval = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        if (date == null) {
            return retval;
        }
        retval = sdf.format(date);
        return retval;
    }

    private Date stringToDate(String text) {
        Date mDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

        if (!text.isEmpty()) {
            try {
                mDate = sdf.parse(text);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return mDate;
    }

    public void deleteProductOfDocument(ProductsOfDocument productsOfDocument){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ProductsOfDocument.TABLE_NAME, ProductsOfDocument.COLUM_ID + "=" + productsOfDocument.getId(), null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
