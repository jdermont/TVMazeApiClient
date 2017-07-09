package omg.jd.tvmazeapiclient.utils

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

object DateTimeUtils {

    val INVALID_DATETIME = DateTime(0, DateTimeZone.UTC)

    private val episodeDateTimeFormatter: DateTimeFormatter by lazy {
        DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ")
    }

    private val printDateTimeFormatter: DateTimeFormatter by lazy {
        DateTimeFormat.forPattern("yyyy-MM-dd")
    }

    fun getEpisodeDateTime(airstamp: String?): DateTime {
        if (airstamp == null) {
            return INVALID_DATETIME
        }
        return episodeDateTimeFormatter.parseDateTime(airstamp)
    }

    fun getDateString(dateTime: DateTime): String {
        return printDateTimeFormatter.print(dateTime)
    }
}