package com.lockwood.kaomoji.domain.model

data class KaomojiList(val id: Long, val type: String, val description: String,
                        val kaomojiList: List<Kaomoji>) {

    val size: Int
        get() = kaomojiList.size

    operator fun get(position: Int) = kaomojiList[position]
}

data class Kaomoji(val id: Long, val text: String, var isFavorite: Boolean)