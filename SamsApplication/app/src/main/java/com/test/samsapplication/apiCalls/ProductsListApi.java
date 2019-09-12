package com.test.samsapplication.apiCalls;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.test.samsapplication.datamodels.ProductResultsData;
import com.test.samsapplication.datamodels.ProductsDataManager;
import com.test.samsapplication.network.NetworkUtils;
import com.test.samsapplication.network.OkHttpCallbacks;
import com.test.samsapplication.network.OkHttpClientImpl;
import com.test.samsapplication.parser.ParserListener;
import com.test.samsapplication.parser.ProductsJSONParserTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class ProductsListApi implements ParserListener
{
    private static final String TAG = ProductsListApi.class.getSimpleName();

    private int pageNumber;
    private int numOfObjects;
    private String responseStr;

    private OkHttpCallbacks okHttpCallbacks;

    /**
     * Receive the page number and num of Objects to load into api call
     * @param pageNumber Page number to load
     * @param numOfObjects Number of objects to be loaded in the page number
     * @param okHttpCallbacks Callback listener for results of api call
     */
    public ProductsListApi(int pageNumber, int numOfObjects, OkHttpCallbacks okHttpCallbacks)
    {
        this.pageNumber = pageNumber;
        this.numOfObjects = numOfObjects;
        this.okHttpCallbacks = okHttpCallbacks;
    }

    /**
     * Get data from the server using Okhttp new call method which will run on new thread.
     * Pass the url with required params, once response is success, parse using GSON.
     * and notify client that data is ready.
     */
    public void getData() {

        try {
            URL url = new URL(String.format(NetworkUtils.PRODUCT_API, pageNumber, numOfObjects));

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            //Enqueue will take it to worker thread
            OkHttpClientImpl.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull final IOException e)
                {
                    Log.d(TAG, "Response Failed");
                    e.printStackTrace();

                    //This can be handled by moving the entire code into Async task or any mechanism for taking response from worker
                    // to main thread.
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Pass the callback back to calling class
                            okHttpCallbacks.onCallFailure(e);
                        }
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    if (response.isSuccessful()) {
                        Log.d(TAG, "Response Success");
                        responseStr = response.body().string();
                        if (okHttpCallbacks != null)
                        {
                            // GSON sample parsing.
                            //ProductResultsData productResultsData = GSONUtil.getInstance().getGSONObject().fromJson(responseStr, ProductResultsData.class);
                            //onParseSuccess(productResultsData);

                            //Manual parsing
                            //Parsing done under Async task, final call will go back to Main thread from onPost execute.
                            new ProductsJSONParserTask(ProductsListApi.this, responseStr).execute();
                        }
                    }
                }
            });
        }
        catch (MalformedURLException ex)
        {
            Log.d(TAG, ex.getLocalizedMessage());
        }
    }

    @Override
    public void onParseSuccess(ProductResultsData data) {

        if (data != null &&
                okHttpCallbacks != null)
        {
            //Set data into Singleton for next screen viewpager left right rotation.
            ProductsDataManager.getSingleTonInstance().setTotalNumberOfProducts(data.getTotalProducts());
            ProductsDataManager.getSingleTonInstance().setProductsList(data.getProducts());
            okHttpCallbacks.onCallSuccess();
        }
    }

    @Override
    public void onParseFailure() {
        if (okHttpCallbacks != null)
        {
            okHttpCallbacks.onCallFailure(new Exception("Parsing Failed"));
        }
    }
}
