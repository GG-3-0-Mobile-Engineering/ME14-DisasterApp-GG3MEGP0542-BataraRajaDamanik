package com.batara.gigihproject.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.batara.gigihproject.R
import com.batara.gigihproject.core.domain.model.Disaster
import com.batara.gigihproject.databinding.ItemListDisasterBinding
import com.bumptech.glide.Glide

class DisasterAdapter : RecyclerView.Adapter<DisasterAdapter.ListViewHolder>() {
    private val listData = ArrayList<Disaster>()

    fun setData(newListData : List<Disaster>?){
        if (newListData.isNullOrEmpty()) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_disaster, parent, false))

    override fun onBindViewHolder(holder: DisasterAdapter.ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListDisasterBinding.bind(itemView)
        fun bind(data : Disaster) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.properties.imageUrl)
                    .into(ivDisaster)
                tvText.text = data.properties.text
                tvDisasterType.text = data.properties.disasterType
            }
        }
    }
}