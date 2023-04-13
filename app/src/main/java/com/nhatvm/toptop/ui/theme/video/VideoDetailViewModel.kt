package com.nhatvm.toptop.ui.theme.video

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoDetailViewModel @Inject constructor(
    val player: Player
): ViewModel() {

    private var _uiState = MutableStateFlow<VideoDetailUiState>(VideoDetailUiState.Default)
    val uiState: StateFlow<VideoDetailUiState>
        get() = _uiState

    init {
        player.prepare()
    }

    fun processAction(action: VideoDetailAction) {
        when (action) {
            is VideoDetailAction.LoadData -> {
                loadVideo(action.id)
            }
            else -> {

            }
        }
    }

    private fun loadVideo(videoId: String) {
        _uiState.value = VideoDetailUiState.Loading
        viewModelScope.launch {
            delay(100L)
//            playVideo("https://storage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4")
            playVideo("https://www.tiktok.com/@khaby.lame/video/7025632079081655558")
            _uiState.value = VideoDetailUiState.Success
        }
    }

    private fun playVideo(videoUrl: String) {
        val uri = Uri.parse(videoUrl)
        val mediaItem = MediaItem.fromUri(uri)
        player.addMediaItem(mediaItem)
        player.play()
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