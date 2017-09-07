package omg.jd.tvmazeapiclient.job

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import omg.jd.tvmazeapiclient.components.settings.isRefreshEnabled
import omg.jd.tvmazeapiclient.components.settings.isRefreshWithWifiOnly
import omg.jd.tvmazeapiclient.job.update.UpdateDataService

object JobUtil {
    private const val PERIOD: Long = 6 * 3600 * 1000L // every 6 hours
    private const val UPDATE_DATA_JOB_ID: Int = 1

    fun rescheduleUpdateDataJob(context: Context) {
        Log.d("JobUtil", "rescheduleUpdateDataJob")
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancelAll()

        if (prefs.isRefreshEnabled()) {
            val job = JobInfo.Builder(UPDATE_DATA_JOB_ID, ComponentName(context, UpdateDataService::class.java))
                    .setRequiresCharging(false)
                    .setRequiresDeviceIdle(false)
                    .setRequiredNetworkType(if (prefs.isRefreshWithWifiOnly()) JobInfo.NETWORK_TYPE_UNMETERED else JobInfo.NETWORK_TYPE_ANY)
                    .setMinimumLatency(PERIOD)
                    .build()

            jobScheduler.schedule(job)
        }
    }
}