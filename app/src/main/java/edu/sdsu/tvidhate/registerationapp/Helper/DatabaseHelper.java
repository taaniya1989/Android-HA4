package edu.sdsu.tvidhate.registerationapp.Helper;

/**
 * Created by tanvi on 3/25/18.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "RegistrationApp.db";
    public static final String TABLE_NAME = "student_details_table";
    public static final String RED_ID = "red_id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL_ID = "email_id";
    public static final String PASSWORD = "password";


    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists " +TABLE_NAME+"("+RED_ID+" TEXT," +
                ""+FIRST_NAME+" TEXT," +
                ""+LAST_NAME+" TEXT," +
                ""+EMAIL_ID+" TEXT," +
                ""+PASSWORD+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String redID,String firstName,String lastName,String emailId,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RED_ID,redID);
        contentValues.put(FIRST_NAME,firstName);
        contentValues.put(LAST_NAME,lastName);
        contentValues.put(EMAIL_ID,emailId);
        contentValues.put(PASSWORD,password);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor authenticateUser(String emailID, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+EMAIL_ID+"='"+emailID+"'",null);
        return res;
    }
}
