package com.lockwood.kaomoji.data

import com.lockwood.kaomoji.domain.model.Kaomoji
import com.lockwood.kaomoji.domain.model.KaomojiList
import com.lockwood.kaomoji.extensions.toBoolean
import com.lockwood.kaomoji.extensions.toLong

class DbDataMapper {

    fun convertFromDomain(kaomoji: KaomojiList) = with(kaomoji) {
        val list = kaomojiList.map { convertItemFromDomain(id, it) }
        TypeKaomoji(id, type, description, list)
    }

    private fun convertItemFromDomain(typeId: Long, kaomoji: Kaomoji) = with(kaomoji) {
        ItemKaomoji(text, isFavorite.toLong(), typeId)
    }

    fun convertToDomain(typeKaomoji: TypeKaomoji) = with(typeKaomoji) {
        val list = kaomojiList.map { convertItemToDomain(it) }
        KaomojiList(_id, type, description, list as ArrayList)
    }

    fun convertItemToDomain(itemKaomoji: ItemKaomoji) = with(itemKaomoji) {
        Kaomoji(_id, text, isFavorite.toBoolean())
    }

    fun convertTypeToId(category: String): String {
        return when (category.toLowerCase()) {
            "kissing" -> "0"
            "hugging" -> "1"
            "love" -> "2"
            "bears" -> "3"
            "cats" -> "4"
            "dogs" -> "5"
            "sheep and goats" -> "6"
            "deep crying face" -> "7"
            "denko" -> "8"
            "flower girl" -> "9"
            "happy gary" -> "10"
            "it’s here!" -> "11"
            "laughing" -> "12"
            "lenny face" -> "13"
            "look of disapproval" -> "14"
            "what’s this? emoticon" -> "15"
            "shrug emoticon" -> "16"
            "smiling" -> "17"
            "smug face" -> "18"
            "emoticon face" -> "19"
            "happy" -> "20"
            "sad" -> "21"
            "good morning" -> "22"
            "good night" -> "23"
            "hello" -> "24"
            "thank you" -> "25"
            "table flip" -> "26"
            else -> "100"
        }
    }
}