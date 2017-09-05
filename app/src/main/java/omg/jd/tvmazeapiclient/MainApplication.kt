package omg.jd.tvmazeapiclient

import android.app.Application
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        FlowManager.init(FlowConfig.Builder(this).build())
    }
}