package com.limapps.base.models

data class PhoneVerificationModel(val method: String,
                                  val phone: String,
                                  val countryCode: String,
                                  val socialId: String,
                                  val accessToken: String)