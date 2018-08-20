package com.lockwood.kaomoji.ui

import android.os.Build
import android.os.Bundle
import com.lockwood.kaomoji.R
import com.lockwood.kaomoji.extensions.transparentStatusBar

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            transparentStatusBar()
        }
    }
}