package es.flakiness.firstapp;

import android.app.Activity
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem
import android.widget.Toast;


@groovy.transform.CompileStatic
class MyDBHelper extends SQLiteOpenHelper {

    public MyDBHelper(Context context) {
        super(context, "MyDB", null, 1)
    }

    @Override
    void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("""\
CREATE TABLE hello(id INTEGER PRIMARY KEY, name TEXT, body TEXT);
""")
    }

    @Override
    void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("""\
DROP TABLE IF EXISTS hello;
""")
    }
}

@groovy.transform.CompileStatic
public class DatabaseActivity extends Activity {

    private MyDBHelper mDBHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        mDBHelper = new MyDBHelper(this)
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.database, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.itemId) {
            case R.id.action_create_database:
                createDatabase()
                break
        }

        return super.onOptionsItemSelected(item);
    }

    def createDatabase() {
        def start = System.currentTimeMillis()
        def writableDB = mDBHelper.writableDatabase
        def end = System.currentTimeMillis()
        Toast.makeText(this, "Create Database took ${end - start}ms", 1000).show()
    }
}
