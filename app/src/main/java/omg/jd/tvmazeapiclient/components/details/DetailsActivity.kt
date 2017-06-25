package omg.jd.tvmazeapiclient.components.details

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.Loader
import kotlinx.android.synthetic.main.activity_details.*
import omg.jd.tvmazeapiclient.BaseActivity
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.db.model.TvShow
import omg.jd.tvmazeapiclient.mvp.PresenterLoader
import omg.jd.tvmazeapiclient.utils.ScreenHelper.ToolbarState
import omg.jd.tvmazeapiclient.utils.loadUrl

class DetailsActivity : BaseActivity<MVPDetails.View, MVPDetails.Presenter>(), MVPDetails.View {
    companion object {
        const val EXTRA_TVSHOW = "EXTRA_TVSHOW"
    }

    override var presenter: MVPDetails.Presenter? = null
    lateinit var tvShow: TvShow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(detailsToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayUseLogoEnabled(false)

        detailsAppBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {

            internal var state = ToolbarState.IDLE
            internal var isShow = false
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }

                if (verticalOffset == 0) {
                    if (state != ToolbarState.EXPANDED) {
                        detailsShowImage.transitionName = getString(R.string.transition_image)
                        state = ToolbarState.EXPANDED
                    }
                } else if (verticalOffset + scrollRange == 0) {
                    if (state != ToolbarState.COLLAPSED) {
                        detailsShowImage.transitionName = ""
                        state = ToolbarState.COLLAPSED
                    }
                } else {
                    if (state != ToolbarState.IDLE) {
                        state = ToolbarState.IDLE
                    }
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

        tvShow = intent.extras.getParcelable<TvShow>(EXTRA_TVSHOW)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<MVPDetails.Presenter> {
        return PresenterLoader(applicationContext, DetailsPresenterFactory())
    }

    override fun onLoadFinished(loader: Loader<MVPDetails.Presenter>?, presenter: MVPDetails.Presenter) {
        super.onLoadFinished(loader, presenter)
        presenter.onInit(tvShow)
    }

    override fun loadImageHeader(imageUrl: String?) {
        detailsShowImage.loadUrl(imageUrl, R.drawable.placeholder)
    }
}
