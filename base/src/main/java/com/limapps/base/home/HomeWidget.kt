package com.limapps.base.home

const val NO_ID = "NO_ID"

open class HomeWidget(
        val id: String = NO_ID,
        val index: Int = 0,
        val title: String = "",
        val type: HomeViewTypes = HomeViewTypes.NONE,
        val category: String = "",
        var verticalGroup: String? = null,
        var verticalSubGroup: String? = null,
        val style: String = "",
        val servicePaginatedType: String = ""
)