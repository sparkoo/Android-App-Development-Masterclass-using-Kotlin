package cz.sparko.course.android.youtubeplayer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeStandalonePlayer
import kotlinx.android.synthetic.main.activity_standalone.*

private const val TAG = "StandaloneActivity"

class StandaloneActivity : AppCompatActivity(), View.OnClickListener {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_standalone)

    btnPlayVideo.setOnClickListener(this)
    btnPlaylist.setOnClickListener(this)
  }

  override fun onClick(v: View?) {
    val intent = when (v?.id) {
      R.id.btnPlayVideo -> YouTubeStandalonePlayer.createVideoIntent(this, getString(R.string.google_api_key), YOUTUBE_VIDEO_ID, 0, true, false)
      R.id.btnPlaylist -> YouTubeStandalonePlayer.createPlaylistIntent(this, getString(R.string.google_api_key), YOUTUBE_PLAYLIST_ID, 0, 0, true, false)
      else -> throw IllegalArgumentException("Unknown button.")
    }
    startActivity(intent)
  }
}
