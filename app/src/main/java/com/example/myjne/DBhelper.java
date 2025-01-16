package com.example.myjne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBhelper extends SQLiteOpenHelper {

    // Database Information
    private static final String DATABASE_NAME = "myjne.db";
    private static final int DATABASE_VERSION = 4;

    // Table and Column Names
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "namaLengkap";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";

    private static final String TABLE_PARCELS = "parcels";
    private static final String COLUMN_PARCEL_ID = "id";
    private static final String COLUMN_PARCEL_ORIGIN = "lokasi_asal";
    private static final String COLUMN_PARCEL_DESTINATION = "lokasi_tujuan";
    private static final String COLUMN_PARCEL_SENDER = "nama_pengirim";
    private static final String COLUMN_PARCEL_PHONE = "nomor_telepon";
    private static final String COLUMN_PARCEL_TYPE = "jenis_barang";
    private static final String COLUMN_PARCEL_WEIGHT = "estimasi_berat";
    private static final String COLUMN_PARCEL_COST = "biaya";

    private static final String TABLE_CHATS = "chats";
    private static final String COLUMN_CHAT_ID = "id";
    private static final String COLUMN_CHAT_MESSAGE = "message";
    private static final String COLUMN_CHAT_SENDER = "sender";
    private static final String COLUMN_CHAT_TIMESTAMP = "timestamp";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // Create users table
            String createUserTable = "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_NAME + " TEXT, " +
                    COLUMN_USER_EMAIL + " TEXT UNIQUE, " +
                    COLUMN_USER_PASSWORD + " TEXT)";
            db.execSQL(createUserTable);

            // Create parcels table
            String createParcelTable = "CREATE TABLE " + TABLE_PARCELS + " (" +
                    COLUMN_PARCEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PARCEL_ORIGIN + " TEXT, " +
                    COLUMN_PARCEL_DESTINATION + " TEXT, " +
                    COLUMN_PARCEL_SENDER + " TEXT, " +
                    COLUMN_PARCEL_PHONE + " TEXT, " +
                    COLUMN_PARCEL_TYPE + " TEXT, " +
                    COLUMN_PARCEL_WEIGHT + " TEXT, " +
                    COLUMN_PARCEL_COST + " TEXT)";
            db.execSQL(createParcelTable);

            // Create chats table
            String createChatTable = "CREATE TABLE " + TABLE_CHATS + " (" +
                    COLUMN_CHAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CHAT_MESSAGE + " TEXT, " +
                    COLUMN_CHAT_SENDER + " TEXT, " +
                    COLUMN_CHAT_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
            db.execSQL(createChatTable);

            Log.d("DBHelper", "Tables created successfully.");
        } catch (Exception e) {
            Log.e("DBHelper", "Error creating tables: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion < 3) {
                String addCostColumn = "ALTER TABLE " + TABLE_PARCELS + " ADD COLUMN " + COLUMN_PARCEL_COST + " TEXT";
                db.execSQL(addCostColumn);
                Log.d("DBHelper", "Column 'biaya' added to table 'parcels'.");
            }
            if (oldVersion < 4) {
                String createChatTable = "CREATE TABLE IF NOT EXISTS " + TABLE_CHATS + " (" +
                        COLUMN_CHAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_CHAT_MESSAGE + " TEXT, " +
                        COLUMN_CHAT_SENDER + " TEXT, " +
                        COLUMN_CHAT_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
                db.execSQL(createChatTable);
                Log.d("DBHelper", "Table 'chats' created successfully.");
            }
        } catch (Exception e) {
            Log.e("DBHelper", "Error upgrading database: " + e.getMessage());
        }
    }

    // Insert a new order into the parcels table
    public boolean insertOrder(String lokasiAsal, String lokasiTujuan, String namaPengirim, String nomorTelepon, String jenisBarang, String estimasiBerat, String biaya) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PARCEL_ORIGIN, lokasiAsal);
        values.put(COLUMN_PARCEL_DESTINATION, lokasiTujuan);
        values.put(COLUMN_PARCEL_SENDER, namaPengirim);
        values.put(COLUMN_PARCEL_PHONE, nomorTelepon);
        values.put(COLUMN_PARCEL_TYPE, jenisBarang);
        values.put(COLUMN_PARCEL_WEIGHT, estimasiBerat);
        values.put(COLUMN_PARCEL_COST, biaya);

        long result = db.insert(TABLE_PARCELS, null, values);
        db.close();

        if (result == -1) {
            Log.e("DBHelper", "Insert order failed.");
            return false;
        } else {
            Log.d("DBHelper", "Insert order successful, ID: " + result);
            return true;
        }
    }

    // Insert a chat message into the chats table
    public boolean insertChatMessage(String message, String sender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHAT_MESSAGE, message);
        values.put(COLUMN_CHAT_SENDER, sender);

        long result = db.insert(TABLE_CHATS, null, values);
        db.close();

        if (result == -1) {
            Log.e("DBHelper", "Insert chat message failed.");
            return false;
        } else {
            Log.d("DBHelper", "Insert chat message successful, ID: " + result);
            return true;
        }
    }

    // Get all chat messages
    public Cursor getAllChatMessages() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CHATS + " ORDER BY " + COLUMN_CHAT_TIMESTAMP + " ASC";
        return db.rawQuery(query, null);
    }

    // Insert a new user
    public boolean insertUser(String namaLengkap, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, namaLengkap);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();

        if (result == -1) {
            Log.e("DBHelper", "Insert user failed.");
            return false;
        } else {
            Log.d("DBHelper", "Insert user successful, ID: " + result);
            return true;
        }
    }

    // Check if a user exists by email
    public boolean checkUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Validate user credentials
    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }
}
