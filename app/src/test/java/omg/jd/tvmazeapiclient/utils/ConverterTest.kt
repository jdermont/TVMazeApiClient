package omg.jd.tvmazeapiclient.utils

import omg.jd.tvmazeapiclient.db.model.Links
import omg.jd.tvmazeapiclient.db.model.Network
import omg.jd.tvmazeapiclient.ws.model.WsLinks
import omg.jd.tvmazeapiclient.ws.model.WsNetwork
import org.junit.Assert
import org.junit.Test

class ConverterTest {

    @Test
    fun convertWsTVShowToTvShowTest() {
        val wsTVShow = createShow(0, "some show")
        val tvShow = wsTVShow.convertToTvShow()

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
    }

    private fun assertNetworkEquals(wsNetwork: WsNetwork, network: Network) {
        Assert.assertEquals(wsNetwork.id, network.id)
        Assert.assertEquals(wsNetwork.name, network.name)
        Assert.assertEquals(wsNetwork.country?.code, network.countryCode)
        Assert.assertEquals(wsNetwork.country?.name, network.countryName)
        Assert.assertEquals(wsNetwork.country?.timezone, network.countryTimezone)
        Assert.assertEquals(wsNetwork.country?.code, network.countryCode)
    }

    private fun assertLinksEquals(wsLinks: WsLinks, links: Links) {
        Assert.assertEquals(wsLinks.self, links.self)
        Assert.assertEquals(wsLinks.previousepisode, links.previousepisode)
        Assert.assertEquals(wsLinks.nextepisode, links.nextepisode)
    }
}