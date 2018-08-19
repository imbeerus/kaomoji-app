package com.lockwood.kaomoji.extensions

import android.support.v4.app.Fragment
import com.lockwood.kaomoji.domain.model.Kaomoji
import com.lockwood.kaomoji.domain.model.KaomojiList

private val LOG_PREFIX = "kaomoji_"
private val LOG_PREFIX_LENGTH = LOG_PREFIX.length
private val MAX_LOG_TAG_LENGTH = 23

val Any.TAG: String
    get() {
        val className = this::class.java.simpleName
        if (className.length > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + className.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1)
        }
        return LOG_PREFIX + className
    }

val Any.fakeDataSet: KaomojiList
    get() {
        return KaomojiList(0, "cat", "cat cat cat", arrayListOf(
                Kaomoji(0, "=^._.^= ∫", true),
                Kaomoji(1, "=^._.^= ∫", false),
                Kaomoji(2, "=^._.^= ∫", false),
                Kaomoji(3, "=^._.^= ∫", false),
                Kaomoji(4, "=^._.^= ∫", false),
                Kaomoji(5, "=^._.^= ∫", false),
                Kaomoji(6, "=^._.^= ∫", false),
                Kaomoji(7, "=^._.^= ∫", false),
                Kaomoji(8, "=^._.^= ∫", true),
                Kaomoji(9, "=^._.^= ∫", false),
                Kaomoji(10, "=^._.^= ∫", false)
        ))
    }