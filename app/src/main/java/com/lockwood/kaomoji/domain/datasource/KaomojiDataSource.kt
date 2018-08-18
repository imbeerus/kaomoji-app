package com.lockwood.kaomoji.domain.datasource

import com.lockwood.kaomoji.domain.model.Kaomoji
import com.lockwood.kaomoji.domain.model.KaomojiList

interface KaomojiDataSource {

    fun requestKaomojiByTypeId(typeId: Long): KaomojiList?

    fun requestKaomojiByType(type: String): KaomojiList?

    fun requestItemKaomoji(id: Long): Kaomoji?

    fun requestItemKaomojiByFavorite(isFavorite: Boolean): Kaomoji?
}