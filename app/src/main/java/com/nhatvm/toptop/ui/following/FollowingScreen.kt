@file:OptIn(ExperimentalFoundationApi::class)

package com.nhatvm.toptop.ui.following

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.nhatvm.toptop.R
import com.nhatvm.toptop.designsystem.TopTopVideoPlayer
import kotlin.math.absoluteValue

@UnstableApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FollowingScreen(modifier: Modifier = Modifier) {

    val pagerState = rememberPagerState()

    val cardWidth = (LocalConfiguration.current.screenWidthDp * 2 / 3) - 24

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Trending creators",
            style = MaterialTheme.typography.h4.copy(color = Color.White)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Follow an account to see their latest videos here.",
            style = MaterialTheme.typography.body1.copy(color = Color.White.copy(alpha = 0.8f))
        )
        Spacer(modifier = Modifier.height(48.dp))
        HorizontalPager(
            pageCount = 10,
            pageSpacing = 12.dp,
            contentPadding = PaddingValues(start = 24.dp),
            beyondBoundsPageCount = 3,
            pageSize = PageSize.Fixed(cardWidth.dp)
        ) { page ->
            val ratio = if (page == pagerState.currentPage) {
                7 / 10f
            } else {
                7 / 8f
            }
            Card(
                modifier = Modifier
                    .width(cardWidth.dp)
                    .aspectRatio(ratio)
                    .clip(RoundedCornerShape(16.dp))
                    .graphicsLayer {

                        val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                CreatorCard(name = "Donal Trump$page", nickName = "@donaltrump_$page")
            }
        }
    }
}

private val threePagesPerViewport = object: PageSize {
    override fun Density.calculateMainAxisPageSize(
        availableSpace: Int,
        pageSpacing: Int
    ): Int {
        return (availableSpace - 2 * pageSpacing) / 3
    }
}

@UnstableApi
@Composable
fun CreatorCard(modifier: Modifier = Modifier, name: String, nickName: String) {

    val videoPlayer = ExoPlayer.Builder(LocalContext.current).build()
    videoPlayer.repeatMode = Player.REPEAT_MODE_ALL
    videoPlayer.playWhenReady = true
    videoPlayer.prepare()

    ConstraintLayout(
        modifier = modifier
            .aspectRatio(7 / 10f)
            .clip(RoundedCornerShape(16.dp))
    ) {
        val (vpIntro, infoLayout, iconClose) = createRefs()

        TopTopVideoPlayer(player = videoPlayer, modifier = Modifier.constrainAs(vpIntro) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        })
        IconButton(onClick = { }, modifier = Modifier
            .size(24.dp)
            .constrainAs(iconClose) {
                top.linkTo(parent.top, margin = 12.dp)
                end.linkTo(parent.end, margin = 12.dp)
            }) {
            Icon(imageVector = Icons.Sharp.Close, contentDescription = "", tint = Color.White)
        }
        CreatorInfoView(
            name = name,
            nickName = nickName,
            modifier = Modifier.constrainAs(infoLayout) {
                bottom.linkTo(parent.bottom, margin = 6.dp)
                start.linkTo(parent.start, margin = 12.dp)
                end.linkTo(parent.end, margin = 12.dp)
            }) {
            // follow action
        }
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            videoPlayer.release()
        }
    }

    val uri = RawResourceDataSource.buildRawResourceUri(R.raw.test)
    val mediaItem = MediaItem.fromUri(uri)
    videoPlayer.setMediaItem(mediaItem)
    SideEffect {
        videoPlayer.play()
    }

}

@Composable
fun CreatorInfoView(
    modifier: Modifier = Modifier,
    name: String,
    nickName: String,
    onFollowAction: () -> Unit
) {
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_dog),
            contentDescription = "",
            modifier = Modifier
                .size(48.dp)
                .background(shape = CircleShape, color = Color.White)
                .border(width = 2.dp, color = Color.White, shape = CircleShape)
                .clip(CircleShape)

        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name, style = MaterialTheme.typography.h5.copy(color = Color.White))
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = nickName,
            style = MaterialTheme.typography.body2.copy(color = Color.White.copy(alpha = 0.6f))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onFollowAction,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xffE94359),
                contentColor = Color.White
            )

        ) {
            Text("Follow")
        }
    }
}

@UnstableApi
@Preview
@Composable
fun CreatorCardPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .padding(24.dp)
                .background(Color.Black)
        ) {
            CreatorCard(name = "Donal Trump", nickName = "@donaltrump")
        }
    }
}