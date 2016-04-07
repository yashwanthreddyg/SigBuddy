package com.android.test.sigbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    ListView contactsListView;
    TextView nameTV, phoneTV;
    Button addBtn, nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        contactsListView = (ListView) findViewById(R.id.contacts_list);
        final CustomAdapter ca = new CustomAdapter(this);
        contactsListView.setAdapter(ca);
        nameTV = (TextView) findViewById(R.id.nameET);
        phoneTV = (TextView) findViewById(R.id.phoneET);
        addBtn = (Button) findViewById(R.id.addBtn);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setEnabled(false);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateNum(phoneTV.getText().toString()) && validateStr(nameTV.getText().toString())) {
                    ca.addContact(new Contact(nameTV.getText().toString(), phoneTV.getText().toString()));
                    nextBtn.setEnabled(true);
                    nameTV.setText("");
                    phoneTV.setText("");
                }
                Toast.makeText(getBaseContext(), "Check input format", Toast.LENGTH_SHORT).show();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLocalStore userLocalStore = new UserLocalStore(getBaseContext());
                userLocalStore.setAsFirstTime(false);
                Intent i = new Intent(getBaseContext(),ConfigActivity.class);
                startActivity(i);
            }
        });
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
