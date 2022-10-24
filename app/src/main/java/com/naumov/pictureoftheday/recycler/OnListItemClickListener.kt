package com.naumov.pictureoftheday.recycler

import com.naumov.pictureoftheday.utils.PriorityEnum

interface OnListItemClickListener {
    fun onItemClick(data: Data)
    fun onAddBtnClick(position: Int, typeData: Int)
    fun onRemoveBtnClick(position: Int)
    fun onMoveClick(position: Int, direction:Int, data:Pair<Data,Boolean>)
    fun onChangePriority(position: Int, valueProirity: PriorityEnum)
}