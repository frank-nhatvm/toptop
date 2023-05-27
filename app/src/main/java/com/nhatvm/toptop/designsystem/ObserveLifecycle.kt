package com.nhatvm.toptop.designsystem

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun ObserverLifecycle(
    onResume: () -> Unit,
    onStart: (() -> Unit)? = null
) {

    val currentOnResume by rememberUpdatedState(newValue = onResume)
    val currentOnStart by rememberUpdatedState(newValue = onStart)
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        Log.e("Frank", "register observe ")
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    Log.e("Frank", "Lifecycle ON_CREATE")
                }
                Lifecycle.Event.ON_START -> {
                    Log.e("Frank", "Lifecycle ON_START")
                    currentOnStart?.invoke()
                }
                Lifecycle.Event.ON_RESUME -> {
                    currentOnResume.invoke()
                }
                Lifecycle.Event.ON_STOP -> {
                    Log.e("Frank", "Lifecycle ON_STOP")
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.e("Frank", "Lifecycle ON_PAUSE")
                }
                Lifecycle.Event.ON_DESTROY -> {
                    Log.e("Frank", "Lifecycle ON_DESTROY")
                }
                else -> {

                }
            }

        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            Log.e("Frank", "remove observe ")
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}