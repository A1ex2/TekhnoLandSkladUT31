package android.a1ex.com.sklad_tsd;

import android.a1ex.com.sklad_tsd.DataBase.DataBaseHelper;
import android.a1ex.com.sklad_tsd.Directories.Cell;
import android.a1ex.com.sklad_tsd.Directories.Product;
import android.a1ex.com.sklad_tsd.Documents.DataDocument.ProductsOfDocument;
import android.a1ex.com.sklad_tsd.Documents.Document;
import android.a1ex.com.sklad_tsd.Fragments.CellProducts;
import android.a1ex.com.sklad_tsd.Fragments.DocumentCells;
import android.a1ex.com.sklad_tsd.Lists.ListOfDocuments;
import android.a1ex.com.sklad_tsd.Loaders.DocumentCellsLoader;
import android.a1ex.com.sklad_tsd.Loaders.DocumentProductsCellLoader;
import android.a1ex.com.sklad_tsd.MyIntentService.IntentServiceDataBase;
import android.a1ex.com.sklad_tsd.MyIntentService.MyServiceDataBase;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DocumentView extends AppCompatActivity implements LoaderManager.LoaderCallbacks {
    private Document mDocument;
    private Cell activeCell;

    private Spinner mSpinner;
    private TextView dateDoc;
    private String[] typeDoc;
    private Button addCell;

    private static final int LOADER1 = 1;
    private static final int LOADER2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_residue_entry);

        addCell = findViewById(R.id.addCell);
        addCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCell(new Cell());
            }
        });

        typeDoc = getResources().getStringArray(R.array.type_doc);
        dateDoc = findViewById(R.id.dateDoc);
        dateDoc.setEnabled(false);

        mSpinner = findViewById(R.id.spinnerTypeDoc);
        ArrayAdapter<String> adapterType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeDoc);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapterType);

        Intent intent = getIntent();
        mDocument = intent.getParcelableExtra(ListOfDocuments.EXTRA_DOCUMENT);

        if (mDocument == null) {
            mDocument = new Document();
            mDocument.setType(typeDoc[0]);
        } else {
            mSpinner.setEnabled(false);
        }

        toGetData();
    }

    private void toGetData() {
        mSpinner.setSelection(getPositionType(mDocument.type));
        dateDoc.setText(mDocument.dateToFormat());

        getSupportLoaderManager().initLoader(LOADER1, null, this);
    }

    private int getPositionType(String type) {
        int position = -1;

        for (int i = 0; i < typeDoc.length; i++) {
            if (typeDoc[i].equals(type)) {
                position = i;
            }
        }

        return position;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.doc_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String typeDoc = mSpinner.getSelectedItem().toString();
        mDocument.type = typeDoc;

        switch (item.getItemId()) {
            case R.id.saveDoc:
                SaveDocument mSaveTask = new SaveDocument();
                mSaveTask.execute(mDocument);
                Toast.makeText(this, "документ записан", Toast.LENGTH_LONG).show();
                finish();
                return true;
            case R.id.pushDoc:
                Toast.makeText(this, "документ отправлен в ЦБ", Toast.LENGTH_LONG).show();
                finish();
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CellProducts.REQUEST_CODE_SCAN) {
            if (resultCode == RESULT_OK) {
//                Cell mCell = data.getParcelableExtra(CellProducts.EXTRA_CELL);
                activeCell = data.getParcelableExtra(CellProducts.EXTRA_CELL);
                Product mProduct = data.getParcelableExtra(CellProducts.EXTRA_PRODUCT);
                if (mProduct != null && mProduct.getVendorCode() != null) {
                    IntentServiceDataBase.insertProductCell(this, mDocument.getId(), activeCell.getId(), mProduct.getId(), 1);
                } else {
                    editCell(activeCell);
                }
            }
        } else if (requestCode == IntentServiceDataBase.REQUEST_CODE_SAVE_PRODUCT_CELL) {
            if (resultCode == RESULT_OK) {
                editCell(activeCell);
            }
        } else if (requestCode == CellProducts.REQUEST_CODE_DELETE) {
            if (resultCode == RESULT_OK) {
                editCell(activeCell);
            }
        }
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int i, @Nullable Bundle bundle) {

        if (i == LOADER1) {
            return new DocumentCellsLoader(this, mDocument.id);
        } else if (i == LOADER2) {
            Cell cell = bundle.getParcelable(CellProducts.EXTRA_CELL);
            return new DocumentProductsCellLoader(this, mDocument.id, cell);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        int id = loader.getId();
        if (id == LOADER1) {
            showCells((ArrayList<Cell>) data);
        } else if (id == LOADER2) {
            Bundle mBundle = (Bundle) data;
//            Cell mCell = mBundle.getParcelable(CellProducts.EXTRA_CELL);
            activeCell = mBundle.getParcelable(CellProducts.EXTRA_CELL);
            ArrayList<ProductsOfDocument> productsOfDocuments = mBundle.getParcelableArrayList(CellProducts.EXTRA_PRODUCTS_OF_DOCUMENT);

            editCellStart(activeCell, productsOfDocuments);
        }
    }

    private void showCells(ArrayList<Cell> cells) {
        DocumentCells mDocCells = DocumentCells.newDocumentCells(cells);
        mDocCells.setActionListener(new DocumentCells.ActionListener() {
            @Override
            public void edit(Cell cell) {
                activeCell = cell;
                editCell(activeCell);
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.docRoot, mDocCells).commitAllowingStateLoss();
        addCell.setVisibility(View.VISIBLE);
    }

    private void editCell(Cell cell) {
        if (mDocument.id == -1) {
            SaveDocument mSaveTask = new SaveDocument();
            mSaveTask.execute(mDocument);
            Toast.makeText(this, "документ записан", Toast.LENGTH_LONG).show();
        }

        Bundle args = new Bundle();
        args.putParcelable(CellProducts.EXTRA_CELL, cell);

//        getSupportLoaderManager().initLoader(LOADER2, args, this);
        getSupportLoaderManager().restartLoader(LOADER2, args, this);
    }

    private void editCellStart(Cell cell, ArrayList<ProductsOfDocument> productsOfDocument) {
        CellProducts cellProducts = CellProducts.newCellProducts(cell, productsOfDocument);
        cellProducts.setActionListener(new CellProducts.ActionListener() {
            @Override
            public void back() {
                getSupportLoaderManager().restartLoader(LOADER1, null, DocumentView.this);
            }

            @Override
            public void addBarCode(String barCode) {
                Intent intent = new Intent(DocumentView.this, MyServiceDataBase.class);
                PendingIntent pendingIntent = createPendingResult(CellProducts.REQUEST_CODE_SCAN, intent, 0);

                intent.putExtra(CellProducts.EXTRA_PENDING_INTENT, pendingIntent);
                intent.putExtra(CellProducts.EXTRA_FIND_BARCODE, barCode);
                intent.putExtra(CellProducts.EXTRA_CELL, activeCell);

                startService(intent);
//                Toast.makeText(DocumentView.this, barCode, Toast.LENGTH_LONG).show();
            }

            @Override
            public void deleteProductOfDocument(ProductsOfDocument productsOfDocument) {
                Intent intent = new Intent(DocumentView.this, MyServiceDataBase.class);
                PendingIntent pendingIntent = createPendingResult(CellProducts.REQUEST_CODE_DELETE, intent, 0);

                intent.putExtra(CellProducts.EXTRA_PENDING_INTENT, pendingIntent);
                intent.putExtra(CellProducts.EXTRA_PRODUCT_OF_DOCUMENT, productsOfDocument);

                startService(intent);
            }
        });

        getSupportFragmentManager().

                beginTransaction().

                replace(R.id.docRoot, cellProducts).

                commit();
        addCell.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

    public class SaveDocument extends AsyncTask<Document, Void, Long> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Long doInBackground(Document... documents) {
            DataBaseHelper helper = new DataBaseHelper(DocumentView.this);
            return helper.insertDocument(documents[0]);
        }

        @Override
        protected void onPostExecute(Long id) {
            super.onPostExecute(id);
            mDocument.id = id;
        }
    }
}
