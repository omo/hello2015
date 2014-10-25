package es.flakiness.firstapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

@groovy.transform.CompileStatic
class StartCounter implements Closeable {
    Activity mContext
    int mCount
    SharedPreferences mPrefs

    StartCounter(Activity context) {
        mContext = context
        mPrefs = mContext.getPreferences(Context.MODE_PRIVATE)
        mCount = mPrefs.getInt("numCreated", 0)
    }

    @Override
    void close() throws IOException {
        mPrefs.edit().with {
            putInt("numCreated", mCount + 1)
            commit()
        }
    }

    def show() {
        Toast.makeText(mContext, "This app is created ${mCount} times", 1000).show()
    }
}

@groovy.transform.CompileStatic
public class MainActivity extends Activity {
    public static final String EXTRA_MESSAGE = MainActivity.package.name + ".MESSAGE";

    private StartCounter mStartCounter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)

        Log.d(MainActivity.simpleName, savedInstanceState?.getString("myKey") ?: "No State")

        setContentView(R.layout.activity_main)

        (findViewById(R.id.send_button) as Button).setOnClickListener({
            def intent = new Intent(this, DisplayMessageActivity);
            def message = (findViewById(R.id.edit_message) as EditText).text.toString()
            intent.putExtra(EXTRA_MESSAGE, message)
            startActivity(intent)
        })

        (findViewById(R.id.action_bar_button) as Button).setOnClickListener({
            // XXX: This causes error. Should report.
            // http://groovy.329449.n5.nabble.com/java-lang-NoClassDefFoundError-void-with-ternary-td5721372.html
            //self.actionBar.showing ? self.actionBar.hide() : self.actionBar.show()
            if (actionBar.showing)
               actionBar.hide()
            else
               actionBar.show()
        })

        (findViewById(R.id.database_button) as Button).onClickListener = {
            startActivity(new Intent(this, DatabaseActivity))
        }

        mStartCounter = new StartCounter(this)
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuInflater.inflate(R.menu.main_activity_actions, menu)
        super.onCreateOptionsMenu(menu)
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        def id = item.itemId
        if (id == R.id.action_settings) {
            return true
        }

        super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("myKey", "Hello?")
        super.onSaveInstanceState(bundle)
    }

    @Override
    public void onStop() {
        super.onStop()
        Log.d(MainActivity.simpleName, "onStop")
    }

    @Override
    public void onStart() {
        super.onStart()
        Log.d(MainActivity.simpleName, "onStart")
        mStartCounter.show()
    }

    @Override
    public void onDestroy() {
        super.onDestroy()
        Log.d(MainActivity.simpleName, "onDestroy")
        mStartCounter.close()
    }
}
