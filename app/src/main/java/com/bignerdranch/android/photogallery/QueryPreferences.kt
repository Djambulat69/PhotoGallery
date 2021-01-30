package com.bignerdranch.android.photogallery

import android.app.Activity
import android.content.Context
import androidx.core.content.edit


private const val PREF_SEARCH_QUERY = "searchQuery"
private const val PREF_LAST_RESULT_ID = "lastResultId"
private const val PREF_NAME = "com.bignerdranch.android.photogallery.PREF_NAME"

object QueryPreferences {
    fun getStoredQuery(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(PREF_SEARCH_QUERY, "")!!
    }
    fun setStoredQuery(context: Context, query: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit {
            putString(PREF_SEARCH_QUERY, query)
        }
    }

    fun getLastResultId(context: Context): String {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(PREF_LAST_RESULT_ID, "")!!
    }
    fun setLastResultId(context: Context, lastResultId: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit {
            putString(PREF_LAST_RESULT_ID, lastResultId)
        }
    }

}