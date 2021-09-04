package ru.neoanon.openweather.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.neoanon.openweather.view.settings.SettingsActivity
import ru.neoanon.openweather.view.settings.SettingsViewModel

@Module
interface SettingsPresentationModule {

	@Binds
	@IntoMap
	@ViewModelKey(SettingsViewModel::class)
	fun bindViewModel(viewModel: SettingsViewModel): ViewModel
}

@Module
interface SettingsModule {

	@ContributesAndroidInjector(modules = [SettingsPresentationModule::class])
	fun contributesSettingsActivity(): SettingsActivity
}