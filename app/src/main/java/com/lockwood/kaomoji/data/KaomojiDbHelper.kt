package com.lockwood.kaomoji.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import com.lockwood.kaomoji.App
import com.lockwood.kaomoji.extensions.copyAsset
import com.lockwood.kaomoji.extensions.isFileExist
import org.jetbrains.anko.db.*
import java.io.File

class KaomojiDbHelper(private val ctx: Context = App.instance) : ManagedSQLiteOpenHelper(ctx,
        KaomojiDbHelper.DB_NAME, null, KaomojiDbHelper.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        if (!checkDataBase()) {
            copyDBFile()
        }
//        db.createTable(TypeKaomojiTable.NAME, true,
//                TypeKaomojiTable.ID to INTEGER + PRIMARY_KEY,
//                TypeKaomojiTable.TYPE to TEXT,
//                TypeKaomojiTable.DESCRIPTION to TEXT)
//
//        db.createTable(ItemKaomojiTable.NAME, true,
//                ItemKaomojiTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
//                ItemKaomojiTable.TEXT to TEXT,
//                ItemKaomojiTable.IS_FAVORITE to INTEGER,
//                ItemKaomojiTable.TYPE_ID to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.dropTable(TypeKaomojiTable.NAME, true)
//        db.dropTable(ItemKaomojiTable.NAME, true)
        File(DB_PATH + DB_NAME).delete()
        onCreate(db)
    }

    fun copyDBFile() = ctx.copyAsset(DB_NAME, DB_PATH + DB_NAME)

    fun checkDataBase(): Boolean = ctx.isFileExist(DB_PATH + DB_NAME)

    companion object {
        const val DB_NAME = "kaomoji.db"
        const val DB_VERSION = 1
        val DB_PATH: String
            get() {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    App.instance.dataDir.path + "/databases/"
                } else {
                    App.instance.filesDir.path.dropLast(5) + "/databases/"
                }
            }
        val instance by lazy { KaomojiDbHelper() }
    }
}