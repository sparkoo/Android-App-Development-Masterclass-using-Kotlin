package cz.sparko.course.android.rssreader

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import cz.sparko.course.android.rssreader.R.layout.list_item
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

data class FeedEntry(
  val name: String,
  val artist: String,
  val releaseDate: String,
  val summary: String,
  val imageUrl: String


) {
  override fun toString(): String {
    return "FeedEntry(name='$name', artist='$artist', releaseDate='$releaseDate')"
  }
}

class MainActivity : AppCompatActivity() {
  @Suppress("PrivatePropertyName")
  private val TAG = this::class.java.simpleName

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    Log.d(TAG, "onCreate: start")
    val downloadData = DownloadData(this, xmlListView)
    downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=50/xml")
    Log.d(TAG, "onCreate: done")
  }

  companion object {
    private class DownloadData(private val context: Context, private val listView: ListView) :
      AsyncTask<String, Void, String>() {
      @Suppress("PrivatePropertyName")
      private val TAG = this::class.java.simpleName

      override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        Log.d(TAG, "onPostExecute: parameter is $result")

        val apps = ParseApps().parse(result)
        Log.d(TAG, "doInBackground: parsed apps $apps")

        val arrayAdapter = ArrayAdapter<FeedEntry>(context, list_item, apps)
        listView.adapter = arrayAdapter
      }

      override fun doInBackground(vararg url: String?): String {
        Log.d(TAG, "doInBackground: starts with ${url[0]}")
        val xmlContent = downloadXML(url[0])
//        Log.d(TAG, "doInBackground: $xmlContent")

        return xmlContent
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
