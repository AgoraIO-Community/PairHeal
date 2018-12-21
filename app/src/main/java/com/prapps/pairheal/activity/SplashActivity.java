package com.prapps.pairheal.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.prapps.pairheal.R;
import com.prapps.pairheal.utils.AppConstants;
import com.prapps.pairheal.utils.PreferenceManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        final boolean isLoggedIn = PreferenceManager.getInstance(this).getBoolean(AppConstants.KEY_LOGGED_IN);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, isLoggedIn ? HomeActivity.class : LoginActivity.class));
            SplashActivity.this.finish();
        }, 500);
    }
}
