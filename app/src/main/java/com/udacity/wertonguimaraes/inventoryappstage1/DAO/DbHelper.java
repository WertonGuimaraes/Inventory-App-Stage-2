package com.udacity.wertonguimaraes.inventoryappstage1.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.Contract.ContractEntry.COLUMN_CONTACT_EMAIL;
import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.Contract.ContractEntry.COLUMN_CONTACT_NAME;
import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.Contract.ContractEntry.COLUMN_CONTACT_PHONE;
import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.Contract.ContractEntry.COLUMN_PRODUCT_IMAGE;
import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.Contract.ContractEntry.COLUMN_PRODUCT_NAME;
import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.Contract.ContractEntry.COLUMN_PRODUCT_PRICE;
import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.Contract.ContractEntry.COLUMN_PRODUCT_QUANTITY;
import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.Contract.ContractEntry.SQL_CREATE_POSTS;
import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.Contract.ContractEntry.SQL_DELETE_POSTS;
import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.Contract.ContractEntry.TABLE_NAME;

/**
 * Created by wertonguimaraes on 31/01/18.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "InventoryAppStageOne.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_POSTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_POSTS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insertItem(String productName, double productPrice, int productQuantity,
                           String productImage, String contactName, String contactEmail,
                           String contactPhone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_PRODUCT_NAME, productName);
        values.put(COLUMN_PRODUCT_PRICE, productPrice);
        values.put(COLUMN_PRODUCT_QUANTITY, productQuantity);
        values.put(COLUMN_PRODUCT_IMAGE, productImage);
        values.put(COLUMN_CONTACT_NAME, contactName);
        values.put(COLUMN_CONTACT_EMAIL, contactEmail);
        values.put(COLUMN_CONTACT_PHONE, contactPhone);

        db.insert(TABLE_NAME, null, values);
    }

    public int countItems() {
        int totalItems;
        SQLiteDatabase readDB = this.getReadableDatabase();

        Cursor cursor = readDB.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        totalItems = cursor.getCount();
        cursor.close();
        return totalItems;
    }
}