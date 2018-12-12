package android.a1ex.com.tekhnolandskladut31;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public Button add_doc;
    public Button list_doc;
    public Button update_directories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, Password.class);
        startActivityForResult(intent, 1);

        add_doc = findViewById(R.id.add_doc);
        add_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DocumentResidueEntry.class);
                startActivity(intent);
            }
        });

        list_doc = findViewById(R.id.list_doc);
        list_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListOfDocumentsResidueEntry.class);
                startActivity(intent);
            }
        });

        update_directories = findViewById(R.id.update_directories);
        update_directories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Запущена загрузка в фоне...", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null){
            finish();
        } else {
            Boolean mOK = true;

            if (!data.getBooleanExtra("result", mOK)) {
                finish();
            }
        }
    }
}
