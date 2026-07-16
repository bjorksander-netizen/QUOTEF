package com.bjorn.quotefilosofis

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationHelper {

    private const val CH_NORMAL     = "ch_normal"
    private const val CH_LOCKSCREEN = "ch_lockscreen"
    private const val CH_SILENT     = "ch_silent"

    fun createChannels(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        nm.createNotificationChannel(
            NotificationChannel(CH_NORMAL, "Normal", NotificationManager.IMPORTANCE_DEFAULT)
        )
        nm.createNotificationChannel(
            NotificationChannel(CH_LOCKSCREEN, "Lockscreen", NotificationManager.IMPORTANCE_HIGH).apply {
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            }
        )
        nm.createNotificationChannel(
            NotificationChannel(CH_SILENT, "Silent", NotificationManager.IMPORTANCE_LOW).apply {
                setSound(null, null)
                enableVibration(false)
            }
        )
    }

    fun showQuote(context: Context) {
        val mode = Prefs.getNotifMode(context)
        if (mode == "off") return

        val lang    = Prefs.getLanguage(context)
        val schools = Prefs.getActiveSchools(context)
        val pool    = ALL_QUOTES.filter { it.school in schools }
        if (pool.isEmpty()) return

        val quote = pool.random()
        val text  = quote.displayText(lang)
        val school = schoolLabel(quote.school, lang)

        val (channelId, visibility) = when (mode) {
            "lockscreen" -> CH_LOCKSCREEN to NotificationCompat.VISIBILITY_PUBLIC
            "silent"     -> CH_SILENT     to NotificationCompat.VISIBILITY_SECRET
            else         -> CH_NORMAL     to NotificationCompat.VISIBILITY_PRIVATE
        }

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notif = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_menu_compass)
            .setContentTitle("${quote.author} · $school")
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setVisibility(visibility)
            .setColor(0xFF4DB6AC.toInt())
            .setAutoCancel(true)
            .build()

        nm.notify(1, notif)
    }
}
