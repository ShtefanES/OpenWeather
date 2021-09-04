package ru.neoanon.openweather.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.neoanon.openweather.view.detailedforecast.DailyForecastViewModel
import ru.neoanon.openweather.view.detailedforecast.ForecastPagerFragment

@Module
interface DailyForecastPresentationModule {

	@Binds
	@IntoMap
	@ViewModelKey(DailyForecastViewModel::class)
	fun bindViewModel(viewModel: DailyForecastViewModel): ViewModel
}

@Module
interface DailyForecastModule {

	@ContributesAndroidInjector(modules = [DailyForecastPresentationModule::class])
	fun provideForecastPagerFragment(): ForecastPagerFragment
}