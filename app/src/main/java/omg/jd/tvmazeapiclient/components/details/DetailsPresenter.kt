package omg.jd.tvmazeapiclient.components.details

import omg.jd.tvmazeapiclient.db.model.TvShow

class DetailsPresenter(val interactor: MVPDetails.Interactor) : MVPDetails.Presenter {
    override var view: MVPDetails.View? = null

    override fun onInit(tvShow: TvShow) {
        interactor.tvShow = tvShow
        view?.loadImageHeader(tvShow.originalImage)
        view?.setupViews(tvShow)
    }
}