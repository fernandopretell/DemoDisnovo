package com.limapps.base.models

import com.google.gson.annotations.SerializedName
import com.limapps.base.models.loyalty.Loyalty

data class UserInfoModel(@SerializedName("id")
                         val id: String,
                         @SerializedName("first_name")
                         val firstName: String,
                         @SerializedName("last_name")
                         val lastName: String,
                         @SerializedName("email")
                         val email: String,
                        // @SerializedName("identification")
                         //val identification: String? = null,
                         @SerializedName("gender")
                         val gender: String? = "",
                         @SerializedName("birth_date")
                         val birthday: String? = null,
                         //@SerializedName("identification_type")
                         //val identificationType: Int = 1,
                         @SerializedName("pic")
                         val urlPicture: String,
                         //@SerializedName("country_code")
                         //val countryCode: String? = "",
                         @SerializedName("phone")
                         val phone: String? = "",/*
                         @SerializedName("social_id")
                         val socialNetworkId: String? = null,*/
                         /*@SerializedName("default_cc")
                         val defaultCreditCard: String? = null,
                         @SerializedName("refered_code")
                         val referralCode: String? = null,
                         @SerializedName("rappi_credits")
                         val rappiCredits: Double = 0.0,
                         @SerializedName("installments")
                         val installments: Int = 0,
                         @SerializedName("rappi_credit_active")
                         val rappiCreditActive: Boolean,*/
                         @SerializedName("blocked")
                         val blocked: Boolean?/*,
                         @SerializedName("loyalty")
                         val loyalty: Loyalty? = null,
                         @SerializedName("is_phone_confirmed")
                         val isPhoneConfirmed: Boolean?,
                         @SerializedName("country_code_name")
                         val countryCodeName: String?*/
)

fun UserInfoModel.getFullName(): String {
    return "$firstName $lastName"
}
/*

fun UserInfoModel.getGlobalPhoneNumber(): String {
    return "${countryCode.orEmpty()} ${phone.orEmpty()}".trim()
}*/
