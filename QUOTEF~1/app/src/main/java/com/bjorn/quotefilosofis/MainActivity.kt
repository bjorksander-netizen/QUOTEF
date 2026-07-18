package com.bjorn.quotefilosofis

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.card.MaterialCardView
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {

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

    private lateinit var previewCard: MaterialCardView
    private lateinit var previewQuoteText: TextView
    private lateinit var previewAuthor: TextView
    private lateinit var previewLabel: TextView

    private lateinit var labelShareCustom: TextView

    private lateinit var previewBgColor: View
    private lateinit var hexBgColor: EditText
    private lateinit var btnPickBgColor: MaterialButton

    private lateinit var previewAccentColor: View
    private lateinit var hexAccentColor: EditText
    private lateinit var btnPickAccentColor: MaterialButton

    private lateinit var btnSave: MaterialButton
    private lateinit var btnShow: MaterialButton
    private lateinit var btnShare: MaterialButton

    private val intervalValues = listOf(15, 30, 60, 120, 360, 720, 1440)

    private var selectedBgColor = 0xFF0A2530.toInt()
    private var selectedAccentColor = 0xFF7FA6AE.toInt()

    private var isRefreshingHex = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NotificationHelper.createChannels(this)
        requestNotificationPermission()
        bindViews()
        loadPrefs()
        setupColorPickers()
        setupHexInputs()
        setupListeners()
        applyLanguage()
        refreshPreview()
        refreshColorPreviews()
    }

    override fun onResume() {
        super.onResume()
        refreshPreview()
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
        langToggle = findViewById(R.id.lang_toggle)
        btnLangId = findViewById(R.id.btn_lang_id)
        btnLangEn = findViewById(R.id.btn_lang_en)

        titleText = findViewById(R.id.title_text)
        subtitleText = findViewById(R.id.subtitle_text)

        labelNotif = findViewById(R.id.label_notif)
        intervalSlider = findViewById(R.id.interval_slider)
        intervalLabel = findViewById(R.id.interval_label)
        labelMode = findViewById(R.id.label_mode)
        btnModeNormal = findViewById(R.id.btn_mode_normal)
        btnModeLockscreen = findViewById(R.id.btn_mode_lockscreen)
        btnModeSilent = findViewById(R.id.btn_mode_silent)
        btnModeOff = findViewById(R.id.btn_mode_off)

        labelWidget = findViewById(R.id.label_widget)
        alphaLabel = findViewById(R.id.alpha_label)
        alphaSlider = findViewById(R.id.alpha_slider)

        labelSchool = findViewById(R.id.label_school)
        cbOptimisme = findViewById(R.id.cb_optimisme)
        cbStoisisme = findViewById(R.id.cb_stoisisme)
        cbNihilisme = findViewById(R.id.cb_nihilisme)
        cbEksistensialisme = findViewById(R.id.cb_eksistensialisme)
        cbAbsurdisme = findViewById(R.id.cb_absurdisme)
        cbTimur = findViewById(R.id.cb_timur)

        previewCard = findViewById(R.id.preview_card)
        previewQuoteText = findViewById(R.id.preview_quote_text)
        previewAuthor = findViewById(R.id.preview_author)
        previewLabel = findViewById(R.id.preview_label)

        labelShareCustom = findViewById(R.id.label_share_custom)

        previewBgColor = findViewById(R.id.preview_bg_color)
        hexBgColor = findViewById(R.id.hex_bg_color)
        btnPickBgColor = findViewById(R.id.btn_pick_bg_color)

        previewAccentColor = findViewById(R.id.preview_accent_color)
        hexAccentColor = findViewById(R.id.hex_accent_color)
        btnPickAccentColor = findViewById(R.id.btn_pick_accent_color)

        btnSave = findViewById(R.id.btn_save)
        btnShow = findViewById(R.id.btn_show)
        btnShare = findViewById(R.id.btn_share)
    }

    private fun loadPrefs() {
        val lang = Prefs.getLanguage(this)
        if (lang == "en") langToggle.check(R.id.btn_lang_en)
        else langToggle.check(R.id.btn_lang_id)

        val savedMinutes = Prefs.getIntervalMinutes(this)
        val idx = intervalValues.indexOf(savedMinutes).takeIf { it >= 0 } ?: 2
        intervalSlider.value = idx.toFloat()

        val modeToggle = findViewById<MaterialButtonToggleGroup>(R.id.mode_toggle)
        modeToggle.check(
            when (Prefs.getNotifMode(this)) {
                "lockscreen" -> R.id.btn_mode_lockscreen
                "silent" -> R.id.btn_mode_silent
                "off" -> R.id.btn_mode_off
                else -> R.id.btn_mode_normal
            }
        )

        alphaSlider.value = Prefs.getWidgetAlphaLevel(this).toFloat()

        val active = Prefs.getActiveSchools(this)
        cbOptimisme.isChecked = "Optimisme" in active
        cbStoisisme.isChecked = "Stoisisme" in active
        cbNihilisme.isChecked = "Nihilisme" in active
        cbEksistensialisme.isChecked = "Eksistensialisme" in active
        cbAbsurdisme.isChecked = "Absurdisme" in active
        cbTimur.isChecked = "Timur" in active

        selectedBgColor = Prefs.getShareBgColor(this)
        selectedAccentColor = Prefs.getShareAccentColor(this)
    }

    private fun setupColorPickers() {
        setupColorButton(btnPickBgColor) { color ->
            selectedBgColor = color
            refreshColorPreviews()
        }
        setupColorButton(btnPickAccentColor) { color ->
            selectedAccentColor = color
            refreshColorPreviews()
        }
    }

    private fun setupColorButton(button: MaterialButton, onColorSelected: (Int) -> Unit) {
        button.setOnClickListener {
            val currentColor = when (button.id) {
                R.id.btn_pick_bg_color -> selectedBgColor
                R.id.btn_pick_accent_color -> selectedAccentColor
                else -> Color.WHITE
            }
            showColorPickerDialog(currentColor, onColorSelected)
        }
    }

    private fun showColorPickerDialog(currentColor: Int, onColorSelected: (Int) -> Unit) {
        val dialog = android.app.Dialog(this)
        dialog.setTitle("Pilih Warna")

        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(48, 32, 48, 32)
        }

        val colorView = View(this).apply {
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 120
            ).apply { bottomMargin = 24 }
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(currentColor)
                cornerRadius = 16f
            }
        }

        val hexInput = EditText(this).apply {
            hint = "#RRGGBB atau #AARRGGBB"
            setText(String.format("%08X", currentColor))
            setTextColor(Color.BLACK)
            setTextSize(16f)
            setPadding(24, 24, 24, 24)
            background = GradientDrawable().apply {
                setColor(Color.WHITE)
                cornerRadius = 12f
                setStroke(2, Color.GRAY)
            }
        }

        hexInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val color = parseHexColor(s?.toString() ?: "")
                if (color != null) {
                    colorView.setBackgroundColor(color)
                }
            }
        })

        layout.addView(colorView)
        layout.addView(hexInput)

        val okButton = android.widget.Button(this).apply {
            text = "OK"
            setOnClickListener {
                val color = parseHexColor(hexInput.text.toString())
                if (color != null) {
                    onColorSelected(color)
                }
                dialog.dismiss()
            }
        }

        val cancelButton = android.widget.Button(this).apply {
            text = "Batal"
            setOnClickListener { dialog.dismiss() }
        }

        val buttonLayout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.HORIZONTAL
            gravity = android.view.Gravity.END
            setPadding(0, 24, 0, 0)
        }
        buttonLayout.addView(cancelButton)
        buttonLayout.addView(okButton)

        layout.addView(buttonLayout)
        dialog.setContentView(layout)
        dialog.show()
    }

    private fun setupHexInputs() {
        setupHexInput(hexBgColor) { color -> selectedBgColor = color; refreshColorPreviews() }
        setupHexInput(hexAccentColor) { color -> selectedAccentColor = color; refreshColorPreviews() }
    }

    private fun setupHexInput(editText: EditText, onColorChanged: (Int) -> Unit) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (isRefreshingHex) return
                val color = parseHexColor(s?.toString() ?: "")
                if (color != null) {
                    onColorChanged(color)
                }
            }
        })

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val color = parseHexColor(editText.text.toString())
                if (color != null) {
                    onColorChanged(color)
                }
            }
        }
    }

    private fun parseHexColor(hex: String): Int? {
        val cleaned = hex.trim().removePrefix("#")
        return try {
            when (cleaned.length) {
                6 -> Color.parseColor("#FF$cleaned")
                8 -> Color.parseColor("#$cleaned")
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun refreshColorPreviews() {
        previewBgColor.setBackgroundColor(selectedBgColor)
        previewAccentColor.setBackgroundColor(selectedAccentColor)

        isRefreshingHex = true
        if (hexBgColor.text.toString() != String.format("%08X", selectedBgColor)) {
            hexBgColor.setText(String.format("%08X", selectedBgColor))
        }
        if (hexAccentColor.text.toString() != String.format("%08X", selectedAccentColor)) {
            hexAccentColor.setText(String.format("%08X", selectedAccentColor))
        }
        isRefreshingHex = false
    }

    private fun refreshPreview() {
        val lang = currentLang()
        val schools = getSelectedSchools()
        val pool = ALL_QUOTES.filter { it.school in schools }
        if (pool.isEmpty()) {
            val msg = if (lang == "en") "No school selected." else "Tidak ada aliran yang dipilih."
            previewQuoteText.text = msg
            previewAuthor.text = ""
            return
        }
        val lastIdx = Prefs.getLastQuoteIndex(this)
        val quote = if (lastIdx >= 0) {
            ALL_QUOTES.getOrNull(lastIdx)?.takeIf { it.school in schools } ?: pool.random()
        } else {
            pool.random()
        }
        previewQuoteText.text = "\u201c${quote.displayText(lang)}\u201d"
        previewAuthor.text = "\u2014 ${quote.author} \u00b7 ${schoolLabel(quote.school, lang)}"
    }

    private fun getSelectedSchools(): Set<String> {
        val schools = mutableSetOf<String>()
        if (cbOptimisme.isChecked) schools += "Optimisme"
        if (cbStoisisme.isChecked) schools += "Stoisisme"
        if (cbNihilisme.isChecked) schools += "Nihilisme"
        if (cbEksistensialisme.isChecked) schools += "Eksistensialisme"
        if (cbAbsurdisme.isChecked) schools += "Absurdisme"
        if (cbTimur.isChecked) schools += "Timur"
        return schools
    }

    private fun setupListeners() {
        langToggle.addOnButtonCheckedListener { _, _, _ ->
            applyLanguage()
            refreshPreview()
        }

        intervalSlider.addOnChangeListener { _, value, _ ->
            intervalLabel.text = intervalDisplay(value.toInt(), currentLang())
        }

        alphaSlider.addOnChangeListener { _, value, _ ->
            alphaLabel.text = alphaDisplay(value.toInt(), currentLang())
        }

        val schoolChangedListener = { _: View, _: Boolean -> refreshPreview() }
        cbOptimisme.setOnCheckedChangeListener(schoolChangedListener)
        cbStoisisme.setOnCheckedChangeListener(schoolChangedListener)
        cbNihilisme.setOnCheckedChangeListener(schoolChangedListener)
        cbEksistensialisme.setOnCheckedChangeListener(schoolChangedListener)
        cbAbsurdisme.setOnCheckedChangeListener(schoolChangedListener)
        cbTimur.setOnCheckedChangeListener(schoolChangedListener)

        btnSave.setOnClickListener { saveAndApply() }
        btnShow.setOnClickListener {
            saveAndApply()
            NotificationHelper.showQuote(this, pickNew = true)
            refreshPreview()
        }
        btnShare.setOnClickListener {
            saveAndApply()
            startActivity(Intent(this, PreviewShareActivity::class.java))
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
            R.id.btn_mode_silent -> "silent"
            R.id.btn_mode_off -> "off"
            else -> "normal"
        }
        Prefs.setNotifMode(this, mode)

        Prefs.setWidgetAlphaLevel(this, alphaSlider.value.toInt())

        Prefs.setShareBgColor(this, selectedBgColor)
        Prefs.setShareAccentColor(this, selectedAccentColor)

        val schools = getSelectedSchools()
        Prefs.setActiveSchools(this, schools)

        QuoteWorker.schedule(this)
        QuoteWidgetProvider.updateAll(this)
    }

    private fun applyLanguage() {
        val lang = currentLang()
        if (lang == "en") {
            titleText.text = "Quote Filosofis"
            subtitleText.text = "Daily wisdom \u00b7 pocket philosophy"
            labelNotif.text = "NOTIFICATIONS"
            intervalLabel.text = intervalDisplay(intervalSlider.value.toInt(), lang)
            labelMode.text = "MODE"
            btnModeNormal.text = "Normal"
            btnModeLockscreen.text = "Lockscreen"
            btnModeSilent.text = "Silent"
            btnModeOff.text = "Off"
            labelWidget.text = "WIDGET"
            alphaLabel.text = alphaDisplay(alphaSlider.value.toInt(), lang)
            labelSchool.text = "PHILOSOPHY SCHOOLS"
            cbOptimisme.text = "Optimism"
            cbStoisisme.text = "Stoicism"
            cbNihilisme.text = "Nihilism"
            cbEksistensialisme.text = "Existentialism"
            cbAbsurdisme.text = "Absurdism"
            cbTimur.text = "Eastern"
            previewLabel.text = "CURRENT QUOTE"
            labelShareCustom.text = "SHARE CUSTOMIZATION"
            btnSave.text = "Save settings"
            btnShow.text = "Show quote now"
            btnShare.text = "Share quote"
        } else {
            titleText.text = "Quote Filosofis"
            subtitleText.text = "Kebijaksanaan harian \u00b7 filsafat di saku"
            labelNotif.text = "NOTIFIKASI"
            intervalLabel.text = intervalDisplay(intervalSlider.value.toInt(), lang)
            labelMode.text = "MODE"
            btnModeNormal.text = "Normal"
            btnModeLockscreen.text = "Lockscreen"
            btnModeSilent.text = "Senyap"
            btnModeOff.text = "Mati"
            labelWidget.text = "WIDGET"
            alphaLabel.text = alphaDisplay(alphaSlider.value.toInt(), lang)
            labelSchool.text = "ALIRAN FILOSOFI"
            cbOptimisme.text = "Optimisme"
            cbStoisisme.text = "Stoisisme"
            cbNihilisme.text = "Nihilisme"
            cbEksistensialisme.text = "Eksistensialisme"
            cbAbsurdisme.text = "Absurdisme"
            cbTimur.text = "Timur"
            previewLabel.text = "QUOTE SAAT INI"
            labelShareCustom.text = "KUSTOMISASI SHARE"
            btnSave.text = "Simpan pengaturan"
            btnShow.text = "Tampilkan quote sekarang"
            btnShare.text = "Bagikan quote"
        }
    }

    private fun currentLang() =
        if (langToggle.checkedButtonId == R.id.btn_lang_en) "en" else "id"

    private fun intervalDisplay(idx: Int, lang: String): String {
        val mins = intervalValues.getOrElse(idx) { 60 }
        val label = when {
            mins < 60 -> if (lang == "en") "$mins min" else "$mins menit"
            mins < 1440 -> if (lang == "en") "${mins / 60} hr" else "${mins / 60} jam"
            else -> if (lang == "en") "Once a day" else "Sekali sehari"
        }
        return if (lang == "en") "Notify every: $label" else "Notifikasi setiap: $label"
    }

    private fun alphaDisplay(level: Int, lang: String): String {
        val pct = listOf("100%", "75%", "50%", "25%", "0%")[level.coerceIn(0, 4)]
        return if (lang == "en") "Background opacity: $pct"
        else "Transparansi latar: $pct"
    }
}
