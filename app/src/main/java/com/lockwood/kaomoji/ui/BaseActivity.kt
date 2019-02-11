package com.lockwood.kaomoji.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.lockwood.kaomoji.R
import com.lockwood.kaomoji.extensions.check
import com.lockwood.kaomoji.extensions.drawableColor
import com.lockwood.kaomoji.extensions.replaceFragment
import com.lockwood.kaomoji.extensions.scroll
import com.lockwood.kaomoji.ui.fragments.KaomojisFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.jetbrains.anko.find

abstract class BaseActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    private var previousMenuItem: MenuItem? = null
    private var lastItemId: Int = 0

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initToolbar(this)
        initNavigationDrawer()
        // set last item checked
        if (savedInstanceState != null) {
            lastItemId = savedInstanceState.getInt(BUNDLE_LAST_ITEM_ID, nav_view.menu.getItem(0).itemId)
            previousMenuItem = nav_view.menu.findItem(lastItemId)
            with(nav_view.menu.findItem(lastItemId)) {
                check()
                toolbarTitle = title.toString()
            }
        } else {
            selectDrawerItem(nav_view.menu.getItem(0))
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(BUNDLE_LAST_ITEM_ID, lastItemId)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        drawableColor(menu.getItem(0).icon, android.R.color.white)
        return super.onPrepareOptionsMenu(menu)
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
            // close drawer when item is tapped
            drawer_layout.closeDrawers()
            appbar.scroll()
            selectDrawerItem(menuItem)
            true
        }
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        // set item as selected to persist highlight
        menuItem.check()
        lastItemId = menuItem.itemId
        previousMenuItem?.isChecked = false
        previousMenuItem = menuItem

        val fragment: Fragment = when (menuItem.itemId) {
            R.id.nav_subcategory_favorites -> KaomojisFragment.newInstance(menuItem.title.toString(), false)
            else -> KaomojisFragment.newInstance(menuItem.title.toString())
        }
        replaceFragment(R.id.fragment_container, fragment)
        toolbarTitle = menuItem.title.toString()
    }

    companion object {
        private const val BUNDLE_LAST_ITEM_ID = "lastItemId"
    }
}