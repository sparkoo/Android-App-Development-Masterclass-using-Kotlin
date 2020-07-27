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
    private var resultHolder: Double = .0
    private var newNumberHolder: Double = .0

    private val numberButtons by lazy {
        setOf<Button>(key_0, key_1, key_2, key_3, key_4, key_5, key_6, key_7, key_8, key_9, key_dot)
    }
    private val opsButtons by lazy {
        setOf<Button>(
            key_plus,
            key_minus,
            key_multiply,
            key_divide,
            key_equals
        )
    }

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
            newNumberHolder = if (newNumber.text.toString() == ".") {
                0.0
            } else {
                newNumber.text.toString().toDouble()
            }
        }

        val opOnClickListener = View.OnClickListener { v ->
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

        numberButtons.forEach { b -> b.setOnClickListener(numberOnClickListener) }
        opsButtons.forEach { b -> b.setOnClickListener(opOnClickListener) }

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