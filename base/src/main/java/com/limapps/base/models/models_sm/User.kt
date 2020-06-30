package com.limapps.base.models.models_sm

import com.google.gson.annotations.SerializedName

data class User (
        @SerializedName("uid") val uid: String = "",
        @SerializedName("birthdate") val birthdate: String = "",
        @SerializedName("city_residence") val city_residence: Int?,
        @SerializedName("first_login") val first_login: Boolean?,
        @SerializedName("gender") val gender: String?="",
        @SerializedName("image_profile") val image_profile: String?="",
        @SerializedName("job") val job: String?="",
        @SerializedName("name") val name: String?="",
        @SerializedName("phone") val phone: String?="",
        @SerializedName("status") val status: String?=""
)