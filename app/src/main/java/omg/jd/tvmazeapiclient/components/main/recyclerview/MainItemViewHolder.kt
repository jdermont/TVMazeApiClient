package omg.jd.tvmazeapiclient.components.main.recyclerview

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.main_item.view.*
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.entity.Episode
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.utils.DateTimeUtils
import omg.jd.tvmazeapiclient.utils.StringUtils
import omg.jd.tvmazeapiclient.utils.loadUrl
import org.joda.time.DateTime

class MainItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val resources: Resources = itemView.resources

    fun updateView(tvShow: TvShow) {
        itemView.mainItemImage.loadUrl(tvShow.originalImage, R.drawable.placeholder)
        itemView.mainItemTitle.text = tvShow.name
        val now = DateTime.now()
        val latestEpisode = tvShow.episodes.lastOrNull { it.datetime <= now }
        val nextEpisode = tvShow.episodes.firstOrNull { it.datetime > now }
        val str = if (nextEpisode == null) "" else " (${DateTimeUtils.getDateString(nextEpisode.datetime)})"
        itemView.mainItemDescription.text = resources.getString(
                R.string.mainDescriptionText,
                tvShow.status ?: "-",
                tvShow.rating,
                makeEpisodeNumber(latestEpisode),
                makeEpisodeNumber(nextEpisode)+str
        )
    }

    private fun makeEpisodeNumber(episode: Episode?): String {
        if (episode == null) {
            return "-"
        } else {
            return "${StringUtils.startPadZero(episode.season)}x${StringUtils.startPadZero(episode.number)}"
        }
    }
}