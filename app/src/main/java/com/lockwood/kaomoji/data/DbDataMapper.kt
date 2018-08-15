package com.lockwood.kaomoji.data

class DbDataMapper {

    fun convertFromDomain(kaomoji: KaomojiList) = with(kaomoji) {
        val list = kaomojiList.map { convertItemFromDomain(id, it) }
        TypeKaomoji(id, type, description, list)
    }

    private fun convertItemFromDomain(typeId: Long, kaomoji: Kaomoji) = with(kaomoji) {
        ItemKaomoji(text, isFavorite, typeId)
    }

    fun convertToDomain(typeKaomoji: TypeKaomoji) = with(typeKaomoji) {
        val list = kaomojiList.map { convertItemToDomain(it) }
        KaomojiList(_id, type, description, list)
    }

    fun convertItemToDomain(itemKaomoji: ItemKaomoji) = with(itemKaomoji) {
        Kaomoji(_id, text, isFavorite)
    }
}