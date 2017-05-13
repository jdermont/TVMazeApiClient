package omg.jd.tvmazeapiclient.components.search.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.search_item.view.*
import omg.jd.tvmazeapiclient.ws.model.TVShow

class SearchItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    init {

    }

    fun updateView(tvShow: TVShow) {
        itemView.searchItemTextView.text = tvShow.show?.name
    }
}