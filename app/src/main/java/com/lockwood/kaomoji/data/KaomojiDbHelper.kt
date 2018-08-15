package com.lockwood.kaomoji.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.lockwood.kaomoji.App
import org.jetbrains.anko.db.*

class KaomojiDbHelper(ctx: Context = App.instance) : ManagedSQLiteOpenHelper(ctx,
        KaomojiDbHelper.DB_NAME, null, KaomojiDbHelper.DB_VERSION) {

    companion object {
        const val DB_NAME = "kaomoji.db"
        const val DB_VERSION = 1
        val instance by lazy { KaomojiDbHelper() }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(TypeKaomojiTable.NAME, true,
                TypeKaomojiTable.ID to INTEGER + PRIMARY_KEY,
                TypeKaomojiTable.TYPE to TEXT,
                TypeKaomojiTable.DESCRIPTION to TEXT)

        db.createTable(ItemKaomojiTable.NAME, true,
                ItemKaomojiTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                ItemKaomojiTable.TEXT to TEXT,
                ItemKaomojiTable.IS_FAVORITE to INTEGER,
                ItemKaomojiTable.TYPE_ID to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(TypeKaomojiTable.NAME, true)
        db.dropTable(ItemKaomojiTable.NAME, true)
        onCreate(db)
    }
}