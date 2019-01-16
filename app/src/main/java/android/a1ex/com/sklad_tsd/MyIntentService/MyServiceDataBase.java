package android.a1ex.com.sklad_tsd.MyIntentService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyServiceDataBase extends Service {
    public MyServiceDataBase() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
