package com.nhatvm.toptop.ui.foryou

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.sharp.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nhatvm.toptop.R


@Composable
fun CommentScreen(modifier: Modifier = Modifier, videoId: Int, closeCommentAction: () -> Unit) {
    Log.e("Frank", "CommentScreen $videoId")

    val halfOfScreen = LocalConfiguration.current.screenHeightDp / 2

    Column(modifier = modifier
        .wrapContentSize()
        .background(color = Color.Black.copy(alpha = 0.6f))
        .clickable {
            closeCommentAction()
        }, verticalArrangement = Arrangement.Bottom
    ) {
        CommentContentScreen(modifier = Modifier.height(halfOfScreen.dp)) {
            closeCommentAction()
        }
    }
}

@Composable
fun CommentContentScreen(modifier: Modifier = Modifier, closeCommentAction: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
    ) {
        CommentHeaderView(commentNumber = 3421) {
            closeCommentAction()
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(100) { index ->
                Text(
                    text = "Comment $index", modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }
        }
        CommentInputView()
    }
}

@Composable
fun CommentHeaderView(
    modifier: Modifier = Modifier,
    commentNumber: Int,
    closeCommentAction: () -> Unit
) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (tvCommentNumbers, iconClose) = createRefs()

        Text(
            text = "$commentNumber comments",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.constrainAs(tvCommentNumbers) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })


        IconButton(onClick = { closeCommentAction() }, modifier = Modifier.constrainAs(iconClose) {
            end.linkTo(parent.end, margin = 12.dp)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }) {
            Icon(Icons.Default.Close, contentDescription = "icon close bottom sheet")
        }
    }
}

@Composable
fun CommentInputView(modifier: Modifier = Modifier) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            (0..5).map {
                Icon(
                    Icons.Default.Person, contentDescription = "", modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1 / 1f)
                        .padding(6.dp)
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_dog),
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .clip(
                        CircleShape
                    )
            )

            TextField(
                value = "", onValueChange = {},
                shape = RoundedCornerShape(50.dp),
                trailingIcon = {
                    Row(modifier = Modifier.wrapContentSize()) {
                        Icon(imageVector = Icons.Default.LocationOn, contentDescription = "")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(imageVector = Icons.Sharp.Person, contentDescription = "")
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            )

        }
    }
}