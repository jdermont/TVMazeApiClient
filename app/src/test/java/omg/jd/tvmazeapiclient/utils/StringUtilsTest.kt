package omg.jd.tvmazeapiclient.utils

import omg.jd.tvmazeapiclient.ws.model.convertToEpisodeEntity
import org.junit.Assert
import org.junit.Test

class StringUtilsTest {

    @Test
    fun startPadZeroTest() {
        Assert.assertEquals("01",StringUtils.startPadZero(1))
        Assert.assertEquals("10",StringUtils.startPadZero(10))
        Assert.assertEquals("100",StringUtils.startPadZero(100))
    }

    @Test
    fun makeEpisodeNumberTest() {
        val episode = createEpisode().copy(season = 5 ,number = 10).convertToEpisodeEntity()
        val episode2 = createEpisode().copy(season = 1 ,number = 210).convertToEpisodeEntity()

        Assert.assertEquals("-", StringUtils.makeEpisodeNumber(null))
        Assert.assertEquals("05x10", StringUtils.makeEpisodeNumber(episode))
        Assert.assertEquals("01x210", StringUtils.makeEpisodeNumber(episode2))
    }
}