package com.udacity.wertonguimaraes.inventoryappstage2.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductContract;
import com.udacity.wertonguimaraes.inventoryappstage2.DAO.ProductDbHelper;
import com.udacity.wertonguimaraes.inventoryappstage2.R;
import com.udacity.wertonguimaraes.inventoryappstage2.activity.ViewProductActivity;
import com.udacity.wertonguimaraes.inventoryappstage2.model.Product;
import com.udacity.wertonguimaraes.inventoryappstage2.util.Convert;
import com.udacity.wertonguimaraes.inventoryappstage2.util.Image;

import java.util.List;

/**
 * Created by wertonguimaraes on 22/02/18.
 */

public class ProductListAdapter extends CursorRecyclerViewAdapter<ProductListAdapter.ViewHolder> {
    private Context mContext;
    private Image image;

    public ProductListAdapter(Context context, Cursor cursor) {
        super(cursor);
        mContext = context;
        image = new Image(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor) {
        final Resources res = mContext.getResources();
        final Product product = Convert.cursorInProduct(cursor);

        String priceText = String.format(res.getString(R.string.price_text), product.getProductPrice());
        final String quantityText = String.format(res.getString(R.string.quantity_text), product.getProductQuantity());

        viewHolder.productNameView.setText(product.getProductName());
        viewHolder.productPriceView.setText(priceText);
        viewHolder.productQuantityView.setText(quantityText);
        image.loadImageFromStorage(product.getProductImageName(), viewHolder.productImageView);
        viewHolder.productSale.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProductDbHelper dbHelper = new ProductDbHelper(mContext);
                        int quantity = product.getProductQuantity();
                        Integer cursorId = product.getId();

                        if (--quantity >= 0) {
                            dbHelper.updateItemQuantity(cursorId, quantity);
                            product.setProductQuantity(quantity);
                            String quantityText = String.format(res.getString(R.string.quantity_text), quantity);
                            viewHolder.productQuantityView.setText(quantityText);
                        } else {
                            Toast.makeText(mContext, res.getString(R.string.quantity_less_than_zero), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView productNameView;
        protected TextView productPriceView;
        protected TextView productQuantityView;
        protected ImageView productImageView;
        protected ImageButton productSale;

        public ViewHolder(View view) {
            super(view);
            productNameView = view.findViewById(R.id.product_name);
            productPriceView = view.findViewById(R.id.product_price);
            productQuantityView = view.findViewById(R.id.product_quantity);
            productImageView = view.findViewById(R.id.product_image);
            productSale = view.findViewById(R.id.sale_product);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Cursor cursor = getCursor();
            cursor.moveToPosition(getLayoutPosition());
            int productId = cursor.getInt(cursor.getColumnIndex(ProductContract.ContractEntry._ID));
            ViewProductActivity.start(mContext, productId);
        }
    }
}
