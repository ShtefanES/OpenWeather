package ru.neoanon.openweather.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.neoanon.openweather.view.mainscreen.MainFragment
import ru.neoanon.openweather.view.mainscreen.WeatherViewModel

@Module
interface WeatherPresentationModule {

	@Binds
	@IntoMap
	@ViewModelKey(WeatherViewModel::class)
	fun bindViewModel(viewModel: WeatherViewModel): ViewModel
}

@Module
interface MainWeatherModule {

	@ContributesAndroidInjector(modules = [WeatherPresentationModule::class])
	fun provideMainFragment(): MainFragment
}