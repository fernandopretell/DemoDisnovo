package com.limapps.base.interfaces

import com.google.android.material.tabs.TabLayout

interface OnTabSelectedAdapterListener : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) = Unit

    override fun onTabUnselected(tab: TabLayout.Tab) = Unit

    override fun onTabReselected(tab: TabLayout.Tab) = Unit
}
