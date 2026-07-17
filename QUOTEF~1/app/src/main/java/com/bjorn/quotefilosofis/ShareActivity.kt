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

/**
 * Activity transparan: render quote menjadi kartu gambar (gaya kartu lirik),
 * lalu buka share sheet Android agar bisa dibagikan ke media sosial mana pun.
 * Ukuran teks & tema warna mengikuti pengaturan user (basic / randomize).
 */
class ShareActivity : Activity() {

    /** Palet warna kartu share. */
    private data class Palette(
        val bg: Int, val card: Int,
        val title: Int, val sub: Int,
        val quote: Int, val brand: Int
    )

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
            val caption = "“${quote.displayText(lang)}” — ${quote.author}"
            val send = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_TEXT, caption)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            val title = if (lang == "en") "Share quote" else "Bagikan quote"
            startActivity(Intent.createChooser(send, title))
        } catch (_: Exception) {
            // gagal render/share: tutup diam-diam
        }
        finish()
    }

    /** Kartu 1080x1920 bergaya kartu lirik. */
    private fun renderCard(quote: Quote, lang: String): Bitmap {
        val w = 1080
        val h = 1920

        // ── Tema warna: basic (teal) atau acak dari daftar palet ─────────────
        val palette = if (Prefs.getShareTheme(this) == "random") PALETTES.random()
                      else PALETTES[0]

        // ── Ukuran teks quote sesuai pengaturan ──────────────────────────────
        val quoteSize = when (Prefs.getShareTextSize(this)) {
            "small" -> 50f
            "large" -> 76f
            else    -> 62f
        }

        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        canvas.drawColor(palette.bg)

        val cardMargin = 120f
        val pad = 76f
        val contentWidth = (w - 2 * cardMargin - 2 * pad).toInt()

        val authorPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = palette.title; textSize = 46f
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
        }
        val schoolPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = palette.sub; textSize = 40f
        }
        val quotePaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = palette.quote; textSize = quoteSize
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
        }
        val brandPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = palette.brand; textSize = 44f
            typeface = Typeface.create("sans-serif", Typeface.BOLD)
        }
        val dividerPaint = Paint().apply { color = 0x26FFFFFF }
        val cardPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = palette.card }

        val text = quote.displayText(lang)
        val quoteLayout = StaticLayout.Builder
            .obtain(text, 0, text.length, quotePaint, contentWidth)
            .setLineSpacing(0f, 1.45f)
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .build()

        // Tinggi kartu dihitung dari isinya
        val headerH = 46f + 58f          // baris penulis + baris aliran
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
        canvas.drawText("❝ QUOTEF", x, y, brandPaint)

        return bmp
    }

    companion object {
        const val EXTRA_QUOTE_INDEX = "quote_index"

        /** Palet[0] = tema dasar (teal). Sisanya dipakai mode randomize. */
        private val PALETTES = listOf(
            Palette(0xFF0A2530.toInt(), 0xFF10333E.toInt(), 0xFFE8F4F6.toInt(),
                    0xFF7FA6AE.toInt(), 0xFFDCEFF2.toInt(), 0xFF8FB0B8.toInt()),
            Palette(0xFF1A1030.toInt(), 0xFF261A44.toInt(), 0xFFEDE7F6.toInt(),
                    0xFFB39DDB.toInt(), 0xFFF3EEFB.toInt(), 0xFFB0A4CE.toInt()),
            Palette(0xFF0B2416.toInt(), 0xFF143423.toInt(), 0xFFE8F5E9.toInt(),
                    0xFFA5D6A7.toInt(), 0xFFF0F9F0.toInt(), 0xFF9CC4A0.toInt()),
            Palette(0xFF2A0E12.toInt(), 0xFF3D1A20.toInt(), 0xFFFFEBEE.toInt(),
                    0xFFEF9A9A.toInt(), 0xFFFFF3F4.toInt(), 0xFFD9A0A4.toInt()),
            Palette(0xFF0A1633.toInt(), 0xFF13244D.toInt(), 0xFFE3F2FD.toInt(),
                    0xFF90CAF9.toInt(), 0xFFF0F7FE.toInt(), 0xFF9BB8D8.toInt()),
            Palette(0xFF1C1A14.toInt(), 0xFF2B2718.toInt(), 0xFFFFF8E1.toInt(),
                    0xFFFFD54F.toInt(), 0xFFFFFBEC.toInt(), 0xFFD4BC77.toInt())
        )
    }
}
