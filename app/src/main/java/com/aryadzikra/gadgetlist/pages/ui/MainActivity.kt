package com.aryadzikra.gadgetlist.pages.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aryadzikra.gadgetlist.R
import com.aryadzikra.gadgetlist.databinding.ActivityMainBinding
import com.aryadzikra.gadgetlist.model.GadgetModel
import com.aryadzikra.gadgetlist.pages.adapter.GadgetAdapter
import com.aryadzikra.gadgetlist.utils.GADGET_KEY
import com.aryadzikra.gadgetlist.utils.SPAN_COUNT_ONE
import com.aryadzikra.gadgetlist.utils.SPAN_COUNT_TWO

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding : ActivityMainBinding by viewBinding()

    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listGadget : MutableList<GadgetModel> = getGadgetList()
        showGadgetList(listGadget)
        setToolbar()
    }

    private fun setToolbar() {
        binding.toolbarMain.setOnMenuItemClickListener {item ->
            when(item.itemId) {
                R.id.action_list_view -> {
                    switchListLayout(gridLayoutManager)
                    switchIcon(item)
                    true
                }
                else -> false
            }
        }
    }

    private fun getGadgetList() : MutableList<GadgetModel> {
        val resultList = mutableListOf<GadgetModel>()
        val gadgetList = resources.getStringArray(R.array.gadget_list)

        gadgetList.forEach { rawData ->
            val rippedGadgetList = rawData.split("|")
            val gadgetName = rippedGadgetList[0]
            val gadgetDescription = rippedGadgetList[1]
            val gadgetPrice = rippedGadgetList[2]
            val gadgetRating = rippedGadgetList[3]
            val gadgetCategory = rippedGadgetList[4]
            val gadgetImgUrl = rippedGadgetList[5]
            val gadgetVideoUrl = rippedGadgetList[6]
            val gadgetModel = GadgetModel(gadgetName, gadgetDescription, gadgetPrice, gadgetRating, gadgetCategory, gadgetImgUrl, gadgetVideoUrl)

            resultList.add(gadgetModel)
        }

        return resultList
    }

    private fun showGadgetList(list: MutableList<GadgetModel>) {
        gridLayoutManager = GridLayoutManager(this, SPAN_COUNT_ONE)
        val adapter = GadgetAdapter(list, gridLayoutManager)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = switchListLayout(gridLayoutManager)

        adapter.setOnItemClickCallback(object : GadgetAdapter.OnItemClickCallback{
            override fun onItemClicked(gadget: GadgetModel) {
                val detailIntent = Intent(this@MainActivity, DetailActivity::class.java)
                detailIntent.putExtra(GADGET_KEY, gadget)
                startActivity(detailIntent)
            }
        })
    }

    private fun switchListLayout(gridLayoutManager: GridLayoutManager) : RecyclerView.LayoutManager {
        if (gridLayoutManager.spanCount == SPAN_COUNT_ONE){
            gridLayoutManager.spanCount = SPAN_COUNT_TWO
        } else {
            gridLayoutManager.spanCount = SPAN_COUNT_ONE
        }
        return gridLayoutManager
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun switchIcon(item : MenuItem) {
        if(gridLayoutManager.spanCount == SPAN_COUNT_TWO){
            item.icon = resources.getDrawable(R.drawable.baseline_grid_view_24)
        } else {
            item.icon = resources.getDrawable(R.drawable.baseline_view_list_24)
        }
    }
}