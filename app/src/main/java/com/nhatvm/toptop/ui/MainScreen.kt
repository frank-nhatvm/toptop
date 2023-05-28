package com.nhatvm.toptop.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.media3.common.util.UnstableApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nhatvm.toptop.R
import com.nhatvm.toptop.ui.following.FollowingScreen
import com.nhatvm.toptop.ui.foryou.ForYouVideoScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
@UnstableApi
fun MainScreen() {
    val coroutine = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 1)
    var isShowBottomBar by remember {
        mutableStateOf(true)
    }
    val toggleBottomBar: (Boolean) -> Unit = { isShow ->
        if (isShowBottomBar != isShow) {
            isShowBottomBar = isShow
        }
    }
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (page == 2) {
                toggleBottomBar(false)
            } else {
                toggleBottomBar(true)
            }
        }
    }
    val scrollToPage: (Boolean) -> Unit = { isForYou ->
        val index = if (isForYou) {
            1
        } else {
            0
        }
        coroutine.launch {
            pagerState.scrollToPage(index)
        }
    }
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val parentHeight = maxHeight
        Scaffold(
            topBar = {
                AnimatedVisibility(visible = isShowBottomBar) {
                    MainTopBar(
                        selectedTabIndex = pagerState.currentPage,
                        scrollToPage = scrollToPage
                    )
                }
            },
            bottomBar = {
                AnimatedVisibility(visible = isShowBottomBar) {
                    MainBottomBar()
                }
            }
        ) { paddingValues ->
            BoxWithConstraints(
                modifier = Modifier.padding(paddingValues),
                contentAlignment = Alignment.TopCenter
            ) {
                val childHeight = this.maxHeight
                val scaleY = parentHeight.value / childHeight.value

                HorizontalPager(
                    pageCount = 3,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { this.scaleY = scaleY }
                ) { page ->
                    when (page) {
                        0 -> FollowingScreen()
                        2 -> ProfileScreen()
                        else -> ForYouVideoScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun MainTopBar(selectedTabIndex: Int, scrollToPage: (Boolean) -> Unit) {
    TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
        ) {
            val (tabContentView, iconSearch) = createRefs()
            TabContentView(
                selectedTabIndex = selectedTabIndex,
                scrollToPage = scrollToPage,
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(tabContentView) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    })

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                .size(24.dp)
                .constrainAs(iconSearch) {
                    end.linkTo(parent.end, margin = 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }) {
                Icon(Icons.Default.Search, contentDescription = "", tint = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabContentView(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    scrollToPage: (Boolean) -> Unit
) {
    val followingAlpha = if (selectedTabIndex == 0) 1f else 0.6f
    val forUAlpha = if (selectedTabIndex == 1) 1f else 0.6f
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            "Following",
            style = MaterialTheme.typography.h6.copy(color = Color.White.copy(alpha = followingAlpha))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "ForYou",
            style = MaterialTheme.typography.h6.copy(color = Color.White.copy(alpha = forUAlpha))
        )
    }
}

@Composable
fun MainBottomBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Black)
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 24.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarItem(
            icon = R.drawable.ic_home,
            name = "Home",
            isSelected = true,
            modifier = Modifier.weight(1f)
        )
        BottomBarItem(
            icon = R.drawable.ic_now,
            name = "Now",
            isSelected = false,
            modifier = Modifier.weight(1f)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_add_video),
            contentDescription = "",
            modifier = Modifier
                .weight(1f)
                .aspectRatio(4 / 3f)
        )
        BottomBarItem(
            icon = R.drawable.ic_inbox,
            name = "Inbox",
            isSelected = false,
            modifier = Modifier.weight(1f)
        )
        BottomBarItem(
            icon = R.drawable.ic_profile,
            name = "Profile",
            isSelected = false,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun BottomBarItem(
    modifier: Modifier = Modifier,
    icon: Int,
    name: String,
    isSelected: Boolean = false
) {
    val alpha = if (isSelected) 1f else 0.8f
    Column(
        modifier = modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painterResource(id = icon),
            contentDescription = "",
            tint = Color.White.copy(alpha = alpha)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            name,
            style = MaterialTheme.typography.subtitle1.copy(color = Color.White.copy(alpha = alpha))
        )
    }
}