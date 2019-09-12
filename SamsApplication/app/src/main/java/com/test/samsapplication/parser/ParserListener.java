package com.test.samsapplication.parser;

import com.test.samsapplication.datamodels.ProductResultsData;

public interface ParserListener
{
    void onParseSuccess(ProductResultsData data);
    void onParseFailure();
}
