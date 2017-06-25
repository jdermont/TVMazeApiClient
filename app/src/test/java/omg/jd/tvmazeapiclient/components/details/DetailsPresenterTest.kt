package omg.jd.tvmazeapiclient.components.details

import com.nhaarman.mockito_kotlin.verify
import omg.jd.tvmazeapiclient.RxAndroidSchedulersOverrideRule
import omg.jd.tvmazeapiclient.db.model.TvShow
import omg.jd.tvmazeapiclient.utils.convertToTvShow
import omg.jd.tvmazeapiclient.utils.createShow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailsPresenterTest {
    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule = RxAndroidSchedulersOverrideRule()

    @Mock
    lateinit var view: MVPDetails.View
    @Mock
    lateinit var interactor: MVPDetails.Interactor

    @InjectMocks
    lateinit var presenter: DetailsPresenter

    @Before
    fun setUp() {
        presenter = DetailsPresenter(interactor)
        presenter.onViewAttached(view)
    }

    @Test
    fun testOnInit() {
        val tvShow = createShow().convertToTvShow()
        presenter.onInit(tvShow)

        verify(interactor).tvShow = tvShow
        verify(view).loadImageHeader(tvShow.originalImage)
    }
}