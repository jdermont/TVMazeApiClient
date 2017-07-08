package omg.jd.tvmazeapiclient.components.search

import android.view.View
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import omg.jd.tvmazeapiclient.RxAndroidSchedulersOverrideRule
import omg.jd.tvmazeapiclient.components.search.recyclerview.SearchItemViewHolder
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.utils.createShow
import omg.jd.tvmazeapiclient.utils.createShowList
import omg.jd.tvmazeapiclient.ws.model.convertToTvShowEntity
import omg.jd.tvmazeapiclient.ws.model.WsTVShow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchPresenterTest {
    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule = RxAndroidSchedulersOverrideRule()

    @Mock
    lateinit var view: MVPSearch.View
    @Mock
    lateinit var interactor: MVPSearch.Interactor

    @InjectMocks
    lateinit var presenter: SearchPresenter

    @Before
    fun setUp() {
        presenter = SearchPresenter(interactor)
        presenter.onViewAttached(view)
    }

    @Test
    fun testOnSearch() {
        val showList = createShowList()
        val searchText = "search string"
        val observable = Observable
                .fromArray(showList)
        `when`(interactor.searchShows(searchText)).thenReturn(observable)

        presenter.onSearch(searchText)

        verify(interactor).searchShows(searchText)
        verify(view).setShows(showList.map { it.show.convertToTvShowEntity() })
    }

    @Test
    fun testOnSearchError() {
        val searchText = "search string"
        val observable = Observable
                .fromArray(listOf<WsTVShow>())
                .doOnNext { throw RuntimeException("No internet") }
        `when`(interactor.searchShows(searchText)).thenReturn(observable)

        presenter.onSearch(searchText)

        verify(interactor).searchShows(searchText)
        verify(view).errorOnGettingList()
    }

    @Test
    fun testOnItemClick() {
        val viewHolder = mock(SearchItemViewHolder::class.java)
        `when`(viewHolder.data).thenReturn(createShow().convertToTvShowEntity())
        `when`(viewHolder.transitedView).thenReturn(mock(View::class.java))

        presenter.onItemClick(viewHolder)

        verify(view).showDetails(viewHolder.data as TvShow, viewHolder.transitedView)
    }
}