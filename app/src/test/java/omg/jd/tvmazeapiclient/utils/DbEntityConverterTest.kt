package omg.jd.tvmazeapiclient.utils

import omg.jd.tvmazeapiclient.db.model.DbFlowEpisode
import omg.jd.tvmazeapiclient.db.model.DbFlowLinks
import omg.jd.tvmazeapiclient.db.model.DbFlowNetwork
import omg.jd.tvmazeapiclient.entity.*
import omg.jd.tvmazeapiclient.db.model.convertToTvShowDbFlow
import omg.jd.tvmazeapiclient.ws.model.convertToTvShowEntity
import org.junit.Assert
import org.junit.Test

class DbEntityConverterTest {

    @Test
    fun convertTVShowToDbFlowTvShowTest() {
        val tvShow = createShowWithEpisodes(0, "some show").convertToTvShowEntity()
        val dbTvShow = tvShow.convertToTvShowDbFlow()

        Assert.assertEquals(dbTvShow.id, tvShow.id)
        Assert.assertEquals(dbTvShow.url, tvShow.url)
        Assert.assertEquals(dbTvShow.name, tvShow.name)
        Assert.assertEquals(dbTvShow.type, tvShow.type)
        Assert.assertEquals(dbTvShow.language, tvShow.language)
        Assert.assertEquals(dbTvShow.genres, tvShow.genres)
        Assert.assertEquals(dbTvShow.status, tvShow.status)
        Assert.assertEquals(dbTvShow.runtime, tvShow.runtime)
        Assert.assertEquals(dbTvShow.premiered, tvShow.premiered)
        Assert.assertEquals(dbTvShow.scheduleTime, tvShow.scheduleTime)
        Assert.assertEquals(dbTvShow.scheduleDays, tvShow.scheduleDays)
        Assert.assertEquals(dbTvShow.rating, tvShow.rating, 0.001)
        Assert.assertEquals(dbTvShow.weight, tvShow.weight)
        assertNetworkEquals(dbTvShow.network!!,tvShow.network!!)
        Assert.assertEquals(dbTvShow.runtime, tvShow.runtime)
        Assert.assertEquals(dbTvShow.mediumImage, tvShow.mediumImage)
        Assert.assertEquals(dbTvShow.originalImage, tvShow.originalImage)
        Assert.assertEquals(dbTvShow.summary, tvShow.summary)
        Assert.assertEquals(dbTvShow.updated, tvShow.updated)
        assertLinksEquals(dbTvShow.links!!, tvShow.links!!)
        for (i in 0..dbTvShow.episodes!!.size-1) {
            assertEpisodeEquals(dbTvShow.episodes!![i],tvShow.episodes[i])
        }
    }

    @Test
    fun convertDbFlowTvShowToTvShowTest() {
        val dbTvShow = createShowWithEpisodes(0, "some show").convertToTvShowEntity().convertToTvShowDbFlow()
        val tvShow = dbTvShow.convertToTvShowEntity()

        Assert.assertEquals(dbTvShow.id, tvShow.id)
        Assert.assertEquals(dbTvShow.url, tvShow.url)
        Assert.assertEquals(dbTvShow.name, tvShow.name)
        Assert.assertEquals(dbTvShow.type, tvShow.type)
        Assert.assertEquals(dbTvShow.language, tvShow.language)
        Assert.assertEquals(dbTvShow.genres, tvShow.genres)
        Assert.assertEquals(dbTvShow.status, tvShow.status)
        Assert.assertEquals(dbTvShow.runtime, tvShow.runtime)
        Assert.assertEquals(dbTvShow.premiered, tvShow.premiered)
        Assert.assertEquals(dbTvShow.scheduleTime, tvShow.scheduleTime)
        Assert.assertEquals(dbTvShow.scheduleDays, tvShow.scheduleDays)
        Assert.assertEquals(dbTvShow.rating, tvShow.rating, 0.001)
        Assert.assertEquals(dbTvShow.weight, tvShow.weight)
        assertNetworkEquals(dbTvShow.network!!,tvShow.network!!)
        Assert.assertEquals(dbTvShow.runtime, tvShow.runtime)
        Assert.assertEquals(dbTvShow.mediumImage, tvShow.mediumImage)
        Assert.assertEquals(dbTvShow.originalImage, tvShow.originalImage)
        Assert.assertEquals(dbTvShow.summary, tvShow.summary)
        Assert.assertEquals(dbTvShow.updated, tvShow.updated)
        assertLinksEquals(dbTvShow.links!!, tvShow.links!!)
        for (i in 0..dbTvShow.episodes!!.size-1) {
            assertEpisodeEquals(dbTvShow.episodes!![i],tvShow.episodes[i])
        }
    }

    private fun assertNetworkEquals(dbNetwork: DbFlowNetwork, network: Network) {
        Assert.assertEquals(dbNetwork.id, network.id)
        Assert.assertEquals(dbNetwork.name, network.name)
        Assert.assertEquals(dbNetwork.countryCode, network.countryCode)
        Assert.assertEquals(dbNetwork.countryName, network.countryName)
        Assert.assertEquals(dbNetwork.countryTimezone, network.countryTimezone)
    }

    private fun assertLinksEquals(dbLinks: DbFlowLinks, links: Links) {
        Assert.assertEquals(dbLinks.self, links.self)
        Assert.assertEquals(dbLinks.previousepisode, links.previousepisode)
        Assert.assertEquals(dbLinks.nextepisode, links.nextepisode)
    }

    private fun assertEpisodeEquals(dbEpisode: DbFlowEpisode, episode: Episode) {
        Assert.assertEquals(dbEpisode.id, episode.id)
        Assert.assertEquals(dbEpisode.url, episode.url)
        Assert.assertEquals(dbEpisode.name, episode.name)
        Assert.assertEquals(dbEpisode.season, episode.season)
        Assert.assertEquals(dbEpisode.number, episode.number)
        Assert.assertEquals(dbEpisode.airdate, episode.airdate)
        Assert.assertEquals(dbEpisode.airtime, episode.airtime)
        Assert.assertEquals(dbEpisode.airstamp, episode.airstamp)
        Assert.assertEquals(dbEpisode.runtime, episode.runtime)
        Assert.assertEquals(dbEpisode.mediumImage, episode.mediumImage)
        Assert.assertEquals(dbEpisode.originalImage, episode.originalImage)
        Assert.assertEquals(dbEpisode.summary, episode.summary)
        assertLinksEquals(dbEpisode.links!!,episode.links!!)
    }
}