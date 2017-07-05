package omg.jd.tvmazeapiclient.utils

import omg.jd.tvmazeapiclient.entity.Episode
import omg.jd.tvmazeapiclient.entity.Links
import omg.jd.tvmazeapiclient.entity.Network
import omg.jd.tvmazeapiclient.ws.convertToTvShowEntity
import omg.jd.tvmazeapiclient.ws.model.WsEpisode
import omg.jd.tvmazeapiclient.ws.model.LinksDbFlow
import omg.jd.tvmazeapiclient.ws.model.WsNetwork
import org.junit.Assert
import org.junit.Test

class WsEntityConverterTest {

    @Test
    fun convertWsTVShowToTvShowTest() {
        val wsTVShow = createShowWithEpisodes(0, "some show")
        val tvShow = wsTVShow.convertToTvShowEntity()

        Assert.assertEquals(wsTVShow.id, tvShow.id)
        Assert.assertEquals(wsTVShow.url, tvShow.url)
        Assert.assertEquals(wsTVShow.name, tvShow.name)
        Assert.assertEquals(wsTVShow.type, tvShow.type)
        Assert.assertEquals(wsTVShow.language, tvShow.language)
        Assert.assertEquals(wsTVShow.genres, tvShow.genres)
        Assert.assertEquals(wsTVShow.status, tvShow.status)
        Assert.assertEquals(wsTVShow.runtime, tvShow.runtime)
        Assert.assertEquals(wsTVShow.premiered, tvShow.premiered)
        Assert.assertEquals(wsTVShow.schedule?.time, tvShow.scheduleTime)
        Assert.assertEquals(wsTVShow.schedule?.days, tvShow.scheduleDays)
        Assert.assertEquals(wsTVShow.rating?.average, tvShow.rating)
        Assert.assertEquals(wsTVShow.weight, tvShow.weight)
        assertNetworkEquals(wsTVShow.network!!,tvShow.network!!)
        Assert.assertEquals(wsTVShow.runtime, tvShow.runtime)
        Assert.assertEquals(wsTVShow.image?.medium, tvShow.mediumImage)
        Assert.assertEquals(wsTVShow.image?.original, tvShow.originalImage)
        Assert.assertEquals(wsTVShow.summary, tvShow.summary)
        Assert.assertEquals(wsTVShow.updated, tvShow.updated)
        assertLinksEquals(wsTVShow.links!!, tvShow.links!!)
        for (i in 0..wsTVShow.embedded?.episodes!!.size-1) {
            assertEpisodeEquals(wsTVShow.embedded?.episodes!![i],tvShow.episodes!![i])
        }
    }

    private fun assertNetworkEquals(wsNetwork: WsNetwork, network: Network) {
        Assert.assertEquals(wsNetwork.id, network.id)
        Assert.assertEquals(wsNetwork.name, network.name)
        Assert.assertEquals(wsNetwork.country?.code, network.countryCode)
        Assert.assertEquals(wsNetwork.country?.name, network.countryName)
        Assert.assertEquals(wsNetwork.country?.timezone, network.countryTimezone)
    }

    private fun assertLinksEquals(wsLinks: LinksDbFlow, links: Links) {
        Assert.assertEquals(wsLinks.self, links.self)
        Assert.assertEquals(wsLinks.previousepisode, links.previousepisode)
        Assert.assertEquals(wsLinks.nextepisode, links.nextepisode)
    }

    private fun assertEpisodeEquals(wsEpisode: WsEpisode, episode: Episode) {
        Assert.assertEquals(wsEpisode.id, episode.id)
        Assert.assertEquals(wsEpisode.url, episode.url)
        Assert.assertEquals(wsEpisode.name, episode.name)
        Assert.assertEquals(wsEpisode.season, episode.season)
        Assert.assertEquals(wsEpisode.number, episode.number)
        Assert.assertEquals(wsEpisode.airdate, episode.airdate)
        Assert.assertEquals(wsEpisode.airtime, episode.airtime)
        Assert.assertEquals(wsEpisode.airstamp, episode.airstamp)
        Assert.assertEquals(wsEpisode.runtime, episode.runtime)
        Assert.assertEquals(wsEpisode.image?.medium, episode.mediumImage)
        Assert.assertEquals(wsEpisode.image?.original, episode.originalImage)
        Assert.assertEquals(wsEpisode.summary, episode.summary)
        assertLinksEquals(wsEpisode.links!!,episode.links!!)
    }
}