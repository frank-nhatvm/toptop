package com.nhatvm.toptop.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberModalBottomSheetState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.media3.common.util.UnstableApi
import com.nhatvm.toptop.R
import com.nhatvm.toptop.ui.comment.CommentScreen
import com.nhatvm.toptop.ui.following.FollowingScreen
import com.nhatvm.toptop.ui.foryou.ListForUVideoScreen
import com.nhatvm.toptop.ui.theme.ToptopTheme
import com.nhatvm.toptop.ui.user.ProfileScreen
import kotlinx.coroutines.launch

@UnstableApi
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen() {

    var currentVideoId by remember {
        mutableStateOf(-1)
    }

    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(initialPage = 1)

    val scrollToPage: (Boolean) -> Unit = { isForU ->
        val page = if (isForU) 1 else 0
        coroutineScope.launch {
            pagerState.scrollToPage(page = page)
        }
    }

    var isShowTabContent by remember {
        mutableStateOf(true)
    }

    val toggleTabContent = { isShow: Boolean ->
        if (isShowTabContent != isShow) {
            isShowTabContent = isShow
        }
    }

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (page == 2) {
                // hide tab content
                toggleTabContent(false)
            } else {
                // show tab content
                toggleTabContent(true)
            }
        }
    }

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val showCommentBottomSheet: (Int) -> Unit = { videoId ->
        currentVideoId = videoId
        coroutineScope.launch {
            sheetState.show()
        }
    }

    val hideCommentBottomSheet: () -> Unit = {
        currentVideoId = -1
        coroutineScope.launch {
            sheetState.hide()
        }
    }

    ModalBottomSheetLayout(sheetState = sheetState, sheetContent = {
        if (currentVideoId != -1) {
            CommentScreen(videoId = currentVideoId) {
                // hide bottom sheet
                hideCommentBottomSheet()
            }
        } else {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }) {

        Scaffold(bottomBar = {
            AnimatedVisibility(visible = isShowTabContent) {
                TopTopBottomAppBar(onOpenHome = {}, onAddVideo = {})
            }
        }) { paddingValues ->
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                val (tabContentView, body) = createRefs()

                HorizontalPager(modifier = Modifier.constrainAs(body) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }, pageCount = 3, state = pagerState) { page ->
                    when (page) {
                        0 -> FollowingScreen()
                        2 -> ProfileScreen()
                        else -> ListForUVideoScreen() { videoId ->
                            showCommentBottomSheet(videoId)
                        }
                    }
                }


                AnimatedVisibility(visible = isShowTabContent) {
                    TabContentView(isTabSelectedIndex = pagerState.currentPage,
                        onSelectedTab = { isForU ->
                            scrollToPage(isForU)
                        },
                        modifier = Modifier
                            .constrainAs(tabContentView) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }

                    )
                }
            }
        }
    }


}

@Composable
fun TopTopBottomAppBar(
    onOpenHome: () -> Unit,
    onAddVideo: () -> Unit,

    ) {
    BottomAppBar(backgroundColor = Color.Black, contentColor = Color.White) {
        BottomNavigationItem(selected = true, onClick = { onOpenHome() }, icon = {
            Icon(painterResource(id = R.drawable.ic_home), "home")
        }, label = {
            Text("Home")
        })
        BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = {
            Icon(painterResource(id = R.drawable.ic_now), "Friends")
        }, label = {
            Text("Friends")
        })

        BottomNavigationItem(
            selected = false, onClick = { onAddVideo() },
            icon = {
                Icon(painterResource(id = R.drawable.ic_add_video), "add more video")
            },
        )

        BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = {
            Icon(painterResource(id = R.drawable.ic_inbox), "Inbox icon")
        }, label = {
            Text("Inbox")
        })
        BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = {
            Icon(painterResource(id = R.drawable.ic_profile), "Profile")
        }, label = {
            Text("Profile")
        })
    }
}


@Composable
fun TabContentItemView(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean,
    isForU: Boolean,
    onSelectedTab: (isForU: Boolean) -> Unit
) {
    val alpha = if (isSelected) 1f else 0.6f

    Column(
        modifier = modifier
            .wrapContentSize()
            .clickable { onSelectedTab(isForU) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6.copy(color = Color.White.copy(alpha = alpha))
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (isSelected) {
            Divider(color = Color.White, thickness = 2.dp, modifier = Modifier.width(24.dp))
        }
    }
}

@Composable
fun TabContentView(
    modifier: Modifier = Modifier,
    isTabSelectedIndex: Int,
    onSelectedTab: (isForU: Boolean) -> Unit,
) {

    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (tabContent, imgSearch) = createRefs()
        Row(modifier = Modifier
            .wrapContentSize()
            .constrainAs(tabContent) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            TabContentItemView(
                title = "Following",
                isSelected = isTabSelectedIndex == 0,
                isForU = false,
                onSelectedTab = onSelectedTab
            )
            Spacer(modifier = Modifier.width(12.dp))
            TabContentItemView(
                title = "For You",
                isSelected = isTabSelectedIndex == 1,
                isForU = true,
                onSelectedTab = onSelectedTab
            )
        }

        IconButton(onClick = { }, modifier = Modifier.constrainAs(imgSearch) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end, margin = 16.dp)
        }) {
            Icon(
                Icons.Default.Search,
                contentDescription = "Icon Search",
                Modifier.size(24.dp),
                tint = Color.White
            )
        }

    }

}

@Preview
@Composable
fun TabContentItemViewPreviewSelected() {
    ToptopTheme {
        TabContentItemView(title = "For You", isSelected = true, isForU = true, onSelectedTab = {})
    }
}

@Preview
@Composable
fun TabContentItemViewPreviewUnSelected() {
    ToptopTheme {
        TabContentItemView(
            title = "Following",
            isSelected = false,
            isForU = false,
            onSelectedTab = {})
    }
}

@Preview
@Composable
fun TabContentViewPreview() {
    ToptopTheme {
        TabContentView(isTabSelectedIndex = 1) {

        }
    }
}