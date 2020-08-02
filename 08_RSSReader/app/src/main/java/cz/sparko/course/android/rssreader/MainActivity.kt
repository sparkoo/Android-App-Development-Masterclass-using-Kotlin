package cz.sparko.course.android.rssreader

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


class MainActivity : AppCompatActivity() {
  @Suppress("PrivatePropertyName")
  private val TAG = this::class.java.simpleName

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    Log.d(TAG, "onCreate: ")
  }

  private inner class DownloadData : AsyncTask<String, Void, String>() {
    private val TAG = this::class.java.simpleName

    override fun onPostExecute(result: String?) {
      super.onPostExecute(result)
    }

    override fun doInBackground(vararg params: String?): String {
      TODO("Not yet implemented")
    }

  }
}