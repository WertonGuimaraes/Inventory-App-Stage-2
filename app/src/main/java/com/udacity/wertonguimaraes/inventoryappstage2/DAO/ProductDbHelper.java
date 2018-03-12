package com.udacity.wertonguimaraes.inventoryappstage2.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.udacity.wertonguimaraes.inventoryappstage2.model.Product;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductContract.ContractEntry.COLUMN_CONTACT_EMAIL;
import static com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductContract.ContractEntry.COLUMN_CONTACT_NAME;
import static com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductContract.ContractEntry.COLUMN_CONTACT_PHONE;
import static com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductContract.ContractEntry.COLUMN_PRODUCT_IMAGE;
import static com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductContract.ContractEntry.COLUMN_PRODUCT_NAME;
import static com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductContract.ContractEntry.COLUMN_PRODUCT_PRICE;
import static com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductContract.ContractEntry.COLUMN_PRODUCT_QUANTITY;
import static com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductContract.ContractEntry.SQL_CREATE_POSTS;
import static com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductContract.ContractEntry.TABLE_NAME;
import static com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductContract.ContractEntry._ID;

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

    public void insertItem(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = productContentValues(product);
        db.insert(TABLE_NAME, null, values);
    }

    public Cursor getAllDataCursor() {
        SQLiteDatabase readDB = this.getReadableDatabase();

        return readDB.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public Cursor getProductCursorById(int productId) {
        SQLiteDatabase readDB = this.getReadableDatabase();

        String selection = _ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(productId)};

        Cursor cursor = readDB.query(
                TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        return cursor;
    }

    public void deleteProduct(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "_id=?";
        String[] whereArgs = new String[] { String.valueOf(productId) };
        db.delete(TABLE_NAME, whereClause, whereArgs);
    }

    public void updateItemQuantity(long id, int quantity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_QUANTITY, quantity);
        String selection = _ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        db.update(TABLE_NAME, values, selection, selectionArgs);
    }

    public void updateItem(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = productContentValues(product);
        String selection = _ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(product.getId())};
        db.update(TABLE_NAME, values, selection, selectionArgs);
    }

    private ContentValues productContentValues(Product product) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        product.getProductImage().compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] productImageByte = bos.toByteArray();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, product.getProductName());
        values.put(COLUMN_PRODUCT_PRICE, product.getProductPrice());
        values.put(COLUMN_PRODUCT_QUANTITY, product.getProductQuantity());
        values.put(COLUMN_PRODUCT_IMAGE, productImageByte);
        values.put(COLUMN_CONTACT_NAME, product.getContactName());
        values.put(COLUMN_CONTACT_EMAIL, product.getContactEmail());
        values.put(COLUMN_CONTACT_PHONE, product.getContactPhone());

        return values;
    }

}