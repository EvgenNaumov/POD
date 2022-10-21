package com.naumov.pictureoftheday.recycler

interface OnListItemClickListener {
    fun onItemClick(data: Data)
    fun onAddBtnClick(position: Int, typeData: Int)
    fun onRemoveBtnClick(position: Int)
    fun onMoveClick(position: Int, direction:Int, data:Data)
}