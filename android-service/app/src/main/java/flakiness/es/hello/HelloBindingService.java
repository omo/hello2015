package flakiness.es.hello;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class HelloBindingService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private IHelloBindingService.Stub mBinder = new IHelloBindingService.Stub() {
        @Override
        public void greet(String message) throws RemoteException {
            Log.d(getClass().getName(), "Got a greet: " + message);
        }
    };
}
