package com.limapps.base.models;

import com.google.gson.annotations.SerializedName;

public class ScheduleDatesProduct {

    @SerializedName("id")
    public int id;

    @SerializedName("starts_time")
    public String startsTime;

    @SerializedName("ends_time")
    public String endsTime;

    @SerializedName("corridor_product_id")
    public int corridorProductId;

    @SerializedName("days")
    public String days;
}
