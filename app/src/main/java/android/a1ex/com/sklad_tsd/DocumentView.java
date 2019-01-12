package android.a1ex.com.sklad_tsd;

import android.a1ex.com.sklad_tsd.DataBase.DataBaseHelper;
import android.a1ex.com.sklad_tsd.Documents.Document;
import android.a1ex.com.sklad_tsd.Lists.ListOfDocuments;
import android.a1ex.com.sklad_tsd.MyIntentService.IntentServiceDataBase;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DocumentView extends AppCompatActivity {
    private Spinner mSpinner;
    private Document mDocument;
    private TextView dateDoc;
    private String[] typeDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_residue_entry);

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

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        DocumentCells docCells = new DocumentCells();
        fTrans.add(R.id.docRoot, docCells);
        fTrans.commit();

        toGetData();

    }

    private void toGetData() {
        mSpinner.setSelection(getPositionType(mDocument.type));
        dateDoc.setText(mDocument.dateToFormat());

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

        if (requestCode == IntentServiceDataBase.REQUEST_CODE_SAVE_DOCUMENTS) {
            if (resultCode == RESULT_OK) {

            }
        }
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
