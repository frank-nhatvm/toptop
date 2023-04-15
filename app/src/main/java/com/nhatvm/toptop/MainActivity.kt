package com.nhatvm.toptop

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nhatvm.toptop.ui.theme.ToptopTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToptopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen() {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        MotionSensing(startRadius = 12f)

        Spacer(modifier = Modifier.height(48.dp))

        ArcTest()
    }

}

@Composable
fun ArcTest() {
//    var width by remember {
//        mutableStateOf(0f)
//    }

    val infiniteTransition = rememberInfiniteTransition()
    val width by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 50f, animationSpec = InfiniteRepeatableSpec(
            animation = tween(
                durationMillis = 3000,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart
        )
    )



    Box(modifier = Modifier
        .wrapContentSize()
        .aspectRatio(1f)
        .drawBehind {
            drawArc(
                startAngle = 0f,
                sweepAngle = 360f,
                color = Color.Red,
                useCenter = false,
                style = Stroke(width = width)
            )
        })
}

@Composable
fun MotionSensing(startRadius: Float) {

    val circleColor = Color(0xff0A6194)
    val secondColor = circleColor.copy(alpha = 0.8f)
    val thirdColor = circleColor.copy(alpha = 0.6f)
    val fourthColor = circleColor.copy(alpha = 0.4f)
    val fifthColor = circleColor.copy(alpha = 0.2f)

    val secondBrush =
        Brush.radialGradient(colors = listOf(Color.Transparent, secondColor), radius = startRadius)
    val thirdBrush =
        Brush.radialGradient(colors = listOf(Color.Transparent, thirdColor), radius = startRadius)
    val fourthBrush =
        Brush.radialGradient(colors = listOf(Color.Transparent, fourthColor), radius = startRadius)
    val fifthBrush =
        Brush.radialGradient(colors = listOf(Color.Transparent, fifthColor), radius = startRadius)

    val firstRadius = startRadius
    val maxSecondRadius = firstRadius * 2
    val maxThirdRadius = firstRadius * 3
    val maxFourthRadius = firstRadius * 4
    val maxFifthRadius = firstRadius * 5

    var secondRadius by remember {
        mutableStateOf(0f)
    }
    var thirdRadius by remember {
        mutableStateOf(0f)
    }
    var fourthRadius by remember {
        mutableStateOf(maxThirdRadius)
    }
    var fifthRadius by remember {
        mutableStateOf(maxFourthRadius)
    }

    val duration = 1000
    LaunchedEffect(true) {
        while (true) {
            Log.e("Frank", "start")
            secondRadius = 0f
            thirdRadius = 0f
            fourthRadius = 0f
            fifthRadius = 0f

            animate(
                initialValue = startRadius, targetValue = maxSecondRadius, animationSpec = tween(
                    durationMillis = duration,
                    easing = LinearEasing,
                )
            ) { value, _ ->
                secondRadius = value
                // Log.e("Frank", "secondRadius $secondRadius")
            }

            animate(
                initialValue = maxSecondRadius, targetValue = maxThirdRadius, animationSpec = tween(
                    durationMillis = duration,
                    easing = LinearEasing
                )
            ) { value, _ ->
                thirdRadius = value
                // Log.e("Frank", "thirdRadius $thirdRadius")
            }

            animate(
                initialValue = maxThirdRadius, targetValue = maxFourthRadius, animationSpec = tween(
                    durationMillis = duration,
                    easing = LinearEasing
                )
            ) { value, _ ->
                fourthRadius = value
            }

            animate(
                initialValue = maxFourthRadius, targetValue = maxFifthRadius, animationSpec = tween(
                    durationMillis = duration,
                    easing = LinearEasing
                )
            ) { value, _ ->
                fifthRadius = value
            }

        }

    }

    Box(contentAlignment = Alignment.Center) {
        CirCle(radius = firstRadius, color = circleColor)
//        Spacer(modifier = Modifier.height(maxSecondRadius.dp))
        CircleAnimation(radius = secondRadius, brush = secondBrush)
//        Spacer(modifier = Modifier.height(maxThirdRadius.dp))
        CircleAnimation(radius = thirdRadius, brush = thirdBrush)
        CircleAnimation(radius = fourthRadius, brush = fourthBrush)
        CircleAnimation(radius = fifthRadius, brush = fifthBrush)
    }
}


@Composable
fun CircleAnimation(radius: Float, brush: Brush) {
    Box(modifier = Modifier.drawBehind {
        drawCircle(radius = radius, brush = brush)
    })
}

@Composable
fun CirCle(radius: Float, color: Color) {
    Box(modifier = Modifier.drawBehind {
        drawCircle(color = color, radius = radius)
    })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToptopTheme {
        HomeScreen()
    }
}