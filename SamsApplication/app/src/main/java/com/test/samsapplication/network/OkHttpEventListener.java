package com.test.samsapplication.network;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Protocol;

/**
 * Helper class for additional OKhttp implementation
 */
public class OkHttpEventListener extends EventListener {

    private static final String TAG =  "OkHttpEventListener";

    @Override
    public void connectFailed(@NotNull Call call, @NotNull InetSocketAddress inetSocketAddress, @NotNull Proxy proxy, @Nullable Protocol protocol, @NotNull IOException ioe) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
        Log.d(TAG, "connectFailed");
    }

    @Override
    public void callFailed(@NotNull Call call, @NotNull IOException ioe) {
        super.callFailed(call, ioe);
        Log.d(TAG, "callFailed");
    }

    @Override
    public void responseBodyEnd(@NotNull Call call, long byteCount) {
        super.responseBodyEnd(call, byteCount);
    }
}
