package com.lockwood.kaomoji.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)

fun Context.drawable(res: Int): Drawable? = ContextCompat.getDrawable(this, res)

fun Context.drawable(res: Int, colorRes: Int): Drawable? {
    val drawable = drawable(res)
    drawableColor(drawable, colorRes)
    return drawable
}

fun Context.drawableColor(drawable: Drawable?, colorRes: Int) = with(drawable) {
    this?.let { DrawableCompat.setTint(it, color(colorRes)) }
}

fun Context.copyToClipboard(label: String, text: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.primaryClip = clip
}

fun Context.copyAsset(inFile: String, outFile: String) {
    val inStream = assets.open(inFile)
    val outStream = FileOutputStream(outFile)
    copyFile(inStream, outStream)
}

fun Context.copyFile(inStream: InputStream, outStream: OutputStream) {
    try {
        inStream.copyTo(outStream)
    } finally {
        outStream.flush()
        outStream.close()
        inStream.close()
    }
}

fun Context.isFileExist(path: String): Boolean = File(path).exists()