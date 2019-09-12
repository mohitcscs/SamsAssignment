package com.test.samsapplication.network;

/**
 * Interface callbacks for API call success or failure
 */
public interface OkHttpCallbacks
{
    void onCallSuccess();

    void onCallFailure(Exception ex);

}
