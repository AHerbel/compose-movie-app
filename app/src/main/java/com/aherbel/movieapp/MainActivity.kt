package com.aherbel.movieapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.aherbel.movieapp.ui.theme.*
import com.aherbel.movieapp.ui.widgets.*
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.round
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Home()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_3A
)
@Composable
fun DefaultPreview() {
    MovieAppTheme {
        Home()
    }
}

@ExperimentalMaterialApi
@Composable
fun Home() {
    val carouselState = rememberCarouselState()
    val selectedItem = movies[carouselState.selectedIndex]
    
    Box(Modifier.fillMaxSize()) {
        CoilImage(
            data = selectedItem.imageUrl,
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = ColorPainter(Color.Black.copy(alpha = 0.5f)),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
        )
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            TopMenu()
            Spacer(Modifier.height(48.dp))
            
            val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
            val posterSpacingDp = screenWidthDp * .55f
            val contentPaddingDp = screenWidthDp * 0.25f
            
            Carousel(movies, posterSpacingDp, contentPaddingDp, carouselState) { movie ->
                MoviePoster(movie)
            }
            Spacer(Modifier.height(24.dp))
            
            Text(
                text = selectedItem.name,
                color = Color.White,
                fontSize = 62.sp,
                fontFamily = jokerFont,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Row {
                if (selectedItem.category.isNotEmpty()) {
                    Category(selectedItem.category)
                }
                Spacer(Modifier.width(8.dp))
                AgeClasification(selectedItem.ageClasification)
                Spacer(Modifier.width(8.dp))
                Score(selectedItem.score)
            }
            Spacer(Modifier.height(24.dp))
            
            val year = selectedItem.year.toString()
            val genres = selectedItem.genres.joinToString()
            val soundsMix = selectedItem.soundsMix.joinToString()
            Descriptors(
                descriptors = listOf(year, genres, soundsMix),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(Modifier.height(24.dp))
            Descriptors(descriptors = selectedItem.tags)
            Spacer(Modifier.height(24.dp))
            Line(color = red)
            Spacer(Modifier.height(24.dp))
            BuyTicketButton()
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun TopMenu() {
    Row(Modifier.padding(24.dp)) {
        val border = Modifier.border(
            BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
            roundedCornerShape
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_menu),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .height(56.dp)
                .then(border)
                .padding(10.dp)
        )
        
        Spacer(Modifier.width(8.dp))
        
        var searchText by rememberSaveable(TextFieldValue(),
                                           stateSaver = TextFieldValue.Saver,
                                           init = { mutableStateOf(TextFieldValue()) })
        SearchLayout(value = searchText, onValueChange = { searchText = it })
    }
}

fun Modifier.snapToCenter(
    offsetX: Animatable<Float, AnimationVector1D>,
    adjustTarget: (Float) -> Float,
    getCenter: (Float) -> Float,
): Modifier = composed {
    pointerInput(Unit) {
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

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}
