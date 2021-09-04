package ru.neoanon.openweather.utils

import android.os.Bundle
import androidx.fragment.app.Fragment

val Fragment.args: Bundle
	get() = arguments
		?: throw IllegalArgumentException("Fragment has no arguments")