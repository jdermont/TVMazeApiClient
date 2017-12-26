package omg.jd.tvmazeapiclient.job.notification

import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.support.v4.app.NotificationCompat
import android.util.Log
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.components.main.MainActivity
import omg.jd.tvmazeapiclient.db.MainDatabase


class NotificationService : IntentService("NotificationService"), MVPNotification.View {
    companion object {
        const val TAG = "NotificationService"
        const val KEY_TVSHOW_ID = "KEY_TVSHOW_ID"
    }

    val presenter: MVPNotification.Presenter by lazy {
        val interactor = NotificationInteractor()
        NotificationPresenter(this, interactor)
    }

    override fun onHandleIntent(intent: Intent) {
        Log.d(TAG,"onHandleIntent")
        val id = intent.getLongExtra(KEY_TVSHOW_ID,0)
        MainDatabase.loadShowSingle(id)
                .subscribe {
                    showNotification(it.id,it.name!!,it.name!!,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),true)
                }
    }

    override fun showNotification(showId: Long, title: String, text: String, uri: Uri?, useVibration: Boolean) {
        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.mipmap.ic_launcher)
        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT)

        val notificationBuilder = NotificationCompat.Builder(this, "channelId")
                .setSmallIcon(R.drawable.ic_tv_white_24dp)
                .setLargeIcon(bitmap)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(text)

        if (uri != null) {
            //notificationBuilder.setSound(uri)
        }

        if (useVibration) {
            notificationBuilder.setVibrate(longArrayOf(1000L,1000L,1000L))
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(showId.toInt(), notificationBuilder.build())
    }
}