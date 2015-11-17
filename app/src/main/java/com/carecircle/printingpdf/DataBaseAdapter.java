package com.carecircle.printingpdf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Gowtham Chandrasekar on 14-11-2015.
 */

public class DataBaseAdapter {
    DataBaseHelper dataBaseHelper;

    DataBaseAdapter(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public long insertData(String name,String phoneNumber)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.KEY_NAME, name); // Contact Name
        values.put(DataBaseHelper.KEY_PH_NO, phoneNumber); // Contact Phone Number

        // Inserting Row
        long id=db.insert(DataBaseHelper.TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
        return id;

    }

    public String getData(){
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();

        String columns[]={DataBaseHelper.KEY_ID,DataBaseHelper.KEY_NAME,DataBaseHelper.KEY_PH_NO};
        Cursor cursor=db.query(DataBaseHelper.TABLE_CONTACTS, columns, null, null, null, null, null);
        StringBuffer buffer=new StringBuffer();
        while (cursor.moveToNext()){
            int no=cursor.getInt(0);
            String name=cursor.getString(1);
            String phone=cursor.getString(2);
            buffer.append(no+ " "+name+" "+phone+"\n");
        }
        return buffer.toString();
    }

    public class DataBaseHelper extends SQLiteOpenHelper {
        // All Static variables
        // Database Version
        private static final int DATABASE_VERSION = 3;

        // Contacts table name
        private static final String TABLE_CONTACTS = "contacts";

        // Database Name
        private static final String DATABASE_NAME = "pdfdata";


        // Contacts Table Columns names
        private static final String KEY_ID = "_id";
        private static final String KEY_NAME = "name";
        private static final String KEY_PH_NO = "phone_number";

        Context context;

        public DataBaseHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Log.d("CalledMethod", "Constructor");
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("CalledMethod", "onCreate");
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " VARCHAR(255),"
                    + KEY_PH_NO + " VARCHAR(255)" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            Log.d("CalledMethod", "onUpgrade");
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

            // Create tables again
            onCreate(db);
        }
    }
}
