package omg.jd.tvmazeapiclient.utils

import android.text.Html
import android.text.Spanned
import android.text.SpannedString

object StringUtils {
    @Suppress("deprecation")
    fun fromHtmlCompat(html: String?): Spanned {
        if (html == null) {
            return SpannedString("")
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            return Html.fromHtml(html)
        }
    }

    fun startPadZero(number: Int): String {
        return number.toString().padStart(2,'0')
    }
}