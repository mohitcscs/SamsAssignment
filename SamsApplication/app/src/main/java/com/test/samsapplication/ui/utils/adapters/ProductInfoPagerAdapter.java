package com.test.samsapplication.ui.utils.adapters;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;
import com.test.samsapplication.R;
import com.test.samsapplication.datamodels.Product;
import com.test.samsapplication.datamodels.ProductsDataManager;
import com.test.samsapplication.network.NetworkUtils;
import com.test.samsapplication.ui.utils.UIHelper;

public class ProductInfoPagerAdapter  extends PagerAdapter {
    private TextView productName;
    private TextView productDescription;
    private TextView price;
    private RatingBar ratingBar;
    private ImageView productImage;
    private Context mContext;

    private Product product;

    /**
     * @return the number of pages to display
     */
    @Override
    public int getCount() {
        return ProductsDataManager.getSingleTonInstance().getProductsList().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    /**
     * Instantiate the {@link View} which should be displayed at {@code position}. Here we
     * inflate a layout from the apps resources and then change the text view to signify the position.
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // Inflate a new layout from our resources
        mContext = container.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_details_viewpager_item,
                container, false);
        // Add the newly created View to the ViewPager
        container.addView(view);
        initializeUI(view);
        product = ProductsDataManager.getSingleTonInstance().getProductsList().get(position);
        loadDataOnUI();
        // Return the View
        return view;
    }

    /**
     * Destroy the item from the {}. In our case this is simply removing the
     * {@link View}.
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    // Retrieve UI widgets from xml
    private void initializeUI(View view){
        productName = view.findViewById(R.id.product_name);
        ratingBar = view.findViewById(R.id.product_rating);
        productDescription = view.findViewById(R.id.product_desc);
        productImage = view.findViewById(R.id.product_image);
        price = view.findViewById(R.id.price);
    }

    //Show product info.
    private void loadDataOnUI(){
        productName.setText(product.getProductName());
        ratingBar.setRating(product.getReviewRating());
        updateProductDescription();
        updateStockStatusAndPrice();
        String imageURL = String.format(NetworkUtils.PRODUCT_IMAGE_API, product.getProductImage());
        try {
            Picasso.get().load(imageURL).into(productImage);
        }
        catch (Exception ex)
        {
            Log.d("onBindViewHolder", "Image downloading issue");
        }
    }

    // Update the Stock Status along with price using spannable text.
    private void updateStockStatusAndPrice(){
        StringBuilder stockAndPrice = new StringBuilder();
        String stockStatus = product.getInStock() ? mContext.getResources().getString(R.string.in_stock) : mContext.getResources().getString(R.string.out_of_stock);
        stockAndPrice.append(stockStatus);
        stockAndPrice.append("  ");
        stockAndPrice.append(product.getPrice());
        SpannableString spannableString = new SpannableString(stockAndPrice.toString());
        spannableString.setSpan(new TextAppearanceSpan(mContext, android.R.style.TextAppearance_Small), 0, stockStatus.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        price.setText(spannableString,TextView.BufferType.SPANNABLE);
    }

    // Update the product description with bold title.
    // Decorate the UI as much as we can adding new lines.
    private void updateProductDescription(){
        StringBuilder desc = new StringBuilder();
        desc.append(mContext.getResources().getString(R.string.product_description));
        desc.append("\n\n");
        desc.append(UIHelper.getSpannedText(product.getShortDescription()));
        desc.append("\n");
        desc.append(UIHelper.getSpannedText(product.getLongDescription()));

        SpannableString spannableString = new SpannableString(desc.toString());
        // spannableString.setSpan(new StyleSpan(Typeface.BOLD),0,20,0);
        spannableString.setSpan(new TextAppearanceSpan(mContext, android.R.style.TextAppearance_Large), 0, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        productDescription.setText(spannableString,TextView.BufferType.SPANNABLE);
    }

}

