package omg.jd.tvmazeapiclient.components.search.recyclerview

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.search_item.view.*
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.recyclerview.TvShowViewHolder
import omg.jd.tvmazeapiclient.utils.loadUrl

class SearchItemViewHolder(itemView: View, clickListener: ViewHolderOnClickListener?) : TvShowViewHolder(itemView, clickListener) {
    init {
        if (clickListener != null) {
            itemView.setOnClickListener {
                clickListener.onItemClick(this)
            }
        }
    }

    override val transitedView: View = itemView.searchItemImage

    val resources: Resources = itemView.resources

    override fun updateView(tvShow: TvShow) {
        super.updateView(tvShow)
        itemView.searchItemImage.loadUrl(tvShow.originalImage, R.drawable.placeholder)
        itemView.searchItemTitle.text = tvShow.name
        itemView.searchItemDescription.text = resources.getString(
                R.string.searchDescriptionText,
                tvShow.premiered ?: "-",
                tvShow.type ?: "-",
                tvShow.status ?: "-",
                tvShow.rating
        )
        itemView.searchItemGenres.text = resources.getString(
                R.string.searchGenresText,
                tvShow.genres.joinToString(separator = ", ")
        )
    }

}
