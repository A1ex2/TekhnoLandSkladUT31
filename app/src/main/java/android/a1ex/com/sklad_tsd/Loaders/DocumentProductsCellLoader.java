package android.a1ex.com.sklad_tsd.Loaders;

import android.a1ex.com.sklad_tsd.DataBase.DataBaseHelper;
import android.a1ex.com.sklad_tsd.Directories.Cell;
import android.a1ex.com.sklad_tsd.Documents.DataDocument.ProductsOfDocument;
import android.a1ex.com.sklad_tsd.Fragments.CellProducts;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

public class DocumentProductsCellLoader extends AsyncTaskLoader<Bundle> {
    private DataBaseHelper helper;
    private long idDocument;
    private Cell mCell;


    public DocumentProductsCellLoader(Context context, long idDocument, Cell cell) {
        super(context);
        this.helper = new DataBaseHelper(context);
        this.idDocument = idDocument;
        this.mCell = cell;
    }

    @Nullable
    @Override
    public Bundle loadInBackground() {
        ArrayList<ProductsOfDocument> mProductsOfDocuments = helper.getProductsCellDocument(idDocument, mCell.id);

        Bundle args = new Bundle();
        args.putParcelableArrayList(CellProducts.EXTRA_PRODUCTS_OF_DOCUMENT, mProductsOfDocuments);
        args.putParcelable(CellProducts.EXTRA_CELL, mCell);

        return args;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
