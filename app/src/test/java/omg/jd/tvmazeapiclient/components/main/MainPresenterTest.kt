package omg.jd.tvmazeapiclient.components.main

import android.view.View
import com.nhaarman.mockito_kotlin.mock
import omg.jd.tvmazeapiclient.RxAndroidSchedulersOverrideRule
import omg.jd.tvmazeapiclient.components.main.recyclerview.MainItemViewHolder
import omg.jd.tvmazeapiclient.entity.EntityUtils
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.utils.createShow
import omg.jd.tvmazeapiclient.ws.model.convertToTvShowEntity
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
class MainPresenterTest {
    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule = RxAndroidSchedulersOverrideRule()

    @Mock
    lateinit var view: MVPMain.View
    @Mock
    lateinit var interactor: MVPMain.Interactor

    @InjectMocks
    lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        presenter = MainPresenter(interactor)
        presenter.onViewAttached(view)
    }

    @Test
    fun testOnMenuCreated() {
        val sortBy = EntityUtils.SORT_BY.DEFAULT
        `when`(interactor.sortBy).thenReturn(sortBy)
        presenter.onMenuCreated()
        verify(view).checkSortBy(sortBy)
    }

    @Test
    fun testSortBy() {
        val sortBy = EntityUtils.SORT_BY.NEXT_EPISODE
        val showList: List<TvShow> = listOf()
        `when`(interactor.sortBy).thenReturn(sortBy)
        `when`(interactor.sortShowList(sortBy)).thenReturn(showList)

        presenter.sortBy(sortBy)

        verify(view).setShows(showList)
        verify(view).checkSortBy(sortBy)
    }

    @Test
    fun testOnClick() {
        val viewHolder = mock(MainItemViewHolder::class.java)
        `when`(viewHolder.data).thenReturn(createShow().convertToTvShowEntity())
        `when`(viewHolder.transitedView).thenReturn(mock(View::class.java))

        presenter.onItemClick(viewHolder)

        verify(view).showDetails(viewHolder.data as TvShow, viewHolder.transitedView)
    }

}