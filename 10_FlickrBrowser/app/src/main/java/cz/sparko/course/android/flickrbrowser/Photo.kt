package cz.sparko.course.android.flickrbrowser

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
  val title: String,
  val author: String,
  val authorId: String,
  val link: String,
  val tags: String,
  val image: String
) : Parcelable
