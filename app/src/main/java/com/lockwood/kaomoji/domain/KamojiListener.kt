package com.lockwood.kaomoji.domain

import android.view.View

interface KamojiListener {
    fun bindKaomoji(kaomoji: Kaomoji)
    fun checkState(kaomoji: Kaomoji)

    fun onClicked(view: View, kaomoji: Kaomoji)
    fun onFavoriteClicked(view: View, kaomoji: Kaomoji)
}