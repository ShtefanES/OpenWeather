package ru.neoanon.openweather.utils.logging

import timber.log.Timber
import android.util.Log.ERROR


/**
 *Created by eshtefan on  08.11.2018.
 */

class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == ERROR && t != null) {
            //TODO add connect to crash monitoring system, for example Crashlytics
        }
    }
}