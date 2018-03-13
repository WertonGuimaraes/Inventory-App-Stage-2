package com.udacity.wertonguimaraes.inventoryappstage2.util;

import android.database.Cursor;

import com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductContract;
import com.udacity.wertonguimaraes.inventoryappstage2.model.Product;

/**
 * Created by wertonguimaraes on 01/03/18.
 */

public class Convert {

    public static Product cursorInProduct(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(ProductContract.ContractEntry._ID));
        String name = cursor.getString(cursor.getColumnIndex(ProductContract.ContractEntry.COLUMN_PRODUCT_NAME));
        double price = cursor.getDouble(cursor.getColumnIndex(ProductContract.ContractEntry.COLUMN_PRODUCT_PRICE));
        int quantity = cursor.getInt(cursor.getColumnIndex(ProductContract.ContractEntry.COLUMN_PRODUCT_QUANTITY));
        String productImageName = cursor.getString(cursor.getColumnIndex(ProductContract.ContractEntry.COLUMN_PRODUCT_IMAGE_NAME));
        String contactName = cursor.getString(cursor.getColumnIndex(ProductContract.ContractEntry.COLUMN_CONTACT_NAME));
        String contactEmail = cursor.getString(cursor.getColumnIndex(ProductContract.ContractEntry.COLUMN_CONTACT_EMAIL));
        String contactPhone = cursor.getString(cursor.getColumnIndex(ProductContract.ContractEntry.COLUMN_CONTACT_PHONE));

        return new Product(id, name, price, quantity, productImageName, contactName, contactEmail, contactPhone);
    }
}
