package cz.sparko.course.android.rssreader

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.net.URL

class MainActivity : AppCompatActivity() {
  @Suppress("PrivatePropertyName")
  private val TAG = this::class.java.simpleName

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    Log.d(TAG, "onCreate: start")
    val downloadData = DownloadData()
    downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
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

      override fun doInBackground(vararg url: String?): String {
        Log.d(TAG, "doInBackground: starts with ${url[0]}")
        val xmlContent = downloadXML(url[0])
        Log.d(TAG, "doInBackground: $xmlContent")
        return "doInBackground completed"
      }

      private fun downloadXML(urlPath: String?): String {
        try {
          return URL(urlPath).readText()
        } catch (e: Exception) {
          Log.e(TAG, "downloadXML: Failed. [${e.message}] [${e.cause}]", e)
        }
        return ""
      }
    }
  }
}
