package com.udacity.wertonguimaraes.inventoryappstage2.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductContract;
import com.udacity.wertonguimaraes.inventoryappstage2.R;

/**
 * Created by wertonguimaraes on 22/02/18.
 */

public class ProductListAdapter extends CursorRecyclerViewAdapter<ProductListAdapter.ViewHolder> {
    private Context mContext;

    public ProductListAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_list, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        Resources res = mContext.getResources();

        String name = getCursorValue(cursor, ProductContract.ContractEntry.COLUMN_PRODUCT_NAME);
        String price = getCursorValue(cursor, ProductContract.ContractEntry.COLUMN_PRODUCT_PRICE);
        String quantity = getCursorValue(cursor, ProductContract.ContractEntry.COLUMN_PRODUCT_QUANTITY);
        byte[] productImage = cursor.getBlob(cursor.getColumnIndex(ProductContract.ContractEntry.COLUMN_PRODUCT_IMAGE));

        String price_text = String.format(res.getString(R.string.price), price);
        String quantity_text = String.format(res.getString(R.string.quantity), quantity);
        Bitmap bitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);

        viewHolder.productNameView.setText(name);
        viewHolder.productPriceView.setText(price_text);
        viewHolder.productQuantityView.setText(quantity_text);
        viewHolder.productImageView.setImageBitmap(bitmap);
    }

    private String getCursorValue(Cursor cursor, String column_name) {
        return cursor.getString(cursor.getColumnIndex(column_name));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView productNameView;
        protected TextView productPriceView;
        protected TextView productQuantityView;
        protected ImageView productImageView;

        public ViewHolder(View view) {
            super(view);
            this.productNameView = view.findViewById(R.id.product_name);
            this.productPriceView = view.findViewById(R.id.product_price);
            this.productQuantityView = view.findViewById(R.id.product_quantity);
            this.productImageView = view.findViewById(R.id.product_image);
        }
    }
}
