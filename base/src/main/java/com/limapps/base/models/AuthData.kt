package com.limapps.base.models

import com.limapps.base.models.UserInfoModel

data class AuthData(val loginType: String,
                    val firstLogin: Boolean,
                    val userData: UserInfoModel)