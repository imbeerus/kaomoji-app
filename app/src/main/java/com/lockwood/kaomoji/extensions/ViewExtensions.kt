package com.lockwood.kaomoji.extensions

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout

val View.ctx: Context
    get() = context

var TextView.textColor: Int
    get() = currentTextColor
    set(v) = setTextColor(v)

fun RecyclerView.addDividerItemDecoration(orientation: Int = DividerItemDecoration.VERTICAL) {
    val dividerItemDecoration = DividerItemDecoration(ctx, orientation)
    addItemDecoration(dividerItemDecoration)
}

fun AppBarLayout.scroll() {
    setExpanded(true, true)
}

fun AppBarLayout.collapse() {
    setExpanded(false, true)
}

fun MenuItem.check() {
    isCheckable = true
    isChecked = true
}

fun RecyclerView.isLastItemReached(): Boolean {
    if (layoutManager is LinearLayoutManager) {
        val lastItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        val size = (layoutManager as LinearLayoutManager).itemCount - 1
        if (lastItemPosition == size) {
            return true
        }
    } else if (layoutManager is GridLayoutManager) {
        val lastItemPosition = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
        val size = (layoutManager as GridLayoutManager).itemCount - ((layoutManager as GridLayoutManager).spanCount * 2)
        if (lastItemPosition == size) {
            return true
        }
    }
    return false
}