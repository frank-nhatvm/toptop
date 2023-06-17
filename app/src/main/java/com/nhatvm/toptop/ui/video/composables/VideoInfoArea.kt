package com.nhatvm.toptop.ui.video.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nhatvm.toptop.ui.theme.ToptopTheme

@Composable
fun VideoInfoArea(
    modifier: Modifier = Modifier,
    accountName: String,
    videoName: String,
    hashTags: List<String>,
    songName: String
) {

    Column(
        modifier = modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = accountName,
            style = MaterialTheme.typography.h4.copy(color = Color.White),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = videoName,
            style = MaterialTheme.typography.h5.copy(color = Color.White),
            maxLines = 2
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = hashTags.joinToString(" "),
            style = MaterialTheme.typography.body1.copy(color = Color.White),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = songName,
            style = MaterialTheme.typography.body1.copy(color = Color.White),
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun VideoInfoAreaPreview() {
    ToptopTheme {
        VideoInfoArea(
            accountName = "NhatVm",
            videoName = "Clone Tiktok UI",
            hashTags = listOf("jetpack compose", "android", "tiktok"),
            songName = "Making my way"
        )
    }
}