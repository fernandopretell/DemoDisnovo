package com.limapps.base.others

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import com.limapps.base.BaseConfig
import com.limapps.base.checkout.models.CheckoutDeliveryInfo
import com.limapps.base.checkout.models.FavorSummary
import com.limapps.base.checkout.models.WebViewCheckoutToken
import com.limapps.base.creditcards.ModelCreditCard
import com.limapps.base.creditcards.ScreenOrigin
import com.limapps.base.credits.RappiCredits
import com.limapps.base.deepLink.CAMPAIGN_ID
import com.limapps.base.deepLink.RAPPI_CREDITS
import com.limapps.base.deepLink.SHOW_FIDELITY
import com.limapps.base.deepLink.SHOW_RECIPES
import com.limapps.base.deepLink.TRADEMARK
import com.limapps.base.home.BannerType
import com.limapps.base.home.analytics.SOURCE_TYPE
import com.limapps.base.models.Whim
import com.limapps.base.models.store.StoreDetail
import com.limapps.base.models.store.StoreModelArgs
import com.limapps.base.models.store.StoreTypeModelV2
import com.limapps.base.models.store.StoreTypeModelV3
import com.limapps.base.models.store.toV3
import com.limapps.base.others.ExtrasKeys.ARTICLE
import com.limapps.base.others.ExtrasKeys.BALANCE
import com.limapps.base.others.ExtrasKeys.BANNER_TITLE
import com.limapps.base.others.ExtrasKeys.BANNER_TYPE
import com.limapps.base.others.ExtrasKeys.CATEGORY_NAME
import com.limapps.base.others.ExtrasKeys.CHAT_NAME
import com.limapps.base.others.ExtrasKeys.CHAT_TYPE
import com.limapps.base.others.ExtrasKeys.CONTACT
import com.limapps.base.others.ExtrasKeys.COUNTRY_CODE
import com.limapps.base.others.ExtrasKeys.COUPON_TO_REDEEM
import com.limapps.base.others.ExtrasKeys.CURRENT_WHIM
import com.limapps.base.others.ExtrasKeys.DELIVERY_INFO
import com.limapps.base.others.ExtrasKeys.EDIT_ORDER
import com.limapps.base.others.ExtrasKeys.FAVOR_SUMMARY
import com.limapps.base.others.ExtrasKeys.FOUND_URL
import com.limapps.base.others.ExtrasKeys.GO_TO_ADD_ADDRESS
import com.limapps.base.others.ExtrasKeys.HIDE_TOOLBAR
import com.limapps.base.others.ExtrasKeys.HIDE_WHIM
import com.limapps.base.others.ExtrasKeys.IS_FROM_HOME
import com.limapps.base.others.ExtrasKeys.IS_FROM_NOTIFICATION
import com.limapps.base.others.ExtrasKeys.IS_ONBOARDING_REGISTER_ENABLED
import com.limapps.base.others.ExtrasKeys.IS_SINGLE_TASK
import com.limapps.base.others.ExtrasKeys.LOAD_COUPON
import com.limapps.base.others.ExtrasKeys.MACHINE_CODE
import com.limapps.base.others.ExtrasKeys.MESSAGE_TRANSACTION_PENDING
import com.limapps.base.others.ExtrasKeys.NATIVE_CHECKOUT
import com.limapps.base.others.ExtrasKeys.OPEN_CHAT_KEY
import com.limapps.base.others.ExtrasKeys.ORDER_ID
import com.limapps.base.others.ExtrasKeys.ORIGIN_DEBT
import com.limapps.base.others.ExtrasKeys.PAY_SECTION
import com.limapps.base.others.ExtrasKeys.PETITION_TRANSFER_ID
import com.limapps.base.others.ExtrasKeys.PSE_AMOUNT
import com.limapps.base.others.ExtrasKeys.PSE_TIME
import com.limapps.base.others.ExtrasKeys.QR_CODE
import com.limapps.base.others.ExtrasKeys.REQUIRE_USER
import com.limapps.base.others.ExtrasKeys.S3_BASE_URL
import com.limapps.base.others.ExtrasKeys.SCHEDULE
import com.limapps.base.others.ExtrasKeys.SCOOTER_CODE
import com.limapps.base.others.ExtrasKeys.SEARCH_KEY
import com.limapps.base.others.ExtrasKeys.SHOW_CUSTOM_LOADERS
import com.limapps.base.others.ExtrasKeys.SHOW_REFRESH
import com.limapps.base.others.ExtrasKeys.SHOW_TOAST_COUPON_ACTIVATED
import com.limapps.base.others.ExtrasKeys.SOURCE
import com.limapps.base.others.ExtrasKeys.STEP_BY_STE_FLOW
import com.limapps.base.others.ExtrasKeys.STOREKEEPER_CHAT_KEY
import com.limapps.base.others.ExtrasKeys.STORES
import com.limapps.base.others.ExtrasKeys.STORE_CATEGORY
import com.limapps.base.others.ExtrasKeys.STORE_GROUP
import com.limapps.base.others.ExtrasKeys.STORE_ID
import com.limapps.base.others.ExtrasKeys.STORE_LOGO
import com.limapps.base.others.ExtrasKeys.STORE_MODEL_ARGS
import com.limapps.base.others.ExtrasKeys.STORE_NAME
import com.limapps.base.others.ExtrasKeys.STORE_SUB_GROUP
import com.limapps.base.others.ExtrasKeys.STORE_TYPE
import com.limapps.base.others.ExtrasKeys.STORE_TYPE_MODEL
import com.limapps.base.others.ExtrasKeys.STORE_TYPE_NAME
import com.limapps.base.others.ExtrasKeys.SUPPORT_APP_SCREEN_ID
import com.limapps.base.others.ExtrasKeys.SUPPORT_CATEGORY_ID
import com.limapps.base.others.ExtrasKeys.SUPPORT_CHAT_KEY
import com.limapps.base.others.ExtrasKeys.SUPPORT_CONVERSATION
import com.limapps.base.others.ExtrasKeys.SUPPORT_DEFAULT_SCREEN_ID
import com.limapps.base.others.ExtrasKeys.SUPPORT_HELPER_ID
import com.limapps.base.others.ExtrasKeys.SUPPORT_SECTION_ID
import com.limapps.base.others.ExtrasKeys.SUPPORT_TYPE_TRANSACTION
import com.limapps.base.others.ExtrasKeys.TITLE_MODAL
import com.limapps.base.others.ExtrasKeys.TRANSACTION_AMOUNT
import com.limapps.base.others.ExtrasKeys.TRANSACTION_CURRENCY
import com.limapps.base.others.ExtrasKeys.URL_TAG
import com.limapps.base.others.ExtrasKeys.USE_CURRENT_LOCATION_SHORTCUT
import com.limapps.base.others.ExtrasKeys.WEBVIEW_TOKEN
import com.limapps.base.others.ExtrasKeys.WEB_VIEW_TITLE
import com.limapps.base.others.ExtrasKeys.WHIM
import com.limapps.base.others.ExtrasSources.EMPTY_SOURCE
import com.limapps.base.rest.URL_KEY
import com.limapps.base.support.ZenkdeskSectionsItems
import com.limapps.base.support.model.ConversationV2
import com.limapps.base.support.model.OrderInfoSupport
import com.limapps.base.support.model.WhimPendingConfirmation
import com.limapps.base.utils.CountryUtil
import com.limapps.base.utils.HOME_TYPE_BY_STORES
import com.limapps.base.utils.StringUtils

const val creditCardIntentV2 = "creditcards.v2"
const val creditCardIntentAdd = "creditcards.add"
const val selectPaymentMethod = "payment.methods"
const val creditCardAddSelectionIntent = "creditcards.add.selection"
const val verifyCreditCardIntent = "cardverification.verify"
const val creditCardIntentCheck = "creditcards.check"
const val setcvvIntent = "creditcards.setcvv"
const val splashIntent = "splash"
const val newHomeIntent = "home.new"
const val homeOnboardingIntent = "home.onboarding"
const val termsAndConditions = "terms.and.conditions"
const val bannersHomeIntent = "home.banners"
const val promotionsBanner = "promotions.banners"


const val supportHistoryV2 = "support.history.v2"
const val supportHelperIntentV2 = "support.helper.v2"
const val supportArticleDetailV2 = "support.detail.article.v2"
const val supportHelpCenterV2 = "support.center.v2"
const val supportTicketDetailV2 = "ticket.detail.v2"
const val supportSectionsIntent = "support.sections"
const val supportArticleIntent = "support.article"
const val supportFlow = "supportFlow.newFlow"
const val supportButtonIntent = "support.float.button"
const val supportLastOrdersIntent = "lastorders"
const val supportCenterLiveIntent = "live.typification"

const val chatIntent = "chat"
const val goldIntent = "gold"
const val goldWebIntent = "gold.web"
const val restaurantStoreIntent = "restaurants"
const val cancelledByPartnetIntent = "restaurants.cancelled"
const val primeIntent = "prime"
const val pseIntent = "pse"
const val pseCheckoutIntent = "pse.checkout"
const val primeSubscribedIntent = "prime.subscribed"
const val primeTrialSubscribedIntent = "prime.trial.subscribed"
const val primeTrialIntent = "prime.trial"
const val primeSubscribeDialogIntent = "prime.one.click.subscription"
const val whimsIntent = "whims"
const val whimConfirmationIntent = "whim.confirmation"
const val checkoutIntent = "checkout"
const val checkoutIntentV2 = "checkoutv2"
const val ultraserviceIntent = "ultraservice"
const val favorIntent = "favor"
const val orderSummaryIntent = "orders.summary"
const val marketIntent = "market"
const val newOrderScheduledIntent = "market.scheduled"
const val marketEditOrderIntent = "market.editorder"
const val marketSlotsIntent = "market.slots"
const val marketOrderSubstitution = "market.order.substitutions"
const val rappiPayRouter = "pay.router"
const val rappiPayCashIn = "pay.cashin"
const val rappiPayInvestRouter = "invest.router"
const val rappiPayOtp = "pay.otp"
const val orderInProgressIntent = "orders.in_progress"
const val newOrderInProgressIntent = "orders.eta"
const val currentOrdersIntent = "orders.current_orders"
const val chatHistoryIntent = "chat.chat_history"
const val storesIntent = "market.stores"
const val onboardingIntent = "onboarding"
const val signinActivityV2Intent = "signin.activity.v2"
const val sectionsIntent = "market.lanes"
const val profileIntent = "profile"
const val newWebViewIntent = "newWebView"
const val phoneVerificationIntent = "login.phoneVerification"
const val phoneVerificationCodeIntent = "login.phoneVerification.code"
const val phoneVerificationDialogIntent = "phoneverification.dialog"
const val orderSubstitutionsIntent = "orders.substitutions"
const val debtsIntent = "debts"
const val paySplitFareIntent = "pay.split"
const val payInStoreIntent = "pay.store"
const val notificationIntent = "notification"
const val rappiCreditsIntent = "rappicredits"
const val promotionsIntent = "promotions"
const val tutorialIntent = "market.tutorial"

const val restaurantsSearchIntent = "restaurants.search"
const val restaurantDetailIntent = "restaurants.store_detail"
const val cancellationNoticeIntent = "cancellation.notice"
const val insuranceIntent = "insurance"
const val paypalIntent = "paypal"
const val edenredIntent = "edenred"
const val paypalAgreementIntent = "paypal.agreement"
const val grinIntent = "grin"
const val rentbrellaIntent = "rentbrella"
const val grinChatIntent = "grin.chat"
const val redeemCouponIntent = "coupons.redeem"
const val paymentCouponIntent = "payment.coupon"
const val rappiusaIntent = "usa"
const val loyaltyIntent = "loyalty"
const val loyaltyRewardsIntent = "loyalty.rewards"
const val loyaltyRankingHistoryIntent = "loyalty.ranking"
const val youtubePlayerIntent = "player"
const val newsFeedIntent = "newsfeed"
const val outOfCoverageIntent = "outofcoverage"
const val offersIntent = "offers"
const val locationRequestIntent = "location.request"
const val addressesIntent = "addresses"
const val postOrderIntent = "marketpostorder.PostOrderService"
const val taxisHomeIntent = "taxi.home"
const val goTaxiTag = "go_taxi"
const val couponAddCardIntent = "coupons.add"
const val couponPrimeSelectPayment = "coupons.select.payment"
const val couponPrimeSubscribed = "coupons.prime.subscribed"
const val goGrinTag = "grin"
const val goRentBrellaTag = "rentbrella"
const val payOnBoarding = "pay.onboarding"
const val fraud = "fraud"
const val webPay = "webPay"
const val goBillingIntent = "home.billing"
const val globalBasket = "globalBasket"
const val goTransmilenio = "pay.transmilenio"

// Referrals
const val referralShareIntent = "referral.share"
const val referralShareModalIntent = "referral.share.modal"


//intents mi bodeguita
const val seguimientoPedidos = "home.menu.lateral"

private fun getIntent(filter: String): Intent {
    return Intent().apply { action = "${BaseConfig.INTENT_PATH}.$filter" }
}

fun getSectionsIntent(): Intent {
    return getIntent(sectionsIntent)
}

fun getGrinIntent(code: String? = null, fromSection: String? = null): Intent {
    val intent = getIntent(grinIntent)
    code?.let { intent.putExtra(SCOOTER_CODE, code) }
    fromSection?.let { intent.putExtra(PAY_SECTION, fromSection) }
    return intent
}

fun getRentbrellaIntent(code: String? = null): Intent {
    val intent = getIntent(rentbrellaIntent)
    code?.let { intent.putExtra(MACHINE_CODE, code) }
    return intent
}

fun getBillingIntent(): Intent {
    return getIntent(goBillingIntent)
}

fun getGrinChatIntent(): Intent {
    return getIntent(grinChatIntent)
}

fun getSplashIntent() = getIntent(splashIntent)

fun getNotificationIntent() = getIntent(notificationIntent)

fun getRappiCreditsIntent(credits: RappiCredits? = null): Intent {
    return getIntent(rappiCreditsIntent)
            .putExtra(RAPPI_CREDITS, credits)
}

fun getPromotionsIntent() = getIntent(promotionsIntent)

fun getOnboardingIntent() = getIntent(onboardingIntent)

fun getSigninActivityV2Intent() = getIntent(signinActivityV2Intent)

fun getPayInStoreIntent(source: String): Intent {
    return getIntent(payInStoreIntent).apply {
        putExtra(SOURCE, source)
    }
}

fun getRappiPayRouterIntent(
        qrCode: String? = null,
        section: String? = null,
        countryCode: String? = null,
        isFromHome: Boolean = false,
        isNotification: Boolean = false,
        transactionAmount: Double? = null,
        transactionCurrency: String? = null,
        petitionTransferId: String? = null,
        contact: String? = null): Intent {
    return getIntent(rappiPayRouter).apply {
        putExtra(QR_CODE, qrCode)
        putExtra(PAY_SECTION, section)
        putExtra(IS_FROM_NOTIFICATION, isNotification)
        putExtra(IS_FROM_HOME, isFromHome)
        putExtra(TRANSACTION_AMOUNT, transactionAmount)
        putExtra(TRANSACTION_CURRENCY, transactionCurrency)
        putExtra(COUNTRY_CODE, countryCode)
        putExtra(PETITION_TRANSFER_ID, petitionTransferId)
        putExtra(CONTACT, contact)
    }
}

fun getRappiPayCashInIntent(balance: Double, amount: Double): Intent {
    return getIntent(rappiPayCashIn).apply {
        putExtra(BALANCE, balance)
        putExtra(TRANSACTION_AMOUNT, amount)
    }
}

fun getTaxisHomeIntent() = getIntent(taxisHomeIntent)

fun getRappiPayInvestRouterIntent(): Intent = getIntent(rappiPayInvestRouter)

fun getRappiPayTransmilenio(): Intent = getIntent(goTransmilenio)

fun getRappiPayOtpIntent(): Intent = getIntent(rappiPayOtp)

fun getTermsAndConditionsIntent(): Intent {
    return getIntent(termsAndConditions)
}

fun getHomeIntent(): Intent {
    return getIntent(newHomeIntent)
}

fun getChangeLocationIntent(storeType: String, source: String): Intent {
    return getIntent(addressesIntent).apply {
        putExtra(STORE_TYPE_NAME, storeType)
        putExtra(SOURCE, source)
    }
}

fun getBannersHomeIntent(title: String, type: BannerType): Intent {
    return getIntent(bannersHomeIntent).apply {
        putExtra(BANNER_TYPE, type)
        putExtra(BANNER_TITLE, title)
    }
}

fun getPromotionsBannerIntent(title: String, type: BannerType): Intent {
    return getIntent(promotionsBanner).apply {
        putExtra(BANNER_TYPE, type)
        putExtra(BANNER_TITLE, title)
    }
}

fun getReferralShareIntent() = getIntent(referralShareIntent)

fun getReferralShareModalIntent() = getIntent(referralShareModalIntent)

fun getChatIntent() = getIntent(chatIntent)

fun getGoldIntent() = getIntent(goldIntent)

fun getWebGoldIntent() = getIntent(goldWebIntent)

fun getProfileIntent(bundle: Bundle? = null): Intent {
    return getIntent(profileIntent).apply { if (bundle != null) putExtras(bundle) }
}

fun getRestaurantIntent(storeType: String?, source: String? = null): Intent {
    val intent = getIntent(restaurantStoreIntent).putExtra(STORE_TYPE, storeType)
    source?.let { intent.putExtra(SOURCE, it) }
    return intent
}

//todo eliminar esta funcion cuando se termine de eliminar storetypemodelv2
fun getRestaurantIntent(storeTypeModelV2: StoreTypeModelV2, source: String? = null): Intent {
    return getRestaurantIntent(storeTypeModelV2.storeType, source)
}

fun getPrimeIntent() = getIntent(primeIntent)

fun getPseIntent(amount: Double, messageTransactionPending: String): Intent {
    return getIntent(pseIntent).apply {
        putExtra(PSE_AMOUNT, amount)
        putExtra(MESSAGE_TRANSACTION_PENDING, messageTransactionPending)
    }
}

fun getPseCheckoutIntent(amountValue: Double, time: Long): Intent {
    return getIntent(pseCheckoutIntent).apply {
        putExtra(PSE_AMOUNT, amountValue)
        putExtra(PSE_TIME, time)
    }
}

fun getPrimeSubscribed() = getIntent(primeSubscribedIntent)

fun getPrimeTrialSubscribed() = getIntent(primeTrialSubscribedIntent)

fun getPrimeTrialIntent() = getIntent(primeTrialIntent)

fun getPrimeSubscribeDialogIntent(deliveryPrice: String): Intent {
    return getIntent(primeSubscribeDialogIntent).apply {
        putExtra(ExtrasKeys.DELIVERY_PRICE, deliveryPrice)
    }
}

fun getWhimsIntent(
        where: String? = null,
        what: String? = null,
        source: String? = null,
        sourceType: String? = null
): Intent {
    return getIntent(whimsIntent)
            .apply {
                putExtra(ExtrasKeys.WHIMS_WHERE, where)
                putExtra(ExtrasKeys.WHIMS_WHAT, what)
                putExtra(SOURCE, source)
                putExtra(SOURCE_TYPE, sourceType)
            }
}

fun getCurrentOrdersIntent() = getIntent(currentOrdersIntent)

fun getFavorIntent(storeTypeModelV2: StoreTypeModelV2, isSingleTask: Boolean = false): Intent {
    return getIntent(favorIntent).apply {
        putExtra(STORE_TYPE, storeTypeModelV2)
        putExtra(IS_SINGLE_TASK, isSingleTask)
    }
}

fun getStoresIntent(storeTypeModelV2: StoreTypeModelV2): Intent {
    return getIntent(storesIntent)
            .putExtra(STORE_TYPE, storeTypeModelV2.storeType)
            .putExtra(STORE_GROUP, storeTypeModelV2.group)
            .putExtra(STORE_SUB_GROUP, storeTypeModelV2.subGroup)
}

fun getOrdersDetailIntent(orderId: String): Intent {
    return getIntent(orderSummaryIntent).putExtra(ORDER_ID, orderId)
}

fun getCheckoutIntent(
        storeTypeModelV3: StoreTypeModelV3,
        checkoutDeliveryInfo: CheckoutDeliveryInfo? = null,
        favorSummary: FavorSummary? = null,
        whim: Whim? = null): Intent {
    val storeType = storeTypeModelV3.storeType
    val isSchedule = storeTypeModelV3.scheduled
    return getCheckoutIntent(storeType,
            isSchedule,
            checkoutDeliveryInfo,
            favorSummary,
            whim)
}

fun getCheckoutIntent(
        storeTypeModelV2: StoreTypeModelV2,
        checkoutDeliveryInfo: CheckoutDeliveryInfo? = null,
        favorSummary: FavorSummary? = null,
        whim: Whim? = null): Intent {
    return getCheckoutIntent(storeTypeModelV2.toV3(),
            checkoutDeliveryInfo,
            favorSummary,
            whim)
}

fun getCheckoutIntent(storeType: String? = null,
                      isSchedule: Boolean = false,
                      checkoutDeliveryInfo: CheckoutDeliveryInfo? = null,
                      favorSummary: FavorSummary? = null,
                      whim: Whim? = null,
                      webToken: WebViewCheckoutToken? = null): Intent {
    return getIntent(getCheckoutPath()).apply {
        putExtra(STORE_TYPE, storeType)
        putExtra(SCHEDULE, isSchedule)
        putExtra(DELIVERY_INFO, checkoutDeliveryInfo)
        putExtra(FAVOR_SUMMARY, favorSummary)
        putExtra(CURRENT_WHIM, whim)
        putExtra(WEBVIEW_TOKEN, webToken)
    }
}

fun getCheckoutIntent(): Intent {
    return getIntent(getCheckoutPath())
}

fun getCashIntent(storeTypeModelV2: StoreTypeModelV2): Intent {
    return getIntent(ultraserviceIntent).putExtra(ExtrasKeys.STORE_TYPE_MODEL, storeTypeModelV2)
}

fun getInsuranceIntent(storeTypeModelV2: StoreTypeModelV2): Intent {
    return getIntent(insuranceIntent).putExtra(ExtrasKeys.STORE_TYPE_MODEL, storeTypeModelV2)
}

fun resolveHomeType(store: StoreTypeModelV2,
                    productsIds: ArrayList<String>? = arrayListOf(),
                    corridors: ArrayList<String> = arrayListOf(),
                    subCorridors: ArrayList<String> = arrayListOf(),
                    sharedProductId: String? = null,
                    source: String? = null,
                    showRecipes: String? = null,
                    trademark: String? = null,
                    campaignId: String? = null,
                    shouldShowFidelity: Boolean = false): Intent {

    return when (store.homeType) {
        HOME_TYPE_BY_STORES -> getRestaurantIntent(store)
        else -> getMarketIntent(store,
                productsIds,
                corridors,
                subCorridors,
                sharedProductId,
                source,
                showRecipes,
                trademark,
                campaignId,
                shouldShowFidelity)
    }
}

fun getMarketIntent(): Intent {
    return getIntent(marketIntent)
}

private fun getMarketIntent(storeModel: StoreTypeModelV2,
                            productsIds: ArrayList<String>? = arrayListOf(),
                            corridors: ArrayList<String> = arrayListOf(),
                            subCorridors: ArrayList<String> = arrayListOf(),
                            sharedProductId: String? = null,
                            source: String?,
                            showRecipes: String?,
                            trademark: String?,
                            campaignId: String?,
                            shouldShowFidelity: Boolean = false): Intent {

    return when {
        storeModel.subOptions.isEmpty() -> getIntent(marketIntent).apply {
            putExtra(SHOW_FIDELITY, shouldShowFidelity)
            applyBundle(storeModel.storeType, storeModel, productsIds, corridors, subCorridors, sharedProductId, source)
        }
        storeModel.subOptions.size == 1 -> getIntent(marketIntent).apply {
            putExtra(SHOW_FIDELITY, shouldShowFidelity)
            applyBundle(storeModel.storeType, storeModel.subOptions[0], productsIds, corridors, subCorridors, sharedProductId, source)
        }
        else -> getStoresIntent(storeModel).apply {
            if (showRecipes != null)
                putExtra(SHOW_RECIPES, showRecipes)
            if (trademark != null)
                putExtra(TRADEMARK, trademark)
            if (campaignId != null)
                putExtra(CAMPAIGN_ID, campaignId)
        }
    }
}

private fun Intent.applyBundle(
    parentStoreType: String?,
    storeTypeModel: StoreTypeModelV2,
    productsIds: ArrayList<String>?,
    corridors: ArrayList<String>,
    subCorridors: ArrayList<String>,
    sharedProductId: String?,
    source: String?) {

    val storeModelArgs = StoreModelArgs(
            storeTypeModel.stores.firstOrNull()?.id ?: "-1",
            storeTypeModel.storeType,
            storeTypeModel.homeType,
            storeTypeModel.name,
            storeTypeModel.scheduled,
            storeTypeModel.group,
            storeTypeModel.subGroup)
    putExtra(STORE_TYPE, parentStoreType)
    putExtra(STORE_MODEL_ARGS, storeModelArgs)

    putExtras(Bundle().apply {
        if (source.isNullOrEmpty()) {
            putExtra(SOURCE, ExtrasKeys.SOURCE_DEEPLINK)
        } else {
            putExtra(SOURCE, source)
        }
        if (productsIds?.isNotEmpty() == true)
            putExtra(ExtrasKeys.PRODUCTS_IDS, productsIds)
        if (corridors.isNotEmpty())
            putStringArrayList(ExtrasKeys.CORRIDOR_IDS, corridors)
        if (subCorridors.isNotEmpty())
            putStringArrayList(ExtrasKeys.SUB_CORRIDOR_IDS, subCorridors)
        if (sharedProductId != null)
            putString(ExtrasKeys.SHARED_PRODUCT_ID, sharedProductId)
    })
}

internal fun getNewOrderInProgressIntent(orderId: String,
                                         opensChat: Boolean = false,
                                         opensSupport: Boolean = false): Intent {
    return getEtaIntent(newOrderInProgressIntent, orderId, opensChat, opensSupport)
}

private fun getEtaIntent(placeHolder: String,
                         orderId: String,
                         opensChat: Boolean,
                         opensSupport: Boolean): Intent {
    return getIntent(placeHolder)
            .apply {
                putExtra(ORDER_ID, orderId)
                if (opensChat) {
                    putExtra(OPEN_CHAT_KEY, if (opensSupport) SUPPORT_CHAT_KEY else STOREKEEPER_CHAT_KEY)
                }
            }
}

fun getCancelledByPartnerIntent(storeLogo: String, categoryName: String, categoryId: String, storeName: String, storeId: String)
        : Intent {
    return getIntent(cancelledByPartnetIntent)
            .apply {
                putExtra(STORE_LOGO, storeLogo)
                putExtra(CATEGORY_NAME, categoryName)
                putExtra(STORE_NAME, storeName)
                putExtra(STORE_CATEGORY, categoryId)
                putExtra(STORE_ID, storeId.toInt())
            }
}

fun getCreditCardIntentV2(source: String = "", skipPayPal: Boolean = false): Intent {
    return getIntent(creditCardIntentV2).apply {
        putExtra(SOURCE, source)
        putExtra(ExtrasKeys.SKIP_PAYPAL, skipPayPal)
    }
}

fun getAddCreditCardIntent(stepByStepFlow: Boolean = false): Intent {
    return getIntent(creditCardIntentAdd).apply {
        putExtra(STEP_BY_STE_FLOW, stepByStepFlow)
    }
}

fun getSelectPaymentMethodIntent(storeType: String,
                                 storeId: String,
                                 price: Double,
                                 origin: ScreenOrigin = ScreenOrigin.APP): Intent {
    return getIntent(selectPaymentMethod).apply {
        putExtra(ExtrasKeys.SCREEN_ORIGIN, origin.value)
        putExtra(STORE_TYPE, storeType)
        putExtra(STORE_ID, storeId)
        putExtra(ExtrasKeys.TOTAL_PRICE, price)
    }
}

fun getAddSelectionCreditCardIntent(paypalDirectly: Boolean = false): Intent {
    return getIntent(creditCardAddSelectionIntent).apply {
        putExtra(ExtrasKeys.ADD_PAYPAL, paypalDirectly)
    }
}

fun getVerifyCreditCardIntent(cardReference: String): Intent {
    return getIntent(verifyCreditCardIntent).apply {
        putExtra(ExtrasKeys.CREDIT_CARD, cardReference)
    }
}

fun getCheckCreditCardIntent() = getIntent(creditCardIntentCheck)

fun getSetCvvIntent(creditCard: ModelCreditCard): Intent {
    return getIntent(setcvvIntent)
            .apply {
                putExtra(ExtrasKeys.CREDIT_CARD, creditCard)
            }
}

fun getChatHistoryIntent(orderId: String, chatType: String, chatName: String): Intent {
    return getIntent(chatHistoryIntent)
            .apply {
                putExtra(ORDER_ID, orderId)
                putExtra(CHAT_TYPE, chatType)
                putExtra(CHAT_NAME, chatName)
            }
}

fun getSubstitutionsIntent(orderId: String): Intent {
    return getIntent(orderSubstitutionsIntent).apply { putExtra(ORDER_ID, orderId) }
}

fun getNewWebViewIntent(url: String,
                        title: String? = null,
                        foundUrl: String? = null,
                        requireUser: Boolean = false,
                        hideToolbar: Boolean = false,
                        loadCoupon: Boolean = false,
                        source: String = "",
                        showCustomLoaders: Boolean = false,
                        showRefresh: Boolean = false,
                        nativeCheckout: Boolean = false,
                        storeType: String = ""): Intent {
    return getIntent(newWebViewIntent).apply {
        putExtra(URL_TAG, url)
        putExtra(WEB_VIEW_TITLE, title)
        putExtra(FOUND_URL, foundUrl)
        putExtra(REQUIRE_USER, requireUser)
        putExtra(HIDE_TOOLBAR, hideToolbar)
        putExtra(LOAD_COUPON, loadCoupon)
        putExtra(SOURCE, source)
        putExtra(SHOW_CUSTOM_LOADERS, showCustomLoaders)
        putExtra(SHOW_REFRESH, showRefresh)
        putExtra(NATIVE_CHECKOUT, nativeCheckout)
        putExtra(STORE_TYPE, storeType)
    }
}

fun getDebtsIntent(origin: String = StringUtils.EMPTY_STRING): Intent {
    return getIntent(debtsIntent).apply {
        putExtra(ORIGIN_DEBT, origin)
    }
}

fun getTutorialIntent() = getIntent(tutorialIntent)

fun getMarketEditOrderIntent() = getIntent(marketEditOrderIntent)

fun getRestaurantsSearchIntent(storeType: String, storesToSearch: List<StoreDetail>): Intent {
    return getIntent(restaurantsSearchIntent).apply {
        putExtra(STORE_TYPE, storeType)
        putParcelableArrayListExtra(STORES, ArrayList(storesToSearch))
    }
}

fun getRestaurantDetailIntent(storeType: String): Intent {
    return getIntent(restaurantDetailIntent).apply {
        putExtra(STORE_TYPE, storeType)
    }
}

fun getCancellationNoticeIntent(): Intent {
    return getIntent(cancellationNoticeIntent)
}

fun getSupportFlowIntent(): Intent {
    return getIntent(supportFlow)
}

fun getPhoneVerificationIntent(): Intent {
    return getIntent(phoneVerificationIntent)
}

fun getPhoneVerificationIntent(phoneCode: String, phoneNumber: String, loginMethod: String, uuid: String, message: String, secondsToExpire: Long): Intent {
    val intent = getIntent(phoneVerificationCodeIntent)
    val bundle = Bundle()
    bundle.putString(ExtrasKeys.PHONE_CODE, phoneCode)
    bundle.putString(ExtrasKeys.LOGIN_METHOD, loginMethod)
    bundle.putString(ExtrasKeys.PHONE_NUMBER, phoneNumber)
    bundle.putString(ExtrasKeys.UUID, uuid)
    bundle.putString(ExtrasKeys.MESSAGE, message)
    bundle.putLong(ExtrasKeys.SECONDS_TO_EXPIRE, secondsToExpire)
    intent.putExtra(ExtrasKeys.VERIFICATION_CODE_INFO, bundle)
    return intent
}

fun getPhoneVerificationDialogIntent(): Intent {
    return getIntent(phoneVerificationDialogIntent)
}

fun getPayPalIntent(url: String): Intent {
    return getIntent(paypalIntent).apply {
        putExtra(URL_KEY, url)
    }
}

fun getEdenredIntent(): Intent {
    return getIntent(edenredIntent)
}

fun getPayPalAgreementIntent(): Intent {
    return getIntent(paypalAgreementIntent)
}

fun getFloatButtonIntent(): Intent {
    return getIntent(supportButtonIntent)
}

fun getAppSettingsIntent(packageName: String): Intent {
    return Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        data = uri
    }
}

fun getOldPrimePaymentCoupon() = getIntent(paymentCouponIntent)

fun getRappiUsaIntent(storeTypeModelV2: StoreTypeModelV2): Intent {
    return getIntent(rappiusaIntent).putExtra(STORE_TYPE_MODEL, storeTypeModelV2)
}

fun getLoyaltyIntent() = getIntent(loyaltyIntent)

fun getLoyaltyRewardsIntent() = getIntent(loyaltyRewardsIntent)

fun getNewsFeedIntent() = getIntent(newsFeedIntent)

fun getLoyaltyRankingHistoryIntent() = getIntent(loyaltyRankingHistoryIntent)

fun getOutOfCoverageIntent(address: String,
                           latitude: Double,
                           longitude: Double): Intent {
    return getIntent(outOfCoverageIntent).apply {
        putExtra(ExtrasKeys.ADDRESS, address)
        putExtra(ExtrasKeys.LATITUDE, latitude)
        putExtra(ExtrasKeys.LONGITUDE, longitude)
    }
}

fun getYoutubePlayerIntent(url: String, landscapeEnabled: Boolean = false): Intent {
    val intent = getIntent(youtubePlayerIntent)
    intent.putExtra(ExtrasKeys.URL, url)
    intent.putExtra(ExtrasKeys.LANDSCAPE_ENABLED, landscapeEnabled)
    return intent
}

fun getLocationRequestIntent(title: String): Intent {
    return getIntent(locationRequestIntent).apply {
        putExtra(TITLE_MODAL, title)
    }
}

fun getOffersIntent(source: String = EMPTY_SOURCE, code: String? = null): Intent {
    return getIntent(offersIntent).apply {
        putExtra(SOURCE, source)
        if (code != null) putExtra(COUPON_TO_REDEEM, code)
    }
}

fun getAddressesIntent(useCurrentLocationShortcut: Boolean = false, goToAddAddress: Boolean = false): Intent {
    val intent = getIntent(addressesIntent)
    intent.putExtra(USE_CURRENT_LOCATION_SHORTCUT, useCurrentLocationShortcut)
    intent.putExtra(GO_TO_ADD_ADDRESS, goToAddAddress)
    return intent
}

fun newOrderScheduledIntent(orderId: String, source: String? = null, editOrder: Boolean? = false): Intent {
    return getIntent(newOrderScheduledIntent)
            .putExtra(ORDER_ID, orderId)
            .putExtra(SOURCE, source)
            .putExtra(EDIT_ORDER, editOrder)
}

fun getWhimConfirmationIntent(whimPendingConfirmation: WhimPendingConfirmation, hide: Boolean = false): Intent {
    return getIntent(whimConfirmationIntent)
            .putExtra(WHIM, whimPendingConfirmation)
            .putExtra(HIDE_WHIM, hide)
}

fun getSupportCenterLiveIntent(orderId: String, supportType: String? = null, funnelSource: String? = null): Intent {
    return getIntent(supportCenterLiveIntent).apply {
        putExtra(ORDER_ID, orderId)
        supportType?.let {
            putExtra(ExtrasKeys.SUPPORT_TYPE, supportType)
        }
        funnelSource.let {
            putExtra(ExtrasKeys.FUNNEL_SOURCE, funnelSource)
        }
    }
}

fun getSupportHistoryIntent(): Intent {
    return getIntent(supportHistoryV2)
}

fun getSupportIntent(
        itemId: String? = "",
        orderInfoSupport: OrderInfoSupport? = null,
        zendeskItemType: ZenkdeskSectionsItems
): Intent {
    return getIntent(supportSectionsIntent).apply {
        putExtra(ExtrasKeys.ZENDESK_ITEM_ID, itemId)
        putExtra(ExtrasKeys.ZENDESK_LOAD_ITEMS_TYPE, zendeskItemType)
        putExtra(ExtrasKeys.ORDER_INFO, orderInfoSupport)
        putExtra(ARTICLE, itemId)
        orderInfoSupport?.let {
            putExtra(ORDER_ID, it.orderId)
        }
    }
}

fun supportArticleIntent(itemId: String? = "", orderId: String? = ""): Intent {
    return getIntent(supportArticleIntent).apply {
        putExtra(ARTICLE, itemId)
        putExtra(ORDER_ID, orderId)
    }
}

@Deprecated("Please use getSupportHelperIntentV2")
fun getSupportIntent(
        itemId: Long? = 0,
        appScreenId: String?,
        zendeskItemType: ZenkdeskSectionsItems = ZenkdeskSectionsItems.SECTION
): Intent {
    return getIntent(supportSectionsIntent).apply {
        putExtra(ExtrasKeys.ZENDESK_ITEM_ID, itemId)
        putExtra(ExtrasKeys.ZENDESK_LOAD_ITEMS_TYPE, zendeskItemType)
        appScreenId?.let {
            putExtra(ExtrasKeys.APP_SCREEN_ID, it)
        }
    }
}

fun lastOrdersIntent(title: String?): Intent {
    return getIntent(supportLastOrdersIntent).apply {
        title?.let { putExtra(WEB_VIEW_TITLE, it) }
    }
}

fun getSupportHelperIntentV2(id: String? = null,
                             appScreenId: String? = SUPPORT_DEFAULT_SCREEN_ID,
                             categoryId: String? = null,
                             sectionId: String? = null,
                             typeTransaction: String? = null): Intent {
    return getIntent(supportHelperIntentV2).apply {
        id?.let {
            putExtra(SUPPORT_HELPER_ID, it)
        }
        appScreenId?.let {
            putExtra(SUPPORT_APP_SCREEN_ID, it)
        }
        sectionId?.let {
            putExtra(SUPPORT_SECTION_ID, it)
        }
        typeTransaction?.let {
            putExtra(SUPPORT_TYPE_TRANSACTION, it)
        }
        categoryId?.let {
            putExtra(SUPPORT_CATEGORY_ID, it)
        }
    }
}

fun getSupportDetailArticleV2(articleId: String? = null,
                              helperId: String? = null,
                              articleUrl: String? = null): Intent {
    return getIntent(supportArticleDetailV2).apply {
        articleId?.let { putExtra(ARTICLE, it) }
        helperId?.let { putExtra(SUPPORT_HELPER_ID, it) }
        articleUrl?.let { putExtra(URL_KEY, it) }
    }
}

fun getSupportHelpCenterHomeV2(source: String): Intent {
    return getIntent(supportHelpCenterV2)
            .apply {
                putExtra(ExtrasKeys.KEY_ORIGIN_SOURCE, source)
            }
}

fun getTicketDetailSupportV2(conversation: ConversationV2? = null, orderId: String? = null): Intent {
    return getIntent(supportTicketDetailV2).apply {
        conversation?.let {
            putExtra(SUPPORT_CONVERSATION, it)
        }
        orderId?.let {
            putExtra(ORDER_ID, it)
        }
    }
}

fun Context.getPostOrderServiceIntent(action: String, orderId: String, storeType: String, storeId: String): Intent {
    return getIntent(action).apply {
        setPackage(packageName)
        putExtra(ORDER_ID, orderId)
        putExtra(STORE_TYPE, storeType)
        putExtra(STORE_ID, storeId)
    }
}

fun getMarketSlotsIntent(orderId: String): Intent {
    return getIntent(marketSlotsIntent).putExtra(ORDER_ID, orderId)
}

fun getCouponSelectCardIntent(): Intent {
    return getIntent(couponAddCardIntent)
}

fun getCouponPrimeSelectPaymentIntent(showToast: Boolean = true): Intent {
    return getIntent(couponPrimeSelectPayment).apply {
        putExtra(SHOW_TOAST_COUPON_ACTIVATED, showToast)
    }
}

fun getCouponPrimeSubscribedIntent(): Intent {
    return getIntent(couponPrimeSubscribed)
}

/**
 * Check orders app action intent [refer to g.co/AppActions for more details]
 */
fun checkOrdersActionIntent(title: String?): Intent {
    return getIntent(supportLastOrdersIntent).apply {
        title?.let {
            putExtra(WEB_VIEW_TITLE, it)
        }
    }
}

/**
 * Order food action intent [refer to g.co/AppActions for more details]
 */
fun orderFoodActionIntent(searchKey: String, source: String? = null): Intent {
    return getRestaurantIntent(StoreTypeConstants.RESTAURANT).apply {
        putExtra(SEARCH_KEY, searchKey)
    }
}

/**
 * Send money by amount and name destination app action intent [refer to g.co/AppActions for more details]
 */
fun payActionIntent(qrCode: String? = null,
                    section: String? = null,
                    isFromHome: Boolean = false,
                    isNotification: Boolean = false,
                    transactionAmount: Double? = null,
                    transactionCurrency: String? = null,
                    contact: String? = null
): Intent {
    return getIntent(rappiPayRouter).apply {
        putExtra(QR_CODE, qrCode)
        putExtra(PAY_SECTION, section)
        putExtra(IS_FROM_NOTIFICATION, isNotification)
        putExtra(IS_FROM_HOME, isFromHome)
        putExtra(TRANSACTION_AMOUNT, transactionAmount)
        putExtra(TRANSACTION_CURRENCY, transactionCurrency)
        putExtra(CONTACT, contact)
    }
}

fun getOnBoardingIntent(s3BaseUrl: String?, registerEnabled: Boolean): Intent {
    return getIntent(payOnBoarding).apply {
        putExtra(S3_BASE_URL, s3BaseUrl)
        putExtra(IS_ONBOARDING_REGISTER_ENABLED, registerEnabled)
    }
}

fun getFraudActivity(code: String): Intent {
    return getIntent(fraud).apply {
        putExtra(ExtrasKeys.SIFT_FRAUD_CODE, code)
    }
}

fun getWebPayIntent(): Intent {
    return getIntent(webPay)
}

fun getGlobalBasketIntent(source: String): Intent {
    return getIntent(globalBasket).apply {
        putExtra(SOURCE, source)
    }
}

fun getMarketOrderSubstitution(orderId: String, storeModelArgs: StoreModelArgs): Intent {
    return getIntent(marketOrderSubstitution).apply {
        putExtra(ORDER_ID, orderId)
        putExtra(STORE_TYPE_MODEL, storeModelArgs)
    }
}

private fun getCheckoutPath(): String {
    return if (CountryUtil.checkoutNewVersion()) {
        checkoutIntentV2
    } else {
        checkoutIntent
    }
}

fun getSeguimientoPedidosIntent(): Intent {
    return getIntent(seguimientoPedidos)
}
