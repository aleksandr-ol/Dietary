package com.example.immortal.dietary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseConnector {
    private static final String DATABASE_NAME = "remainds_db";
    private static final String TABLE_NAME = "remainds";
    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;

    public DatabaseConnector(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
    }

    public void open() throws SQLException {
        database = databaseOpenHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null)
            database.close();
    }

    public void insertRemaind(String title, String time, int is_checked) {
        ContentValues newContact = new ContentValues();
        newContact.put("title", title);
        newContact.put("time", time);
        newContact.put("is_checked", is_checked);

        open();
        database.insert(TABLE_NAME, null, newContact);
        close();
    }

    public void updateRemaind(long id, String title, String time, int is_checked) {
        ContentValues editContact = new ContentValues();
        editContact.put("title", title);
        editContact.put("time", time);
        editContact.put("is_checked", is_checked);

        open();
        database.update(TABLE_NAME, editContact, "_id=" + id, null);
        close();
    }

    public Cursor getAllRemainds() {
        database = databaseOpenHelper.getWritableDatabase();
        return database.rawQuery("select * from "+TABLE_NAME , null);
    }

    public Cursor getOneRemaind(long id) {
        return database.query(TABLE_NAME, null, "_id=" + id, null, null, null, null);
    }

    public void deleteRemaind(long id) {
        open();
        database.delete(TABLE_NAME, "_id=" + id, null);
        close();
    }

    private void fill_table(SQLiteDatabase db){
        ContentValues contact1 = new ContentValues();
        contact1.put("title", "Сніданок");
        contact1.put("time", "08:00");
        contact1.put("is_checked", 0);

        ContentValues contact2 = new ContentValues();
        contact2.put("title", "Обід");
        contact2.put("time", "12:00");
        contact2.put("is_checked", 0);

        ContentValues contact3 = new ContentValues();
        contact3.put("title", "Полудник");
        contact3.put("time", "16:00");
        contact3.put("is_checked", 0);

        ContentValues contact4 = new ContentValues();
        contact4.put("title", "Вечеря");
        contact4.put("time", "20:00");
        contact4.put("is_checked", 1);

        db.insert(TABLE_NAME, null, contact1);
        db.insert(TABLE_NAME, null, contact2);
        db.insert(TABLE_NAME, null, contact3);
        db.insert(TABLE_NAME, null, contact4);
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper{
        public DatabaseOpenHelper(Context context, String name,
                                  SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createquery = "CREATE TABLE " + TABLE_NAME + " (_id integer primary key autoincrement, " +
                    "title TEXT, time TEXT, is_checked INTEGER);";
            db.execSQL(createquery);
            fill_table(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
