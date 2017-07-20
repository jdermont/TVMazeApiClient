package omg.jd.tvmazeapiclient.recyclerview

import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView
import android.view.View
import omg.jd.tvmazeapiclient.entity.TvShow

abstract class TvShowViewHolder(itemView: View, clickListener: ViewHolderOnClickListener?) : RecyclerView.ViewHolder(itemView) {
    init {
        if (clickListener != null) {
            itemView.setOnClickListener {
                clickListener.onItemClick(this)
            }
        }
    }

    abstract val transitedView: View
    var data: Any? = null

    @CallSuper
    open fun updateView(tvShow: TvShow) {
        data = tvShow
    }

    interface ViewHolderOnClickListener {
        fun onItemClick(viewHolder: TvShowViewHolder)
    }
}