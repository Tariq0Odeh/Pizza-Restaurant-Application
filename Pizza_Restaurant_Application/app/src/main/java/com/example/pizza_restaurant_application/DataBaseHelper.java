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
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PizzaDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER = "user";
    private static final String TABLE_PIZZA = "pizza";


    // User table columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_PASSWORD_HASH = "password_hash";
    private static final String COLUMN_PROFILE_PICTURE = "profile_picture";
    private static final String COLUMN_IS_ADMIN = "is_admin";

    // Pizza table columns
    private static final String COLUMN_PIZZA_NAME = "name";
    private static final String COLUMN_PIZZA_CATEGORY = "category";
    private static final String COLUMN_PIZZA_DESCRIPTION = "description";
    private static final String COLUMN_PIZZA_SMALL_PRICE = "small_price";
    private static final String COLUMN_PIZZA_MEDIUM_PRICE = "medium_price";
    private static final String COLUMN_PIZZA_LARGE_PRICE = "large_price";

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
                COLUMN_PROFILE_PICTURE + " TEXT," +
                COLUMN_IS_ADMIN + " INTEGER" +
                ")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_PIZZA_TABLE = "CREATE TABLE " + TABLE_PIZZA +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PIZZA_NAME + " TEXT," +
                COLUMN_PIZZA_CATEGORY + " TEXT," +
                COLUMN_PIZZA_DESCRIPTION + " TEXT," +
                COLUMN_PIZZA_SMALL_PRICE + " REAL," +
                COLUMN_PIZZA_MEDIUM_PRICE + " REAL," +
                COLUMN_PIZZA_LARGE_PRICE + " REAL" +
                ")";
        db.execSQL(CREATE_PIZZA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PIZZA);
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
        values.put(COLUMN_IS_ADMIN, user.isAdmin() ? 1 : 0);
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

    public boolean insertPizza(Pizza pizza) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PIZZA_NAME, pizza.getName());
        values.put(COLUMN_PIZZA_CATEGORY, pizza.getCategory());
        values.put(COLUMN_PIZZA_DESCRIPTION, pizza.getDescription());
        values.put(COLUMN_PIZZA_SMALL_PRICE, pizza.getSmallPrice());
        values.put(COLUMN_PIZZA_MEDIUM_PRICE, pizza.getMediumPrice());
        values.put(COLUMN_PIZZA_LARGE_PRICE, pizza.getLargePrice());
        long result = db.insert(TABLE_PIZZA, null, values);
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
            int isAdminInt = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_ADMIN));
            boolean isAdmin = (isAdminInt == 1);
            Uri uri = (profilePicture != null) ? Uri.parse(profilePicture) : null;
            user = new User(user_email, phone, firstName, lastName, gender, null, uri, isAdmin);
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

    public void insertAdminUser() {
        User adminUser = new User(
                "t@gmail.com",
                "0509876543",
                "tariq",
                "odeh",
                "male",
                "ttpp1100",
                null,
                true
        );
        insertUser(adminUser);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++

    public List<Pizza> getAllPizzas() {
        List<Pizza> pizzas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PIZZA, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_NAME));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_CATEGORY));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_DESCRIPTION));
                double smallPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_SMALL_PRICE));
                double mediumPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_MEDIUM_PRICE));
                double largePrice = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_LARGE_PRICE));

                Pizza pizza = new Pizza();
                pizza.setName(name);
                pizza.setCategory(category);
                pizza.setDescription(description);
                pizza.setSmallPrice(smallPrice);
                pizza.setMediumPrice(mediumPrice);
                pizza.setLargePrice(largePrice);

                pizzas.add(pizza);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return pizzas;
    }

}
