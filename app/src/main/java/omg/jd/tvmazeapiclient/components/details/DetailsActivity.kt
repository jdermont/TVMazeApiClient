package omg.jd.tvmazeapiclient.components.details

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.Loader
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_details.*
import omg.jd.tvmazeapiclient.BaseActivity
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.db.MainDatabase
import omg.jd.tvmazeapiclient.entity.TvShow
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

        tvShow = intent.extras.getParcelable<TvShow>(EXTRA_TVSHOW)

        initToolbar()
        initViews()
    }

    private fun initToolbar() {
        setSupportActionBar(detailsToolbar)
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
        detailsFloatingActionButton.setOnClickListener {
            presenter?.onFabClicked(detailsFloatingActionButton.tag as MainDatabase.TvShowInDB)
        }
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
        val detailsString = getString(
                R.string.detailsDescriptionText,
                tvShow.premiered ?: "-",
                tvShow.type ?: "-",
                tvShow.status ?: "-",
                tvShow.rating
        )
        detailsDescriptionText.text = detailsString
        detailsSummaryText.text = StringUtils.fromHtmlCompat(tvShow.summary)
    }

    override fun setFloatingActionButton(tvShowInDB: MainDatabase.TvShowInDB) {
        if (detailsFABOverlay.visibility == View.INVISIBLE) {
            detailsFABOverlay.visibility = View.VISIBLE
            val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
            detailsFABOverlay.startAnimation(animation)
        }
        when (tvShowInDB) {
            MainDatabase.TvShowInDB.IN_DB ->
                detailsFloatingActionButton.setImageResource(R.drawable.ic_delete_white_24dp)
            MainDatabase.TvShowInDB.NOT_IN_DB ->
                detailsFloatingActionButton.setImageResource(R.drawable.ic_add_white_24dp)
        }
        detailsFloatingActionButton.tag = tvShowInDB
    }

    override fun enableFloatingActionProgress() {
        detailsFloatingActionButton.isClickable = false
        detailsFABProgress.visibility = View.VISIBLE
    }

    override fun disableFloatingActionProgress() {
        detailsFloatingActionButton.isClickable = true
        detailsFABProgress.visibility = View.INVISIBLE
    }

    override fun writeEpisodes(latest: String, next: String) {
        detailsEpisodesText.text = getString(R.string.episodes_numbers,latest,next)
        detailsProgressBar.visibility = View.GONE
    }
}
