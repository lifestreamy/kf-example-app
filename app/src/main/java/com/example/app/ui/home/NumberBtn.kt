package com.example.app.ui.home

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import com.balysv.materialripple.MaterialRippleLayout
import com.example.app.R
import com.example.app.utils.Toaster

class NumberBtn : RelativeLayout {

    private lateinit var textView: TextView
    private lateinit var ripple: MaterialRippleLayout

    var onClick: ((String) -> Unit)? = null

    private var number: String = ""


    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor (context: Context) : super(context)

    private fun init(attrs: AttributeSet) {

        inflate(context, R.layout.widget_button, this)
        this.textView = findViewById(R.id.tvButton)
        this.ripple = findViewById(R.id.rpButton)


        val array = context.obtainStyledAttributes(attrs, R.styleable.NumberBtn)
        this.number = array.getString(R.styleable.NumberBtn_value) ?: ""

        array.recycle()

        ripple.setOnClickListener {
            if (this.number.isEmpty())
                Toaster.I()?.show("Ошбика: не задано значение кнопки!")
            else
                onClick?.invoke(this.number)
        }

        textView.text = this.number

    }
}