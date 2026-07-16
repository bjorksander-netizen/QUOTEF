package com.bjorn.quotefilosofis

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews

class QuoteWidgetProvider : AppWidgetProvider() {

    companion object {
        private val BACKGROUNDS = intArrayOf(
            R.drawable.widget_bg_0,
            R.drawable.widget_bg_1,
            R.drawable.widget_bg_2,
            R.drawable.widget_bg_3,
            R.drawable.widget_bg_4
        )

        fun updateAll(context: Context) {
            val manager = AppWidgetManager.getInstance(context)
            val ids = manager.getAppWidgetIds(
                android.content.ComponentName(context, QuoteWidgetProvider::class.java)
            )
            for (id in ids) updateWidget(context, manager, id)
        }

        fun updateWidget(context: Context, manager: AppWidgetManager, widgetId: Int) {
            val lang    = Prefs.getLanguage(context)
            val schools = Prefs.getActiveSchools(context)
            val pool    = ALL_QUOTES.filter { it.school in schools }
            val quote   = if (pool.isEmpty()) null else pool.random()

            val views = RemoteViews(context.packageName, R.layout.widget_quote)

            if (quote != null) {
                val text   = quote.displayText(lang)
                val school = schoolLabel(quote.school, lang)
                views.setTextViewText(R.id.widget_text, "“$text”")
                views.setTextViewText(R.id.widget_author, "— ${quote.author} · $school")
            } else {
                views.setTextViewText(R.id.widget_text, if (lang == "en") "No school selected." else "Tidak ada aliran yang dipilih.")
                views.setTextViewText(R.id.widget_author, "")
            }

            val level = Prefs.getWidgetAlphaLevel(context).coerceIn(0, 4)
            views.setInt(R.id.widget_root, "setBackgroundResource", BACKGROUNDS[level])

            manager.updateAppWidget(widgetId, views)
        }
    }

    override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) {
        for (id in ids) updateWidget(context, manager, id)
    }
}
