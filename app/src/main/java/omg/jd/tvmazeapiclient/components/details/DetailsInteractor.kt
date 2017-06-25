package omg.jd.tvmazeapiclient.components.details

import omg.jd.tvmazeapiclient.db.model.TvShow

class DetailsInteractor : MVPDetails.Interactor {

    private lateinit var cachedTvShow: TvShow

    override var tvShow: TvShow
        get() = cachedTvShow
        set(value) {
            cachedTvShow = value
        }

}