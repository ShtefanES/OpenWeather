package ru.neoanon.openweather.view

import dagger.android.DispatchingAndroidInjector
import ru.neoanon.openweather.utils.mvvm.ViewModelFactory
import javax.inject.Inject

abstract class MvvmActivity : BaseActivity() {

	@Inject
	override lateinit var androidInjector: DispatchingAndroidInjector<Any>

	@Inject
	lateinit var viewModelFactory: ViewModelFactory
}