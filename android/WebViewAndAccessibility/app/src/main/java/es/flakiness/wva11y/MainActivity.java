package es.flakiness.wva11y;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.TextView;

public class MainActivity extends Activity {

    public class JSBridge {
        @JavascriptInterface
        public void notifyLinkIsClicked(final String url) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    linkIsClicked(url);
                }
            });
        }
    }

    public void linkIsClicked(String url) {
        TextView text = (TextView) findViewById(R.id.target_text);
        text.setText(url);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView target = (WebView) findViewById(R.id.target_webview);
        target.getSettings().setJavaScriptEnabled(true);
        target.addJavascriptInterface(new JSBridge(), "bridge");
        target.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
