package com.lockwood.kaomoji.domain

interface KaomojiDataSource {

    fun requestKaomojiByTypeId(typeId: Long): KaomojiList?

    fun requestItemKaomoji(id: Long): Kaomoji?

}