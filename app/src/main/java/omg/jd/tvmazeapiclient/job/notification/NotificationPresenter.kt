package omg.jd.tvmazeapiclient.job.notification

import android.app.job.JobParameters

class NotificationPresenter(val view: MVPNotification.View,
                            val interactor: MVPNotification.Interactor) : MVPNotification.Presenter {
    override fun onStartJob(job: JobParameters?) {

    }

    override fun onStopJob(job: JobParameters?) {
        // stub, not used?
    }
}