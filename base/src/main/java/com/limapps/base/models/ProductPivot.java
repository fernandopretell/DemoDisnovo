package com.limapps.base.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductPivot implements Parcelable {

    private int id;

    @SerializedName("product_id")
    private int productId;

    @SerializedName("order_id")
    private int orderId;

    private int units;

    @SerializedName("store_id")
    private int storeId;

    @SerializedName("sale_type")
    private String saleType;

    @SerializedName("unit_price")
    private double unitPrice;

    @SerializedName("total_price")
    private double totalPrice;

    protected ProductPivot(Parcel in) {
        id = in.readInt();
        productId = in.readInt();
        orderId = in.readInt();
        units = in.readInt();
        storeId = in.readInt();
        saleType = in.readString();
        unitPrice = in.readDouble();
        totalPrice = in.readDouble();
    }

    public static final Creator<ProductPivot> CREATOR = new Creator<ProductPivot>() {
        @Override
        public ProductPivot createFromParcel(Parcel in) {
            return new ProductPivot(in);
        }

        @Override
        public ProductPivot[] newArray(int size) {
            return new ProductPivot[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(productId);
        dest.writeInt(orderId);
        dest.writeInt(units);
        dest.writeInt(storeId);
        dest.writeString(saleType);
        dest.writeDouble(unitPrice);
        dest.writeDouble(totalPrice);
    }
}
