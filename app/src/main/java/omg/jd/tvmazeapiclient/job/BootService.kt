package omg.jd.tvmazeapiclient.job

import android.app.IntentService
import android.content.Intent
import android.util.Log

class BootService : IntentService("BootService") {
    override fun onHandleIntent(intent: Intent?) {
        Log.d("BootService", "onHandleIntent")
        JobUtil.rescheduleUpdateDataJob(applicationContext)
    }
}
