package com.example.tastytray;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "orders.db";
    private static final int DATABASE_VERSION = 1;

    // Table names and column names
    private static final String TABLE_ORDERS = "orders";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CUSTOMER_NAME = "product_name";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String ORDER_PRICE = "price";
    private static final String COLUMN_QUANTITY = "quantity";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the orders table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createOrdersTable = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CUSTOMER_NAME + " TEXT,"
                + COLUMN_PHONE_NUMBER + " TEXT,"
                + COLUMN_QUANTITY + " INTEGER,"
                + ORDER_PRICE + " REAL"
                + ")";
        db.execSQL(createOrdersTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

   // insert an order
    public boolean insertOrder(String productName, String phoneNumber, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CUSTOMER_NAME, productName);
        values.put(COLUMN_PHONE_NUMBER, phoneNumber);
        values.put(COLUMN_QUANTITY, quantity);
        // Insert row
        long result;
        try {
            result = db.insert(TABLE_ORDERS, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return result > 0;
    }

    public ArrayList<MyOrderModel> getOrders() {
        ArrayList<MyOrderModel> orderModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + ", " + COLUMN_CUSTOMER_NAME + ", " + COLUMN_PHONE_NUMBER + ", " + COLUMN_QUANTITY + " FROM " + TABLE_ORDERS, null);
//        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + ", " + COLUMN_CUSTOMER_NAME + ", " + COLUMN_PHONE_NUMBER + ", " + COLUMN_QUANTITY + ", " + ORDER_PRICE + " FROM " + TABLE_ORDERS, null);

        if(cursor.moveToFirst()){
            do {
                MyOrderModel model = new MyOrderModel();
                model.setQuantity(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)));
                model.setSoldItemName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CUSTOMER_NAME)));
                model.setOrderImage(R.drawable.burgur);
                model.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                orderModels.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orderModels;
    }
}
