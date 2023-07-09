package com.nhatvm.toptop.ui.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun CommentScreen(modifier: Modifier = Modifier, videoId: Int, hideBottomSheet: () -> Unit) {

    val screenHeight = LocalConfiguration.current.screenHeightDp / 2
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(screenHeight.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Comment for video : $videoId")

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = hideBottomSheet) {
            Text("Close")
        }
    }

}