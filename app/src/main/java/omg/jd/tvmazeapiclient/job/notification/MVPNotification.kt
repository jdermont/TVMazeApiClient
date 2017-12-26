package omg.jd.tvmazeapiclient.job.notification

import android.app.job.JobParameters
import android.net.Uri

interface MVPNotification {
    interface Interactor {

    }

    interface Presenter {
        fun onStartJob(job: JobParameters?)
        fun onStopJob(job: JobParameters?)
    }

    interface View {
        fun showNotification(showId: Long, title: String, text: String, uri: Uri?, useVibration: Boolean)
    }
}