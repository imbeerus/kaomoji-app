package com.lockwood.kaomoji.extensions

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
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