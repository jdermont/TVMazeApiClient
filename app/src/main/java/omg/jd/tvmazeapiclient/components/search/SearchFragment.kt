package omg.jd.tvmazeapiclient.components.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.fragment_search.*
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.components.search.recyclerview.SearchItemsAdapter
import omg.jd.tvmazeapiclient.db.model.TvShow
import omg.jd.tvmazeapiclient.mvp.PresenterLoader
import omg.jd.tvmazeapiclient.utils.VerticalSpaceItemDecoration


class SearchFragment : Fragment(), LoaderManager.LoaderCallbacks<MVPSearch.Presenter>, MVPSearch.View {

    companion object {
        const val LOADER_ID: Int = 109
    }

    var presenter: MVPSearch.Presenter? = null

    val adapter: SearchItemsAdapter = SearchItemsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        searchEditText.post { // workaround so it wont be triggered after orientation change
            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    presenter?.onSearch(s.toString())
                }
            })
        }
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
