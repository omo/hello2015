package flakiness.es.hello;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;


public class Hello extends Activity {

    public static final String CLASS_NAME = "HelloGreeting";
    public static final String GREETING_KEY = "greeting";

    void helloPut() {
        ParseObject obj = new ParseObject(CLASS_NAME);
        obj.put(GREETING_KEY, "Hello, World!");
        obj.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(Hello.this, "Put it", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void helloGetAndDelete() {
        ParseQuery<ParseObject> q = ParseQuery.getQuery(CLASS_NAME);
        q.whereExists(GREETING_KEY).getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (null == parseObject) {
                    Toast.makeText(Hello.this, "Failed:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(Hello.this, "Got it:" + parseObject.getString(GREETING_KEY), Toast.LENGTH_SHORT).show();
                parseObject.deleteEventually();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        Parse.initialize(this, ApplicationKeys.APPLICATION_ID, ApplicationKeys.CLIENT_KEY);

        ((Button)findViewById(R.id.get_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helloGetAndDelete();
            }
        });

        ((Button)findViewById(R.id.put_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helloPut();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hello, menu);
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
