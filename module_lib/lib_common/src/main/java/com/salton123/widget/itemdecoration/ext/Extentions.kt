package com.salton123.widget.itemdecoration.ext

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author   zyyoona7
 * @version  v1.0
 * @since    2018/12/13.
 */
internal fun RecyclerView.itemCount(): Int {
    return this.adapter?.itemCount ?: 0
}

internal fun RecyclerView.itemType(itemPosition: Int): Int {
    return this.adapter?.getItemViewType(itemPosition) ?: -1
}

internal fun GridLayoutManager.spanSize(itemPosition: Int): Int {
    return this.spanSizeLookup.getSpanSize(itemPosition)
}

internal fun GridLayoutManager.spanIndex(itemPosition: Int): Int {
    return this.spanSizeLookup.getSpanIndex(itemPosition, spanCount)
}