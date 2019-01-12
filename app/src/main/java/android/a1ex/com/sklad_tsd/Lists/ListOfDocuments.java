package android.a1ex.com.sklad_tsd.Lists;

import android.a1ex.com.sklad_tsd.DocumentView;
import android.a1ex.com.sklad_tsd.Documents.Document;
import android.a1ex.com.sklad_tsd.Loaders.DocumentLoader;
import android.a1ex.com.sklad_tsd.R;
import android.a1ex.com.sklad_tsd.RecyclerAdapters.RecyclerAdapterDocuments;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class ListOfDocuments extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Document>>{
    private Spinner mSpinner;
    private String typeDoc;

    private RecyclerView recycler;
    private RecyclerAdapterDocuments adapter;
    private ArrayList<Document> mDocuments = new ArrayList<>();

    public static final String EXTRA_DOCUMENT = "android.a1ex.com.homework10.extra.DOCUMENT";
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_document_residue_entry);

        mSpinner = findViewById(R.id.spinnerTypeDocList);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.type_doc);
                typeDoc = choose[position];
                getSupportLoaderManager().restartLoader(0, null, ListOfDocuments.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        typeDoc = mSpinner.getSelectedItem().toString();
        recycler = findViewById(R.id.recyclerDoc);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapterDocuments(this, R.layout.document_item, mDocuments);
        recycler.setAdapter(adapter);

        adapter.setActionListener(new RecyclerAdapterDocuments.ActionListener() {
            @Override
            public void onClick(Document document) {
                Intent intent = new Intent(ListOfDocuments.this, DocumentView.class);
                intent.putExtra(EXTRA_DOCUMENT, document);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == IntentServiceDataBase.REQUEST_CODE_GET_DOCUMENTS) {
//            if (resultCode == RESULT_OK) {
//
//            }
//        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<Document>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new DocumentLoader(this, typeDoc);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Document>> loader, ArrayList<Document> documents) {
        mDocuments.clear();
        mDocuments.addAll(documents);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Document>> loader) {

    }
}
