package com.udacity.wertonguimaraes.inventoryappstage1.DAO;

import android.provider.BaseColumns;

/**
 * Created by wertonguimaraes on 31/01/18.
 */

public final class Contract {

    private Contract() {
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
                        ContractEntry.COLUMN_PRODUCT_NAME + " TEXT," +
                        ContractEntry.COLUMN_PRODUCT_PRICE + " REAL," +
                        ContractEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER," +
                        ContractEntry.COLUMN_PRODUCT_IMAGE + " TEXT," +
                        ContractEntry.COLUMN_CONTACT_NAME + " TEXT," +
                        ContractEntry.COLUMN_CONTACT_EMAIL + " TEXT," +
                        ContractEntry.COLUMN_CONTACT_PHONE + " TEXT)";

        public static final String SQL_DELETE_POSTS =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}