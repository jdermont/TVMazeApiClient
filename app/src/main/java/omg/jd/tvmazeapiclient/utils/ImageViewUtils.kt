package omg.jd.tvmazeapiclient.utils

import android.support.annotation.IntegerRes
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadUrl(url: String?, @IntegerRes placeholderId: Int = 0) {
    if (placeholderId != 0) {
        Glide.with(context).load(url).placeholder(placeholderId).into(this)
    } else {
        Glide.with(context).load(url).into(this)
    }
}