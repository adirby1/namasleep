package com.example.adir.namasleep;

import android.app.Application;

import com.parse.Parse;

public class MyApplication extends Application {


    String appId = "4FffJgR8BDL6eSk1cWrpC3jBkaP1BAt4UGf2DODU";
    String clientId = "u5T1bW6kK3QMpApW298jKZca6qPVW48ijJOuo5at";

    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, appId, clientId);
    }
}
