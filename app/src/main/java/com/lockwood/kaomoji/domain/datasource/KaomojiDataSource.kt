package com.lockwood.kaomoji.domain.datasource

import com.lockwood.kaomoji.domain.model.Kaomoji
import com.lockwood.kaomoji.domain.model.KaomojiList

interface KaomojiDataSource {

    fun requestKaomojiByTypeId(typeId: Long): KaomojiList?

    fun requestItemKaomoji(id: Long): Kaomoji?

}