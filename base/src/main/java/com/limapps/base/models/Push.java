package com.limapps.base.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Push {

    @SerializedName("application_id")
    @Expose
    public String applicationId;

    @SerializedName("client_key")
    @Expose
    public String clientKey;

}