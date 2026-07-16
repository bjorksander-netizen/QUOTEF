package com.bjorn.quotefilosofis

import android.content.Context
import android.content.SharedPreferences

object Prefs {
    private const val NAME = "quote_prefs"

    const val MODE_NORMAL = "normal"
    const val MODE_LOCKSCREEN = "lockscreen"
    const val MODE_SILENT = "silent"
    const val MODE_OFF = "off"

    private fun sp(c: Context): SharedPreferences =
        c.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    fun getMode(c: Context): String = sp(c).getString("mode", MODE_LOCKSCREEN)!!
    fun setMode(c: Context, v: String) = sp(c).edit().putString("mode", v).apply()

    fun getIntervalMinutes(c: Context): Long = sp(c).getLong("interval", 180L)
    fun setIntervalMinutes(c: Context, v: Long) = sp(c).edit().putLong("interval", v).apply()

    fun getSchools(c: Context): Set<String> =
        sp(c).getStringSet("schools", Quotes.SCHOOLS.toSet())!!.toSet()
    fun setSchools(c: Context, v: Set<String>) =
        sp(c).edit().putStringSet("schools", v).apply()

    // 0 = pekat (100%), 4 = transparan penuh (0%)
    fun getWidgetAlphaLevel(c: Context): Int = sp(c).getInt("widget_alpha", 1)
    fun setWidgetAlphaLevel(c: Context, v: Int) =
        sp(c).edit().putInt("widget_alpha", v.coerceIn(0, 4)).apply()
}
