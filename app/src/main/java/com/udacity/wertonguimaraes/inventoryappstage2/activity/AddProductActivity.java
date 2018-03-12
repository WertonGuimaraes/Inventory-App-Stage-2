package com.udacity.wertonguimaraes.inventoryappstage2.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductDbHelper;
import com.udacity.wertonguimaraes.inventoryappstage2.R;
import com.udacity.wertonguimaraes.inventoryappstage2.model.Product;

import java.io.FileNotFoundException;

public class AddProductActivity extends AppCompatActivity {

    protected EditText mProductName;
    protected EditText mProductPrice;
    protected EditText mProductQuantity;
    protected EditText mContactName;
    protected EditText mContactEmail;
    protected EditText mContactPhone;

    protected ImageView mProductImage;
    protected ImageView mEditProductImage;

    protected Button mAddItem;
    protected ProductDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        initView();
        initDatabase();
        initButtonListeners();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initDatabase() {
        mDbHelper = new ProductDbHelper(getApplicationContext());
    }

    protected void initView() {
        mProductName = findViewById(R.id.et_product_name);
        mProductPrice = findViewById(R.id.et_product_price);
        mProductQuantity = findViewById(R.id.et_product_quantity);
        mContactName = findViewById(R.id.et_contact_name);
        mContactEmail = findViewById(R.id.et_contact_email);
        mContactPhone = findViewById(R.id.et_contact_phone);

        mProductImage = findViewById(R.id.iv_product_image);
        mEditProductImage = findViewById(R.id.edit_product_image);

        mAddItem = findViewById(R.id.bt_save_item);
    }

    private void initButtonListeners() {
        mAddItem.setOnClickListener(mAddItemListener);
        mEditProductImage.setOnClickListener(mEditImageProductListener);
    }

    private View.OnClickListener mAddItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (allFieldsWasFilled()) {
                Product product = new Product(null,
                        mProductName.getText().toString(),
                        Double.parseDouble(mProductPrice.getText().toString()),
                        Integer.parseInt(mProductQuantity.getText().toString()),
                        ((BitmapDrawable) mProductImage.getDrawable()).getBitmap(),
                        mContactName.getText().toString(),
                        mContactEmail.getText().toString(),
                        mContactPhone.getText().toString());

                mDbHelper.insertItem(product);
                Toast.makeText(getApplicationContext(), "Item added successfully.", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                Toast.makeText(getApplicationContext(), "There are fields that need filled.", Toast.LENGTH_SHORT).show();
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

    protected boolean allFieldsWasFilled() {
        return isFilled(mProductName) && isFilled(mProductPrice) && isFilled(mProductQuantity) &&
                isFilled(mContactName) && isFilled(mContactEmail) && isFilled(mContactPhone);
    }

    private boolean isFilled(EditText field) {
        return !field.getText().toString().trim().isEmpty();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            if (data != null) {

                Uri targetUri = data.getData();
                Bitmap bitmap;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                    mProductImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}