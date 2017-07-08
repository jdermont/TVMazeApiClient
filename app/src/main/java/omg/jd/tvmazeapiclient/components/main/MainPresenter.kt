package omg.jd.tvmazeapiclient.components.main

class MainPresenter(val interactor: MVPMain.Interactor) : MVPMain.Presenter {
    override var view: MVPMain.View? = null

    override fun onInit() {
//        view?.setLoading()
//        interactor.getShowList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    view?.setShows(it)
//                }
    }

}