package omg.jd.tvmazeapiclient.utils

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.Assert
import org.junit.Test

class DateTimeUtilsTest {

    @Test
    fun getEpisodeDateTimeTestGood() {
        val airstamp = "2007-09-24T20:30:00-04:00"
        val dateTime = DateTimeUtils.getEpisodeDateTime(airstamp).toDateTime(DateTimeZone.UTC)
        val dateTimeUTC = DateTime(2007,9,25,0,30,0,0,DateTimeZone.UTC)

        Assert.assertEquals(dateTimeUTC.dayOfMonth().get(),dateTime.dayOfMonth().get())
        Assert.assertEquals(dateTimeUTC.monthOfYear().get(),dateTime.monthOfYear().get())
        Assert.assertEquals(dateTimeUTC.year().get(),dateTime.year().get())
        Assert.assertEquals(dateTimeUTC.hourOfDay().get(),dateTime.hourOfDay().get())
        Assert.assertEquals(dateTimeUTC.minuteOfHour().get(),dateTime.minuteOfHour().get())
    }

    @Test(expected = IllegalArgumentException::class)
    fun getEpisodeDateTimeTestBad() {
        val airstamp = "2007-09-24T20:30:00 lol"
        val dateTime = DateTimeUtils.getEpisodeDateTime(airstamp)
    }

    @Test
    fun getEpisodeDateTimeTestNull() {
        val dateTime = DateTimeUtils.getEpisodeDateTime(null)
        Assert.assertEquals(DateTimeUtils.INVALID_DATETIME,dateTime)
    }

    @Test
    fun getDateStringTest() {
        val dateTime = DateTime(2007,9,25,0,30,0,0,DateTimeZone.UTC)
        Assert.assertEquals("2007-09-25",DateTimeUtils.getDateString(dateTime))
    }
}