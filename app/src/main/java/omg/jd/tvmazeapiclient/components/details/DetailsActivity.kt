package omg.jd.tvmazeapiclient.components.details

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.Loader
import kotlinx.android.synthetic.main.activity_details.*
import omg.jd.tvmazeapiclient.BaseActivity
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.db.model.Episode
import omg.jd.tvmazeapiclient.db.model.TvShow
import omg.jd.tvmazeapiclient.mvp.PresenterLoader
import omg.jd.tvmazeapiclient.utils.ScreenHelper.ToolbarState
import omg.jd.tvmazeapiclient.utils.StringUtils
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

        tvShow = intent.extras.getParcelable<TvShow>(EXTRA_TVSHOW)

        initToolbar()
        initViews()
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayUseLogoEnabled(false)
    }

    private fun initViews() {
        detailsAppBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var state = ToolbarState.IDLE
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }

                if (verticalOffset == 0) {
                    if (state != ToolbarState.EXPANDED) {
                        detailsShowImage.transitionName = getString(R.string.transition_image)
                        detailsCollapsingToolbarLayout.title = " "
                        state = ToolbarState.EXPANDED
                    }
                } else if (verticalOffset + scrollRange == 0) {
                    if (state != ToolbarState.COLLAPSED) {
                        detailsShowImage.transitionName = ""
                        detailsCollapsingToolbarLayout.title = tvShow.name
                        state = ToolbarState.COLLAPSED
                    }
                } else {
                    if (state != ToolbarState.IDLE) {
                        detailsCollapsingToolbarLayout.title = " "
                        state = ToolbarState.IDLE
                    }
                }
            }
        })
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

    override fun setupViews(tvShow: TvShow) {
        val detailsString = "${tvShow.premiered ?: "-"}\n${tvShow.type ?: "-"}\n${tvShow.status ?: "-"}\n${tvShow.rating}"
        detailsDescriptionText.text = detailsString
        detailsSummaryText.text = StringUtils.fromHtmlCompat(tvShow.summary)
    }

    override fun setupEpisodes(latestEpisode: Episode?, nextEpisode: Episode?) {
        val latestString =
                if (latestEpisode == null) ""
                else "${StringUtils.startPadZero(latestEpisode.season)}x${StringUtils.startPadZero(latestEpisode.number)}"
        val nextString =
                if (nextEpisode == null) ""
                else "${StringUtils.startPadZero(nextEpisode.season)}x${StringUtils.startPadZero(nextEpisode.number)}"
        detailsEpisodesText.text = latestString+"\n"+nextString
    }
}
