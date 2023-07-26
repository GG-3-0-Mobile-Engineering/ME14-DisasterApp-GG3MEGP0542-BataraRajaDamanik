package com.batara.gigihproject.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.batara.gigihproject.R
import com.batara.gigihproject.core.domain.model.FilterLocation
import com.batara.gigihproject.core.utils.RecyclerViewClickLocationListener
import com.batara.gigihproject.databinding.ItemFilterDisasterBinding

class FilterLocationDisasterAdapter(private val disasterAdapter: DisasterAdapter) : RecyclerView.Adapter<FilterLocationDisasterAdapter.ListViewHolder>() {
    private val listData = ArrayList<FilterLocation>()
    var listener : RecyclerViewClickLocationListener? = null

    fun setData(newListData : List<FilterLocation>?){
        if (newListData.isNullOrEmpty()) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_filter_disaster, parent, false))

    override fun onBindViewHolder(holder: FilterLocationDisasterAdapter.ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFilterDisasterBinding.bind(itemView)
        fun bind(data : FilterLocation) {
            with(binding) {
                binding.tvFilterDisaster.text = data.type
            }
        }

        init {
            binding.root.setOnClickListener {
                listener?.onItemClickedLocation(it, listData[adapterPosition], disasterAdapter)
            }
        }
    }
}