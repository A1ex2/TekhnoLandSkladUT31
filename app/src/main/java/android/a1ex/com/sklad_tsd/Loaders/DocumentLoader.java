package android.a1ex.com.sklad_tsd.Loaders;

import android.a1ex.com.sklad_tsd.DataBase.DataBaseHelper;
import android.a1ex.com.sklad_tsd.Documents.Document;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

public class DocumentLoader extends AsyncTaskLoader<ArrayList<Document>> {
    private DataBaseHelper helper;
    private String typeDoc;

    public DocumentLoader(Context context, String typeDoc) {
        super(context);

        this.helper = new DataBaseHelper(context);
        this.typeDoc = typeDoc;
    }

    @Override
    public ArrayList<Document> loadInBackground() {
        return helper.getDocuments(typeDoc);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}