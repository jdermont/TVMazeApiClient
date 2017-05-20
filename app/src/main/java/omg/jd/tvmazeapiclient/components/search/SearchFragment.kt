package omg.jd.tvmazeapiclient.components.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.fragment_search.*
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.components.search.recyclerview.SearchItemsAdapter
import omg.jd.tvmazeapiclient.db.model.TvShow
import omg.jd.tvmazeapiclient.mvp.PresenterLoader


class SearchFragment : Fragment(), LoaderManager.LoaderCallbacks<MVPSearch.Presenter>, MVPSearch.View {

    companion object {
        const val LOADER_ID: Int = 109
    }

    var presenter: MVPSearch.Presenter? = null

    val adapter: SearchItemsAdapter = SearchItemsAdapter()

    var searchView: SearchView? = null
    var queryTextListener: SearchView.OnQueryTextListener? = null

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchManager = activity.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }

        if (searchView != null) {
            searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity.componentName))

            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    presenter?.onSearch(newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    presenter?.onSearch(query)
                    return true
                }
            }
            searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    presenter?.onSearch(newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    presenter?.onSearch(query)
                    return true
                }
            })
        }
        super.onCreateOptionsMenu(menu, inflater)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        activity.supportLoaderManager.initLoader(LOADER_ID, null, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        (activity as AppCompatActivity).setSupportActionBar(searchToolbar)
        searchRecyclerView.layoutManager = LinearLayoutManager(context)
        searchRecyclerView.adapter = adapter
    }

    override fun onLoadFinished(loader: Loader<MVPSearch.Presenter>?, presenter: MVPSearch.Presenter?) {
        this.presenter = presenter
    }

    override fun onLoaderReset(loader: Loader<MVPSearch.Presenter>?) {
        presenter = null
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<MVPSearch.Presenter> {
        return PresenterLoader(context, SearchPresenterFactory())
    }

    override fun onResume() {
        super.onResume()
        presenter?.onViewAttached(this)
    }

    override fun onPause() {
        super.onPause()
        presenter?.onViewDetached()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroyed()
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

}
