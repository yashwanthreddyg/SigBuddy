package com.android.test.sigbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LauncherActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        UserLocalStore userLocalStore = new UserLocalStore(this);

        if(userLocalStore.isFirstTime()) {
            userLocalStore.setAsFirstTime(false);
            Intent coreIntent = new Intent(LauncherActivity.this, IntroActivity.class);
            startActivity(coreIntent);
        }
        else {
            Intent configIntent = new Intent(LauncherActivity.this, ConfigActivity.class);
            startActivity(configIntent);
        }
//        startActivity(new Intent(this, CoreActivity.class));
    }
}
