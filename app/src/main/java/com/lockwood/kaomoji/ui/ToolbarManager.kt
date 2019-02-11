package com.lockwood.kaomoji.ui

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.lockwood.kaomoji.R
import com.lockwood.kaomoji.extensions.drawable

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
            setHomeAsUpIndicator(activity.drawable(R.drawable.ic_baseline_menu, android.R.color.white))
        }
    }
}