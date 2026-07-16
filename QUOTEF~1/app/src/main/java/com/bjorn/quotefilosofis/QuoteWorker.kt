package com.bjorn.quotefilosofis

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class QuoteWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        val quote = Quotes.random(Prefs.getSchools(applicationContext))
        NotificationHelper.show(applicationContext, quote)
        QuoteWidgetProvider.updateAll(applicationContext, quote)
        return Result.success()
    }

    companion object {
        private const val WORK_NAME = "quote_periodic"

        fun schedule(c: Context) {
            val minutes = Prefs.getIntervalMinutes(c).coerceAtLeast(15L)
            val request = PeriodicWorkRequestBuilder<QuoteWorker>(minutes, TimeUnit.MINUTES).build()
            WorkManager.getInstance(c).enqueueUniquePeriodicWork(
                WORK_NAME, ExistingPeriodicWorkPolicy.UPDATE, request
            )
        }
    }
}
