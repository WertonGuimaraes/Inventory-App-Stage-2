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
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductDbHelper;
import com.udacity.wertonguimaraes.inventoryappstage2.R;
import com.udacity.wertonguimaraes.inventoryappstage2.model.Product;
import com.udacity.wertonguimaraes.inventoryappstage2.util.Convert;

public class ViewProductActivity extends AppCompatActivity {

    private static final String PRODUCT_POSITION = "productPosition";

    private TextView mProductName;
    private TextView mProductPrice;
    private TextView mProductQuantity;
    private TextView mContactName;
    private TextView mContactEmail;
    private TextView mContactPhone;

    private ImageView mProductImage;

    private ProductDbHelper dbHelper;

    public static void start(Context context, int productPosition) {
        Intent intent = new Intent(context, ViewProductActivity.class);
        intent.putExtra(PRODUCT_POSITION, productPosition);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        mInitDatabase();
        mInitView();
        mPopulateView(getCurrentProduct());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void mInitDatabase() {
        dbHelper = new ProductDbHelper(getApplicationContext());
    }

    private void mInitView() {
        mProductName = findViewById(R.id.tv_product_name);
        mProductPrice = findViewById(R.id.tv_product_price);
        mProductQuantity = findViewById(R.id.tv_product_quantity);
        mContactName = findViewById(R.id.tv_contact_name);
        mContactEmail = findViewById(R.id.tv_contact_email);
        mContactPhone = findViewById(R.id.tv_contact_phone);
        mProductImage = findViewById(R.id.iv_product_image);
    }

    private void mPopulateView(Product product) {
        Resources res = getApplicationContext().getResources();
        String price_text = String.format(res.getString(R.string.price_text), product.getProductPrice());
        String quantity_text = String.format(res.getString(R.string.quantity_text), product.getProductQuantity());
        String contact_name_text = String.format(res.getString(R.string.contact_name_text), product.getContactName());
        String contact_email_text = String.format(res.getString(R.string.contact_email_text), product.getContactEmail());
        String contact_phone_text = String.format(res.getString(R.string.contact_phone_text), product.getContactPhone());

        mProductName.setText(product.getProductName());
        mProductPrice.setText(price_text);
        mProductQuantity.setText(quantity_text);
        mContactName.setText(contact_name_text);
        mContactEmail.setText(contact_email_text);
        mContactPhone.setText(contact_phone_text);
        mProductImage.setImageBitmap(product.getProductImage());
    }

    private Product getCurrentProduct() {
        Bundle data = getIntent().getExtras();
        int productPosition = data.getInt(PRODUCT_POSITION);

        Cursor cursor = dbHelper.getAllDataCursor();
        cursor.moveToPosition(productPosition);
        return Convert.cursorInProduct(cursor);
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
            case (R.id.action_add):
                startActivity(new Intent(getApplicationContext(), AddProductActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}