package omg.jd.tvmazeapiclient.components.details

import android.os.Bundle
import android.support.v4.content.Loader
import kotlinx.android.synthetic.main.activity_details.*
import omg.jd.tvmazeapiclient.BaseActivity
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.mvp.PresenterLoader
import omg.jd.tvmazeapiclient.utils.loadUrl

class DetailsActivity : BaseActivity<MVPDetails.View, MVPDetails.Presenter>(), MVPDetails.View {

    companion object {
        const val EXTRA_TVSHOW = "EXTRA_TVSHOW"
    }

    override var presenter: MVPDetails.Presenter? = null

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

}
