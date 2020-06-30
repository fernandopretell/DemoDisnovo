package com.limapps.base.profile

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object ProfileActionsEvents {

    private val subject = PublishSubject.create<ProfileActions>()

    fun updateMenuUserName() = subject.onNext(ProfileActions.UpdateUserName())

    fun updateMenuProfilePhoto() = subject.onNext(ProfileActions.UpdateProfilePhoto())

    fun logout() = subject.onNext(ProfileActions.Logout())

    fun getActions(): Observable<ProfileActions> = subject.hide()
}

sealed class ProfileActions {
    class UpdateUserName : ProfileActions()
    class UpdateProfilePhoto : ProfileActions()
    class Logout : ProfileActions()
}