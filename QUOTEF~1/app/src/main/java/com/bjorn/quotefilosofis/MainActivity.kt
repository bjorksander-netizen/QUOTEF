package com.bjorn.quotefilosofis

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (!granted) {
                Toast.makeText(
                    this,
                    "Tanpa izin notifikasi, hanya widget yang aktif.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    private val alphaLabels =
        listOf("100% (pekat)", "75%", "50%", "25%", "0% (transparan)")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NotificationHelper.createChannels(this)
        askNotificationPermission()

        val modeGroup = findViewById<RadioGroup>(R.id.mode_group)
        val intervalSpinner = findViewById<Spinner>(R.id.interval_spinner)
        val schoolsContainer = findViewById<LinearLayout>(R.id.schools_container)
        val preview = findViewById<TextView>(R.id.preview)
        val alphaSlider = findViewById<Slider>(R.id.alpha_slider)
        val alphaLabel = findViewById<TextView>(R.id.alpha_label)

        // --- Mode notifikasi ---
        when (Prefs.getMode(this)) {
            Prefs.MODE_NORMAL -> findViewById<RadioButton>(R.id.mode_normal).isChecked = true
            Prefs.MODE_LOCKSCREEN -> findViewById<RadioButton>(R.id.mode_lockscreen).isChecked = true
            Prefs.MODE_SILENT -> findViewById<RadioButton>(R.id.mode_silent).isChecked = true
            else -> findViewById<RadioButton>(R.id.mode_off).isChecked = true
        }

        // --- Interval ---
        val intervals = listOf(15L, 30L, 60L, 180L, 360L, 720L, 1440L)
        val labels = listOf(
            "Setiap 15 menit", "Setiap 30 menit", "Setiap 1 jam",
            "Setiap 3 jam", "Setiap 6 jam", "Setiap 12 jam", "Sekali sehari"
        )
        intervalSpinner.adapter = android.widget.ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, labels
        )
        intervalSpinner.setSelection(
            intervals.indexOf(Prefs.getIntervalMinutes(this)).coerceAtLeast(0)
        )

        // --- Transparansi widget ---
        val level = Prefs.getWidgetAlphaLevel(this)
        alphaSlider.value = level.toFloat()
        alphaLabel.text = "Transparansi latar: ${alphaLabels[level]}"
        alphaSlider.addOnChangeListener { _, value, _ ->
            alphaLabel.text = "Transparansi latar: ${alphaLabels[value.toInt().coerceIn(0, 4)]}"
        }

        // --- Aliran filosofi ---
        val saved = Prefs.getSchools(this)
        val checkboxes = Quotes.SCHOOLS.map { school ->
            CheckBox(this).apply {
                text = school
                minHeight = (44 * resources.displayMetrics.density).toInt()
                isChecked = school in saved
                schoolsContainer.addView(this)
            }
        }

        // --- Simpan ---
        findViewById<Button>(R.id.btn_save).setOnClickListener {
            val mode = when (modeGroup.checkedRadioButtonId) {
                R.id.mode_normal -> Prefs.MODE_NORMAL
                R.id.mode_lockscreen -> Prefs.MODE_LOCKSCREEN
                R.id.mode_silent -> Prefs.MODE_SILENT
                else -> Prefs.MODE_OFF
            }
            Prefs.setMode(this, mode)
            Prefs.setIntervalMinutes(this, intervals[intervalSpinner.selectedItemPosition])
            Prefs.setWidgetAlphaLevel(this, alphaSlider.value.toInt())

            val selected = checkboxes.filter { it.isChecked }.map { it.text.toString() }.toSet()
            Prefs.setSchools(this, if (selected.isEmpty()) Quotes.SCHOOLS.toSet() else selected)

            QuoteWorker.schedule(this)
            QuoteWidgetProvider.updateAll(this, Quotes.random(Prefs.getSchools(this)))
            Toast.makeText(this, "Tersimpan.", Toast.LENGTH_SHORT).show()
        }

        // --- Quote sekarang ---
        findViewById<Button>(R.id.btn_now).setOnClickListener {
            val q = Quotes.random(Prefs.getSchools(this))
            preview.text = "“${q.text}”\n— ${q.author} (${q.school})"
            NotificationHelper.show(this, q)
            QuoteWidgetProvider.updateAll(this, q)
        }

        QuoteWorker.schedule(this)
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
