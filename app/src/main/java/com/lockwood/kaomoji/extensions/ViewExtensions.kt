package com.lockwood.kaomoji.extensions

import android.content.Context
import android.support.design.widget.NavigationView
import android.view.View
import android.widget.TextView

val View.ctx: Context
    get() = context

var TextView.textColor: Int
    get() = currentTextColor
    set(v) = setTextColor(v)