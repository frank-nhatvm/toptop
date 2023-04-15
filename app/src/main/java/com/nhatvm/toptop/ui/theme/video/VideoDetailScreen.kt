package com.nhatvm.toptop.ui.theme.video

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.nhatvm.toptop.designsystem.TopTopVideoPlayer

@UnstableApi
@Composable
fun VideoDetailScreen(
    viewModel: VideoDetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    if (uiState.value == VideoDetailUiState.Default) {
        viewModel.processAction(VideoDetailAction.LoadData(id = "10"))
    }

    VideoDetailScreen(
        uiState = uiState.value,
        player = viewModel.player,
        processAction = { action ->
            viewModel.processAction(action)
        }
    )
}

@UnstableApi
@Composable
fun VideoDetailScreen(
    uiState: VideoDetailUiState,
    player: Player,
    processAction: (VideoDetailAction) -> Unit
) {

    when (uiState) {
        is VideoDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Loading")
            }
        }
        is VideoDetailUiState.Success -> {
            ConstraintLayout(modifier = Modifier
                .fillMaxSize()
                .clickable(
                    onClick = {
                        processAction(VideoDetailAction.ToggleVideo)
                    }
                )) {
                val videoPlayerView = createRef()
                TopTopVideoPlayer(
                    player = player,
                    modifier = Modifier.constrainAs(videoPlayerView) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.matchParent
                        height = Dimension.matchParent
                    })
            }
        }
        else -> {

        }
    }


}