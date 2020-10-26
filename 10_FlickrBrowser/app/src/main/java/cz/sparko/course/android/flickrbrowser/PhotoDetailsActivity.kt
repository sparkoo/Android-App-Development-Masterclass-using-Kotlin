package cz.sparko.course.android.flickrbrowser

import android.os.Bundle
import android.util.Log

private const val TAG = "PhotoDetailsActivity"
class PhotoDetailsActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    Log.d(TAG, "onCreate: ")
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_photo_details)
    activateToolbar(true)
  }
}