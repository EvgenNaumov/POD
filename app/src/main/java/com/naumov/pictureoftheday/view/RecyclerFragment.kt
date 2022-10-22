package com.naumov.pictureoftheday.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import com.naumov.pictureoftheday.databinding.FragmentRecyclerBinding
import com.naumov.pictureoftheday.recycler.Data
import com.naumov.pictureoftheday.recycler.ItemTouchHelperCallback
import com.naumov.pictureoftheday.recycler.OnListItemClickListener
import com.naumov.pictureoftheday.recycler.RecyclerAdapter

class RecyclerFragment : Fragment(), OnListItemClickListener {

    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RecyclerAdapter

    private val listData = arrayListOf(
        Pair(Data(id=0, someText = "Заголовок", type = Data.TYPE_HEADER), false),
        Pair(Data(id=1, someText = "Mars", type = Data.TYPE_MARS), false),
        Pair(Data(id=2, someText = "Mars", type = Data.TYPE_MARS), false),
        Pair(Data(id=3, someText = "Mars", type = Data.TYPE_MARS), false),
        Pair(Data(id=4, someText = "Заголовок", type = Data.TYPE_HEADER), false),
        Pair(Data(id=5, someText = "Earth", type = Data.TYPE_EARTH), false),
        Pair(Data(id=6,someText = "Earth", type = Data.TYPE_EARTH), false),
        Pair(Data(id=7,someText = "Earth", type = Data.TYPE_EARTH), false),
        Pair(Data(id=8,someText = "Earth", type = Data.TYPE_EARTH), false),
        Pair(Data(id=9,someText = "Earth", type = Data.TYPE_EARTH), false),

        )

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
        adapter.setList(listData)
        binding.recyclerView.adapter = adapter
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView)
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
        when (typeData) {
            Data.TYPE_EARTH -> {
                listData.add(position,
                    Pair(Data(listData.maxOf { listD -> listD.first.id}+1,Data.TYPE_EARTH,"Earth", "Новый"), false))
            }
            Data.TYPE_MARS -> {
                listData.add(position,
                    Pair(Data(listData.maxOf { listD -> listD.first.id}+1,Data.TYPE_MARS, "Mars", "Новый"),false))
            }
            Data.TYPE_HEADER -> {
                listData.add(position,
                    Pair(Data(listData.maxOf { listD -> listD.first.id}+1, Data.TYPE_HEADER, "Заголовок", "Новый"),false))
            }
        }
        adapter.setAddToList(listData, position)

    }

    override fun onMoveClick(position: Int, direction: Int, data: Pair<Data,Boolean>) {
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
}