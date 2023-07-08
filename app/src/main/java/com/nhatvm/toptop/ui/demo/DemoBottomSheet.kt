package com.nhatvm.toptop.ui.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kotlinx.coroutines.launch
import androidx.compose.material3.rememberModalBottomSheetState as rememberModalBottomSheetStateM3

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DemoModalBottomSheetM3() {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val sheetStateM3 = rememberModalBottomSheetStateM3()

    val scope = rememberCoroutineScope()
    var isShowBottomSheet by remember {
        mutableStateOf(false)
    }
    Scaffold(
        bottomBar = {
            TopTopBottomAppBar()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Button(onClick = {
                isShowBottomSheet = true
            }) {
                Text("Show bottom sheet")
            }

            if (isShowBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { isShowBottomSheet = false },
                    sheetState = sheetStateM3
                ) {
                    BottomSheetContent() {
                        scope.launch { sheetStateM3.hide() }.invokeOnCompletion {
                            isShowBottomSheet = false
                        }
                    }
                }
            }
        }
    }
}

/**
 * It is ok for M2
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DemoModalBottomSheetLayout() {
    val bottomSheetScaffoldState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val showBottomSheet: () -> Unit = {
        coroutineScope.launch {
            bottomSheetScaffoldState.show()
        }
    }
    val hideBottomSheet: () -> Unit = {
        coroutineScope.launch {
            bottomSheetScaffoldState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetScaffoldState, sheetContent = {
            BottomSheetContent {
                hideBottomSheet()
            }
        }) {
        Scaffold(bottomBar = { TopTopBottomAppBar() }) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Blue)
                    .padding(paddingValues)
            ) {
                Button(onClick = {
                    showBottomSheet()
                }) {
                    Text("Show bottom sheet")
                }

            }
        }
    }
}


/**
 * it seems BottomSheetScaffold does not support to show BottomAppBar
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DemoBottomSheetScaffold() {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val showBottomSheet: () -> Unit = {
        coroutineScope.launch {
            scaffoldState.bottomSheetState.expand()
        }
    }
    val hideBottomSheet: () -> Unit = {
        coroutineScope.launch {
            scaffoldState.bottomSheetState
        }
    }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomSheetContent {
                hideBottomSheet()
            }
        },
    ) {
        Scaffold(bottomBar = {
            TopTopBottomAppBar()
        }) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Blue)
                    .padding(paddingValues)
            ) {
                Button(onClick = {
                    showBottomSheet()
                }) {
                    Text("Show bottom sheet")
                }

            }
        }
    }
}


@Composable
fun BottomSheetContent(modifier: Modifier = Modifier, hideBottomSheet: () -> Unit) {

    Column(
        modifier = modifier
            .background(color = Color.White)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Spacer(modifier = Modifier.height(56.dp))
        Text("Bottom Sheet Content")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = hideBottomSheet) {
            Text("Close")
        }
    }
}

@Composable
private fun TopTopBottomAppBar() {
    BottomAppBar(
        cutoutShape = CircleShape,
        modifier = Modifier.clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
    ) {

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val centerVerticalGuideLine = createGuidelineFromStart(0.5f)
            val (leftMenus, rightMenus) = createRefs()
            Row(
                modifier = Modifier.constrainAs(leftMenus) {
                    start.linkTo(parent.start)
                    end.linkTo(centerVerticalGuideLine, margin = 28.dp)
                    width = Dimension.fillToConstraints
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                BottomNavigationItem(selected = true, onClick = { }, icon = {
                    Icon(Icons.Default.Home, "")
                }, label = {
                    Text("Home")
                })
                BottomNavigationItem(selected = true, onClick = { }, icon = {
                    Icon(Icons.Default.Person, "")
                }, label = {
                    Text("Friends")
                })
            }

            Row(
                modifier = Modifier.constrainAs(rightMenus) {
                    start.linkTo(centerVerticalGuideLine, margin = 28.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }, verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                BottomNavigationItem(selected = true, onClick = { }, icon = {
                    Icon(Icons.Default.AccountBox, "")
                }, label = {
                    Text("Inbox")
                })
                BottomNavigationItem(selected = true, onClick = { }, icon = {
                    Icon(Icons.Default.Face, "")
                }, label = {
                    Text("Profile")
                })
            }

        }


    }
}