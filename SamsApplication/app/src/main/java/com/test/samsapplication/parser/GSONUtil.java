package com.test.samsapplication.parser;

import com.google.gson.Gson;

/**
 * GSON parsing library
 */
public class GSONUtil
{
    private static GSONUtil instance;
    private static Gson gson;
    public static GSONUtil getInstance()
    {
        if (instance == null)
        {
            gson  = new Gson();
            instance = new GSONUtil();
        }
        return instance;
    }

    /**
     * Get gson object
     * @return GSON object used for parsing
     */
    public Gson getGSONObject()
    {
        return gson;
    }
}
