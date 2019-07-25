package myshop.com.myshop.utils;

import android.app.Application;
import android.content.Context;


public class MyShopApp extends Application {

    public static Session session;

    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
    }

    public static void init(Context context) {
        session = Session.getInstance();
        session.init(context);
    }
}
