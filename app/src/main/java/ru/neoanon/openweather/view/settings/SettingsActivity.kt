package ru.neoanon.openweather.view.settings

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.RadioButton
import io.reactivex.disposables.CompositeDisposable
import ru.neoanon.openweather.R
import ru.neoanon.openweather.app.App
import ru.neoanon.openweather.databinding.ActivitySettingsBinding
import ru.neoanon.openweather.model.UnitsOfWeather
import ru.neoanon.openweather.model.enumerations.PressureType
import ru.neoanon.openweather.model.enumerations.TempType
import ru.neoanon.openweather.view.aboutapp.AboutAppActivity
import javax.inject.Inject

/**
 *Created by eshtefan on  14.11.2018.
 */

class SettingsActivity : AppCompatActivity() {
    @Inject
    lateinit var settingsViewModelFactory: SettingsViewModelFactory

    private lateinit var settingsViewModel: SettingsViewModel
    private val disposable = CompositeDisposable()
    private val changedSettings: UnitsOfWeather = UnitsOfWeather("", "")
    private lateinit var rbCelsius: RadioButton
    private lateinit var rbKelvin: RadioButton
    private lateinit var rbPressureMmHg: RadioButton
    private lateinit var rbPressureHectopascal: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        rbCelsius = binding.rbCelsius
        rbKelvin = binding.rbKelvin
        rbPressureMmHg = binding.rbPressureMmHg
        rbPressureHectopascal = binding.rbPressureHectopascal

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(getString(R.string.setting_menu_item))

        binding.rgTemperatureType.setOnCheckedChangeListener { _, i ->
            when (i) {
                rbCelsius.id -> {
                    rbCelsius.setTextColor(ContextCompat.getColor(this, R.color.colorCheckedText))
                    rbKelvin.setTextColor(ContextCompat.getColor(this, R.color.colorUncheckedText))
                    changedSettings.tempType = TempType.CELSIUS.id
                }
                else -> {
                    rbCelsius.setTextColor(ContextCompat.getColor(this, R.color.colorUncheckedText))
                    rbKelvin.setTextColor(ContextCompat.getColor(this, R.color.colorCheckedText))
                    changedSettings.tempType = TempType.KELVIN.id
                }
            }
        }

        binding.rgPressureType.setOnCheckedChangeListener { _, i ->
            when (i) {
                rbPressureMmHg.id -> {
                    rbPressureMmHg.setTextColor(ContextCompat.getColor(this, R.color.colorCheckedText))
                    rbPressureHectopascal.setTextColor(ContextCompat.getColor(this, R.color.colorUncheckedText))
                    changedSettings.pressureType = PressureType.TORR.id
                }
                else -> {
                    rbPressureMmHg.setTextColor(ContextCompat.getColor(this, R.color.colorUncheckedText))
                    rbPressureHectopascal.setTextColor(ContextCompat.getColor(this, R.color.colorCheckedText))
                    changedSettings.pressureType = PressureType.HECTOPASCAL.id
                }
            }
        }


        binding.tvAboutApp.setOnClickListener {
            startActivity(
                Intent(this, AboutAppActivity::class.java)
            )
            overridePendingTransition(R.anim.enter, R.anim.exit)
        }

        (application as App).getAppComponent().inject(this)

        settingsViewModel = ViewModelProviders.of(this, settingsViewModelFactory).get(SettingsViewModel::class.java)

        disposable.add(
            settingsViewModel.subscribeToUnits()
                .subscribe({ unit -> initToggles(unit) }, {
                })
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun onBackPressed() {
        disposable.add(
            settingsViewModel.saveSettings(changedSettings)
                .subscribe({
                    finish()
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
                }, {
                    finish()
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
                })
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    private fun initToggles(unit: UnitsOfWeather) {
        val temp = unit.tempType
        val pressure = unit.pressureType
        when (temp) {
            TempType.CELSIUS.id -> rbCelsius.isChecked = true
            else -> rbKelvin.isChecked = true
        }
        when (pressure) {
            PressureType.TORR.id -> rbPressureMmHg.isChecked = true
            else -> rbPressureHectopascal.isChecked = true
        }
    }
}