package com.lockwood.kaomoji.domain.model

data class KaomojiList(val id: Long, var type: String, var description: String,
                       val kaomojiList: List<Kaomoji>) {

    val size: Int
        get() = kaomojiList.size

    operator fun get(position: Int) = kaomojiList[position]

    companion object {
        const val PREF_TYPE = "type"
        const val DEF_TYPE_VALUE = "kissing"
    }
}

data class Kaomoji(val id: Long, val text: String, var isFavorite: Boolean)