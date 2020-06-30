package com.limapps.base.handlers

interface LoyaltyProgramTreatmentProvider {

    fun isEnabled(): Boolean

    fun getUrl(): String
}