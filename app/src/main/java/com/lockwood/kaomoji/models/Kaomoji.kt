package com.lockwood.kaomoji.models

import android.view.View

class Kaomoji(val value: String, var isFavorite: Boolean)

interface KamojiListener {
    fun bindKaomoji(kaomoji: Kaomoji)
    fun checkState(kaomoji: Kaomoji)

    fun onClicked(view: View, kaomoji: Kaomoji)
    fun onFavoriteClicked(view: View, kaomoji: Kaomoji)
}