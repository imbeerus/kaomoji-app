package com.lockwood.kaomoji.domain.datasource

import com.lockwood.kaomoji.domain.model.Kaomoji
import com.lockwood.kaomoji.domain.model.KaomojiList

interface KaomojiDataSource {

    fun requestAllItemKaomoji(offset: Int): KaomojiList?

    fun requestFavoriteItemKaomoji(): KaomojiList?

    fun requestKaomojiByType(type: String): KaomojiList?

    fun requestItemKaomoji(id: Long): Kaomoji?
}