package android.a1ex.com.sklad_tsd.Loaders;

import android.a1ex.com.sklad_tsd.DataBase.DataBaseHelper;
import android.a1ex.com.sklad_tsd.Directories.Cell;
import android.a1ex.com.sklad_tsd.Documents.DataDocument.ProductsOfDocument;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

public class DocumentProductsCellLoader extends AsyncTaskLoader<ArrayList<ProductsOfDocument>> {
    private DataBaseHelper helper;
    private long idDocument;
    private long idCell;


    public DocumentProductsCellLoader(Context context, long idDocument, long idCell) {
        super(context);
        this.helper = new DataBaseHelper(context);
        this.idDocument = idDocument;
        this.idCell = idCell;
    }

    @Nullable
    @Override
    public ArrayList<ProductsOfDocument> loadInBackground() {
        return helper.getProductsCellDocument(idDocument, idCell);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
