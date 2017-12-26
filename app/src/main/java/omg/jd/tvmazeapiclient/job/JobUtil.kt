package omg.jd.tvmazeapiclient.job

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.preference.PreferenceManager
import android.util.Log
import omg.jd.tvmazeapiclient.components.settings.areNotificationsEnabled
import omg.jd.tvmazeapiclient.components.settings.isRefreshEnabled
import omg.jd.tvmazeapiclient.components.settings.isRefreshWithWifiOnly
import omg.jd.tvmazeapiclient.job.notification.NotificationService
import omg.jd.tvmazeapiclient.job.update.UpdateDataService

object JobUtil {
    private const val PERIOD: Long = 6 * 3600 * 1000L // every 6 hours
    private const val UPDATE_DATA_JOB_ID: Int = 1

    fun rescheduleUpdateDataJob(context: Context) {
        Log.d("JobUtil", "rescheduleUpdateDataJob")
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(UPDATE_DATA_JOB_ID)

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

    fun scheduleNotificationJob(context: Context, tvShowId: Long, whenRealTime: Long) {
        Log.d("JobUtil", "scheduleNotificationJob")
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (prefs.areNotificationsEnabled()) {

        } else {

        }

        val intent = Intent(context,NotificationService::class.java)
        intent.putExtra(NotificationService.KEY_TVSHOW_ID,tvShowId)
        val pendingIntent = PendingIntent.getService(context, tvShowId.toInt(), intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val timeStart = getBootTimeFromRealTime(whenRealTime) - 3600 * 1000L
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, timeStart, pendingIntent)
        } else {
            alarmManager.setWindow(AlarmManager.ELAPSED_REALTIME_WAKEUP, timeStart, 3600 * 1000L, pendingIntent)
        }
    }

    private fun getBootTimeFromRealTime(calendarTime: Long): Long {
        val difference = calendarTime - System.currentTimeMillis()
        return SystemClock.elapsedRealtime() + difference
    }
}