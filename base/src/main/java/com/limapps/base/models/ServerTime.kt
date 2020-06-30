package com.limapps.base.models

import com.google.gson.annotations.SerializedName
import org.threeten.bp.ZoneId

data class ServerTime(@SerializedName("timestamp") var timestamp: Long = 0,
                      @SerializedName("day") val day: String = "",
                      @SerializedName("hour") var hour: String = "",
                      @SerializedName("offset") val offset: String = "",
                      @SerializedName("timezone") val serverZone: ServerZone = ServerZone(ZoneId.systemDefault().id))
