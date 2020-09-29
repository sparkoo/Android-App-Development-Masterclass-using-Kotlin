package cz.sparko.course.android.youtubeplayer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    btnPlaySingle.setOnClickListener(this)
    btnStandalone.setOnClickListener(this)
  }

  override fun onClick(v: View?) {
    val intent = when(v?.id) {
      R.id.btnPlaySingle -> Intent(this, YoutubeActivity::class.java)
      R.id.btnStandalone -> Intent(this, StandaloneActivity::class.java)
      else -> throw IllegalArgumentException("Unknown button.")
    }
    startActivity(intent)
  }
}