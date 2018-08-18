package com.lockwood.kaomoji.domain.commands

import com.lockwood.kaomoji.domain.datasource.KaomojiProvider
import com.lockwood.kaomoji.domain.model.KaomojiList

class RequestAllKaomojiCommand(
        private val kaomojiProvider: KaomojiProvider = KaomojiProvider()) :
        Command<KaomojiList> {

    companion object {
        const val LIST_DESCRIPTION = "Contains all available kaomojis"
        const val LIST_TYPE = "All"
    }

    override fun execute() = kaomojiProvider.requestAllItemKaomoji().apply {
        type = LIST_TYPE
        description = LIST_DESCRIPTION
    }
}