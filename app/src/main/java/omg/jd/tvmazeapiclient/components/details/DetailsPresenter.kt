package omg.jd.tvmazeapiclient.components.details

import omg.jd.tvmazeapiclient.mvp.BaseView

class DetailsPresenter(val interactor: DetailsInteractor) : MVPDetails.Presenter {

    var view: MVPDetails.View? = null

    override fun onViewAttached(view: BaseView) {
        this.view = view as MVPDetails.View
    }

    override fun onViewDetached() {
        this.view = null
    }

    override fun onDestroyed() {
        this.view = null
    }

}