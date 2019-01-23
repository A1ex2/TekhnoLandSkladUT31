package android.a1ex.com.sklad_tsd;

import android.a1ex.com.sklad_tsd.ConnectTo1c.SOAP_Dispatcher;
import android.a1ex.com.sklad_tsd.DataBase.DataBaseHelper;
import android.a1ex.com.sklad_tsd.Directories.Cell;
import android.a1ex.com.sklad_tsd.Lists.ListCells;
import android.a1ex.com.sklad_tsd.Lists.ListOfDocuments;
import android.a1ex.com.sklad_tsd.Lists.ListProducts;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button add_doc;
    private Button list_doc;
    private Button update_directories;

    public static final int ACTION_GetCellList = 10;
    public static final int ACTION_ConnectionError = 0;
    private boolean entrance;
    static final String STATE_ENTRANCE = "STATE_ENTRANCE";
    public static SoapObject soapParam_Response;
    public static Handler soapHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soapHandler = new incomingHandler(this);

        if (savedInstanceState != null) {
            entrance = savedInstanceState.getBoolean(STATE_ENTRANCE, false);
        }

        if (!entrance) {
//            Intent intent = new Intent(this, Password.class);
//            startActivityForResult(intent, 1);
        }

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

                startExchange(ACTION_GetCellList);

//                DataBaseHelper helper = new DataBaseHelper(MainActivity.this);
//                for (int i = 0; i < 500; i++) {
//                    helper.insertCell(new Cell("Cell" + i, "Cell" + i));
//                }
//
//                for (int i = 0; i < 500; i++) {
//                    helper.insertProduct(new Product("Product " + i, "A" + i + "A" + i));
//                }
            }
        });
    }

    protected void startExchange(int ACTION) {

        SOAP_Dispatcher dispatcher = new SOAP_Dispatcher(ACTION);
        dispatcher.start();

    }

    @Override
    protected void onRestart() {

        super.onRestart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1) {
            if (data == null) {
                finish();
            } else {
                Boolean mOK = true;

                if (!data.getBooleanExtra("result", mOK)) {
                    finish();
                }
                entrance = true;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        outState.putBoolean(STATE_ENTRANCE, entrance);
        super.onSaveInstanceState(outState, outPersistentState);
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

    class incomingHandler extends Handler {
        private final WeakReference<MainActivity> mTarget;

        public incomingHandler(MainActivity context) {
            mTarget = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity target = mTarget.get();

            switch (msg.what) {
                case ACTION_GetCellList:
                    Log.d("LoadData", "true Load data");
                    DataBaseHelper dbHelper = new DataBaseHelper(getBaseContext());
                    ArrayList<Cell> mCells = dbHelper.initialiseCellData();

                    if (mCells != null) {
                        for (Cell cell : mCells) {
                            dbHelper.insertCell(cell);
                        }
                    }
            }
        }
    }

}
