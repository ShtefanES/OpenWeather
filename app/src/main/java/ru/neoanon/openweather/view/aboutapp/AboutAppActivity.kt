package ru.neoanon.openweather.view.aboutapp

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ru.neoanon.openweather.BuildConfig
import ru.neoanon.openweather.R
import kotlinx.android.synthetic.main.activity_about_app.*

class AboutAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)

        tv_version_app_and_version_build.text =
                getString(
                    R.string.version_app_and_version_build,
                    BuildConfig.VERSION_NAME,
                    BuildConfig.VERSION_CODE.toString()
                )
        tv_source_weathers.movementMethod =
                LinkMovementMethod.getInstance()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.about_application)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}