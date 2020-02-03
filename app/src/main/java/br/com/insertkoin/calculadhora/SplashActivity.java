package br.com.insertkoin.calculadhora;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    private static final String SETTINGS = "Settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (!sharedPreferences.getBoolean("primeiroacesso", false)) {
                    startActivity(new Intent(getBaseContext(), WelcomeActivity.class));
                    editor.putBoolean("primeiroacesso", true);
                    editor.commit();
                }else {
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                }

                finish();
            }
        }, 1000);
    }
}
