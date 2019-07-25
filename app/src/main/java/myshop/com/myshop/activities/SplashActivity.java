package myshop.com.myshop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import myshop.com.myshop.R;
import myshop.com.myshop.utils.MyShopApp;
import myshop.com.myshop.utils.Session;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MyShopApp.init(getApplicationContext());

        if (Session.getInstance().isLogged()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
