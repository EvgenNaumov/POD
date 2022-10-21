package com.naumov.pictureoftheday.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naumov.pictureoftheday.databinding.FragmentRecyclerItemEarthBinding
import com.naumov.pictureoftheday.databinding.FragmentRecyclerItemHeaderBinding
import com.naumov.pictureoftheday.databinding.FragmentRecyclerItemMarsBinding

class RecyclerAdapter(val listData: List<Data>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return listData.count()
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Data.TYPE_EARTH -> {
                val binding =
                    FragmentRecyclerItemEarthBinding.inflate(LayoutInflater.from(parent.context))
                EarthViewHolder(binding)
            }
            Data.TYPE_MARS -> {
                val binding =
                    FragmentRecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context))
                MarsViewHolder(binding)
            }
            Data.TYPE_HEADER -> {
                val binding =
                    FragmentRecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(binding)
            }

            else -> {
                val binding =
                    FragmentRecyclerItemEarthBinding.inflate(LayoutInflater.from(parent.context))
                EarthViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //TODO("Not yet implemented")
    }

    class MarsViewHolder(val binding: FragmentRecyclerItemMarsBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    class EarthViewHolder(val binding: FragmentRecyclerItemEarthBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    class HeaderViewHolder(val binding: FragmentRecyclerItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

}