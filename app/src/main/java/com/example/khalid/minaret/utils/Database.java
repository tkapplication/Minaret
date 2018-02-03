package com.example.khalid.minaret.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.khalid.minaret.models.CalenderModel;
import com.example.khalid.minaret.models.Message;
import com.example.khalid.minaret.models.Post;

import java.util.ArrayList;

/**
 * Created by khalid on 1/16/2018.
 */

public class Database extends SQLiteOpenHelper {
    public static String DataBase_Name = "woovendor";

    String favorite = "favorite";
    String messages = "messages";
    Context context;
    String test = "test";
    String calender = "calender";
    String news = "news";

    public Database(Context context) {
        super(context, DataBase_Name, null, 4);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql1 = "CREATE TABLE " + messages + " (title TEXT,message TEXT)";
        String sql2 = "CREATE TABLE " + favorite + " (id TEXT PRIMARY KEY,title TEXT,content TEXT,date TEXT,image TEXT,comment TEXT,comment_count TEXT,favorite_count TEXT)";

        String sql3 = "CREATE TABLE " + test + " (title TEXT,message TEXT)";
        String sql4 = "CREATE TABLE " + calender + " (id TEXT PRIMARY KEY,title TEXT,description TEXT,start_date TEXT,end_date TEXT,address TEXT)";
        sqLiteDatabase.execSQL(sql4);
        String sql5 = "CREATE TABLE " + news + " (id TEXT PRIMARY KEY,title TEXT,content TEXT,date TEXT,image TEXT,comment TEXT,comment_count TEXT,favorite_count TEXT)";
        sqLiteDatabase.execSQL(sql5);

        sqLiteDatabase.execSQL(sql3);
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sql1 = "CREATE TABLE IF NOT EXISTS " + calender + " (id TEXT PRIMARY KEY,title TEXT,description TEXT,start_date TEXT,end_date TEXT)";
        sqLiteDatabase.execSQL(sql1);
        String sql2 = "CREATE TABLE IF NOT EXISTS " + news + " (id TEXT PRIMARY KEY,title TEXT,content TEXT,date TEXT,image TEXT,comment TEXT,comment_count TEXT,favorite_count TEXT)";
        sqLiteDatabase.execSQL(sql2);

    }

    public void addMessage(Message message) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", message.getTitle());
        values.put("message", message.getMessage());


        database.insert(messages, null, values);
    }

    public void addCalender(CalenderModel calenderModel) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", calenderModel.getId());
        values.put("title", calenderModel.getTitle());
        values.put("description", calenderModel.getDescription());
        values.put("start_date", calenderModel.getStart_date());
        values.put("end_date", calenderModel.getEnd_date());
        values.put("address", calenderModel.getAddress());

        database.insert(calender, null, values);
    }

    public void addNews(Post post) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", post.getId());
        values.put("title", post.getTitle());
        values.put("content", post.getContent());
        values.put("date", post.getDate());
        values.put("image", post.getImage());
        values.put("comment", post.getComment());
        values.put("comment_count", post.getComment_count());
        values.put("favorite_count", post.getFavorite_count());


        database.insert(news, null, values);

    }


    public void addFavorite(Post post) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", post.getId());
        values.put("title", post.getTitle());
        values.put("content", post.getContent());
        values.put("date", post.getDate());
        values.put("image", post.getImage());
        values.put("comment", post.getComment());
        values.put("comment_count", post.getComment_count());
        values.put("favorite_count", post.getFavorite_count());


        database.insert(favorite, null, values);
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

    public ArrayList<CalenderModel> getCalender() {
        ArrayList<CalenderModel> onsaleproduct = new ArrayList<>();


        String selectQuery = "SELECT  * FROM " + calender;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                CalenderModel product = new CalenderModel();
                product.setId(cursor.getString(0));
                product.setTitle(cursor.getString(1));
                product.setDescription(cursor.getString(2));
                product.setStart_date(cursor.getString(3));
                product.setEnd_date(cursor.getString(4));
                product.setAddress(cursor.getString(5));

                onsaleproduct.add(product);
            }
            while (cursor.moveToNext());
        }
        return onsaleproduct;
    }

    public ArrayList<Post> getNews() {
        ArrayList<Post> onsaleproduct = new ArrayList<>();


        String selectQuery = "SELECT  * FROM " + news;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Post product = new Post();
                product.setId(cursor.getString(0));
                product.setTitle(cursor.getString(1));
                product.setContent(cursor.getString(2));
                product.setDate(cursor.getString(3));
                product.setImage(cursor.getString(4));
                product.setComment(cursor.getString(5));
                product.setComment_count(cursor.getString(6));
                product.setFavorite_count(cursor.getString(7));

                onsaleproduct.add(product);
            }
            while (cursor.moveToNext());
        }
        return onsaleproduct;
    }

    public ArrayList<Post> getFavorite() {
        ArrayList<Post> onsaleproduct = new ArrayList<>();


        String selectQuery = "SELECT  * FROM " + favorite;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Post product = new Post("", "", "", "", "", "", "", "");
                product.setId(cursor.getString(0));
                product.setTitle(cursor.getString(1));
                product.setContent(cursor.getString(2));
                product.setDate(cursor.getString(3));
                product.setImage(cursor.getString(4));
                product.setComment(cursor.getString(5));
                product.setComment_count(cursor.getString(6));
                product.setFavorite_count(cursor.getString(7));


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

    public void deleteFavorite(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(favorite, "id=?", new String[]{id});
        database.close();

    }

    public void deleteCalender(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(calender, "id=?", new String[]{id});
        database.close();

    }

    public boolean checkFavorite(String id) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor mCursor = database.rawQuery("SELECT * FROM " + favorite + " WHERE id=?"
                , new String[]{id});
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                if (mCursor.moveToFirst()) {

                    if (!mCursor.getString(3).equals(""))

                        return true;
                }
            }
        }
        return false;
    }
}