package omg.jd.tvmazeapiclient.components.search

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import omg.jd.tvmazeapiclient.RxAndroidSchedulersOverrideRule
import omg.jd.tvmazeapiclient.ws.model.TVShow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
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
        presenter = SearchPresenter(view,interactor)
    }

    @Test
    fun testOnSearch() {
        val showList = createShowList()
        val searchText = "search string"
        val observable = Observable
                .fromIterable(showList)
                .toList()
                .toObservable()
        `when`(interactor.searchShows(searchText)).thenReturn(observable)

        presenter.onSearch(searchText)

        verify(interactor).searchShows(searchText)
        verify(view).setShows(showList)
    }

    private fun createShowList(): List<TVShow> {
        val tvShow = TVShow(0.0,null)
        return listOf(tvShow)
    }
}