package com.example.flickhunt.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.flickhunt.models.Movie
import com.example.flickhunt.repository.MoviePagingRepository
import com.example.flickhunt.repository.MovieRepository
import com.example.flickhunt.retrofit.ResponseWrapper2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val pagingRepository: MoviePagingRepository
) : ViewModel() {

    private val movieTypeFlow = MutableStateFlow("")
    private val movieSearchFlow = MutableStateFlow("batman")

    private val movieTypeAndSearchCombinedFlow: Flow<Pair<String, String>> =
        movieTypeFlow.combine(movieSearchFlow) { type, search ->
            Pair(type, search)
        }

    var movieList = movieTypeAndSearchCombinedFlow.flatMapLatest { (type, search) ->
        pagingRepository.getMovies(search, type).cachedIn(viewModelScope)
    }

    var singleMovieState by mutableStateOf(MovieDetailsState())
        private set

    fun onMovieTypeSwitched(movieTypeText: String) {
        viewModelScope.launch {
            movieTypeFlow.emit(movieTypeText)
        }
    }
    fun getMovie(movieId : String){
        viewModelScope.launch {
            singleMovieState = singleMovieState.copy(isLoading = true)
            when(val movieResponse = movieRepository.getMovie(movieId)){
                is ResponseWrapper2.Success -> {
                    singleMovieState = singleMovieState.copy(
                        success = true,
                        movie = movieResponse.data
                    )
                }
                is ResponseWrapper2.Empty -> {
                    singleMovieState = singleMovieState.copy(
                        isEmpty = true
                    )
                }
                is ResponseWrapper2.Error -> {
                    singleMovieState = singleMovieState.copy(
                        failed = true
                    )
                }
                else -> {}
            }
        }
    }

}
data class MovieDetailsState(
    var movie : Movie? = null,
    var isLoading : Boolean = false,
    var success : Boolean = false,
    var failed : Boolean = false,
    var isEmpty : Boolean = false
)