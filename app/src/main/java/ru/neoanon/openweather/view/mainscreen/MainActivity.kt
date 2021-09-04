package ru.neoanon.openweather.view.mainscreen

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import ru.neoanon.openweather.R
import ru.neoanon.openweather.app.App
import ru.neoanon.openweather.utils.mvvm.viewModelDelegate
import ru.neoanon.openweather.view.MvvmActivity
import ru.neoanon.openweather.view.places.PlacesFragment
import ru.neoanon.openweather.view.settings.SettingsActivity

class MainActivity : MvvmActivity() {
    private val disposable = CompositeDisposable()

    private lateinit var drawerLayout: DrawerLayout

    private val weatherViewModel: WeatherViewModel by viewModelDelegate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = drawer_layout

        commitFragment(R.id.main_content_frame, MainFragment())
        commitFragment(R.id.navig_content_frame, PlacesFragment())
        val toolbar = toolbar_container.toolbar
        setSupportActionBar(toolbar)
        initNavigationDrawer(drawerLayout, toolbar)
    }

    override fun onStart() {
        super.onStart()
        disposable.add(weatherViewModel.subscribeSelectedRegionName()
            .subscribe { regionName -> supportActionBar?.title = regionName })

        disposable.add(weatherViewModel.closeDrawerSub
            .subscribe { drawerLayout.closeDrawer(GravityCompat.START) })
    }


    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            weatherViewModel.closeDrawer()
        } else {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.enter, R.anim.exit)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    private fun initNavigationDrawer(drawerLayout: DrawerLayout, toolbar: Toolbar) {
        val actionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ) {}
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

    private fun commitFragment(containerViewId: Int, fragment: Fragment) {
        val fTrans = supportFragmentManager.beginTransaction()
        fTrans.replace(containerViewId, fragment)
        fTrans.commit()
    }
}
