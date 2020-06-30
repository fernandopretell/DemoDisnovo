package com.limapps.base

import android.content.Context
import com.limapps.base.models.UserInfoModel
import io.reactivex.Completable

interface SessionController {
    fun initFiles()
    fun registerBroadcast(context: Context)
    fun removeComponentsData(): Completable
    fun removeUserData(): Completable
    fun restartApp(context: Context)
    fun initUserDependencies(user: UserInfoModel)
}