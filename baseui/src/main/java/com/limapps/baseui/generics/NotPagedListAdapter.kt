package com.limapps.baseui.generics

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup

class NotPagedListAdapter<T : RecyclerItem<*>, S : RappiRecyclerView<*>>(
        private val factory: ViewFactory<S>
) : ListAdapter<T, androidx.recyclerview.widget.RecyclerView.ViewHolder>(RecyclerItemDiffCallback<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return ViewHolder(factory.getView(parent, viewType))
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val view = holder.itemView as RappiRecyclerView<Any>
        view.bind(getItem(position).getContent(), position)

    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getType()
    }
}