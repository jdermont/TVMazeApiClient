package omg.jd.tvmazeapiclient.components.details

import omg.jd.tvmazeapiclient.mvp.PresenterFactory

class DetailsPresenterFactory : PresenterFactory<MVPDetails.Presenter> {
    override fun create(): MVPDetails.Presenter {
        val interactor: DetailsInteractor = DetailsInteractor()
        return DetailsPresenter(interactor)
    }
}