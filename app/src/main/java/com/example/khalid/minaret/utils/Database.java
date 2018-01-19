package com.example.khalid.minaret.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.khalid.minaret.models.Message;

import java.util.ArrayList;

/**
 * Created by khalid on 1/16/2018.
 */

public class Database extends SQLiteOpenHelper {
    public static String DataBase_Name = "woovendor";


    String messages = "messages";
    Context context;

    public Database(Context context) {
        super(context, DataBase_Name, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql1 = "CREATE TABLE " + messages + " (title TEXT,message TEXT)";


        sqLiteDatabase.execSQL(sql1);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addMessage(Message message) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", message.getTitle());
        values.put("message", message.getMessage());


        database.insert(messages, null, values);
    }

    public ArrayList<Message> getMessages() {
        ArrayList<Message> onsaleproduct = new ArrayList<>();


        String selectQuery = "SELECT  * FROM " + messages;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Message product = new Message("", "");
                product.setTitle(cursor.getString(0));
                product.setMessage(cursor.getString(1));


                onsaleproduct.add(product);
            }
            while (cursor.moveToNext());
        }
        return onsaleproduct;
    }
    public void deleteMessage(String title) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(messages, "title=?", new String[]{title});
        database.close();

    }

}