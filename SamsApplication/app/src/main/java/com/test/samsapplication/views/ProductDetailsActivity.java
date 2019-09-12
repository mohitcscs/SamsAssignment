package com.test.samsapplication.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.test.samsapplication.R;

public class ProductDetailsActivity extends AppCompatActivity {

    private static final String TAG = ProductDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ProductsInfoSlidingFragment fragment = new ProductsInfoSlidingFragment();
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();

        getSupportActionBar().setTitle("Product Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return true;
    }
}
