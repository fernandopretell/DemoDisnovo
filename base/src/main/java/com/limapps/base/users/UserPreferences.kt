package com.limapps.base.users

interface UserPreferences {

    fun isNewUser() : Boolean

    fun setNewUser(active: Boolean)
}