package com.nhatvm.toptop.ui.foryou

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.launch


@UnstableApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForYouVideoScreen(
    modifier: Modifier = Modifier,

    ) {

    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

//    val bottomSheetState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var currentVideoId by remember {
        mutableStateOf(0)
    }

    val showBottomSheet: () -> Unit = {
        coroutineScope.launch {
//            bottomSheetState.bottomSheetState.expand()
            bottomSheetState.show()
        }
    }

    val hideBottomSheet: () -> Unit = {
        coroutineScope.launch {
//            bottomSheetState.bottomSheetState.collapse()
            bottomSheetState.hide()
        }
    }


//    BottomSheetScaffold(sheetContent = {
//        CommentScreen(videoId = currentVideoId){
//            hideBottomSheet()
//        }
//    }, scaffoldState = bottomSheetState, sheetBackgroundColor = Color.Transparent) {
//        ListForYouVideoScreen(showCommentScreen = { videoId ->
//            Log.e("Frank", "call to show comment screen ${videoId}")
//            //  currentVideoId = videoId
//            showBottomSheet()
//        }
//        )
//    }

    ModalBottomSheetLayout(
        sheetContent = {
            CommentScreen(videoId = currentVideoId) {
                hideBottomSheet()
            }
        }, modifier = modifier.fillMaxSize(),
        scrimColor = Color.Transparent,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetState = bottomSheetState,
        sheetBackgroundColor = Color.Transparent
    ) {
        Scaffold { paddingValues ->
            ListForYouVideoScreen(
                modifier = Modifier.padding(paddingValues),
                showCommentScreen = { videoId ->
                    Log.e("Frank", "call to show comment screen ${videoId}")
                    //  currentVideoId = videoId
                    showBottomSheet()
                }
            )
        }
    }


}