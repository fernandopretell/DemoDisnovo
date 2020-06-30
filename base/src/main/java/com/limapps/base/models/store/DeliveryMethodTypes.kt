package com.limapps.base.models.store

enum class DeliveryMethodTypes(val value: String) {
    DELIVERY("delivery"),
    PICKUP("pickup"),
    MARKETPLACE("marketplace");

    companion object {
        private val map = values().associateBy(DeliveryMethodTypes::value)
        fun fromString(value: String) = map[value.toLowerCase()]
    }
}

fun DeliveryMethodTypes?.isPickup(): Boolean {
    return this == DeliveryMethodTypes.PICKUP
}

fun DeliveryMethodTypes.isDelivery(): Boolean {
    return this == DeliveryMethodTypes.DELIVERY
}