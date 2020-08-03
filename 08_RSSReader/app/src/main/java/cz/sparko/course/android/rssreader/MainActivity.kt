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

    Log.d(TAG, "onCreate: start")
    val downloadData = DownloadData()
    downloadData.execute("URL goes here")
    Log.d(TAG, "onCreate: done")
  }

  companion object {
    private class DownloadData : AsyncTask<String, Void, String>() {
      @Suppress("PrivatePropertyName")
      private val TAG = this::class.java.simpleName

      override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        Log.d(TAG, "onPostExecute: parameter is $result")
      }

      override fun doInBackground(vararg params: String?): String {
        Log.d(TAG, "doInBackground: starts with ${params[0]}")
        return "doInBackground completed"
      }

    }
  }
}