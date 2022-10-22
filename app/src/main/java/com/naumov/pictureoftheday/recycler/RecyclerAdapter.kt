package com.naumov.pictureoftheday.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.naumov.pictureoftheday.R
import com.naumov.pictureoftheday.databinding.FragmentRecyclerItemEarthBinding
import com.naumov.pictureoftheday.databinding.FragmentRecyclerItemHeaderBinding
import com.naumov.pictureoftheday.databinding.FragmentRecyclerItemMarsBinding

class RecyclerAdapter(val OnItemClickCallback:OnListItemClickListener) :
        RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>(),
        ItemTouchHelperAdapter {
    private lateinit var listData: MutableList<Pair<Data,Boolean>>

    override fun getItemCount(): Int {
        return listData.count()
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].first.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
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

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listData[position])
    }

   inner class MarsViewHolder(val binding: FragmentRecyclerItemMarsBinding) :
        BaseViewHolder(binding.root), ItemTouchHelperViewHolder {
       override fun onItemSelect() {
           binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.colorPrimary))
       }

       override fun onItemClear() {
           itemView.setBackgroundColor(Color.WHITE)
       }

       override fun bind(data: Pair<Data,Boolean>) {
            binding.marsTextView.text = data.first.someText
            binding.addItemImageView.setOnClickListener{
                OnItemClickCallback.onAddBtnClick(layoutPosition, Data.TYPE_MARS)
            }
            binding.removeItemImageView.setOnClickListener {
                OnItemClickCallback.onRemoveBtnClick(layoutPosition)
            }
            binding.marsMoveItemUp.setOnClickListener {
                OnItemClickCallback.onMoveClick(layoutPosition,1, data)
            }
            binding.marsMoveItemDown.setOnClickListener {
                OnItemClickCallback.onMoveClick(layoutPosition,0, data)
            }

            binding.marsDescriptionTextView.visibility = if(listData[layoutPosition].second) View.VISIBLE else View.GONE
            binding.marsImageView.setOnClickListener {
                listData[layoutPosition] = listData[layoutPosition].let {
                    it.first to !it.second
                }
                notifyItemChanged(layoutPosition)
            }
        }
    }

    inner class EarthViewHolder(val binding: FragmentRecyclerItemEarthBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Pair<Data,Boolean>) {
            binding.earthTextView.text = data.first.someText
            binding.addItemImageView.setOnClickListener{
                OnItemClickCallback.onAddBtnClick(layoutPosition, Data.TYPE_EARTH)
            }
            binding.removeItemImageView.setOnClickListener{
                OnItemClickCallback.onRemoveBtnClick(layoutPosition)
            }
            binding.earthMoveItemUp.setOnClickListener {
                OnItemClickCallback.onMoveClick(layoutPosition,1, data)
            }
            binding.earthMoveItemDown.setOnClickListener {
                OnItemClickCallback.onMoveClick(layoutPosition,0, data)
            }
            binding.earthDescriptionTextView.visibility = if(listData[layoutPosition].second) View.VISIBLE else View.GONE
            binding.earthImageView.setOnClickListener {
                listData[layoutPosition] = listData[layoutPosition].let {
                    it.first to !it.second
                }
                notifyItemChanged(layoutPosition)
            }
        }
    }

    class HeaderViewHolder(val binding: FragmentRecyclerItemHeaderBinding) :
       BaseViewHolder(binding.root) {
        override fun bind(data: Pair<Data,Boolean>) {
            binding.header.text = data.first.someText
        }
    }

    abstract class BaseViewHolder(view: View):RecyclerView.ViewHolder(view){
            abstract fun bind(data:Pair<Data,Boolean>)
    }

    fun setList(newList: MutableList<Pair<Data,Boolean>>) {
        this.listData = newList
    }

    fun setAddToList(newList: MutableList<Pair<Data,Boolean>>, position: Int) {
        this.listData = newList
        notifyItemChanged(position)
    }

    fun setRemoveToList(newList: MutableList<Pair<Data,Boolean>>, position: Int) {
        this.listData = newList
        notifyItemRemoved(position)
    }

    fun moveItem(newList:MutableList<Pair<Data,Boolean>>,fromPosition:Int, toPosition:Int){
        this.listData = newList
        notifyItemMoved(fromPosition,toPosition)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        listData.removeAt(fromPosition).apply {
            listData.add(toPosition,this)
        }
        notifyItemMoved(fromPosition,toPosition)
    }

    override fun onItemDismiss(position: Int) {
        OnItemClickCallback.onRemoveBtnClick(position)
    }
}