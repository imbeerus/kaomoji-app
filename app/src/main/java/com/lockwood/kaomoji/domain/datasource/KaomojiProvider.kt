package com.lockwood.kaomoji.domain.datasource

import com.lockwood.kaomoji.data.KaomojiDb
import com.lockwood.kaomoji.domain.model.Kaomoji
import com.lockwood.kaomoji.domain.model.KaomojiList
import com.lockwood.kaomoji.extensions.firstResult

class KaomojiProvider(private val sources: List<KaomojiDataSource> = KaomojiProvider.SOURCES) {

    companion object {
        val SOURCES by lazy { listOf(KaomojiDb()) }
    }

    fun requestFavoriteItemKaomoji(): KaomojiList = requestToSources { it.requestFavoriteItemKaomoji() }

    fun requestAllItemKaomoji(offset: Int): KaomojiList = requestToSources { it.requestAllItemKaomoji(offset) }

    fun requestKaomojiByType(type: String): KaomojiList = requestToSources { it.requestKaomojiByType(type) }

    fun requestItemKaomoji(id: Long): Kaomoji = requestToSources { it.requestItemKaomoji(id) }

    private fun <T : Any> requestToSources(f: (KaomojiDataSource) -> T?): T = sources.firstResult { f(it) }

}