package robolectric.omg.jd.tvmazeapiclient.db

import com.raizlabs.android.dbflow.kotlinextensions.BuildConfig
import omg.jd.tvmazeapiclient.db.MainDatabase
import omg.jd.tvmazeapiclient.utils.createShow
import omg.jd.tvmazeapiclient.ws.model.convertToTvShowEntity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import robolectric.omg.jd.tvmazeapiclient.utils.DbFlowRule

@Config(constants = BuildConfig::class, sdk = intArrayOf(25))
@RunWith(RobolectricTestRunner::class)
class MainDatabaseTest {
    @get:Rule
    val dbflow = DbFlowRule.create()

    @Before
    fun setUp() {

    }

    @Test
    fun saveTvShowTest() {
        val tvShow = createShow().convertToTvShowEntity()
        MainDatabase.saveTvShow(tvShow).subscribe()
        Assert.assertEquals(true,MainDatabase.dbChanged)

        val showList = MainDatabase.loadShowList().blockingFirst()
        Assert.assertEquals(1,showList.size)
        Assert.assertEquals(tvShow,showList[0])
    }

    @Test
    fun deleteTvShowTest() {
        val tvShow1 = createShow().convertToTvShowEntity()
        val tvShow2 = tvShow1.copy(id = 666)
        MainDatabase.saveTvShow(tvShow1).subscribe()
        MainDatabase.saveTvShow(tvShow2).subscribe()

        MainDatabase.deleteTvShow(tvShow2).subscribe()
        val showList = MainDatabase.loadShowList().blockingFirst()
        Assert.assertEquals(1,showList.size)
        Assert.assertFalse(showList.contains(tvShow2))
    }


}
