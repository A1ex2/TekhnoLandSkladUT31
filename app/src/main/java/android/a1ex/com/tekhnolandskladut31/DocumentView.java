package android.a1ex.com.tekhnolandskladut31;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Date;

public class DocumentView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_residue_entry);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.doc_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveDoc:
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

}
