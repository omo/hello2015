package es.flakiness.firstapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText

@groovy.transform.CompileStatic
public class MainActivity extends Activity {
    public static final String EXTRA_MESSAGE = MainActivity.package.name + ".MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (findViewById(R.id.send_button) as Button).setOnClickListener({
            def intent = new Intent(this, DisplayMessageActivity);
            def message = (findViewById(R.id.edit_message) as EditText).text.toString()
            intent.putExtra(EXTRA_MESSAGE, message)
            startActivity(intent)
        })
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
}
