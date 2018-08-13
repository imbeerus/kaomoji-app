package com.lockwood.kaomoji.ui

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.lockwood.kaomoji.R
import com.lockwood.kaomoji.extensions.setFirstItemChecked
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find

abstract class BaseActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        nav_view.setFirstItemChecked()
        initToolbar()
        initNavigationDrawer()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initNavigationDrawer() {
        nav_view.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isCheckable = true
            menuItem.isChecked = true
            // close drawer when item is tapped
            drawer_layout.closeDrawers()
            selectDrawerItem(menuItem)
            true
        }
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        toolbarTitle = menuItem.title.toString()
    }
}