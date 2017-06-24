package omg.jd.tvmazeapiclient.components.details

import omg.jd.tvmazeapiclient.db.model.TvShow

class DetailsPresenter(val interactor: MVPDetails.Interactor) : MVPDetails.Presenter {
    override var view: MVPDetails.View? = null

    override fun onInit(tvShow: TvShow) {
        view?.loadImageHeader(tvShow.originalImage)
    }
}