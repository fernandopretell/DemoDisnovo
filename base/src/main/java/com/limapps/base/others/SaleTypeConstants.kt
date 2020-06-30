package com.limapps.base.others


object SaleTypeConstants {

    const val WEIGHT_WEIGHT = "WW"
    const val WEIGHT_UNIT = "WU"
    const val WEIGHT_BOTH = "WB"
    const val TRAY = "T"
    const val UNIT = "U"

    val saleTypes = listOf(
            SaleTypeConstants.UNIT,
            SaleTypeConstants.TRAY,
            SaleTypeConstants.WEIGHT_BOTH,
            SaleTypeConstants.WEIGHT_UNIT,
            SaleTypeConstants.WEIGHT_WEIGHT)
}
