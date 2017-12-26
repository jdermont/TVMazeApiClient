package omg.jd.tvmazeapiclient.job

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("BootReceiver", "onReceive")
        val serviceIntent = Intent(context, BootService::class.java)
        context.startService(serviceIntent)
    }
}