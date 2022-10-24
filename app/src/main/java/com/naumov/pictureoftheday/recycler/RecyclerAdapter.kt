package com.naumov.pictureoftheday.recycler

import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.naumov.pictureoftheday.R
import com.naumov.pictureoftheday.databinding.FragmentRecyclerItemEarthBinding
import com.naumov.pictureoftheday.databinding.FragmentRecyclerItemHeaderBinding
import com.naumov.pictureoftheday.databinding.FragmentRecyclerItemMarsBinding
import com.naumov.pictureoftheday.recycler.diffutil.Change
import com.naumov.pictureoftheday.recycler.diffutil.DiffutilCallback
import com.naumov.pictureoftheday.recycler.diffutil.createCombinedPayload
import com.naumov.pictureoftheday.utils.PriorityEnum
import java.io.InputStream

class RecyclerAdapter(val OnItemClickCallback: OnListItemClickListener) :
    RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>(),
    ItemTouchHelperAdapter {
    private lateinit var listData: MutableList<Pair<Data, Boolean>>

    fun setList(newList: MutableList<Pair<Data, Boolean>>) {
        this.listData = newList
    }

    fun setListDataForDiffUtil(listDataNew: MutableList<Pair<Data, Boolean>>) {
        val diff = DiffUtil.calculateDiff(DiffutilCallback(this.listData, listDataNew))
        diff.dispatchUpdatesTo(this)
        this.listData = listDataNew
    }

    override fun getItemCount(): Int {
        return listData.count()
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].first.type
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
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

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int
    ) {
        holder.bind(listData[position])
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val createCombinePayload =
                createCombinedPayload(payloads as MutableList<Change<Pair<Data, Boolean>>>)//
            //если в DiffUtilCallback проверяется много полей то if обязателен
            if (createCombinePayload.newData.first.someDescription != createCombinePayload.oldData.first.someDescription) {
                if (createCombinePayload.newData.first.type == Data.TYPE_EARTH){
                holder.itemView.findViewById<TextView>(R.id.earthDescriptionTextView).text =
                    createCombinePayload.newData.first.someDescription} else {
                    holder.itemView.findViewById<TextView>(R.id.marsDescriptionTextView).text =
                        createCombinePayload.newData.first.someDescription
                }
            } else if (createCombinePayload.newData.first.priority != createCombinePayload.oldData.first.priority) {

                val drawable = when (createCombinePayload.newData.first.priority) {
                    PriorityEnum.Height -> {
                        ContextCompat.getDrawable(holder.itemView.context, R.drawable.red_circle)
                    }
                    PriorityEnum.Medium -> {
                        ContextCompat.getDrawable(holder.itemView.context, R.drawable.yellow_circle)

                    }
                    PriorityEnum.Low -> {
                        ContextCompat.getDrawable(holder.itemView.context, R.drawable.blue_circle)

                    }
                    else -> {
                        ContextCompat.getDrawable(
                            holder.itemView.context,
                            R.drawable.normal_priority
                        )
                    }
                }
                if (createCombinePayload.newData.first.type == Data.TYPE_EARTH) {
                    holder.itemView.findViewById<ImageView>(R.id.earthPriority)
                        .setImageDrawable(drawable)

                } else {
                    holder.itemView.findViewById<ImageView>(R.id.marsPriority)
                        .setImageDrawable(drawable)
                }
            }

        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        listData.removeAt(fromPosition).apply {
            listData.add(toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        OnItemClickCallback.onRemoveBtnClick(position)
    }

    inner class MarsViewHolder(val binding: FragmentRecyclerItemMarsBinding) :
        BaseViewHolder(binding.root), ItemTouchHelperViewHolder {
        override fun onItemSelect() {
            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorPrimary
                )
            )
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }

        override fun bind(data: Pair<Data, Boolean>) {
            binding.marsTextView.text = data.first.someText

            val drawable = when (data.first.priority) {
                PriorityEnum.Height -> {
                    ContextCompat.getDrawable(binding.root.context, R.drawable.red_circle)
                }
                PriorityEnum.Medium -> {
                    ContextCompat.getDrawable(binding.root.context, R.drawable.yellow_circle)

                }
                PriorityEnum.Low -> {
                    ContextCompat.getDrawable(binding.root.context, R.drawable.blue_circle)

                }
                else -> {
                    ContextCompat.getDrawable(binding.root.context, R.drawable.normal_priority)
                }
            }
            binding.marsPriority.setImageDrawable(drawable)

            binding.marsPriority.setOnClickListener { it ->
                val popupMenu = PopupMenu(binding.root.context, it)
                popupMenu.inflate(R.menu.menu_recycle_item)
                val menu = popupMenu.menu
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.action_height_priority -> {
                            OnItemClickCallback.onChangePriority(
                                layoutPosition,
                                PriorityEnum.Height
                            )
                            Toast.makeText(
                                itemView.context,
                                "action_height_priority",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        R.id.action_medium_priority -> {
                            OnItemClickCallback.onChangePriority(
                                layoutPosition,
                                PriorityEnum.Medium
                            )
                            Toast.makeText(
                                itemView.context,
                                "action_medium_priority",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        R.id.action_low_priority -> {
                            OnItemClickCallback.onChangePriority(layoutPosition, PriorityEnum.Low)
                            Toast.makeText(
                                itemView.context,
                                "action_low_priority",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            OnItemClickCallback.onChangePriority(
                                layoutPosition,
                                PriorityEnum.Normal
                            )
                        }
                    }
                    return@setOnMenuItemClickListener true
                }
                popupMenu.show()
            }

            binding.addItemImageView.setOnClickListener {
                OnItemClickCallback.onAddBtnClick(layoutPosition, Data.TYPE_MARS)
            }
            binding.removeItemImageView.setOnClickListener {
                OnItemClickCallback.onRemoveBtnClick(layoutPosition)
            }
            binding.marsMoveItemUp.setOnClickListener {
                OnItemClickCallback.onMoveClick(layoutPosition, 1, data)
            }
            binding.marsMoveItemDown.setOnClickListener {
                OnItemClickCallback.onMoveClick(layoutPosition, 0, data)
            }

            binding.marsDescriptionTextView.visibility =
                if (listData[layoutPosition].second) View.VISIBLE else View.GONE
            binding.marsImageView.setOnClickListener {
                listData[layoutPosition] = listData[layoutPosition].let {
                    it.first to !it.second
                }
                notifyItemChanged(layoutPosition)
            }
        }

    }

    inner class EarthViewHolder(val binding: FragmentRecyclerItemEarthBinding) :
        BaseViewHolder(binding.root), ItemTouchHelperViewHolder {
        override fun onItemSelect() {
            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorPrimary
                )
            )
        }

        override fun onItemClear() {
            binding.root.setBackgroundColor(0)
        }

        override fun bind(data: Pair<Data, Boolean>) {

            binding.earthTextView.text = data.first.someText
            val drawable = when (data.first.priority) {
                PriorityEnum.Height -> {
                    ContextCompat.getDrawable(binding.root.context, R.drawable.red_circle)
                }
                PriorityEnum.Medium -> {
                    ContextCompat.getDrawable(binding.root.context, R.drawable.yellow_circle)

                }
                PriorityEnum.Low -> {
                    ContextCompat.getDrawable(binding.root.context, R.drawable.blue_circle)

                }
                else -> {
                    ContextCompat.getDrawable(binding.root.context, R.drawable.normal_priority)
                }
            }
            binding.earthPriority.setImageDrawable(drawable)

            binding.earthPriority.setOnClickListener { it ->
                val popupMenu = PopupMenu(binding.root.context, it)
                popupMenu.inflate(R.menu.menu_recycle_item)
                val menu = popupMenu.menu
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.action_height_priority -> {
                            OnItemClickCallback.onChangePriority(
                                layoutPosition,
                                PriorityEnum.Height
                            )
                            Toast.makeText(
                                itemView.context,
                                "action_height_priority",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        R.id.action_medium_priority -> {
                            OnItemClickCallback.onChangePriority(
                                layoutPosition,
                                PriorityEnum.Medium
                            )
                            Toast.makeText(
                                itemView.context,
                                "action_medium_priority",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        R.id.action_low_priority -> {
                            OnItemClickCallback.onChangePriority(layoutPosition, PriorityEnum.Low)
                            Toast.makeText(
                                itemView.context,
                                "action_low_priority",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            OnItemClickCallback.onChangePriority(
                                layoutPosition,
                                PriorityEnum.Normal
                            )
                        }
                    }
                    return@setOnMenuItemClickListener true
                }
                popupMenu.show()
            }

            binding.addItemImageView.setOnClickListener {
                OnItemClickCallback.onAddBtnClick(layoutPosition, Data.TYPE_EARTH)
            }
            binding.removeItemImageView.setOnClickListener {
                OnItemClickCallback.onRemoveBtnClick(layoutPosition)
            }
            binding.earthMoveItemUp.setOnClickListener {
                OnItemClickCallback.onMoveClick(layoutPosition, 1, data)
            }
            binding.earthMoveItemDown.setOnClickListener {
                OnItemClickCallback.onMoveClick(layoutPosition, 0, data)
            }
            binding.earthDescriptionTextView.visibility =
                if (listData[layoutPosition].second) View.VISIBLE else View.GONE
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
        override fun bind(data: Pair<Data, Boolean>) {
            binding.header.text = data.first.someText
        }
    }

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(data: Pair<Data, Boolean>)
    }

    fun setAddToList(newList: MutableList<Pair<Data, Boolean>>, position: Int) {
        this.listData = newList
        notifyItemChanged(position)
    }

    fun setRemoveToList(newList: MutableList<Pair<Data, Boolean>>, position: Int) {
        this.listData = newList
        notifyItemRemoved(position)
    }

    fun moveItem(
        newList: MutableList<Pair<Data, Boolean>>,
        fromPosition: Int,
        toPosition: Int
    ) {
        this.listData = newList
        notifyItemMoved(fromPosition, toPosition)
    }

}