package flakiness.es.hello

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

@groovy.transform.CompileStatic
public class HelloActivity extends Activity {

    @Override
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)
    }

    @Override
    boolean onCreateOptionsMenu(Menu menu) {
        menuInflater.inflate(R.menu.hello, menu)
        true
    }

    @Override
    boolean onOptionsItemSelected(MenuItem item) {
        if (item.itemId== R.id.action_settings) {
            return true;
        }

        super.onOptionsItemSelected(item)
    }
}
