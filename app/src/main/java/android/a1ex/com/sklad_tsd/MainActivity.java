package android.a1ex.com.sklad_tsd;

import android.a1ex.com.sklad_tsd.ConnectTo1c.SOAP_Dispatcher;
import android.a1ex.com.sklad_tsd.ConnectTo1c.UIManager;
import android.a1ex.com.sklad_tsd.DataBase.DataBaseHelper;
import android.a1ex.com.sklad_tsd.Directories.Cell;
import android.a1ex.com.sklad_tsd.Directories.Product;
import android.a1ex.com.sklad_tsd.Lists.ListCells;
import android.a1ex.com.sklad_tsd.Lists.ListOfDocuments;
import android.a1ex.com.sklad_tsd.Lists.ListProducts;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button addDoc;
    private Button listDoc;
    private Button updateCells;
    private Button updateProducts;

    public static final int ACTION_GetCellList = 10;
    public static final int ACTION_GetProductList = 11;
    public static final int ACTION_ConnectionError = 0;

    private boolean entrance;
    static final String STATE_ENTRANCE = "STATE_ENTRANCE";
    public static UIManager uiManager;
    public static SoapFault responseFault;

    public static SoapObject soapParam_Response;
    public static Handler soapHandler;

    private SaveTaskCells mTaskCells;
    private SaveTaskProduct mTaskProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uiManager = new UIManager(this);
        soapHandler = new incomingHandler(this);

        if (savedInstanceState != null) {
            entrance = savedInstanceState.getBoolean(STATE_ENTRANCE, false);
        }

        if (!entrance) {
//            Intent intent = new Intent(this, Password.class);
//            startActivityForResult(intent, 1);
        }

        addDoc = findViewById(R.id.add_doc);
        addDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DocumentView.class);
                startActivity(intent);
            }
        });

        listDoc = findViewById(R.id.list_doc);
        listDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListOfDocuments.class);
                startActivity(intent);
            }
        });

        updateCells = findViewById(R.id.update_Cells);
        updateCells.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Запущена загрузка...", Toast.LENGTH_LONG).show();
                startExchange(ACTION_GetCellList);
            }
        });

        updateProducts = findViewById(R.id.update_Products);
        updateProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Запущена загрузка...", Toast.LENGTH_LONG).show();
                startExchange(ACTION_GetProductList);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mTaskCells != null) {
            mTaskCells.cancel(true);
        }

        if (mTaskProduct != null) {
            mTaskProduct.cancel(true);
        }
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
            case R.id.buttonClear:
                DataBaseHelper helper = new DataBaseHelper(getBaseContext());
                helper.deleteCellsAll();
                helper.deleteProductAll();
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
                case ACTION_ConnectionError:
                    uiManager.showToast("Ошибка: " + getSoapErrorMessage());
                    break;

                case ACTION_GetCellList: {
                    Log.d("LoadData", "true Load data");
                    DataBaseHelper dbHelper = new DataBaseHelper(getBaseContext());
                    ArrayList<Cell> mCells = dbHelper.initialiseCellData();

                    if (mCells != null) {
                        mTaskCells = new SaveTaskCells();
                        mTaskCells.execute(mCells);
                    }
                }
                break;
                case ACTION_GetProductList: {
                    Log.d("LoadData", "true Load data");
                    DataBaseHelper dbHelper = new DataBaseHelper(getBaseContext());
                    ArrayList<Product> mProducts = dbHelper.initialiseProductData();

                    if (mProducts != null) {
                        mTaskProduct = new SaveTaskProduct();
                        mTaskProduct.execute(mProducts);
                    }
                }
                break;
            }
        }
    }

    public class SaveTaskCells extends AsyncTask<ArrayList<Cell>, Integer, Boolean> {
        private ProgressDialog mDialog;

        public SaveTaskCells() {
        }

        @Override
        protected Boolean doInBackground(ArrayList<Cell>... arrayLists) {
            DataBaseHelper dbHelper = new DataBaseHelper(getBaseContext());

            for (ArrayList<Cell> mCells : arrayLists) {
                for (int i = 0; i < mCells.size() - 1; i++) {
                    Cell cell = mCells.get(i);
                    if (dbHelper.getCellToBarCode(cell.getAddress()).getAddress() == null) {
                        dbHelper.insertCell(cell);
                    }
                    publishProgress(mCells.size(), i + 1);
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mDialog = new ProgressDialog(MainActivity.this);
            mDialog.setMessage("Wait...");
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mDialog.setMessage(String.format("Saved %d from %d", values[1], values[0]));
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Toast.makeText(MainActivity.this, "загрузка ячеек завершена", Toast.LENGTH_LONG).show();
        }
    }

    public class SaveTaskProduct extends AsyncTask<ArrayList<Product>, Integer, Boolean> {
        private ProgressDialog mDialog;

        public SaveTaskProduct() {
        }

        @Override
        protected Boolean doInBackground(ArrayList<Product>... arrayLists) {
            DataBaseHelper dbHelper = new DataBaseHelper(getBaseContext());

            for (ArrayList<Product> mProducts : arrayLists) {
                for (int i = 0; i < mProducts.size() - 1; i++) {
                    Product product = mProducts.get(i);
                    if (dbHelper.getProductToBarCode(product.getBarcode()).getBarcode() == null) {
                        dbHelper.insertProduct(product);
                    }
                    publishProgress(mProducts.size(), i + 1);
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mDialog = new ProgressDialog(MainActivity.this);
            mDialog.setMessage("Wait...");
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mDialog.setMessage(String.format("Saved %d from %d", values[1], values[0]));
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Toast.makeText(MainActivity.this, "загрузка номенклатуры завершена", Toast.LENGTH_LONG).show();
        }
    }

    private String getSoapErrorMessage() {

        String errorMessage;

        if (responseFault == null)
            errorMessage = getString(R.string.textNoInternet);
        else {
            try {
                errorMessage = responseFault.faultstring;
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = "Неизвестная ошибка.";
            }
        }
        return errorMessage;
    }

}
