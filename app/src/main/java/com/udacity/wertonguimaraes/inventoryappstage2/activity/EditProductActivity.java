package com.udacity.wertonguimaraes.inventoryappstage2.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.udacity.wertonguimaraes.inventoryappstage2.R;
import com.udacity.wertonguimaraes.inventoryappstage2.model.Product;
import com.udacity.wertonguimaraes.inventoryappstage2.util.Convert;

public class EditProductActivity extends AddProductActivity {

    private static final String PRODUCT_ID = "productPosition";
    private Product mProduct;

    public static void start(Context context, int productPosition) {
        Intent intent = new Intent(context, EditProductActivity.class);
        intent.putExtra(PRODUCT_ID, productPosition);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        convertCursorToProduct();
        populateView();
        initButtonListeners();
    }

    private void convertCursorToProduct() {
        Bundle data = getIntent().getExtras();
        int productId = data.getInt(PRODUCT_ID);
        Cursor cursor = dbHelper.getProductCursorById(productId);
        mProduct = Convert.cursorInProduct(cursor);
        cursor.close();
    }

    private void populateView() {
        productName.setText(mProduct.getProductName());
        productPrice.setText(String.valueOf(mProduct.getProductPrice()));
        productQuantity.setText(String.valueOf(mProduct.getProductQuantity()));
        contactName.setText(mProduct.getContactName());
        contactEmail.setText(mProduct.getContactEmail());
        contactPhone.setText(mProduct.getContactPhone());
        productImage.setImageBitmap(mProduct.getProductImage());
    }

    private void initButtonListeners() {
        editProductImage.setOnClickListener(mEditImageProductListener);
    }

    private View.OnClickListener mEditItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (allFieldsWasFilled()) {
                mProduct.setProductName(productName.getText().toString());
                mProduct.setProductPrice(Double.parseDouble(productPrice.getText().toString()));
                mProduct.setProductQuantity(Integer.parseInt(productQuantity.getText().toString()));
                mProduct.setProductImage(((BitmapDrawable) productImage.getDrawable()).getBitmap());
                mProduct.setContactName(contactName.getText().toString());
                mProduct.setContactEmail(contactEmail.getText().toString());
                mProduct.setContactPhone(contactPhone.getText().toString());

                dbHelper.updateItem(mProduct);
                Toast.makeText(getApplicationContext(), getString(R.string.product_edited_successfully), Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.info_product_not_complete), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener mEditImageProductListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 0);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_acitvity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (android.R.id.home):
                Toast.makeText(getApplicationContext()," aaa", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return true;
            case (R.id.action_edit):
                if (allFieldsWasFilled()) {
                    mProduct.setProductName(productName.getText().toString());
                    mProduct.setProductPrice(Double.parseDouble(productPrice.getText().toString()));
                    mProduct.setProductQuantity(Integer.parseInt(productQuantity.getText().toString()));
                    mProduct.setProductImage(((BitmapDrawable) productImage.getDrawable()).getBitmap());
                    mProduct.setContactName(contactName.getText().toString());
                    mProduct.setContactEmail(contactEmail.getText().toString());
                    mProduct.setContactPhone(contactPhone.getText().toString());

                    dbHelper.updateItem(mProduct);
                    Toast.makeText(getApplicationContext(), getString(R.string.product_edited_successfully), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.info_product_not_complete), Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}