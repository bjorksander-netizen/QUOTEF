package com.bjorn.quotefilosofis

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {

    // ── UI refs ───────────────────────────────────────────────────────────────
    private lateinit var langToggle: MaterialButtonToggleGroup
    private lateinit var btnLangId: MaterialButton
    private lateinit var btnLangEn: MaterialButton

    private lateinit var titleText: TextView
    private lateinit var subtitleText: TextView

    private lateinit var labelNotif: TextView
    private lateinit var intervalSlider: Slider
    private lateinit var intervalLabel: TextView
    private lateinit var labelMode: TextView
    private lateinit var btnModeNormal: MaterialButton
    private lateinit var btnModeLockscreen: MaterialButton
    private lateinit var btnModeSilent: MaterialButton
    private lateinit var btnModeOff: MaterialButton

    private lateinit var labelWidget: TextView
    private lateinit var alphaLabel: TextView
    private lateinit var alphaSlider: Slider

    private lateinit var labelSchool: TextView
    private lateinit var cbOptimisme: CheckBox
    private lateinit var cbStoisisme: CheckBox
    private lateinit var cbNihilisme: CheckBox
    private lateinit var cbEksistensialisme: CheckBox
    private lateinit var cbAbsurdisme: CheckBox
    private lateinit var cbTimur: CheckBox

    private lateinit var labelShare: TextView
    private lateinit var labelShareSize: TextView
    private lateinit var btnSizeSmall: MaterialButton
    private lateinit var btnSizeMedium: MaterialButton
    private lateinit var btnSizeLarge: MaterialButton
    private lateinit var labelShareTheme: TextView
    private lateinit var btnThemeBasic: MaterialButton
    private lateinit var btnThemeRandom: MaterialButton

    private lateinit var btnSave: MaterialButton
    private lateinit var btnShow: MaterialButton
    private lateinit var btnShare: MaterialButton

    // ── Interval options (minutes) ────────────────────────────────────────────
    private val intervalValues = listOf(15, 30, 60, 120, 360, 720, 1440)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NotificationHelper.createChannels(this)
        requestNotificationPermission()
        bindViews()
        loadPrefs()
        setupListeners()
        applyLanguage()
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= 33 &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 100
            )
        }
    }

    private fun bindViews() {
        langToggle          = findViewById(R.id.lang_toggle)
        btnLangId           = findViewById(R.id.btn_lang_id)
        btnLangEn           = findViewById(R.id.btn_lang_en)

        titleText           = findViewById(R.id.title_text)
        subtitleText        = findViewById(R.id.subtitle_text)

        labelNotif          = findViewById(R.id.label_notif)
        intervalSlider      = findViewById(R.id.interval_slider)
        intervalLabel       = findViewById(R.id.interval_label)
        labelMode           = findViewById(R.id.label_mode)
        btnModeNormal       = findViewById(R.id.btn_mode_normal)
        btnModeLockscreen   = findViewById(R.id.btn_mode_lockscreen)
        btnModeSilent       = findViewById(R.id.btn_mode_silent)
        btnModeOff          = findViewById(R.id.btn_mode_off)

        labelWidget         = findViewById(R.id.label_widget)
        alphaLabel          = findViewById(R.id.alpha_label)
        alphaSlider         = findViewById(R.id.alpha_slider)

        labelSchool         = findViewById(R.id.label_school)
        cbOptimisme         = findViewById(R.id.cb_optimisme)
        cbStoisisme         = findViewById(R.id.cb_stoisisme)
        cbNihilisme         = findViewById(R.id.cb_nihilisme)
        cbEksistensialisme  = findViewById(R.id.cb_eksistensialisme)
        cbAbsurdisme        = findViewById(R.id.cb_absurdisme)
        cbTimur             = findViewById(R.id.cb_timur)

        labelShare          = findViewById(R.id.label_share)
        labelShareSize      = findViewById(R.id.label_share_size)
        btnSizeSmall        = findViewById(R.id.btn_size_small)
        btnSizeMedium       = findViewById(R.id.btn_size_medium)
        btnSizeLarge        = findViewById(R.id.btn_size_large)
        labelShareTheme     = findViewById(R.id.label_share_theme)
        btnThemeBasic       = findViewById(R.id.btn_theme_basic)
        btnThemeRandom      = findViewById(R.id.btn_theme_random)

        btnSave             = findViewById(R.id.btn_save)
        btnShow             = findViewById(R.id.btn_show)
        btnShare            = findViewById(R.id.btn_share)
    }

    private fun loadPrefs() {
        // Language
        val lang = Prefs.getLanguage(this)
        if (lang == "en") langToggle.check(R.id.btn_lang_en)
        else              langToggle.check(R.id.btn_lang_id)

        // Interval
        val savedMinutes = Prefs.getIntervalMinutes(this)
        val idx = intervalValues.indexOf(savedMinutes).takeIf { it >= 0 } ?: 2
        intervalSlider.value = idx.toFloat()

        // Mode
        val modeToggle = findViewById<MaterialButtonToggleGroup>(R.id.mode_toggle)
        modeToggle.check(when (Prefs.getNotifMode(this)) {
            "lockscreen" -> R.id.btn_mode_lockscreen
            "silent"     -> R.id.btn_mode_silent
            "off"        -> R.id.btn_mode_off
            else         -> R.id.btn_mode_normal
        })

        // Alpha
        alphaSlider.value = Prefs.getWidgetAlphaLevel(this).toFloat()

        // Share: ukuran teks
        val sizeToggle = findViewById<MaterialButtonToggleGroup>(R.id.share_size_toggle)
        sizeToggle.check(when (Prefs.getShareTextSize(this)) {
            "small" -> R.id.btn_size_small
            "large" -> R.id.btn_size_large
            else    -> R.id.btn_size_medium
        })

        // Share: tema warna
        val themeToggle = findViewById<MaterialButtonToggleGroup>(R.id.share_theme_toggle)
        themeToggle.check(
            if (Prefs.getShareTheme(this) == "random") R.id.btn_theme_random
            else R.id.btn_theme_basic
        )

        // Schools
        val active = Prefs.getActiveSchools(this)
        cbOptimisme.isChecked        = "Optimisme"        in active
        cbStoisisme.isChecked        = "Stoisisme"        in active
        cbNihilisme.isChecked        = "Nihilisme"        in active
        cbEksistensialisme.isChecked = "Eksistensialisme" in active
        cbAbsurdisme.isChecked       = "Absurdisme"       in active
        cbTimur.isChecked            = "Timur"            in active
    }

    private fun setupListeners() {
        langToggle.addOnButtonCheckedListener { _, _, _ -> applyLanguage() }

        intervalSlider.addOnChangeListener { _, value, _ ->
            intervalLabel.text = intervalDisplay(value.toInt(), currentLang())
        }

        alphaSlider.addOnChangeListener { _, value, _ ->
            alphaLabel.text = alphaDisplay(value.toInt(), currentLang())
        }

        btnSave.setOnClickListener { saveAndApply() }
        btnShow.setOnClickListener {
            saveAndApply()
            NotificationHelper.showQuote(this)
        }
        btnShare.setOnClickListener {
            // Bagikan quote terakhir yang tampil (atau acak jika belum ada)
            startActivity(
                android.content.Intent(this, ShareActivity::class.java)
                    .putExtra(ShareActivity.EXTRA_QUOTE_INDEX, Prefs.getLastQuoteIndex(this))
            )
        }
    }

    private fun applyLanguage() {
        val lang = currentLang()
        if (lang == "en") {
            titleText.text    = "Quote Filosofis"
            subtitleText.text = "Daily wisdom · pocket philosophy"
            labelNotif.text   = "Notifications"
            intervalLabel.text = intervalDisplay(intervalSlider.value.toInt(), lang)
            labelMode.text    = "Mode"
            btnModeNormal.text      = "Normal"
            btnModeLockscreen.text  = "Lockscreen"
            btnModeSilent.text      = "Silent"
            btnModeOff.text         = "Off"
            labelWidget.text  = "Widget"
            alphaLabel.text   = alphaDisplay(alphaSlider.value.toInt(), lang)
            labelShare.text      = "Share Card"
            labelShareSize.text  = "Text size"
            btnSizeSmall.text    = "Small"
            btnSizeMedium.text   = "Medium"
            btnSizeLarge.text    = "Large"
            labelShareTheme.text = "Color theme"
            btnThemeBasic.text   = "Basic"
            btnThemeRandom.text  = "Randomize"
            labelSchool.text  = "Philosophy Schools"
            cbOptimisme.text        = "Optimism"
            cbStoisisme.text        = "Stoicism"
            cbNihilisme.text        = "Nihilism"
            cbEksistensialisme.text = "Existentialism"
            cbAbsurdisme.text       = "Absurdism"
            cbTimur.text            = "Eastern"
            btnSave.text      = "Save settings"
            btnShow.text      = "Show quote now"
            btnShare.text     = "Share quote"
        } else {
            titleText.text    = "Quote Filosofis"
            subtitleText.text = "Kebijaksanaan harian · filsafat di saku"
            labelNotif.text   = "Notifikasi"
            intervalLabel.text = intervalDisplay(intervalSlider.value.toInt(), lang)
            labelMode.text    = "Mode"
            btnModeNormal.text      = "Normal"
            btnModeLockscreen.text  = "Lockscreen"
            btnModeSilent.text      = "Senyap"
            btnModeOff.text         = "Mati"
            labelWidget.text  = "Widget"
            alphaLabel.text   = alphaDisplay(alphaSlider.value.toInt(), lang)
            labelShare.text      = "Kartu Share"
            labelShareSize.text  = "Ukuran teks"
            btnSizeSmall.text    = "Kecil"
            btnSizeMedium.text   = "Sedang"
            btnSizeLarge.text    = "Besar"
            labelShareTheme.text = "Tema warna"
            btnThemeBasic.text   = "Dasar"
            btnThemeRandom.text  = "Acak"
            labelSchool.text  = "Aliran Filosofi"
            cbOptimisme.text        = "Optimisme"
            cbStoisisme.text        = "Stoisisme"
            cbNihilisme.text        = "Nihilisme"
            cbEksistensialisme.text = "Eksistensialisme"
            cbAbsurdisme.text       = "Absurdisme"
            cbTimur.text            = "Timur"
            btnSave.text      = "Simpan pengaturan"
            btnShow.text      = "Tampilkan quote sekarang"
            btnShare.text     = "Bagikan quote"
        }
    }

    private fun saveAndApply() {
        val lang = currentLang()
        Prefs.setLanguage(this, lang)

        val idx = intervalSlider.value.toInt().coerceIn(0, intervalValues.size - 1)
        Prefs.setIntervalMinutes(this, intervalValues[idx])

        val modeToggle = findViewById<MaterialButtonToggleGroup>(R.id.mode_toggle)
        val mode = when (modeToggle.checkedButtonId) {
            R.id.btn_mode_lockscreen -> "lockscreen"
            R.id.btn_mode_silent     -> "silent"
            R.id.btn_mode_off        -> "off"
            else                     -> "normal"
        }
        Prefs.setNotifMode(this, mode)

        Prefs.setWidgetAlphaLevel(this, alphaSlider.value.toInt())

        val sizeToggle = findViewById<MaterialButtonToggleGroup>(R.id.share_size_toggle)
        Prefs.setShareTextSize(this, when (sizeToggle.checkedButtonId) {
            R.id.btn_size_small -> "small"
            R.id.btn_size_large -> "large"
            else                -> "medium"
        })

        val themeToggle = findViewById<MaterialButtonToggleGroup>(R.id.share_theme_toggle)
        Prefs.setShareTheme(this,
            if (themeToggle.checkedButtonId == R.id.btn_theme_random) "random" else "basic"
        )

        val schools = mutableSetOf<String>()
        if (cbOptimisme.isChecked)        schools += "Optimisme"
        if (cbStoisisme.isChecked)        schools += "Stoisisme"
        if (cbNihilisme.isChecked)        schools += "Nihilisme"
        if (cbEksistensialisme.isChecked) schools += "Eksistensialisme"
        if (cbAbsurdisme.isChecked)       schools += "Absurdisme"
        if (cbTimur.isChecked)            schools += "Timur"
        Prefs.setActiveSchools(this, schools)

        // Reschedule & update widget
        QuoteWorker.schedule(this)
        QuoteWidgetProvider.updateAll(this)
    }

    private fun currentLang() =
        if (langToggle.checkedButtonId == R.id.btn_lang_en) "en" else "id"

    private fun intervalDisplay(idx: Int, lang: String): String {
        val mins = intervalValues.getOrElse(idx) { 60 }
        val label = when {
            mins < 60   -> if (lang == "en") "$mins min"      else "$mins menit"
            mins < 1440 -> if (lang == "en") "${mins/60} hr"  else "${mins/60} jam"
            else        -> if (lang == "en") "Once a day"     else "Sekali sehari"
        }
        return if (lang == "en") "Notify every: $label" else "Notifikasi setiap: $label"
    }

    private fun alphaDisplay(level: Int, lang: String): String {
        val pct = listOf("100%", "75%", "50%", "25%", "0%")[level.coerceIn(0, 4)]
        return if (lang == "en") "Background opacity: $pct"
        else                     "Transparansi latar: $pct"
    }
}
