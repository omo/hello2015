package es.flakiness.firstapp

import android.app.Activity
import android.app.Fragment
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.NavUtils
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
        // Seems like the default is already true but anyway...
        this.actionBar.displayHomeAsUpEnabled = true
        setContentView(R.layout.activity_display_message)
        (findViewById(R.id.display_message_container) as ViewGroup).addView(new TextView(this).with {
            textSize = 40
            def text = resources.getString(R.string.received_message_is) + this.intent.getStringExtra(MainActivity.EXTRA_MESSAGE)
            setText(text)
            it
        })
    }

    @Override
    boolean onOptionsItemSelected(MenuItem item) {
        switch(item.itemId) {
            // XXX: I'm not sure how this can be useful.
            // http://developer.android.com/training/implementing-navigation/ancestral.html
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this)
                return true
            case R.id.action_search:
                return true
            case R.id.action_settings:
                return true
            default:
                super.onOptionsItemSelected(item)

        }
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
