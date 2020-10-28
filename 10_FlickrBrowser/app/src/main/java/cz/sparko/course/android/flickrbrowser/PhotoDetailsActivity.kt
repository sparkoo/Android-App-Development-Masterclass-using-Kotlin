package cz.sparko.course.android.flickrbrowser

import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_photo_details.*

private const val TAG = "PhotoDetailsActivity"

class PhotoDetailsActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    Log.d(TAG, "onCreate: ")
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_photo_details)
    activateToolbar(true)

    intent.getParcelableExtra<Photo>(PHOTO_TRANSFER)?.let {
      Log.d(TAG, "onCreate: transfered photo [$it]")
      photo_author.text = resources.getString(R.string.photo_author_text, it.author)
      photo_title.text = resources.getString(R.string.photo_title_text, it.title)
      photo_tags.text = resources.getString(R.string.photo_tags_text, it.tags)
      Picasso.get().load(it.image)
        .error(R.drawable.image_placeholder)
        .placeholder(R.drawable.image_placeholder)
        .into(photo_image)
    }
  }
}