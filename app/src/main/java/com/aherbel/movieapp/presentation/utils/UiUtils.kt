package com.aherbel.movieapp.presentation.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

fun Modifier.snapToCenter(
    offsetX: Animatable<Float, AnimationVector1D>,
    adjustTarget: (Float) -> Float,
    getCenter: (Float) -> Float,
): Modifier = composed {
    pointerInput(Unit.INSTANCE) {
        coroutineScope {
            while (true) {
                val down = awaitPointerEventScope { awaitFirstDown() }
                awaitPointerEventScope {
                    horizontalDrag(down.id) { change ->
                        val adjustedX = adjustTarget(
                            offsetX.value + change.positionChange().x
                        )
                        launch {
                            offsetX.snapTo(adjustedX)
                        }
                    }
                }
                
                val itemCenter = getCenter(offsetX.value)
                launch {
                    offsetX.animateTo(itemCenter)
                }
            }
        }
    }
}

fun Modifier.offset(
    getX: () -> Float,
    getY: () -> Float,
    rtlAware: Boolean = true,
) = this then object : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints,
    ): MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            if (rtlAware) {
                placeable.placeRelative(getX().roundToInt(), getY().roundToInt())
            } else {
                placeable.place(getX().roundToInt(), getY().roundToInt())
            }
        }
    }
}