package com.limapps.base.checkout.models

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.limapps.base.creditcards.ModelCreditCard
import org.junit.Test
import java.lang.reflect.Type
import kotlin.test.assertNotNull

class ModelCreditCardTest {

    private val gson = Gson()

    private val monolithResponse = "[{\"application_user_id\":11613824,\"bin\":\"123456\",\"name\":\"FULL NAME\",\"card_reference\":1,\"expiry_year\":2021,\"termination\":\"4321\",\"expiry_month\":4,\"transaction_reference\":null,\"type\":\"visa\",\"default\":false,\"deleted_at\":null,\"payment_gateway_type\":null,\"country_code\":null,\"bank_name\":null,\"status\":\"valid\",\"payment_source_id\":null,\"restriction_message\":null,\"sanitized_type\":\"visa\",\"available\":true,\"has_document\":false,\"payment_origin\":\"Spreedly\"}]"
    private val microserviceResponse = "[{\"verified\":false,\"id\":1,\"user_id\":null,\"need_verification\":false,\"month\":4,\"year\":2021,\"card_type\":\"visa\",\"last_four_digits\":\"4321\",\"first_six_digits\":\"123456\",\"full_name\":\"FULL NAME\",\"default_cc\":false,\"restriction_message\":null,\"available\":true,\"has_document\":false,\"payment_origin\":\"Spreedly\",\"fingerprint\":\"65fe04931334988cc017edc5786a8ddd7a9c\"}]"

    private val creditCard: ModelCreditCard by lazy {
        ModelCreditCard("FULL NAME", "1", "123456", "4321", "visa", null, false, true, false)
    }

    private val typeToken: Type by lazy {
        object : TypeToken<List<ModelCreditCard>>() {}.type
    }

    @Test
    fun `Map credit cards correctly from monolith`() {
        val mappedCreditCards = gson.fromJson<List<ModelCreditCard>>(monolithResponse, typeToken)

        val cardUnderTest = mappedCreditCards.firstOrNull { it.cardReference == creditCard.cardReference }

        assertBasicCCInformation(cardUnderTest)
    }

    @Test
    fun `Map credit cards correctly from microservice`() {
        val mappedCreditCards = gson.fromJson<List<ModelCreditCard>>(microserviceResponse, typeToken)

        val cardUnderTest = mappedCreditCards.firstOrNull { it.cardReference == creditCard.cardReference }

        assertBasicCCInformation(cardUnderTest)
    }

    private fun assertBasicCCInformation(cardUnderTest: ModelCreditCard?) {
        assertNotNull(cardUnderTest)
        assert(cardUnderTest!!.name == creditCard.name)
        assert(cardUnderTest.cardReference == creditCard.cardReference)
        assert(cardUnderTest.bin == creditCard.bin)
        assert(cardUnderTest.termination == creditCard.termination)
        assert(cardUnderTest.type == creditCard.type)
    }
}