package com.prapps.pairheal.utils;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;
import com.prapps.pairheal.R;

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .enableLocalDataStore()
                .build()
        );
        ParseInstallation.getCurrentInstallation().saveInBackground(e -> {
            Log.i("ROYCE","save callback");
            if(e == null)
                Log.i("ROYCE","save saved");
            else
                Log.i("ROYCE",e.getLocalizedMessage());

        });
    }
}
