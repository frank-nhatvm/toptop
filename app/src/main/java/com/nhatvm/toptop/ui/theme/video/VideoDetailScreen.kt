package com.nhatvm.toptop.ui.theme.video

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.nhatvm.toptop.designsystem.TopTopVideoPlayer
import com.nhatvm.toptop.ui.theme.video.composables.VideoAttractiveInfo
import com.nhatvm.toptop.ui.theme.video.composables.VideoInfoArea

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
    player: ExoPlayer,
    processAction: (VideoDetailAction) -> Unit
) {

    when (uiState) {
        is VideoDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Loading")
            }
        }
        is VideoDetailUiState.Success -> {
            VideoDetailScreen(player = player, processAction = processAction)
        }
        else -> {

        }
    }

}

@UnstableApi
@Composable
fun VideoDetailScreen(
    player: ExoPlayer,
    processAction: (VideoDetailAction) -> Unit
) {
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 56.dp)
        .clickable(
            onClick = {
                processAction(VideoDetailAction.ToggleVideo)
            }
        )) {
        val (videoPlayerView, sidebar, videoInfoArea) = createRefs()
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

        VideoAttractiveInfo(
            modifier = Modifier.constrainAs(sidebar) {
                end.linkTo(parent.end, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 16.dp)
            },
            onAvatarClicked = {},
            onBookmarkClicked = {},
            onCommentClicked = {},
            onLikeClicked = {},
            onShareClicked = {},
        )
        VideoInfoArea(
            accountName = "Khame Pet",
            videoName = "Funny Pets",
            hashTags = listOf("#pet", "#funny"),
            songName = "rock in roll",
            modifier = Modifier.constrainAs(videoInfoArea) {
                bottom.linkTo(sidebar.bottom)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(sidebar.start, margin = 32.dp)
                width = Dimension.fillToConstraints
            })
    }
}