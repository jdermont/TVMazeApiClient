package omg.jd.tvmazeapiclient.mvp


interface BasePresenter {
    fun onViewAttached(view: BaseView)
    fun onViewDetached()
    fun onDestroyed()
}