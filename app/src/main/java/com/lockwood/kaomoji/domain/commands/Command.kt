package com.lockwood.kaomoji.domain.commands

interface Command<out T> {
    fun execute(): T
}