package com.lockwood.kaomoji.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.util.Log
import com.lockwood.kaomoji.App
import com.lockwood.kaomoji.extensions.TAG
import com.lockwood.kaomoji.extensions.copyAsset
import com.lockwood.kaomoji.extensions.isFileExist
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import java.io.File
import java.io.IOException

class KaomojiDbHelper(private val ctx: Context = App.instance) : ManagedSQLiteOpenHelper(ctx,
        KaomojiDbHelper.DB_NAME, null, KaomojiDbHelper.DB_VERSION) {

    override fun onCreate(p0: SQLiteDatabase?) {
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        File(DB_PATH + DB_NAME).delete()
        openDatabase()
    }

    fun openDatabase() {
        if (!checkDataBase()) {
            try {
                val checkDB = ctx.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null)
                checkDB?.close()
                copyDBFile()
            } catch (e: IOException) {
                Log.e(TAG, e.message)
            }
        }
        SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE)
    }


    private fun copyDBFile() = ctx.copyAsset(DB_NAME, DB_PATH + DB_NAME)

    private fun checkDataBase(): Boolean = ctx.isFileExist(DB_PATH + DB_NAME)

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