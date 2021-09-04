package ru.neoanon.openweather.utils.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import ru.neoanon.openweather.view.MvvmActivity
import ru.neoanon.openweather.view.MvvmFragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewModelDelegate<VM : ViewModel>(
	private val vmClass: Class<VM>,
	private val vmStoreOwner: () -> ViewModelStoreOwner,
	private val vmFactory: () -> ViewModelProvider.Factory,
) : ReadOnlyProperty<ViewModelStoreOwner, VM> {

	private var viewModel: VM? = null

	override fun getValue(thisRef: ViewModelStoreOwner, property: KProperty<*>): VM =
		viewModel ?: ViewModelProvider(vmStoreOwner(), vmFactory())
			.get(vmClass)
			.also { viewModel = it }
}

inline fun <reified VM : ViewModel> MvvmFragment.viewModelDelegate(
	noinline ownerProducer: () -> ViewModelStoreOwner = { this },
	noinline factoryProducer: () -> ViewModelProvider.Factory = { viewModelFactory }
): ViewModelDelegate<VM> =
	ViewModelDelegate(VM::class.java, ownerProducer, factoryProducer)

inline fun <reified VM : ViewModel> MvvmActivity.viewModelDelegate(
	noinline ownerProducer: () -> ViewModelStoreOwner = { this },
	noinline factoryProducer: () -> ViewModelProvider.Factory = { viewModelFactory }
): ViewModelDelegate<VM> =
	ViewModelDelegate(VM::class.java, ownerProducer, factoryProducer)