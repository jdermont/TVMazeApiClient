package omg.jd.tvmazeapiclient.components.search

import omg.jd.tvmazeapiclient.mvp.PresenterFactory

class SearchPresenterFactory : PresenterFactory<MVPSearch.Presenter> {
    override fun create(): MVPSearch.Presenter {
        val interactor: SearchInteractor = SearchInteractor()
        return SearchPresenter(interactor)
    }

}