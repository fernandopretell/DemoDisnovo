package com.limapps.base.utils

import android.content.Context
import android.content.res.Configuration
import com.limapps.base.R
import com.limapps.base.others.IResourceProvider
import java.util.Locale

private val hashMapAddressTagIconDrawable = hashMapOf<String, Int>()
private val hashMapAddressTagMiniIconDrawable = hashMapOf<String, Int>()

const val GOOGLE = "google"
const val PHONE_MIXED = "phone_mixed"
const val TWILIO = "phone"
fun Int.getStringByLocale(locale: String, context: Context): String {
    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(Locale(locale))

    return try {
        context.createConfigurationContext(configuration).resources.getString(this)
    } catch (e: Exception) {
        ""
    }
}

enum class AddressTag {
    HOME(),
    OFFICE(),
    PARTNER(),
    OTHER();
}

fun AddressTag.toStringResource(resourceProvider: IResourceProvider): String {
    return when (this) {
        AddressTag.HOME -> resourceProvider.getString(R.string.address_tag_home)
        AddressTag.OFFICE -> resourceProvider.getString(R.string.address_tag_office)
        AddressTag.PARTNER -> resourceProvider.getString(R.string.address_tag_partner)
        AddressTag.OTHER -> resourceProvider.getString(R.string.address_tag_other)
    }
}

fun String?.getIconDrawableFromString(context: Context): Int {
    if(this != null) {
        val userAddressTag = this
        hashMapAddressTagIconDrawable[userAddressTag]?.let {
            return it
        }
        when (userAddressTag) {
            R.string.address_tag_home.getStringByLocale(SPANISH, context),
            R.string.address_tag_home.getStringByLocale(PORTUGUESE, context),
            R.string.address_tag_home.getStringByLocale(ENGLISH, context) -> {
                hashMapAddressTagIconDrawable[userAddressTag] = R.drawable.icon_house
                return R.drawable.icon_house
            }
            R.string.address_tag_office.getStringByLocale(SPANISH, context),
            R.string.address_tag_office.getStringByLocale(PORTUGUESE, context),
            R.string.address_tag_office.getStringByLocale(ENGLISH, context) -> {
                hashMapAddressTagIconDrawable[userAddressTag] = R.drawable.icon_work
                return R.drawable.icon_work
            }
            R.string.address_tag_partner.getStringByLocale(SPANISH, context),
            R.string.address_tag_partner.getStringByLocale(PORTUGUESE, context),
            R.string.address_tag_partner.getStringByLocale(ENGLISH, context) -> {
                hashMapAddressTagIconDrawable[userAddressTag] = R.drawable.icon_heart
                return R.drawable.icon_heart
            }
            else -> {
                hashMapAddressTagIconDrawable[userAddressTag] = R.drawable.icon_address_location
                return R.drawable.icon_address_location
            }
        }
    } else {
        return R.drawable.icon_address_location
    }
}

fun String?.getMiniIconDrawableFromString(context: Context): Int {
    if(this != null) {
        val userAddressTag = this
        hashMapAddressTagMiniIconDrawable[userAddressTag]?.let {
            return it
        }
        when (userAddressTag) {
            R.string.address_tag_home.getStringByLocale(SPANISH, context),
            R.string.address_tag_home.getStringByLocale(PORTUGUESE, context),
            R.string.address_tag_home.getStringByLocale(ENGLISH, context) -> {
                hashMapAddressTagMiniIconDrawable[userAddressTag] = R.drawable.ic_mini_home_wrapper
                return R.drawable.ic_mini_home_wrapper
            }
            R.string.address_tag_office.getStringByLocale(SPANISH, context),
            R.string.address_tag_office.getStringByLocale(PORTUGUESE, context),
            R.string.address_tag_office.getStringByLocale(ENGLISH, context) -> {
                hashMapAddressTagMiniIconDrawable[userAddressTag] = R.drawable.ic_mini_office_wrapper
                return R.drawable.ic_mini_office_wrapper
            }
            R.string.address_tag_partner.getStringByLocale(SPANISH, context),
            R.string.address_tag_partner.getStringByLocale(PORTUGUESE, context),
            R.string.address_tag_partner.getStringByLocale(ENGLISH, context) -> {
                hashMapAddressTagMiniIconDrawable[userAddressTag] = R.drawable.ic_mini_girlfriend_wrapper
                return R.drawable.ic_mini_girlfriend_wrapper
            }
            else -> {
                hashMapAddressTagMiniIconDrawable[userAddressTag] = R.drawable.ic_mini_location_wrapper
                return R.drawable.ic_mini_location_wrapper
            }
        }
    } else {
        return R.drawable.ic_mini_location_wrapper
    }
}