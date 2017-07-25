package omg.jd.tvmazeapiclient.components.details

import com.nhaarman.mockito_kotlin.verify
import com.raizlabs.android.dbflow.kotlinextensions.delete
import io.reactivex.Observable
import omg.jd.tvmazeapiclient.RxAndroidSchedulersOverrideRule
import omg.jd.tvmazeapiclient.db.MainDatabase
import omg.jd.tvmazeapiclient.ws.model.convertToEpisodeEntity
import omg.jd.tvmazeapiclient.ws.model.convertToTvShowEntity
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
        val tvShow = createShow().convertToTvShowEntity()
        `when`(interactor.tvShow).thenReturn(tvShow)
        val tvShowWithEpisodes = tvShow.copy(
                episodes = listOf(
                        createEpisode(id = 0, name = "Pilot 1").convertToEpisodeEntity(),
                        createEpisode(id = 1, name = "Pilot 2").convertToEpisodeEntity()
                )
        )
        val episodesObservable = Observable.just(tvShowWithEpisodes)
        `when`(interactor.retrieveEpisodes()).thenReturn(episodesObservable)
        val existsInDbObservable = Observable.just(MainDatabase.TvShowInDB.NOT_IN_DB)
        `when`(interactor.checkForTvShowInDB()).thenReturn(existsInDbObservable)
        presenter.onInit(tvShow)

        verify(interactor).setTvShowIfNeeded(tvShow)
        verify(view).loadImageHeader(tvShow.originalImage)
        verify(view).setupViews(tvShow)
    }

    @Test
    fun testOnFabClickedSave() {
        val inDB = MainDatabase.TvShowInDB.IN_DB
        val observable = Observable.just(inDB)
        `when`(interactor.saveTvShow()).thenReturn(observable)

        presenter.onFabClicked(MainDatabase.TvShowInDB.NOT_IN_DB)
        verify(view).enableFloatingActionProgress()
        verify(interactor).saveTvShow()
        verify(view).setFloatingActionButton(inDB)
        verify(view).disableFloatingActionProgress()
    }

    @Test
    fun testOnFabClickedDelete() {
        val notInDB = MainDatabase.TvShowInDB.NOT_IN_DB
        val observable = Observable.just(notInDB)
        `when`(interactor.deleteTvShow()).thenReturn(observable)

        presenter.onFabClicked(MainDatabase.TvShowInDB.IN_DB)
        verify(view).disableFloatingActionProgress()
        verify(interactor).deleteTvShow()
        verify(view).setFloatingActionButton(notInDB)
        verify(view).enableFloatingActionProgress()
    }
}