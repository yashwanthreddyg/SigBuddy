package com.android.test.sigbuddy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KH9151 on 19-03-2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    int version = 1;

    public DBHelper(Context context) {
        super(context, "CONTACTS_DB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createContactsTable = "CREATE TABLE CONTACTS(" +
                "NAME TEXT NOT NULL," +
                "PHONE TEXT NOT NULL);";

        db.execSQL(createContactsTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
