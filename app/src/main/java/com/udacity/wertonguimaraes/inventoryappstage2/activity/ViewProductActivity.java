package com.udacity.wertonguimaraes.inventoryappstage2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.udacity.wertonguimaraes.inventoryappstage2.R;

public class ViewProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        mInitView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void mInitView() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}