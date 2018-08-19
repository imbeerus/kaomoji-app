package com.lockwood.kaomoji.domain.commands

import com.lockwood.kaomoji.domain.datasource.KaomojiProvider
import com.lockwood.kaomoji.domain.model.KaomojiList

class RequestHomeKaomojiCommand(
        private val type: String,
        private val kaomojiProvider: KaomojiProvider = KaomojiProvider()) :
        Command<KaomojiList> {

    companion object {
        const val LIST_DESCRIPTION = "Contains default kaomojis"
        const val LIST_TYPE = "Home"
    }

    override fun execute() = kaomojiProvider.requestKaomojiByType(type).apply {
        type = LIST_TYPE
        description = LIST_DESCRIPTION
    }
}