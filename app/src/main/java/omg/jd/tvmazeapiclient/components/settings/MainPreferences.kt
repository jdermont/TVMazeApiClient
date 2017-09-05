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

fun SharedPreferences.areNotificationsEnabled(): Boolean = getBoolean("KEY_NOTIFICATION",true)

fun SharedPreferences.isRefreshEnabled(): Boolean = getBoolean("KEY_REFRESH_IN_BACKGROUND", true)

fun SharedPreferences.isRefreshWithWifiOnly(): Boolean = getBoolean("KEY_REFRESH_WIFI_ONLY", true)

fun SharedPreferences.setNotificationSound(sound: String?) {
    edit().putString("KEY_NOTIFICATION_SOUND", sound).apply()
}

fun SharedPreferences.getNotificationSound(): String? = getString("KEY_NOTIFICATION_SOUND",null)
