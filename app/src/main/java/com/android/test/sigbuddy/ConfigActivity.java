package com.android.test.sigbuddy;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ConfigActivity extends AppCompatActivity
{
    ListView contactsListView;
    TextView nameTV, phoneTV;
    Button addBtn;
    Switch aSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        contactsListView = (ListView) findViewById(R.id.contacts_list);
        final CustomAdapter ca = new CustomAdapter(this);
        contactsListView.setAdapter(ca);
        nameTV = (TextView) findViewById(R.id.nameET);
        phoneTV = (TextView) findViewById(R.id.phoneET);
        addBtn = (Button)findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateNum(phoneTV.getText().toString()) && validateStr(nameTV.getText().toString())) {
                    ca.addContact(new Contact(nameTV.getText().toString(), phoneTV.getText().toString()));
                    nameTV.setText("");
                    phoneTV.setText("");
                }
                Toast.makeText(getBaseContext(), "Check input format", Toast.LENGTH_SHORT).show();
            }
        });
        aSwitch = (Switch)findViewById(R.id.switch1);

        if(isMyServiceRunning(SigBuddyCoreService.class))
        {
            aSwitch.setChecked(true);
        }
        else
            aSwitch.setChecked(false);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Intent startSigBuddyService = new Intent(getBaseContext(),SigBuddyCoreService.class);
                    startService(startSigBuddyService);
                }
                else
                {
                    Intent stopSigBuddyService = new Intent(getBaseContext(),SigBuddyCoreService.class);
                    stopService(stopSigBuddyService);
                }
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    private boolean validateStr(String str) {
        if (str == null || str.equals(""))
            return false;
        else
            return true;
    }

    private boolean validateNum(String str) {
        if (str == null || str.equals(""))
            return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
