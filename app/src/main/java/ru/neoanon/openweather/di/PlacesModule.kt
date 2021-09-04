package ru.neoanon.openweather.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.neoanon.openweather.view.places.LocationViewModel
import ru.neoanon.openweather.view.places.PlacesFragment

@Module
interface PlacesPresentationModule {

	@Binds
	@IntoMap
	@ViewModelKey(LocationViewModel::class)
	fun bindViewModel(viewModel: LocationViewModel): ViewModel
}

@Module
interface PlacesModule {

	@ContributesAndroidInjector(modules = [
		WeatherPresentationModule::class,
		PlacesPresentationModule::class,
	])
	fun providePlacesFragment(): PlacesFragment
}