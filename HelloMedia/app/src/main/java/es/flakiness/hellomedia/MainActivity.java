package es.flakiness.hellomedia;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends Activity {

    private MediaPlayer player;

    @InjectView(R.id.url_text)
    TextView urlText;

    @InjectView(R.id.title_text)
    TextView titleText;

    @OnClick(R.id.button_action_get_content)
    public void onClickActionGetContentButton() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("audio/*");
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.button_play)
    public void onClickPlay() {
        if (null == urlText.getText() || 0 == urlText.getText().length()) {
            Toast.makeText(this, "Nothing to play", Toast.LENGTH_SHORT).show();
        }

        Uri uri = Uri.parse(urlText.getText().toString());
        playNew(uri);
    }

    @OnClick(R.id.button_stop)
    public void onClickStop() {
        if (null != player && player.isPlaying())
            player.stop();
    }

    void playNew(Uri uri) {
        if (player != null) {
            player.stop();
            player.release();
        }

        player = new MediaPlayer();

        try {
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(getApplicationContext(), uri);
            player.prepare();
            player.start();
            Toast.makeText(this, "Started...", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            player.release();
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Toast.makeText(this, "Got Result:" + uri.toString(), Toast.LENGTH_SHORT).show();
            urlText.setText(uri.toString());

            // http://developer.android.com/reference/android/media/MediaMetadataRetriever.html#getFrameAtTime(long)
            // http://stackoverflow.com/questions/6306636/read-id3-tags-of-an-mp3-file
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(this, uri);
            String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            titleText.setText(title);
            mmr.release();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
