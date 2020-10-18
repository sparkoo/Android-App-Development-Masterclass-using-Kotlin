package cz.sparko.course.android.flickrbrowser

import android.os.AsyncTask
import android.util.Log
import cz.sparko.course.android.flickrbrowser.DownloadStatus.*
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

private const val TAG = "GetRawData"

class GetRawData(val callback: OnDownloadComplete) : AsyncTask<String, Void, String>() {
  private var downloadStatus = IDLE

  override fun doInBackground(vararg params: String?): String {
    val url = params[0]
    if (url == null) {
      downloadStatus = NOT_INITIALIZED
      return "No URL set"
    }

    return try {
      downloadStatus = OK
      Log.d(TAG, "doInBackground: downloading '$url'")
      URL(url).readText()
    } catch (e: Exception) {
      downloadStatus = when(e) {
        is MalformedURLException -> NOT_INITIALIZED
        is IOException -> FAILED_OR_EMPTY
        is SecurityException -> PERMISSIONS_ERROR
        else -> ERROR
      }
      Log.e(TAG, "doInBackground: ", e)
      e.message.toString()
    }
  }

  override fun onPostExecute(result: String) {
    callback.onDownloadComplete(result, downloadStatus)
  }

  interface OnDownloadComplete {
    fun onDownloadComplete(data: String, status: DownloadStatus)
  }
}

enum class DownloadStatus {
  OK, IDLE, NOT_INITIALIZED, FAILED_OR_EMPTY, PERMISSIONS_ERROR, ERROR
}