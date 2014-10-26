package es.flakiness.hellocupboard;

import android.database.sqlite.SQLiteDatabase;

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
    SQLiteDatabase provideDatabase() {
        return mDatabaseHelper.getWritableDatabase();
    }
}
