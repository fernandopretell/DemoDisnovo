package com.limapps.base.dynamicrate.v1

class DynamicRates(private val dynamicRates: MutableList<DynamicRate> = mutableListOf()) {

    fun getDynamicRates() = dynamicRates

    fun replaceWithNewData(data: List<DynamicRate>) {
        dynamicRates.clear()
        dynamicRates.addAll(data)
    }

    private fun isDynamicRatesEmpty(): Boolean{
        return dynamicRates.isEmpty()
    }

    fun getDynamicRateRequest(): ArrayList<DynamicRateRequest>? {
        var dynamicRatesRequest: ArrayList<DynamicRateRequest>? = null
        if (isDynamicRatesEmpty().not()) {
            dynamicRatesRequest = ArrayList(dynamicRates.size)
            dynamicRates.forEach { dynamicRate ->
                val dynamicRateRequest = DynamicRateRequest(dynamicRate.storeId, dynamicRate.getChargesShippingId())
                dynamicRatesRequest.add(dynamicRateRequest)
            }
        }
        return dynamicRatesRequest
    }
}
