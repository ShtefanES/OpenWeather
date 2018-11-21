package ru.neoanon.openweather

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 *Created by eshtefan on  19.11.2018.
 */

class RxTestRule : TestRule {
    override fun apply(base: Statement?, description: Description?): Statement =
        object : Statement() {
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { schedulerCallable -> Schedulers.trampoline() }
                try {
                    base?.evaluate()
                } finally {
                    RxAndroidPlugins.reset()
                    RxJavaPlugins.reset()
                }
            }
        }
}