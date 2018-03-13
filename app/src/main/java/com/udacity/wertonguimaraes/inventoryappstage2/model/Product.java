package com.udacity.wertonguimaraes.inventoryappstage2.model;

public class Product {
    private Integer mId;
    private String mProductName;
    private double mProductPrice;
    private int mProductQuantity;
    private String mProductImageName;
    private String mContactName;
    private String mContactEmail;
    private String mContactPhone;

    public Product(Integer id, String productName, double productPrice, int productQuantity,
                   String productImageName, String contactName, String contactEmail,
                   String contactPhone) {
        mId = id;
        mProductName = productName;
        mProductPrice = productPrice;
        mProductQuantity = productQuantity;
        mProductImageName = productImageName;
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

    public String getProductImageName() {
        return mProductImageName;
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