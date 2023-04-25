package com.example.deliveryapp.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.deliveryapp.R

//custom view for quantity picker
class QuantityPicker (
    context : Context,
    attrs : AttributeSet?
        ) : LinearLayout(context, attrs) {

            private val minValue = 0
            private val step = 1
            var currentValue = 1

            val btnLess: Button
            val btnMore: Button

            //constructeur
            init {
                LayoutInflater.from(context).inflate(R.layout.quantitypicker, this, true)
                btnLess = findViewById(R.id.btn_less)
                btnMore = findViewById(R.id.btn_more)
                setValue()
            }
            fun increaseQuantity()  {
                currentValue += step
                setValue()
            }

            fun decreaseQuantity()  {
                if(currentValue - step >= minValue) {
                    currentValue -= step
                    setValue()
                }
            }

            fun setValue() {
                findViewById<TextView>(R.id.quantity).setText(currentValue.toString())
            }


}