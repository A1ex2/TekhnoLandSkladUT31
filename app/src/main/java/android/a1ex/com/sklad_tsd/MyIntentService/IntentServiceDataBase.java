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

    public static final int REQUEST_CODE_GET_DOCUMENTS = 11;
    public static final int REQUEST_CODE_SAVE_DOCUMENTS = 12;


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

            }
            try {
                pendingIntent.send(this, Activity.RESULT_OK, result);
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
    }

}
