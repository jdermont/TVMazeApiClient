package omg.jd.tvmazeapiclient.components.main.recyclerview

import android.content.res.Resources
import android.view.View
import kotlinx.android.synthetic.main.main_item.view.*
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.recyclerview.TvShowViewHolder
import omg.jd.tvmazeapiclient.utils.DateTimeUtils
import omg.jd.tvmazeapiclient.utils.StringUtils
import omg.jd.tvmazeapiclient.utils.loadUrl
import org.joda.time.DateTime
import org.joda.time.Days

class MainItemViewHolder(itemView: View, clickListener: ViewHolderOnClickListener?) : TvShowViewHolder(itemView, clickListener) {
    init {
        if (clickListener != null) {
            itemView.setOnClickListener {
                clickListener.onItemClick(this)
            }
        }
    }

    override val transitedView: View = itemView.mainItemImage
    val resources: Resources = itemView.resources

    override fun updateView(tvShow: TvShow) {
        super.updateView(tvShow)
        itemView.mainItemImage.loadUrl(tvShow.originalImage, R.drawable.placeholder)
        itemView.mainItemTitle.text = tvShow.name
        val now = DateTime.now()
        val latestEpisode = tvShow.episodes.lastOrNull { it.datetime != DateTimeUtils.INVALID_DATETIME && it.datetime <= now }
        val nextEpisode = tvShow.episodes.firstOrNull { it.datetime > now }
        val str = if (nextEpisode == null) "" else " (${DateTimeUtils.getDateString(nextEpisode.datetime)})"
        itemView.mainItemDescription.text = resources.getString(
                R.string.mainDescriptionText,
                tvShow.status ?: "-",
                tvShow.rating,
                StringUtils.makeEpisodeNumber(latestEpisode),
                StringUtils.makeEpisodeNumber(nextEpisode)+str
        )
        if (nextEpisode != null) {
            val days = Days.daysBetween(now, nextEpisode.datetime).days
            itemView.mainItemNextEpisodeInDays.text = resources.getQuantityString(R.plurals.nextEpisodeInDays, days, days)
        } else {
            itemView.mainItemNextEpisodeInDays.text = ""
        }
    }
}