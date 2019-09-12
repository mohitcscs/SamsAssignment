package com.test.samsapplication.ui.utils.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.test.samsapplication.views.ProductDetailsActivity;
import com.test.samsapplication.R;
import com.test.samsapplication.datamodels.Product;
import com.test.samsapplication.network.NetworkUtils;
import com.test.samsapplication.ui.utils.UIHelper;

import java.util.ArrayList;

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Product> mValues;
    public ProductsRecyclerViewAdapter(ArrayList<Product> items)
    {
        this.mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        Product product = mValues.get(position);
        holder.mItem = product;

        holder.productName.setText(holder.mItem.getProductName());
        float rating = holder.mItem.getReviewRating();
        if(rating > 0) {
            holder.productRating.setVisibility(View.VISIBLE);
            holder.productRating.setRating(holder.mItem.getReviewRating());
        } else {
            holder.productRating.setVisibility(View.INVISIBLE);
        }
        holder.productPrice.setText(holder.mItem.getPrice());
        holder.stockStatus.setText(holder.mItem.getInStock() ? R.string.in_stock : R.string.out_of_stock);
        String imageURL = String.format(NetworkUtils.PRODUCT_IMAGE_API, holder.mItem.getProductImage());
        try {
            Picasso.get().load(imageURL).into(holder.productImage);
        }
        catch (Exception ex)
        {
            Log.d("onBindViewHolder", "Image downloading issue");
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchDetailsScreen(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        TextView productName;
        TextView stockStatus;
        TextView productPrice;
        RatingBar productRating;
        ImageView productImage;
        Product mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            productName = mView.findViewById(R.id.product_name);
            productPrice = mView.findViewById(R.id.product_price);
            productImage = mView.findViewById(R.id.product_image);
            productRating = mView.findViewById(R.id.product_rating);
            stockStatus = mView.findViewById(R.id.stock_status);
        }
    }

    /**
     * Launch details screen
     * @param view View object for context
     * @param position position for the selected item.
     */
    private void launchDetailsScreen(View view, int position)
    {
        Intent intent = new Intent(view.getContext(), ProductDetailsActivity.class);
        intent.putExtra(UIHelper.BUNDLE_PRODUCT_KEY, position);
        view.getContext().startActivity(intent);

    }
}
