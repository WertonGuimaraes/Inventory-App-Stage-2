package com.udacity.wertonguimaraes.inventoryappstage2.util;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductContract;
import com.udacity.wertonguimaraes.inventoryappstage2.model.Product;

/**
 * Created by wertonguimaraes on 01/03/18.
 */

public class Convert {

    public static Product cursorInProduct(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex(ProductContract.ContractEntry.COLUMN_PRODUCT_NAME));
        double price = cursor.getDouble(cursor.getColumnIndex(ProductContract.ContractEntry.COLUMN_PRODUCT_PRICE));
        int quantity = cursor.getInt(cursor.getColumnIndex(ProductContract.ContractEntry.COLUMN_PRODUCT_QUANTITY));
        byte[] productImage = cursor.getBlob(cursor.getColumnIndex(ProductContract.ContractEntry.COLUMN_PRODUCT_IMAGE));
        String contactName = cursor.getString(cursor.getColumnIndex(ProductContract.ContractEntry.COLUMN_CONTACT_NAME));
        String contactEmail = cursor.getString(cursor.getColumnIndex(ProductContract.ContractEntry.COLUMN_CONTACT_EMAIL));
        String contactPhone = cursor.getString(cursor.getColumnIndex(ProductContract.ContractEntry.COLUMN_CONTACT_PHONE));

        Bitmap imageBitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);
        return new Product(name, price, quantity, imageBitmap, contactName, contactEmail, contactPhone);
    }
}
