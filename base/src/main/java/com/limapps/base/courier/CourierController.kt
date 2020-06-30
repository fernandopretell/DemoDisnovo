package com.limapps.base.courier

import com.limapps.base.models.ActionPoint
import io.reactivex.Observable

interface CourierController {

    fun saveActionPoints(actionPoints: List<ActionPoint>)

    @Throws(IndexOutOfBoundsException::class)
    fun getActionPointByIndex(index: Int): ActionPoint

    fun getActionPoints(): List<ActionPoint>

    fun updateIndexActionPoints(index: Int)

    fun updateActionPoint(actionPoint: ActionPoint, index: Int)

    fun getActionPointAsync(): Observable<List<ActionPoint>>

    fun saveAddressExtraInfo(info: String, actionPointIndex: Int)

    fun saveActionPointDeclaredValue(declaredValueIndex: Int, actionPointIndex: Int)

    fun getAddressExtraInfoByIndex(index: Int) : String

    fun getActionPointDeclaredValueByIndex(index: Int) : Int

    fun deleteActionPoint(index: Int)

    fun deleteInformation()

}