package es.flakiness.hellorealm;

import io.realm.RealmObject;

public class Greet extends RealmObject {
    private String message;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
