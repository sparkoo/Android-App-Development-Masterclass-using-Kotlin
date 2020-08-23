package cz.sparko.course.android.rssreader

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class ParseApps {
  @Suppress("PrivatePropertyName")
  private val TAG = this::class.java.simpleName

  fun parse(xmlData: String): List<FeedEntry> {
//    Log.d(TAG, "parse: parsing [$xmlData]")
    val apps = ArrayList<FeedEntry>()

    val factory = XmlPullParserFactory.newInstance()
    factory.isNamespaceAware = true

    val xmlParser = factory.newPullParser()
    xmlParser.setInput(xmlData.reader())

    var eventType = xmlParser.eventType
    while (eventType != XmlPullParser.END_DOCUMENT) {
      if (xmlParser.name == null) {
        eventType = xmlParser.next()
        continue
      }
      val tagName = xmlParser.name

      when (eventType) {
        XmlPullParser.START_TAG -> {
          if (tagName == "entry") {
            val entry = parseEntry(xmlParser)
            Log.d(TAG, "parse: parsed $entry")
            apps.add(entry)
          }
        }
      }

      eventType = xmlParser.next()
    }

    return apps
  }

  private fun parseEntry(xmlParser: XmlPullParser): FeedEntry {
    Log.d(TAG, "parseEntry: ")

    var eventType = xmlParser.next()
    var name = ""
    var artist = ""
    var releaseDate = ""
    var summary = ""
    var image = ""

    var currentText = ""
    while (!(eventType == XmlPullParser.END_TAG && xmlParser.name == "entry")) {
      when (eventType) {
        XmlPullParser.TEXT -> currentText = xmlParser.text
        XmlPullParser.END_TAG -> {
          when (xmlParser.name) {
            "name" -> name = currentText
            "artist" -> artist = currentText
            "releaseDate" -> releaseDate = currentText
            "summary" -> summary = currentText
            "image" -> image = currentText
          }
        }
      }
      eventType = xmlParser.next()
    }

    return FeedEntry(name, artist, releaseDate, summary, image)
  }
}