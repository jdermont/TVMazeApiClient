package omg.jd.tvmazeapiclient.components.main

import io.reactivex.Observable
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.mvp.BasePresenter
import omg.jd.tvmazeapiclient.mvp.BaseView

interface MVPMain {
    interface Interactor {
        fun getShowList(): Observable<List<TvShow>>
    }

    interface Presenter : BasePresenter<View> {

    }

    interface View : BaseView {

    }
}