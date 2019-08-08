package myshop.com.myshop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private static Session instance;
    private SharedPreferences pref;

    private final String PREF_EMAIL = "EMAIL";
    private final String PREF_LOGGED = "IS_LOGGED";


    private Session() {
        super();
    }

    public static Session getInstance(){
        if (instance == null){
            instance = new Session();
        }
        return instance;
    }

    public void init(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveEmail(String email){
        pref.edit().putString(PREF_EMAIL, email).apply();
    }

    public String getEmail(){
        return pref.getString(PREF_EMAIL, "");
    }

    public void saveLogged(boolean logged){
        pref.edit().putBoolean(PREF_LOGGED, logged).apply();
    }

    public boolean isLogged(){
        return pref.getBoolean(PREF_LOGGED, false);
    }

    public void cerrarSesion(){
        pref.edit().clear().apply();
    }
}
