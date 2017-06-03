package omg.jd.tvmazeapiclient.mvp


interface BasePresenter<T : BaseView> {
    var view : T?

    fun onViewAttached(view: T) {
        this.view = view
    }

    fun onViewDetached() {
        this.view = null
    }

    fun onDestroyed() {
        this.view = null
    }
}