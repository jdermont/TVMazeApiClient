package omg.jd.tvmazeapiclient.components.main

import omg.jd.tvmazeapiclient.mvp.PresenterFactory

class MainPresenterFactory : PresenterFactory<MVPMain.Presenter> {
    override fun create(): MVPMain.Presenter {
        val interactor: MainInteractor = MainInteractor()
        return MainPresenter(interactor)
    }
}
