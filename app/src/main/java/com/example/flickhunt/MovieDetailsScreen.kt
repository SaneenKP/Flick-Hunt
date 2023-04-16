package com.example.flickhunt

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.flickhunt.ui.theme.FlickHuntTheme
import com.example.flickhunt.viewmodels.HomeViewModel

class MovieDetailsScreen() : ComponentActivity() {

    private val homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickHuntTheme {
                homeViewModel.getMovie("tt1569923")
                val movieState = homeViewModel.singleMovieState
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    if (movieState.success){
                        Log.d("saneen" , " sucecess - ${movieState.movie}")
                    }
                    if (movieState.failed){
                        Log.d("saneen" , "oops failed to fetch data , please try again later.")
                    }
                }
            }
        }
    }
}

