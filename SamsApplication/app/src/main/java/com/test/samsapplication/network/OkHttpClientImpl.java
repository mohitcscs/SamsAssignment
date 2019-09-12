package com.test.samsapplication.network;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * OkHttp library for making api calls.
 */
public class OkHttpClientImpl
{
    private static final String TAG = OkHttpClientImpl.class.getSimpleName();

    private OkHttpClient okHttpClient;
    private static OkHttpClientImpl okHttpClientImpl;

    public static OkHttpClientImpl getInstance()
    {
        if (okHttpClientImpl == null)
        {
            okHttpClientImpl = new OkHttpClientImpl();
        }
        return okHttpClientImpl;
    }

    public OkHttpClient getOkHttpClient() {
        if(okHttpClient == null)
        {
            Log.d(TAG,"okHttpClient is null so create first");
            clearAndCreateOkhttpClient();
        }
        return okHttpClient;
    }

    private void clearAndCreateOkhttpClient(){
        //We should create a new Client, instead of using the older one to avoid OkHttp TCP window full error
        if(okHttpClient != null)
        {
            //Close all the ideal connection
            okHttpClient.dispatcher().executorService().shutdown();
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    okHttpClient.connectionPool().evictAll();
                }
            });
            thread.start();

            try {
                if(okHttpClient.cache() != null) {
                    okHttpClient.cache().close();
                }
            } catch (IOException ex) {
                Log.d(TAG, ex.getLocalizedMessage());
            }
        }
        okHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(NetworkUtils.HTTP_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(NetworkUtils.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(NetworkUtils.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .followSslRedirects(true)
                .eventListener(new OkHttpEventListener()) //Helper Event listener when working directly with new calls instead of queue mechanism
                .followRedirects(true)
                .retryOnConnectionFailure(true);

        okHttpClient = builder.build();
        okHttpClient.dispatcher().setMaxRequestsPerHost(1);
    }
}
