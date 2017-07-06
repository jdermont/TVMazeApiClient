package omg.jd.tvmazeapiclient.components.main

import com.raizlabs.android.dbflow.sql.language.SQLite
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import omg.jd.tvmazeapiclient.db.model.DbFlowTvShow
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.entity.convertToTvShowEntity

class MainInteractor : MVPMain.Interactor {
    override fun getShowList(): Observable<List<TvShow>> {
        return Observable.fromCallable {
            SQLite.select().from(DbFlowTvShow::class.java)
                    .queryList()
        }
                .subscribeOn(Schedulers.io())
                .map { it.map { it.convertToTvShowEntity() } }
    }

}