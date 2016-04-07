package com.android.test.sigbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;

public class SendSmsActivity extends AppCompatActivity
{
    private SmsManager smsManager=SmsManager.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        Intent intent=getIntent();
        String[] location=intent.getStringExtra("data").split(" ");
        smsManager.sendTextMessage("NUMBER", null, "NAME"+location[0]+" "+location[1], null, null);
    }
}
