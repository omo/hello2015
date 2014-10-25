package es.flakiness.hellocupboard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by omo on 10/25/14.
 */
public class HelloDatabaseOpenHelper extends SQLiteOpenHelper {

    static {
        cupboard().register(Greet.class);
    }

    private static final String DATABASE_NAME = "hello.db";
    private static final int DATABASE_VERSION = 1;

    public HelloDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        cupboard().withDatabase(sqLiteDatabase).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        cupboard().withDatabase(sqLiteDatabase).upgradeTables();
    }
}
