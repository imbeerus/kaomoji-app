package com.lockwood.kaomoji.domain.commands

import com.lockwood.kaomoji.domain.datasource.KaomojiProvider
import com.lockwood.kaomoji.domain.model.Kaomoji

class RequestKaomojiCommand(
        private val id: Long,
        private val kaomojiProvider: KaomojiProvider = KaomojiProvider()) :
        Command<Kaomoji> {

    override fun execute() = kaomojiProvider.requestItemKaomoji(id)
}