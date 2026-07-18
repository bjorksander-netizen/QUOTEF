package com.bjorn.quotefilosofis

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class PreviewShareActivity : AppCompatActivity() {

    private lateinit var previewTitle: TextView
    private lateinit var previewSubtitle: TextView
    private lateinit var previewCardContainer: android.widget.LinearLayout
    private lateinit var previewCardInner: android.widget.LinearLayout
    private lateinit var previewAuthor: TextView
    private lateinit var previewSchool: TextView
    private lateinit var previewDivider: android.view.View
    private lateinit var previewQuoteText: TextView
    private lateinit var previewBrand: TextView
    private lateinit var btnShareNow: MaterialButton
    private lateinit var btnBack: MaterialButton

    private var quoteIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_share)

        quoteIndex = intent.getIntExtra(EXTRA_QUOTE_INDEX, -1)

        bindViews()
        loadPreview()
        setupListeners()
        applyLanguage()
    }

    private fun bindViews() {
        previewTitle = findViewById(R.id.preview_title)
        previewSubtitle = findViewById(R.id.preview_subtitle)
        previewCardContainer = findViewById(R.id.preview_card_container)
        previewCardInner = findViewById(R.id.preview_card_inner)
        previewAuthor = findViewById(R.id.preview_author)
        previewSchool = findViewById(R.id.preview_school)
        previewDivider = findViewById(R.id.preview_divider)
        previewQuoteText = findViewById(R.id.preview_quote_text)
        previewBrand = findViewById(R.id.preview_brand)
        btnShareNow = findViewById(R.id.btn_share_now)
        btnBack = findViewById(R.id.btn_back)
    }

    private fun loadPreview() {
        val lang = Prefs.getLanguage(this)
        val schools = Prefs.getActiveSchools(this)
        val pool = ALL_QUOTES.filter { it.school in schools }

        val quote = if (quoteIndex >= 0) {
            ALL_QUOTES.getOrNull(quoteIndex)
        } else {
            val lastIdx = Prefs.getLastQuoteIndex(this)
            ALL_QUOTES.getOrNull(lastIdx)?.takeIf { it.school in schools }
                ?: pool.randomOrNull()
        }

        if (quote == null) {
            val msg = if (lang == "en") "No quote available." else "Tidak ada quote tersedia."
            previewQuoteText.text = msg
            previewAuthor.text = ""
            previewSchool.text = ""
            previewBrand.text = ""
            return
        }

        previewQuoteText.text = "\u201c${quote.displayText(lang)}\u201d"
        previewAuthor.text = "\u2014 ${quote.author}"
        previewSchool.text = schoolLabel(quote.school, lang)

        val bgColor = Prefs.getShareBgColor(this)
        val cardColor = Prefs.getShareCardColor(this)
        val textColor = Prefs.getShareTextColor(this)
        val authorColor = Prefs.getShareAuthorColor(this)
        val schoolColor = Prefs.getShareSchoolColor(this)
        val brandColor = Prefs.getShareBrandColor(this)
        val dividerColor = Prefs.getShareDividerColor(this)

        previewCardContainer.setBackgroundColor(bgColor)
        previewCardInner.background = GradientDrawable().apply {
            cornerRadius = 44f
            setColor(cardColor)
        }
        previewQuoteText.setTextColor(textColor)
        previewAuthor.setTextColor(authorColor)
        previewSchool.setTextColor(schoolColor)
        previewBrand.setTextColor(brandColor)
        previewDivider.setBackgroundColor(dividerColor)
    }

    private fun setupListeners() {
        btnShareNow.setOnClickListener {
            val intent = Intent(this, ShareActivity::class.java).apply {
                putExtra(ShareActivity.EXTRA_QUOTE_INDEX, quoteIndex)
            }
            startActivity(intent)
            finish()
        }
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun applyLanguage() {
        val lang = Prefs.getLanguage(this)
        if (lang == "en") {
            previewTitle.text = "Share Preview"
            previewSubtitle.text = "Quote appearance before sharing"
            btnShareNow.text = "Share Now"
            btnBack.text = "Back"
        } else {
            previewTitle.text = "Pratinjau Share"
            previewSubtitle.text = "Tampilan quote sebelum dibagikan"
            btnShareNow.text = "Bagikan Sekarang"
            btnBack.text = "Kembali"
        }
    }

    companion object {
        const val EXTRA_QUOTE_INDEX = "quote_index"
    }
}
