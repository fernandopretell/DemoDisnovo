package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class CountryCode(
        @SerializedName("name")
        val name: String,
        @SerializedName("code")
        val code: String,
        @SerializedName("code_iso_2")
        val codeIso: String,
        @SerializedName("flag")
        val flag: String
)