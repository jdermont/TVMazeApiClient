package omg.jd.tvmazeapiclient.components.details

import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.mvp.PresenterLoader
import omg.jd.tvmazeapiclient.utils.loadUrl

class DetailsActivity : AppCompatActivity(), MVPDetails.View, LoaderManager.LoaderCallbacks<MVPDetails.Presenter> {

    companion object {
        const val EXTRA_TVSHOW = "EXTRA_TVSHOW"
    }

    var presenter: MVPDetails.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        detailsShowImage.loadUrl(intent.extras.getString(EXTRA_TVSHOW), R.drawable.placeholder)
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<MVPDetails.Presenter> {
        return PresenterLoader(applicationContext, DetailsPresenterFactory())
    }

    override fun onLoadFinished(loader: Loader<MVPDetails.Presenter>?, presenter: MVPDetails.Presenter?) {
        this.presenter = presenter
    }

    override fun onLoaderReset(loader: Loader<MVPDetails.Presenter>?) {
        presenter = null
    }
}
