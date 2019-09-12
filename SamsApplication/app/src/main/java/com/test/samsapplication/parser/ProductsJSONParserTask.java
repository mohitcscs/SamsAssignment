package com.test.samsapplication.parser;

import android.os.AsyncTask;

import com.test.samsapplication.datamodels.Product;
import com.test.samsapplication.datamodels.ProductResultsData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Parser class for Products list api call.
 * Async task to work on different thread and reply on main thread.
 */
public class ProductsJSONParserTask extends AsyncTask<Void, Void, ProductResultsData>
{
    private String networkResponse;
    private ParserListener mParserListener;

    public ProductsJSONParserTask(ParserListener mParserListener, String networkResponse){
        this.mParserListener = mParserListener;
        this.networkResponse = networkResponse;
    }

    @Override
    protected ProductResultsData doInBackground(Void ... voids) {
        return parseJSONResponse(networkResponse);
    }

    protected void onPostExecute(ProductResultsData result)
    {
        //notify caller for parsing is done.
        mParserListener.onParseSuccess(result);
    }

    /**
     * Parse json object and json array recursively.
     * This can be acheived using various Parsing libraries, eg. GSON, jackson, combination of Retrofit, etc.
     * @param networkResponse Response in string format from server
     */
    private ProductResultsData parseJSONResponse(String networkResponse){
        ArrayList<Product> productsList = new ArrayList<>();
        ProductResultsData mSearchResultsData = new ProductResultsData();

        try {
            JSONObject jsonObj = new JSONObject(networkResponse);
            int statusCode = jsonObj.getInt("statusCode");
            if(statusCode != 200){ //Check success result code.
                mParserListener.onParseFailure();
            }
            mSearchResultsData.setStatusCode(statusCode);
            mSearchResultsData.setPageNumber(jsonObj.getInt("pageNumber"));
            mSearchResultsData.setTotalProducts(jsonObj.getInt("totalProducts"));
            mSearchResultsData.setPageSize(jsonObj.getInt("pageSize"));

            // Getting JSON Array node
            JSONArray productsJSON = jsonObj.getJSONArray("products");

            // looping through All products
            for (int i = 0; i < productsJSON.length(); i++) {
                Product product = new Product();
                JSONObject c = productsJSON.getJSONObject(i);
                product.setProductId(c.optString("productId"));
                product.setProductImage(c.optString("productImage"));
                product.setProductName(c.optString("productName"));
                product.setLongDescription(c.optString("longDescription"));
                product.setShortDescription(c.optString("shortDescription"));
                product.setPrice(c.optString("price"));
                product.setReviewRating(c.getInt("reviewRating"));
                product.setReviewCount(c.getInt("reviewCount"));
                product.setInStock(c.getBoolean("inStock"));
                // adding product to product list
                productsList.add(product);
            }
            mSearchResultsData.setProducts(productsList);
        } catch (JSONException e){
            mParserListener.onParseFailure();
        } catch (Exception e){
            mParserListener.onParseFailure();
        }
        return mSearchResultsData;
    }
}
