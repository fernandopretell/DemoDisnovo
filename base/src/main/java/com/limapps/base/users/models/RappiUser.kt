package com.limapps.base.users.models

import com.limapps.base.checkout.utils.getPaymentName
import com.limapps.base.others.IResourceProvider
import java.math.BigDecimal

data class RappiUser(val id: String = "",
                     val name: String = "",
                     val lastName: String = "",
                     val birthday: String = "",
                     val email: String = "",
                     val gender: String = "",
                     val identification: String = "",
                     val identificationType: Int = 0,
                     val pictureUrl: String = "",
                     val phone: String = "",
                     val countryCodePhone: String = "",
                     val socialNetworkId: String = "",
                     val defaultCreditCard: String = "",
                     val defaultInstallments: Int = 0,
                     val hasAccountsToVerify: Boolean = false,
                     val state: STATE = STATE.INVALID,
                     val userLevel: String = "",
                     val userPoints: Int = 0)

fun RappiUser.fullName() = "$name $lastName"
fun RappiUser.globalPhoneNumber() = "$countryCodePhone $phone"


data class RappiPayment(val balance: BigDecimal = BigDecimal.ZERO,
                        val active: Boolean = false,
                        val name: String = "")


data class RappiSubscription(val name: String = "",
                             val active: Boolean = false,
                             val paymentMethods: List<String> = emptyList(),
                             val benefits: List<RappiSubscriptionBenefit> = emptyList(),
                             val appliesTrial: Boolean = false,
                             val dayForTrial: Int = 0,
                             val minAmount: Double = 0.0,
                             val price: Double = 0.0,
                             val priceDiscount: Double = 0.0,
                             val monthlySavings: Double = 0.0,
                             val isAutomaticRenewalActivated: Boolean = false,
                             val endsAt: String = "",
                             val actualPlan: PrimeType,
                             val state: STATE = STATE.INVALID,
                             val isFreePrime: Boolean = false
)

data class RappiSubscriptionBenefit(val icon: String,
                                    val message: String,
                                    val title: String)


enum class STATE {
    INVALID,
    VALID,
    GUESS
}

enum class PrimeType(val value: String) {
    MONTHLY("monthly"),
    ANNUAL("annual"),
    BIMONTHLY("bimonthly"),
    QUARTERLY("quarterly"),
    BIANNUAL("biannual"),
    NONE("none");

    companion object {
        fun getTypeByName(nameString: String): PrimeType {
            return when (nameString.toLowerCase()) {
                MONTHLY.value -> MONTHLY
                ANNUAL.value -> ANNUAL
                BIMONTHLY.value -> BIMONTHLY
                QUARTERLY.value -> QUARTERLY
                BIANNUAL.value -> BIANNUAL
                NONE.value -> NONE
                else -> NONE
            }
        }
    }
}

fun RappiSubscription.isPaymentMethodAvailable(method: String?): Boolean {
    return paymentMethods.contains(method)
}

fun RappiSubscription.getFirstValueFromCondition(): Double {
    return minAmount
}

fun RappiSubscription.isValidAmountPrime(totalProducts: Double): Boolean = if (isMinAmountValid()) totalProducts >= this.minAmount else false

fun RappiSubscription.isMinAmountValid() = minAmount > 0

fun RappiSubscription.remainingAmountToValidPrime(totalProducts: Double): Double = minAmount - totalProducts

fun RappiSubscription.getPaymentMethodNamesFriendlyString(resourceProvider: IResourceProvider): String {
    return paymentMethods.joinToString(", ") { resourceProvider.getString(getPaymentName(it)) }
}

fun RappiUser.getUserName(): String = name