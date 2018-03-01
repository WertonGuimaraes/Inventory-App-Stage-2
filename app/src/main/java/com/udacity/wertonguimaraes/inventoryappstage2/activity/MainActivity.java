package com.udacity.wertonguimaraes.inventoryappstage2.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductDbHelper;
import com.udacity.wertonguimaraes.inventoryappstage2.R;
import com.udacity.wertonguimaraes.inventoryappstage2.adapter.ProductListAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRvItem;
    private ProductListAdapter mProductAdapter;
    private TextView mTotalItems;
    private ProductDbHelper dbHelper;
    private Cursor mCursorAllItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInitDatabase();
        mInitView();
        populateRecyclerView();
        updateTotalItemsTextView();
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
    protected void onResume() {
        super.onResume();
        populateRecyclerView();
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