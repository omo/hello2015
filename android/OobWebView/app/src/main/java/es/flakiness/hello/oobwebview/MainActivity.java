package es.flakiness.hello.oobwebview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends Activity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (VERSION_CODES.LOLLIPOP <= VERSION.SDK_INT) {
            Log.d(TAG, "WebView.enableSlowWholeDocumentDraw()");
            WebView.enableSlowWholeDocumentDraw();
        }

        setContentView(R.layout.activity_main);

        final MyWebView web = (MyWebView)findViewById(R.id.web);
        web.loadUrl("http://mobile.nytimes.com/");
        final Button button = (Button)findViewById(R.id.capture_button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                capture(web);
            }
        });
    }

    private void capture(MyWebView target) {
        int w = target.getWidth();
        int h = target.getContentHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        target.capture(canvas);
        Log.d(TAG, String.format("Captured w: %d h: %d", w, h));
        File file = saveToCache(bitmap);
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Save the image"));
    }

    // http://stackoverflow.com/questions/3425906/creating-temporary-files-in-android
    // http://developer.android.com/reference/android/content/Context.html#getExternalCacheDir()
    public File saveToCache(Bitmap bitmap) {
        try {
            File file = File.createTempFile("capture", ".png", getExternalCacheDir());
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(CompressFormat.PNG, 100, out);
            out.close();
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
