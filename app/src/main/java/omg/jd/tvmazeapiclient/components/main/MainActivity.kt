package omg.jd.tvmazeapiclient.components.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.Loader
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.activity_main.*
import omg.jd.tvmazeapiclient.BaseActivity
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.components.details.DetailsActivity
import omg.jd.tvmazeapiclient.components.main.recyclerview.MainItemsAdapter
import omg.jd.tvmazeapiclient.components.search.SearchActivity
import omg.jd.tvmazeapiclient.components.settings.SettingsActivity
import omg.jd.tvmazeapiclient.entity.EntityUtils.SORT_BY
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.mvp.PresenterLoader
import omg.jd.tvmazeapiclient.recyclerview.TvShowViewHolder

class MainActivity : BaseActivity<MVPMain.View, MVPMain.Presenter>(), MVPMain.View, TvShowViewHolder.ViewHolderOnClickListener {
    override var presenter: MVPMain.Presenter? = null

    val adapter: MainItemsAdapter = MainItemsAdapter(this)

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)

        initViews()
    }

    private fun initViews() {
        val colspan: Int = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
        mainRecyclerView.layoutManager = StaggeredGridLayoutManager(colspan, StaggeredGridLayoutManager.VERTICAL)
        mainRecyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu)
        this.menu = menu
        presenter?.onMenuCreated()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search_for_tvshow -> presenter?.onStartSearchComponentClicked()
            R.id.action_sort_by_default -> presenter?.onSortByClicked(SORT_BY.DEFAULT)
            R.id.action_sort_by_premiered -> presenter?.onSortByClicked(SORT_BY.PREMIERED)
            R.id.action_sort_by_next_episode -> presenter?.onSortByClicked(SORT_BY.NEXT_EPISODE)
            R.id.action_settings -> presenter?.onSettingsClicked()
            else -> { }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun startSearchComponent() {
        val intent = Intent(applicationContext,SearchActivity::class.java)
        startActivity(intent)
    }

    override fun checkSortBy(sortBy: SORT_BY) {
        val itemId: Int
        when (sortBy) {
            SORT_BY.DEFAULT -> itemId = R.id.action_sort_by_default
            SORT_BY.PREMIERED -> itemId = R.id.action_sort_by_premiered
            SORT_BY.NEXT_EPISODE -> itemId = R.id.action_sort_by_next_episode
        }
        val item: MenuItem? = menu?.findItem(itemId)
        item?.isChecked = true
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<MVPMain.Presenter> {
        return PresenterLoader(applicationContext, MainPresenterFactory(PreferenceManager.getDefaultSharedPreferences(applicationContext)))
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }

    override fun setLoading() {
        mainMultiStateView.viewState = MultiStateView.VIEW_STATE_LOADING
    }

    override fun setEmpty() {
        mainMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
    }

    override fun setShows(shows: List<TvShow>) {
        adapter.updateList(shows)
        if (shows.isEmpty()) {
            setEmpty()
        } else {
            mainMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        }
    }

    override fun onItemClick(viewHolder: TvShowViewHolder) {
        presenter?.onItemClick(viewHolder)
    }

    override fun showDetails(show: TvShow, transitedView: View) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.EXTRA_TVSHOW, show.copy(episodes = listOf()))
        intent.putExtra(DetailsActivity.EXTRA_CONTAINS_EPISODES, !show.episodes.isEmpty())
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, transitedView, getString(R.string.transition_image))
        startActivity(intent, options.toBundle())
    }

    override fun startSettingsComponent() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
}