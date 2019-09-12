package com.test.samsapplication.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class for Product json object
 */
public class Product implements Parcelable {
    private String productId;
    private String productName;
    private String shortDescription;
    private String longDescription;
    private String price;
    private String productImage;
    private float reviewRating;
    private int reviewCount;
    private boolean inStock;

    private Product(Parcel in) {
        productId = in.readString();
        productImage = in.readString();
        productName = in.readString();
        price = in.readString();
        longDescription = in.readString();
        shortDescription = in.readString();
        reviewCount = in.readInt();
        reviewRating = in.readFloat();
        inStock = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productId);
        parcel.writeString(productImage);
        parcel.writeString(productName);
        parcel.writeString(price);
        parcel.writeString(longDescription);
        parcel.writeString(shortDescription);
        parcel.writeInt(reviewCount);
        parcel.writeFloat(reviewRating);
        parcel.writeByte((byte) (inStock ? 1 : 0));
    }

    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public Product(){

    }

    public String getProductId() {
        return productId == null ? "" :productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName == null ? "" : productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortDescription() {
        return shortDescription == null ? "" : shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription == null ? "" : longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getPrice() {
        return price == null ? "" : price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage == null ? "" : productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public float getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public boolean getInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
}
