package com.limapps.base.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceInfo implements Parcelable {

    @SerializedName("place_id")
    private String placeId;

    @SerializedName("name")
    private String name;

    @SerializedName("lat")
    private double lat;

    @SerializedName("lng")
    private double lng;

    @SerializedName("types")
    private List<String> types;

    public PlaceInfo(String placeId, String name, double lat, double lng, List<String> types) {
        this.placeId = placeId;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.types = types;
    }

    protected PlaceInfo(Parcel in) {
        placeId = in.readString();
        name = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        types = in.createStringArrayList();
    }

    public static final Creator<PlaceInfo> CREATOR = new Creator<PlaceInfo>() {
        @Override
        public PlaceInfo createFromParcel(Parcel in) {
            return new PlaceInfo(in);
        }

        @Override
        public PlaceInfo[] newArray(int size) {
            return new PlaceInfo[size];
        }
    };

    public String getPlaceId() {
        return placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(placeId);
        parcel.writeString(name);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeStringList(types);
    }
}
