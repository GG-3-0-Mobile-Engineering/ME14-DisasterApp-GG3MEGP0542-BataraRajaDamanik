package com.batara.gigihproject.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.batara.gigihproject.R
import com.batara.gigihproject.core.domain.model.FilterLocation
import com.batara.gigihproject.core.utils.RecyclerViewClickListener
import com.batara.gigihproject.databinding.ListLocationItemBinding

class FilterLocationDisasterAdapter(private val disasterAdapter: DisasterAdapter) : RecyclerView.Adapter<FilterLocationDisasterAdapter.ListViewHolder>() {
    private val listData = ArrayList<FilterLocation>()
    var listener : RecyclerViewClickListener? = null

    fun setData(newListData : List<FilterLocation>?){
        if (newListData.isNullOrEmpty()) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        val filteredList = listData.filter { item ->
            item.type.contains(query, ignoreCase = true)
        }
        setData(filteredList)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_location_item, parent, false))

    override fun onBindViewHolder(holder: FilterLocationDisasterAdapter.ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListLocationItemBinding.bind(itemView)
        fun bind(data : FilterLocation) {
            with(binding) {
                binding.tvListLocation.text = data.type
            }
        }

        init {
            binding.root.setOnClickListener {
                listener?.onItemClicked(it, listData[adapterPosition], disasterAdapter)
            }
        }
    }
}