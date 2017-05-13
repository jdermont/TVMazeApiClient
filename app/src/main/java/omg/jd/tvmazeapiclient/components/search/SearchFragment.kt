package omg.jd.tvmazeapiclient.components.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_search.*
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.components.search.recyclerview.SearchItemsAdapter
import omg.jd.tvmazeapiclient.ws.model.TVShow

class SearchFragment : Fragment(), MVPSearch.View {
    private val presenter: MVPSearch.Presenter by lazy { SearchPresenter(this) }

    val adapter: SearchItemsAdapter = SearchItemsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.onSearch(s.toString())
            }
        })
        searchRecyclerView.layoutManager = LinearLayoutManager(context)
        searchRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun setShows(shows: List<TVShow>) {
        adapter.updateList(shows)
    }

}
