package com.test.samsapplication.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Model class for ProductResultsData json object
 */
public class ProductResultsData implements Parcelable
{
    private ArrayList<Product> products = null; //Arraylist for list of products
    private int totalProducts;
    private int pageNumber;
    private int pageSize;
    private int statusCode;

    private ProductResultsData(Parcel in) {
        totalProducts = in.readInt();
        statusCode = in.readInt();
        pageNumber = in.readInt();
        pageSize = in.readInt();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(totalProducts);
        parcel.writeInt(statusCode);
        parcel.writeInt(pageNumber);
        parcel.writeInt(pageSize);
    }
    public static final Parcelable.Creator<ProductResultsData> CREATOR
            = new Parcelable.Creator<ProductResultsData>() {
        public ProductResultsData createFromParcel(Parcel in) {
            return new ProductResultsData(in);
        }

        public ProductResultsData[] newArray(int size) {
            return new ProductResultsData[size];
        }
    };

    public ProductResultsData(){

    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
