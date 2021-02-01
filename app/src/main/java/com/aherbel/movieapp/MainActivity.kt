package com.aherbel.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animatedFloat
import androidx.compose.animation.asDisposableClock
import androidx.compose.animation.core.AnimatedFloat
import androidx.compose.animation.core.TargetAnimation
import androidx.compose.foundation.*
import androidx.compose.foundation.animation.FlingConfig
import androidx.compose.foundation.animation.FloatAndroidFlingDecaySpec
import androidx.compose.foundation.animation.fling
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.AmbientAnimationClock
import androidx.compose.ui.platform.AmbientConfiguration
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.asFontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.aherbel.movieapp.model.Movie
import com.aherbel.movieapp.ui.theme.*
import dev.chrisbanes.accompanist.coil.CoilImage
import java.util.*
import kotlin.math.abs
import kotlin.math.round
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    
    @ExperimentalLayout
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

@ExperimentalLayout
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

@ExperimentalLayout
@Composable
fun Home() {
    val carouselState = rememberCarouselState()
    val selectedItem = movies[carouselState.selectedIndex]
    
    Box(Modifier.fillMaxSize()) {
        CoilImage(
            data = selectedItem.imageUrl,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = ColorPainter(Color.Black.copy(alpha = 0.5f)),
            modifier = Modifier.matchParentSize(),
        )
        
        ScrollableColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            TopMenu()
            Spacer(Modifier.height(48.dp))
            Text(
                text = selectedItem.name,
                color = Color.White,
                fontSize = TextUnit.Sp(62),
                fontFamily = jokerFont.asFontFamily(),
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
            
            val screenWidthDp = AmbientConfiguration.current.screenWidthDp.dp
            val posterSpacingDp = screenWidthDp * .55f
            val contentPaddingDp = screenWidthDp * 0.25f
            
            Carousel(movies, posterSpacingDp, contentPaddingDp, carouselState) { movie ->
                MoviePoster(movie)
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

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
    val animatedOffset = state.animatedOffset
    
    val density = AmbientDensity.current
    val screenWidthDp = AmbientConfiguration.current.screenWidthDp.dp
    val screenWidthPx = with(density) { screenWidthDp.toPx() }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 48.dp)
            .padding(horizontal = contentPadding)
            .draggable(
                orientation = Orientation.Horizontal,
                onDrag = { state.onDrag(it) },
                onDragStopped = { state.fling(it) },
            )
    ) {
        items.forEachIndexed { index, item ->
            val center = itemSpacingPx * index
            Column(
                Modifier
                    .offset(
                        getX = { center + animatedOffset.value },
                        getY = {
                            val distFromCenter = abs(animatedOffset.value + center) / screenWidthPx
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
fun MoviePoster(movie: Movie, modifier: Modifier = Modifier) {
    CoilImage(
        data = movie.imageUrl,
        modifier = modifier
    )
}

@Composable
fun Descriptors(
    descriptors: List<String>,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Sp(10),
) {
    val text = descriptors.joinToString(separator = " \u00b7 ")
    Text(
        text = text,
        color = Color.White,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
fun BuyTicketButton() {
    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(backgroundColor = red),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = stringResource(R.string.buy_ticket).toUpperCase(Locale.ROOT),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AgeClasification(age: Int) {
    Clasification(AnnotatedString("$age+"))
}

@Composable
fun Category(category: String) {
    Clasification(AnnotatedString(category))
}

@Composable
fun Score(score: Float) {
    check(score in 0.0f..10f) {
        "Score must be a number between 0 and 10"
    }
    Clasification(
        buildAnnotatedString {
            val spanStyle = SpanStyle(color = black, fontWeight = FontWeight.ExtraBold)
            withStyle(style = spanStyle.copy(fontSize = TextUnit.Sp(12))) {
                append("$score")
            }
            withStyle(style = spanStyle.copy(fontSize = TextUnit.Sp(8))) {
                append("/10")
            }
        },
        backgroundColor = yellow
    )
}

@Composable
fun Clasification(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    backgroundColor: Color = Color.Black.copy(alpha = 0.7f),
) {
    Text(
        text = text,
        color = color,
        fontSize = TextUnit.Sp(12),
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .background(
                backgroundColor,
                RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 14.dp, vertical = 20.dp)
    )
}

@Composable
fun TopMenu() {
    Row(Modifier.padding(24.dp)) {
        val border = Modifier.border(
            BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
            roundedCornerShape
        )
        Icon(
            imageVector = vectorResource(R.drawable.ic_menu),
            tint = Color.White,
            modifier = Modifier
                .height(56.dp)
                .then(border)
                .padding(10.dp)
        )
        
        Spacer(Modifier.width(8.dp))
        
        var searchText by savedInstanceState(saver = TextFieldValue.Saver) {
            TextFieldValue()
        }
        SearchLayout(value = searchText, onValueChange = { searchText = it })
    }
}

@Composable
fun Line(
    color: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
    thickness: Dp = 1.dp,
) {
    val halfAlphaColor = color.copy(alpha = 0.5f)
    Box(
        Modifier
            .preferredHeight(thickness)
            .fillMaxWidth(0.5f)
            .background(
                Brush.horizontalGradient(
                    0.0f to halfAlphaColor,
                    0.5f to color,
                    1.0f to halfAlphaColor,
                )
            )
    )
}

@Composable
fun SearchLayout(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                roundedCornerShape
            )
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            backgroundColor = Color.Transparent,
            activeColor = Color.Transparent,
            inactiveColor = Color.Transparent,
            placeholder = {
                Text(
                    text = stringResource(R.string.search_products),
                    color = Color.White,
                )
            },
            textStyle = TextStyle.Default.copy(color = Color.White),
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Filled.Search,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(16.dp))
    }
}

@Composable
fun rememberCarouselState(): CarouselState {
    val density = AmbientDensity.current
    val clock = AmbientAnimationClock.current.asDisposableClock()
    val animatedOffset = animatedFloat(0f)
    return remember(clock, density) {
        CarouselState(
            density,
            animatedOffset,
        )
    }
}

class CarouselState(
    private val density: Density,
    internal val animatedOffset: AnimatedFloat,
) {
    
    private val flingConfig = FlingConfig(FloatAndroidFlingDecaySpec(density)) { adjustTarget(it) }
    
    private var itemCount: Int = 0
    internal var itemSpacingPx: Float = 0f
    
    private val upperBound: Float = 0f
    private val lowerBound: Float get() = -1 * (itemCount - 1) * itemSpacingPx
    
    val selectedIndex: Int get() = offsetToIndex(animatedOffset.value, itemSpacingPx)
    
    internal fun onDrag(delta: Float) {
        var target = animatedOffset.value + delta
        when {
            target > upperBound -> {
                target = upperBound
            }
            target < lowerBound -> {
                target = lowerBound
            }
        }
        animatedOffset.snapTo(target)
    }
    
    internal fun fling(velocity: Float) {
        animatedOffset.fling(velocity, flingConfig)
    }
    
    internal fun update(itemsCount: Int, itemSpacing: Dp) {
        itemCount = itemsCount
        itemSpacingPx = with(density) { itemSpacing.toPx() }
        animatedOffset.setBounds(lowerBound, upperBound)
    }
    
    private fun adjustTarget(target: Float): TargetAnimation {
        return TargetAnimation((target / itemSpacingPx).roundToInt() * itemSpacingPx)
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

private fun offsetToIndex(offset: Float, spacingPx: Float): Int =
    round(-1 * offset / spacingPx).toInt()
