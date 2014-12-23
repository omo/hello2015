package es.flakiness.hellocupboard;

import android.database.sqlite.SQLiteDatabase;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by omo on 10/25/14.
 */
@Module(
        injects = { MainActivity.class }
)
public class HelloModule {

    private final HelloDatabaseOpenHelper mDatabaseHelper;
    private final App mApp;

    public HelloModule(App app) {
        mApp = app;
        mDatabaseHelper = new HelloDatabaseOpenHelper(app);
    }

    @Provides @Singleton
    public SQLiteDatabase provideDatabase() {
        return mDatabaseHelper.getWritableDatabase();
    }

    @Provides @Singleton
    public Bus provideBus() {

        return new Bus(ThreadEnforcer.MAIN);
    }
}
