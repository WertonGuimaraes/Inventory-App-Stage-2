package com.udacity.wertonguimaraes.inventoryappstage2.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.wertonguimaraes.inventoryappstage2.R;
import com.udacity.wertonguimaraes.inventoryappstage2.activity.ViewProductActivity;
import com.udacity.wertonguimaraes.inventoryappstage2.model.Product;
import com.udacity.wertonguimaraes.inventoryappstage2.util.Convert;

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_list, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        Resources res = mContext.getResources();
        Product product = Convert.cursorInProduct(cursor);

        String price_text = String.format(res.getString(R.string.price_text), product.getProductPrice());
        String quantity_text = String.format(res.getString(R.string.quantity_text), product.getProductQuantity());

        viewHolder.productNameView.setText(product.getProductName());
        viewHolder.productPriceView.setText(price_text);
        viewHolder.productQuantityView.setText(quantity_text);
        viewHolder.productImageView.setImageBitmap(product.getProductImage());
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView productNameView;
        protected TextView productPriceView;
        protected TextView productQuantityView;
        protected ImageView productImageView;

        public ViewHolder(View view) {
            super(view);
            productNameView = view.findViewById(R.id.product_name);
            productPriceView = view.findViewById(R.id.product_price);
            productQuantityView = view.findViewById(R.id.product_quantity);
            productImageView = view.findViewById(R.id.product_image);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ViewProductActivity.start(mContext, getLayoutPosition());
        }
    }
}
