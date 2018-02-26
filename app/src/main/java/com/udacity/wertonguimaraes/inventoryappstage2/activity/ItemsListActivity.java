package com.udacity.wertonguimaraes.inventoryappstage2.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductDbHelper;
import com.udacity.wertonguimaraes.inventoryappstage2.R;
import com.udacity.wertonguimaraes.inventoryappstage2.adapter.ProductListAdapter;

public class ItemsListActivity extends AppCompatActivity {

    private RecyclerView mRvItem;
    private ProductListAdapter mProductAdapter;
    private TextView mTotalItems;
    private ProductDbHelper dbHelper;
    private Cursor mCursorAllItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        mInitDatabase();
        mInitView();
        populateRecyclerView();
        updateTotalItemsTextView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void mInitDatabase() {
        dbHelper = new ProductDbHelper(getApplicationContext());
    }

    private void mInitView() {
        mRvItem = findViewById(R.id.recycler_view);
        mTotalItems = findViewById(R.id.total_items);
    }

    private void populateRecyclerView() {
        mRvItem.setLayoutManager(new LinearLayoutManager(this));
        mProductAdapter = new ProductListAdapter(getApplicationContext(), dbHelper.getAllDataCursor());
        mRvItem.setAdapter(mProductAdapter);
    }

    private void updateTotalItemsTextView() {
        mCursorAllItems = dbHelper.getAllDataCursor();
        int totalItems = mCursorAllItems.getCount();
        mTotalItems.setText(String.valueOf(totalItems));
        mCursorAllItems.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}