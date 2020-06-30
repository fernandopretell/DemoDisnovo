package com.limapps.base.controllers

import io.reactivex.Single

interface DebtController {
    fun checkIfUserHasDebts(userId: String): Single<Boolean>
}