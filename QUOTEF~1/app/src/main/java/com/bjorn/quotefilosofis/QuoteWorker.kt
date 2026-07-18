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
        val context = applicationContext
        val schools = Prefs.getActiveSchools(context)
        val pool = ALL_QUOTES.filter { it.school in schools }
        if (pool.isNotEmpty()) {
            val quote = pool.random()
            val index = ALL_QUOTES.indexOf(quote)
            Prefs.setLastQuoteIndex(context, index)
        }
        NotificationHelper.showQuote(context)
        QuoteWidgetProvider.updateAll(context)
        return Result.success()
    }

    companion object {
        private const val WORK_NAME = "quote_periodic"

        fun schedule(c: Context) {
            val minutes = Prefs.getIntervalMinutes(c).toLong().coerceAtLeast(15L)
            val request = PeriodicWorkRequestBuilder<QuoteWorker>(minutes, TimeUnit.MINUTES).build()
            WorkManager.getInstance(c).enqueueUniquePeriodicWork(
                WORK_NAME, ExistingPeriodicWorkPolicy.UPDATE, request
            )
        }
    }
}
