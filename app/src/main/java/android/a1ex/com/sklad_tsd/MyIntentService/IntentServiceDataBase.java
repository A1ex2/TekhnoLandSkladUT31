package android.a1ex.com.sklad_tsd.MyIntentService;

import android.a1ex.com.sklad_tsd.DataBase.DataBaseHelper;
import android.a1ex.com.sklad_tsd.Documents.Document;
import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;


public class IntentServiceDataBase extends IntentService {

    private static final String EXTRA_PENDING_INTENT = "android.a1ex.com.sklad_tsd.extra.PENDING_INTENT";

    private static final String ACTION_GET_DOCUMENTS = "android.a1ex.com.sklad_tsd.action.ACTION_GET_DOCUMENTS";
    public static final String EXTRA_GET_DOCUMENTS = "android.a1ex.com.sklad_tsd.extra.EXTRA_GET_DOCUMENTS";
    public static final String EXTRA_TYPE_DOCUMENTS = "android.a1ex.com.sklad_tsd.extra.EXTRA_TYPE_DOCUMENTS";

    private static final String ACTION_SAVE_DOCUMENTS = "android.a1ex.com.sklad_tsd.action.ACTION_SAVE_DOCUMENTS";
    public static final String EXTRA_SAVE_DOCUMENT = "android.a1ex.com.sklad_tsd.extra.EXTRA_SAVE_DOCUMENT";
    public static final String EXTRA_ID_DOCUMENT = "android.a1ex.com.sklad_tsd.extra.EXTRA_ID_DOCUMENT";

    private static final String ACTION_SAVE_PRODUCT_CELL = "android.a1ex.com.sklad_tsd.action.ACTION_SAVE_PRODUCT_CELL";
    public static final String EXTRA_PRODUCT = "android.a1ex.com.sklad_tsd.extra.EXTRA_PRODUCT";
    public static final String EXTRA_CELL = "android.a1ex.com.sklad_tsd.extra.EXTRA_CELL";
    public static final String EXTRA_GUANTITY = "android.a1ex.com.sklad_tsd.extra.EXTRA_GUANTITY";

    public static final int REQUEST_CODE_GET_DOCUMENTS = 11;
    public static final int REQUEST_CODE_SAVE_DOCUMENTS = 12;
    public static final int REQUEST_CODE_SAVE_PRODUCT_CELL = 13;


    public IntentServiceDataBase() {
        super("IntentServiceDataBase");
    }

    public static void saveDocuments(AppCompatActivity activity, Document document) {
        Intent intent = new Intent(activity, IntentServiceDataBase.class);
        PendingIntent pendingIntent = activity.createPendingResult(REQUEST_CODE_SAVE_DOCUMENTS, intent, 0);

        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.putExtra(EXTRA_SAVE_DOCUMENT, document);
        intent.setAction(ACTION_SAVE_DOCUMENTS);
        activity.startService(intent);
    }

    public static void getDocuments(AppCompatActivity activity) {
        Intent intent = new Intent(activity, IntentServiceDataBase.class);
        PendingIntent pendingIntent = activity.createPendingResult(REQUEST_CODE_GET_DOCUMENTS, intent, 0);

        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.setAction(ACTION_GET_DOCUMENTS);
        activity.startService(intent);
    }

    public static void insertProductCell(AppCompatActivity activity, long idDocument, long idCell, long idProduct, long quantity) {
        Intent intent = new Intent(activity, IntentServiceDataBase.class);
        PendingIntent pendingIntent = activity.createPendingResult(REQUEST_CODE_SAVE_PRODUCT_CELL, intent, 0);

        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.putExtra(EXTRA_ID_DOCUMENT, idDocument);
        intent.putExtra(EXTRA_CELL, idCell);
        intent.putExtra(EXTRA_PRODUCT, idProduct);
        intent.putExtra(EXTRA_GUANTITY, quantity);

        intent.setAction(ACTION_SAVE_PRODUCT_CELL);
        activity.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            DataBaseHelper helper = new DataBaseHelper(this);
            PendingIntent pendingIntent = intent.getParcelableExtra(EXTRA_PENDING_INTENT);
            Intent result = new Intent();

            if (ACTION_SAVE_DOCUMENTS.equals(action)) {
                Document document = intent.getParcelableExtra(EXTRA_SAVE_DOCUMENT);
                long id = helper.insertDocument(document);
                result.putExtra(EXTRA_ID_DOCUMENT, id);

            } else if (ACTION_GET_DOCUMENTS.equals(action)) {
//                ArrayList<Document> documents = helper.getDocuments();
//                result.putExtra(EXTRA_GET_DOCUMENTS, documents);

            } else if (ACTION_SAVE_PRODUCT_CELL.equals(action)) {
                long idDocument = intent.getLongExtra(EXTRA_ID_DOCUMENT, -1);
                long idCell = intent.getLongExtra(EXTRA_CELL, -1);
                long idProduct = intent.getLongExtra(EXTRA_PRODUCT, -1);
                long quantity = intent.getLongExtra(EXTRA_GUANTITY, 1);

                long id = helper.insertProductCell(idDocument, idCell, idProduct, quantity);
                result.putExtra(EXTRA_GET_DOCUMENTS, id);
            }
            try {
                pendingIntent.send(this, Activity.RESULT_OK, result);
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
    }

}
