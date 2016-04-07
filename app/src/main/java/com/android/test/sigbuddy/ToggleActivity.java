package com.android.test.sigbuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class ToggleActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle);

        Switch toggle = (Switch) findViewById(R.id.switch_service_status);
        assert toggle != null;
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                Toast.makeText(ToggleActivity.this, "service"+(isChecked?" started":" stopped"), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
