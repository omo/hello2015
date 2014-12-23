package es.flakiness.hellopicasso;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class MainActivity extends Activity {

    static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView target = (ImageView)findViewById(R.id.image_view);
        final Picasso p = Picasso.with(this);
        p.setLoggingEnabled(true);
        p.load("https://farm8.staticflickr.com/7301/9935571905_674272afd3_b.jpg").into(target,
                new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Success");
                    }

                    @Override
                    public void onError() {
                        Log.d(TAG, "Error");
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
