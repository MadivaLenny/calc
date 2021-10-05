package com.example.calc

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.ArithmeticException


class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView
    var lastNumeric: Boolean = false
    var stateError: Boolean =false
    private var lastDot: Boolean =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
    }
    fun onDigit(view: View){
        if (stateError) {
            textView.text = (view as Button).text
            stateError = false
        }
        else{
            textView.append((view as Button).text)
        }
        lastNumeric = true
    }

    fun onOperator(view: View) {
        if (lastNumeric && !stateError) {
            textView.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onDecimalPoint (view: View) {
        if (lastNumeric && !stateError && !lastDot) {
            textView.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onClear(view: View){
        this.textView.text =""
        lastNumeric = false
        stateError = false
        lastDot = false
    }

    fun onEqual(view: View){
        if (lastNumeric && !stateError){
            val txt = textView.text.toString()
            val expression = ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()
                textView.text = result.toString()
                lastDot = true
            } catch (ex: ArithmeticException) {
                textView.text = "Syntax Error"
                stateError = true
                lastNumeric = false
            }
        }
    }

    }
