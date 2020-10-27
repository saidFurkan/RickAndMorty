package com.example.rickandmorty;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME   = "RickAndMortyDB";
    // Contacts table name
    private static final String TABLE_CHARACTERS = "characters";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_CHARACTERS + "(id INTEGER PRIMARY KEY,name TEXT,status TEXT,lastKnownLoc TEXT,imageLoc INTEGER" + ")";
        Log.d("DBHelper", "SQL : " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHARACTERS);
        onCreate(db);
    }

    public void insertCharacter(Character character) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id",character.getID());
        values.put("name", character.getName());
        values.put("status", character.getStatus());
        values.put("lastKnownLoc", character.getLastKnownLoc());
        values.put("imageLoc", character.getImage());

        db.insert(TABLE_CHARACTERS, null, values);
        db.close();
    }

    public ArrayList<Character> getAllCharacters() {
        ArrayList<Character> characters = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        // String sqlQuery = "SELECT  * FROM " + TABLE_COUNTRIES;
        // Cursor cursor = db.rawQuery(sqlQuery, null);

        Cursor cursor = db.query(TABLE_CHARACTERS, new String[]{"id", "name", "status", "lastKnownLoc", "imageLoc"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Character character = new Character(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4));
            characters.add(character);
        }
        cursor.close();

        return characters;
    }
}