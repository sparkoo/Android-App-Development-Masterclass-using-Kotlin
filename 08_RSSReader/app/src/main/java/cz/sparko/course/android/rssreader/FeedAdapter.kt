package cz.sparko.course.android.rssreader

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class FeedAdapter(context: Context, private val resource: Int, private val apps: List<FeedEntry>) :
  ArrayAdapter<FeedEntry>(context, resource, apps) {
  @Suppress("PrivatePropertyName")
  private val TAG = this::class.java.simpleName
  private val inflater = LayoutInflater.from(context)

  override fun getCount(): Int {
    Log.d(TAG, "getCount: [${apps.size}]")
    return apps.size
  }

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val viewHolder: ViewHolder
    val view: View
    if (convertView == null) {
      view = inflater.inflate(resource, parent, false)
      viewHolder = ViewHolder(view)
      view.tag = viewHolder
    } else {
      view = convertView
      viewHolder = view.tag as ViewHolder
    }

    val app = apps[position]
    viewHolder.tvName.text = app.name
    viewHolder.tvArtist.text = app.artist
    viewHolder.tvSummary.text = app.summary

    return view
  }
}


class ViewHolder(v: View) {
  val tvName: TextView = v.findViewById(R.id.tvName)
  val tvArtist: TextView = v.findViewById(R.id.tvArtist)
  val tvSummary: TextView = v.findViewById(R.id.tvSummary)
}
