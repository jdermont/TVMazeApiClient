package omg.jd.tvmazeapiclient.mvp

import android.content.Context
import android.support.v4.content.Loader

class PresenterLoader<T : BasePresenter<*>>(context: Context, private val factory: PresenterFactory<T>) : Loader<T>(context) {
    private var presenter: T? = null

    override fun onStartLoading() {
        if (presenter != null) {
            deliverResult(presenter)
            return
        }

        forceLoad()
    }

    override fun onForceLoad() {
        // Create the Presenter using the Factory
        presenter = factory.create()

        // Deliver the result
        deliverResult(presenter)
    }

    override fun onReset() {
        presenter?.onDestroyed()
        presenter = null
    }
}