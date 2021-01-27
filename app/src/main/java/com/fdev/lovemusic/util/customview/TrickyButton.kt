package com.fdev.lovemusic.util.customview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.fdev.lovemusic.R

class TrickyButton(
    context: Context,
    attributeSet: AttributeSet? = null
) : CardView(context, attributeSet) {


    private var disableBackgroundColor : ColorStateList

    private var normalColor : ColorStateList

    private var isVirtuallyEnable : Boolean = false

    init{
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.TrickyTextField,
            0, 0
        ).apply {
            try{
                disableBackgroundColor = getColorStateList(R.styleable.TrickyButton_onDisableBackgroundColor) ?: ColorStateList.valueOf(
                    Color.GRAY)
                normalColor = cardBackgroundColor
            }finally {
                recycle()
            }
        }
    }

    fun enableButton(){
        isVirtuallyEnable = true
        setCardBackgroundColor(normalColor)
    }

    fun disableButton(){
        isVirtuallyEnable = false
        setCardBackgroundColor(disableBackgroundColor)
    }

    fun setOnTrickyClickListener(
            onEnable : ()  -> Unit = {},
            onDisable : () -> Unit = {}
    ){
        setOnClickListener {
            if(isVirtuallyEnable){
                onEnable()
            }else{
                onDisable()
            }
        }
    }


}