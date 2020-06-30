package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class PaypalAgreement(@SerializedName("approval_url")
                           val approvalUrl: String,
                           @SerializedName("ba_token")
                           val baToken: String)