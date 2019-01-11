package android.a1ex.com.sklad_tsd;

import android.a1ex.com.sklad_tsd.Documents.Document;
import android.a1ex.com.sklad_tsd.MyIntentService.IntentServiceDataBase;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

public class DocumentView extends AppCompatActivity {
    private Spinner mSpinner;
    private Document mDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_residue_entry);

        mDocument = new Document();

        mSpinner = findViewById(R.id.spinnerTypeDoc);
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
                IntentServiceDataBase.saveDocuments(this, mDocument);
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
                Toast.makeText(this, "документ записан", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
