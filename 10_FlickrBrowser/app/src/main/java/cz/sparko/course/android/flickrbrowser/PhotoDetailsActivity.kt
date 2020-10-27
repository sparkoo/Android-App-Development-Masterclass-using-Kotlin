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
      photo_title.text = it.title
      photo_tags.text = it.tags
      Picasso.get().load(it.image)
        .error(R.drawable.image_placeholder)
        .placeholder(R.drawable.image_placeholder)
        .into(photo_image)
    }
  }
}