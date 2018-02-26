package com.udacity.wertonguimaraes.inventoryappstage2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductDbHelper;
import com.udacity.wertonguimaraes.inventoryappstage2.R;

public class AddItemActivity extends AppCompatActivity {

    private EditText mProductName;
    private EditText mProductPrice;
    private EditText mProductQuantity;
    private EditText mProductImage;
    private EditText mContactName;
    private EditText mContactEmail;
    private EditText mContactPhone;

    private Button mAddItem;
    private ProductDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mInitView();
        mInitDatabase();
        mInitButtonListeners();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void mInitDatabase() {
        dbHelper = new ProductDbHelper(getApplicationContext());
    }

    private void mInitView() {
        mProductName = findViewById(R.id.et_product_name);
        mProductPrice = findViewById(R.id.et_product_price);
        mProductQuantity = findViewById(R.id.et_product_quantity);
        mProductImage = findViewById(R.id.et_product_image);
        mContactName = findViewById(R.id.et_contact_name);
        mContactEmail = findViewById(R.id.et_contact_email);
        mContactPhone = findViewById(R.id.et_contact_phone);

        mAddItem = findViewById(R.id.bt_save_item);
    }

    private void mInitButtonListeners() {
        mAddItem.setOnClickListener(mAddItemListener);
    }

    private View.OnClickListener mAddItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (allFieldsWasFilled()) {
                dbHelper.insertItem(
                        mProductName.getText().toString(),
                        Double.parseDouble(mProductPrice.getText().toString()),
                        Integer.parseInt(mProductQuantity.getText().toString()),
                        mProductImage.getText().toString(),
                        mContactName.getText().toString(),
                        mContactEmail.getText().toString(),
                        mContactPhone.getText().toString());
                Toast.makeText(getApplicationContext(), "Item added successfully.", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                Toast.makeText(getApplicationContext(), "There are fields that need filled.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private boolean allFieldsWasFilled(){
        return isFilled(mProductName) && isFilled(mProductPrice) && isFilled(mProductQuantity) &&
                isFilled(mProductPrice) && isFilled(mContactName) && isFilled(mContactEmail) &&
                isFilled(mContactPhone);
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
}