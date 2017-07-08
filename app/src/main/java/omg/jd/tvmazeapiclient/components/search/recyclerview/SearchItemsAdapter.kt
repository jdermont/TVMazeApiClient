package omg.jd.tvmazeapiclient.components.search.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.entity.TvShow

class SearchItemsAdapter(val clickListener: ViewHolderOnClickListener?) : RecyclerView.Adapter<SearchItemViewHolder>() {

    val showList: ArrayList<TvShow> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchItemViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.search_item, parent, false)
        return SearchItemViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder?, position: Int) {
        holder?.updateView(showList[position])
    }

    override fun getItemCount(): Int {
        return showList.size
    }

    // TODO: use DiffUtil
    fun updateList(showList: List<TvShow>) {
        this.showList.clear()
        this.showList.addAll(showList)
        notifyDataSetChanged()
    }

    interface ViewHolderOnClickListener {
        fun onClick(viewHolder: SearchItemViewHolder)
    }
}