package com.udacity.wertonguimaraes.inventoryappstage1.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.udacity.wertonguimaraes.inventoryappstage1.DAO.ProductDbHelper;
import com.udacity.wertonguimaraes.inventoryappstage1.R;

public class MainActivity extends AppCompatActivity {

    private TextView mTotalItems;
    private Button mAddItems;
    private ProductDbHelper dbHelper;
    private Cursor mCursorAllItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInitDatabase();
        mInitView();
        updateTotalItemsTextView();
        mInitButtonListeners();
    }

    private void mInitDatabase() {
        dbHelper = new ProductDbHelper(getApplicationContext());
    }

    private void mInitView() {
        mTotalItems = findViewById(R.id.total_items);
        mAddItems = findViewById(R.id.add_item);
    }

    private void mInitButtonListeners() {
        mAddItems.setOnClickListener(mAddItemListener);
    }

    private View.OnClickListener mAddItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dbHelper.insertItem(
                    "Smart TV",
                    2999.99,
                    5,
                    "http://s3.amazonaws.com/digitaltrends-uploads-prod/2013/02/Samsung-Smart-Tv.jpg",
                    "Werton Guimaraes",
                    "werton007@gmail.com",
                    "+55 83 99652XXXX");
            updateTotalItemsTextView();
        }
    };

    private void updateTotalItemsTextView() {
        mCursorAllItems = dbHelper.getAllDataCursor();
        int totalItems = mCursorAllItems.getCount();
        mTotalItems.setText(String.valueOf(totalItems));
        mCursorAllItems.close();
    }
}