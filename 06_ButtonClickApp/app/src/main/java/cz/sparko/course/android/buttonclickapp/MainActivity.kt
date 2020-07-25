package cz.sparko.course.android.buttonclickapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  var timesClicked: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    textView.text = ""
    textView.movementMethod = ScrollingMovementMethod()
    button.setOnClickListener {
      textView.append("pico [${timesClicked++}] kokotsrac ${editText.text}\n")
    }
  }
}
