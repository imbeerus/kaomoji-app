package com.lockwood.kaomoji.ui

import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.lockwood.kaomoji.R
import com.lockwood.kaomoji.extensions.drawable
import org.jetbrains.anko.ctx

interface ToolbarManager {

    val toolbar: Toolbar

    var toolbarTitle: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }

    fun initToolbar(activity: AppCompatActivity) {
        activity.setSupportActionBar(toolbar)
        val actionbar: ActionBar? = activity.supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(activity.ctx.drawable(R.drawable.ic_baseline_menu, android.R.color.white))
        }
    }
}