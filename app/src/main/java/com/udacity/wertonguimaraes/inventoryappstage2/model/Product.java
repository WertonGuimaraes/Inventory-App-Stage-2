package com.udacity.wertonguimaraes.inventoryappstage2.model;

import android.graphics.Bitmap;

public class Product {
    private Integer mId;
    private String mProductName;
    private double mProductPrice;
    private int mProductQuantity;
    private Bitmap mProductImage;
    private String mContactName;
    private String mContactEmail;
    private String mContactPhone;

    public Product(Integer id, String productName, double productPrice, int productQuantity,
                   Bitmap productImage, String contactName, String contactEmail,
                   String contactPhone) {
        mId = id;
        mProductName = productName;
        mProductPrice = productPrice;
        mProductQuantity = productQuantity;
        mProductImage = productImage;
        mContactName = contactName;
        mContactEmail = contactEmail;
        mContactPhone = contactPhone;
    }

    public Integer getId() {
        return mId;
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

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public void setProductPrice(double productPrice) {
        mProductPrice = productPrice;
    }

    public void setProductImage(Bitmap productImage) {
        mProductImage = productImage;
    }

    public void setProductQuantity(int productQuantity) {
        mProductQuantity = productQuantity;
    }

    public void setContactName(String contactName) {
        mContactName = contactName;
    }

    public void setContactEmail(String contactEmail) {
        mContactEmail = contactEmail;
    }

    public void setContactPhone(String contactPhone) {
        mContactPhone = contactPhone;
    }
}