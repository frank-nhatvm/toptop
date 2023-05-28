package com.nhatvm.toptop.ui.feeds

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi


@OptIn(ExperimentalFoundationApi::class)
@UnstableApi
@Composable
fun FeedScreen(modifier: Modifier = Modifier, videoId: Int, toggleBottomBar: (Boolean) -> Unit) {
    val pagerState = rememberPagerState(initialPage = 1)
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (page == 2) {
                toggleBottomBar(false)
            } else {
                toggleBottomBar(true)
            }
        }
    }
//    HorizontalPager(pageCount = 3, state = pagerState, modifier = modifier) { page ->
//        when (page) {
//            0 -> FollowingScreen()
//            2 -> ProfileScreen()
//            else -> ForYouVideoScreen(videoId = videoId)
//        }
//    }
}