package com.android.test.sigbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IntroActivity extends AppCompatActivity {

    EditText uname;
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        final UserLocalStore userLocalStore = new UserLocalStore(this);
        uname = (EditText) findViewById(R.id.uname);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uname.equals("")) {
                    Toast.makeText(getBaseContext(), "username cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    userLocalStore.setUsername(uname.getText().toString());
                    Intent i = new Intent(getBaseContext(),ContactsActivity.class);
                    startActivity(i);

                }
            }
        });
    }
}
