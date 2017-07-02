package omg.jd.tvmazeapiclient.utils

import android.support.annotation.IntegerRes
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(url: String?, @IntegerRes placeholderId: Int = 0) {
    if (placeholderId != 0) {
        Picasso.with(context).load(url).error(placeholderId).placeholder(placeholderId).into(this)
    } else {
        Picasso.with(context).load(url).into(this)
    }
}