package cz.sparko.course.android.youtubeplayer

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

const val YOUTUBE_VIDEO_ID = "1CR0QmCaMTs"
const val YOUTUBE_PLAYLIST_ID = "RDCMUCHOtaAJCOBDUWIcL4372D9A"

private const val TAG = "YoutubeActivity"

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as ConstraintLayout
    setContentView(layout)

    val playerView = YouTubePlayerView(this)
    playerView.layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
    layout.addView(playerView)

    playerView.initialize(getString(R.string.google_api_key), this)
  }

  override fun onInitializationSuccess(
    provider: YouTubePlayer.Provider?,
    player: YouTubePlayer?,
    wasRestored: Boolean
  ) {
    Log.d(TAG, "onInitializationSuccess: provider is ${provider?.javaClass}")
    Log.d(TAG, "onInitializationSuccess: player is ${player?.javaClass}")
    Toast.makeText(this, "Initialized YoutubePlayer successfully", Toast.LENGTH_SHORT).show()

    if (!wasRestored) {
      player?.cueVideo(YOUTUBE_VIDEO_ID)
    }
  }

  override fun onInitializationFailure(
    provider: YouTubePlayer.Provider?,
    youTubeInitializationResult: YouTubeInitializationResult?
  ) {
    val REQUEST_CODE = 0

    if (youTubeInitializationResult?.isUserRecoverableError == true) {
      youTubeInitializationResult.getErrorDialog(this, REQUEST_CODE)?.show()
    } else {
      Toast.makeText(
        this,
        "Error initializing the YoutubePlayer [$youTubeInitializationResult]",
        Toast.LENGTH_LONG
      ).show()
    }
  }
}
