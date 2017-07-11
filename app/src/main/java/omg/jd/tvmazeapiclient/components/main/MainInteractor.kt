package omg.jd.tvmazeapiclient.components.main

import com.raizlabs.android.dbflow.sql.language.SQLite
import io.reactivex.Observable
import omg.jd.tvmazeapiclient.components.main.MVPMain.Interactor.SORT_BY
import omg.jd.tvmazeapiclient.db.model.DbFlowTvShow
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.entity.convertToTvShowEntity

class MainInteractor : MVPMain.Interactor {

    override var sortBy: SORT_BY = SORT_BY.DEFAULT

    private lateinit var cachedShowList: List<TvShow>

    override fun loadShowList(): Observable<List<TvShow>> {
        return Observable.fromCallable {
            SQLite.select().from(DbFlowTvShow::class.java)
                    .queryList()
        }
                .map {
                    it.map { it.convertToTvShowEntity() }
                            .sortedWith(Comparator(sortBy.comparator))
                }
                .doOnNext { cachedShowList = it }
    }

    override fun sortShowList(sortBy: SORT_BY): List<TvShow> {
        this.sortBy = sortBy
        cachedShowList = cachedShowList.sortedWith(Comparator(sortBy.comparator))
        return cachedShowList
    }

}