package omg.jd.tvmazeapiclient

import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import omg.jd.tvmazeapiclient.mvp.BasePresenter
import omg.jd.tvmazeapiclient.mvp.BaseView

abstract class BaseActivity<V : BaseView, P : BasePresenter<V>> : AppCompatActivity(), LoaderManager.LoaderCallbacks<P> {

    companion object {
        val LOADER_ID = this::class.java.name.hashCode()
    }

    open var presenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportLoaderManager.initLoader(LOADER_ID, null, this)
    }

    override fun onLoadFinished(loader: Loader<P>?, presenter: P) {
        this.presenter = presenter
    }

    override fun onLoaderReset(loader: Loader<P>?) {
        presenter = null
    }


}