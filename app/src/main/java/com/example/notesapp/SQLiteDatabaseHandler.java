package com.example.notesapp;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserInfo";
    private static final String TABLE_NAME = "User";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD_HASH = "hash";
    private static final String KEY_CONTACT = "contact";

    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_EMAIL, KEY_PASSWORD_HASH, KEY_CONTACT};

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE User ( " + "name TEXT, "
                + "email TEXT PRIMARY KEY, " + "hash TEXT," + "contact TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteOne(UserDetails userDetails) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(UserDetails.getEmail())});
        db.close();
    }

    public UserDetails getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        UserDetails user = new UserDetails();
        assert cursor != null;
        user.setName(cursor.getString(0));
        user.setEmail(cursor.getString(1));
        user.setPassword_hash(cursor.getString(2));
        user.setContact(cursor.getString(3));

        cursor.close();

        return user;
    }

    public List<UserDetails> allUsers() {

        List<UserDetails> user = new LinkedList<UserDetails>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        UserDetails player = null;

        if (cursor.moveToFirst()) {
            do {

                player = new UserDetails();
                player.setName(cursor.getString(0));
                player.setEmail(cursor.getString(1));
                player.setPassword_hash(cursor.getString(2));
                player.setContact(cursor.getString(3));
                user.add(player);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return user;
    }

    public void addUser(UserDetails player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, player.getName());
        values.put(KEY_EMAIL, UserDetails.getEmail());
        values.put(KEY_PASSWORD_HASH, player.getPassword_hash());
        values.put(KEY_CONTACT, player.getContact());

        // insert
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public int updateUser(UserDetails user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_EMAIL, UserDetails.getEmail());
        values.put(KEY_PASSWORD_HASH, user.getPassword_hash());
        values.put(KEY_CONTACT, user.getContact());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[]{String.valueOf(UserDetails.getEmail())});

        db.close();

        return i;
    }

}
