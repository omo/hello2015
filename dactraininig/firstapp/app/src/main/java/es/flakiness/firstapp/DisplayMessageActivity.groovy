package es.flakiness.firstapp

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

@groovy.transform.CompileStatic
public class DisplayMessageActivity extends Activity {

    @Override
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(new TextView(this).with {
            textSize = 40
            setText(this.intent.getStringExtra(MainActivity.EXTRA_MESSAGE))
            it
        })
    }

    @Override
    boolean onOptionsItemSelected(MenuItem item) {
        def id = item.itemId
        if (id == R.id.action_settings) {
            return true
        }

        super.onOptionsItemSelected(item)
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_display_message, container, false)
            return rootView;
        }
    }
}
