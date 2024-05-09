package com.example.pizza_restaurant_application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDatabase";
    private static final int DATABASE_VERSION = 8;
    private static final String TABLE_USER = "user";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_PASSWORD_HASH = "password_hash";
    private static final String COLUMN_PROFILE_PICTURE = "profile_picture";
    private static String user_email = "";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_EMAIL + " TEXT UNIQUE," +
                COLUMN_PHONE + " TEXT," +
                COLUMN_FIRST_NAME + " TEXT," +
                COLUMN_LAST_NAME + " TEXT," +
                COLUMN_GENDER + " TEXT," +
                COLUMN_PASSWORD_HASH + " TEXT," +
                COLUMN_PROFILE_PICTURE + " TEXT" +
                ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public boolean insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PHONE, user.getPhone());
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_GENDER, user.getGender());
        values.put(COLUMN_PASSWORD_HASH, encryptPassword(user.getPassword()));
        Uri profilePictureUri = user.getProfilePicture();
        if (profilePictureUri != null) {
            values.put(COLUMN_PROFILE_PICTURE, profilePictureUri.toString());
        } else {
            values.putNull(COLUMN_PROFILE_PICTURE);
        }
        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return result != -1;
    }

    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_EMAIL + "=?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkPhoneExists(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_PHONE + "=?", new String[]{phone});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String hashedPassword = encryptPassword(password);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD_HASH + "=?", new String[]{email, hashedPassword});
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        return valid;
    }

    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password;
        }
    }

    public void  setUserEmail(String email) {
        user_email = email;
    }

    public User getUserByEmail() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_EMAIL + "=?", new String[]{user_email});
        User user = null;
        if (cursor.moveToFirst()) {
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER));
            String profilePicture = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_PICTURE));
            Uri uri = (profilePicture != null) ? Uri.parse(profilePicture) : null;
            user = new User(user_email, phone, firstName, lastName, gender, null, uri);
        }
        cursor.close();
        return user;
    }


    public boolean updateUserInfo(String email, String firstName, String lastName, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_PHONE, phone);
        int rowsAffected = db.update(TABLE_USER, values, COLUMN_EMAIL + "=?", new String[]{email});
        return rowsAffected > 0;
    }

    public boolean updateUserPassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD_HASH, encryptPassword(newPassword));
        int rowsAffected = db.update(TABLE_USER, values, COLUMN_EMAIL + "=?", new String[]{email});
        return rowsAffected > 0;
    }

    public boolean updateUserProfilePicture(String email, String newProfilePicture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROFILE_PICTURE, newProfilePicture);
        int rowsAffected = db.update(TABLE_USER, values, COLUMN_EMAIL + "=?", new String[]{email});
        return rowsAffected > 0;
    }

}
