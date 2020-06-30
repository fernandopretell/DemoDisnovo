package com.limapps.baseui.generics

import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

class RappiListAdapter<T : RecyclerItem<*>, S : RappiRecyclerView<*>>(
        private val factory: ViewFactory<S>
) : PagedListAdapter<T, androidx.recyclerview.widget.RecyclerView.ViewHolder>(RecyclerItemDiffCallback<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return ViewHolder(factory.getView(parent, viewType))
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val view = holder.itemView as RappiRecyclerView<Any>
        getItem(position)?.let { view.bind(it.getContent(), position) }

    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.getType() ?: -1
    }
}

interface RecyclerItem<R : Any> {

    fun getType(): Int

    fun getId(): Int

    fun getContent(): R

    fun getComparator(): Any
}

interface RappiRecyclerView<R : Any> {

    fun bind(item: R, position: Int)

}

class RecyclerItemDiffCallback<T : RecyclerItem<*>> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(firsteItem: T, secondItem: T): Boolean {
        return firsteItem.getId() == secondItem.getId()
    }

    override fun areContentsTheSame(firsteItem: T, secondItem: T): Boolean {
        return firsteItem.getComparator() == secondItem.getComparator()
    }

}

abstract class ViewFactory<S : RappiRecyclerView<*>> {

    abstract fun getView(parent: ViewGroup, viewType: Int): S
}

class ViewHolder<S : RappiRecyclerView<*>>(view: S) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view as View)