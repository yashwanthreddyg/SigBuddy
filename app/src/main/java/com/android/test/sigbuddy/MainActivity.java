package com.android.test.sigbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editTextSms=(EditText) findViewById(R.id.editText_sms);
        final EditText editTextPhone=(EditText)findViewById(R.id.editText_phone);
        Button buttonSend=(Button) findViewById(R.id.button_send);
        final SmsManager smsManager=SmsManager.getDefault();

        assert buttonSend != null;
        buttonSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                assert editTextPhone != null;
                assert editTextSms != null;
                smsManager.sendTextMessage(editTextPhone.getText().toString(), null, editTextSms.getText().toString(), null, null);
            }
        });

        startActivity(new Intent(MainActivity.this, ToggleActivity.class));
    }
}