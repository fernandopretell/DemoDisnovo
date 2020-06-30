package com.limapps.base.persistence.db

interface SettingsConnection {
    val createQueries: List<String>

    val nameDataBase: String

    fun getUpgradeQueries(): List<String>

    val versionDataBase: Int
}