package com.nhatvm.toptop.ui.video.composables

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nhatvm.toptop.R
import com.nhatvm.toptop.ui.theme.ToptopTheme


@Composable
fun VideoAttractiveInfo(
    modifier: Modifier = Modifier,
    onAvatarClicked: () -> Unit,
    onLikeClicked: () -> Unit,
    onCommentClicked: () -> Unit,
    onBookmarkClicked: () -> Unit,
    onShareClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Avatar(onClick = onAvatarClicked)
        Spacer(modifier = Modifier.height(16.dp))
        VideoAttractiveInfoItem(icon = R.drawable.ic_heart, text = "250,5K") {
            onLikeClicked()
        }
        Spacer(modifier = Modifier.height(16.dp))
        VideoAttractiveInfoItem(icon = R.drawable.ic_chat, text = "100K") {
            onCommentClicked()
        }
        Spacer(modifier = Modifier.height(16.dp))
        VideoAttractiveInfoItem(icon = R.drawable.ic_bookmark, text = "89K") {
            onBookmarkClicked()
        }
        Spacer(modifier = Modifier.height(16.dp))
        VideoAttractiveInfoItem(icon = R.drawable.ic_share, text = "132,5K") {
            onShareClicked()
        }
        Spacer(modifier = Modifier.height(16.dp))
        AudioTrack()
    }
}

@Composable
fun Avatar(modifier: Modifier = Modifier, onClick: () -> Unit) {
    ConstraintLayout(modifier = modifier.clickable {
        onClick()
    }) {
        val (imgAvatar, imgPlus) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.ic_dog),
            contentDescription = "avatar", contentScale = ContentScale.Inside,
            modifier = Modifier
                .size(48.dp)
                .background(
                    shape = CircleShape, color = Color.White
                )
                .border(color = Color.White, shape = CircleShape, width = 2.dp)
                .constrainAs(imgAvatar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Box(
            modifier = Modifier
                .size(24.dp)
                .background(color = MaterialTheme.colors.error, shape = CircleShape)
                .constrainAs(imgPlus) {
                    top.linkTo(imgAvatar.bottom)
                    bottom.linkTo(imgAvatar.bottom)
                    start.linkTo(imgAvatar.start)
                    end.linkTo(imgAvatar.end)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "add ",
                tint = Color.White,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Composable
fun VideoAttractiveInfoItem(
    modifier: Modifier = Modifier,
    icon: Int,
    text: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.clickable {
            onClick()
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "icon",
            modifier = Modifier.size(30.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, style = MaterialTheme.typography.body2.copy(color = Color.White))
    }
}

@Composable
fun AudioTrack(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()

    val rotate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(
                durationMillis = 5000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    Image(
        modifier = modifier
            .size(30.dp)
            .rotate(rotate),
        painter = painterResource(id = R.drawable.ic_audio_track),
        contentDescription = "audio track"
    )
}

@Preview(name = "Avatar preview", showBackground = true)
@Composable
fun AvatarPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .padding(24.dp)
                .background(color = Color.Cyan)
        ) {
            Avatar(modifier = Modifier.padding(24.dp)) {}
        }
    }
}

@Preview(name = "Like Preview", showBackground = true)
@Composable
fun VideoAttractiveInfoItemLikePreview() {
    ToptopTheme {
        VideoAttractiveInfoItem(
            modifier = Modifier
                .background(color = Color.Black)
                .padding(24.dp),
            icon = R.drawable.ic_heart,
            text = "250,5K"
        ) {

        }
    }
}

@Preview
@Composable
fun AudioTrackPreview() {
    ToptopTheme {
        Box(
            modifier = Modifier
                .padding(24.dp)
                .background(color = Color.White)
        ) {
            AudioTrack()
        }
    }
}

@Preview(name = "VideoAttractiveInfo Preview", showBackground = true)
@Composable
fun VideoAttractiveInfoPreview() {
    ToptopTheme {
        VideoAttractiveInfo(
            onAvatarClicked = { },
            onLikeClicked = { },
            onCommentClicked = { },
            onBookmarkClicked = { }) {

        }
    }
}