package com.example.flickhunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.flickhunt.models.Movie
import com.example.flickhunt.ui.theme.FlickHuntTheme
import com.example.flickhunt.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickHuntTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                    MovieList()
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        FlickHuntTheme {
            Greeting("Android")
            MovieList()
        }
    }

    @Composable
    fun MovieList() {

        val movieList = homeViewModel.movieList.collectAsLazyPagingItems()

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                columns = GridCells.Adaptive(116.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    items(count = movieList.itemCount) { item ->
                        SetMovieItem(movie = movieList[item])
                    }
                }
            )
        }
    }

    @Composable
    private fun SetMovieItem(movie : Movie?){
        movie?.let { _movie ->
            MovieView(
                posterUrl = _movie.Poster,
                movieName = _movie.Title,
                movieType = _movie.Type
            )
        }
    }

    @Composable
    fun MovieView(
        posterUrl: String?,
        movieName: String?,
        movieType: String?
    ) {
        Box(
            modifier = Modifier
                .width(116.dp)
                .wrapContentSize()
        ) {
            Column {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(144.dp)
                        .clip(shape = RoundedCornerShape(16.dp)),
                    model = posterUrl, contentDescription = movieName,
                    contentScale = ContentScale.FillBounds,
                )
                Text(
                    modifier = Modifier.padding(top = 6.dp, bottom = 2.dp, start = 2.dp),
                    text = movieName ?: "NA",
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = movieType ?: "NA",
                    textAlign = TextAlign.Start,
                    color = Color(0xFF8D8D8D),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}