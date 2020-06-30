package com.limapps.base.persistence.preferences

import android.content.Context
import com.google.gson.Gson
import com.limapps.base.Environment
import com.limapps.base.R
import com.limapps.base.models.Address
import com.limapps.base.models.UserInfoModel
import com.limapps.base.others.ResourcesProvider
import com.limapps.base.persistence.preferences.types.BooleanPreference
import com.limapps.base.persistence.preferences.types.FloatPreference
import com.limapps.base.persistence.preferences.types.IntPreference
import com.limapps.base.persistence.preferences.types.LongPreference
import com.limapps.base.persistence.preferences.types.StringPreference
import com.limapps.base.utils.BRAZIL_CODE
import com.limapps.base.utils.BRAZIL_CURRENCY_CODE
import com.limapps.base.utils.CHILE_CODE
import com.limapps.base.utils.CHILE_CURRENCY_CODE
import com.limapps.base.utils.COLOMBIA_CODE
import com.limapps.base.utils.COLOMBIA_CURRENCY_CODE
import com.limapps.base.utils.CountryUtil
import com.limapps.base.utils.MEXICO_CODE
import com.limapps.base.utils.MEXICO_CURRENCY_CODE
import com.limapps.base.utils.PERU_CODE
import com.limapps.base.utils.PERU_CURRENCY_CODE
import com.limapps.base.utils.StringUtils
import com.limapps.base.utils.URUGUAY_CODE
import com.limapps.base.utils.URUGUAY_CURRENCY_CODE
import org.json.JSONObject

@Deprecated("Use your own preferences handler to keep all the preferences in different files")
object PreferencesManager : PreferencesBase() {

    private lateinit var gson: Gson

    fun init(context: Context, gson: Gson) {
        this.gson = gson
        super.init(context, "UserSettings")
        //PayMainPreferencesManager.init(context, gson)
    }

    fun locationRequestModalShowed(userId: String): BooleanPreference = booleanPreference(userId + SHOW_LOCATION_REQUEST_MODAL)

    fun storeId(): StringPreference = stringPreference(STORE_ID)

    fun logIn(): BooleanPreference = booleanPreference(IS_LOGIN)

    fun accessToken(): StringPreference = stringPreference(ACCESS_TOKEN, "")

    fun refreshToken(): StringPreference = stringPreference(REFRESH_TOKEN, "")

    val userProfile: UserInfoModel?
        get() {
            return get(USER_PROFILE)?.let {
                return if (it.isNotBlank()) {
                    gson.fromJson(it, UserInfoModel::class.java)
                } else {
                    null
                }
            }
        }

    fun getCurrentAddress(): Address? {
        return get(CURRENT_ADDRESS)?.let {
            return if (it.isNotBlank()) {
                gson.fromJson(it, Address::class.java)
            } else {
                null
            }
        }
    }

    fun setUserProfile(userProfile: String) {
        set(USER_PROFILE, userProfile)
    }

    fun creditCards(): StringPreference = stringPreference(CREDIT_CARDS)

    fun tipBackup(): IntPreference = intPreference(TIP_BACKUP)

    fun tipPositionBackup(): StringPreference = stringPreference(TIP_POSITION_BACKUP)

    fun basePath(): StringPreference {
        return stringPreference(BASE_PATH, ResourcesProvider.getString(R.string.url_grability))
    }

    fun imagesPath(): StringPreference = stringPreference(IMAGES_PATH)

    fun imagesS3Path(): StringPreference = stringPreference(IMAGES_S3_PATH)

    fun setAdditionalData(json: String) {
//        additionalData().set(json)
        CountryUtil.reloadValues()
    }

    fun additionalData(): StringPreference {
        return stringPreference(ADDITIONAL_DATA, StringUtils.EMPTY_JSON_ARRAY)
    }

    fun phoneCode(): StringPreference = stringPreference(PHONE_CODE, "")


    fun addresses(): StringPreference {
        return stringPreference(ADDRESSES, StringUtils.EMPTY_JSON_ARRAY)
    }

    fun currentAddress(): StringPreference {
        return stringPreference(CURRENT_ADDRESS, StringUtils.EMPTY_JSON_OBJECT)
    }

    fun cities(): StringPreference {
        return stringPreference(CITIES, ResourcesProvider.getString(R.string.default_cities))
    }

    fun baseMicroService(): StringPreference {
        return stringPreference(MICRO_SERVICES, ResourcesProvider.getString(R.string.url_micro_services))
    }

    fun baseCentralizedService(): StringPreference {
        return stringPreference(CENTRALIZED_MICRO_SERVICES, ResourcesProvider.getString(R.string.url_micro_services))
    }

    fun registeredPurchases(): BooleanPreference {
        return booleanPreference(HAS_REGISTERED_PURCHASES)
    }

    fun supportUrl(): StringPreference {
        return stringPreference(SUPPORT_URL, ResourcesProvider.getString(R.string.url_support))
    }

    fun latitudeData(): FloatPreference = floatPreference(LATITUDE)

    fun longitudeData(): FloatPreference = floatPreference(LONGITUDE)

    fun deliveryPriceData(): FloatPreference = floatPreference(DELIVERY_PRICE)

    fun countryCodeData(): StringPreference {
        return stringPreference(COUNTRY_CODE, COLOMBIA_CODE)
    }

    fun paymentMethodDefault(): StringPreference {
        return stringPreference(PAYMENT_METHOD_DEFAULT, StringUtils.EMPTY_JSON_OBJECT)
    }

    fun isFirstTimeWithPay(): BooleanPreference {
        return booleanPreference(IS_FIRST_TIME_WITH_PAY, true)
    }

    fun questionPrimeRenovateActive(): BooleanPreference {
        return booleanPreference(RENOVATE_PRIME_ACTIVATE, true)
    }

    fun appRatedOnPlayStore(): BooleanPreference {
        return booleanPreference(APP_RATED_ON_PLAY_STORE)
    }

    fun rateOnPlayStoreTimestamp(): LongPreference {
        return longPreference(RATE_ON_PLAY_STORE_TIMESTAMP)
    }

    fun getKeyArg(cardReference: String): StringPreference {
        return stringPreference(CC_SECURITY_CODE + cardReference, "")
    }

    fun setHomeViewType(viewType: Int) = set(HOME_VIEW_TYPE, viewType)

    fun getHomeViewType() = getInt(HOME_VIEW_TYPE)

    fun setDeferredDeepLink(referringParams: JSONObject) = set(DEFERRED_DEEP_LINK, referringParams.toString())

    fun setShowedLocationRequestModal(userId: String, showed: Boolean) = set(userId + SHOW_LOCATION_REQUEST_MODAL, showed)

    fun hasPendingDeferredDeeplink() = contains(DEFERRED_DEEP_LINK)

    fun getDeferredDeepLink() = get(DEFERRED_DEEP_LINK)

    fun clearDeferredDeepLink() = remove(DEFERRED_DEEP_LINK)

    fun getCurrencyCode(): String {
        return when (countryCodeData().get()) {
            COLOMBIA_CODE -> COLOMBIA_CURRENCY_CODE
            MEXICO_CODE -> MEXICO_CURRENCY_CODE
            BRAZIL_CODE -> BRAZIL_CURRENCY_CODE
            CHILE_CODE -> CHILE_CURRENCY_CODE
            URUGUAY_CODE -> URUGUAY_CURRENCY_CODE
            PERU_CODE -> PERU_CURRENCY_CODE
            else -> ""
        }
    }

    fun environment(default: Environment = Environment.DEVELOPMENT): IntPreference {
        return intPreference(ENVIRONMENT, default.ordinal)
    }
}
