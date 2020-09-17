package cz.sparko.course.android.rssreader

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
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

  private val FEED_URL_BUNDLE_KEY = "feedUrl"
  private val FEED_LIMIT_BUNDLE_KEY = "feedLimit"

  private var downloadData: DownloadData? = null

  private var feedUrlFormat =
    "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"
  private var feedLimit = 10

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    Log.d(TAG, "onCreate: first hehe")
    feedUrlFormat = savedInstanceState?.getString(FEED_URL_BUNDLE_KEY, feedUrlFormat) ?: feedUrlFormat
    feedLimit = savedInstanceState?.getInt(FEED_LIMIT_BUNDLE_KEY, feedLimit) ?: feedLimit
    downloadUrl(feedUrlFormat, feedLimit, true)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.feeds_menu, menu)
    when(feedLimit) {
      10 -> menu?.findItem(R.id.menu_10)?.isChecked = true
      25 -> menu?.findItem(R.id.menu_25)?.isChecked = true
    }
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    Log.d(TAG, "onOptionsItemSelected: $item")
    val feedUrlFormatSelect = when (item.itemId) {
      R.id.menu_free -> "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"
      R.id.menu_paid -> "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml"
      R.id.menu_songs -> "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml"
      else -> feedUrlFormat
    }

    val feedLimitSelect = when (item.itemId) {
      R.id.menu_10 -> {
        item.isChecked = true; 10
      }
      R.id.menu_25 -> {
        item.isChecked = true; 25
      }
      else -> feedLimit
    }

    downloadUrl(feedUrlFormatSelect, feedLimitSelect, item.itemId == R.id.menu_refresh)

    this.feedUrlFormat = feedUrlFormatSelect
    this.feedLimit = feedLimitSelect

    return super.onOptionsItemSelected(item)
  }

  private fun downloadUrl(feedUrlFormat: String, feedLimit: Int, force: Boolean = false) {
    Log.d(TAG, "downloadUrl: [${feedUrlFormat.format(feedLimit)}, $force]")
    if (force || feedUrlFormat != this.feedUrlFormat || feedLimit != this.feedLimit) {
      Log.d(TAG, "downloadUrl: downloading !!!")
      downloadData = DownloadData(this, xmlListView)
      downloadData?.execute(feedUrlFormat.format(feedLimit))
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    Log.d(TAG, "onSaveInstanceState: saving [$feedUrlFormat] [$feedLimit]")
    outState.putString(FEED_URL_BUNDLE_KEY, feedUrlFormat)
    outState.putInt(FEED_LIMIT_BUNDLE_KEY, feedLimit)
  }

  override fun onDestroy() {
    super.onDestroy()
    downloadData?.cancel(true)
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

        listView.adapter = FeedAdapter(context, R.layout.list_record, apps)
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
