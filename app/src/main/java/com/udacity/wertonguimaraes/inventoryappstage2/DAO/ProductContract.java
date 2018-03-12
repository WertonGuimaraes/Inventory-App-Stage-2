package com.udacity.wertonguimaraes.inventoryappstage2.DAO;

import android.provider.BaseColumns;

/**
 * Created by wertonguimaraes on 31/01/18.
 */

public final class ProductContract {

    private ProductContract() {
    }

    public static class ContractEntry implements BaseColumns {
        public static final String TABLE_NAME = "items";

        public static final String COLUMN_PRODUCT_NAME = "productName";
        public static final String COLUMN_PRODUCT_PRICE = "productPrice";
        public static final String COLUMN_PRODUCT_QUANTITY = "productQuantity";
        public static final String COLUMN_PRODUCT_IMAGE = "productImage";

        public static final String COLUMN_CONTACT_NAME = "contactName";
        public static final String COLUMN_CONTACT_EMAIL = "contactEmail";
        public static final String COLUMN_CONTACT_PHONE = "contactPhone";

        public static final String SQL_CREATE_POSTS =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        ContractEntry._ID + " INTEGER PRIMARY KEY," +
                        ContractEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL," +
                        ContractEntry.COLUMN_PRODUCT_PRICE + " REAL NOT NULL," +
                        ContractEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL," +
                        ContractEntry.COLUMN_PRODUCT_IMAGE + " BLOB NOT NULL," +
                        ContractEntry.COLUMN_CONTACT_NAME + " TEXT NOT NULL," +
                        ContractEntry.COLUMN_CONTACT_EMAIL + " TEXT NOT NULL," +
                        ContractEntry.COLUMN_CONTACT_PHONE + " TEXT NOT NULL)";
    }
}