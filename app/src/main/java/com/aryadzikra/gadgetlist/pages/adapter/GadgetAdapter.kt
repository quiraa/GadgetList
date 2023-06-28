package com.aryadzikra.gadgetlist.pages.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aryadzikra.gadgetlist.R
import com.aryadzikra.gadgetlist.model.GadgetModel
import com.aryadzikra.gadgetlist.utils.SPAN_COUNT_ONE
import com.aryadzikra.gadgetlist.utils.VIEW_TYPE_GRID
import com.aryadzikra.gadgetlist.utils.VIEW_TYPE_LINEAR
import com.google.android.material.textview.MaterialTextView

class GadgetAdapter(private val listGadget : MutableList<GadgetModel>, private val layoutManager: GridLayoutManager) : RecyclerView.Adapter<GadgetAdapter.GadgetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GadgetViewHolder {
        val view : View = if (viewType == VIEW_TYPE_LINEAR) {
            LayoutInflater.from(parent.context).inflate(R.layout.item_linear, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        }
        return GadgetViewHolder(view, viewType)
    }

    override fun getItemCount(): Int = listGadget.size

    override fun onBindViewHolder(holder: GadgetViewHolder, position: Int) {
        val gadgetList = listGadget[position]
        holder.bindView(gadgetList)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listGadget[holder.adapterPosition])
        }
    }

    class GadgetViewHolder(itemView: View, private val viewType: Int) : RecyclerView.ViewHolder(itemView) {
        private lateinit var img : ImageView
        private lateinit var name : MaterialTextView
        private lateinit var description : MaterialTextView
        private lateinit var rating : MaterialTextView
        private lateinit var category : MaterialTextView
        fun bindView (gadget: GadgetModel) {
            if(viewType == VIEW_TYPE_LINEAR) {
                img = itemView.findViewById(R.id.item_linear_img)
                name = itemView.findViewById(R.id.item_linear_title)
                description = itemView.findViewById(R.id.item_linear_description)
                rating = itemView.findViewById(R.id.item_linear_rating)
                category = itemView.findViewById(R.id.item_linear_category)

                img.load(gadget.imageUrl){
                    crossfade(true)
                    placeholder(R.drawable.baseline_image_24)
                }
                name.text = gadget.name
                description.text = gadget.description
                rating.text = gadget.rating
                category.text = gadget.category
            } else run {
                img = itemView.findViewById(R.id.item_grid_img)
                name = itemView.findViewById(R.id.item_grid_title)
                description = itemView.findViewById(R.id.item_grid_description)
                rating = itemView.findViewById(R.id.item_grid_rating)
                category = itemView.findViewById(R.id.item_grid_category)

                img.load(gadget.imageUrl) {
                    crossfade(true)
                    placeholder(R.drawable.baseline_image_24)
                }
                name.text = gadget.name
                description.text = gadget.description
                rating.text = gadget.rating
                category.text = gadget.category
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val mLayoutManager : GridLayoutManager = layoutManager
        val spanCount = mLayoutManager.spanCount
        return if(spanCount == SPAN_COUNT_ONE) {
            VIEW_TYPE_LINEAR
        } else {
            VIEW_TYPE_GRID
        }

        return super.getItemViewType(position)
    }

    interface OnItemClickCallback {
        fun onItemClicked(gadget : GadgetModel)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}