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

    protected EditText productName;
    protected EditText productPrice;
    protected EditText productQuantity;
    protected EditText contactName;
    protected EditText contactEmail;
    protected EditText contactPhone;

    protected ImageView productImage;
    protected ImageView editProductImage;

    protected Button addItem;
    protected ProductDbHelper dbHelper;

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
        dbHelper = new ProductDbHelper(getApplicationContext());
    }

    protected void initView() {
        productName = findViewById(R.id.et_product_name);
        productPrice = findViewById(R.id.et_product_price);
        productQuantity = findViewById(R.id.et_product_quantity);
        contactName = findViewById(R.id.et_contact_name);
        contactEmail = findViewById(R.id.et_contact_email);
        contactPhone = findViewById(R.id.et_contact_phone);

        productImage = findViewById(R.id.iv_product_image);
        editProductImage = findViewById(R.id.edit_product_image);

        addItem = findViewById(R.id.bt_save_item);
    }

    private void initButtonListeners() {
        addItem.setOnClickListener(addItemListener);
        editProductImage.setOnClickListener(editImageProductListener);
    }

    private View.OnClickListener addItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (allFieldsWasFilled()) {
                Product product = new Product(null,
                        productName.getText().toString(),
                        Double.parseDouble(productPrice.getText().toString()),
                        Integer.parseInt(productQuantity.getText().toString()),
                        ((BitmapDrawable) productImage.getDrawable()).getBitmap(),
                        contactName.getText().toString(),
                        contactEmail.getText().toString(),
                        contactPhone.getText().toString());

                dbHelper.insertItem(product);
                Toast.makeText(getApplicationContext(), getString(R.string.product_added_successfully), Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.info_product_not_complete), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener editImageProductListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 0);
        }
    };

    protected boolean allFieldsWasFilled() {
        return isFilled(productName) && isFilled(productPrice) && isFilled(productQuantity) &&
                isFilled(contactName) && isFilled(contactEmail) && isFilled(contactPhone);
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
                    productImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}