package es.flakiness.hellocupboard;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import dagger.ObjectGraph;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by omo on 10/25/14.
 */
public class App extends Application {
    private ObjectGraph mGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        mGraph = ObjectGraph.create(new HelloModule(this));
    }

    static public App get(Activity activity) {
        return (App)activity.getApplication();
    }

    static public void inject(MainActivity activity) {
        get(activity).mGraph.inject(activity);
    }
}
