package omg.jd.tvmazeapiclient.components.main

import android.content.SharedPreferences
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import omg.jd.tvmazeapiclient.db.MainDatabase
import omg.jd.tvmazeapiclient.entity.EntityUtils
import omg.jd.tvmazeapiclient.entity.EntityUtils.SORT_BY
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.components.settings.getSortBy
import omg.jd.tvmazeapiclient.components.settings.putSortBy
import omg.jd.tvmazeapiclient.ws.ApiClient
import omg.jd.tvmazeapiclient.ws.model.convertToEpisodesListEntity

class MainInteractor(val pref: SharedPreferences) : MVPMain.Interactor {

    override var sortBy: SORT_BY = pref.getSortBy()
    override val needToReload: Boolean
        get() = toReload

    private lateinit var cachedShowList: List<TvShow>
    private var toReload: Boolean = true
    private val reloadDisposable: Disposable = MainDatabase.dbChangedSubject.subscribe { toReload = true }

    override fun loadFromDbShowList(): Observable<List<TvShow>> {
        return MainDatabase.loadShowList()
                .map { EntityUtils.sorted(it, sortBy) }
                .doOnNext {
                    toReload = false
                    cachedShowList = it
                }
    }

    override fun sortShowList(sortBy: SORT_BY): List<TvShow> {
        this.sortBy = sortBy
        pref.putSortBy(sortBy)
        cachedShowList = EntityUtils.sorted(cachedShowList, sortBy)
        return cachedShowList
    }

    override fun getShowList(): List<TvShow> {
        return cachedShowList
    }

    override fun updateShowList(showList: List<TvShow>): Observable<List<TvShow>> {
        return Observable.just(showList)
                .doOnNext {
                    it.forEach {
                        retrieveEpisodes(it).subscribe(
                                { // onNext
                                },
                                { // onError
                                    it.printStackTrace()
                                },
                                { // onComplete
                                    updateTvShow(it)
                                }
                        )
                    }
                }
    }

    private fun retrieveEpisodes(show: TvShow): Observable<TvShow> {
        return ApiClient.retrieveTVShow(show.id.toString())
                .map {
                    if (it.embedded != null) {
                        show.episodes = it.embedded.convertToEpisodesListEntity()
                    }
                    show
                }
    }

    private fun updateTvShow(show: TvShow) {
        MainDatabase.updateTvShow(show).subscribe()
    }

    override fun destroy() {
        reloadDisposable.dispose()
    }

}