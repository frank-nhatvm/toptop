package com.nhatvm.toptop.ui.foryou

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi


@UnstableApi
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ForYouVideoScreen(
    modifier: Modifier = Modifier,
    videoId: Int
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(scaffoldState = bottomSheetScaffoldState, sheetContent = {
        CommentScreen(videoId = videoId)
    }, modifier = modifier.fillMaxSize()) { paddingValues ->
        Text(text = "VIdeo $videoId", modifier = Modifier.fillMaxSize())
//        VideoDetailScreen(videoId = videoId, showCommentScreen = {
//            if(bottomSheetScaffoldState.bottomSheetState.isCollapsed){
//                coroutineScope.launch {
//                    bottomSheetScaffoldState.bottomSheetState.expand()
//                }
//            }else{
//                coroutineScope.launch {
//                    bottomSheetScaffoldState.bottomSheetState.collapse()
//                }
//            }
//        }
//        )
    }

}