package com.udacity.wertonguimaraes.inventoryappstage2.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductContract;
import com.udacity.wertonguimaraes.inventoryappstage2.R;

/**
 * Created by wertonguimaraes on 22/02/18.
 */

public class ProductListAdapter extends CursorRecyclerViewAdapter<ProductListAdapter.ViewHolder> {

    public ProductListAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_list, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        String name = getCursorValue(cursor, ProductContract.ContractEntry.COLUMN_PRODUCT_NAME);
        String price = getCursorValue(cursor, ProductContract.ContractEntry.COLUMN_PRODUCT_PRICE);
        String quantity = getCursorValue(cursor, ProductContract.ContractEntry.COLUMN_PRODUCT_QUANTITY);

        viewHolder.productNameView.setText(name);
        viewHolder.productPriceView.setText(price);
        viewHolder.productQuantityView.setText(quantity);
    }

    private String getCursorValue(Cursor cursor, String column_name) {
        return cursor.getString(cursor.getColumnIndex(column_name));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView productNameView;
        protected TextView productPriceView;
        protected TextView productQuantityView;


        public ViewHolder(View view) {
            super(view);
            this.productNameView = view.findViewById(R.id.product_name);
            this.productPriceView = view.findViewById(R.id.product_price);
            this.productQuantityView = view.findViewById(R.id.product_quantity);
        }
    }
}
