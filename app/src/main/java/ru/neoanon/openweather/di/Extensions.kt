package ru.neoanon.openweather.di

import android.app.Activity
import android.support.v4.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection

/**
 *Created by eshtefan on  06.12.2018.
 */

fun Activity.daggerInject() = AndroidInjection.inject(this)

fun Fragment.daggerInject() = AndroidSupportInjection.inject(this)