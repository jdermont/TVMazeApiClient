package robolectric.omg.jd.tvmazeapiclient.db.dbflow

import com.raizlabs.android.dbflow.kotlinextensions.BuildConfig
import com.raizlabs.android.dbflow.sql.language.SQLite
import omg.jd.tvmazeapiclient.db.model.*
import omg.jd.tvmazeapiclient.utils.createEpisodeList
import omg.jd.tvmazeapiclient.utils.createShow
import omg.jd.tvmazeapiclient.utils.createShowList
import omg.jd.tvmazeapiclient.utils.createShowListWithEpisodes
import omg.jd.tvmazeapiclient.ws.model.convertToEpisodeEntity
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
class BasicDbFlowCRUDTest {
    @get:Rule
    val dbflow = DbFlowRule.create()

    @Before
    fun insertTvShowsInto() {
    }

    @Test
    fun queryTvShowsWithoutEpisodes() {
        val N = 5
        val showList = createShowList(N).map { it.show.convertToTvShowEntity().convertToTvShowDbFlow() }
        showList.forEach { it.save(); }

        val dbShowList = SQLite.select().from(DbFlowTvShow::class.java)
                .where()
                .queryList()

        Assert.assertEquals(N,dbShowList.size)
        for ((index, value) in dbShowList.withIndex()) {
            Assert.assertEquals(showList[index],value)
        }
    }

    @Test
    fun queryTvShowsWithEpisodes() {
        val N = 5
        val showList = createShowListWithEpisodes(N).map { it.show.convertToTvShowEntity().convertToTvShowDbFlow() }
        showList.forEach { it.save() }

        val dbShowList = SQLite.select().from(DbFlowTvShow::class.java)
                .queryList()

        Assert.assertEquals(N,dbShowList.size)
        for ((index, value) in dbShowList.withIndex()) {
            Assert.assertEquals(showList[index].episodes,value.episodes)
        }
    }

    @Test
    fun deleteTvShowTest() {
        val N = 5
        val showList = createShowListWithEpisodes(N).map { it.show.convertToTvShowEntity().convertToTvShowDbFlow() }
        showList.forEach { it.save() }

        val dbShowList = SQLite.select().from(DbFlowTvShow::class.java)
                .queryList()
        Assert.assertEquals(N,dbShowList.size)

        val lastTvShow = dbShowList.last()
        lastTvShow.delete()

        val dbShowListOneDeleted = SQLite.select().from(DbFlowTvShow::class.java)
                .queryList()
        Assert.assertEquals(N-1,dbShowListOneDeleted.size)
        Assert.assertEquals(null,dbShowListOneDeleted.find { it.id == lastTvShow.id })

        // check if associated object are delete succesfully
        val networks = SQLite.select().from(DbFlowNetwork::class.java)
                .queryList()
        Assert.assertEquals(N-1,networks.size)

        val episodes = SQLite.select().from(DbFlowEpisode::class.java)
                .queryList()

        Assert.assertEquals((N-1)*2,episodes.size) // each tv show contains 2 episodes
    }

    @Test
    fun updateTvShowTest() {
        val show = createShow().convertToTvShowEntity().convertToTvShowDbFlow()
        show.save()
        val dbShow: DbFlowTvShow = SQLite.select().from(DbFlowTvShow::class.java)
                .querySingle()!!
        Assert.assertEquals(show,dbShow)

        val episodes = createEpisodeList().map { it.convertToEpisodeEntity().convertToEpisodeDbFlow() }
        dbShow.episodes = episodes
        dbShow.save()

        val dbShowWithEpisodes: DbFlowTvShow = SQLite.select().from(DbFlowTvShow::class.java)
                .querySingle()!!
        Assert.assertEquals(episodes,dbShowWithEpisodes.episodes)
    }
}