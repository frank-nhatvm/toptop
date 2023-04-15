package com.nhatvm.toptop.ui.theme.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.REPEAT_MODE_ALL
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.nhatvm.toptop.data.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@UnstableApi
@HiltViewModel
class VideoDetailViewModel @Inject constructor(
    val player: ExoPlayer,
    private val videoRepository: VideoRepository
): ViewModel() {

    private var _uiState = MutableStateFlow<VideoDetailUiState>(VideoDetailUiState.Default)
    val uiState: StateFlow<VideoDetailUiState>
        get() = _uiState


    init {
        player.repeatMode = REPEAT_MODE_ALL
        player.playWhenReady = true
        player.prepare()
    }

    fun processAction(action: VideoDetailAction) {
        when (action) {
            is VideoDetailAction.LoadData -> {
                loadVideo(action.id)
            }
            is VideoDetailAction.ToggleVideo -> {
                toggleVideoPlayer()
            }
            else -> {

            }
        }
    }

    private fun loadVideo(videoId: String) {
        _uiState.value = VideoDetailUiState.Loading
        viewModelScope.launch {
            delay(100L)
            playVideo(videoRepository.getVideo())
            _uiState.value = VideoDetailUiState.Success
        }
    }

    private fun playVideo(videoResourceId: Int) {
        val uri = RawResourceDataSource.buildRawResourceUri(videoResourceId)
        val mediaItem = MediaItem.fromUri(uri)
        player.setMediaItem(mediaItem)
        player.play()
    }

    private fun toggleVideoPlayer() {
        if (player.isLoading) {

        } else
            if (player.isPlaying) {
                player.pause()
            } else {
                player.play()
            }
    }


    override fun onCleared() {
        super.onCleared()
        player.release()
    }

}

sealed interface VideoDetailUiState {
    object Default: VideoDetailUiState
    object Loading: VideoDetailUiState
    object Success: VideoDetailUiState
    data class Error(val msg: String): VideoDetailUiState
}

sealed class VideoDetailAction {
    data class LoadData(val id: String): VideoDetailAction()
    object ToggleVideo: VideoDetailAction()
}