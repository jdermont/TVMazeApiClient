package omg.jd.tvmazeapiclient.job

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.preference.PreferenceManager
import omg.jd.tvmazeapiclient.components.settings.isRefreshEnabled
import omg.jd.tvmazeapiclient.components.settings.isRefreshWithWifiOnly
import omg.jd.tvmazeapiclient.job.update.UpdateDataService

object JobUtil {
    private const val UPDATE_DATA_JOB_ID: Int = 1

    fun rescheduleUpdateDataJob(context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancelAll()

        if (prefs.isRefreshEnabled()) {
            val job = JobInfo.Builder(UPDATE_DATA_JOB_ID, ComponentName(context, UpdateDataService::class.java))
                    .setRequiresCharging(false)
                    .setRequiresDeviceIdle(false)
                    .setRequiredNetworkType(if (prefs.isRefreshWithWifiOnly()) JobInfo.NETWORK_TYPE_UNMETERED else JobInfo.NETWORK_TYPE_ANY)
                    .setPeriodic(60 * 1000L)
                    .build()

            jobScheduler.schedule(job)
        }
    }
}