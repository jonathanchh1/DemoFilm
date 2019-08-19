@file:Suppress("DEPRECATION")

package com.emi.nollyfilms.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent.*
import android.widget.Toast
import com.emi.nollyfilms.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_video.*
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.emi.nollyfilms.databinding.ActivityVideoBinding


class VideoActivity : AppCompatActivity() {


    var mYouTubePlayer: YouTubePlayer? = null
    var currentVolume = 3
    private var youtube_video_id : String = ""
    private var youTubePlayerView : YouTubePlayerView?=null
    var isLoading = MutableLiveData(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val binding : ActivityVideoBinding = DataBindingUtil.setContentView(this, R.layout.activity_video)
        binding.activity = this
        binding.lifecycleOwner = this
        youtube_video_id = intent.getStringExtra(getString(R.string.videourl)) as String
        isLoading.value = true
        youTubePlayerView = findViewById<YouTubePlayerView>(R.id.youtube_player_view)
        youTubePlayerView?.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                mYouTubePlayer = youTubePlayer
                    youTubePlayer.loadVideo(youtube_video_id, 0f)
                    youTubePlayer.setVolume(currentVolume)
                   isLoading.value = false
                }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                super.onError(youTubePlayer, error)
                finish()
                overridePendingTransition(0, 0)
                startActivity(intent)
                overridePendingTransition(0, 0)
               isLoading.value = true
            }
        })

        emptyView.setOnClickListener {
            Toast.makeText(baseContext,"Clicked!", Toast.LENGTH_SHORT).show()
        }

        exit.setOnClickListener {
            youTubePlayerView?.release()
            finish()
        }

    }


    override fun onResume() {
        super.onResume()
        getOrientation()
    }

    fun refresh(swipeRefreshLayout: SwipeRefreshLayout){
        youTubePlayerView?.postInvalidate()
        swipeRefreshLayout.isRefreshing = false
        mYouTubePlayer?.play()
    }
    override fun onDestroy() {
        super.onDestroy()
        youTubePlayerView?.clearFocus()
        youTubePlayerView?.release()
    }

    fun getOrientation() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (!youTubePlayerView?.isFullScreen()!!) {
                youTubePlayerView?.enterFullScreen()
            }
        }
    }
    override fun onStop() {
        super.onStop()
        youTubePlayerView?.release()
        mYouTubePlayer?.pause()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KEYCODE_VOLUME_DOWN) {
            if(currentVolume>=10) {
                currentVolume -= 10
                mYouTubePlayer?.setVolume(currentVolume)
            }
        }else  if (keyCode == KEYCODE_VOLUME_UP) {
            if(currentVolume<=90) {
                currentVolume += 10
                mYouTubePlayer?.setVolume(currentVolume)
            }
        } else if(keyCode == KEYCODE_BACK){
            finish()
        }
        return true
    }


}

