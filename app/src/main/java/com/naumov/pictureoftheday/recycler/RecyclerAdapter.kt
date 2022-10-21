package com.naumov.pictureoftheday.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naumov.pictureoftheday.databinding.FragmentRecyclerItemEarthBinding
import com.naumov.pictureoftheday.databinding.FragmentRecyclerItemHeaderBinding
import com.naumov.pictureoftheday.databinding.FragmentRecyclerItemMarsBinding

class RecyclerAdapter(val OnItemClick:OnListItemClickListener) : RecyclerView.Adapter<RecyclerAdapter.BaseHiewHolder>() {
    private lateinit var listData: List<Data>

    override fun getItemCount(): Int {
        return listData.count()
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHiewHolder {
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

    override fun onBindViewHolder(holder: BaseHiewHolder, position: Int) {
        holder.bind(listData[position])
    }

   inner class MarsViewHolder(val binding: FragmentRecyclerItemMarsBinding) :
        BaseHiewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.marsTextView.text = data.someText
            binding.addItemImageView.setOnClickListener{
                OnItemClick.onAddBtnClick(layoutPosition, Data.TYPE_MARS)
            }
            binding.removeItemImageView.setOnClickListener {
                OnItemClick.onRemoveBtnClick(layoutPosition)
            }
            binding.marsMoveItemUp.setOnClickListener {
                OnItemClick.onMoveClick(layoutPosition,1, data)
            }
            binding.marsMoveItemDown.setOnClickListener {
                OnItemClick.onMoveClick(layoutPosition,0, data)
            }
        }
    }

    inner class EarthViewHolder(val binding: FragmentRecyclerItemEarthBinding) :
        BaseHiewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.earthTextView.text = data.someText
            binding.addItemImageView.setOnClickListener{
                OnItemClick.onAddBtnClick(layoutPosition, Data.TYPE_EARTH)
            }
            binding.removeItemImageView.setOnClickListener{
                OnItemClick.onRemoveBtnClick(layoutPosition)
            }
            binding.earthMoveItemUp.setOnClickListener {
                OnItemClick.onMoveClick(layoutPosition,1, data)
            }
            binding.earthMoveItemDown.setOnClickListener {
                OnItemClick.onMoveClick(layoutPosition,0, data)
            }
        }
    }

    class HeaderViewHolder(val binding: FragmentRecyclerItemHeaderBinding) :
       BaseHiewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.header.text = data.someText
        }
    }

    abstract class BaseHiewHolder(view: View):RecyclerView.ViewHolder(view){
            abstract fun bind(data:Data)
    }

    fun setList(newList: List<Data>) {
        this.listData = newList
    }

    fun setAddToList(newList: List<Data>, position: Int) {
        this.listData = newList
        notifyItemChanged(position)
    }

    fun setRemoveToList(newList: List<Data>, position: Int) {
        this.listData = newList
        notifyItemRemoved(position)
    }

    fun moveItem(newList:List<Data>,fromPosition:Int, toPosition:Int){
        this.listData = newList
        notifyItemMoved(fromPosition,toPosition)
    }
}