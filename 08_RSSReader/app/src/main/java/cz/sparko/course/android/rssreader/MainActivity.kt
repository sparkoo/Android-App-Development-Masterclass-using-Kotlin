package cz.sparko.course.android.rssreader

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
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
                    val connection = URL(urlPath).openConnection() as HttpURLConnection
                    Log.d(
                        TAG,
                        "downloadXML: response code from [$urlPath] was [${connection.responseCode}]"
                    )
                    val xmlContentBuilder = StringBuilder()
                    connection.inputStream.buffered().reader()
                        .use { xmlContentBuilder.append(it.readText()) }
                    return xmlContentBuilder.toString()
                } catch (e: Exception) {
                    when (e) {
                        is MalformedURLException -> Log.e(
                            TAG,
                            "downloadXML: Wrong URL to download [${e.message}]]",
                            e
                        )
                        is IOException -> Log.e(
                            TAG,
                            "downloadXML: Some IO failed [${e.message}]]",
                            e
                        )
                        is SecurityException -> Log.e(
                            TAG,
                            "downloadXML: Missing permissions [${e.message}]]",
                            e
                        )
                        else -> Log.e(TAG, "downloadXML: Some other failure [${e.message}]", e)
                    }
                }
                return ""
            }
        }
    }
}