package com.lockwood.kaomoji.extensions

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.TextView

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
    val lastItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
    val size = layoutManager.itemCount - 1
    if (lastItemPosition == size) {
        return true
    }
    return false
}