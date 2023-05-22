package com.nhatvm.toptop.ui.foryou

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.runtime.Composable
import androidx.media3.common.util.UnstableApi

@OptIn(ExperimentalFoundationApi::class)
@UnstableApi
@Composable
fun ListForYouVideoScreen() {
    VerticalPager(pageCount = 10) { videoId ->
        ForYouVideoScreen(videoId = videoId)
    }
}