package com.limapps.base.utils

import org.junit.Test
import kotlin.test.assertEquals

const val CORRECT_COMMA_AMOUNT = "1,342,343.12"
const val WRONG_COMMA_AMOUNT = "1,342,34.12"
const val CORRECT_DOT_AMOUNT = "1.342.343,12"
const val WRONG_DOT_AMOUNT = "1.342.34,12"
const val CORRECT_NO_DECIMAL_AMOUNT = "1.342.343"
const val WRONG_NO_DECIMAL_AMOUNT = "1.342.34"
const val CORRECT_AMOUNT = 1342343.12
const val WRONG_AMOUNT = 134234.12
const val CORRECT_AMOUNT_NO_DECIMALS = 1342343.0
const val WRONG_AMOUNT_NO_DECIMALS = 134234.0

class MoneyUtilsTest {

    @Test
    fun getCleanValueFromColombianPesos() {
        assertEquals("4070", "$4.070".getPriceCleanValue(COLOMBIA_CODE))
    }

    @Test
    fun getCleanValueFromArgentinianPesos() {
        assertEquals("4070.89", "$4.070,89".getPriceCleanValue(ARGENTINA_CODE))
    }

    @Test
    fun getCleanValueFromMexicanPesos() {
        assertEquals("4070.89", "$4,070.89".getPriceCleanValue(MEXICO_CODE))
    }

    @Test
    fun getCleanValueFromBrazilianReal() {
        assertEquals("4070.89", "R$4.070,89".getPriceCleanValue(BRAZIL_CODE))
    }

    @Test
    fun formatCorrectlyArgentinianPesos() {
        assertEquals("$4.070,22", 4070.22.priceToString(ARGENTINA_CODE))
    }

    @Test
    fun formatCorrectlyColombianPesos() {
        assertEquals("$4.070", 4070.22.priceToString(COLOMBIA_CODE))
        assertEquals("-$4.071", (-4070.65).priceToString(COLOMBIA_CODE))
    }

    @Test
    fun formatCorrectlyMexicanPesos() {
        assertEquals("$4,070.50", 4070.65.priceToString(MEXICO_CODE))
    }

    @Test
    fun formatCorrectlyBrazilianReal() {
        assertEquals("R$4.070,65", 4070.65.priceToString(BRAZIL_CODE))
    }

    @Test
    fun omitDecimalsForArgentinianPesos() {
        assertEquals("$4.070", 4070.22.priceToString(ARGENTINA_CODE, true))
    }

    @Test
    fun omitDecimalsForBrazilianReal() {
        assertEquals("R$4.071", 4070.65.priceToString(BRAZIL_CODE, true))
    }

    @Test
    fun omitDecimalsForMexicanPesos() {
        assertEquals("$4,070", 4070.65.priceToString(MEXICO_CODE, true))
    }

    @Test
    fun argentinaNumberWithWrongSeparator() {
        val currency = getCountryCurrency(ARGENTINA_CODE)
        assertEquals(WRONG_AMOUNT, "$DOLLAR_SYMBOL$WRONG_DOT_AMOUNT".parseNumberFormat(
                currency.currencySymbol,
                currency.decimalSeparator.toString(),
                currency.groupingSeparator.toString()
        ))
    }

    @Test
    fun argentinaNumberToDouble() {
        val currency = getCountryCurrency(ARGENTINA_CODE)
        assertEquals(CORRECT_AMOUNT, "$DOLLAR_SYMBOL$CORRECT_DOT_AMOUNT".parseNumberFormat(
                currency.currencySymbol,
                currency.decimalSeparator.toString(),
                currency.groupingSeparator.toString()
        ))
    }

    @Test
    fun mexicoNumberWithWrongSeparator() {
        val currency = getCountryCurrency(MEXICO_CODE)
        assertEquals(WRONG_AMOUNT, "$DOLLAR_SYMBOL$WRONG_COMMA_AMOUNT".parseNumberFormat(
                currency.currencySymbol,
                currency.decimalSeparator.toString(),
                currency.groupingSeparator.toString()
        ))
    }

    @Test
    fun mexicoNumberToDouble() {
        val currency = getCountryCurrency(MEXICO_CODE)
        assertEquals(CORRECT_AMOUNT, "$DOLLAR_SYMBOL$CORRECT_COMMA_AMOUNT".parseNumberFormat(
                currency.currencySymbol,
                currency.decimalSeparator.toString(),
                currency.groupingSeparator.toString()
        ))
    }

    @Test
    fun colombiaNumberWithWrongSeparator() {
        val currency = getCountryCurrency(COLOMBIA_CODE)
        assertEquals(WRONG_AMOUNT_NO_DECIMALS, "$DOLLAR_SYMBOL$WRONG_NO_DECIMAL_AMOUNT".parseNumberFormat(
                currency.currencySymbol,
                currency.decimalSeparator.toString(),
                currency.groupingSeparator.toString()
        ))
    }

    @Test
    fun colombiaNumberToDouble() {
        val currency = getCountryCurrency(COLOMBIA_CODE)
        assertEquals(CORRECT_AMOUNT_NO_DECIMALS, "$DOLLAR_SYMBOL$CORRECT_NO_DECIMAL_AMOUNT".parseNumberFormat(
                currency.currencySymbol,
                currency.decimalSeparator.toString(),
                currency.groupingSeparator.toString()
        ))
    }

    @Test
    fun brazilNumberWithWrongSeparator() {
        val currency = getCountryCurrency(BRAZIL_CODE)
        assertEquals(WRONG_AMOUNT, "$REAL_SYMBOL$WRONG_DOT_AMOUNT".parseNumberFormat(
                currency.currencySymbol,
                currency.decimalSeparator.toString(),
                currency.groupingSeparator.toString()
        ))
    }

    @Test
    fun brazilNumberToDouble() {
        val currency = getCountryCurrency(BRAZIL_CODE)
        assertEquals(CORRECT_AMOUNT, "$REAL_SYMBOL$CORRECT_DOT_AMOUNT".parseNumberFormat(
                currency.currencySymbol,
                currency.decimalSeparator.toString(),
                currency.groupingSeparator.toString()
        ))
    }

    @Test
    fun chileNumberWithWrongSeparator() {
        val currency = getCountryCurrency(CHILE_CODE)
        assertEquals(WRONG_AMOUNT, "$DOLLAR_SYMBOL$WRONG_DOT_AMOUNT".parseNumberFormat(
                currency.currencySymbol,
                currency.decimalSeparator.toString(),
                currency.groupingSeparator.toString()
        ))
    }

    @Test
    fun chileNumberToDouble() {
        val currency = getCountryCurrency(CHILE_CODE)
        assertEquals(CORRECT_AMOUNT, "$DOLLAR_SYMBOL$CORRECT_DOT_AMOUNT".parseNumberFormat(
                currency.currencySymbol,
                currency.decimalSeparator.toString(),
                currency.groupingSeparator.toString()
        ))
    }

    @Test
    fun uruguayNumberWithWrongSeparator() {
        val currency = getCountryCurrency(URUGUAY_CODE)
        assertEquals(WRONG_AMOUNT, "$DOLLAR_SYMBOL$WRONG_DOT_AMOUNT".parseNumberFormat(
                currency.currencySymbol,
                currency.decimalSeparator.toString(),
                currency.groupingSeparator.toString()
        ))
    }

    @Test
    fun uruguayNumberToDouble() {
        val currency = getCountryCurrency(URUGUAY_CODE)
        assertEquals(CORRECT_AMOUNT, "$DOLLAR_SYMBOL$CORRECT_DOT_AMOUNT".parseNumberFormat(
                currency.currencySymbol,
                currency.decimalSeparator.toString(),
                currency.groupingSeparator.toString()
        ))
    }

    @Test
    fun peruNumberWithWrongSeparator() {
        val currency = getCountryCurrency(PERU_CODE)
        assertEquals(WRONG_AMOUNT, "$SOL_SYMBOL$WRONG_COMMA_AMOUNT".parseNumberFormat(
                currency.currencySymbol,
                currency.decimalSeparator.toString(),
                currency.groupingSeparator.toString()
        ))
    }

    @Test
    fun peruNumberToDouble() {
        val currency = getCountryCurrency(PERU_CODE)
        assertEquals(CORRECT_AMOUNT, "$SOL_SYMBOL$CORRECT_COMMA_AMOUNT".parseNumberFormat(
                currency.currencySymbol,
                currency.decimalSeparator.toString(),
                currency.groupingSeparator.toString()
        ))
    }

    @Test
    fun costaRicaNumberWithWrongSeparator() {
        val currency = getCountryCurrency(COSTA_RICA_CODE)
        assertEquals(WRONG_AMOUNT, "$COLON_SYMBOL$WRONG_COMMA_AMOUNT".parseNumberFormat(
                currency.currencySymbol,
                currency.decimalSeparator.toString(),
                currency.groupingSeparator.toString()
        ))
    }

    @Test
    fun costaRicaNumberToDouble() {
        val currency = getCountryCurrency(COSTA_RICA_CODE)
        assertEquals(CORRECT_AMOUNT, "$COLON_SYMBOL$CORRECT_COMMA_AMOUNT".parseNumberFormat(
                currency.currencySymbol,
                currency.decimalSeparator.toString(),
                currency.groupingSeparator.toString()
        ))
    }


}