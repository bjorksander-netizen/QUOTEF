package com.bjorn.quotefilosofis

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.os.Bundle
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class ShareActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lang = Prefs.getLanguage(this)
        val idx = intent.getIntExtra(EXTRA_QUOTE_INDEX, -1)
        val quote = ALL_QUOTES.getOrNull(idx) ?: run {
            val schools = Prefs.getActiveSchools(this)
            val pool = ALL_QUOTES.filter { it.school in schools }
            (pool.ifEmpty { ALL_QUOTES }).random()
        }

        try {
            val bmp = renderCard(quote, lang)
            val dir = File(cacheDir, "shared").apply { mkdirs() }
            val file = File(dir, "quote.png")
            FileOutputStream(file).use { bmp.compress(Bitmap.CompressFormat.PNG, 100, it) }
            bmp.recycle()

            val uri = FileProvider.getUriForFile(this, "$packageName.fileprovider", file)
            val caption = "\u201c${quote.displayText(lang)}\u201d \u2014 ${quote.author}"
            val send = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_TEXT, caption)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            val title = if (lang == "en") "Share quote" else "Bagikan quote"
            startActivity(Intent.createChooser(send, title))
        } catch (_: Exception) {
        }
        finish()
    }

    private fun renderCard(quote: Quote, lang: String): Bitmap {
        val w = 1080
        val h = 1920
        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)

        val bgColor = Prefs.getShareBgColor(this)
        val cardColor = Prefs.getShareCardColor(this)
        val textColor = Prefs.getShareTextColor(this)
        val authorColor = Prefs.getShareAuthorColor(this)
        val schoolColor = Prefs.getShareSchoolColor(this)
        val brandColor = Prefs.getShareBrandColor(this)
        val dividerColor = Prefs.getShareDividerColor(this)

        canvas.drawColor(bgColor)

        val cardMargin = 120f
        val pad = 76f
        val contentWidth = (w - 2 * cardMargin - 2 * pad).toInt()

        val authorPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = authorColor
            textSize = 46f
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
        }
        val schoolPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = schoolColor
            textSize = 40f
        }
        val quotePaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = textColor
            textSize = 62f
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
        }
        val brandPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = brandColor
            textSize = 44f
            typeface = Typeface.create("sans-serif", Typeface.BOLD)
        }
        val dividerPaint = Paint().apply { color = dividerColor }
        val cardPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = cardColor }

        val text = quote.displayText(lang)
        val quoteLayout = StaticLayout.Builder
            .obtain(text, 0, text.length, quotePaint, contentWidth)
            .setLineSpacing(0f, 1.45f)
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .build()

        val headerH = 46f + 58f
        val cardH = pad + headerH + 44f + 3f + 60f +
                quoteLayout.height + 68f + 44f + pad
        val cardTop = ((h - cardH) / 2f).coerceAtLeast(80f)
        val rect = RectF(cardMargin, cardTop, w - cardMargin, cardTop + cardH)
        canvas.drawRoundRect(rect, 44f, 44f, cardPaint)

        val x = cardMargin + pad
        var y = cardTop + pad + 46f
        canvas.drawText(quote.author, x, y, authorPaint)
        y += 58f
        canvas.drawText(schoolLabel(quote.school, lang), x, y, schoolPaint)
        y += 44f
        canvas.drawRect(x, y, w - cardMargin - pad, y + 3f, dividerPaint)
        y += 60f
        canvas.save()
        canvas.translate(x, y)
        quoteLayout.draw(canvas)
        canvas.restore()
        y += quoteLayout.height + 68f + 32f
        canvas.drawText("\u275d Quote Filosofis", x, y, brandPaint)

        return bmp
    }

    companion object {
        const val EXTRA_QUOTE_INDEX = "quote_index"
    }
}
