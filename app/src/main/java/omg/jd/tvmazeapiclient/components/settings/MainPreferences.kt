package omg.jd.tvmazeapiclient.components.settings

import android.content.SharedPreferences
import omg.jd.tvmazeapiclient.entity.EntityUtils

fun SharedPreferences.putSortBy(sortBy: EntityUtils.SORT_BY) {
    edit().putInt("KEY_SORT_BY", sortBy.id).apply()
}

fun SharedPreferences.getSortBy(): EntityUtils.SORT_BY {
    val id = getInt("KEY_SORT_BY",0)
    return EntityUtils.SORT_BY.fromId(id)
}