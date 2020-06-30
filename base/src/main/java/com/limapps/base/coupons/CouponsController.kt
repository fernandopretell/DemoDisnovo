package com.limapps.base.coupons

import android.content.Intent
import android.os.Bundle
import com.limapps.base.models.Coupon
import com.limapps.base.models.CouponProduct
import com.limapps.base.models.Offer
import com.limapps.base.models.store.StoreTypeModelV2
import com.limapps.base.utils.Optional
import io.reactivex.Observable
import io.reactivex.Single

interface CouponsController {

    fun currentOffer(): Observable<Optional<Offer>>

    fun currentDeliveryOffer(): Observable<Optional<Offer>>

    fun currentCoupon(): Observable<Optional<Coupon>>

    fun currentCouponOrNull(): Coupon?

    fun clearCoupons()

    fun isUnderMinimumToRedeem(price: Double): Boolean

    fun updateCoupon(coupon: Coupon, source: String? = null): Boolean

    fun couponIsNotRedeem(coupon: Coupon): Boolean

    fun getCouponProducts(): Single<List<CouponProduct>>

    fun getMapCouponProducts(): Single<Map<String, CouponProduct>>

    fun getCouponSource(): String?

    fun getDeepLinkIntentByStoreType(storeTypeModel: StoreTypeModelV2, extras: Bundle = Bundle()): Observable<Intent>

    fun getDeepLinkIntent(coupon: Coupon): Observable<Intent>

    fun getDeepLinkIntent(offers: List<Offer>, storeTypeModel: StoreTypeModelV2?): Observable<Intent>

    fun disableCoupon(couponCode: String): Single<String>

    fun isNewFlowEnabled(): Boolean

    fun isCouponProgressEnabled(): Boolean

    fun isStoreTypeAlertEnabled(): Boolean
}