package com.example.pizza_restaurant_application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Database2";
    private static final int DATABASE_VERSION = 7;

    // Tables
    private static final String TABLE_USER = "user";
    private static final String TABLE_PIZZA = "pizza";
    private static final String TABLE_FAVORITES = "favorites";
    private static final String TABLE_ORDERS = "orders";
    private static final String TABLE_SPECIAL_OFFERS = "special_offers";
    private static final String TABLE_SPECIAL_OFFERS_FAVORITES = "special_offers_favorites";
    private static final String TABLE_SPECIAL_OFFERS_ORDERS = "special_offers_orders";

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

    // Favorites table columns
    private static final String COLUMN_FAVORITE_ID = "favorite_id";
    private static final String COLUMN_USER_EMAIL = "user_email";

    // Orders table columns
    private static final String COLUMN_ORDER_ID = "order_id";
    private static final String COLUMN_PIZZA_ID = "pizza_id";
    private static final String COLUMN_ORDER_PIZZA_NAME = "pizza_name";
    private static final String COLUMN_ORDER_SIZE = "size";
    private static final String COLUMN_ORDER_QUANTITY = "quantity";
    private static final String COLUMN_ORDER_TOTAL_PRICE = "total_price";
    private static final String COLUMN_ORDER_DATE = "date";
    private static final String COLUMN_ORDER_TIME = "time";

    private static final String COLUMN_SPECIAL_OFFER_ID = "special_offer_id";

    // Update the table columns
    private static final String COLUMN_SPECIAL_OFFER_ORDER_ID = "special_offer_order_id";

    private static final String COLUMN_SPECIAL_OFFER_FAVORITE_ID = "special_offer_favorite_id";

    private static String user_email = "";
    private boolean clearTableFlag = false;

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

        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USER_EMAIL + " TEXT," +
                COLUMN_FAVORITE_ID + " INTEGER," +
                "FOREIGN KEY(" + COLUMN_USER_EMAIL + ") REFERENCES " + TABLE_USER + "(" + COLUMN_EMAIL + ")," +
                "FOREIGN KEY(" + COLUMN_FAVORITE_ID + ") REFERENCES " + TABLE_PIZZA + "(" + COLUMN_ID + ")" +
                ")";
        db.execSQL(CREATE_FAVORITES_TABLE);

        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS +
                "(" +
                COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USER_EMAIL + " TEXT," +
                COLUMN_PIZZA_ID + " INTEGER," +
                COLUMN_ORDER_PIZZA_NAME + " TEXT," +
                COLUMN_ORDER_SIZE + " TEXT," +
                COLUMN_ORDER_QUANTITY + " INTEGER," +
                COLUMN_ORDER_TOTAL_PRICE + " REAL," +
                COLUMN_ORDER_DATE + " TEXT," +
                COLUMN_ORDER_TIME + " TEXT," +
                "FOREIGN KEY(" + COLUMN_USER_EMAIL + ") REFERENCES " + TABLE_USER + "(" + COLUMN_EMAIL + ")," +
                "FOREIGN KEY(" + COLUMN_PIZZA_ID + ") REFERENCES " + TABLE_PIZZA + "(" + COLUMN_ID + ")" +
                ")";
        db.execSQL(CREATE_ORDERS_TABLE);

        String CREATE_SPECIAL_OFFERS_TABLE = "CREATE TABLE " + TABLE_SPECIAL_OFFERS +
                "(" +
                COLUMN_SPECIAL_OFFER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PIZZA_NAME + " TEXT," +
                COLUMN_PIZZA_CATEGORY + " TEXT," +
                COLUMN_PIZZA_DESCRIPTION + " TEXT," +
                COLUMN_ORDER_SIZE + " TEXT," +
                COLUMN_ORDER_TOTAL_PRICE + " REAL," +
                COLUMN_ORDER_DATE + " TEXT" +
                ")";
        db.execSQL(CREATE_SPECIAL_OFFERS_TABLE);


        String CREATE_SPECIAL_OFFERS_ORDERS_TABLE = "CREATE TABLE " + TABLE_SPECIAL_OFFERS_ORDERS + "("
                + COLUMN_SPECIAL_OFFER_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_EMAIL + " TEXT,"
                + COLUMN_SPECIAL_OFFER_ID + " INTEGER,"
                + COLUMN_PIZZA_NAME + " TEXT,"
                + COLUMN_ORDER_SIZE + " TEXT,"
                + COLUMN_ORDER_QUANTITY + " INTEGER,"
                + COLUMN_ORDER_TOTAL_PRICE + " REAL,"
                + COLUMN_ORDER_DATE + " TEXT,"
                + COLUMN_ORDER_TIME + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_USER_EMAIL + ") REFERENCES " + TABLE_USER + "(" + COLUMN_EMAIL + "),"
                + "FOREIGN KEY(" + COLUMN_SPECIAL_OFFER_ID + ") REFERENCES " + TABLE_SPECIAL_OFFERS + "(" + COLUMN_SPECIAL_OFFER_ID + ")" + ")";
        db.execSQL(CREATE_SPECIAL_OFFERS_ORDERS_TABLE);


        // Creating the special offers favorites table
        String CREATE_SPECIAL_OFFERS_FAVORITES_TABLE = "CREATE TABLE " + TABLE_SPECIAL_OFFERS_FAVORITES +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USER_EMAIL + " TEXT," +
                COLUMN_SPECIAL_OFFER_FAVORITE_ID + " INTEGER," +
                "FOREIGN KEY(" + COLUMN_USER_EMAIL + ") REFERENCES " + TABLE_USER + "(" + COLUMN_EMAIL + ")," +
                "FOREIGN KEY(" + COLUMN_SPECIAL_OFFER_FAVORITE_ID + ") REFERENCES " + TABLE_SPECIAL_OFFERS + "(" + COLUMN_SPECIAL_OFFER_ID + ")" +
                ")";
        db.execSQL(CREATE_SPECIAL_OFFERS_FAVORITES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PIZZA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPECIAL_OFFERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPECIAL_OFFERS_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPECIAL_OFFERS_FAVORITES);

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

   //+++++++++++++++++++++++++++++++++++++++++++++++++

    // Method to check if a pizza exists in the database
    public boolean pizzaExists(String pizzaName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PIZZA, new String[]{COLUMN_PIZZA_NAME},
                COLUMN_PIZZA_NAME + " = ?", new String[]{pizzaName},
                null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // Method to update an existing pizza
    public boolean updatePizza(Pizza pizza) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PIZZA_CATEGORY, pizza.getCategory());
        values.put(COLUMN_PIZZA_DESCRIPTION, pizza.getDescription());
        values.put(COLUMN_PIZZA_SMALL_PRICE, pizza.getSmallPrice());
        values.put(COLUMN_PIZZA_MEDIUM_PRICE, pizza.getMediumPrice());
        values.put(COLUMN_PIZZA_LARGE_PRICE, pizza.getLargePrice());
        int rowsAffected = db.update(TABLE_PIZZA, values,
                COLUMN_PIZZA_NAME + " = ?", new String[]{pizza.getName()});
        db.close();
        return rowsAffected > 0;
    }


    // Method to insert or update a pizza
    public boolean insertPizza(Pizza pizza) {
        if (pizzaExists(pizza.getName())) {
            return updatePizza(pizza);
        } else {
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
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++

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

    public void insertAdminUserIfNeeded() {
        if (!checkEmailExists("t@gmail.com")) {
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
    }

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

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    // Method to add a favorite pizza for a user
    public boolean addFavoritePizza(int pizzaId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, user_email);
        values.put(COLUMN_FAVORITE_ID, pizzaId);
        long result = db.insert(TABLE_FAVORITES, null, values);
        db.close();
        return result != -1;
    }

    // Method to remove a favorite pizza for a user
    public boolean removeFavoritePizza(int pizzaId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_FAVORITES, COLUMN_USER_EMAIL + " = ? AND " + COLUMN_FAVORITE_ID + " = ?",
                new String[]{user_email, String.valueOf(pizzaId)});
        db.close();
        return result > 0;
    }

    // Method to get all favorite pizzas for a user
    public List<Pizza> getFavoritePizzas() {
        List<Pizza> favoritePizzas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PIZZA + " INNER JOIN " + TABLE_FAVORITES +
                " ON " + TABLE_PIZZA + "." + COLUMN_ID + " = " + TABLE_FAVORITES + "." + COLUMN_FAVORITE_ID +
                " WHERE " + TABLE_FAVORITES + "." + COLUMN_USER_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{user_email});
        if (cursor.moveToFirst()) {
            do {
                // Retrieve pizza details from cursor
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_NAME));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_CATEGORY));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_DESCRIPTION));
                double smallPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_SMALL_PRICE));
                double mediumPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_MEDIUM_PRICE));
                double largePrice = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_LARGE_PRICE));

                // Create Pizza object and add it to the list
                Pizza pizza = new Pizza();
                pizza.setName(name);
                pizza.setCategory(category);
                pizza.setDescription(description);
                pizza.setSmallPrice(smallPrice);
                pizza.setMediumPrice(mediumPrice);
                pizza.setLargePrice(largePrice);
                favoritePizzas.add(pizza);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return favoritePizzas;
    }

    public int getPizzaIdByName(String pizzaName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int pizzaId = -1; // Default value if pizza ID is not found
        Cursor cursor = db.query(TABLE_PIZZA,
                new String[]{COLUMN_ID},
                COLUMN_PIZZA_NAME + "=?",
                new String[]{pizzaName},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            // If cursor is not null and contains at least one row, extract the pizza ID
            pizzaId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            cursor.close();
        }
        return pizzaId;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean insertOrder(int pizzaId, String pizzaName, String size, int quantity, double totalPrice, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_email", user_email);
        values.put("pizza_id", pizzaId);
        values.put("pizza_name", pizzaName);
        values.put("size", size);
        values.put("quantity", quantity);
        values.put("total_price", totalPrice);
        values.put("date", date);
        values.put("time", time);
        long result = db.insert("orders", null, values);
        db.close();
        return result != -1;
    }

    public List<Order> getUserOrders() {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM orders WHERE user_email=?", new String[]{user_email});
        if (cursor.moveToFirst()) {
            do {
                int orderId = cursor.getInt(cursor.getColumnIndexOrThrow("order_id"));
                int pizzaId = cursor.getInt(cursor.getColumnIndexOrThrow("pizza_id"));
                String pizzaName = cursor.getString(cursor.getColumnIndexOrThrow("pizza_name"));
                String size = cursor.getString(cursor.getColumnIndexOrThrow("size"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                double totalPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("total_price"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                Order order = new Order(orderId, pizzaId, pizzaName, size, quantity, totalPrice, date, time);
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orders;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public List<OrderWithCustomerName> getAllOrdersWithCustomerName() {
        List<OrderWithCustomerName> ordersWithCustomerName = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor userCursor = db.rawQuery("SELECT " + COLUMN_EMAIL + ", " + COLUMN_FIRST_NAME + ", " + COLUMN_LAST_NAME + " FROM " + TABLE_USER, null);
        if (userCursor.moveToFirst()) {
            do {
                String userEmail = userCursor.getString(userCursor.getColumnIndexOrThrow(COLUMN_EMAIL));
                String firstName = userCursor.getString(userCursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME));
                String lastName = userCursor.getString(userCursor.getColumnIndexOrThrow(COLUMN_LAST_NAME));

                Cursor orderCursor = db.rawQuery("SELECT * FROM " + TABLE_ORDERS + " WHERE " + COLUMN_USER_EMAIL + "=?", new String[]{userEmail});
                if (orderCursor.moveToFirst()) {
                    do {
                        int orderId = orderCursor.getInt(orderCursor.getColumnIndexOrThrow(COLUMN_ORDER_ID));
                        int pizzaId = orderCursor.getInt(orderCursor.getColumnIndexOrThrow(COLUMN_PIZZA_ID));
                        String pizzaName = orderCursor.getString(orderCursor.getColumnIndexOrThrow(COLUMN_ORDER_PIZZA_NAME));
                        String size = orderCursor.getString(orderCursor.getColumnIndexOrThrow(COLUMN_ORDER_SIZE));
                        int quantity = orderCursor.getInt(orderCursor.getColumnIndexOrThrow(COLUMN_ORDER_QUANTITY));
                        double totalPrice = orderCursor.getDouble(orderCursor.getColumnIndexOrThrow(COLUMN_ORDER_TOTAL_PRICE));
                        String date = orderCursor.getString(orderCursor.getColumnIndexOrThrow(COLUMN_ORDER_DATE));
                        String time = orderCursor.getString(orderCursor.getColumnIndexOrThrow(COLUMN_ORDER_TIME));

                        OrderWithCustomerName order = new OrderWithCustomerName(orderId, pizzaId, pizzaName, size, quantity, totalPrice, date, time, firstName, lastName);
                        ordersWithCustomerName.add(order);
                    } while (orderCursor.moveToNext());
                }
                orderCursor.close();
            } while (userCursor.moveToNext());
        }
        userCursor.close();
        return ordersWithCustomerName;
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public List<String> getAllPizzaTypes() {
        List<String> pizzaTypes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMN_PIZZA_NAME + " FROM " + TABLE_PIZZA, null);
        if (cursor.moveToFirst()) {
            do {
                String pizzaType = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_NAME));
                pizzaTypes.add(pizzaType);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return pizzaTypes;
    }

    public boolean insertSpecialOffer(String type, String size, double price, String period) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Retrieve pizza category and description from the Pizza table based on the provided pizza name
        String category = "";
        String description = "";
        Cursor pizzaCursor = db.query(TABLE_PIZZA,
                new String[]{COLUMN_PIZZA_CATEGORY, COLUMN_PIZZA_DESCRIPTION},
                COLUMN_PIZZA_NAME + "=?",
                new String[]{type},
                null, null, null);

        if (pizzaCursor != null && pizzaCursor.moveToFirst()) {
            category = pizzaCursor.getString(pizzaCursor.getColumnIndexOrThrow(COLUMN_PIZZA_CATEGORY));
            description = pizzaCursor.getString(pizzaCursor.getColumnIndexOrThrow(COLUMN_PIZZA_DESCRIPTION));
            pizzaCursor.close();
        }

        // Insert the special offer with all the necessary information
        ContentValues values = new ContentValues();
        values.put(COLUMN_PIZZA_NAME, type);
        values.put(COLUMN_PIZZA_CATEGORY, category); // Add pizza category
        values.put(COLUMN_PIZZA_DESCRIPTION, description); // Add pizza description
        values.put(COLUMN_ORDER_SIZE, size);
        values.put(COLUMN_ORDER_TOTAL_PRICE, price);
        values.put(COLUMN_ORDER_DATE, period);
        long result = db.insert(TABLE_SPECIAL_OFFERS, null, values);

        db.close();
        return result != -1;
    }

    public List<SpecialOffer> getAllSpecialOffers() {
        List<SpecialOffer> specialOffers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to retrieve all special offers
        Cursor cursor = db.query(TABLE_SPECIAL_OFFERS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extracting data from the cursor
                int specialOfferId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SPECIAL_OFFER_ID));
                String pizzaName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_NAME));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_CATEGORY));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_DESCRIPTION));
                String size = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_SIZE));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TOTAL_PRICE));
                String period = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_DATE));

                // Creating SpecialOffer object and adding to the list
                SpecialOffer specialOffer = new SpecialOffer(specialOfferId, pizzaName, category, description, size, price, period);
                specialOffers.add(specialOffer);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return specialOffers;
    }


    //++++++++++++++++++++++++++++++++++++++++++++++++


    public boolean insertSpecialOfferOrder(int specialOfferId, String pizzaName, String size, int quantity, double totalPrice, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, user_email);
        values.put(COLUMN_SPECIAL_OFFER_ID, specialOfferId);
        values.put(COLUMN_PIZZA_NAME, pizzaName);
        values.put(COLUMN_ORDER_SIZE, size);
        values.put(COLUMN_ORDER_QUANTITY, quantity); // Add quantity to ContentValues
        values.put(COLUMN_ORDER_TOTAL_PRICE, totalPrice);
        values.put(COLUMN_ORDER_DATE, date);
        values.put(COLUMN_ORDER_TIME, time);
        long result = db.insert(TABLE_SPECIAL_OFFERS_ORDERS, null, values);
        db.close();
        return result != -1;
    }



    public List<SpecialOfferOrder> getSpecialOfferOrders() {
        List<SpecialOfferOrder> specialOfferOrders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SPECIAL_OFFERS_ORDERS + " WHERE " + COLUMN_USER_EMAIL + "=?", new String[]{user_email});
        if (cursor.moveToFirst()) {
            do {
                int orderId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ID));
                int specialOfferId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SPECIAL_OFFER_ID));
                String pizzaName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_NAME));
                String size = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_SIZE));
                double totalPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TOTAL_PRICE));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_DATE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TIME));
                SpecialOfferOrder order = new SpecialOfferOrder(orderId, specialOfferId, user_email, pizzaName, size, totalPrice, date, time);
                specialOfferOrders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return specialOfferOrders;
    }


    //++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean addFavoriteSpecialOffer(int specialOfferId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, user_email);
        values.put(COLUMN_SPECIAL_OFFER_FAVORITE_ID, specialOfferId);
        long result = db.insert(TABLE_SPECIAL_OFFERS_FAVORITES, null, values);
        db.close();
        return result != -1;
    }

    public boolean removeFavoriteSpecialOffer(int specialOfferId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_SPECIAL_OFFERS_FAVORITES, COLUMN_USER_EMAIL + " = ? AND " + COLUMN_SPECIAL_OFFER_FAVORITE_ID + " = ?",
                new String[]{user_email, String.valueOf(specialOfferId)});
        db.close();
        return result > 0;
    }

    public boolean isFavoriteSpecialOffer(int specialOfferId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_SPECIAL_OFFERS_FAVORITES +
                " WHERE " + COLUMN_USER_EMAIL + " = ? AND " + COLUMN_SPECIAL_OFFER_FAVORITE_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{user_email, String.valueOf(specialOfferId)});
        boolean isFavorite = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isFavorite;
    }


    public List<SpecialOffer> getFavoriteSpecialOffers() {
        List<SpecialOffer> favoriteSpecialOffers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "
                + TABLE_SPECIAL_OFFERS + "." + COLUMN_SPECIAL_OFFER_ID + ", "
                + TABLE_SPECIAL_OFFERS + "." + COLUMN_PIZZA_NAME + ", "
                + TABLE_SPECIAL_OFFERS + "." + COLUMN_PIZZA_CATEGORY + ", "
                + TABLE_SPECIAL_OFFERS + "." + COLUMN_PIZZA_DESCRIPTION + ", "
                + TABLE_SPECIAL_OFFERS + "." + COLUMN_ORDER_SIZE + ", "
                + TABLE_SPECIAL_OFFERS + "." + COLUMN_ORDER_TOTAL_PRICE + ", "
                + TABLE_SPECIAL_OFFERS + "." + COLUMN_ORDER_DATE
                + " FROM " + TABLE_SPECIAL_OFFERS
                + " INNER JOIN " + TABLE_SPECIAL_OFFERS_FAVORITES
                + " ON " + TABLE_SPECIAL_OFFERS + "." + COLUMN_SPECIAL_OFFER_ID + " = " + TABLE_SPECIAL_OFFERS_FAVORITES + "." + COLUMN_SPECIAL_OFFER_FAVORITE_ID
                + " WHERE " + TABLE_SPECIAL_OFFERS_FAVORITES + "." + COLUMN_USER_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{user_email});
        if (cursor.moveToFirst()) {
            do {
                int specialOfferId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SPECIAL_OFFER_ID));
                String pizzaName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_NAME));
                String pizzaCategory = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_CATEGORY));
                String pizzaDescription = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PIZZA_DESCRIPTION));
                String size = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_SIZE));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TOTAL_PRICE));
                String period = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_DATE)); // Assuming period is the date here, adjust as needed

                SpecialOffer specialOffer = new SpecialOffer(specialOfferId, pizzaName, pizzaCategory, pizzaDescription, size, price, period);
                favoriteSpecialOffers.add(specialOffer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return favoriteSpecialOffers;
    }


}
