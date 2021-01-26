package com.fdev.lovemusic.util.customview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.view.children
import com.fdev.lovemusic.R

class TrickyButton(
    context: Context,
    attributeSet: AttributeSet? = null
) : CardView(context, attributeSet) {


    var disableBackgroundColor : ColorStateList

    var normalColor : ColorStateList
//
//    var disableTextColor : ColorStateList?

    init{
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.TrickyTextField,
            0, 0
        ).apply {
            try{
                disableBackgroundColor = getColorStateList(R.styleable.TrickyButton_onDisableBackgroundColor) ?: ColorStateList.valueOf(
                    Color.GRAY)
//                disableTextColor = getColorStateList(R.styleable.TrickyButton_onDisableTextColor)
//
                normalColor = cardBackgroundColor
            }finally {
                recycle()
            }
        }
    }

    fun enableButton(){
        isEnabled = true
        setCardBackgroundColor(normalColor)
    }

    fun disableButton(){
        isEnabled = false
        setCardBackgroundColor(disableBackgroundColor)
    }
}