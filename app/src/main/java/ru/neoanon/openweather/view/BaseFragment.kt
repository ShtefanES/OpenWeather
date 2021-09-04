package ru.neoanon.openweather.view

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment(), HasAndroidInjector {

	abstract var androidInjector: DispatchingAndroidInjector<Any>

	override fun onAttach(context: Context) {
		AndroidSupportInjection.inject(this)
		super.onAttach(context)
	}

	override fun androidInjector(): AndroidInjector<Any> =
		androidInjector
}