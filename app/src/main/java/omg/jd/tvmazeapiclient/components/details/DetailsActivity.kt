package omg.jd.tvmazeapiclient.components.details

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.Loader
import kotlinx.android.synthetic.main.activity_details.*
import omg.jd.tvmazeapiclient.BaseActivity
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.db.model.TvShow
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
        setSupportActionBar(detailsToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayUseLogoEnabled(false)

        detailsAppBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = false
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }

                if (scrollRange + verticalOffset <= 64) {
                    detailsCollapsingToolbarLayout.title = "Title"
                    isShow = true
                } else if (isShow) {
                    detailsCollapsingToolbarLayout.title = " "
                    isShow = false
                }
            }
        })

        detailsShowImage.loadUrl(intent.extras.getParcelable<TvShow>(EXTRA_TVSHOW).originalImage, R.drawable.placeholder)
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<MVPDetails.Presenter> {
        return PresenterLoader(applicationContext, DetailsPresenterFactory())
    }

}
