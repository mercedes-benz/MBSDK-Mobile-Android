package com.daimler.mbmobilesdk.utils.bindings

import android.media.MediaPlayer
import android.net.Uri
import android.widget.VideoView
import androidx.databinding.BindingAdapter

@BindingAdapter("videoUri")
fun setVideoUri(view: VideoView, uri: String) {
    view.setVideoURI(Uri.parse(uri))
}

@BindingAdapter("isPlaying")
fun setVideoPlaying(view: VideoView, isPlaying: Boolean) {
    if (isPlaying && view.isPlaying.not()) {
        view.start()
    } else if (isPlaying.not() && view.isPlaying) {
        view.stopPlayback()
    }
}

@BindingAdapter("onPrepared")
fun setOnPreparedListener(view: VideoView, onPrepared: MediaPlayer.OnPreparedListener) {
    view.setOnPreparedListener(onPrepared)
}