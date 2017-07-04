package omg.jd.tvmazeapiclient.db.dbflow.utils

import com.google.gson.Gson
import com.raizlabs.android.dbflow.converter.TypeConverter

@com.raizlabs.android.dbflow.annotation.TypeConverter
class StringListConverter : TypeConverter<String, StringList>() {
    override fun getModelValue(data: String?): StringList {
        return Gson().fromJson(data, StringList::class.java)
    }

    override fun getDBValue(model: StringList?): String {
        return Gson().toJson(model)
    }

}
