package com.test.samsapplication.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.test.samsapplication.apiCalls.ProductsListApi;
import com.test.samsapplication.datamodels.Product;
import com.test.samsapplication.datamodels.ProductsDataManager;
import com.test.samsapplication.network.OkHttpCallbacks;
import com.test.samsapplication.ui.utils.UIHelper;

import java.util.ArrayList;

/**
 * View model class for Home Activity to save the objects state.
 */
public class HomeActivityViewModel extends AndroidViewModel  implements OkHttpCallbacks
{
    private static final String TAG = HomeActivityViewModel.class.getSimpleName();

    private MutableLiveData<ArrayList<Product>> productsList;
    public MutableLiveData<ArrayList<Product>> getProductsList()
    {
        if (productsList == null)
        {
            productsList = new MutableLiveData<>();
        }
        return productsList;
    }

    public HomeActivityViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Get the data
     * @param pageNumber Page number to load into api call
     */
    public void fetchData(int pageNumber)
    {
        new ProductsListApi(pageNumber, UIHelper.PRODUCT_LIST_PAGE_SIZE, this).getData();
    }

    @Override
    public void onCallSuccess()
    {
        productsList.setValue(ProductsDataManager.getSingleTonInstance().getProductsList());
    }

    @Override
    public void onCallFailure(Exception ex) {
        Log.d(TAG, ex.getLocalizedMessage());
        productsList.setValue(null);
    }
}
