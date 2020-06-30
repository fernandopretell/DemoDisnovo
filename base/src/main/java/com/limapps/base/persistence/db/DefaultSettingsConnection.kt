package com.limapps.base.persistence.db

import android.content.Context
import com.limapps.base.R

class DefaultSettingsConnection(var context: Context) : SettingsConnection {

    private val createList: MutableList<String> = ArrayList()

    companion object {

        private const val NAME_DATABASE = "base.db"
        private const val NUM_VERSION = 14
    }

    init {
        createList.add(context.getString(R.string.create_basket))
        createList.add(context.getString(R.string.create_address))
        createList.add(context.getString(R.string.create_coupons))
        createList.add(context.getString(R.string.create_offers))
        createList.add(context.getString(R.string.create_offer_product))
        createList.add(context.getString(R.string.create_contacts_pay_searches))
    }

    override val createQueries: List<String> get() = createList

    override val nameDataBase: String get() = NAME_DATABASE

    override fun getUpgradeQueries(): List<String> {
        val updateQueries = ArrayList<String>()

        updateQueries.add(context.getString(R.string.delete_basket))
        updateQueries.add(context.getString(R.string.create_basket))

        return updateQueries
    }

    override val versionDataBase: Int get() = NUM_VERSION
}
