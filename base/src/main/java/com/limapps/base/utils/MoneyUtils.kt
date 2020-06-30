package com.limapps.base.utils

import com.limapps.base.persistence.preferences.PreferencesManager
import com.limapps.common.tryOrDefault
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

const val DOLLAR_SYMBOL = "$"
const val REAL_SYMBOL = "R$"
const val SOL_SYMBOL = "S/"
const val EURO_SYMBOL = "€"
const val POUND_SYMBOL = "£"
const val COLON_SYMBOL = "₡"

private const val MONEY_FORMAT = "#,###,###,###.##;-#,###,###,###.##"

fun String.getPriceCleanValue(countryCode: String = PreferencesManager.countryCodeData().get()): String {
    val countryCurrency = getCountryCurrency(countryCode)
    return this
            .replace(REAL_SYMBOL, "")
            .replace(DOLLAR_SYMBOL, "")
            .replace(EURO_SYMBOL, "")
            .replace(POUND_SYMBOL, "")
            .replace(SOL_SYMBOL, "")
            .replace(COLON_SYMBOL, "")
            .replace("\\s".toRegex(), "")
            .replace(countryCurrency.groupingSeparator.toString(), "")
            .replace(countryCurrency.decimalSeparator.toString(), ".")
            .trim()
}

fun String.getDoublePriceCleanValue(countryCode: String = PreferencesManager.countryCodeData().get()): Double {
    return this.getPriceCleanValue(countryCode).toDouble()
}

fun getCountryCurrency(countryCode: String): CountryCurrency {
    return when (countryCode) {
        ARGENTINA_CODE -> CountryCurrency.ARGENTINA
        BRAZIL_CODE -> CountryCurrency.BRAZIL
        MEXICO_CODE -> CountryCurrency.MEXICO
        COLOMBIA_CODE -> CountryCurrency.COLOMBIA
        CHILE_CODE -> CountryCurrency.CHILE
        URUGUAY_CODE -> CountryCurrency.URUGUAY
        PERU_CODE -> CountryCurrency.PERU
        COSTA_RICA_CODE -> CountryCurrency.COSTA_RICA
        ECUADOR_CODE -> CountryCurrency.ECUADOR
        else -> CountryCurrency.DEFAULT
    }
}

fun String.removeMoneyFormat(currencySymbol: String): String =
    this.replace("[\$R$currencySymbol]".toRegex(), "")

fun String.parseNumberFormat(currencySymbol: String, decimalSeparator: String, groupingSeparator: String): Double {
    val newAmount = removeMoneyFormat("$currencySymbol$groupingSeparator")
    val locale: Locale = if (decimalSeparator == ",") {
        Locale("es", "AR")
    } else {
        Locale.US
    }
    val numberFormat = NumberFormat.getInstance(locale)
    return tryOrDefault({ numberFormat.parse(newAmount) }, 0.0).toDoubleNotRound()
}

fun Number.toDoubleNotRound(): Double{
    return Math.floor(this.toDouble() * 100) / 100.0
}

fun Double.invertSign(): Double {
    return -1 * this
}

/*fun Double.formatDoubleToCurrency(countryDataProvider: CountryDataProvider): String {
    val countryCode = countryDataProvider.getCountryCode()
    return priceToString(countryCode)
}*/

@JvmOverloads
fun Double.priceToString(countryCode: String = PreferencesManager.countryCodeData().get(),
                         omitDecimals: Boolean = false,
                         omitAdjustPriceValue: Boolean = false,
                         currency: CountryCurrency = getCountryCurrency(countryCode),
                         maxDecimals: Int = currency.maxDecimals,
                         minDecimals: Int = currency.minDecimals
): String {
    val copyPrice = this.adjustPriceValue(countryCode, omitAdjustPriceValue)
    val currencySymbol = currency.currencySymbol

    val symbols = DecimalFormatSymbols(Locale.US).apply {
        decimalSeparator = currency.decimalSeparator
        groupingSeparator = currency.groupingSeparator
    }
    val moneyFormat = DecimalFormat(MONEY_FORMAT, symbols).apply {
        maximumFractionDigits = maxDecimals
        minimumFractionDigits = minDecimals
    }

    if (omitDecimals) {
        moneyFormat.maximumFractionDigits = 0
    }

    val sign = if (copyPrice < 0) "-" else ""
    return "$sign$currencySymbol${moneyFormat.format(Math.abs(copyPrice))}"
}

@JvmOverloads
fun Double.toPriceDirectly(countryCode: String = PreferencesManager.countryCodeData().get(),
                           fractionDigits: Int): String {
    val currency = getCountryCurrency(countryCode)

    val symbols = DecimalFormatSymbols(Locale.US).apply {
        decimalSeparator = currency.decimalSeparator
        groupingSeparator = currency.groupingSeparator
    }
    val moneyFormat = DecimalFormat(MONEY_FORMAT, symbols).apply {
        maximumFractionDigits = fractionDigits
        minimumFractionDigits = fractionDigits
    }

    return moneyFormat.format(Math.abs(this))
}

@JvmOverloads
fun String.priceWithDecimals(countryCode: String = PreferencesManager.countryCodeData().get()): Double {
    val currency = getCountryCurrency(countryCode)
    return parseNumberFormat(currency.currencySymbol, currency.decimalSeparator.toString(), currency.groupingSeparator.toString())
}

private fun Double.adjustPriceValue(countryCode: String, omitAdjustPriceValue: Boolean): Double {
    return when {
        countryCode == MEXICO_CODE && omitAdjustPriceValue.not() -> this.roundValue()
        else -> this
    }
}

private fun Double.roundValue() = Math.round(this * 2) / 2.0

fun getCurrentCurrencySymbol(countryCode: String = PreferencesManager.countryCodeData().get()): String {
    return getCurrencySymbol(countryCode)
}

private fun getCurrencySymbol(countryCode: String): String {
    return when (countryCode) {
        BRAZIL_CODE -> "$REAL_SYMBOL "
        PERU_CODE -> "$SOL_SYMBOL "
        COSTA_RICA_CODE -> "$COLON_SYMBOL "
        else -> DOLLAR_SYMBOL
    }
}