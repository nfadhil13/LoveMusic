package com.fdev.lovemusic.util.customview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.size
import com.fdev.lovemusic.R
import com.google.android.material.textfield.TextInputEditText


class TrickyTextField(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    private val editText: EditText = EditText(context, attributeSet)
    private val errorText: TextView = TextView(context, attributeSet)
    private var errorTextParams = LayoutParams(
        LayoutParams.MATCH_PARENT,
        LayoutParams.WRAP_CONTENT
    )
    private var editTextParams = LayoutParams(
        LayoutParams.MATCH_PARENT,
        LayoutParams.WRAP_CONTENT
    )


    private var isError: ((editTextValue: String) -> ErrorData) = { ErrorData.NotError }


    private val editTextListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            when(val errorData = isError(s.toString())){
                is ErrorData.NotError -> {
                    onSuccess()
                }
                is ErrorData.Error -> {
                    onError(errorData.textMesage)
                }
            }
        }

    }

    private val cursor  = GradientDrawable()

    private val errorColor: ColorStateList
    private val normalColor: ColorStateList


    init {
        orientation = VERTICAL
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.TrickyTextField,
            0, 0
        ).apply {
            try {

                val textSize = pixelsToSp(getDimension(R.styleable.TrickyTextField_textSize, 0f))

                val cursorSize = getDimension(R.styleable.TrickyTextField_cursorWidth , 2f)

                errorColor = getColorStateList(R.styleable.TrickyTextField_errorTextColor)
                    ?: ColorStateList.valueOf(
                        Color.RED
                    )
                normalColor = getColorStateList(R.styleable.TrickyTextField_backgroundTint) ?: ColorStateList.valueOf(Color.BLACK)



                cursor.apply {
                    shape = GradientDrawable.RECTANGLE
                    color = normalColor
                    setSize(dpToInt(cursorSize), textSize.toInt())
                }

                editText.apply {
                    editText.addTextChangedListener(editTextListener)
                    layoutParams = editTextParams
                    hint = getString(R.styleable.TrickyTextField_hint)
                    this.textSize = textSize
                    setTextColor(normalColor)
                    setHintTextColor(getColorStateList(R.styleable.TrickyTextField_textColorHint))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        textCursorDrawable = cursor
                    }

                    backgroundTintList = normalColor

                }
                errorText.apply {
                    visibility = View.INVISIBLE
                    editTextParams.setMargins(0, dpToInt(16.0f), 0, 0)
                    errorText.setPadding(dpToInt(5.0f), 0, 0, 0)
                    layoutParams = errorTextParams
                    text = getText(R.styleable.TrickyTextField_errorText)
                    setTextColor(errorColor)
                    this.textSize =
                        pixelsToSp(getDimension(R.styleable.TrickyTextField_errorTextSize, 0f))

                }
            } finally {
                recycle()
            }
        }
        addView(editText)
        addView(errorText)
    }


    fun addOnErrorListener(
        isError: (editTextValue: String) -> ErrorData = { ErrorData.NotError }
    ) {
        this.isError = isError
    }

    fun getCurrentText(): String = editText.text.toString()


    fun disposeView() = editText.removeTextChangedListener(editTextListener)


    private fun onError(errorMessage : String) {
        editText.apply {
            backgroundTintList = errorColor
            setTextColor(errorColor)
        }
        errorText.apply{
            visibility = View.VISIBLE
            text = errorMessage
        }
        cursor.apply {
            color = errorColor
        }

    }

    private fun onSuccess() {
        editText.apply {
            backgroundTintList = normalColor
            setTextColor(normalColor)
        }
        errorText.apply{
            visibility = View.INVISIBLE
        }
        cursor.apply {
            color = normalColor
        }

    }

    fun requestError(message : String){
        onError(message)
    }

    private fun dpToInt(dp: Float): Int {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.resources.displayMetrics
        ).toInt()
    }

    private fun pixelsToSp(px: Float): Float {
        val scaledDensity: Float = context.resources.displayMetrics.scaledDensity
        return px / scaledDensity
    }


}

sealed class ErrorData(
    val isError : Boolean = false,
    val textMesage : String = ""
){
    object NotError : ErrorData(false , "")
    data class Error(val message : String) : ErrorData(true ,  message)
}
