package com.limapps.base.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.limapps.base.utils.DATE_FORMAT_FACEBOOK
import com.limapps.base.utils.DATE_FORMAT_SEPARATOR_MINUS
import com.limapps.base.utils.StringUtils
import com.limapps.base.utils.stringToCalendar
import org.json.JSONObject

const val ID = "id"
const val FIRST_NAME = "first_name"
const val LAST_NAME = "last_name"
const val EMAIL = "email"
const val GENDER = "gender"
const val BIRTHDAY = "birthday"
const val USER_BIRTHDAY = "user_birthday"
const val PERMISSION_MOBILE_PHONE = "user_mobile_phone"
const val DEFAULT_IDENTIFICATION = "1"

const val IDENTIFICATION_TYPE_CITIZEN = 1
const val IDENTIFICATION_TYPE_FOREIGN = 2


const val GENDER_MALE = "M"
const val GENDER_FEMALE = "F"

data class UserLoginRequest(@SerializedName("accessToken") val accessToken: String? = StringUtils.EMPTY_STRING,
                            @SerializedName("birth_date") val birthday: String? = StringUtils.EMPTY_STRING,
                            @SerializedName("email") val email: String? = StringUtils.EMPTY_STRING,
                            @SerializedName("first_name") val name: String? = StringUtils.EMPTY_STRING,
                            @SerializedName("gender") val gender: String? = StringUtils.EMPTY_STRING,
                            @SerializedName("global_phone_number") val globalPhone: String? = StringUtils.EMPTY_STRING,
                            @SerializedName("identification") val identification: String? = DEFAULT_IDENTIFICATION,
                            @SerializedName("identification_type") val identificationType: Int = IDENTIFICATION_TYPE_FOREIGN,
                            @SerializedName("last_name") val lastName: String? = StringUtils.EMPTY_STRING,
                            @SerializedName("phone") val phone: String? = StringUtils.EMPTY_STRING,
                            @SerializedName("sms_code") val smsCode: String? = StringUtils.EMPTY_STRING,
                            @SerializedName("social_network") val socialNetwork: SocialNetwork = SocialNetwork.FACEBOOK,
                            @SerializedName("social_id") val socialNetworkId: String? = StringUtils.EMPTY_STRING,
                            @SerializedName("username") val username: String? = StringUtils.EMPTY_STRING,
                            @SerializedName("password") val password: String? = StringUtils.EMPTY_STRING,
                            @SerializedName("country_code") val countryCode: String? = StringUtils.EMPTY_STRING,
                            @SerializedName("scope") val scope: String?= SCOPE) : Parcelable {

    constructor(userObject: JSONObject, accessToken: String) : this(
            accessToken = accessToken,
            birthday = getBirthday(userObject.optString(BIRTHDAY)),
            email = userObject.optString(EMAIL),
            name = userObject.optString(FIRST_NAME),
            gender = getGender(userObject.optString(GENDER)),
            lastName = userObject.optString(LAST_NAME),
            socialNetworkId = userObject.optString(ID))

    constructor(token: String, socialId: String, socialNetwork: SocialNetwork,
                firstName: String, lastName: String, email: String) : this(
        accessToken = token,
            socialNetworkId = socialId, socialNetwork = socialNetwork,
            name = firstName, lastName = lastName, email = email)

    constructor(token: String, phone: String, socialId: String, socialNetwork: SocialNetwork,
                countryCode: String) : this(
        accessToken = token, phone = phone,
            socialNetworkId = socialId, socialNetwork = socialNetwork,
            countryCode = getCountryCode(countryCode))

    constructor(userName: String, password: String) : this(
        username = userName, password = password,
            socialNetwork = SocialNetwork.DEFAULT)

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(accessToken)
        dest.writeString(birthday)
        dest.writeString(email)
        dest.writeString(name)
        dest.writeString(gender)
        dest.writeString(globalPhone)
        dest.writeString(identification)
        dest.writeInt(identificationType)
        dest.writeString(lastName)
        dest.writeString(phone)
        dest.writeString(smsCode)
        dest.writeString(socialNetwork.name)
        dest.writeString(socialNetworkId)
        dest.writeString(username)
        dest.writeString(password)
        dest.writeString(scope)
    }

    override fun describeContents(): Int = 0

    companion object {
        const val SCOPE = "all"
        const val MALE = "male"
        const val FEMALE = "female"

        fun getGender(gender: String): String {
            var verifiedGender = gender
            if (gender.isNotEmpty()) {
                if (gender.equals(MALE, true)) {
                    verifiedGender = GENDER_MALE
                } else if (gender.equals(FEMALE, true)) {
                    verifiedGender = GENDER_FEMALE
                }
            }
            return verifiedGender
        }

        fun getBirthday(birthday: String): String {
            if (birthday.isNotBlank()) {
                var verifiedBirthday = birthday
                stringToCalendar(birthday, DATE_FORMAT_FACEBOOK).let {
                    verifiedBirthday = StringUtils.dateToString(it, DATE_FORMAT_SEPARATOR_MINUS)
                }
                return verifiedBirthday
            }
            return birthday
        }

        fun getCountryCode(countryCode: String): String {
            return if (countryCode.startsWith("+")) {
                countryCode
            } else {
                "+$countryCode"
            }
        }

        @JvmField
        val CREATOR: Parcelable.Creator<UserLoginRequest> = object : Parcelable.Creator<UserLoginRequest> {
            override fun createFromParcel(parcelIn: Parcel): UserLoginRequest {
                return UserLoginRequest(parcelIn.readString(),
                        parcelIn.readString(),
                        parcelIn.readString(),
                        parcelIn.readString(),
                        parcelIn.readString(),
                        parcelIn.readString(),
                        parcelIn.readString(),
                        parcelIn.readInt(),
                        parcelIn.readString(),
                        parcelIn.readString(),
                        parcelIn.readString(),
                        SocialNetwork.valueOf(parcelIn.readString().toString()),
                        parcelIn.readString(),
                        parcelIn.readString(),
                        parcelIn.readString(),
                        parcelIn.readString())
            }

            override fun newArray(size: Int): Array<UserLoginRequest?> = arrayOfNulls(size)
        }
    }
}