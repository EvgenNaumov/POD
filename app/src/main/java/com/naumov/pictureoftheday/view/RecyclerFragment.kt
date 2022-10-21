package com.naumov.pictureoftheday.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.naumov.pictureoftheday.databinding.FragmentRecyclerBinding
import com.naumov.pictureoftheday.recycler.Data
import com.naumov.pictureoftheday.recycler.OnListItemClickListener
import com.naumov.pictureoftheday.recycler.RecyclerAdapter

class RecyclerFragment : Fragment(), OnListItemClickListener {

    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RecyclerAdapter

    private val listData = arrayListOf<Data>(
        Data(someText = "Заголовок", type = Data.TYPE_HEADER),
        Data(someText = "Mars", type = Data.TYPE_MARS),
        Data(someText = "Mars", type = Data.TYPE_MARS),
        Data(someText = "Mars", type = Data.TYPE_MARS),
        Data(someText = "Заголовок", type = Data.TYPE_HEADER),
        Data(someText = "Earth", type = Data.TYPE_EARTH),
        Data(someText = "Earth", type = Data.TYPE_EARTH),
        Data(someText = "Earth", type = Data.TYPE_EARTH),
        Data(someText = "Earth", type = Data.TYPE_EARTH),
        Data(someText = "Earth", type = Data.TYPE_EARTH),

        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecyclerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {

        }
        adapter = RecyclerAdapter(this)
        adapter.setList(listData)
        binding.recyclerView.adapter = adapter
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
                listData.add(position, Data(Data.TYPE_EARTH, "Earth", "Новый"))
            }
            Data.TYPE_MARS -> {
                listData.add(position, Data(Data.TYPE_MARS, "Mars", "Новый"))
            }
            Data.TYPE_HEADER -> {
                listData.add(position, Data(Data.TYPE_HEADER, "Заголовок", "Новый"))
            }
        }
        adapter.setAddToList(listData, position)

    }

    override fun onMoveClick(position: Int, direction: Int, data:Data) {
        listData.removeAt(position)
        when (direction) {
            0 -> {
                listData.add(Math.min(listData.size, position - 1),data)
                adapter.moveItem(listData, position, Math.min(listData.size, position - 1))
            }
            1 -> {
                listData.add(Math.min(listData.size, position + 1),data)
                adapter.moveItem(listData, position, Math.min(listData.size, position + 1))
            }
        }

    }

    override fun onRemoveBtnClick(position: Int) {
        listData.removeAt(position)
        adapter.setRemoveToList(listData, position)
    }
}