package com.bjorn.quotefilosofis

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationHelper {
    private const val CH_NORMAL = "quotes_normal"
    private const val CH_LOCK = "quotes_lockscreen"
    private const val CH_SILENT = "quotes_silent"
    private const val NOTIF_ID = 1001

    fun createChannels(c: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val nm = c.getSystemService(NotificationManager::class.java)
        nm.createNotificationChannel(
            NotificationChannel(CH_NORMAL, "Quote (biasa)", NotificationManager.IMPORTANCE_DEFAULT).apply {
                lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            })
        nm.createNotificationChannel(
            NotificationChannel(CH_LOCK, "Quote (lockscreen)", NotificationManager.IMPORTANCE_HIGH).apply {
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            })
        nm.createNotificationChannel(
            NotificationChannel(CH_SILENT, "Quote (senyap)", NotificationManager.IMPORTANCE_LOW).apply {
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                setSound(null, null)
                enableVibration(false)
            })
    }

    fun show(c: Context, quote: Quote) {
        val mode = Prefs.getMode(c)
        if (mode == Prefs.MODE_OFF) return

        val channel = when (mode) {
            Prefs.MODE_LOCKSCREEN -> CH_LOCK
            Prefs.MODE_SILENT -> CH_SILENT
            else -> CH_NORMAL
        }

        val pi = PendingIntent.getActivity(
            c, 0, Intent(c, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(c, channel)
            .setSmallIcon(android.R.drawable.ic_menu_edit)
            .setContentTitle("${quote.author} • ${quote.school}")
            .setContentText(quote.text)
            .setStyle(NotificationCompat.BigTextStyle().bigText("“${quote.text}”\n— ${quote.author}"))
            .setContentIntent(pi)
            .setAutoCancel(true)
            .setColor(0xFF4DB6AC.toInt())
            .setVisibility(
                if (mode == Prefs.MODE_NORMAL) NotificationCompat.VISIBILITY_PRIVATE
                else NotificationCompat.VISIBILITY_PUBLIC
            )
        if (mode == Prefs.MODE_SILENT) builder.setSilent(true)

        try {
            androidx.core.app.NotificationManagerCompat.from(c).notify(NOTIF_ID, builder.build())
        } catch (_: SecurityException) {
            // Izin notifikasi belum diberikan — abaikan dengan aman.
        }
    }
}
