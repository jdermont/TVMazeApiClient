package omg.jd.tvmazeapiclient.job.update

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.util.Log
import omg.jd.tvmazeapiclient.job.JobUtil

class UpdateDataService : JobService(), MVPUpdate.View {
    companion object {
        const val TAG = "UpdateDataService"
    }

    val presenter: MVPUpdate.Presenter by lazy {
        val interactor = UpdateInteractor()
        UpdatePresenter(this, interactor)
    }

    override fun onStartJob(job: JobParameters?): Boolean {
        Log.d(TAG,"onStartJob")
        presenter.onStartJob(job)
        return false
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        Log.d(TAG,"onStopJob "+job)
        return false
    }

    override fun finishJob(job: JobParameters?) {
        JobUtil.rescheduleUpdateDataJob(applicationContext)
        jobFinished(job,false)
    }
}