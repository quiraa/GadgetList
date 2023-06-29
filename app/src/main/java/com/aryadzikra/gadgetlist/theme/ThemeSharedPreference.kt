package com.aryadzikra.gadgetlist.theme

import android.content.Context
import android.preference.PreferenceManager

@Suppress("DEPRECATION")
class ThemeSharedPreference(context : Context) {
     companion object {
         private const val THEME_KEY = "theme_key"
     }

    private val currentTheme = PreferenceManager.getDefaultSharedPreferences(context)
    var theme = currentTheme.getInt(THEME_KEY, 0)
        set(value) = currentTheme.edit().putInt(THEME_KEY, value).apply()
}