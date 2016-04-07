package com.android.test.sigbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by KH9151 on 19-03-2016.
 */
public class CustomAdapter extends BaseAdapter {
    private Context context;
    DBHelper dbHelper;
    ArrayList<Contact> arr;
    LayoutInflater layoutInflater;
    CustomAdapter ca;
    public CustomAdapter(Context context){
        this.context = context;
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        Cursor c = db.rawQuery("SELECT NAME,PHONE FROM CONTACTS",null);
        arr = new ArrayList<>();
        if (c.getCount() > 0)
        {
            c.moveToFirst();
            do {
                arr.add(new Contact(c.getString(c.getColumnIndex("NAME")),c.getString(c.getColumnIndex("PHONE"))));
            } while (c.moveToNext());
            c.close();
        }
        ca = this;
    }
    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {

        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rootView = layoutInflater.inflate(R.layout.contacts_list_item,null);
        TextView nameTV,phoneTV;
        Button delBtn;
        nameTV = (TextView)rootView.findViewById(R.id.contact_name);
        phoneTV = (TextView)rootView.findViewById(R.id.contact_number);
        delBtn = (Button)rootView.findViewById(R.id.deleteBtn);
        nameTV.setText(arr.get(position).name);
        phoneTV.setText(arr.get(position).phone);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("CONTACTS", "NAME = ?", new String[]{arr.get(position).name});
                arr.remove(position);
                ca.notifyDataSetChanged();
            }
        });
        return rootView;
    }
    public boolean addContact(Contact c){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NAME",c.name);
        cv.put("PHONE",c.phone);
        db.insert("CONTACTS",null,cv);
        arr.add(c);
        ca.notifyDataSetChanged();
        return true;
    }
}
