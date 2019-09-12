package com.test.samsapplication.datamodels;

import java.util.ArrayList;

/**
 *  Singleton Manager to share products data.
 */
public class ProductsDataManager
{
    private int totalNumberOfProducts = 0;
    private static ProductsDataManager mProductsDataManager;
    private ArrayList<Product> products = new ArrayList<>();

    public static ProductsDataManager getSingleTonInstance(){
        if(mProductsDataManager == null){
            mProductsDataManager = new ProductsDataManager();
        }
        return mProductsDataManager;
    }

    public ArrayList<Product> getProductsList(){
        return products;
    }

    public void setProductsList(ArrayList<Product> products){
        this.products.addAll(products);
    }

    public int getTotalNumberOfProducts() {
        return totalNumberOfProducts;
    }

    public void setTotalNumberOfProducts(int totalNumberOfProducts) {
        this.totalNumberOfProducts = totalNumberOfProducts;
    }

    public void clearData(){
        products.clear();
        totalNumberOfProducts = 0;
    }
}
