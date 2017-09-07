package omg.jd.tvmazeapiclient.job

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val serviceIntent = Intent(context, BootService::class.java)
        context.startService(serviceIntent)
    }
}