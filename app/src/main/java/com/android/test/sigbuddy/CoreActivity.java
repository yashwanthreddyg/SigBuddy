package com.android.test.sigbuddy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class CoreActivity extends AppCompatActivity
{

    Button startServiceBtn, stopServiceBtn;
    TextView sigText;

    TelephonyManager TelephonManager;
    MyPhoneStateListener pslistener;
    Switch onOffSwitch;
    int SignalStrength = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);
        /*Intent intent=new Intent(this, SmsReceiveActivity.class);
        intent.putExtra("data", "name 17.2122 78.2313");
        startActivity(intent);*/

        onOffSwitch = (Switch) findViewById(R.id.onOffSwitch);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    Intent startServiceIntent = new Intent(getBaseContext(), SigBuddyCoreService.class);
                    startService(startServiceIntent);
                } else
                {
                    Intent stopServiceIntent = new Intent(getBaseContext(), SigBuddyCoreService.class);
                    stopService(stopServiceIntent);
                }

            startActivity(new Intent(CoreActivity.this, ContactsActivity.class));
            }
        });

        try
        {
            pslistener = new MyPhoneStateListener();
            TelephonManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            TelephonManager.listen(pslistener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private class MyPhoneStateListener extends PhoneStateListener
    {
        @Override
        public void onSignalStrengthsChanged(android.telephony.SignalStrength signalStrength)
        {
            super.onSignalStrengthsChanged(signalStrength);
            SignalStrength = signalStrength.getGsmSignalStrength();
            SignalStrength = (2 * SignalStrength) - 113; // -> dBm
//            Log.d("SignalChange", "" + SignalStrength);
        }


    }
}
