package com.bignerdranch.android.photogallery

import android.app.Activity
import android.content.Context
import androidx.core.content.edit


private const val PREF_SEARCH_QUERY = "searchQuery"

object QueryPreferences {
    fun getStoredQuery(activity: Activity): String {
        val prefs = activity.getPreferences(Context.MODE_PRIVATE)
        return prefs.getString(PREF_SEARCH_QUERY, "")!!
    }
    fun setStoredQuery(activity: Activity, query: String) {
        activity.getPreferences(Context.MODE_PRIVATE)
                .edit {
                    putString(PREF_SEARCH_QUERY, query)
                }
    }
}