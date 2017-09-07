package omg.jd.tvmazeapiclient.job.update

import android.app.job.JobParameters
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import omg.jd.tvmazeapiclient.RxAndroidSchedulersOverrideRule
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
class UpdatePresenterTest {
    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule = RxAndroidSchedulersOverrideRule()

    @Mock
    lateinit var view: MVPUpdate.View
    @Mock
    lateinit var interactor: MVPUpdate.Interactor

    @InjectMocks
    lateinit var presenter: UpdatePresenter

    @Before
    fun setUp() {
        presenter = UpdatePresenter(view,interactor)
    }

    @Test
    fun testOnStartJob() {
        val jobParameters: JobParameters? = null
        val showIds = listOf(1L,2L,3L)
        `when`(interactor.loadShowIdList()).thenReturn(Observable.just(showIds))

        presenter.onStartJob(jobParameters)

        showIds.forEach { verify(interactor).updateShow(it) }
        verify(view).finishJob(jobParameters)
    }

    @Test
    fun testOnStopJob() {
        val jobParameters: JobParameters? = null

        presenter.onStopJob(jobParameters)

        // stub, not used?
    }
}