package es.flakiness.hellocupboard;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by omo on 10/25/14.
 */
public class App extends Application {

    private HelloDatabaseOpenHelper mDatabaseHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        mDatabaseHelper = new HelloDatabaseOpenHelper(this);
    }

    public void addNew() {
        Greet g = new Greet();
        g.at = new Date();
        g.message = "Hello?";
        cupboard().withDatabase(getDatabase()).put(g);
    }

    private SQLiteDatabase getDatabase() {
        return mDatabaseHelper.getWritableDatabase();
    }

    public java.util.List<Greet> getList() {
        return cupboard().withDatabase(getDatabase()).query(Greet.class).list();
    }

    static public App get(Activity activity) {
        return (App)activity.getApplication();
    }

}
