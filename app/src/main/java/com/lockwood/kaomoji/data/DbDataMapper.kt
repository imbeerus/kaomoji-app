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
        val types: Map<String, String> = mapOf(
                "kissing" to "0",
                "hugging" to "1",
                "love" to "2",
                "bears" to "3",
                "cats" to "4",
                "dogs" to "5",
                "sheep and goats" to "6",
                "deep crying face" to "7",
                "denko" to "8",
                "flower girl" to "9",
                "happy gary" to "10",
                "it’s here!" to "11",
                "laughing" to "12",
                "lenny face" to "13",
                "look of disapproval" to "14",
                "what’s this? emoticon" to "15",
                "shrug emoticon" to "16",
                "smiling" to "17",
                "smug face" to "18",
                "emoticon face" to "19",
                "happy" to "20",
                "sad" to "21",
                "good morning" to "22",
                "good night" to "23",
                "hello" to "24",
                "thank you" to "25",
                "table flip" to "26"
        )
        return types[category.toLowerCase()].toString()
    }
}