package omg.jd.tvmazeapiclient.components.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.content.Loader
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.activity_main.*
import omg.jd.tvmazeapiclient.BaseActivity
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.components.details.MVPDetails
import omg.jd.tvmazeapiclient.components.main.recyclerview.MainItemsAdapter
import omg.jd.tvmazeapiclient.components.search.SearchActivity
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.mvp.PresenterLoader

class MainActivity : BaseActivity<MVPMain.View, MVPMain.Presenter>(), MVPMain.View {
    override var presenter: MVPMain.Presenter? = null

    val adapter: MainItemsAdapter = MainItemsAdapter()

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
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search_for_tvshow -> {
                val intent = Intent(applicationContext,SearchActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> { }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<MVPMain.Presenter> {
        return PresenterLoader(applicationContext, MainPresenterFactory())
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
}