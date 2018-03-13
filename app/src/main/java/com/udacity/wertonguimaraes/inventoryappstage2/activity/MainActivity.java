package com.udacity.wertonguimaraes.inventoryappstage2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductDbHelper;
import com.udacity.wertonguimaraes.inventoryappstage2.R;
import com.udacity.wertonguimaraes.inventoryappstage2.adapter.ProductListAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRvItem;
    private ProductDbHelper dbHelper;
    private TextView mInfoNoProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatabase();
        initView();
        showData();
    }

    private void initDatabase() {
        dbHelper = new ProductDbHelper(getApplicationContext());
    }

    private void initView() {
        mRvItem = findViewById(R.id.recycler_view);
        mInfoNoProduct = findViewById(R.id.no_product_info);
    }

    private void populateRecyclerView() {
        mRvItem.setLayoutManager(new LinearLayoutManager(this));
        ProductListAdapter productAdapter = new ProductListAdapter(getApplicationContext(), dbHelper.getAllDataCursor());
        mRvItem.setAdapter(productAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showData();
    }

    private void showData() {
        if (dbHelper.getAllDataCursor().getCount() > 0) {
            mInfoNoProduct.setVisibility(View.GONE);
            populateRecyclerView();
        } else {
            mInfoNoProduct.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_acitvity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_add):
                startActivity(new Intent(getApplicationContext(), AddProductActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}