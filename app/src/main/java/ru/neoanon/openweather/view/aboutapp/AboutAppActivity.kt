package ru.neoanon.openweather.view.aboutapp

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.MenuItem
import ru.neoanon.openweather.BuildConfig
import ru.neoanon.openweather.R
import ru.neoanon.openweather.databinding.ActivityAboutAppBinding

class AboutAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityAboutAppBinding = DataBindingUtil.setContentView(this, R.layout.activity_about_app)

        binding.tvVersionAppAndVersionBuild.text =
                getString(
                    R.string.version_app_and_version_build,
                    BuildConfig.VERSION_NAME,
                    BuildConfig.VERSION_CODE.toString()
                )
        binding.tvSourceWeathers.movementMethod =
                LinkMovementMethod.getInstance()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.about_application)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}