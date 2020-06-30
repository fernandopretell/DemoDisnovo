package com.limapps.base.deepLink

import android.content.Intent
import android.os.Bundle
import io.reactivex.Observable
import org.json.JSONObject

const val STORE_TYPE_KEY = "store_type"
const val MARKET_TYPE_KEY = "market_type"
const val RESTAURANT_BRAND_NAME = "brand_name"
const val DEEP_LINK_DEFAULT_DOMAIN = "bnc.lt"
const val RAPPI_PAY = "rappi_pay"
const val VIEW_CC_SCREEN = "credit_cards"
const val RAPPI_PRIME_TRIAL = "rappi_prime_trial"
const val RAPPI_PRIME = "rappi_prime"
const val RAPPI_GOLD = "rappi_gold"
const val HELP_CENTER = "help_center"
const val PROFILE = "profile"
const val PRODUCT_ID = "product_id"
const val PRODUCT_TITLE = "product_title"
const val TAG_ID = "tag_id"
const val CATEGORY_INDEX = "category_index"
const val CATEGORY = "category"
const val CORRIDORS_IDS = "corridors_ids"
const val CORRIDOR = "corridor"
const val SUB_CORRIDORS_IDS = "sub_corridors_ids"
const val TRADEMARK = "trademark"
const val CAMPAIGN_ID = "campaign_id"
const val SHOW_FIDELITY = "show_fidelity"
const val SHOW_RECIPES = "show_recipes"
const val GO_TO_KEY = "goto"
const val ARTICLE_ID = "article_id"
const val SCREEN_ID = "screen_id"
const val CODE = "code"
const val AGENT_ID = "agent_id"
const val SECTION = "section"
const val AMOUNT = "amt"
const val CURRENCY = "cur"
const val COUNTRY_CODE = "country_code"
const val WEB_VIEW = "webview"
const val COUPON = "coupon"
const val LINK = "LINK"
const val ORDER_ID = "orderId"
const val ORDER_ID_PARAM = "order_id"
const val NON_BRANCH_LINK = "+non_branch_link"
const val CLICKED_BRANCH_LINK = "+clicked_branch_link"
const val WHERE = "where"
const val WHAT = "what"
const val SHARED_PRODUCT_ID = "shared_product_id"
const val PROMOTIONS = "promotions"
const val RAPPI_CREDITS = "rappi_credits"
const val NOTIFICATIONS = "notifications"
const val FAVORITES = "favorites"
const val CATEGORY_ID = "category_id"
const val SECTION_ID = "section_id"
const val ADDRESS = "address"
const val ORDER_IN_PROGRESS = "orders.in_progress"
const val PARAM_URL = "url"
const val RECOMMENDATIONS = "recommendations"
const val RECOMMENDATION_SECTION = "section"
const val LOYALTY = "loyalty"
const val LEVELS = "levels"
const val MISSIONS = "missions"
const val INFO = "info"
const val BONUS = "bonus"
const val RESCHEDULE_ORDER_ID = "reschedule_order_id"
const val GO_TAXI = "go-taxi"
const val RATING = "rating"
const val RATE_SECTION = "rate"
const val PLATE = "plate"
const val RAPPI_PAY_ME = "rappipay.me"
const val RAPPI_COM = "rappi.com"
const val PETITION_TRANSFER_ID = "petition_transfer_id"

// Referrals
const val SELECT_REFER = "select_refer"
const val REFERRALS_CONTACTS = "referrals_contacts"
const val REFERRALS_IMPORT = "referrals_import"
const val REFERRALS_SHARE = "referral_share"
const val PARAM_REFERRALS_SHARE = "banner"

// Phone
const val VERIFY_PHONE_CODE = "verify_phone_code"

//home
const val HOME = "home"
const val WIDGET_ID = "widget_id"
const val WIDGET_CATEGORY = "widget_category"

// Actions
const val ACTION_KEY = "action"
const val ASSIST_CHECK_ORDERS = "check_orders"
const val ASSIST_ORDER_FOOD = "order_food"
const val ASSIST_PAY_TRANSFER = "pay_transfer"
const val ASSISTANT_PAY_BALANCE = "pay_balance"


const val CONTACT = "contact"

const val USER_ID = "userId"
const val URL = "https://www.rappi.com/?"
const val WHATSAPP_URL = "api.whatsapp.com"
const val REFERRING_LINK = "~referring_link"
const val RAPPI_SCHEME = "gbrappi"
const val DIAL_SCHEME = "tel"

const val GO_GRIN = "grin"
const val GO_RENTBRELLA = "rentbrella"

const val EDIT_ORDER_ID = "edit_order_id"

/**
 * Deep links supported
 *
 * https://www.rappi.com?goto=home&widget_id={home_widget_id}
 * https://www.rappi.com?goto=home&widget_category={home_widget_category}
 * https://www.rappi.com?goto=select_refer
 * https://www.rappi.com?goto=rappy_pay
 * https://www.rappi.com?goto=credit_cards
 * https://www.rappi.com?goto=rappi_gold
 * https://www.rappi.com?goto=help_center
 * https://www.rappi.com?goto=help_center&order_id={any_order_id}&article_id={any_article_id}
 * https://www.rappi.com?goto=help_center&category_id={any_category_id}
 * https://www.rappi.com?goto=rappi_prime
 * https://www.rappi.com?goto=profile
 * https://www.rappi.com?goto=favorites
 * https://www.rappi.com?goto=orders.in_progress&order_id={any_order_id}
 * https://www.rappi.com?goto=recommendations&section=restaurant
 * https://www.rappi.com?goto=recommendations&section=market
 * https://www.rappi.com?goto=recommendations&section=other
 * https://www.rappi.com?store_type=restaurant&brand_name=kokoriko
 * https://www.rappi.com?store_type=restaurant
 * https://www.rappi.com?store_type=restaurant&tag_id=1
 * https://www.rappi.com?store_type=restaurant&category_id=1
 * https://www.rappi.com?store_type=restaurant&productsIds=<productId1>,<productI2>&brand_name=<brand_name>&orderId=<orderId>
 * https://www.rappi.com?store_type={any_story_type}
 * https://www.rappi.com?store_type={any_story_type}&search_key={key}
 * https://www.rappi.com?store_type=market&market_type=farmatodo (edited)
 * https://www.rappi.com?store_type=market&market_type=hiper
 * https://www.rappi.com?store_type=market&market_type=super
 * https://www.rappi.com?store_type=market&market_type={any_market_story_type}
 * https://www.rappi.com?store_type=market&market_type={any_market_story_type}&corridors_ids="1,2,3,5"
 * https://www.rappi.com?store_type=market&market_type={any_market_story_type}&sub_corridors_ids="1,2,3,5"
 * https://www.rappi.com?store_type=market&market_type={any_market_story_type}&product_id="1,2,3,5"
 * https://www.rappi.com?store_type=market&market_type={any_market_story_type}&show_fidelity={true}
 * https://www.rappi.com?store_type=market&show_recipes={true}&trademark={trademark}&campaign_id={campaign_id}"
 * https://www.rappi.com?edit_order_id={123}"
 * https://www.rappi.com?goto=loyalty
 * https://www.rappi.com?goto=loyalty&section=levels
 * https://www.rappi.com?goto=loyalty&section=missions
 * https://www.rappi.com?goto=loyalty&section=info
 * https://www.rappi.com?goto=loyalty&section=bonus
 *
 * */

interface DeeplinkController {

    fun manageParams(
            referringParams: JSONObject = JSONObject(),
            error: Any? = null,
            rawUrl: String? = null,
            isAssisting: Boolean? = false,
            bundle: Bundle = Bundle()
    ): Observable<Intent>
}
