package omg.jd.tvmazeapiclient.components.main.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import omg.jd.tvmazeapiclient.R
import omg.jd.tvmazeapiclient.entity.TvShow

class MainItemsAdapter : RecyclerView.Adapter<MainItemViewHolder>() {

    val showList: ArrayList<TvShow> = ArrayList()

    override fun onBindViewHolder(holder: MainItemViewHolder?, position: Int) {
        holder?.updateView(showList[position])
    }

    override fun getItemCount(): Int {
        return showList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainItemViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.main_item, parent, false)
        return MainItemViewHolder(view)
    }

    // TODO: use DiffUtil
    fun updateList(showList: List<TvShow>) {
        this.showList.clear()
        this.showList.addAll(showList)
        notifyDataSetChanged()
    }

}