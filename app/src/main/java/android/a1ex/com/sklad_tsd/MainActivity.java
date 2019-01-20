package android.a1ex.com.sklad_tsd;

import android.a1ex.com.sklad_tsd.DataBase.DataBaseHelper;
import android.a1ex.com.sklad_tsd.Directories.Cell;
import android.a1ex.com.sklad_tsd.Directories.Product;
import android.a1ex.com.sklad_tsd.Lists.ListCells;
import android.a1ex.com.sklad_tsd.Lists.ListOfDocuments;
import android.a1ex.com.sklad_tsd.Lists.ListProducts;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public Button add_doc;
    public Button list_doc;
    public Button update_directories;

    private DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DataBaseHelper(this);

        Intent intent = new Intent(this, Password.class);
        startActivityForResult(intent, 1);

        add_doc = findViewById(R.id.add_doc);
        add_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DocumentView.class);
                startActivity(intent);
            }
        });

        list_doc = findViewById(R.id.list_doc);
        list_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListOfDocuments.class);
                startActivity(intent);
            }
        });

        update_directories = findViewById(R.id.update_directories);
        update_directories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Запущена загрузка в фоне...", Toast.LENGTH_LONG).show();

                for (int i = 0; i < 500; i++) {
                    helper.insertCell(new Cell("Cell" + i, "Cell" + i));
                }

                for (int i = 0; i < 500; i++) {
                    helper.insertProduct(new Product("Product " + i, "A" + i + "A" + i));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            finish();
        } else {
            Boolean mOK = true;

            if (!data.getBooleanExtra("result", mOK)) {
                finish();
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.list_products:
                startActivity(new Intent(this, ListProducts.class));
                break;
            case R.id.list_cells:
                startActivity(new Intent(this, ListCells.class));
                break;
        }
    }
}
