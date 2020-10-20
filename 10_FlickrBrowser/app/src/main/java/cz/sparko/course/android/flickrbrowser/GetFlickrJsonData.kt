package cz.sparko.course.android.flickrbrowser

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject

private const val TAG = "GetFlickrJsonData"

class GetFlickrJsonData(private val listener: OnDataAvailable) :
  AsyncTask<String, Void, ArrayList<Photo>>() {

  override fun doInBackground(vararg params: String): ArrayList<Photo> {
    Log.d(TAG, "doInBackground: ")

    val photos = ArrayList<Photo>()
    try {
      val items = JSONObject(params[0]).getJSONArray("items")
      for (i in 0 until items.length()) {
        photos.add(parsePhoto(items.getJSONObject(i)))
      }
    } catch (e: Exception) {
      Log.e(TAG, "doInBackground: Failed parsing photos", e)
      cancel(true)
      listener.onError(e)
    }
    return photos
  }

  private fun parsePhoto(jsonObject: JSONObject): Photo {
    val photoUrl = jsonObject.getJSONObject("media").getString("m")
      .replaceFirst("_m.jpg", "_b.jpg")
    return Photo(
      jsonObject.getString("title"),
      jsonObject.getString("author"),
      jsonObject.getString("author_id"),
      jsonObject.getString("link"),
      jsonObject.getString("tags"),
      photoUrl
    )
  }

  override fun onPostExecute(result: ArrayList<Photo>) {
    Log.d(TAG, "onPostExecute: ")
    listener.onDataAvailable(result)
  }

  interface OnDataAvailable {
    fun onDataAvailable(data: List<Photo>)
    fun onError(e: Exception)
  }
}