package omg.jd.tvmazeapiclient.components.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.Loader
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.activity_search.*
import omg.jd.tvmazeapiclient.BaseActivity
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.components.details.DetailsActivity
import omg.jd.tvmazeapiclient.components.search.recyclerview.SearchItemViewHolder
import omg.jd.tvmazeapiclient.components.search.recyclerview.SearchItemsAdapter
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.mvp.PresenterLoader


class SearchActivity : BaseActivity<MVPSearch.View, MVPSearch.Presenter>(), MVPSearch.View,
        SearchItemsAdapter.ViewHolderOnClickListener {

    companion object {
        const val SEARCH_TEXT_KEY = "SEARCH_TEXT_KEY"
    }

    override var presenter: MVPSearch.Presenter? = null

    val adapter: SearchItemsAdapter = SearchItemsAdapter(this)

    var searchText: String = ""
    var searchView: SearchView? = null
    val queryTextListener: SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String): Boolean {
            searchText = newText
            presenter?.onSearch(newText)
            return true
        }

        override fun onQueryTextSubmit(query: String): Boolean {
            searchText = query
            presenter?.onSearch(query)
            return true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(searchToolbar)

        initViews()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        searchText = savedInstanceState?.getString(SEARCH_TEXT_KEY) ?: ""
    }

    private fun initViews() {
        val colspan: Int = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
        searchRecyclerView.layoutManager = StaggeredGridLayoutManager(colspan,StaggeredGridLayoutManager.VERTICAL)
        searchRecyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView = searchItem?.actionView as SearchView
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(this.componentName))
        searchView?.setOnQueryTextListener(queryTextListener)
        if (!searchText.isEmpty()) {
            searchView?.setQuery(searchText, false)
            searchItem.expandActionView()
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search ->
                // Not implemented here
                return false
            else -> {
            }
        }
        searchView?.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(SEARCH_TEXT_KEY, searchText)
        super.onSaveInstanceState(outState)
    }

    override fun setShows(shows: List<TvShow>) {
        adapter.updateList(shows)
        if (shows.isEmpty()) {
            setEmpty()
        } else {
            searchMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        }
    }

    override fun setLoading() {
        searchMultiStateView.viewState = MultiStateView.VIEW_STATE_LOADING
    }

    override fun setEmpty() {
        searchMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<MVPSearch.Presenter> {
        return PresenterLoader(applicationContext, SearchPresenterFactory())
    }

    override fun onClick(viewHolder: SearchItemViewHolder) {
        presenter?.onItemClick(viewHolder)
    }

    override fun showDetails(show: TvShow, transitedView: View) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.EXTRA_TVSHOW, show)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, transitedView, getString(R.string.transition_image))
        startActivity(intent, options.toBundle())
    }

    override fun errorOnGettingList() {
        searchMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        Toast.makeText(applicationContext, R.string.error_while_searching, Toast.LENGTH_SHORT).show()
    }

}
