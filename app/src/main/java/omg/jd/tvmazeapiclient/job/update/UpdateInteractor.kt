package omg.jd.tvmazeapiclient.job.update

import android.util.Log
import com.raizlabs.android.dbflow.kotlinextensions.save
import io.reactivex.Observable
import omg.jd.tvmazeapiclient.db.MainDatabase
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.ws.ApiClient
import omg.jd.tvmazeapiclient.ws.model.convertToTvShowEntity

class UpdateInteractor : MVPUpdate.Interactor {
    companion object {
        const val TAG = "UpdateInteractor"
    }

    override fun loadShowIdList(): Observable<List<Long>> {
        return MainDatabase.loadShowIdList()
    }

    override fun updateShow(showId: Long) {
        Log.d(TAG, "updateShow $showId begin")
        retrieveShow(showId).subscribe(
                { // onNext
                    MainDatabase.saveTvShow(it)
                },
                { // onError
                    it.printStackTrace()
                },
                { // onComplete
                }
        )
        Log.d(TAG, "updateShow $showId end")
    }

    private fun retrieveShow(showId: Long): Observable<TvShow> {
        return ApiClient.retrieveTVShow(showId.toString())
                .map { it.convertToTvShowEntity() }
    }
}