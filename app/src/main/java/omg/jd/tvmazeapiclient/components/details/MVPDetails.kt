package omg.jd.tvmazeapiclient.components.details

import omg.jd.tvmazeapiclient.db.model.TvShow
import omg.jd.tvmazeapiclient.mvp.BasePresenter
import omg.jd.tvmazeapiclient.mvp.BaseView

interface MVPDetails {
    interface Interactor {
        var tvShow: TvShow
    }

    interface Presenter : BasePresenter<View> {
        fun onInit(tvShow: TvShow)
    }

    interface View : BaseView {
        fun loadImageHeader(imageUrl: String?)
        fun setupViews(tvShow: TvShow)
    }
}