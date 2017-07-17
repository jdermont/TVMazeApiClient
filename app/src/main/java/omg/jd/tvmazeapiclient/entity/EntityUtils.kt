package omg.jd.tvmazeapiclient.entity

import org.joda.time.DateTime

object EntityUtils {
    enum class SORT_BY {
        DEFAULT, PREMIERED, NEXT_EPISODE
    }

    fun sorted(tvShowList: List<TvShow>, sortBy: SORT_BY): List<TvShow> {
        when (sortBy) {
            SORT_BY.DEFAULT -> return tvShowList.sortedWith(Comparator {
                o1, o2 -> o1.compareTo(o2)
            })
            SORT_BY.PREMIERED -> return tvShowList.sortedWith(Comparator comp@ {
                o1, o2 ->
                    o1.premiered ?: (o2.premiered ?: return@comp 0)
                    o1.premiered ?: return@comp 1
                    o2.premiered ?: return@comp -1
                    o1.premiered.compareTo(o2.premiered)
            })
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
