package cz.sparko.course.android.flickrbrowser

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cz.sparko.course.android.flickrbrowser.DownloadStatus.OK
import kotlinx.android.synthetic.main.content_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), GetRawData.OnDownloadComplete,
  GetFlickrJsonData.OnDataAvailable, RecyclerItemClickListener.OnRecyclerClickListener {

  private val flickrRecyclerViewAdapter = FlickrRecyclerViewAdapter(emptyList())

  override fun onCreate(savedInstanceState: Bundle?) {
    Log.d(TAG, "onCreate: called")
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(findViewById(R.id.toolbar))

    recycler_view.layoutManager = LinearLayoutManager(this)
    recycler_view.addOnItemTouchListener(RecyclerItemClickListener(this, recycler_view, this))
    recycler_view.adapter = flickrRecyclerViewAdapter

    val getRawData = GetRawData(this)
    getRawData.execute(
      createUri(
        "https://api.flickr.com/services/feeds/photos_public.gne",
        "android,oreo",
        "en-us",
        true
      )
    )

    Log.d(TAG, "onCreate: ends")
  }

  override fun onItemClick(view: View, position: Int) {
    Log.d(TAG, "onItemClick: $position")
    Toast.makeText(this, "Clicked on [$position]", Toast.LENGTH_SHORT).show()
  }

  override fun onItemLongClick(view: View, position: Int) {
    Log.d(TAG, "onItemLongClick: $position")
    Toast.makeText(this, "Long clicked on [$position]", Toast.LENGTH_SHORT).show()
  }

  private fun createUri(baseUrl: String, tags: String, lang: String, matchAll: Boolean): String {
    return Uri.parse(baseUrl).buildUpon()
      .appendQueryParameter("tags", tags)
      .appendQueryParameter("tagmode", if (matchAll) "ALL" else "ANY")
      .appendQueryParameter("lang", lang)
      .appendQueryParameter("format", "json")
      .appendQueryParameter("nojsoncallback", "1")
      .build()
      .toString()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    Log.d(TAG, "onCreateOptionsMenu: called")
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    Log.d(TAG, "onOptionsItemSelected: called")
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    return when (item.itemId) {
      R.id.action_settings -> true
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onDownloadComplete(data: String, status: DownloadStatus) {
    Log.d(TAG, "onDownloadComplete: status [$status]")
    if (status == OK) {
      GetFlickrJsonData(this).execute(data)
    } else {
      Log.e(TAG, "onDownloadComplete: status not OK, don't even try to parse")
    }
    Log.d(TAG, "onDownloadComplete: called ?")
  }

  override fun onDataAvailable(data: List<Photo>) {
    Log.v(TAG, "onDataAvailable: $data")
    flickrRecyclerViewAdapter.loadNewData(data)
  }

  override fun onError(e: Exception) {
  }
}