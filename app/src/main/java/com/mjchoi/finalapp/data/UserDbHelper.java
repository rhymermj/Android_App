package com.mjchoi.finalapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDbHelper extends SQLiteOpenHelper {
    public UserDbHelper(Context context) {
        super(context, "user", null, 1);
    }


    @Override
    // Create database; creates a table
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(email text primary key, password text)");
    }


    @Override
    // Upgrades the database
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the table if already exists
        db.execSQL("DROP TABLE IF EXISTS user");
    }

    // Insert user and return boolean result
    public boolean addUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);

        // Insert row
        long row = db.insert("user", null, values);
        if (row == -1){
            return false;
        }
        else{
            return true;
        }
    }

    // Check if the user exists or not and return boolean result
    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from user where email=?", new String[]{email});
        if (cursor.getCount() > 0){
            return false;
        }
        else{
            return true;
        }
    }

    // Check user email and password and return boolean result
    public boolean checkCredentials(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=? and password=?", new String[] {email, password});
        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }
}
