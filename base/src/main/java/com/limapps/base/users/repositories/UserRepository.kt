package com.limapps.base.users.repositories

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.limapps.base.di.qualifiers.BasePath
import com.limapps.base.di.qualifiers.MicroService
import com.limapps.base.models.ACF_CLOSE_TIME
import com.limapps.base.models.ACF_DEFAULT_CLOSE_TIME
import com.limapps.base.models.ADDRESSES_SPLIT_NAME
import com.limapps.base.models.AbProxyRequest
import com.limapps.base.models.AbProxyResponse
import com.limapps.base.models.Attributes
import com.limapps.base.models.CORE_MENU_INVOICE
import com.limapps.base.models.CORE_UI_CONF_SPLIT_NAME
import com.limapps.base.models.HOME_GLOBAL_SEARCH_SPLIT_NAME
import com.limapps.base.models.HOME_LOCATION_SCREEN_SPLIT_NAME
import com.limapps.base.models.ADDRESS_NEW_UI_CONFIG_SPLIT_NAME
import com.limapps.base.models.UserInfoModel
import com.limapps.base.models.CORE_PHONE_VERIFICATION_METHOD
import com.limapps.base.others.RappiData
import com.limapps.base.persistence.preferences.PreferencesManager
import com.limapps.base.rest.constants.HttpConstants
import com.limapps.base.users.StateCreditRappi
import com.limapps.base.users.UserRappiCredit
//import com.limapps.base.users.saveInUserSettings
import com.limapps.base.users.services.AbProxyService
import com.limapps.base.users.services.UserMicroService
import com.limapps.base.users.services.UserService
import com.limapps.common.applySchedulers
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import javax.inject.Inject

class UserRepository @Inject constructor(
        //@BasePath private val service: UserService,
        //@MicroService private val microService: UserMicroService,
        //@MicroService private val abProxyService: AbProxyService,
        private val preferenceManager: PreferencesManager
        //private val gson: Gson
) {

    /*fun getUserRappiCredit(): Observable<UserRappiCredit> =
            service.getUserRappiCredit()
                    .doOnNext { it.saveInUserSettings() }
                    .applySchedulers()*/

    /*fun getHomeTreatments(userCountry: String): Single<ArrayList<AbProxyResponse>> {
        val features = arrayListOf(
                HOME_LOCATION_SCREEN_SPLIT_NAME,
                ADDRESSES_SPLIT_NAME,
                CORE_UI_CONF_SPLIT_NAME,
                CORE_MENU_INVOICE,
                ACF_CLOSE_TIME,
                ACF_DEFAULT_CLOSE_TIME,
                ADDRESS_NEW_UI_CONFIG_SPLIT_NAME,
                CORE_PHONE_VERIFICATION_METHOD
        )
        return abProxyService.getAbProxy(AbProxyRequest(
                features = features,
                attributes = Attributes(
                        country = userCountry,
                        appVersion = RappiData.appVersion
                )
        )).subscribeOn(Schedulers.io())
    }*/

   /* fun getHomeNewUITreatment(userCountry: String): Single<ArrayList<AbProxyResponse>> {
        val features = arrayListOf(
                HOME_GLOBAL_SEARCH_SPLIT_NAME
        )
        return abProxyService.getAbProxy(AbProxyRequest(
                features = features,
                attributes = Attributes(
                        country = userCountry,
                        appVersion = RappiData.appVersion
                )
        )).subscribeOn(Schedulers.io())
    }*/

    /*fun getInformation(): Single<UserInfoModel> {
        return microService.getInformation()
                .doOnSuccess { preferenceManager.setUserProfile(gson.toJson(it)) }
                .applySchedulers()
    }*/

   /* fun setStateRappiCredit(stateRappiCredit: Boolean): Single<JSONObject> {
        return service.setStateRappiCredit(StateCreditRappi(stateRappiCredit))
    }*/

   /* fun update(userInfoModel: UserInfoModel): Observable<UserInfoModel> {
        return service.update(userInfoModel).applySchedulers()
    }

    fun uploadProfilePicture(file: File): Observable<String> {
        val requestFile = RequestBody.create(MediaType.parse(HttpConstants.IMAGE_PNG), file)

        val body = MultipartBody.Part.createFormData("image", "profile_picture.png", requestFile)

        val part = MultipartBody.Part.createFormData("image", "profile_picture.png")

        return service.updatePicture(part, body).applySchedulers()
    }*/

    /*fun updatePhoneNumber(method: String, json: JsonObject): Single<Boolean> {
        return microService.updatePhoneNumber(method, RequestBody.create(MediaType.parse(HttpConstants.APPLICATION_JSON), json.toString()))
                .map { it.get("is_successful")?.asBoolean ?: false }
                .applySchedulers()
    }*/

}