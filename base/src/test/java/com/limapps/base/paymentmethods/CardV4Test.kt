package com.limapps.base.paymentmethods

import com.limapps.base.checkout.utils.CC
import com.limapps.base.checkout.utils.MASTERPASS_ORIGIN
import com.limapps.base.checkout.utils.VISA_CHECKOUT_ORIGIN
import org.junit.Assert
import org.junit.Test

class CardV4Test {

    private val emptyString = ""

    //region getPaymentType
    @Test
    fun `getPaymentType should return CC`() {
        val paymentType = CC
        val card = createCardV4()

        val paymentTypeObtained = card.getPaymentType()

        Assert.assertEquals(paymentType, paymentTypeObtained)
    }
    //endregion

    //region getReasonMessage
    @Test
    fun `getReasonMessage should return Empty when restrictionMessage null`() {
        val card = createCardV4().copy(restrictionMessage = null)

        val message = card.getReasonMessage()

        Assert.assertEquals(emptyString, message)
    }

    @Test
    fun `getReasonMessage should not return Null when restrictionMessage null`() {
        val card = createCardV4().copy(restrictionMessage = null)

        val message = card.getReasonMessage()

        Assert.assertNotNull(message)
    }

    @Test
    fun `getReasonMessage should return Empty when restrictionMessage is empty`() {
        val card = createCardV4().copy(restrictionMessage = emptyList())

        val message = card.getReasonMessage()

        Assert.assertEquals(emptyString, message)
    }

    @Test
    fun `getReasonMessage should return First Element when restrictionMessage has more than one`() {
        val firstElement = "First"
        val card = createCardV4().copy(restrictionMessage = listOf(firstElement, "Second", "Third"))

        val message = card.getReasonMessage()

        Assert.assertEquals(firstElement, message)
    }
    //endregion

    //region needsVerification
    @Test
    fun `needsVerification should return True when needVerification and available are true`() {
        val card = createCardV4().copy(needVerification = true, available = true)

        val needsVerification = card.needsVerification()

        Assert.assertTrue(needsVerification)
    }

    @Test
    fun `needsVerification should return False when needVerification is true and available is false`() {
        val card = createCardV4().copy(needVerification = true, available = false)

        val needsVerification = card.needsVerification()

        Assert.assertFalse(needsVerification)
    }

    @Test
    fun `needsVerification should return False when needVerification is false and available is true`() {
        val card = createCardV4().copy(needVerification = false, available = true)

        val needsVerification = card.needsVerification()

        Assert.assertFalse(needsVerification)
    }

    @Test
    fun `needsVerification should return False when needVerification and available are false`() {
        val card = createCardV4().copy(needVerification = false, available = false)

        val needsVerification = card.needsVerification()

        Assert.assertFalse(needsVerification)
    }
    //endregion

    //region getIdentifier
    @Test
    fun `getIdentifier should return id in String format`() {
        val id = 1
        val strId = id.toString()
        val card = createCardV4().copy(id = id)

        val identifier = card.getIdentifier()

        Assert.assertEquals(identifier, strId)
    }
    //endregion

    //region isDefault
    @Test
    fun `isDefault should return True when defaultCc and creditCardIsDefault are true`() {
        val card = createCardV4().copy(defaultCc = true, creditCardIsDefault = true)

        val isDefault = card.isDefault()

        Assert.assertTrue(isDefault)
    }

    @Test
    fun `isDefault should return False when defaultCc is false and creditCardIsDefault is true`() {
        val card = createCardV4().copy(defaultCc = false, creditCardIsDefault = true)

        val isDefault = card.isDefault()

        Assert.assertFalse(isDefault)
    }

    @Test
    fun `isDefault should return False when defaultCc is true and creditCardIsDefault is false`() {
        val card = createCardV4().copy(defaultCc = true, creditCardIsDefault = false)

        val isDefault = card.isDefault()

        Assert.assertFalse(isDefault)
    }

    @Test
    fun `isDefault should return False when defaultCc and creditCardIsDefault are false`() {
        val card = createCardV4().copy(defaultCc = false, creditCardIsDefault = false)

        val isDefault = card.isDefault()

        Assert.assertFalse(isDefault)
    }
    //endregion

    //region isAvailable
    @Test
    fun `isAvailable should return True when available is true`() {
        val card = createCardV4().copy(available = true)
        val isAvailable = card.isAvailable()

        Assert.assertTrue(isAvailable)
    }

    @Test
    fun `isAvailable should return False when available is false`() {
        val card = createCardV4().copy(available = false)
        val isAvailable = card.isAvailable()

        Assert.assertFalse(isAvailable)
    }
    //endregion

    //region isAvailableToSelect
    @Test
    fun `isAvailableToSelect should return True when available is true`() {
        val card = createCardV4().copy(available = true)

        val isAvailableToSelect = card.isAvailableToSelect()

        Assert.assertTrue(isAvailableToSelect)
    }

    @Test
    fun `isAvailableToSelect should return False when available is false`() {
        val card = createCardV4().copy(available = false)

        val isAvailableToSelect = card.isAvailableToSelect()

        Assert.assertFalse(isAvailableToSelect)
    }
    //endregion

    //region getUIType
    @Test
    fun `getUIType should return ITEM_CREDIT_CARD_METHOD`() {
        val itemCreditCardMethod = ITEM_CREDIT_CARD_METHOD
        val card = createCardV4()

        val uiType = card.getUIType()

        Assert.assertEquals(itemCreditCardMethod, uiType)
    }
    //endregion

    //region isVisaCheckout
    @Test
    fun `isVisaCheckout should return True when paymentOrigin is VISA_CHECKOUT_ORIGIN`() {
        val visaCheckoutOrigin = VISA_CHECKOUT_ORIGIN
        val card = createCardV4().copy(paymentOrigin = visaCheckoutOrigin)

        val isVisaCheckout = card.isVisaCheckout()

        Assert.assertTrue(isVisaCheckout)
    }

    @Test
    fun `isVisaCheckout should return False when paymentOrigin is MASTERPASS_ORIGIN`() {
        val visaCheckoutOrigin = MASTERPASS_ORIGIN
        val card = createCardV4().copy(paymentOrigin = visaCheckoutOrigin)

        val isVisaCheckout = card.isVisaCheckout()

        Assert.assertFalse(isVisaCheckout)
    }
    //endregion

    //region isMasterpass
    @Test
    fun `isMasterpass should return True when paymentOrigin is MASTERPASS_ORIGIN`() {
        val masterpassOrigin = MASTERPASS_ORIGIN
        val card = createCardV4().copy(paymentOrigin = masterpassOrigin)

        val isMasterpass = card.isMasterpass()

        Assert.assertTrue(isMasterpass)
    }

    @Test
    fun `isMasterpass should return False when paymentOrigin is VISA_CHECKOUT_ORIGIN`() {
        val masterpassOrigin = VISA_CHECKOUT_ORIGIN
        val card = createCardV4().copy(paymentOrigin = masterpassOrigin)

        val isMasterpass = card.isMasterpass()

        Assert.assertFalse(isMasterpass)
    }
    //endregion

    //region getDefaultInstallment
    @Test
    fun `getDefaultInstallment should return 0 when it is null`() {
        val installment = null
        val card = createCardV4().copy(defaultInstallment = installment)

        val defaultInstallment = card.getDefaultInstallment()

        Assert.assertSame(0, defaultInstallment)
    }

    @Test
    fun `getDefaultInstallment should return defaultInstallment when it is not null`() {
        val installment = 1
        val card = createCardV4().copy(defaultInstallment = installment)

        val defaultInstallment = card.getDefaultInstallment()

        Assert.assertSame(installment, defaultInstallment)
    }
    //endregion

    //region getAvailableListInstallments
    @Test
    fun `getAvailableListInstallments should return availableInstallments when it is not null nor empty`() {
        val installments = listOf(1, 3, 6)
        val card = createCardV4().copy(availableInstallments = installments)

        val availableInstallments = card.getAvailableListInstallments()

        Assert.assertSame(availableInstallments, installments)
    }

    @Test
    fun `getAvailableListInstallments should return empty list when is null`() {
        val card = createCardV4().copy(availableInstallments = null)

        val availableInstallments = card.getAvailableListInstallments()

        Assert.assertSame(emptyList<Int>(), availableInstallments)
    }

    @Test
    fun `getAvailableListInstallments should return empty list when availableInstallments is empty`() {
        val card = createCardV4().copy(availableInstallments = emptyList())

        val availableInstallments = card.getAvailableListInstallments()

        Assert.assertSame(emptyList<Int>(), availableInstallments)
    }
    //endregion

    private fun createCardV4(): CardV4 {
        return CardV4(
                false, false, "", "", "", false,
                "", "", "", "", false, 0, "",
                "", "", "", 0, false, "", false,
                false, false, null, false, 1, 1,
                false, 0, null, "", null, 0,
                null
        )
    }

}