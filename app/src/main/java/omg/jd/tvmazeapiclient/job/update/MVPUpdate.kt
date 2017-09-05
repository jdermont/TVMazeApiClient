package omg.jd.tvmazeapiclient.job.update

import android.app.job.JobParameters
import io.reactivex.Observable

interface MVPUpdate {
    interface Interactor {
        fun loadShowIdList(): Observable<List<Long>>
        fun updateShow(showId: Long)
    }

    interface Presenter {
        fun onStartJob(job: JobParameters?)
        fun onStopJob(job: JobParameters?)
    }

    interface View {
        fun finishJob(job: JobParameters?)
    }
}
