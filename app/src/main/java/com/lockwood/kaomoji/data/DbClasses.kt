package com.lockwood.kaomoji.data

class TypeKaomoji(val map: MutableMap<String, Any?>, val kaomojiList: List<ItemKaomoji>) {
    var _id: Long by map
    var type: String by map
    var description: String by map

    constructor(id: Long, type: String, description: String, kaomojiList: List<ItemKaomoji>)
            : this(HashMap(), kaomojiList) {
        this._id = id
        this.type = type
        this.description = description
    }
}

class ItemKaomoji(var map: MutableMap<String, Any?>) {
    var _id: Long by map
    var text: String by map
    var isFavorite: Long by map
    var typeId: Long by map

    constructor(text: String, isFavorite: Long, typeId: Long)
            : this(HashMap()) {
        this.text = text
        this.isFavorite = isFavorite
        this.typeId = typeId
    }
}