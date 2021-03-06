package cz.sparko.course.android.flickrbrowser

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

private const val TAG = "FlickrRecyclerViewAdapt"

class FlickrRecyclerViewAdapter(private var photos: List<Photo>) :
  RecyclerView.Adapter<FlickrImageViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
    Log.d(TAG, "onCreateViewHolder: ")
    return FlickrImageViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)
    )
  }

  override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {
    if (photos.isEmpty()) {
      holder.thumbnail.setImageResource(R.drawable.image_placeholder)
      holder.title.setText(R.string.empty_photo)
    } else {
      val photoItem = getPhoto(position)
      Picasso.get().load(photoItem?.image)
        .error(R.drawable.image_placeholder)
        .placeholder(R.drawable.image_placeholder)
        .into(holder.thumbnail)
      holder.title.text = photoItem?.title ?: "Photo not found"
    }
  }

  fun loadNewData(newPhotos: List<Photo>) {
    photos = newPhotos
    notifyDataSetChanged()
  }

  fun getPhoto(position: Int): Photo? {
    return if (photos.size >= position) {
      photos[position]
    } else {
      null
    }
  }

  override fun getItemCount(): Int {
    return if (photos.isNotEmpty()) photos.size else 1
  }
}

class FlickrImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  val thumbnail: ImageView = view.findViewById(R.id.imageThumbnail)
  val title: TextView = view.findViewById(R.id.imageTitle)
}
