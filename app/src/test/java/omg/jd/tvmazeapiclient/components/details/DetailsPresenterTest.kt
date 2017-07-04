package omg.jd.tvmazeapiclient.components.details

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import omg.jd.tvmazeapiclient.RxAndroidSchedulersOverrideRule
import omg.jd.tvmazeapiclient.utils.convertToEpisode
import omg.jd.tvmazeapiclient.utils.convertToTvShow
import omg.jd.tvmazeapiclient.utils.createEpisode
import omg.jd.tvmazeapiclient.utils.createShow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
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
        `when`(interactor.tvShow).thenReturn(tvShow)
        val tvShowWithEpisodes = tvShow.copy(
                _episodes = listOf(
                        createEpisode(id = 0, name = "Pilot 1").convertToEpisode(),
                        createEpisode(id = 0, name = "Pilot 2").convertToEpisode()
                )
        )
        val observable = Observable.just(tvShowWithEpisodes)
        `when`(interactor.retrieveEpisodes()).thenReturn(observable)
        presenter.onInit(tvShow)

        verify(interactor).setTvShowIfNeeded(tvShow)
        verify(view).loadImageHeader(tvShow.originalImage)
        verify(view).setupViews(tvShow)
    }
}