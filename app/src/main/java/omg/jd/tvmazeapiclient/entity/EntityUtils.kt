package omg.jd.tvmazeapiclient.entity

import org.joda.time.DateTime

object EntityUtils {
    enum class SORT_BY(val id: Int) {
        DEFAULT(0), PREMIERED(1), NEXT_EPISODE(2);

        companion object {
            fun fromId(id: Int): SORT_BY {
                when (id) {
                    0 -> return DEFAULT
                    1 -> return PREMIERED
                    2 -> return NEXT_EPISODE
                    else -> throw IllegalArgumentException("Invalid SORT_BY int")
                }
            }
        }
    }

    fun sorted(tvShowList: List<TvShow>, sortBy: SORT_BY): List<TvShow> {
        when (sortBy) {
            SORT_BY.DEFAULT -> return tvShowList.sorted()
            SORT_BY.PREMIERED -> return tvShowList.sortedBy { it.premiered }
            SORT_BY.NEXT_EPISODE -> {
                val now = DateTime.now()
                return tvShowList.sortedWith(Comparator comp@ {
                    o1, o2 ->
                    val e1 = o1.episodes.firstOrNull { it.datetime > now }
                    val e2 = o2.episodes.firstOrNull { it.datetime > now }
                    e1 ?: (e2 ?: return@comp 0)
                    e1 ?: return@comp 1
                    e2 ?: return@comp -1
                    e1.compareTo(e2)
                })
            }
        }
    }
}
