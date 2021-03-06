package omg.jd.tvmazeapiclient

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * This rule registers SchedulerHooks for RxJava and RxAndroid to ensure that subscriptions always
 * subscribeOn and observeOn Schedulers.immediate(). Warning, this rule will reset RxAndroidPlugins
 * and RxJavaPlugins before and after each test so if the application code uses RxJava plugins this
 * may affect the behaviour of the testing method.
 */
class RxAndroidSchedulersOverrideRule : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
                base.evaluate()
            }
        }
    }
}
