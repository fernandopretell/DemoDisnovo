package com.limapps.base.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.limapps.base.R
import com.limapps.base.others.IResourceProvider
import com.limapps.base.persistence.preferences.PAY_WAITING_LIST
import com.limapps.base.persistence.preferences.PRODUCT_REVIEW_STORES
import com.limapps.base.persistence.preferences.PreferencesManager
import com.limapps.common.getStringOrDefault
import com.limapps.common.stringToArray
import com.limapps.common.tryOrDefault

@Deprecated(message = "From now on, every module have to wrap/implement/extend BaseDataProvider/RappiBaseDataProvider.",
        replaceWith = ReplaceWith("BaseDataProvider/RappiBaseDataProvider"),
        level = DeprecationLevel.WARNING)
object CountryUtil {

    private const val CHECKOUT_NEW_VERSION = "CHECKOUT_NEW_VERSION"
    private const val DEFAULT_MIN_VALUE_TO_REDEEM = "0"
    private const val MIN_VALUE_TO_REDEEM = "min_value_to_redeem"
    private const val ORDER_MAX_VALUE = "order_max_value"
    private const val KEY = "key"
    private const val VALUE = "value"
    private const val F_A_AND = "f_a_and"

    const val HOME_CALL_CENTER_LINE = "home_call_center_line"
    private const val RAPPI_CASH_CHARGES_INFO = "rappicash_charge_info"
    private const val TIP_VALUES = "tip_values"
    private const val TIP_SECOND = "tip_second"
    private const val TIP_MINIMUM = "minimum_tip"
    private const val TIP_POSITION = "tip_position"
    const val MARKET_TERMS_AND_CONDITIONS_PRICE_1 = "market_terms_and_conditions_price_1"
    const val MARKET_TERMS_AND_CONDITIONS_PRICE_2 = "market_terrms_and_conditions_price_2"
    private const val ETA_GOOGLE_ROUTE = "google_route"
    private const val ETA_DELAY = "delay_eta"
    private const val ETA_STOREKEEPER_ICON = "icon_rt_eta"
    private const val ETA_USER_ICON = "icon_user_eta"
    private const val FREE_PRODUCT_LIMIT = "free_product_limit"

    private const val INFO_SHIPPING_COST = "info_shipping_cost"
    private const val INFO_SERVICE_FEE = "checkout_service_fee"
    private const val MAX_SERVICE_FEE = "max_service_fee"

    private const val MENU_CALL_STATUS = "mainmenu_call_status"
    const val MENU_CALL_NUMBER = "mainmenu_call_number"

    const val RAPPI_CREDITS_DESCRIPTION = "rappicredito_description"

    const val REDEEM_COUPON_GIFT = "redeem_coupon_gift"

    private const val BANK_PARTNER_INTEGRATION_ENABLED = "rappipay_integration_dvv_enabled"

    const val FAVOR_DESCRIPTION = "favor_description"
    const val FAVOR_COST_1 = "favor_cost_1"
    const val FAVOR_COST_2 = "favor_cost_2"
    const val FAVOR_COST_3 = "favor_cost_3"
    const val FAVOR_MAX_LIMIT = "favor_max_limit"

    const val RAPPI_PAY_SHOW_QR_SPLASH = "rappipay_show_qr_splash_and"

    private const val FAVOR_MIN_COST = "rappifavor_min_cost"
    private const val FAVOR_MIN_DISTANCE = "rappifavor_min_distance"

    private const val CANCELLATION_INFO = "cancellation_info"
    private const val CANCELLATION_POLICY = "cancellation_policy"
    private const val CANCELLATION_CALL_SUPPORT = "cancel_call_support"
    private const val CANCELLATION_CALL_SUPPORT_PHONE_NUMBER = "cancel_call_support_phone"

    private const val REST_ETA_MAX_TIME_ALERT = "rest_eta_maxtime_alert "
    private const val DEFAULT_ETA_MAX_TIME_ALERT_MIN = 50
    private const val MINIMUM_ORDER_STATUS = "minimum_order_status"
    private const val MINIMUM_ORDER_AMOUNT = "minimum_order_amount"
    private const val MINIMUM_ORDER_WORLD_CUP = "minimum_order_world_cup"
    private const val MINIMUM_ORDER_STORES = "minimum_order_stores"

    private const val CANCELLED_BY_PARTNER_STATE = "cancelled_by_partner_visible"

    private const val ADDRESS_PIN_RADIUS_DISTANCE = "address_pin_radius_distance"

    private const val DEBT_SUPPORT_PHONE = "debt_payment_phone_support_number"

    private val preferencesManager = PreferencesManager

    private val gson = Gson()

    private var jsonArray: JsonArray? = null


    fun getFilters(): String {
        return getData(F_A_AND, "")
    }

    fun getPinRadiusDistance(): Int = getData(ADDRESS_PIN_RADIUS_DISTANCE, "0").toInt()

    fun getCountryCode(): String = preferencesManager.countryCodeData().get()

    fun getCurrencyCode(): String = getCurrencyCode(getCountryCode())

    fun checkoutNewVersion(): Boolean = getData(CHECKOUT_NEW_VERSION, "false").toBoolean()

    fun isMexico(): Boolean = getCountryCode().equals(MEXICO_CODE, ignoreCase = true)

    fun isColombia(): Boolean = getCountryCode().equals(COLOMBIA_CODE, ignoreCase = true)

    fun isBrazil(): Boolean = getCountryCode().equals(BRAZIL_CODE, ignoreCase = true)

    fun isArgentina(): Boolean = getCountryCode().equals(ARGENTINA_CODE, ignoreCase = true)

    fun isChile(): Boolean = getCountryCode().equals(CHILE_CODE, ignoreCase = true)

    fun isUruguay(): Boolean = getCountryCode().equals(URUGUAY_CODE, ignoreCase = true)

    fun isPeru(): Boolean = getCountryCode().equals(PERU_CODE, ignoreCase = true)

    fun getOrderMaxValue(): String = getData(ORDER_MAX_VALUE, "-1.0")

    fun getTipSecond(): String = getData(TIP_SECOND)

    fun getTipMinimum(): String = getData(TIP_MINIMUM)

    fun getTipPosition(): String = getData(TIP_POSITION)

    fun getTipValues(): Array<Double> {
        val values = getDefaultTipValues()
        try {
            val tipsValues = getData(TIP_VALUES).split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            values[0] = tipsValues[0].trim().toDouble()
            values[1] = tipsValues[1].trim().toDouble()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return values
    }

    private fun getDefaultTipValues(): Array<Double> {
        val values: Array<Double>
        val countryCode = getCountryCode()
        values = when (countryCode) {
            MEXICO_CODE -> arrayOf(10.0, 20.0)
            BRAZIL_CODE -> arrayOf(2.0, 4.0)
            COLOMBIA_CODE -> arrayOf(1000.0, 2000.0)
            ARGENTINA_CODE -> arrayOf(10.0, 20.0)
            CHILE_CODE -> arrayOf(500.0, 900.0)
            URUGUAY_CODE -> arrayOf(12.0, 24.0)
            else -> arrayOf(1000.0, 2000.0)
        }
        return values
    }

    fun isMinimumOrderStatusActive(): Boolean = getData(MINIMUM_ORDER_STATUS, "false").toBoolean()

    fun getMinimumOrderAmount(): Int = getData(MINIMUM_ORDER_AMOUNT, "0").toInt()

    fun isOrderWorldCup(): Boolean = getData(MINIMUM_ORDER_WORLD_CUP, "false").toBoolean()

    fun getMinimunOrderStores(): Array<String> = getData(MINIMUM_ORDER_STORES, "").stringToArray()

    fun isEtaGoogleRouteActive(): Boolean = getData(ETA_GOOGLE_ROUTE, "false").toBoolean()

    fun getFreeProductLimit(): String = getData(FREE_PRODUCT_LIMIT, "0")

    fun getInfoShippingCost(): String = getData(INFO_SHIPPING_COST, "")

    fun getInfoServiceFee(): String = getData(INFO_SERVICE_FEE, "")

    fun getMaxServiceFee(): Double {
        val defaultValue = Double.MAX_VALUE.toString()
        return tryOrDefault({ getData(MAX_SERVICE_FEE, defaultValue) }, defaultValue).toDouble()
    }

    fun getRappiCashChargesInfo(): String = getData(RAPPI_CASH_CHARGES_INFO, "")

    fun isMenuCallEnabled(): Boolean = getData(MENU_CALL_STATUS, "false").toBoolean()

    fun getCancellationPolicy(): String = getData(CANCELLATION_POLICY, "")

    fun getCancellationInfo(): String = getData(CANCELLATION_INFO, "")

    fun getFavorMinCost(): String = getData(FAVOR_MIN_COST, "")

    fun getFavorMinDistance(): String = getData(FAVOR_MIN_DISTANCE, "")

    fun getFavorMaxLimit(): Double = getData(FAVOR_MAX_LIMIT, "100000").toDouble()

    fun getData(key: String, defaultValue: String? = ""): String {
        if (jsonArray == null) {
            reloadValues()
        }

        val jsonObject = jsonArray?.firstOrNull { hasInformation(it.asJsonObject, key) }?.asJsonObject
        return jsonObject?.getStringOrDefault(VALUE) ?: defaultValue.orEmpty()
    }

    private fun hasInformation(jsonObject: JsonObject, key: String): Boolean {
        return jsonObject.has(KEY) && jsonObject.has(VALUE) && jsonObject.getStringOrDefault(KEY) == key
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return getData(key, defaultValue.toString()).toInt()
    }

    fun getDouble(key: String, defaultValue: Double = 0.0): Double {
        return getData(key, defaultValue.toString()).toDouble()
    }

    private fun getCurrencyCode(countryCode: String): String {
        return when (countryCode) {
            COLOMBIA_CODE -> COLOMBIA_CURRENCY_CODE
            MEXICO_CODE -> MEXICO_CURRENCY_CODE
            BRAZIL_CODE -> BRAZIL_CURRENCY_CODE
            CHILE_CODE -> CHILE_CURRENCY_CODE
            URUGUAY_CODE -> URUGUAY_CURRENCY_CODE
            PERU_CODE -> PERU_CURRENCY_CODE
            else -> ""
        }
    }

    fun getCountryName(context: Context, countryCode: String): String {
        val countryNameId = getCountryNameResourceId(countryCode)
        return if (countryNameId != -1) context.getString(countryNameId) else ""
    }

    private fun getCountryNameResourceId(countryCode: String): Int {
        return when (countryCode) {
            COLOMBIA_CODE -> R.string.country_selection_colombia
            MEXICO_CODE -> R.string.country_selection_mexico
            BRAZIL_CODE -> R.string.country_selection_brazil
            ARGENTINA_CODE -> R.string.country_selection_argentina
            CHILE_CODE -> R.string.country_selection_chile
            URUGUAY_CODE -> R.string.country_selection_uruguay
            PERU_CODE -> R.string.base_peru
            ECUADOR_CODE -> R.string.country_selection_ecuador
            COSTA_RICA_CODE -> R.string.country_selection_costa_rica
            else -> -1
        }
    }

    fun getCurrentCountryName(resourceProvider: IResourceProvider): String {
        val countryNameId = getCountryNameResourceId(getCountryCode())
        return if (countryNameId != -1) resourceProvider.getString(countryNameId) else ""
    }

    fun reloadValues() {
        jsonArray = tryOrDefault({
            gson.fromJson(preferencesManager.additionalData().get(), JsonArray::class.java)
        }, gson.fromJson(StringUtils.EMPTY_JSON_ARRAY, JsonArray::class.java)
        )
    }

    fun cleanCachedData() {
        jsonArray = null
    }

    fun getEtaDelay(): Int = tryOrDefault({ getData(ETA_DELAY).toInt() * 100 }, 700)

    fun is20MinEnabled(): Boolean {
        return getData("20_mins_status", "false").toBoolean()
    }

    fun is20MinWebviewEnabled(): Boolean {
        return getData("20_mins_support_web", "false").toBoolean()
    }

    fun get20MinSupportWindow(): Int {
        return getData("20_mins_time_to_close_order", "0").toInt()
    }


    fun getPayWaitingListUrl(): String = getData(PAY_WAITING_LIST, "https://rappi.typeform.com/to/rOrGb0?userid=")

    // Booking Url
    fun getBookingUrl(storeType: String?): String? {
        return storeType?.let {
            val storeTypeData = getData(it, "")
            if (storeTypeData.isNotEmpty()) {
                gson.fromJson(storeTypeData, JsonArray::class.java)
                        .firstOrNull()?.asJsonObject
                        .getStringOrDefault("page", "")
            } else {
                null
            }
        }
    }

    // Cancelled by partner
    fun isCancelledByPartnerActive(): Boolean = getData(CANCELLED_BY_PARTNER_STATE, "false").toBoolean()

    fun showRappiPayQr(): Boolean = getData(RAPPI_PAY_SHOW_QR_SPLASH, "false").toBoolean()

    fun getProductReviewStores(): Set<String> = getData(PRODUCT_REVIEW_STORES).split(",").toSet()

    fun getETAStorekeeperIcon(): String {
        return getData(ETA_STOREKEEPER_ICON, "")
    }

    fun getETAUserIcon(): String {
        return getData(ETA_USER_ICON, "")
    }

    fun getETAMaxAlertTime(): Int = getData(REST_ETA_MAX_TIME_ALERT, DEFAULT_ETA_MAX_TIME_ALERT_MIN.toString()).toInt()

    fun getMinValueToRedeem(): Int {
        return tryOrDefault({ getData(MIN_VALUE_TO_REDEEM, DEFAULT_MIN_VALUE_TO_REDEEM) }, DEFAULT_MIN_VALUE_TO_REDEEM).toInt()
    }

    fun getDebtSupportPhoneNumber(): String {
        return getData(DEBT_SUPPORT_PHONE, "")
    }

    fun isCancellationSupportPhoneActive(): Boolean = getData(CANCELLATION_CALL_SUPPORT, "false").toBoolean()

    fun isBankPartnerIntegrationEnabled(): Boolean = getData(BANK_PARTNER_INTEGRATION_ENABLED, "false").toBoolean()

    fun getCancellationSupportPhoneNumber(): String {
        return getData(CANCELLATION_CALL_SUPPORT_PHONE_NUMBER, "")
    }

    fun isSupportLiveActive(): Boolean = getData("android_live_support_typification", "false").toBoolean()

    fun getRawData() = jsonArray
}
