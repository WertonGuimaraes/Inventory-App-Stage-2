package com.udacity.wertonguimaraes.inventoryappstage2.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductDbHelper;
import com.udacity.wertonguimaraes.inventoryappstage2.R;
import com.udacity.wertonguimaraes.inventoryappstage2.model.Product;
import com.udacity.wertonguimaraes.inventoryappstage2.util.Convert;

public class ViewProductActivity extends AppCompatActivity {

    private static final String PRODUCT_ID = "productPosition";

    private TextView mProductName;
    private TextView mProductPrice;
    private TextView mProductQuantity;
    private TextView mContactName;
    private TextView mContactEmail;
    private TextView mContactPhone;

    private ImageView mProductImage;

    private ImageButton mAddProductQuantity;
    private ImageButton mRemoveProductQuantity;

    private Product mProduct;
    private ProductDbHelper mDbHelper;

    public static void start(Context context, int productPosition) {
        Intent intent = new Intent(context, ViewProductActivity.class);
        intent.putExtra(PRODUCT_ID, productPosition);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        initDatabase();
        initView();
        convertCursorToProduct();
        initButtonListeners();
        populateView();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initDatabase() {
        mDbHelper = new ProductDbHelper(getApplicationContext());
    }

    private void initView() {
        mProductName = findViewById(R.id.tv_product_name);
        mProductPrice = findViewById(R.id.tv_product_price);
        mProductQuantity = findViewById(R.id.tv_product_quantity);
        mContactName = findViewById(R.id.tv_contact_name);
        mContactEmail = findViewById(R.id.tv_contact_email);
        mContactPhone = findViewById(R.id.tv_contact_phone);
        mProductImage = findViewById(R.id.iv_product_image);
        mAddProductQuantity = findViewById(R.id.ib_add_product_quantity);
        mRemoveProductQuantity = findViewById(R.id.ib_remove_product_quantity);
    }

    private void initButtonListeners() {
        mAddProductQuantity.setOnClickListener(mAddProductQuantityListener);
        mRemoveProductQuantity.setOnClickListener(mRemoveProductQuantityListener);
    }

    private View.OnClickListener mAddProductQuantityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int newQuantity = mProduct.getProductQuantity() + 1;
            updateQuantity(newQuantity);
        }
    };

    private View.OnClickListener mRemoveProductQuantityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int newQuantity = mProduct.getProductQuantity() - 1;
            if (newQuantity >= 0) {
                updateQuantity(newQuantity);
            } else {
                Toast.makeText(getApplicationContext(), "nao", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void updateQuantity(int quantity) {
        mDbHelper.updateItemQuantity(mProduct.getId(), quantity);
        mProduct.setProductQuantity(quantity);
        String quantityText = String.format(getString(R.string.quantity_text), quantity);
        mProductQuantity.setText(quantityText);
    }

    private void populateView() {
        Resources res = getApplicationContext().getResources();
        String priceText = String.format(res.getString(R.string.price_text), mProduct.getProductPrice());
        String quantityText = String.format(res.getString(R.string.quantity_text), mProduct.getProductQuantity());
        String contactNameText = String.format(res.getString(R.string.contact_name_text), mProduct.getContactName());
        String contactEmailText = String.format(res.getString(R.string.contact_email_text), mProduct.getContactEmail());
        String contactPhoneText = String.format(res.getString(R.string.contact_phone_text), mProduct.getContactPhone());

        mProductName.setText(mProduct.getProductName());
        mProductPrice.setText(priceText);
        mProductQuantity.setText(quantityText);
        mContactName.setText(contactNameText);
        mContactEmail.setText(contactEmailText);
        mContactPhone.setText(contactPhoneText);
        mProductImage.setImageBitmap(mProduct.getProductImage());
    }

    private void convertCursorToProduct() {
        Bundle data = getIntent().getExtras();
        int productPosition = data.getInt(PRODUCT_ID);
        Cursor cursor = mDbHelper.getProductCursorById(productPosition);
        mProduct = Convert.cursorInProduct(cursor);
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_product_acitvity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (android.R.id.home):
                onBackPressed();
                return true;
            case (R.id.action_edit):
                EditProductActivity.start(getApplicationContext(), mProduct.getId());
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        convertCursorToProduct();
        populateView();
    }
}