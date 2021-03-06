package omg.jd.tvmazeapiclient.job.update

import android.app.job.JobParameters
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UpdatePresenter(val view: MVPUpdate.View,
                      val interactor: MVPUpdate.Interactor) : MVPUpdate.Presenter {
    override fun onStartJob(job: JobParameters?) {
        interactor.loadShowIdList()
                .doOnNext {
                    it.forEach { interactor.updateShow(it) }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.finishJob(job)
                }
    }

    override fun onStopJob(job: JobParameters?) {
        // stub, not used?
    }
}