package es.flakiness.hellocupboard;

import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import javax.inject.Inject;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by omo on 10/25/14.
 */
public class Greeter {
    @Inject SQLiteDatabase mDatabase;

    public void addNew() {
        Greet g = new Greet();
        g.at = new Date();
        g.message = "Hello?";
        cupboard().withDatabase(mDatabase).put(g);
    }

    public java.util.List<Greet> getList() {
        return cupboard().withDatabase(mDatabase).query(Greet.class).list();
    }
}
