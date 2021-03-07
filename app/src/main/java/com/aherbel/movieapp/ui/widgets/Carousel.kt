package com.aherbel.movieapp.ui.widgets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aherbel.movieapp.*
import com.aherbel.movieapp.ui.theme.roundedCornerShape
import kotlin.math.abs
import kotlin.math.round
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun <T> Carousel(
    items: List<T>,
    itemSpacing: Dp,
    contentPadding: Dp,
    state: CarouselState,
    foregroundContent: @Composable (item: T) -> Unit,
) {
    
    state.update(items.size, itemSpacing)
    val itemSpacingPx = state.itemSpacingPx
    val offsetX = state.offsetX
    
    val density = LocalDensity.current
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val screenWidthPx = with(density) { screenWidthDp.toPx() }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 48.dp)
            .padding(horizontal = contentPadding)
            .snapToCenter(
                offsetX = state.offsetX,
                adjustTarget = { state.adjustTarget(it) },
                getCenter = { state.getItemCenterByTarget(it) }
            )
    ) {
        items.forEachIndexed { index, item ->
            val center = itemSpacingPx * index
            Column(
                Modifier
                    .offset(
                        getX = { center + offsetX.value },
                        getY = {
                            val distFromCenter = abs(offsetX.value + center) / screenWidthPx
                            lerp(0f, 250f, distFromCenter)
                        }
                    )
                    .width(screenWidthDp * 0.5f)
                    .clip(roundedCornerShape)
            ) {
                foregroundContent(item)
            }
        }
    }
}

@Composable
fun rememberCarouselState(): CarouselState {
    val density = LocalDensity.current
    val offsetX = remember { Animatable(0f) }
    return remember(density) {
        CarouselState(
            density,
            offsetX
        )
    }
}

class CarouselState constructor(
    private val density: Density,
    internal val offsetX: Animatable<Float, AnimationVector1D>,
) {
    
    private var itemCount: Int = 0
    internal var itemSpacingPx: Float = 0f
    
    private val upperBound: Float = 0f
    private val lowerBound: Float get() = -1 * (itemCount - 1) * itemSpacingPx
    
    val selectedIndex: Int get() = offsetToIndex(offsetX.value, itemSpacingPx)
    
    fun adjustTarget(target: Float): Float {
        return when {
            target > upperBound -> {
                upperBound
            }
            target < lowerBound -> {
                lowerBound
            }
            else -> target
        }
    }
    
    internal fun getItemCenterByTarget(target: Float): Float {
        val adjustedTarget = adjustTarget(target)
        return (adjustedTarget / itemSpacingPx).roundToInt() * itemSpacingPx
    }
    
    internal fun update(itemsCount: Int, itemSpacing: Dp) {
        itemCount = itemsCount
        itemSpacingPx = with(density) { itemSpacing.toPx() }
        offsetX.updateBounds(lowerBound, upperBound)
    }
    
}

private fun offsetToIndex(offset: Float, spacingPx: Float): Int =
    round(-1 * offset / spacingPx).toInt()