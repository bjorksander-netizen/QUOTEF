package com.bjorn.quotefilosofis

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class QuoteWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) {
        val quote = Quotes.random(Prefs.getSchools(context))
        ids.forEach { id -> update(context, manager, id, quote) }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == ACTION_REFRESH) {
            updateAll(context, Quotes.random(Prefs.getSchools(context)))
        }
    }

    companion object {
        const val ACTION_REFRESH = "com.bjorn.quotefilosofis.REFRESH_WIDGET"

        private val BACKGROUNDS = intArrayOf(
            R.drawable.widget_bg_0, R.drawable.widget_bg_1, R.drawable.widget_bg_2,
            R.drawable.widget_bg_3, R.drawable.widget_bg_4
        )

        fun updateAll(context: Context, quote: Quote) {
            val manager = AppWidgetManager.getInstance(context)
            val ids = manager.getAppWidgetIds(
                ComponentName(context, QuoteWidgetProvider::class.java)
            )
            ids.forEach { id -> update(context, manager, id, quote) }
        }

        private fun update(context: Context, manager: AppWidgetManager, id: Int, quote: Quote) {
            val views = RemoteViews(context.packageName, R.layout.widget_quote)
            views.setTextViewText(R.id.widget_quote, "“${quote.text}”")
            views.setTextViewText(R.id.widget_author, "— ${quote.author} · ${quote.school}")

            val level = Prefs.getWidgetAlphaLevel(context).coerceIn(0, 4)
            views.setInt(R.id.widget_root, "setBackgroundResource", BACKGROUNDS[level])

            val refresh = Intent(context, QuoteWidgetProvider::class.java).setAction(ACTION_REFRESH)
            val pi = PendingIntent.getBroadcast(
                context, 0, refresh,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_root, pi)
            manager.updateAppWidget(id, views)
        }
    }
}
