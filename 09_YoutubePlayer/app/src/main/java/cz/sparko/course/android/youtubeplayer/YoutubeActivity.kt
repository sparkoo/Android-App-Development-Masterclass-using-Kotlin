package cz.sparko.course.android.youtubeplayer

import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as ConstraintLayout
    setContentView(layout)

    val playerView = YouTubePlayerView(this)
    playerView.layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
    layout.addView(playerView)
  }

  override fun onInitializationSuccess(
    p0: YouTubePlayer.Provider?,
    p1: YouTubePlayer?,
    p2: Boolean
  ) {
    TODO("Not yet implemented")
  }

  override fun onInitializationFailure(
    p0: YouTubePlayer.Provider?,
    p1: YouTubeInitializationResult?
  ) {
    TODO("Not yet implemented")
  }
}
