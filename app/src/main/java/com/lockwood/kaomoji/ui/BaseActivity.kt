package com.lockwood.kaomoji.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.lockwood.kaomoji.R
import com.lockwood.kaomoji.domain.model.KaomojiList
import com.lockwood.kaomoji.extensions.drawableColor
import com.lockwood.kaomoji.extensions.scroll
import com.lockwood.kaomoji.ui.fragments.KaomojisFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.ctx
import org.jetbrains.anko.find

abstract class BaseActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    private var currentIitem: KaomojiList? = null
    private var previousMenuItem: MenuItem? = null

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initToolbar(this)
        initNavigationDrawer()
        // set last item checked
        if (previousMenuItem == null) {
            selectDrawerItem(nav_view.menu.getItem(0))
        } else {
            selectDrawerItem(previousMenuItem as MenuItem)
        }
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
        ctx.drawableColor(menu.getItem(0).icon, android.R.color.white)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                true
            }
            R.id.action_info -> {
                // TODO: change to current item description
                alert("someting").show()
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
        menuItem.isCheckable = true
        menuItem.isChecked = true
        previousMenuItem?.isChecked = false
        previousMenuItem = menuItem

        val fragment: Fragment = when (menuItem.itemId) {
            R.id.nav_subcategory_favorites -> KaomojisFragment.newInstance(menuItem.title.toString(), false)
            else -> KaomojisFragment.newInstance(menuItem.title.toString())
        }
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()

        toolbarTitle = menuItem.title.toString()
    }
}