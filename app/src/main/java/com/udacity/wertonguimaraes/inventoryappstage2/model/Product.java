package com.udacity.wertonguimaraes.inventoryappstage2.model;

import android.graphics.Bitmap;

public class Product {
    private String mProductName;
    private double mProductPrice;
    private int mProductQuantity;
    private Bitmap mProductImage;
    private String mContactName;
    private String mContactEmail;
    private String mContactPhone;

    public Product(String productName, double productPrice, int productQuantity,
                   Bitmap productImage, String contactName, String contactEmail,
                   String contactPhone) {
        mProductName = productName;
        mProductPrice = productPrice;
        mProductQuantity = productQuantity;
        mProductImage = productImage;
        mContactName = contactName;
        mContactEmail = contactEmail;
        mContactPhone = contactPhone;
    }

    public String getProductName() {
        return mProductName;
    }

    public double getProductPrice() {
        return mProductPrice;
    }

    public int getProductQuantity() {
        return mProductQuantity;
    }

    public Bitmap getProductImage() {
        return mProductImage;
    }

    public String getContactName() {
        return mContactName;
    }

    public String getContactEmail() {
        return mContactEmail;
    }

    public String getContactPhone() {
        return mContactPhone;
    }
}