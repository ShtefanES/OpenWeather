package ru.neoanon.openweather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector

abstract class BaseActivity : AppCompatActivity(), HasAndroidInjector {

	abstract var androidInjector: DispatchingAndroidInjector<Any>

	override fun onCreate(savedInstanceState: Bundle?) {
		AndroidInjection.inject(this)
		super.onCreate(savedInstanceState)
	}

	override fun androidInjector(): AndroidInjector<Any> =
		androidInjector
}