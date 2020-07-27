package cz.sparko.course.android.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

val TAG = MainActivity::class.java.simpleName

class MainActivity : AppCompatActivity() {
    var resultHolder: Double = .0
    var newNumberHolder: Double = .0
    var operationHolder: String = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultHolder = savedInstanceState?.getDouble(result.id.toString()) ?: .0
        newNumberHolder = savedInstanceState?.getDouble(newNumber.id.toString()) ?: .0

        operation.text = savedInstanceState?.getString(operation.id.toString()) ?: "="
        showNumbers()

        val numberOnClickListener = View.OnClickListener { v ->
            val pressed = (v as Button).text
            newNumber.append(pressed)
            if (newNumber.text.toString() == ".") {
                newNumberHolder = 0.0
            } else {
                newNumberHolder = newNumber.text.toString().toDouble()
            }
        }

        val opOnClickListener = View.OnClickListener {v ->
            val btn = v as Button
            if (resultHolder == 0.0) {
                resultHolder = newNumberHolder
                newNumberHolder = 0.0
            } else {
                if (operation.text != "=") {
                    Log.d(TAG, "onCreate: ${operation.text} -> ${btn.text}")
                    when (operation.text) {
                        "+" -> resultHolder += newNumberHolder
                        "-" -> resultHolder -= newNumberHolder
                        "*" -> resultHolder *= newNumberHolder
                        "/" -> resultHolder /= newNumberHolder
                    }
                    operation.text = btn.text
                }
                newNumberHolder = 0.0
            }
            operation.text = btn.text
            showNumbers()
        }

        key_0.setOnClickListener(numberOnClickListener)
        key_1.setOnClickListener(numberOnClickListener)
        key_2.setOnClickListener(numberOnClickListener)
        key_3.setOnClickListener(numberOnClickListener)
        key_4.setOnClickListener(numberOnClickListener)
        key_5.setOnClickListener(numberOnClickListener)
        key_6.setOnClickListener(numberOnClickListener)
        key_7.setOnClickListener(numberOnClickListener)
        key_8.setOnClickListener(numberOnClickListener)
        key_9.setOnClickListener(numberOnClickListener)
        key_dot.setOnClickListener(numberOnClickListener)

        key_plus.setOnClickListener(opOnClickListener)
        key_minus.setOnClickListener(opOnClickListener)
        key_divide.setOnClickListener(opOnClickListener)
        key_multiply.setOnClickListener(opOnClickListener)
        key_equals.setOnClickListener(opOnClickListener)

        key_c.setOnClickListener {
            resultHolder = .0
            newNumberHolder = .0
            showNumbers()
        }
    }

    private fun showNumbers() {
        result.text = Editable.Factory.getInstance().newEditable(resultHolder.toString())
        if (newNumberHolder != 0.0) {
            newNumber.text = Editable.Factory.getInstance().newEditable(newNumberHolder.toString())
        } else {
            newNumber.text.clear()
        }
    }
}