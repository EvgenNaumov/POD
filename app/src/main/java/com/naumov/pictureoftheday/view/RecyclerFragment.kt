package com.naumov.pictureoftheday.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.naumov.pictureoftheday.databinding.FragmentRecyclerBinding
import com.naumov.pictureoftheday.recycler.*
import com.naumov.pictureoftheday.utils.PriorityEnum
import com.naumov.pictureoftheday.viewmodel.NoticeAppState
import com.naumov.pictureoftheday.viewmodel.NoticeViewModel

class RecyclerFragment : Fragment(), OnListItemClickListener {

    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RecyclerAdapter
    private lateinit var listData:MutableList<Pair<Data,Boolean>>

    private val viewModel: NoticeViewModel by lazy {
        ViewModelProvider(this).get(NoticeViewModel::class.java)
    }
//    private val listData = arrayListOf(
//        Pair(NoticeData(id = 0, someText = "Заголовок", type = Data.TYPE_HEADER), false),
//        Pair(NoticeData(id = 1, someText = "Mars", type = Data.TYPE_MARS), false),
//        Pair(NoticeData(id = 2, someText = "Mars", type = Data.TYPE_MARS), false),
//        Pair(
//            NoticeData(id = 3, someText = "Mars", type = Data.TYPE_MARS, priority = PriorityEnum.Height),
//            false
//        ),
//        Pair(NoticeData(id = 4, someText = "Заголовок", type = Data.TYPE_HEADER), false),
//        Pair(
//            NoticeData(
//                id = 5,
//                someText = "Earth",
//                type = Data.TYPE_EARTH,
//                priority = PriorityEnum.Height
//            ), false
//        ),
//        Pair(
//            NoticeData(id = 6, someText = "Earth", type = Data.TYPE_EARTH, priority = PriorityEnum.Low),
//            false
//        ),
//        Pair(NoticeData(id = 7, someText = "Earth", type = Data.TYPE_EARTH), false),
//        Pair(NoticeData(id = 8, someText = "Earth", type = Data.TYPE_EARTH), false),
//        Pair(NoticeData(id = 9, someText = "Earth", type = Data.TYPE_EARTH), false),
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRecyclerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecyclerAdapter(this)
//        adapter.setList(listData)
        binding.recyclerView.adapter = adapter
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView)

        val observer = { data: NoticeAppState -> renderData(data) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getAll()
    }

    private fun renderData(data: NoticeAppState) {
        when (data) {
            is NoticeAppState.Error -> {
                Snackbar.make(binding.root, "Ошибка получения данных", Snackbar.LENGTH_SHORT)
            }
            is NoticeAppState.Success->{
                listData = data.noticeData.toMutableList()
                adapter.setList(listData)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = RecyclerFragment()
    }

    override fun onItemClick(data: Data) {
        TODO("Not yet implemented")
    }

    override fun onAddBtnClick(position: Int, typeData: Int) {
        val listDataAdapter = adapter.getListData()

        when (typeData) {
            Data.TYPE_EARTH -> {
                listData.add(listDataAdapter.size.coerceAtMost(position + 1),
                    Pair(
                        Data(
                            listData.maxOf { listD -> listD.first.id } + 1,
                            title = "Заметка ".plus((listData.maxOf { listD -> listD.first.id } + 1).toString())  ,
                            type = Data.TYPE_EARTH,
                            someText = "Новая заметка",
                            someDescription = "",
                            priority = PriorityEnum.Normal,
                            id_section = "земля"
                        ), false
                    )
                )
            }
            Data.TYPE_MARS -> {
                listData.add(listData.size.coerceAtMost(position + 1),
                    Pair(
                        Data(
                            listData.maxOf { listD -> listD.first.id } + 1,
                            title = "Заметка ".plus((listData.maxOf { listD -> listD.first.id } + 1).toString())  ,
                            type = Data.TYPE_MARS,
                            someText = "Новая заметка",
                            someDescription = "",
                            priority = PriorityEnum.Normal,
                            id_section = "марс"
                        ), false
                    )
                )
            }
            Data.TYPE_HEADER -> {
                listData.add(listData.size.coerceAtMost(position + 1),
                    Pair(
                        Data(
                            listData.maxOf { listD -> listD.first.id } + 1,
                            title = "Заметка ".plus((listData.maxOf { listD -> listD.first.id } + 1).toString())  ,
                            type = Data.TYPE_HEADER,
                            someText = "Новая заметка",
                            someDescription = "",
                            priority = PriorityEnum.Normal,
                            id_section = "марс"
                        ), false
                    )
                )
            }
        }
        adapter.setAddToList(listDataAdapter, position)

    }

    override fun onMoveClick(position: Int, direction: Int, data: Pair<Data, Boolean>) {
        listData.removeAt(position)
        when (direction) {
            1 -> {
                listData.add(0.coerceAtLeast(position - 1), data)
                adapter.moveItem(listData, position, listData.indexOf(data))
            }
            0 -> {
                listData.add(listData.size.coerceAtMost(position + 1), data)
                adapter.moveItem(listData, position, listData.indexOf(data))
            }
        }

    }

    override fun onRemoveBtnClick(position: Int) {
        listData.removeAt(position)
        adapter.setRemoveToList(listData, position)
    }

    override fun onChangePriority(position: Int, valueProirity: PriorityEnum) {
        val dataPair = listData[position]
        dataPair.first.priority = valueProirity
        listData.removeAt(position)
        listData.add(position, dataPair)
        adapter.setAddToList(listData, position)
    }

    interface callBackListNotice {
        fun onResponse(listNotice: List<Data>)
    }
}