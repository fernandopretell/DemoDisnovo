package com.limapps.common

import androidx.recyclerview.widget.RecyclerView


fun RecyclerView.addOnScrollStateChangedListener(f: (newState: Int, RecyclerView.OnScrollListener) -> Unit) {

    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            f(newState, this)
        }
    })

}


fun RecyclerView.addOnScrolledListener(f: (dx: Int, dy: Int, RecyclerView.OnScrollListener) -> Unit) {

    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            f(dx, dy, this)
        }

    })

}