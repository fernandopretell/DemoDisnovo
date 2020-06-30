package com.limapps.base.di

import android.app.Application
import android.content.Context
import com.limapps.base.InjectableApplication
import com.limapps.base.di.qualifiers.*
import com.limapps.base.handlers.AddressController
import com.limapps.base.handlers.HomeTreatmentsController
import com.limapps.base.interfaces.DistanceController
import com.limapps.base.interfaces.RappiBroadcast
import com.limapps.base.location.LocationProvider
import com.limapps.base.maps.controllers.DirectionsController
import com.limapps.base.maps.controllers.PlaceController
import com.limapps.base.maps.controllers.StaticMapsController
import com.limapps.base.maps.di.MapStyle
import com.limapps.base.maps.di.QueryComponent
import com.limapps.base.maps.di.TokenId
import com.limapps.base.others.IResourceProvider
import com.limapps.base.persistence.preferences.PreferencesManager
import com.limapps.base.remoteconfig.RemoteConfigManager
import com.limapps.base.rest.interceptors.UrlInterceptor
import com.limapps.base.rest.retrofit.services.PlacesService
import com.limapps.base.utils.Logger
import dagger.android.AndroidInjector
import okhttp3.Interceptor
import retrofit2.Retrofit
import javax.inject.Named

interface BaseComponent : AndroidInjector<InjectableApplication> {

    /**
     * Analytics APi
     */
    //fun analyticsLogger(): AnalyticsAction

    //fun analyticsConfig(): AnalyticsConfig

    //fun mainFunnel(): MainFunnel

   // fun getHomeWidgetAnalyticsHelper(): HomeWidgetAnalyticsHelper //This probably is  not necessary

    /**
     * UxCamObfuscator Api
     */
   // fun uxCamObfuscator(): UxCamObfuscator

    /**
     * Split APi
     */
    //fun splitAnalytics(): SplitAnalytics

//    fun splitTreatmentManager(): SplitTreatmentManager


    /**
     * Firebase Api
     */

    //val firebaseHandler: FirebaseHandler

    fun remoteConfigManager(): RemoteConfigManager


    /**
     *  CoreApi
     */
    fun context(): Context

    fun application(): Application

    //fun gson(): Gson

    fun resourceProvider(): IResourceProvider

    //fun logger(): Logger

    fun preferencesManager(): PreferencesManager

    fun urlInterceptor(): UrlInterceptor

    //fun foregroundBackgroundListener(): ForegroundBackgroundListener

    //fun pushNotificationController(): PushNotificationController

    fun getRappiBroadCast(): RappiBroadcast

   // fun baseDataProvider(): BaseDataProvider

    /**
     * User Api
     */
    //fun userController(): UserController

    /**
     * Country Api
     */

    //fun countryDataProvider(): CountryDataProvider

    /**
     * Payment Api
     */

    //val creditCardController: CreditCardController

   // val cardsVerificationController: CardsVerificationController

    /**
     * Account Api
     */
   // fun phoneVerificationController(): PhoneVerificationController

    //fun accountNavigator(): AccountNavigator

    /*@Named(HAS_PRIME_ACTIVE)
    fun hasPrimeActive(): Boolean*/

    fun homeTreatmentsController(): HomeTreatmentsController

    /**
     * Address Api
     */
    fun addressController(): AddressController

    fun locationProvider(): LocationProvider

    //fun countriesAndCitiesRepository(): CountriesAndCitiesRepository

    //fun getCoverageRepository(): CoverageRepository

    /**
     * Stores APi
     */
    //val storesController: StoresController

    /**
     * Pay APi
     */

    /**
     * Coupons APi
     */
    //fun couponsController(): CouponsController


    /**
     * Referral and Loyalty Api
     */
    //val referralCodeController: ReferralCodeController

    //val loyaltyBaseController: LoyaltyBaseController


    /**
     * rappiCredits Api
     */
    //val rappiCreditsController: RappiCreditsController


    /**
     * Whims Api
     */
    //val whimController: WhimController


    /**
     * Courier Api
     */
    //val courierController: CourierController

    /**
     * Debt Api
     */
   // val debtController: DebtController

    /**
     * Distance Api
     */
    val distanceController: DistanceController


    /**
     * Maps and Places APi
     */
    val staticMapsController: StaticMapsController

    @TokenId
    fun getTokenId(): String

    /*@QueryComponent
    fun getMapsComponent(): String*/

    @MapStyle
    fun mapStyle(): String

    fun getPlaceController(): PlaceController


    /**
     * Deeplinks Api
     */
    //fun getDeepLinkController(): DeeplinkController

    /**
     * Etas Api
     */
    fun getDirectionsController(): DirectionsController

    //val componentProviders: Map<String, ComponentProvider>

    /**
     * Support api
     */
   // fun getSupportCenterController(): SupportCenterController

    //TODO this will probably die



    // TODO verify if is really necessary expose this bindings
    @AppLoggingInterceptors
    fun getAppLoggingInterceptor(): Interceptor

    //@Named(RAPPI_AUTHENTICATOR)
    //fun getRappiAuthenticator(): Authenticator

    @AuthorizationInterceptors
    fun getAuthorizationInterceptors(): ArrayList<Interceptor>

    @MicroService
    fun retrofitMicroService(): Retrofit

    @CentralizedService
    fun retrofitCentralized(): Retrofit

    @SwitchServices
    fun retrofitGeneralClient(): Retrofit

    @BasePath
    fun retrofitBasePath(): Retrofit

//    @BasePath
//    fun informationService(): InformationService
//
//    @MicroService
//    fun informationMicroService(): InformationService
//
//    @SwitchServices
//    fun generalClientInformationService(): InformationService
//
//    @BasePath
//    fun userService(): UserService
//
//    @MicroService
//    fun userMicroService(): UserMicroService
//
//    @MicroService
//    fun storeService(): StoresService

    @SwitchServices
    fun placesServices(): PlacesService

    //@MicroService
    //fun abProxyService(): AbProxyService
    //TODO end
}