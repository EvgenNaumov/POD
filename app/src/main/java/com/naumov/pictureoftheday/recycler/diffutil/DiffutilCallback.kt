package com.naumov.pictureoftheday.recycler.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.naumov.pictureoftheday.recycler.Data

class DiffutilCallback(
    private val oldItems: List<Pair<Data, Boolean>>,
    private val newItems: List<Pair<Data, Boolean>>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
       return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].first.id == newItems[newItemPosition].first.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].first.someDescription == newItems[newItemPosition].first.someDescription
        //можно проверять неограниченное кол-во полей
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val old = oldItems[oldItemPosition]
        val new = newItems[newItemPosition]
        return Change(old,new)
    }
}

