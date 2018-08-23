package com.lockwood.kaomoji.ui.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import com.lockwood.kaomoji.R

class SwitchImage @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    private lateinit var falseImage: Drawable
    private lateinit var trueImage: Drawable

    private var isChecked: Boolean = false

    init {
        context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SwitchImage,
                0, 0).apply {
            try {
                trueImage = getDrawable(R.styleable.SwitchImage_si_trueImage)
                falseImage = getDrawable(R.styleable.SwitchImage_si_falseImage)
            } finally {
                recycle()
            }
        }
    }

    fun initState(value: Boolean) {
        isChecked = value
    }

    fun checkState() {
        if (isChecked) {
            setImageDrawable(trueImage)
        } else {
            setImageDrawable(falseImage)
        }
    }

    fun changeState() {
        isChecked = !isChecked
        checkState()
    }
}