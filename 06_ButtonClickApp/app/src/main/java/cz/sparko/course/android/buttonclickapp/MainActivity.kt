package cz.sparko.course.android.buttonclickapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

val TAG = MainActivity::class.java.name

class MainActivity : AppCompatActivity() {
  var timesClicked: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d(TAG, "onCreate: ")
    setContentView(R.layout.activity_main)

    textView.text = ""
    textView.movementMethod = ScrollingMovementMethod()
    button.setOnClickListener {
      textView.append("pico [${timesClicked++}] kokotsrac ${editText.text}\n")
      editText.text.clear()
    }
  }



  override fun onResume() {
    super.onResume()
    println("onResume")
  }

  override fun onPause() {
    super.onPause()
    println("onPause")
  }

  override fun onStart() {
    super.onStart()
    println("onStart")
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
    Log.d(TAG, "onRestoreInstanceState: getting ${textView.id}")
    textView.text = savedInstanceState.getCharSequence(textView.id.toString())
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    Log.d(TAG, "onSaveInstanceState: saving ${textView.text} to ${textView.id.toString()}")
    outState.putCharSequence(textView.id.toString(), textView.text)
  }

  override fun onStop() {
    super.onStop()
    println("onStop")
  }

  override fun onDestroy() {
    super.onDestroy()
    println("onDestroy")
  }
}
