package com.limapps.base.paymentmethods

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.limapps.base.checkout.utils.RAPPI_PAY
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class PaymentMethodV4Test {

    private val emptyString = ""

    @Mock
    private lateinit var metadata: Metadata

    //region getIdentifier
    @Test
    fun `getIdentifier should return id`() {
        val paymentMethod = createPaymentMethodV4().copy(id = "1")
        val id = paymentMethod.id

        val identifier = paymentMethod.getIdentifier()

        Assert.assertEquals(id, identifier)
    }
    //endregion

    //region getDescription
    @Test
    fun `getDescription should return descriptionV4`() {
        val paymentMethod = createPaymentMethodV4().copy(descriptionV4 = "Tarjeta")
        val description = paymentMethod.descriptionV4

        val descriptionV4 = paymentMethod.getDescription()

        Assert.assertEquals(description, descriptionV4)
    }
    //endregion

    //region getReasonMessage
    @Test
    fun `getReasonMessage should return message when it is not null`() {
        val paymentMethod = createPaymentMethodV4().copy(message = "Mensaje")
        val message = paymentMethod.message

        val reasonMessage = paymentMethod.getReasonMessage()

        Assert.assertEquals(message, reasonMessage)
    }

    @Test
    fun `getReasonMessage should return empty when it is null`() {
        val paymentMethod = createPaymentMethodV4().copy(message = null)

        val reasonMessage = paymentMethod.getReasonMessage()

        Assert.assertEquals(emptyString, reasonMessage)
    }
    //endregion

    //region getPaymentIcon
    @Test
    fun `getPaymentIcon should return icon`() {
        val paymentMethod = createPaymentMethodV4().copy(icon = "Icono")
        val icon = paymentMethod.icon

        val paymentIcon = paymentMethod.getPaymentIcon()

        Assert.assertEquals(icon, paymentIcon)
    }
    //endregion

    //region getPaymentType
    @Test
    fun `getPaymentType should return id`() {
        val paymentMethod = createPaymentMethodV4().copy(id = "1")
        val id = paymentMethod.id

        val paymentType = paymentMethod.getPaymentType()

        Assert.assertEquals(id, paymentType)
    }
    //endregion

    //region isDefault
    @Test
    fun `isDefault should return true when default is true`() {
        val paymentMethod = createPaymentMethodV4().copy(default = true)

        val isDefault = paymentMethod.isDefault()

        Assert.assertTrue(isDefault)
    }

    @Test
    fun `isDefault should return false when default is false`() {
        val paymentMethod = createPaymentMethodV4().copy(default = false)

        val isDefault = paymentMethod.isDefault()

        Assert.assertFalse(isDefault)
    }
    //endregion

    //region isAvailable
    @Test
    fun `isAvailable should return true when available is true`() {
        val paymentMethod = createPaymentMethodV4().copy(available = true)

        val isAvailable = paymentMethod.isAvailable()

        Assert.assertTrue(isAvailable)
    }

    @Test
    fun `isAvailable should return false when available is false`() {
        val paymentMethod = createPaymentMethodV4().copy(available = false)

        val isAvailable = paymentMethod.isAvailable()

        Assert.assertFalse(isAvailable)
    }
    //endregion

    //region getUIType
    @Test
    fun `getUIType should return ITEM_PAY_METHOD when id equals RAPPI_PAY`() {
        val paymentMethod = createPaymentMethodV4().copy(id = RAPPI_PAY)

        val uiType = paymentMethod.getUIType()

        Assert.assertEquals(ITEM_PAY_METHOD, uiType)
    }

    @Test
    fun `getUIType should return ITEM_PAYMENT_METHOD when id is not equals RAPPI_PAY`() {
        val paymentMethod = createPaymentMethodV4().copy(id = "Any")

        val uiType = paymentMethod.getUIType()

        Assert.assertEquals(ITEM_PAYMENT_METHOD, uiType)
    }
    //endregion

    //region getFlow
    @Test
    fun `getFlow should return PAYPAL_WEBVIEW when flowType equals PAYPAL_WEBVIEW`() {
        val paymentMethod = createPaymentMethodV4().copy(flowType = FlowType.PAYPAL_WEBVIEW.value)

        val flowType = paymentMethod.getFlow()

        Assert.assertEquals(FlowType.PAYPAL_WEBVIEW, flowType)
    }

    @Test
    fun `getFlow should return RAPPIPAY_PSE_FORM when flowType equals RAPPIPAY_PSE_FORM`() {
        val paymentMethod = createPaymentMethodV4().copy(flowType = FlowType.RAPPIPAY_PSE_FORM.value)

        val flowType = paymentMethod.getFlow()

        Assert.assertEquals(FlowType.RAPPIPAY_PSE_FORM, flowType)
    }

    @Test
    fun `getFlow should return EDENRED_WEBVIEW when flowType equals EDENRED_WEBVIEW`() {
        val paymentMethod = createPaymentMethodV4().copy(flowType = FlowType.EDENRED_WEBVIEW.value)

        val flowType = paymentMethod.getFlow()

        Assert.assertEquals(FlowType.EDENRED_WEBVIEW, flowType)
    }

    @Test
    fun `getFlow should return CC_DEFAULT_FORM when flowType equals CC_DEFAULT_FORM`() {
        val paymentMethod = createPaymentMethodV4().copy(flowType = FlowType.CC_DEFAULT_FORM.value)

        val flowType = paymentMethod.getFlow()

        Assert.assertEquals(FlowType.CC_DEFAULT_FORM, flowType)
    }

    @Test
    fun `getFlow should return CC_STEP_BY_STEP when flowType equals CC_STEP_BY_STEP`() {
        val paymentMethod = createPaymentMethodV4().copy(flowType = FlowType.CC_STEP_BY_STEP.value)

        val flowType = paymentMethod.getFlow()

        Assert.assertEquals(FlowType.CC_STEP_BY_STEP, flowType)
    }

    @Test
    fun `getFlow should return SELECTION when flowType equals SELECTION`() {
        val paymentMethod = createPaymentMethodV4().copy(flowType = FlowType.SELECTION.value)

        val flowType = paymentMethod.getFlow()

        Assert.assertEquals(FlowType.SELECTION, flowType)
    }

    @Test
    fun `getFlow should return RAPPIPAY_CC_FORM when flowType equals RAPPIPAY_CC_FORM`() {
        val paymentMethod = createPaymentMethodV4().copy(flowType = FlowType.RAPPIPAY_CC_FORM.value)

        val flowType = paymentMethod.getFlow()

        Assert.assertEquals(FlowType.RAPPIPAY_CC_FORM, flowType)
    }
    //endregion

    //region isRechargeableMode
    @Test
    fun `isRechargeableMode should return true when id equals RAPPI_PAY and showAs equals RECHARGEABLE`() {
        val paymentMethod = createPaymentMethodV4().copy(id = RAPPI_PAY, showAs = RappiPayMode.RECHARGEABLE)

        val isRechargeableMode = paymentMethod.isRechargeableMode()

        Assert.assertTrue(isRechargeableMode)
    }

    @Test
    fun `isRechargeableMode should return true when id equals RAPPI_PAY and showAs equals DISCOUNT_RECHARGE`() {
        val paymentMethod = createPaymentMethodV4().copy(id = RAPPI_PAY, showAs = RappiPayMode.DISCOUNT_RECHARGE)

        val isRechargeableMode = paymentMethod.isRechargeableMode()

        Assert.assertTrue(isRechargeableMode)
    }

    @Test
    fun `isRechargeableMode should return false when id equals RAPPI_PAY and showAs not equals RECHARGEABLE nor DISCOUNT_RECHARGE`() {
        val paymentMethod = createPaymentMethodV4().copy(id = RAPPI_PAY, showAs = RappiPayMode.TOP)

        val isRechargeableMode = paymentMethod.isRechargeableMode()

        Assert.assertFalse(isRechargeableMode)
    }

    @Test
    fun `isRechargeableMode should return false when id not equals RAPPI_PAY and showAs equals RECHARGEABLE`() {
        val paymentMethod = createPaymentMethodV4().copy(id = "1", showAs = RappiPayMode.RECHARGEABLE)

        val isRechargeableMode = paymentMethod.isRechargeableMode()

        Assert.assertFalse(isRechargeableMode)
    }

    @Test
    fun `isRechargeableMode should return false when id not equals RAPPI_PAY and showAs equals DISCOUNT_RECHARGE`() {
        val paymentMethod = createPaymentMethodV4().copy(id = "1", showAs = RappiPayMode.DISCOUNT_RECHARGE)

        val isRechargeableMode = paymentMethod.isRechargeableMode()

        Assert.assertFalse(isRechargeableMode)
    }

    @Test
    fun `isRechargeableMode should return false when id not equals RAPPI_PAY and showAs not equals RECHARGEABLE nor DISCOUNT_RECHARGE`() {
        val paymentMethod = createPaymentMethodV4().copy(id = "1", showAs = RappiPayMode.TOP)

        val isRechargeableMode = paymentMethod.isRechargeableMode()

        Assert.assertFalse(isRechargeableMode)
    }
    //endregion

    //region getPayIcon
    @Test
    fun `getPayIcon should return icon when isRechargeableMode and rechargeMethod is null`() {
        val rechargeMethods = null
        val icon = "icon"
        val paymentMethod = createPaymentMethodV4_isRechargeableMode_true()
                .copy(icon = icon, rechargeMethod = rechargeMethods)

        val paymentIcon = paymentMethod.getPayIcon()

        Assert.assertEquals(icon, paymentIcon)
    }

    @Test
    fun `getPayIcon should return icon when isRechargeableMode and rechargeMethod icon is empty`() {
        val rechargeMethodIcon = ""
        val rechargeMethods = listOf(RechargeMethod("", rechargeMethodIcon, ""))
        val icon = "icon"
        val paymentMethod = createPaymentMethodV4_isRechargeableMode_true()
                .copy(icon = icon, rechargeMethod = rechargeMethods)

        val paymentIcon = paymentMethod.getPayIcon()

        Assert.assertEquals(icon, paymentIcon)
    }

    @Test
    fun `getPayIcon should return rechargeMethodIcon when isRechargeableMode and rechargeMethod icon is not empty`() {
        val rechargeMethodIcon = "rechargeMethodIcon"
        val rechargeMethods = listOf(RechargeMethod("", rechargeMethodIcon, ""))
        val icon = "icon"
        val paymentMethod = createPaymentMethodV4_isRechargeableMode_true()
                .copy(icon = icon, rechargeMethod = rechargeMethods)

        val paymentIcon = paymentMethod.getPayIcon()

        Assert.assertEquals(rechargeMethodIcon, paymentIcon)
    }

    @Test
    fun `getPayIcon should return first rechargeMethodIcon when isRechargeableMode and rechargeMethod icons are not empty`() {
        val firstRechargeMethodIcon = "rechargeMethodIcon"
        val secondRechargeMethodIcon = "rechargeMethodIcon2"
        val rechargeMethods = listOf(
                RechargeMethod("", firstRechargeMethodIcon, ""),
                RechargeMethod("", secondRechargeMethodIcon, "")
        )
        val icon = "icon"
        val paymentMethod = createPaymentMethodV4_isRechargeableMode_true()
                .copy(icon = icon, rechargeMethod = rechargeMethods)

        val paymentIcon = paymentMethod.getPayIcon()

        Assert.assertEquals(firstRechargeMethodIcon, paymentIcon)
    }

    @Test
    fun `getPayIcon should return icon when is not isRechargeableMode and rechargeMethod is null`() {
        val rechargeMethods = null
        val icon = "icon"
        val paymentMethod = createPaymentMethodV4_isRechargeableMode_false()
                .copy(icon = icon, rechargeMethod = rechargeMethods)

        val paymentIcon = paymentMethod.getPayIcon()

        Assert.assertEquals(icon, paymentIcon)
    }
    //endregion

    //region getDefaultInstallment
    @Test
    fun `getDefaultInstallment should return defaultInstallment when it is not null`() {
        val installment = 1
        val paymentMethod = createPaymentMethodV4().copy(defaultInstallment = installment)

        val defaultInstallment = paymentMethod.getDefaultInstallment()

        Assert.assertEquals(installment, defaultInstallment)
    }

    @Test
    fun `getDefaultInstallment should return zero when defaultInstallment is null`() {
        val installment = null
        val paymentMethod = createPaymentMethodV4().copy(defaultInstallment = installment)

        val defaultInstallment = paymentMethod.getDefaultInstallment()

        Assert.assertEquals(0, defaultInstallment)
    }
    //endregion

    //region getAvailableListInstallments
    @Test
    fun `getAvailableListInstallments should return availableInstallments when it is not null`() {
        val availableInstallments = listOf(1, 2, 3)
        val paymentMethod = createPaymentMethodV4().copy(availableInstallments = availableInstallments)

        val availableListInstallments = paymentMethod.getAvailableListInstallments()

        Assert.assertEquals(availableInstallments, availableListInstallments)
    }

    @Test
    fun `getAvailableListInstallments should return emptyList when availableInstallments is null`() {
        val availableInstallments = null
        val paymentMethod = createPaymentMethodV4().copy(availableInstallments = availableInstallments)

        val availableListInstallments = paymentMethod.getAvailableListInstallments()

        Assert.assertEquals(emptyList<Int>(), availableListInstallments)
    }
    //endregion

    //region isBillingAgreement
    @Test
    fun `isBillingAgreement should return true when metadata toBillingAgreement is true`() {
        val paymentMethod = createPaymentMethodV4_Metadata_mocked()
        whenever(metadata.toBillingAgreement()).thenReturn(true)

        val isBillingAgreement = paymentMethod.isBillingAgreement()

        Assert.assertTrue(isBillingAgreement)
    }

    @Test
    fun `isBillingAgreement should return false when metadata toBillingAgreement is false`() {
        val paymentMethod = createPaymentMethodV4_Metadata_mocked()
        whenever(metadata.toBillingAgreement()).thenReturn(false)

        val isBillingAgreement = paymentMethod.isBillingAgreement()

        Assert.assertFalse(isBillingAgreement)
    }
    //endregion

    //region getTopUpAmount
    @Test
    fun `getTopUpAmount should call metadata toTopUpAmount`() {
        val paymentMethod = createPaymentMethodV4_Metadata_mocked()

        paymentMethod.getTopUpAmount()

        verify(metadata).toTopUpAmount()
    }
    //endregion

    @Test
    fun `getPseTime should return DEFAULT_PSE_TIME when checkOutInfo is null`() {
        val paymentMethod = createPaymentMethodV4().copy(checkOutInfo = null)

        val pseTime = paymentMethod.getPseTime()

        Assert.assertEquals(DEFAULT_PSE_TIME, pseTime)
    }
    //endregion

    //region getFullName
    @Test
    fun `getFullName should call metadata toFullName`() {
        val paymentMethod = createPaymentMethodV4_Metadata_mocked()

        paymentMethod.getFullName()

        verify(metadata).toFullName()
    }
    //endregion


    private fun createPaymentMethodV4(): PaymentMethodV4 {
        return PaymentMethodV4(
                false, 0.0, null, false, "", "",
                "", "", "", "", null, null,
                Metadata(false, 0.0, "", null),
                false, "", "", null, 0, ""
        )
    }

    private fun createPaymentMethodV4_isRechargeableMode_true(): PaymentMethodV4 {
        return createPaymentMethodV4().copy(id = RAPPI_PAY, showAs = RappiPayMode.RECHARGEABLE)
    }

    private fun createPaymentMethodV4_isRechargeableMode_false(): PaymentMethodV4 {
        return createPaymentMethodV4().copy(id = "1", showAs = RappiPayMode.TOP)
    }

    private fun createPaymentMethodV4_Metadata_mocked(): PaymentMethodV4 {
        metadata = Mockito.mock(Metadata::class.java)
        return createPaymentMethodV4().copy(metadata = metadata)
    }

}