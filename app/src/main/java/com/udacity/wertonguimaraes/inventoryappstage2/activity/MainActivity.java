package com.udacity.wertonguimaraes.inventoryappstage2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductDbHelper;
import com.udacity.wertonguimaraes.inventoryappstage2.R;

public class MainActivity extends AppCompatActivity {

    private Button mAddItems;
    private Button mListItems;
    private ProductDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInitDatabase();
        mInitView();
        mInitButtonListeners();
    }

    private void mInitDatabase() {
        dbHelper = new ProductDbHelper(getApplicationContext());
    }

    private void mInitView() {
        mAddItems = findViewById(R.id.add_item);
        mListItems = findViewById(R.id.view_items);
    }

    private void mInitButtonListeners() {
        mAddItems.setOnClickListener(mAddItemListener);
        mListItems.setOnClickListener(mListItemsListener);
    }

    private View.OnClickListener mAddItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), AddItemActivity.class));
//            dbHelper.insertItem(
//                    "Smart TV",
//                    2999.99,
//                    5,
//                    "http://s3.amazonaws.com/digitaltrends-uploads-prod/2013/02/Samsung-Smart-Tv.jpg",
//                    "Werton Guimaraes",
//                    "werton007@gmail.com",
//                    "+55 83 99652XXXX");
        }
    };

    private View.OnClickListener mListItemsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), ItemsListActivity.class));
        }
    };
}