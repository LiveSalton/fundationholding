package com.salton123.widget.itemdecoration

import androidx.recyclerview.widget.RecyclerView
import com.salton123.widget.itemdecoration.provider.GridItemDecoration
import com.salton123.widget.itemdecoration.provider.LinearItemDecoration
import com.salton123.widget.itemdecoration.provider.StaggeredGridItemDecoration

/**
 * @author   zyyoona7
 * @version  v1.0
 * @since    2018/12/12.
 */
class RecyclerViewDivider : RecyclerView.ItemDecoration() {

    companion object {

        @JvmStatic
        fun linear() = LinearItemDecoration.Builder()

        @JvmStatic
        fun grid() = GridItemDecoration.Builder()

        @JvmStatic
        fun staggeredGrid() = StaggeredGridItemDecoration.Builder()
    }
}