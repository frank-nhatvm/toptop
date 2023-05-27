package com.nhatvm.toptop.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import com.nhatvm.toptop.ui.following.FollowingScreen
import com.nhatvm.toptop.ui.foryou.ForYouVideoScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
@UnstableApi
fun MainScreen() {

    val pagerState = rememberPagerState()

    val isShowBottomBar by remember {
        derivedStateOf {
            pagerState.currentPage != 2
        }
    }

    Scaffold(
        topBar = {
            Row(
                Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .background(color = Color.Magenta)
            ) {

            }
        },
        bottomBar = {
            AnimatedVisibility(visible = isShowBottomBar) {
                Row(
                    Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(color = Color.Green)
                ) {

                }
            }
        }
    ) { paddingValues ->

        HorizontalPager(
            pageCount = 3, modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) { index ->
            when (index) {
                0 -> FollowingScreen()
                2 -> ProfileScreen()
                else -> ForYouVideoScreen()
            }
        }
    }

}