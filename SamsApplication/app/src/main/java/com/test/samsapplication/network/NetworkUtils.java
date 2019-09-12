package com.test.samsapplication.network;

/**
 * Network related variables to be accessed through out the application
 */
public class NetworkUtils
{
    public static final int HTTP_CONNECTION_TIMEOUT = 30000;

    public static final int HTTP_READ_TIMEOUT = 30000;

    // API to get Products list with page number and size.
    public static final String PRODUCT_API = "https://mobile-tha-server.firebaseapp.com/walmartproducts/%s/%s";

    // API to get Image URL with image parameter.
    public static final String PRODUCT_IMAGE_API = "https://mobile-tha-server.firebaseapp.com%s";

}
