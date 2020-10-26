package cz.sparko.course.android.flickrbrowser

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

private const val TAG = "BaseActivity"

internal const val FLICKR_QUERY = "FLICKR_QUERY"
internal const val PHOTO_TRANSFER = "PHOTO_TRANSFER"

open class BaseActivity : AppCompatActivity() {
  internal fun activateToolbar(enableHome: Boolean) {
    Log.d(TAG, "activateToolbar: ")

    setSupportActionBar(findViewById<View>(R.id.toolbar) as Toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
  }
}