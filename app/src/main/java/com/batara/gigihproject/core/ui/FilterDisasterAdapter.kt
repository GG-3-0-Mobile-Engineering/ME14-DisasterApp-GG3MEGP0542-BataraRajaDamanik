package com.batara.gigihproject.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.batara.gigihproject.R
import com.batara.gigihproject.core.domain.model.FilterDisaster
import com.batara.gigihproject.core.utils.RecyclerViewClickListener
import com.batara.gigihproject.databinding.ItemFilterDisasterBinding

class FilterDisasterAdapter(private val disasterAdapter: DisasterAdapter) : RecyclerView.Adapter<FilterDisasterAdapter.ListViewHolder>() {
    private val listData = ArrayList<FilterDisaster>()
    var listener : RecyclerViewClickListener? = null

    fun setData(newListData : List<FilterDisaster>?){
        if (newListData.isNullOrEmpty()) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_filter_disaster, parent, false))

    override fun onBindViewHolder(holder: FilterDisasterAdapter.ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFilterDisasterBinding.bind(itemView)
        fun bind(data : FilterDisaster) {
            with(binding) {
                binding.tvFilterDisaster.text = data.type
            }
        }

        init {
            binding.root.setOnClickListener {
                listener?.onItemClicked(it, listData[adapterPosition], disasterAdapter)
            }
        }
    }
}