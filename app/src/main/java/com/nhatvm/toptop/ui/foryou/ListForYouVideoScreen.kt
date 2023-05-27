package com.nhatvm.toptop.ui.foryou

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import com.nhatvm.toptop.ui.video.VideoDetailScreen
import com.nhatvm.toptop.ui.video.VideoDetailViewModel

@OptIn(ExperimentalFoundationApi::class)
@UnstableApi
@Composable
fun ListForYouVideoScreen(
    modifier: Modifier = Modifier,
    showCommentScreen: (Int) -> Unit
) {
    VerticalPager(pageCount = 10, modifier = modifier) { videoId ->
        val viewModel: VideoDetailViewModel = hiltViewModel(key = videoId.toString())
        VideoDetailScreen(
            viewModel = viewModel,
            videoId = videoId,
            showCommentScreen = showCommentScreen
        )
    }
}