package com.limapps.base.creditcards

import android.content.Context
import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.limapps.base.R
import com.limapps.base.checkout.utils.CC
import com.limapps.base.checkout.utils.GOOGLE_PAY
import com.limapps.base.checkout.utils.MASTERPASS_ORIGIN
import com.limapps.base.checkout.utils.PAYPAL
import com.limapps.base.checkout.utils.PAYPAL_ORIGIN
import com.limapps.base.checkout.utils.RAPPI_PAY
import com.limapps.base.checkout.utils.VISA_CHECKOUT_ORIGIN
import com.limapps.common.getStringOrDefault
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelCreditCard(@SerializedName("name", alternate = ["full_name"])
                           val name: String? = null,
                           @SerializedName("card_reference", alternate = ["id"])
                           val cardReference: String = "",
                           @SerializedName("bin", alternate = ["first_six_digits"])
                           val bin: String? = null,
                           @SerializedName("termination", alternate = ["last_four_digits"])
                           val termination: String? = null,
                           @SerializedName("type", alternate = ["card_type"])
                           val type: String? = null,
                           @SerializedName("restriction_message")
                           val restrictionMessage: List<String?>? = null,
                           @SerializedName("need_verification")
                           val needsVerification: Boolean? = false,
                           @SerializedName("available")
                           val available: Boolean? = null,
                           var isSelected: Boolean = false,
                           @SerializedName("payment_origin", alternate = ["gateway_type"])
                           val paymentOrigin: String? = null,
                           @SerializedName("default_cc")
                           val defaultCC: Boolean = false,
                           @SerializedName("verified")
                           val verified: Boolean = false,
                           @SerializedName("requires_cvv")
                           val requiresCVV: Boolean = false,
                           @SerializedName("request_new_cvv")
                           val requestNewCVV: Boolean = false,
                           @SerializedName("logo_url")
                           val logoUrl: String? = null,
                           @SerializedName("email")
                           val email: String? = null,
                           @SerializedName("special_properties")
                           val specialProperties: SpecialPropertiesModel? = null,
                           @SerializedName("spreedly_card_token")
                           val spreedly_card_token: String? = null,
                           @Transient
                           var token: String? = null,
                           @SerializedName("available_installments")
                           val availableInstallments: List<Int> = emptyList(),
                           @SerializedName("default_installments")
                           val defaultInstallment: Int = 0,
                           @SerializedName("charge_data")
                           val chargeData: MutableMap<String, String>? = null


) : Parcelable {

    companion object {
        fun fromJsonObject(creditCard: JsonObject): ModelCreditCard {
            return ModelCreditCard(
                    type = creditCard.getStringOrDefault("card_type").orEmpty(),
                    termination = creditCard.getStringOrDefault("last_four_digits").orEmpty(),
                    paymentOrigin = creditCard.getStringOrDefault("gateway_type").orEmpty()

            )
        }
    }

    fun getSpreedlyToken(): String? {
        return token ?: spreedly_card_token
    }
}

@Parcelize
data class SpecialPropertiesModel(
        @SerializedName("isDebit")
        val isDebit: Boolean
) : Parcelable

fun ModelCreditCard.getNumberCard(context: Context): String {
    return context.getString(R.string.dummy_credit_card, termination)
}

fun ModelCreditCard.isInReview(): Boolean {
    return needsVerification == true
}

fun ModelCreditCard.isAvailable(): Boolean {
    return this.available == true
}

fun ModelCreditCard.tryToSelectCard() {
    this.isSelected = isSelectable()
}

fun ModelCreditCard.isSelectable(): Boolean {
    return this.isAvailable() && !this.isInReview()
}

fun ModelCreditCard.isVisaCheckout(): Boolean = this.paymentOrigin == VISA_CHECKOUT_ORIGIN

fun ModelCreditCard.isMasterpass(): Boolean = this.paymentOrigin == MASTERPASS_ORIGIN

fun ModelCreditCard.isWallet(): Boolean = this.isVisaCheckout() || this.isMasterpass()

fun ModelCreditCard.isPayPal(): Boolean = this.paymentOrigin == PAYPAL_ORIGIN