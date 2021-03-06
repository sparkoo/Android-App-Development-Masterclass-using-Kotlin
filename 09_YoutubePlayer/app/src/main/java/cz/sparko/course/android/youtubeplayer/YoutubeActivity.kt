package cz.sparko.course.android.youtubeplayer

import android.content.Intent
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
const val YOUTUBE_PLAYLIST_ID = "PLN26ZvUvDxVTWBGo0w4ICWPU6-7BZb7Kb"

private const val TAG = "YoutubeActivity"

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
  private val DIALOG_REQUEST_CODE = 1

  val playerView by lazy { YouTubePlayerView(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as ConstraintLayout
    setContentView(layout)

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

    player?.setPlayerStateChangeListener(playerStateChangeListener)
    player?.setPlaybackEventListener(playbackEventListener)

    if (!wasRestored) {
      player?.loadVideo(YOUTUBE_VIDEO_ID)
    } else {
      player?.play()
    }
  }

  override fun onInitializationFailure(
    provider: YouTubePlayer.Provider?,
    youTubeInitializationResult: YouTubeInitializationResult?
  ) {
    if (youTubeInitializationResult?.isUserRecoverableError == true) {
      youTubeInitializationResult.getErrorDialog(this, DIALOG_REQUEST_CODE)?.show()
    } else {
      Toast.makeText(
        this,
        "Error initializing the YoutubePlayer [$youTubeInitializationResult]",
        Toast.LENGTH_LONG
      ).show()
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    Log.d(TAG, "onActivityResult: request: [$requestCode] result: [$resultCode]")

    if (requestCode == DIALOG_REQUEST_CODE) {
      Log.d(TAG, "onActivityResult: ${intent?.toString()}")
      Log.d(TAG, "onActivityResult: ${intent?.extras.toString()}")
      playerView.initialize(getString(R.string.google_api_key), this)
    }
  }

  private val playbackEventListener = object: YouTubePlayer.PlaybackEventListener {
    override fun onPlaying() {
      Toast.makeText(this@YoutubeActivity, "PlaybackEventListener#onPlaying", Toast.LENGTH_SHORT).show()
      Log.d(TAG, "onPlaying: ")
    }

    override fun onStopped() {
      Toast.makeText(this@YoutubeActivity, "PlaybackEventListener#onStopped", Toast.LENGTH_SHORT).show()
      Log.d(TAG, "onStopped: ")
    }

    override fun onPaused() {
      Toast.makeText(this@YoutubeActivity, "PlaybackEventListener#onPaused", Toast.LENGTH_SHORT).show()
      Log.d(TAG, "onPaused: ")
    }

    override fun onBuffering(p0: Boolean) {
      Toast.makeText(this@YoutubeActivity, "PlaybackEventListener#onBuffering($p0)", Toast.LENGTH_SHORT).show()
      Log.d(TAG, "onBuffering: $p0")
    }

    override fun onSeekTo(p0: Int) {
      Toast.makeText(this@YoutubeActivity, "PlaybackEventListener#onSeekTo($p0)", Toast.LENGTH_SHORT).show()
      Log.d(TAG, "onSeekTo: $p0")
    }

  }

  private val playerStateChangeListener = object: YouTubePlayer.PlayerStateChangeListener {
    override fun onLoading() {
      Toast.makeText(this@YoutubeActivity, "PlayerStateChangeListener#onLoading", Toast.LENGTH_SHORT).show()
      Log.d(TAG, "onLoading: ")
    }

    override fun onLoaded(p0: String?) {
      Toast.makeText(this@YoutubeActivity, "PlayerStateChangeListener#onLoaded($p0)", Toast.LENGTH_SHORT).show()
      Log.d(TAG, "onLoaded: $p0")
    }

    override fun onAdStarted() {
      Toast.makeText(this@YoutubeActivity, "PlayerStateChangeListener#onAdStarted", Toast.LENGTH_SHORT).show()
      Log.d(TAG, "onAdStarted: ")
    }

    override fun onVideoStarted() {
      Toast.makeText(this@YoutubeActivity, "PlayerStateChangeListener#onVideoStarted", Toast.LENGTH_SHORT).show()
      Log.d(TAG, "onVideoStarted: ")
    }

    override fun onVideoEnded() {
      Toast.makeText(this@YoutubeActivity, "PlayerStateChangeListener#onVideoEnded", Toast.LENGTH_SHORT).show()
      Log.d(TAG, "onVideoEnded: ")
    }

    override fun onError(p0: YouTubePlayer.ErrorReason?) {
      Toast.makeText(this@YoutubeActivity, "PlayerStateChangeListener#onError($p0)", Toast.LENGTH_SHORT).show()
      Log.d(TAG, "onError: $p0")
    }
  }
}
