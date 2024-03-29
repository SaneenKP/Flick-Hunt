package com.example.flickhunt

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.flickhunt.models.Movie
import com.example.flickhunt.ui.theme.FlickHuntTheme
import com.example.flickhunt.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val movieTypeList: List<String> = listOf("", "movie", "series", "episode")
    private val movieTypeTagList: List<String> = listOf("Home", "Movie", "Series", "Episode")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickHuntTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Header(headerText = "Flick Hunt", bookMarked = false , onBookMarkIconClick = {})
                        MovieTypeSwitcher(switchType = {
                            homeViewModel.onMovieTypeSwitched(movieTypeList[it])
                        })
                        MovieList()
                    }
                }
            }
        }
    }

    @Composable
    fun MovieTypeSwitcher(switchType: (movieTypePosition: Int) -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(movieTypeTagList.size) { position ->
                    MovieTypePebble(movieType = movieTypeTagList[position] , switchType = {
                        switchType(movieTypeTagList.indexOf(it))
                    })
                }
            }
        }
    }

    @Composable
    fun MovieTypePebble(movieType: String , switchType : (movieType : String) -> Unit) {
        Card(
            modifier = Modifier
                .background(Color(0xFFFFFFFF))
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(4.dp)
                .clickable { switchType(movieType) },
            shape = RoundedCornerShape(13.dp),
            elevation = 4.dp,
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = movieType,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
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
    private fun SetMovieItem(movie: Movie?) {
        movie?.let { _movie ->
            MovieView(
                posterUrl = _movie.Poster,
                movieName = _movie.Title,
                movieType = _movie.Type,
                movieId  = _movie.imdbID
            )
        }
    }

    @Composable
    fun MovieView(
        posterUrl: String?,
        movieName: String?,
        movieType: String?,
        movieId : String?,
    ) {
        val movieItemGradient = Brush.linearGradient(
            colors = listOf(
//                Color(0xB3333333),
//                Color(0x00333333)
                Color.Red,
                Color.Blue
            ),
//            start = Offset.Zero,
//            end = Offset( 0F , 144F)
        )
        Box(
            modifier = Modifier
                .width(116.dp)
                .wrapContentSize()
                .clickable { startActivity(Intent(this, MovieDetailsScreen::class.java)) }
        ) {
            Column {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(144.dp)
                        .clip(shape = RoundedCornerShape(16.dp)).background(movieItemGradient),
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
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp),
                painter = painterResource(id = R.drawable.ic_bookmark),
                contentDescription = "Bookmark Movie",
            )
        }
    }
    @Composable
    private fun Header(
        headerText: String, bookMarked: Boolean, onBookMarkIconClick: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 13.dp),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Flick Hunt",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 24.dp)
                    .clickable {
                        onBookMarkIconClick()
                    },
                painter = painterResource(id = R.drawable.ic_bookmark),
                contentDescription = "BookMark Icon",
            )
        }
    }
}