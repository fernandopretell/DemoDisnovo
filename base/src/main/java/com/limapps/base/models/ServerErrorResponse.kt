package com.limapps.base.models

data class ServerErrorResponse(val message: String,
                               val code: String = "",
                               val severity: String = "",
                               val ordersWithDebt: String = "",
                               val url: String = "",
                               var ignoreAuth: Boolean = false,
                               var serverCode: String = "-1")
