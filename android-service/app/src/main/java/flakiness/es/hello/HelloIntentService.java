package flakiness.es.hello;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class HelloIntentService extends IntentService {

    static public String TAG = HelloIntentService.class.getName();

    public HelloIntentService() {
        super("HelloIntentService");
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(10*1000, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Hello!: " + intent.getAction());
    }
}
