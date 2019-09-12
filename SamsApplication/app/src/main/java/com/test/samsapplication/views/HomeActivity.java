package com.test.samsapplication.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.samsapplication.R;
import com.test.samsapplication.datamodels.Product;
import com.test.samsapplication.datamodels.ProductsDataManager;
import com.test.samsapplication.ui.utils.UIHelper;
import com.test.samsapplication.ui.utils.RecyclerViewItemSpacingDecoration;
import com.test.samsapplication.ui.utils.RecyclerViewScrollListener;
import com.test.samsapplication.ui.utils.adapters.ProductsRecyclerViewAdapter;
import com.test.samsapplication.viewModel.HomeActivityViewModel;

import java.util.ArrayList;

/**
 * Home Launcher activity. This will load list of all assets.
 * ViewModel will store the data to save from config changes.
 */
public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private HomeActivityViewModel homeActivityViewModel;
    private LiveData<ArrayList<Product>> productsList;
    private RecyclerView productListRecyclerView;
    private ProgressBar progressbar;
    private ProgressBar lazyProgressBar;
    private TextView loadingTextView;

    private static final int PAGE_START = 1;
    private int TOTAL_PAGES = 1;
    private int currentPage = PAGE_START;
    private boolean isLoading = false;
    private boolean isLastPage = false;


    private ProductsRecyclerViewAdapter productsRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI();  //init the UI elements

        initViewModel(); //Init View model class

        productsList =  homeActivityViewModel.getProductsList(); //get the data from View model to save calls on config changes.
        if (productsList.getValue() == null)
        {
            getData(currentPage); // getData from API Call
        }

        //Set Live data observer to listen for any data change in back end.
        productsList.observe(this, new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products)
            {
                if (products!= null && products.size() > 0) {
                    loadingTextView.setVisibility(View.GONE);
                    notifyRecyclerView(products);
                    isLoading = false; //Set loading to false as data has been received.
                }
                else
                {
                    progressbar.setVisibility(View.GONE);
                    loadingTextView.setText("Error occurred, please try again later.");
                }
            }
        });
    }

    /**
     * Load UI elements here.
     */
    private void initializeUI(){
        loadingTextView = findViewById(R.id.textView);
        productListRecyclerView = findViewById(R.id.recyclerView);
        progressbar = findViewById(R.id.progressbar);
        progressbar.setVisibility(View.VISIBLE);

        lazyProgressBar = findViewById(R.id.lazyProgressBar);
        lazyProgressBar.setVisibility(View.INVISIBLE);

        //Set the layout manager on tablet or small devices.
        if(UIHelper.isLargeDevice(getApplicationContext()))
        {
            productListRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), UIHelper.XLARGE_SCREEN__LIST_COLUMN_COUNT));
        } else {
            productListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }
        RecyclerView.ItemDecoration dividerItemDecoration = new RecyclerViewItemSpacingDecoration(getApplicationContext());
        productListRecyclerView.addItemDecoration(dividerItemDecoration);
        productListRecyclerView.addOnScrollListener(mRecyclerViewScrollListener);
    }

    private RecyclerViewScrollListener mRecyclerViewScrollListener = new RecyclerViewScrollListener(getBaseContext()) {
        @Override
        protected void loadMoreItems() {
            isLoading = true;
            currentPage += 1; //Increment page number from the last page
            if(currentPage <= getTotalPageCount()) {
                Log.d(TAG, "Load data for Page No " +currentPage);
                getData(currentPage);
            }
        }

        @Override
        public int getTotalPageCount() {
            return TOTAL_PAGES;
        }

        @Override
        public boolean isLastPage() {
            return isLastPage;
        }

        @Override
        public boolean isLoading() {
            return isLoading;
        }
    };

    /**
     * Notify recycler view with the new data.
     */
    private void notifyRecyclerView(ArrayList<Product> products)
    {
        if (productsRecyclerViewAdapter == null)
        {
            productsRecyclerViewAdapter = new ProductsRecyclerViewAdapter(products);
            productListRecyclerView.setAdapter(productsRecyclerViewAdapter);

            int totalProducts = ProductsDataManager.getSingleTonInstance().getTotalNumberOfProducts();
            TOTAL_PAGES = (int) Math.ceil((double) totalProducts / (double) UIHelper.PRODUCT_LIST_PAGE_SIZE);
        }
        else
        {
            productsRecyclerViewAdapter.notifyDataSetChanged();
        }
        progressbar.setVisibility(View.GONE);
        lazyProgressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Init the view model associated the activity.
     * Jetpack MVVM architecture component.
     */
    private void initViewModel()
    {
        homeActivityViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);
    }

    /**
     * Get api call data here.
     */
    private void getData(int pageNumber)
    {
        if (currentPage != PAGE_START) //Show lazy loading progress bar only if page number is greater than 1.
        {
            lazyProgressBar.setVisibility(View.VISIBLE);
        }
        else {
            isLoading = false;
        }
        homeActivityViewModel.fetchData(pageNumber);
    }
}
