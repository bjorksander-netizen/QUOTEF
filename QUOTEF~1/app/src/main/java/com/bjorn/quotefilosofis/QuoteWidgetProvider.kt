package com.bjorn.quotefilosofis

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class QuoteWidgetProvider : AppWidgetProvider() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == ACTION_NEW_QUOTE) {
            val schools = Prefs.getActiveSchools(context)
            val pool = ALL_QUOTES.filter { it.school in schools }
            if (pool.isNotEmpty()) {
                val quote = pool.random()
                Prefs.setLastQuoteIndex(context, ALL_QUOTES.indexOf(quote))
            }
            updateAll(context)
            return
        }
        super.onReceive(context, intent)
    }

    companion object {
        const val ACTION_NEW_QUOTE = "com.bjorn.quotefilosofis.ACTION_NEW_QUOTE"

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
                ComponentName(context, QuoteWidgetProvider::class.java)
            )
            for (id in ids) updateWidget(context, manager, id)
        }

        fun updateWidget(context: Context, manager: AppWidgetManager, widgetId: Int) {
            val lang    = Prefs.getLanguage(context)
            val schools = Prefs.getActiveSchools(context)
            val pool    = ALL_QUOTES.filter { it.school in schools }

            val lastIdx = Prefs.getLastQuoteIndex(context)
            val quote = if (lastIdx >= 0) {
                ALL_QUOTES.getOrNull(lastIdx)?.takeIf { it.school in schools }
            } else {
                val r = pool.randomOrNull()
                if (r != null) Prefs.setLastQuoteIndex(context, ALL_QUOTES.indexOf(r))
                r
            }

            val views = RemoteViews(context.packageName, R.layout.widget_quote)

            if (quote != null) {
                val index  = ALL_QUOTES.indexOf(quote)

                val text   = quote.displayText(lang)
                val school = schoolLabel(quote.school, lang)
                views.setTextViewText(R.id.widget_text, "\u201c$text\u201d")
                views.setTextViewText(R.id.widget_author, "\u2014 ${quote.author} \u00b7 $school  \u21ea")

                // Ketuk baris penulis = bagikan quote yang SAMA dengan yang tampil
                val shareIntent = Intent(context, ShareActivity::class.java)
                    .putExtra(ShareActivity.EXTRA_QUOTE_INDEX, index)
                val sharePi = PendingIntent.getActivity(
                    context, 100000 + widgetId, shareIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                views.setOnClickPendingIntent(R.id.widget_author, sharePi)
            } else {
                views.setTextViewText(R.id.widget_text, if (lang == "en") "No school selected." else "Tidak ada aliran yang dipilih.")
                views.setTextViewText(R.id.widget_author, "")
            }

            val level = Prefs.getWidgetAlphaLevel(context).coerceIn(0, 4)
            views.setInt(R.id.widget_root, "setBackgroundResource", BACKGROUNDS[level])

            // Ketuk area quote = quote baru
            val newQuoteIntent = Intent(context, QuoteWidgetProvider::class.java).apply {
                action = ACTION_NEW_QUOTE
            }
            val pending = PendingIntent.getBroadcast(
                context, widgetId, newQuoteIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_text, pending)
            views.setOnClickPendingIntent(R.id.widget_root, pending)

            manager.updateAppWidget(widgetId, views)
        }
    }

    override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) {
        for (id in ids) updateWidget(context, manager, id)
    }
}
