package com.shivamdev.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shivamchopra on 07/06/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notesdb";
    private static final String TABLE_NAME = "notes";

    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESC = "desc";

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT,"
            + KEY_DESC + " TEXT)";

    private static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String GET_NOTES_QUERY = "SELECT * FROM " + TABLE_NAME;

    private static DbHelper instance;

    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_QUERY);
        onCreate(db);
    }

    public void addNote(NoteData note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, note.title);
        cv.put(KEY_DESC, note.desc);
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public List<NoteData> getNotes() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_NOTES_QUERY, null);
        List<NoteData> notesList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                NoteData note = new NoteData();
                note.id = cursor.getInt(0);
                note.title = cursor.getString(1);
                note.desc = cursor.getString(2);
                notesList.add(note);
            } while (cursor.moveToNext());
        }
        db.close();
        return notesList;
    }

    public void editNote(NoteData note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.title);
        values.put(KEY_DESC, note.desc);
        db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[]{String.valueOf(note.id)});
        db.close();
    }

    public void deleteNote(NoteData note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(note.id)});
        db.close();
    }
}