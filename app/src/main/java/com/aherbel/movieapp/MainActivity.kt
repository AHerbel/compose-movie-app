package com.aherbel.movieapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aherbel.infrastructure.repositories.LocalMoviesRepository
import com.aherbel.movieapp.presentation.theme.MovieAppTheme
import com.aherbel.movieapp.presentation.theme.jokerFont
import com.aherbel.movieapp.presentation.theme.red
import com.aherbel.movieapp.presentation.theme.roundedCornerShape
import com.aherbel.movieapp.presentation.viewmodels.MoviesViewModel
import com.aherbel.movieapp.presentation.widgets.*
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.coil.CoilImage

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    private val moviesViewModel: MoviesViewModel by viewModels()
    
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Home(moviesViewModel)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun Home(moviesViewModel: MoviesViewModel) {
    val carouselState = rememberCarouselState { selectedIndex ->
        moviesViewModel.onSelectedMovieChanged(selectedIndex)
    }
    
    val selectedItem = moviesViewModel.selectedMovie
    
    Box(modifier = Modifier.fillMaxSize()) {
        CoilImage(
            data = selectedItem?.imageUrl ?: R.drawable.joker,
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
            TopMenu(moviesViewModel.searchQuery, moviesViewModel::onSearchQueryChanged)
            Spacer(Modifier.height(48.dp))
            
            val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
            val posterSpacingDp = screenWidthDp * .55f
            val contentPaddingDp = screenWidthDp * 0.25f
            
            Carousel(moviesViewModel.movies, posterSpacingDp, contentPaddingDp, carouselState) { movie ->
                MoviePoster(movie)
            }
            Spacer(Modifier.height(24.dp))
            
            if (selectedItem != null) {
                Text(
                    text = selectedItem.name,
                    color = Color.White,
                    fontSize = 62.sp,
                    fontFamily = jokerFont,
                    textAlign = TextAlign.Center,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp)
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
            } else {
                Text(
                    text = stringResource(R.string.no_movies_available),
                    color = Color.White,
                    fontSize = 50.sp,
                    fontFamily = jokerFont,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun TopMenu(
    text: String,
    onTextChange: (String) -> Unit,
) {
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
        
        SearchLayout(text, onTextChange)
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
        val moviesViewModel = MoviesViewModel(LocalMoviesRepository())
        Home(moviesViewModel)
    }
}