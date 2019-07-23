package myshop.com.myshop.utils;

import android.app.Application;
import android.content.Context;


import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyShopApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
    }

    public static void init(Context context) {
        Realm.init(context);
    }

    public static synchronized Realm getRealmInstance() {
        return Realm.getInstance(
                new RealmConfiguration.Builder()
                        .name("myshop.module.realm")
                        .modules(new MyShopModule())
                        .deleteRealmIfMigrationNeeded()
                        .build());
    }
}
