package com.example.ddetector.dronedetector;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ZHI on 5/10/2018.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "people_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "name";



    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null,1);
    }

    //Create a table
    @Override
    public void onCreate(SQLiteDatabase db ) {
         String createTable = "CREATE TABLE" + TABLE_NAME + "(ID INTERGER PRIMARY KEY AUTOINCREMENT," + COL2 +"TEXT)";
         db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
   long result = db.insert(TABLE_NAME, null, contentValues);

   if (result == -1) {
       return false;
   } else {
       return true;
   }
    }
}