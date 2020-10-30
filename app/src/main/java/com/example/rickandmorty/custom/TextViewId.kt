package com.example.rickandmorty.custom

import android.content.Context
import android.util.AttributeSet
import com.example.rickandmorty.R

class TextViewId(context: Context, attrs: AttributeSet): androidx.appcompat.widget.AppCompatTextView(context, attrs) {
    var attributes = context.obtainStyledAttributes(attrs,
        R.styleable.TextViewId
    )
    var textViewId = attributes.getInt(R.styleable.TextViewId_id, -1)

    init {
        attributes.recycle()
    }
}
