package com.lockwood.kaomoji.ui.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import com.lockwood.kaomoji.R


class SwitchImage : ImageView {

    private lateinit var falseImage: Drawable
    private lateinit var trueImage: Drawable

    private var isChecked: Boolean = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                    attrs,
                    R.styleable.SwitchImage,
                    0, 0)

            try {
                trueImage = a.getDrawable(R.styleable.SwitchImage_si_trueImage)
                falseImage = a.getDrawable(R.styleable.SwitchImage_si_falseImage)
            } finally {
                a.recycle()
            }
        }
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

    fun initState(value: Boolean) {
        isChecked = value
    }
}