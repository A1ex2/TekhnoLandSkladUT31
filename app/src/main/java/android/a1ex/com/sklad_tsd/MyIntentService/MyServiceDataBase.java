package android.a1ex.com.sklad_tsd.MyIntentService;

import android.a1ex.com.sklad_tsd.DataBase.DataBaseHelper;
import android.a1ex.com.sklad_tsd.Directories.Cell;
import android.a1ex.com.sklad_tsd.Directories.Product;
import android.a1ex.com.sklad_tsd.DocumentView;
import android.a1ex.com.sklad_tsd.Documents.DataDocument.ProductsOfDocument;
import android.a1ex.com.sklad_tsd.Fragments.CellProducts;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyServiceDataBase extends Service {
    public MyServiceDataBase() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DataBaseHelper helper = new DataBaseHelper(this);

        PendingIntent pendingIntent = intent.getParcelableExtra(CellProducts.EXTRA_PENDING_INTENT);
        Intent result = new Intent();

        ProductsOfDocument productsOfDocument = intent.getParcelableExtra(CellProducts.EXTRA_PRODUCT_OF_DOCUMENT);
        if (productsOfDocument == null) {

            String barCode = intent.getStringExtra(CellProducts.EXTRA_FIND_BARCODE);
            Toast.makeText(this, barCode, Toast.LENGTH_LONG).show();

            Cell mCell = intent.getParcelableExtra(CellProducts.EXTRA_CELL);
            if (mCell.getAddress() == null) {
                mCell = helper.getCellToBarCode(barCode);
            } else {
                Product mProduct = helper.getProductToBarCode(barCode);
                result.putExtra(CellProducts.EXTRA_PRODUCT, mProduct);
            }
            result.putExtra(CellProducts.EXTRA_CELL, mCell);
        } else {
            helper.deleteProductOfDocument(productsOfDocument);
        }

        try {
            pendingIntent.send(this, DocumentView.RESULT_OK, result);
        } catch (
                PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
