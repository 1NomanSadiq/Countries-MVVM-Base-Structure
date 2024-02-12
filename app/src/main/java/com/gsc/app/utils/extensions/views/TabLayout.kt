package com.gsc.app.utils.extensions.views

import com.google.android.material.tabs.TabLayout
import com.gsc.app.R

fun TabLayout.onTabSelected(listener: (TabLayout.Tab?) -> Unit) {
    val listeners = getTag(R.string.tabListeners) as? MutableMap<String, (TabLayout.Tab?) -> Unit> ?: mutableMapOf()
    listeners["onTabSelected"] = listener
    setTag(R.string.tabListeners, listeners)
    refreshListeners()
}

fun TabLayout.onTabUnSelected(listener: (TabLayout.Tab?) -> Unit) {
    val listeners = getTag(R.string.tabListeners) as? MutableMap<String, (TabLayout.Tab?) -> Unit> ?: mutableMapOf()
    listeners["onTabUnselected"] = listener
    setTag(R.string.tabListeners, listeners)
    refreshListeners()
}

fun TabLayout.onTabReselected(listener: (TabLayout.Tab?) -> Unit) {
    val listeners = getTag(R.string.tabListeners) as? MutableMap<String, (TabLayout.Tab?) -> Unit> ?: mutableMapOf()
    listeners["onTabReselected"] = listener
    setTag(R.string.tabListeners, listeners)
    refreshListeners()
}

private fun TabLayout.refreshListeners() {
    clearOnTabSelectedListeners()
    val listeners = getTag(R.string.tabListeners) as? Map<String, (TabLayout.Tab?) -> Unit>
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            listeners?.get("onTabSelected")?.invoke(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            listeners?.get("onTabUnselected")?.invoke(tab)
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
            listeners?.get("onTabReselected")?.invoke(tab)
        }
    })
}
