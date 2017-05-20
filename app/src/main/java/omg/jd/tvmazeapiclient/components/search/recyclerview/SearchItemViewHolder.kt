package omg.jd.tvmazeapiclient.components.search.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.search_item.view.*
import omg.jd.tvmazeapiclient.db.model.TvShow
import omg.jd.tvmazeapiclient.utils.loadUrl

class SearchItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    init {

    }

    fun updateView(tvShow: TvShow) {
        itemView.searchItemImage.loadUrl(tvShow.originalImage)
        itemView.searchItemTitle.text = tvShow.name
    }
}