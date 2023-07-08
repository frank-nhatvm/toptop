package com.nhatvm.toptop.ui.demo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kotlinx.coroutines.launch

@Composable
fun DemoScaffoldScreen() {
    val coroutineScope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "TopTop") },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(Icons.Default.Menu, contentDescription = "")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "shopping cart")
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Search, contentDescription = "search")
                    }
                }
            )
        },
        bottomBar = {
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                shape = CircleShape,
                backgroundColor = Color(0xFFE94359)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        drawerContent = {
            Text(text = "Item  1")
            Divider(thickness = 2.dp, color = Color.Black)
            Text(text = "Item 2")
            Divider(thickness = 2.dp, color = Color.Black)
            Box(modifier = Modifier.weight(1f))
            Text("Sign out")
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Body")
        }
    }
}

@Composable
fun BottomAppBarItem(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onSelected: () -> Unit
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .clickable { onSelected() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(icon, contentDescription = title)
        Text(title)
    }
}