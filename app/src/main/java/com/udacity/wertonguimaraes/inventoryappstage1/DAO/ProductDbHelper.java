package com.udacity.wertonguimaraes.inventoryappstage1.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.ProductContract.ContractEntry.COLUMN_CONTACT_EMAIL;
import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.ProductContract.ContractEntry.COLUMN_CONTACT_NAME;
import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.ProductContract.ContractEntry.COLUMN_CONTACT_PHONE;
import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.ProductContract.ContractEntry.COLUMN_PRODUCT_IMAGE;
import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.ProductContract.ContractEntry.COLUMN_PRODUCT_NAME;
import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.ProductContract.ContractEntry.COLUMN_PRODUCT_PRICE;
import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.ProductContract.ContractEntry.COLUMN_PRODUCT_QUANTITY;
import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.ProductContract.ContractEntry.SQL_CREATE_POSTS;
import static com.udacity.wertonguimaraes.inventoryappstage1.DAO.ProductContract.ContractEntry.TABLE_NAME;

/**
 * Created by wertonguimaraes on 31/01/18.
 */

public class ProductDbHelper extends SQLiteOpenHelper {
    // Mude para 3 para aplicar as migrações (no final vai ficar a mesma coisa, é só paara teste)
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "InventoryAppStageOne.db";
    private static final String TAG = ProductDbHelper.class.getName();
    private Context mContext;

    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_POSTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrade database.");

        try {
            for (int version = oldVersion; version < newVersion; ++version) {
                String migrationName = String.format("from_%d_to_%d.sql", version, (version + 1));
                Log.d(TAG, "Looking for migration file: " + migrationName);
                readAndExecuteSQLScript(db, mContext, migrationName);
            }
        } catch (Exception exception) {
            Log.e(TAG, "Exception running upgrade script:", exception);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void readAndExecuteSQLScript(SQLiteDatabase db, Context ctx, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            Log.d(TAG, "SQL script file name is empty");
            return;
        }

        Log.d(TAG, "Script found. Executing...");
        AssetManager assetManager = ctx.getAssets();
        BufferedReader reader = null;

        try {
            InputStream is = assetManager.open(fileName);
            InputStreamReader isr = new InputStreamReader(is);
            reader = new BufferedReader(isr);
            executeSQLScript(db, reader);
        } catch (IOException e) {
            Log.e(TAG, "IOException:", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "IOException:", e);
                }
            }
        }
    }

    private void executeSQLScript(SQLiteDatabase db, BufferedReader reader) throws IOException {
        String line;
        StringBuilder statement = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            statement.append(line);
            statement.append("\n");
            if (line.endsWith(";")) {
                db.execSQL(statement.toString());
                statement = new StringBuilder();
            }
        }
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

    public Cursor getAllDataCursor() {
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

        return cursor;
    }
}