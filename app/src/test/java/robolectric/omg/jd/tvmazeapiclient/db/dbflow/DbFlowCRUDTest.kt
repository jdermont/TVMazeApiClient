package robolectric.omg.jd.tvmazeapiclient.db.dbflow

import com.raizlabs.android.dbflow.kotlinextensions.BuildConfig
import com.raizlabs.android.dbflow.sql.language.SQLite
import omg.jd.tvmazeapiclient.db.model.DbFlowTvShow
import omg.jd.tvmazeapiclient.utils.*
import omg.jd.tvmazeapiclient.ws.convertToTvShowDbFlow
import omg.jd.tvmazeapiclient.ws.convertToTvShowEntity
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
class DbFlowCRUDTest {
    @get:Rule
    val dbflow = DbFlowRule.create()

    @Before
    fun insertTvShowsInto() {
    }

    @Test
    fun queryTvShowsWithoutEpisodes() {
        val showList = createShowList(5).map { it.show.convertToTvShowEntity().convertToTvShowDbFlow() }
        showList.forEach { it.save(); }

        val dbShowList = SQLite.select().from(DbFlowTvShow::class.java)
                .where()
                .queryList()

        for ((index, value) in dbShowList.withIndex()) {
            Assert.assertEquals(showList[index],value)
        }
    }

    @Test
    fun queryTvShowsWithEpisodes() {
        val showList = createShowListWithEpisodes(5).map { it.show.convertToTvShowEntity().convertToTvShowDbFlow() }
        showList.forEach { it.save() }

        val dbShowList = SQLite.select().from(DbFlowTvShow::class.java)
                .queryList()

        for ((index, value) in dbShowList.withIndex()) {
            Assert.assertEquals(showList[index].episodes,value.episodes)
        }
    }
}