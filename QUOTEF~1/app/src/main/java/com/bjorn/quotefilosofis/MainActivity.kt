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

    private lateinit var previewCardColor: View
    private lateinit var hexCardColor: EditText
    private lateinit var btnPickCardColor: MaterialButton

    private lateinit var previewTextColor: View
    private lateinit var hexTextColor: EditText
    private lateinit var btnPickTextColor: MaterialButton

    private lateinit var previewAuthorColor: View
    private lateinit var hexAuthorColor: EditText
    private lateinit var btnPickAuthorColor: MaterialButton

    private lateinit var previewSchoolColor: View
    private lateinit var hexSchoolColor: EditText
    private lateinit var btnPickSchoolColor: MaterialButton

    private lateinit var previewBrandColor: View
    private lateinit var hexBrandColor: EditText
    private lateinit var btnPickBrandColor: MaterialButton

    private lateinit var previewDividerColor: View
    private lateinit var hexDividerColor: EditText
    private lateinit var btnPickDividerColor: MaterialButton

    private lateinit var btnSave: MaterialButton
    private lateinit var btnShow: MaterialButton
    private lateinit var btnShare: MaterialButton

    private val intervalValues = listOf(15, 30, 60, 120, 360, 720, 1440)

    private var selectedBgColor = 0xFF0A2530.toInt()
    private var selectedCardColor = 0xFF10333E.toInt()
    private var selectedTextColor = 0xFFDCEFF2.toInt()
    private var selectedAuthorColor = 0xFFE8F4F6.toInt()
    private var selectedSchoolColor = 0xFF7FA6AE.toInt()
    private var selectedBrandColor = 0xFF8FB0B8.toInt()
    private var selectedDividerColor = 0x26FFFFFF.toInt()

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

        previewCardColor = findViewById(R.id.preview_card_color)
        hexCardColor = findViewById(R.id.hex_card_color)
        btnPickCardColor = findViewById(R.id.btn_pick_card_color)

        previewTextColor = findViewById(R.id.preview_text_color)
        hexTextColor = findViewById(R.id.hex_text_color)
        btnPickTextColor = findViewById(R.id.btn_pick_text_color)

        previewAuthorColor = findViewById(R.id.preview_author_color)
        hexAuthorColor = findViewById(R.id.hex_author_color)
        btnPickAuthorColor = findViewById(R.id.btn_pick_author_color)

        previewSchoolColor = findViewById(R.id.preview_school_color)
        hexSchoolColor = findViewById(R.id.hex_school_color)
        btnPickSchoolColor = findViewById(R.id.btn_pick_school_color)

        previewBrandColor = findViewById(R.id.preview_brand_color)
        hexBrandColor = findViewById(R.id.hex_brand_color)
        btnPickBrandColor = findViewById(R.id.btn_pick_brand_color)

        previewDividerColor = findViewById(R.id.preview_divider_color)
        hexDividerColor = findViewById(R.id.hex_divider_color)
        btnPickDividerColor = findViewById(R.id.btn_pick_divider_color)

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
        selectedCardColor = Prefs.getShareCardColor(this)
        selectedTextColor = Prefs.getShareTextColor(this)
        selectedAuthorColor = Prefs.getShareAuthorColor(this)
        selectedSchoolColor = Prefs.getShareSchoolColor(this)
        selectedBrandColor = Prefs.getShareBrandColor(this)
        selectedDividerColor = Prefs.getShareDividerColor(this)
    }

    private fun setupColorPickers() {
        setupColorButton(btnPickBgColor) { color ->
            selectedBgColor = color
            refreshColorPreviews()
        }
        setupColorButton(btnPickCardColor) { color ->
            selectedCardColor = color
            refreshColorPreviews()
        }
        setupColorButton(btnPickTextColor) { color ->
            selectedTextColor = color
            refreshColorPreviews()
        }
        setupColorButton(btnPickAuthorColor) { color ->
            selectedAuthorColor = color
            refreshColorPreviews()
        }
        setupColorButton(btnPickSchoolColor) { color ->
            selectedSchoolColor = color
            refreshColorPreviews()
        }
        setupColorButton(btnPickBrandColor) { color ->
            selectedBrandColor = color
            refreshColorPreviews()
        }
        setupColorButton(btnPickDividerColor) { color ->
            selectedDividerColor = color
            refreshColorPreviews()
        }
    }

    private fun setupColorButton(button: MaterialButton, onColorSelected: (Int) -> Unit) {
        button.setOnClickListener {
            val currentColor = when (button.id) {
                R.id.btn_pick_bg_color -> selectedBgColor
                R.id.btn_pick_card_color -> selectedCardColor
                R.id.btn_pick_text_color -> selectedTextColor
                R.id.btn_pick_author_color -> selectedAuthorColor
                R.id.btn_pick_school_color -> selectedSchoolColor
                R.id.btn_pick_brand_color -> selectedBrandColor
                R.id.btn_pick_divider_color -> selectedDividerColor
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

        val seekR = createSeekBar("R", Color.red(currentColor)) { progress, _ ->
            updateColorFromSeekBars(hexInput, colorView, seekR, seekG, seekB, seekA)
        }
        val seekG = createSeekBar("G", Color.green(currentColor)) { progress, _ ->
            updateColorFromSeekBars(hexInput, colorView, seekR, seekG, seekB, seekA)
        }
        val seekB = createSeekBar("B", Color.blue(currentColor)) { progress, _ ->
            updateColorFromSeekBars(hexInput, colorView, seekR, seekG, seekB, seekA)
        }
        val seekA = createSeekBar("A", Color.alpha(currentColor)) { progress, _ ->
            updateColorFromSeekBars(hexInput, colorView, seekR, seekG, seekB, seekA)
        }

        layout.addView(colorView)
        layout.addView(hexInput)
        layout.addView(seekR)
        layout.addView(seekG)
        layout.addView(seekB)
        layout.addView(seekA)

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

    private fun createSeekBar(
        label: String,
        initial: Int,
        onChanged: (Int, android.widget.SeekBar?) -> Unit
    ): android.widget.LinearLayout {
        val container = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.HORIZONTAL
            gravity = android.view.Gravity.CENTER_VERTICAL
            setPadding(0, 8, 0, 8)
        }

        val labelText = android.widget.TextView(this).apply {
            text = label
            setTextColor(Color.BLACK)
            textSize = 14f
            layoutParams = android.widget.LinearLayout.LayoutParams(60, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT)
        }

        val seekBar = android.widget.SeekBar(this).apply {
            max = 255
            progress = initial
            layoutParams = android.widget.LinearLayout.LayoutParams(0, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val valueText = android.widget.TextView(this).apply {
            text = String.format("%02X", initial)
            setTextColor(Color.BLACK)
            textSize = 14f
            layoutParams = android.widget.LinearLayout.LayoutParams(80, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT)
        }

        seekBar.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                valueText.text = String.format("%02X", progress)
                onChanged(progress, seekBar)
            }
            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
        })

        container.addView(labelText)
        container.addView(seekBar)
        container.addView(valueText)
        return container
    }

    private fun updateColorFromSeekBars(
        hexInput: EditText,
        colorView: View,
        seekR: android.widget.LinearLayout,
        seekG: android.widget.LinearLayout,
        seekB: android.widget.LinearLayout,
        seekA: android.widget.LinearLayout
    ) {
        val r = getSeekBarProgress(seekR)
        val g = getSeekBarProgress(seekG)
        val b = getSeekBarProgress(seekB)
        val a = getSeekBarProgress(seekA)
        val color = Color.argb(a, r, g, b)
        colorView.setBackgroundColor(color)
        hexInput.setText(String.format("%08X", color))
    }

    private fun getSeekBarProgress(container: android.widget.LinearLayout): Int {
        for (i in 0 until container.childCount) {
            val child = container.getChildAt(i)
            if (child is android.widget.SeekBar) return child.progress
        }
        return 0
    }

    private fun setupHexInputs() {
        setupHexInput(hexBgColor) { color -> selectedBgColor = color; refreshColorPreviews() }
        setupHexInput(hexCardColor) { color -> selectedCardColor = color; refreshColorPreviews() }
        setupHexInput(hexTextColor) { color -> selectedTextColor = color; refreshColorPreviews() }
        setupHexInput(hexAuthorColor) { color -> selectedAuthorColor = color; refreshColorPreviews() }
        setupHexInput(hexSchoolColor) { color -> selectedSchoolColor = color; refreshColorPreviews() }
        setupHexInput(hexBrandColor) { color -> selectedBrandColor = color; refreshColorPreviews() }
        setupHexInput(hexDividerColor) { color -> selectedDividerColor = color; refreshColorPreviews() }
    }

    private fun setupHexInput(editText: EditText, onColorChanged: (Int) -> Unit) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
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
        previewCardColor.setBackgroundColor(selectedCardColor)
        previewTextColor.setBackgroundColor(selectedTextColor)
        previewAuthorColor.setBackgroundColor(selectedAuthorColor)
        previewSchoolColor.setBackgroundColor(selectedSchoolColor)
        previewBrandColor.setBackgroundColor(selectedBrandColor)
        previewDividerColor.setBackgroundColor(selectedDividerColor)

        if (hexBgColor.text.toString() != String.format("%08X", selectedBgColor)) {
            hexBgColor.setText(String.format("%08X", selectedBgColor))
        }
        if (hexCardColor.text.toString() != String.format("%08X", selectedCardColor)) {
            hexCardColor.setText(String.format("%08X", selectedCardColor))
        }
        if (hexTextColor.text.toString() != String.format("%08X", selectedTextColor)) {
            hexTextColor.setText(String.format("%08X", selectedTextColor))
        }
        if (hexAuthorColor.text.toString() != String.format("%08X", selectedAuthorColor)) {
            hexAuthorColor.setText(String.format("%08X", selectedAuthorColor))
        }
        if (hexSchoolColor.text.toString() != String.format("%08X", selectedSchoolColor)) {
            hexSchoolColor.setText(String.format("%08X", selectedSchoolColor))
        }
        if (hexBrandColor.text.toString() != String.format("%08X", selectedBrandColor)) {
            hexBrandColor.setText(String.format("%08X", selectedBrandColor))
        }
        if (hexDividerColor.text.toString() != String.format("%08X", selectedDividerColor)) {
            hexDividerColor.setText(String.format("%08X", selectedDividerColor))
        }
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
            NotificationHelper.showQuote(this)
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
        Prefs.setShareCardColor(this, selectedCardColor)
        Prefs.setShareTextColor(this, selectedTextColor)
        Prefs.setShareAuthorColor(this, selectedAuthorColor)
        Prefs.setShareSchoolColor(this, selectedSchoolColor)
        Prefs.setShareBrandColor(this, selectedBrandColor)
        Prefs.setShareDividerColor(this, selectedDividerColor)

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

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()
}
