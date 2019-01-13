package android.a1ex.com.sklad_tsd.Loaders;

import android.a1ex.com.sklad_tsd.DataBase.DataBaseHelper;
import android.a1ex.com.sklad_tsd.Directories.Cell;
import android.a1ex.com.sklad_tsd.Documents.Document;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

public class DocumentCellsLoader extends AsyncTaskLoader<ArrayList<Cell>> {
    private DataBaseHelper helper;
    private long id;


    public DocumentCellsLoader(Context context, long id) {
        super(context);
        this.helper = new DataBaseHelper(context);
        this.id = id;
    }

    @Nullable
    @Override
    public ArrayList<Cell> loadInBackground() {
        return helper.getCellsDocument(id);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
